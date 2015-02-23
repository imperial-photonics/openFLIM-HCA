/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import static com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.HCAFLIMPlugin.frame_;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import javax.swing.JOptionPane;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

/**
 *
 * @author Frederik
 */
public class Arduino {
    MMStudio gui_;
    CMMCore core_;
    private static final Arduino fINSTANCE =  new Arduino();
    private static HCAFLIMPluginFrame frame;
    
    
    public Arduino(){
    gui_ = MMStudio.getInstance();
    core_ = gui_.getCore();
    frame = (HCAFLIMPluginFrame) frame_;
    }
        
    public static Arduino getInstance() {
       return fINSTANCE;
    }
    
    public void initializeArduino() {
        try {    
            core_.setProperty("Arduino-Shutter", "OnOff", 1);
            core_.setProperty("Arduino-Switch", "Label", 63);
            core_.setProperty("Arduino-Switch", "Blanking Mode", "Off");
            core_.setProperty("Arduino-Switch", "Sequence", "Off");

        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-initializeArduino");
        }            
    }
    
    public void setArduinoShutterOpen() {
        try {
            core_.setProperty("Arduino-Shutter", "OnOff", "0");
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-openArduinoShutter");
        }
    }
    
    public void setArduinoShutterClose() {
        try {
            core_.setProperty("Arduino-Shutter", "OnOff", "1");
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-closeArduinoShutter");
        }
    }
    
    public int getInputValue(int numInput){
        int in=0;
        String input="AnalogInput"+numInput;
        try {
            in=Integer.parseInt(core_.getProperty("Arduino-Input", input));
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-getInputValue; Cannot get arduino input"+input);
        }
        in=5/1023*in;
        return in;
    }
    
    public String getInputHighLow(int numInput){
        int in=0;
        String highLow;
        String input="AnalogInput"+numInput;
        try {
            in=Integer.parseInt(core_.getProperty("Arduino-Input", input));
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-getInputHighLow; Cannot get arduino input"+input);
        }
        if(in >= 500){
            highLow="high";
        }else if(in< 500){
            highLow="low";
        }else{
            highLow=null;
        }
        return highLow;
    }
    
    public void checkSafety(){
        
       // reading the input  values from Adruino AO and A1. If they are low under 0.5. Then nothing is happening. If they
       // are high. Message is popping up and is telling reduce light.
        boolean check=false;
        int value1=-1;
        int value2=-1;
        value1=getInputValue(0);
        value2=getInputValue(1);
        if (value1==-1){
            value1=0;
            System.out.println("No value detected on Arduino input 0. Go on in unsafe mode. Be aware this can damage the HRI.");
        }
        if (value2==-1){
            value2=0;
            System.out.println("No value detected on Arduino input 1. Go on in unsafe mode. Be aware this can damage the HRI.");
        }
        while(check==false){
        if(value1>0.5&&value2<0.5){
            JOptionPane.showMessageDialog(frame,
            "Incubation light chamber is on. This can damage the HRI. Please turn it off to start the measurement!",
            "Incubation light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if(value1<0.5&&value2>0.5){
            JOptionPane.showMessageDialog(frame,
            "Room light is on. Please turn it off to start the measurement!",
            "Room light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if(value1>0.5&&value2>0.5){
            JOptionPane.showMessageDialog(frame,
            "Room light and incubation chamber light is on. Please turn it off to start the measurement",
            "Light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if (value1<0.5&&value2<0.5){
            check=true;
        }
        }
    }
}
