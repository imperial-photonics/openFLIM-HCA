/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
AO= incubation light photodiode
A1= room light photodiode
A2= laser intensity photodiode
All digital outputs used as shutter
*/
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import static com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.HCAFLIMPlugin.frame_;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mmcorej.CMMCore;
import static oracle.jrockit.jfr.events.Bits.doubleValue;
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
    double th1=0.5;
    double th2=0.5;
    private VariableTest var_;
    
    public Arduino(){
    gui_ = MMStudio.getInstance();
    core_ = gui_.getCore();
    frame = (HCAFLIMPluginFrame) frame_;
    var_ = VariableTest.getInstance();
    
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
    
    public double getInputValue(int numInput){
        String value=null;
        String input="AnalogInput"+numInput;
        try {
            value=core_.getProperty("Arduino-Input", input);
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-getInputValue; Cannot get arduino input"+input);
        }
        double in1=Double.parseDouble(value)*5/1023;
        return in1;
    }
    
    public String getInputHighLow(int numInput){
        String value=null;
        int in=0;
        String highLow;
        String input="AnalogInput"+numInput;
        try {
            value=core_.getProperty("Arduino-Input", input);
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; methode-getInputHighLow; Cannot get arduino input"+input);
        }
        int ind=value.indexOf(".");
        value=value.substring(0, ind);
        in=Integer.parseInt(value);
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
        double value1=-1;
        double value2=-1;
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
        if(value1>var_.th1&&value2<var_.th2){
            JOptionPane.showMessageDialog(frame,
            "Incubation light chamber is on. This can damage the HRI. Please turn it off to start the measurement!",
            "Incubation light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if(value1<var_.th1&&value2>var_.th2){
            JOptionPane.showMessageDialog(frame,
            "Room light is on. Please turn it off to start the measurement!",
            "Room light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if(value1>var_.th1&&value2>var_.th2){
            JOptionPane.showMessageDialog(frame,
            "Room light and incubation chamber light is on. Please turn it off to start the measurement",
            "Light alarm",
            JOptionPane.WARNING_MESSAGE);
        } else if (value1<var_.th1&&value2<var_.th2){
            check=true;
        }
        }
    }
    
    public double getLaserIntensity(){
        double value=getInputValue(2);
        return value;
    }

    public int testApp() {
       String value="-1";
       int valInt=-1;
        try {
            value = core_.getProperty("Arduino-Input", "AnalogInput0");
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        int ind=value.indexOf(".");
        value=value.substring(0, ind);
        valInt=Integer.parseInt(value);
       return valInt; 
    }
}
