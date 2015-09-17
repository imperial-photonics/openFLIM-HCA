/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
AO= incubation light photodiode
A1= room light photodiode
A2= mercury burner vs. light path microswitch
A5= laser intensity photodiode
D8=shutter
D9=shutter
D10=LED
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
    private MMStudio gui_;
    private CMMCore core_;
    private static final Arduino fINSTANCE =  new Arduino();
    private static HCAFLIMPluginFrame frame;
    double th1=0.5;
    double th2=0.5;
    private Variable var_;
    
    public static Arduino getInstance() {
       return fINSTANCE;
    }
    
    
    public Arduino(){
        try{
            gui_ = MMStudio.getInstance();
            core_ = gui_.getCore();
        }catch(Exception e){
            System.out.println("Error: Arduino initalization unsure!");
        }
    frame = (HCAFLIMPluginFrame) frame_;
    var_ = Variable.getInstance();
    }
    
    public void initializeArduino() {
        try {    
            core_.setProperty("Arduino-Shutter", "OnOff", "0");
            core_.setProperty("Arduino-Switch", "State", "3");
            core_.setProperty("Arduino-Switch", "Blanking Mode", "Off");
            core_.setProperty("Arduino-Switch", "Sequence", "Off");

        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; initializeArduino");
        }           
    }
    
    public String setMode(String mode){
        if(null != mode)switch (mode) {
            case "shutter":
                try {
                    core_.setProperty("Arduino-Switch", "State", "3");
                } catch (Exception ex) {
                    System.out.println("Error: Class-Arduino; setShutterMode; shutter");
                }   break;
            case "led":
                try{
                    core_.setProperty("Arduino-Switch", "State", "4");
                } catch (Exception ex) {
                    System.out.println("Error: Class-Arduino; setShutterMode; led");
            }   break;
            default:
                mode="Mode not found. Valid commands 'shutter' or 'led'.";
                break;
        }
        return mode;
    }
    
    public void setDigitalOutHigh() {
        
        
        try {
            core_.setProperty("Arduino-Shutter", "OnOff", "1");
            /*
            if(core_.getProperty("OlympusHub", "SidePort")=="SidePort"){
                core_.setProperty("Arduino-Shutter", "OnOff", "1");
            }
            else{
                popUpwindow("Please change to the side port path if you are using the laser!", "Ocular path choosen.");
            }*/
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; setDigitalOutHigh");
        }
    }
    
    public void setDigitalOutLow() {
        try {
            core_.setProperty("Arduino-Shutter", "OnOff", "0");
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; setDigitalOutLow");
        }
    }
    
    public double getInputValue(int numInput){
        String value="-1";
        String input="AnalogInput"+numInput;
        try {
            value=core_.getProperty("Arduino-Input", input);
        } catch (Exception ex) {
            System.out.println("Error: Class-Arduino; getInputValue; Cannot get arduino input"+input);
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
            System.out.println("Error: Class-Arduino; getInputHighLow; Cannot get arduino input"+input);
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
    
    public boolean checkSafety(){
        
       // reading the input  values from Adruino AO and A1. If they are low under 0.5. Then nothing is happening. If they
       // are high. Message is popping up and is telling reduce light.
        if (var_.safetyOff==false){
            double value0=-1;
            double value1=-1;
            double value2=-1;
            value0=getInputValue(0);
            value1=getInputValue(1);
            value2=getInputValue(2);
            if (value0==-1){
                System.out.println("No value detected on Arduino input 0. Go on in unsafe mode. Be aware this can damage the HRI.");
            } else if (value1==-1){
                System.out.println("No value detected on Arduino input 1. Go on in unsafe mode. Be aware this can damage the HRI.");
            } else if(value0>var_.th1&&value1<var_.th2){
                popUpwindow("Incubation light chamber is on. This can damage the HRI. Please turn it off to start the measurement!", "Incubation light alarm");
            } else if(value0<var_.th1&&value1>var_.th2){
                popUpwindow("Room light is on. Please turn it off to start the measurement!", "Room light alarm");
            } else if(value0>var_.th1&&value1>var_.th2){
                popUpwindow("Room light and incubation chamber light is on. Please turn it off to start the measurement.", "Light alarm");
            } else if(value2<1000){
               popUpwindow("Mercury burner path is still selected, please change to laser excitation path!", "Mercury burner path chosen."); 
            }
            else if (value0<var_.th1&&value1<var_.th2){
                return false;
            } 
                
            return true;
        }else{
        return false;
        }
    }
    
    public double getLaserIntensity(){
        double value=getInputValue(5)*20;
        value = Math.round(100.0 * value) / 100.0;
        return value;
    }
    
    public void popUpwindow(String text, String header){
        Object[] options = {"Nope",
                        "Ok, I will check it!"};
        int n = JOptionPane.showOptionDialog(frame,
            text,
            header,
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]
        );
    }
    
}
