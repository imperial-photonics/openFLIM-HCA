/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

/**
 *
 * @author Sunil
 */

// Import the Micromanager core
import mmcorej.CMMCore;

// Import HCAFLIMPluginFrame for parent access
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;

// Import XYZ motion interface for moving around
import com.github.dougkelly88.FLIMPlateReaderGUI.InstrumentInterfaceClasses.XYZMotionInterface;

// Import XYSequencing for access to generate search patterns
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents.XYSequencing;
import ij.IJ;

// Import MMStudio to get access to the GUI - Is this for MM or IJ???
import org.micromanager.MMStudio;

// Try and import stuff for ImageJ imageprocessing capabilities
//Cut-paste from: https://micro-manager.org/w/images/b/b6/RatiometricImaging_singleImage.bsh
//import ij.*; // Was unused?
import ij.gui.*;
//import org.micromanager.api.AcquisitionOptions; - cannot find symbol?
//import ij.WindowManager;
//import java.lang.System; // Was unused?
import ij.process.*;
import ij.ImagePlus;
//import ij.plugin.*; // Was unused?
//import java.lang.Math; // Was unused?
//import java.awt.image.*; // Was unused?
//import ij.measure.*; // Was unused?
//import ij.text.*; // Was unused?
//import ij.plugin.filter.*; // Was unused?
import org.micromanager.utils.ImageUtils;
// End of added imageJ bits

// Remember - ImagePlus > ImageStack > ImagePRocessor: http://fiji.sc/Introduction_into_Developing_Plugins

public class Prefind {
    private CMMCore core_;
    private XYZMotionInterface xyzmi_;
    private XYSequencing xyseq_;  
    private MMStudio gui_;    
    private HCAFLIMPluginFrame frame_;
    
    // Prefind results info
    private boolean judgement = false;
    private double h_pix=0;
    private double v_pix=0;
    
    // Maybe only need this one for a plugin filter?
    static int NO_UNDO;
    
    public Prefind(HCAFLIMPluginFrame parent) {
        // Think the getInstance means we manipulate the original, not a copy?
        gui_ = MMStudio.getInstance();
        core_ = gui_.getCore();
        frame_= HCAFLIMPluginFrame.getInstance();
        ImagePlus rawImage = new ij.ImagePlus();
        ImagePlus output = new ij.ImagePlus();
    }
    
    public double getHCentre(){
        return h_pix;
    }
    
    public double getVCentre(){
        return v_pix;
    }
    
    public boolean getJudgement(){
        return judgement;
    }
    
    public boolean checkPrefindimg(){
        // Always assume rejection to start with
        return false;
    }
    
    public ImagePlus Snapandshow(ImagePlus prefindImage){
        // Test function to check core access
        try{
            core_.snapImage();            
            ImageProcessor improc0 = ImageUtils.makeProcessor(core_,core_.getImage());
            prefindImage.setProcessor(null, improc0);
            prefindImage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
        return prefindImage;
    }

    public boolean Analyse(ImagePlus input){
        // For now, default to doing a simple intensity threshold...
        //return IntensityThresh(input);
        
        //Run a macro of name macroname.ijm, located in MM directory > Plugins
        String macroname = frame_.getSelectedAnalyser();
        //Also choose the form of the destructor? ;)
        //Remove the .ijm at the end of the filename
        String[] parts = macroname.split(".ijm");
        //Hopefully there isn't anyone putting ".ijm" in the middle of their filenames...
        return runAMacro(input, parts[0]);
    }
    
    private boolean IntensityThresh(ImagePlus input){
        int bytedepth = input.getBytesPerPixel();
        double maxthreshold = (Math.pow(2,(bytedepth*8)))-1;
        Double minthreshold = frame_.getPrefindThreshold();
        input.getProcessor().setThreshold(minthreshold, maxthreshold, 0);
        input.getProcessor().threshold(minthreshold.intValue());
        input.updateAndDraw();
        return false;
    }
    
    private boolean runAMacro(ImagePlus input, String macroname){
        // Reset the target co-ordinates...
        h_pix=0;
        v_pix=0;
        //We're going to use ### as our split delimiter
        Double pfthresh=frame_.getPrefindThreshold();
        Double pctcover=frame_.getPercentCoverage();
        String Parameterstring = "Threshold="+pfthresh.intValue()+"###Pctcover="+pctcover.intValue(); //ADD IN GETPERCENTCOVERAGE
        //IJ.run(input, macroname, Parameterstring);
        IJ.runMacroFile(macroname, Parameterstring);
        String feedback = (input.getTitle());
        String[] feedbackarray = feedback.split("###"); // In case we try to get other parameters back too (mostly relative pixel positions for targeting)
        // Look up Maps for ~equivalent to dynamically named variables. For now though....
        // Declare variables that we think might be passed back...
        // Assume that the first thing back 
        judgement = false;

        // Read 
        for (int i=0;i<feedbackarray.length;i++){
            String[] thisvar=feedbackarray[i].split("=");
            // Note: using == is a bad idea, especially for strings etc
            if (thisvar[0].equals("Accept")){
                judgement=true;
            } else if (thisvar[0].equals("h_pix")){
                h_pix=Double.parseDouble(thisvar[1]);
            } else if (thisvar[0].equals("v_pix")){
                v_pix=Double.parseDouble(thisvar[1]);   
            } else {
                System.out.println("Unknown (or no) arguments returned from the macro! FAIL!");
                h_pix=0;
                v_pix=0;
                return false;
            }
        }
        System.out.println(feedback);
        
        input.setTitle("Prefind");
        return judgement;
    }
}

