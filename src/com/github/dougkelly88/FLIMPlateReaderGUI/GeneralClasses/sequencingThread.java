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
public class sequencingThread implements Runnable {
    
    private HCAFLIMPluginFrame frame;
    
    public sequencingThread(HCAFLIMPluginFrame frame_)
    {
        frame = frame_;
    }
    
    @Override
    public void run() {
        
        try {
            frame.doSequenceAcquisition();
            // sequencingThread sTh= new sequencingThread();
            //  sequencingThread.frame_.doSequenceAcquisition();
        } catch (InterruptedException ex) {
            System.out.println("sequncingThread no acces to mainFrame!");
        }
        
    }
    
    
}
