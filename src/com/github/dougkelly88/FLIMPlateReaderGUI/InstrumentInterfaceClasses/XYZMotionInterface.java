/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.InstrumentInterfaceClasses;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.PlateProperties;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Variable;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FOV;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import mmcorej.DeviceType;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;

/**
 *
 * @author dk1109
 */
public final class XYZMotionInterface {

    PlateProperties pp_;
    CMMCore core_;
    String xystage_;
    String zstage_;
    Point2D.Double[] stageWellCentres_ = new Point2D.Double[3];
    Point2D.Double[] xpltWellCentres_ = new Point2D.Double[3];
    AffineTransform transform_;
    HCAFLIMPluginFrame parent_;
    private Variable var_;

    //TODO: implement safety checks for objective fouling. 
    //TODO: deal with objective focal shifts
    //TODO: deal (limited) with camera coregistration with stage move?
    //TODO: set first/last well centres in calibration method
    public XYZMotionInterface(HCAFLIMPluginFrame parent) {
        parent_ = parent;
        pp_ = parent.pp_;
        core_ = parent.core_;
        var_ = Variable.getInstance();
        
        xystage_ = core_.getXYStageDevice();
        zstage_ = core_.getFocusDevice();
                
        stageWellCentres_[0] = new Point2D.Double(106100, 6700); //TL
        stageWellCentres_[1] = new Point2D.Double(7100, 69700);  //BR
        stageWellCentres_[2] = new Point2D.Double(7100, 6700); //TR
        xpltWellCentres_[0] = new Point2D.Double(
                pp_.getTopLeftWellOffsetH(),
                pp_.getTopLeftWellOffsetV());
        xpltWellCentres_[1] = new Point2D.Double(
                pp_.getTopLeftWellOffsetH() + (pp_.getPlateColumns() - 1) * pp_.getWellSpacingH(),
                pp_.getTopLeftWellOffsetV() + (pp_.getPlateRows() - 1) * pp_.getWellSpacingV());
        xpltWellCentres_[2] = new Point2D.Double(
                pp_.getTopLeftWellOffsetH() + (pp_.getPlateColumns() - 1) * pp_.getWellSpacingH(),
                pp_.getTopLeftWellOffsetV());
        transform_ = deriveAffineTransform(xpltWellCentres_, stageWellCentres_);
        
        try{
            core_.setPosition(zstage_, Double.parseDouble(core_.getProperty("Objective", "Safe Position")));
            //core_.home(xystage_);
            core_.waitForDeviceType(DeviceType.XYStageDevice);
            gotoFOV(new FOV("C4", pp_, 1000));
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        } 
    }

    public int gotoFOV(FOV fov) {
        double []Offsets = parent_.getObjectiveOffsets();
        try {
            Point2D.Double stage = fovXYtoStageXY(fov);
            core_.setXYPosition(xystage_, stage.getX()+Offsets[0], stage.getY()+Offsets[1]);
            // parent_.currentFOV_ = fov;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        enableManualXYControls(var_.manStageCheck);
        return 1;
    }

    public FOV getCurrentFOV() {
        double []Offsets = parent_.getObjectiveOffsets();
        try {
            Point2D.Double xy = stageXYtoFOVXY(core_.getXYStagePosition(xystage_));
            Double z = getZAbsolute();
            return new FOV(xy.getX()-Offsets[0], xy.getY()-Offsets[1], z-Offsets[2], pp_);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new FOV("A1", pp_, 0);   // replace with something that more clearly
        // indicated error?
    }
    
    public boolean isStageBusy(){
        if(!parent_.getTestmode()){
            try {
                    Point2D.Double xy = stageXYtoFOVXY(core_.getXYStagePosition(xystage_));
                    return false;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return true;
                } 
        }
        // Stage moves are instantaneous in testmode...
        return false;
    }

    public Point2D.Double fovXYtoStageXY(FOV fov) {

        Point2D.Double xy = new Point2D.Double(fov.getX(), fov.getY());
        Point2D.Double xyout = new Point2D.Double();

        transform_.transform(xy, xyout);

        return xyout;
    }

    public Point2D.Double stageXYtoFOVXY(Point2D.Double stagexy) {

        Point2D.Double xyout = new Point2D.Double();
        try {
            transform_.inverseTransform(stagexy, xyout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return xyout;
    }

    public void setStageWellCentres(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        stageWellCentres_[0] = p1;
        stageWellCentres_[1] = p2;
        stageWellCentres_[2] = p3;
    }

    public void setXPLTWellCentres(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        xpltWellCentres_[0] = p1;
        xpltWellCentres_[1] = p2;
        xpltWellCentres_[2] = p3;
    }

    public static AffineTransform deriveAffineTransform(
            Point2D.Double[] xplt, Point2D.Double[] stage) {
            // GENERAL CASE:    
        // if S is stage space and P is XPLT plate space, 
        // and T is the matrix to transform between the two:
        // S = TP
        // SP^-1 = TPP^-1
        // T = SP^-1;
        // where S is a 2x3 matrix with 3 points, T is a 2x3 matrix and P is a 
        // 3x3 matrix with 3 points and the bottom row occupied by ones...

        double[][] Parr = {{xplt[0].getX(), xplt[1].getX(), xplt[2].getX()},
        {xplt[0].getY(), xplt[1].getY(), xplt[2].getY()}, {1, 1, 1}};
        RealMatrix P = MatrixUtils.createRealMatrix(Parr);

        double[][] Sarr = {{stage[0].getX(), stage[1].getX(), stage[2].getX()},
        {stage[0].getY(), stage[1].getY(), stage[2].getY()}};
        RealMatrix S = MatrixUtils.createRealMatrix(Sarr);

        RealMatrix Pinv = (new LUDecomposition(P)).getSolver().getInverse();
        RealMatrix transformationMatrix = S.multiply(Pinv);

        double m00 = transformationMatrix.getEntry(0, 0);
        double m01 = transformationMatrix.getEntry(0, 1);
        double m02 = transformationMatrix.getEntry(0, 2);
        double m10 = transformationMatrix.getEntry(1, 0);
        double m11 = transformationMatrix.getEntry(1, 1);
        double m12 = transformationMatrix.getEntry(1, 2);

        return new AffineTransform(m00, m10, m01, m11, m02, m12);
    }

    public boolean moveXYRelative(double x, double y) {
        try {
            core_.setRelativeXYPosition(xystage_, x, y);
            //parent_.currentFOV_.setX(x);
            //parent_.currentFOV_.setY(y);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean moveZRelative(double z) {
        try {
            core_.setRelativePosition(zstage_, z);
            //parent_.currentFOV_.setZ(z);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean moveZAbsolute(double z) {
        double []Offsets = parent_.getObjectiveOffsets();
        try {
            // TODO: check within bounds?
            // TODO: calibrate to make up for lack of parfocality...
            core_.setPosition(zstage_, z+Offsets[2]);
            //parent_.currentFOV_.setZ(z);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    
    public Double getZAbsolute(){
        double []Offsets = parent_.getObjectiveOffsets();
        double z = 0.0;
        
        try{
            z = core_.getPosition(zstage_);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        return z-Offsets[2];
    }

    public void enableManualXYControls(boolean on){
        try {
                if (on)
                    core_.setProperty(xystage_, "Enable joystick?", "True");
                else {
                    core_.setProperty(xystage_, "Enable joystick?", "False");
                    double x = core_.getXPosition(xystage_);
                    double y = core_.getYPosition(xystage_);
                    //parent_.currentFOV_.setX(x);
                    //parent_.currentFOV_.setY(y);
                            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void enableManualZControls(boolean on){
        try {
                if (on)
                    core_.setProperty("OlympusHub", "Control", "Manual + Computer");
                else {
                    core_.setProperty("OlympusHub", "Control", "Computer");
                    double z = core_.getPosition(zstage_);
                    //parent_.currentFOV_.setZ(z);
                }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    
    }
    
    public void enableManualZOnly(boolean on){
        try {
                if (on)
                    core_.setProperty("ManualFocus", "FocusWheel", "Frame");
                else {
                    core_.setProperty("ManualFocus", "FocusWheel", "Off");
                    double z = core_.getPosition(zstage_);
                    //parent_.currentFOV_.setZ(z);
                }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    
    }
    
    public double customAutofocus(Double offset){
        Double focusOffset = null;
        
        if(var_.autofocusWhich.equals("ZDC Olympus")){
            try{
                core_.setProperty("Objective", "Use Safe Position", "0");
                this.moveZRelative(-offset);
                core_.setProperty("AutoFocusZDC", "MeasureOffset", "Now");
                focusOffset = Double.parseDouble(core_.getProperty("AutoFocusZDC", "Offset"));
            //    this.moveZAbsolute(offset + focusOffset);
                this.moveZRelative(offset - focusOffset); // Doug
            //    this.moveZRelative(offset);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        return focusOffset;
        }
        else if(var_.autofocusWhich.equals("Definite focus Zeiss")){
            System.out.println("autofocusComboBox in ProSetting is on Definite focus Zeis. Not implemented yet! Go back to ZDC Olympus.");
        }
        else{
            System.out.println("autofocusComboBox in ProSetting is seeing nothing!");
        }   
         return focusOffset;   
    }
    
    public void runInitializationRitual(){
        try {
            core_.home(xystage_);
        } catch (Exception ex) {
            Logger.getLogger(XYZMotionInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
