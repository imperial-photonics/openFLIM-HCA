/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

/**
 *
 * @author Frederik
 */
public class Arduino {
    MMStudio gui_;
    CMMCore core_;
    int eight=0;
    int nine=0;
    int ten=0;
    int eleven=0;
    int twelve=0;
    int thirteen=0;
    private static final Arduino fINSTANCE =  new Arduino();
    
    public Arduino(){
    gui_ = MMStudio.getInstance();
    core_ = gui_.getCore();
    }
        
    public static Arduino getInstance() {
       return fINSTANCE;
    }
    
    public void initializeArduino() {
        
            /*     if(numOut.contains(8)){
            eight=1;
            }
            if(numOut.contains(9)){
            nine=2;
            }
            if(numOut.contains(10)){
            ten=4;
            }
            if(numOut.contains(11)){
            eleven=8;
            }
            if(numOut.contains(12)){
            twelve=16;
            }
            if(numOut.contains(13)){
            thirteen=32;
            }
            int sum=eight+nine+ten+eleven+twelve+thirteen;
            core_.setProperty("Arduino-Switch", "Label", sum);*/
        try {    
            core_.setProperty("Arduino-Shutter", "OnOff", 1);
            core_.setProperty("Arduino-Switch", "Label", 63);
            core_.setProperty("Arduino-Switch", "Blanking Mode", "Off");
            core_.setProperty("Arduino-Switch", "Sequence", "Off");

        } catch (Exception ex) {
           System.out.println("Error: Class-Arduino; methode-initializeArduino");
        }            
        eight=0;
        nine=0;
        ten=0;
        eleven=0;
        twelve=0;
        thirteen=0;
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
        in=5/1025*in;
        return in;
    }
    
    public String getInputHighLow(int numInput){
        int in=0;
        String highLow=null;
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
}
