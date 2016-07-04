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
import ij.io.DirectoryChooser;
// End of added imageJ bits

//Import stuff for file reading
import java.io.*;

// Remember - ImagePlus > ImageStack > ImagePRocessor: http://fiji.sc/Introduction_into_Developing_Plugins

public class Prefind {
    private CMMCore core_;
    private XYZMotionInterface xyzmi_;
    private XYSequencing xyseq_;  
    private MMStudio gui_;    
    private HCAFLIMPluginFrame frame_;
    
    // Prefind results info
    private boolean judgement;
    private double[] h_pix;
    private double[] v_pix;
    
    // Maybe only need this one for a plugin filter?
    static int NO_UNDO;
    
    public Prefind(HCAFLIMPluginFrame parent) {
        // Think the getInstance means we manipulate the original, not a copy?
        gui_ = MMStudio.getInstance();
        core_ = gui_.getCore();
        frame_= HCAFLIMPluginFrame.getInstance();
        ImagePlus rawImage = new ij.ImagePlus();
        ImagePlus output = new ij.ImagePlus();
        h_pix = new double[] {0.0};
        v_pix = new double[] {0.0};
        judgement = false;
    }
    
    public double[] getHCentre(){
        return h_pix;
    }
    
    public double[] getVCentre(){
        return v_pix;
    }
    
    public String getmacropath(){
        String directoryName=ij.IJ.getDirectory("macros");
        String macropath = directoryName+frame_.getSelectedAnalyser();      
        return macropath;
    }
    
    public double[] getvarlimits(int whichvarnumber){
        double[] limits = new double[2];
        //Set defaults
        limits[0]=0;
        limits[1]=0;
        // Get macro path
        String macropath = getmacropath();
        
        boolean success=false;
        try {
            String varname = "";
            //http://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
            //http://www.javapractices.com/topic/TopicAction.do?Id=42
            //http://stackoverflow.com/questions/2788080/java-how-to-read-a-text-file

            try{
                //Create object of FileReader
                FileReader inputFile = new FileReader(macropath);

                //Instantiate the BufferedReader Class
                BufferedReader bufferReader = new BufferedReader(inputFile);

                //Variable to hold the one line data
                String line;

                // Read file line by line and print on the console
                for (int i=0;i<4;i++) {
                    line = bufferReader.readLine();
                    //System.out.println(line);
                    if((i+1)==whichvarnumber){
                        limits[0] = Double.parseDouble(line.split("!!!")[2]);
                        limits[1] = Double.parseDouble(line.split("!!!")[3]);
                    }
                }
                bufferReader.close();
            } catch(IOException e) {
                System.out.println(e);
            }

        } catch (Exception e) {
            success=false;
        }
        if(success=true){
            
        } else {    
            // Get min from macrofile
            limits[0]=0;
            // Get max from macrofile
            limits[1]=0;
        }
        return limits;
    }
    
    public String getvarname(int whichvarnumber){
        // Get macro path
        String macropath = getmacropath();
        String varname = "";
        int maxvars=4; //maximum number of lines with info on variables
        int minparams=4; //minimum number of parameters per line
        //http://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
        //http://www.javapractices.com/topic/TopicAction.do?Id=42
        //http://stackoverflow.com/questions/2788080/java-how-to-read-a-text-file

        //Variable to hold the one line data
        String line;
        
        try{
            //Create object of FileReader
            FileReader inputFile = new FileReader(macropath);

            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);

            // Read file line by line
            // Make sure that there's at least ### parameters in 
            for (int i=0;i<maxvars;i++) {
                Boolean read_error = false;
                
                try{
                    line = bufferReader.readLine();
                } catch (IOException e) {
                    line = "BAD LINE";
                    //System.out.println("Tried to read nonexistent line! Macro too short!");
                }
                if (line!= null){
                    //System.out.println(line);
                    if((line.split("!!!")).length<minparams){
                        varname="";
                        System.out.println("Not enough parameters provided by macro! Should be at least "+minparams);
                    } else if ((i+1)==whichvarnumber){
                        varname = line.split("!!!")[1];
                    }
                } else {
                    System.out.println("Not enough lines in the file!");
                    varname = "UNDEFINED";
                }
            }
            bufferReader.close();
        } catch(IOException e) {
            //Set varname to blank here?
            System.out.println(e);
        }
        
        return varname;
    }
    
    public String getVarValue(int whichvarnumber){
        return frame_.getPrefindSettingValue(whichvarnumber);
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
    
    private int getNoOfParams(){
        String macropath = getmacropath();
        int paramsFound = 0;
        String line="";
        try{
            //Create object of FileReader
            FileReader inputFile = new FileReader(macropath);
            //Instantiate the BufferedReader Class
            BufferedReader bufferReader = new BufferedReader(inputFile);
            // Read file line by line
            // Make sure that there's at least ### parameters in 
            boolean paramsAllRead = false;
            while (paramsAllRead == false) {
                Boolean read_error = false;
                try{
                    line = bufferReader.readLine();
                } catch (IOException e) {
                    line = "BAD LINE";
                    //paramsAllRead = true; //Maybe?
                }
                if (line!= null){
                    if((line.split("//!!!")).length>1){
                        paramsFound++;
                    }
                } else {
                    paramsAllRead = true;
                }
            }
            bufferReader.close();
        } catch(IOException e) {
            //Set varname to blank here?
            System.out.println(e);
        }
        return paramsFound;
    }
    
    private boolean runAMacro(ImagePlus input, String macroname){
        // Reset the target co-ordinates...
        // Garbage collector should deal with the Java nonsense where you need to instantiate an array each time you just want to set new values from scratch...
        // http://stackoverflow.com/questions/4208655/empty-an-array-in-java-processing
        h_pix = new double[] {0};
        v_pix = new double[] {0};
        //We're going to use ### as our split delimiter
        Double pfthresh=frame_.getPrefindThreshold();
        Double pctcover=frame_.getPercentCoverage();
        String paramString = "";
                
        for(int i=1;i<=getNoOfParams();i++){
            String paramName = getvarname(i);
            String paramValue = getVarValue(i);
            paramString = paramString+paramName+"="+paramValue+"###";
        }
        
        String Parameterstring = "Threshold="+pfthresh.intValue()+"###Pctcover="+pctcover.intValue(); //ADD IN GETPERCENTCOVERAGE
        //IJ.run(input, macroname, Parameterstring);
        //IJ.runMacroFile(macroname, Parameterstring);
        IJ.runMacroFile(macroname, paramString);
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
                //Split second part by commas...
                String[] h_array = thisvar[1].split(",");
                //Loop for conversion to double, assignment to array...
                h_pix = new double [h_array.length];
                for(int j=0;j<h_array.length;j++){
                    h_pix [j] = Double.parseDouble(h_array[j]);
                }
            } else if (thisvar[0].equals("v_pix")){
                //Split second part by commas...
                String[] v_array = thisvar[1].split(",");
                //Loop for conversion to double, assignment to array...
                v_pix = new double [v_array.length];
                for(int j=0;j<v_array.length;j++){
                    v_pix [j] = Double.parseDouble(v_array[j]);
                }
            } else if (thisvar[0].equals("Reject")){
                //Special case for not accepting - keep last other than total failure
                System.out.println("Field rejected");
                h_pix = new double[] {0};
                v_pix = new double[] {0};
                input.setTitle("Prefind");
            } else {
                System.out.println("Unknown (or no) arguments returned from the macro! FAIL!");
                input.setTitle("Prefind");
                h_pix = new double[] {0};
                v_pix = new double[] {0};
            }
        }
        System.out.println(feedback);
        
        input.setTitle("Prefind");
        return judgement;
    }
}

