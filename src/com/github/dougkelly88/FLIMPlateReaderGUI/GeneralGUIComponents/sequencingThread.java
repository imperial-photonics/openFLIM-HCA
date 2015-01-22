/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.FLIMClasses.GUIComponents.FLIMPanel;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Acquisition;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.PlateProperties;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.VariableTest;
import com.github.dougkelly88.FLIMPlateReaderGUI.InstrumentInterfaceClasses.XYZMotionInterface;
import com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.GUIComponents.LightPathPanel;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.AcqOrderTableModel;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators.FComparator;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators.SeqAcqSetupChainedComparator;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators.TComparator;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators.WellComparator;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators.ZComparator;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FOV;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FilterSetup;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SeqAcqSetup;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.TimePoint;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents.SpectralSequencing;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents.TimeCourseSequencing;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents.XYSequencing;
import com.github.dougkelly88.FLIMPlateReaderGUI.XYZClasses.GUIComponents.XYZPanel;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import mmcorej.DeviceType;

/**
 *
 * @author Frederik
 */
public class sequencingThread implements Runnable{
    public CMMCore core_;
    static HCAFLIMPluginFrame frame_;
    private VariableTest var_;
    public PlateProperties pp_;
    public XYZMotionInterface xyzmi_;
    private AcqOrderTableModel tableModel_;
    public FOV currentFOV_;
    private XYSequencing xYSequencing1;
    private TimeCourseSequencing timeCourseSequencing1;
    private LightPathPanel lightPathControls1;
    private FLIMPanel fLIMPanel1;
    private SpectralSequencing spectralSequencing1;
    private XYZPanel xYZPanel1;
    
    
            
    @Override
    public void run() {
            var_= VariableTest.getInstance();
            System.out.println("in sequencingThread");
            System.out.println("in sequencingThread"+frame_.testMu);
            Acquisition acq = new Acquisition();
            ArrayList<FOV> fovs = new ArrayList<FOV>();
            ArrayList<TimePoint> tps = new ArrayList<TimePoint>();
            ArrayList<FilterSetup> fss = new ArrayList<FilterSetup>();

            // get all sequence parameters and put them together into an 
            // array list of objects containing all acquisition points...
            // Note that if a term is absent from the sequence setup, current
            // values should be used instead...
            
            List<SeqAcqSetup> sass = new ArrayList<SeqAcqSetup>();
//            ArrayList<String> order = tableModel_.getData();
            ArrayList<String> order = frame_.order;
            System.out.println("in sequencingThread"+order);
            if (!order.contains("XYZ")){
                fovs.add(xyzmi_.getCurrentFOV());
            } else {
                 fovs = xYSequencing1.getFOVTable();
            }
            
            if (!order.contains("Time course")){
                tps.add(new TimePoint(0.0));
            } else {
                tps = timeCourseSequencing1.getTimeTable();
            }
            
            if (!order.contains("Filter change")){
                int intTime = 100;
                try {
                    intTime = (int) core_.getExposure();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                fss.add(new FilterSetup(lightPathControls1, intTime, fLIMPanel1));
            } else {
                fss = spectralSequencing1.getFilterTable();
            } 
            
            List<Comparator<SeqAcqSetup>> comparators = new ArrayList<Comparator<SeqAcqSetup>>();
            
            for (FOV fov : fovs){
                for (TimePoint tp : tps){
                    for (FilterSetup fs : fss){
                        sass.add(new SeqAcqSetup(fov, tp, fs));
                    }
                }
            }
            
//55           System.out.print(sass+"\n");
            // use chained comparators to sort by multiple fields SIMULTANEOUSLY,
            // based on order determined in UI table. 
            // DEBUG
//            testSorting(sass);
            for (String str : order){
                if (str.equals("XYZ")){
                        comparators.add(new WellComparator());
                        comparators.add(new ZComparator());
                }
//                else if (str.equals("Z"))
//                    comparators.add(new ZComparator());
                else if (str.equals("Filter change"))
                    comparators.add(new FComparator());
                else if (str.equals("Time course"))
                    comparators.add(new TComparator());
            }
            Collections.sort(sass, new SeqAcqSetupChainedComparator(comparators));
//55            System.out.print(sass+"\n");
            // DEBUG
//            System.out.println("After sorting according to UI: ");
//            for (SeqAcqSetup sas : sass){
//                System.out.println(sas.toString());
//            }
//            System.out.println("Pause here during debug");
            
            long start_time = System.currentTimeMillis();
            // TODO: modify data saving such that time courses, z can be put in a 
            // single OME.TIFF. DISCUSS WITH IAN!
            // N.B. z should be relatively easy...
            // for now, just make a base folder and name files based on 
            // filterlabel, time point.  
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
            String baseLevelPath = var_.basepath + "/Sequenced FLIM acquisition " +
                    timeStamp;
            for (FilterSetup fs : fss){
                String flabel = fs.getLabel();
//                File f = new File(baseLevelPath);
                File f = new File(baseLevelPath + "/" + flabel);
                try {
                    boolean check1 = f.mkdirs();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            
            
            
//            for (SeqAcqSetup sas : sass){
            Double lastTime = 0.0;
            String lastFiltLabel = "";
            FOV lastFOV = new FOV(0, 0, 0, pp_);
            Double lastZ = 0.0;
//            int fovSinceLastAF = 0;
            for (int ind = 0; ind < sass.size(); ind++){
                // TODO: how much can these steps be parallelised?
                // set FOV params
                SeqAcqSetup sas = sass.get(ind);
                System.out.println(sas);
                // if time point changed different from last time, wait until 
                // next time point reached...
                if ((!sas.getTimePoint().getTimeCell().equals(lastTime)) & (order.contains("Time course"))){
                    Double next_time = sas.getTimePoint().getTimeCell() * 1000;
                    while ((System.currentTimeMillis() - start_time) < next_time){
                        Double timeLeft = next_time - (System.currentTimeMillis() - start_time);
                        System.out.println("Waiting for " + timeLeft + " until next time point...");
                    }
                }
                // if FOV different, deal with it here...
                if ( ( (!sas.getFOV().equals(lastFOV)) | (sas.getFOV().getZ() != lastZ) ) & (order.contains("XYZ")) ){
                    // TODO: this needs tweaking in order that autofocus works properly with Z stacks...
                    // Perhaps only do when XY change, and not Z?
                    xyzmi_.gotoFOV(sas.getFOV());
                    if (xYZPanel1.getAFInSequence())
                        xyzmi_.customAutofocus(xYZPanel1.getSampleAFOffset());                     
                }
                
                // set filter params - can these be handled by a single class?
                if ( (!sas.getFilters().getLabel().equals(lastFiltLabel)) & order.contains("Filter change") ){
                    try {
                        String s = core_.getShutterDevice();
                        if (!"".equals(s))
                            core_.setShutterOpen(false);
                        s = sas.getFilters().getExFilt();
                        if (!"".equals(s))
                            core_.setProperty("SpectralFW", "Label", s);
                        s = sas.getFilters().getCube();
                        if (!"".equals(s))
                            core_.setProperty("FilterCube", "Label", s);
                        s = sas.getFilters().getEmFilt();
                        if (!"".equals(s))
                            core_.setProperty("CSUX-Filter Wheel", "Label", s);
                        s = sas.getFilters().getNDFilt();
                        if (!"".equals(s))
                            core_.setProperty("NDFW", "Label", s);
                        core_.setExposure(sas.getFilters().getIntTime());
                        s = sas.getFilters().getDiFilt();
                        if (!"".equals(s))
                            core_.setProperty("CSUX-Dichroic Mirror", "Label", s);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                // do acquisition
                String fovLabel = String.format("%05d", ind);
//                String path = baseLevelPath + "/" + "T=" + sas.getTimePoint().getTimeCell() + 
                String path = baseLevelPath + "/" + sas.getFilters().getLabel() + "/"+ 
                        " Well=" + sas.getFOV().getWell() +                        
                        " X=" + sas.getFOV().getX() +
                        " Y=" + sas.getFOV().getY() +
                        "T=" + sas.getTimePoint().getTimeCell() + 
                        " Filterset=" + sas.getFilters().getLabel() + 
                        " Z=" + sas.getFOV().getZ() +
                        " ID=" + fovLabel + 
                        ".ome.tiff";
                try{
                    core_.setShutterOpen(true);
                    core_.waitForDeviceType(DeviceType.XYStageDevice);
                    core_.waitForDeviceType(DeviceType.AutoFocusDevice);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                
                acq.snapFLIMImage(path, sas.getFilters().getDelays(), sas);
                
                // shutter laser
                // TODO: have this work properly in line with auto-shutter?
                try {
//                    core_.setProperty("NDFW", "Label", "STOP");
                    core_.setShutterOpen(false);
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                
                lastTime = sas.getTimePoint().getTimeCell();
                lastFOV = sas.getFOV();
                lastZ = sas.getFOV().getZ();
                lastFiltLabel = sas.getFilters().getLabel();
                
//55            Stop acquisition
                      
                if(frame_.abortHCAsequencBoolean){
                    break;
                }
                
               
            }
            
            // RESET DELAY TO BE CONSISTENT WITH UI
            try{
                core_.setProperty("Delay box", "Delay (ps)", fLIMPanel1.getCurrentDelay());
            } catch (Exception  e){
                System.out.println(e.getMessage());
            }
    }
    
}
