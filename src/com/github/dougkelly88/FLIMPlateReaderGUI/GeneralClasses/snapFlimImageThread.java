/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;

/**
 *
 * @author Frederik
 */
public class snapFlimImageThread implements Runnable {
    private HCAFLIMPluginFrame frame;
    
    public snapFlimImageThread(HCAFLIMPluginFrame frame_){
        // initialize new instance of HCAFLIMPluginFrame
        frame = frame_;
    }

    @Override
    public void run() {
        System.out.println("Start snapFlimImageThread");
        frame.snapFLIMImageButton();
        }
    
}
