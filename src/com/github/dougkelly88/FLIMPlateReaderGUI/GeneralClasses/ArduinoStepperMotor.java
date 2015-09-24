/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import static com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.HCAFLIMPlugin.frame_;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

/**
 *
 * @author Frederik
 */
public class ArduinoStepperMotor {
    private MMStudio gui_;
    private CMMCore core_;
    private static final ArduinoStepperMotor fINSTANCE =  new ArduinoStepperMotor();
    private static HCAFLIMPluginFrame frame;
    
    public static ArduinoStepperMotor getInstance() {
       return fINSTANCE;
    }
    
    
    public ArduinoStepperMotor(){
        try{
            gui_ = MMStudio.getInstance();
            core_ = gui_.getCore();
        }catch(Exception e){
            System.out.println("Error: ArduinoStepperMotor initalization unsure!");
        }
    frame = (HCAFLIMPluginFrame) frame_;
    }
    /*
    stepper motor functions
    DO8=Sleep
    DO9=Step
    DO10=Dir
    */
     
    
    public void wake(){
        try {
            core_.setProperty("ArduinoSM-Switch", "State", "1");
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Set Arduino Switch state to 1");
    }
    
    public void sleep(){
        try {
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "0");
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Set Arduino Switch shutter 0");
    }
    
    public void stepRight(){
        /* one step in "right" direction
        first digital outputs (DO) set to high (activate motor)
        second enable DO8/9 ON (sleep+direction)
        third enable DO8/9/10 ON (sleep+direction+step)
        last set DO low (sleep mode to prevent motor from heating)
        */
        try {
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "1");
            core_.setProperty("ArduinoSM-Switch", "State", "3");
            core_.setProperty("ArduinoSM-Switch", "State", "7");
            core_.setProperty("ArduinoSM-Switch", "State", "3");
            core_.setProperty("ArduinoSM-Switch", "State", "7");
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "0");
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Set Arduino stepRight");
    }
    
    public void stepLeft(){
        /* one step in "right" direction
        first digital outputs (DO) set to high (activate motor)
        second enable DO8 ON (sleep)
        third enable DO8/9/10 ON (sleep+direction+step)
        last set DO low (sleep mode to prevent motor from heating)
        */
        try {
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "1");
            core_.setProperty("ArduinoSM-Switch", "State", "1");
            TimeUnit.SECONDS.sleep((long) 1);
            core_.setProperty("ArduinoSM-Switch", "State", "7");
            TimeUnit.SECONDS.sleep((long) 1);
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "0");
        } catch (Exception ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Set Arduino stepLeft");
    }
    
    public void turnRight(){
        try {
            core_.setProperty("ArduinoSM-Shutter", "OnOff", "1");
            for(int i=0; i<100; i++)
            {   
                core_.setProperty("ArduinoSM-Switch", "State", "3");
                core_.setProperty("ArduinoSM-Switch", "State", "7");
                core_.setProperty("ArduinoSM-Switch", "State", "3");
                core_.setProperty("ArduinoSM-Switch", "State", "7");
                core_.setProperty("ArduinoSM-Switch", "State", "3");
                core_.setProperty("ArduinoSM-Switch", "State", "7");
                core_.setProperty("ArduinoSM-Switch", "State", "3");
            }
            
            } catch (Exception ex) {
                Logger.getLogger(ArduinoStepperMotor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    public void turnLeft(){
        try {
            core_.setProperty("ArduinoSM-Switch", "State", "7");
            
            for(int i=0; i<50; i++)
            {
            
                core_.setProperty("ArduinoSM-Shutter", "OnOff", "1");
                
                TimeUnit.MILLISECONDS.sleep(100);
                core_.setProperty("ArduinoSM-Shutter", "OnOff", "0");
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println("i ist "+i);
            }
            
            } catch (Exception ex) {
                Logger.getLogger(ArduinoStepperMotor.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
}
