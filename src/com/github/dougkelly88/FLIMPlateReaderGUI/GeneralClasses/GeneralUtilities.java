/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;

/**
 *
 * @author Sunil
 */

public class GeneralUtilities {
    int h_binning = 1;
    int v_binning = 1;
    public HCAFLIMPluginFrame parent_;
    
    public GeneralUtilities(HCAFLIMPluginFrame HCAFPF){
        //Default constructor
        parent_ = HCAFPF;
    }
    
    public int[] getCameraBinning(){
        int[] Returnvalue = {1,1};
        String Binningstring = "1x1";
        try{
            Binningstring = parent_.core_.getProperty("Camera", "Binning");
        } catch (Exception e) {
            System.out.println("FAIL trying to read camera binning, exception: "+e);
        }
        
        String Delimiters = "x|X|by";
        String[] Binvalues = Binningstring.split(Delimiters);
        
        Double Xbin=0.0;
        int Xbinint=0;
        Double Ybin=0.0;
        int Ybinint=0;
        
        if (Binvalues.length > 1){
            // Must be blah by blah...
            // This bit: replaceAll("[^\\d.]", "") dumps all non-numeric characters (excluding .)...
            Xbin = (Double.parseDouble(Binvalues[0].replaceAll("[^\\d.]", "")));
            Xbinint = Xbin.intValue();
            Ybin = (Double.parseDouble(Binvalues[1].replaceAll("[^\\d.]", "")));
            Ybinint = Ybin.intValue();            
        } else {
            // Square binning
            Xbin = (Double.parseDouble(Binvalues[0].replaceAll("[^\\d.]", "")));
            Xbinint = Xbin.intValue();
            Ybinint = Xbinint;   
        }
    Returnvalue[0] = Xbinint;
    Returnvalue[1] = Ybinint;
    return Returnvalue;
    }
    
// CAN'T seem to get these called from PrefindPanel.java - moved there...    
    
//    public int SanityCheckInt(String input_text){
//        int retval = (int) Math.round(SanityCheckDouble(input_text));
//        return retval;
//    }
//
//    public double SanityCheckDouble(String input_text){
//        String NumberRegex = "[^0-9]"; //The ^ at the start means "anything except whatever's next"
//        String OriginalString=input_text;
//        String[] SplitArray = OriginalString.split("\\."); 
//        String Output="";
//        //OK, so if we don't see a decimal point, assume it's an int
//        if(SplitArray.length!=0){
//            for (int i=0;i<SplitArray.length;i++){
//                if(i==(SplitArray.length-1)&&i>0){
//                    Output=Output+".";
//                }
//                String RegexedString = SplitArray[i].replaceAll(NumberRegex, "");
//                //System.out.println(RegexedString);
//                Output=Output+RegexedString;
//            }
//        } else {
//            Output=OriginalString.replaceAll(NumberRegex, "");
//        }
//        int res=0;
//        res=Output.length();
//        //OK, we should have a sensible string now (excluding if just a decimal)
//        //but could still be a double if there's no digits before the decimal
//        if(Output.equals(".")){
//            Output="0";
//        }
//        Double retval=Double.parseDouble(Output);
//        //System.out.println(retval);
//        return retval;
//    }
}
