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
public class prefindThread implements Runnable {
    private HCAFLIMPluginFrame frame;
    
    public prefindThread(HCAFLIMPluginFrame frame_){
        // initialize new instance of HCAFLIMPluginFrame
        frame = frame_;
    }

    @Override
    public void run() {
        System.out.println("Start prefindThread");
        // NEED TO ADD STUFF IN THE MAIN FRAME TO GET THIS TO WORK...
        try{
            frame.prefind();
        } catch (Exception e) {
            System.out.println("Prefind threw an error!");
        }
        //frame.snapFLIMImageButton(); REPLACE WITH THE CALL TO PREFIND
    }
    
}