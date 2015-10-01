/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import GeneralGUIComponents.HCAFLIMPluginFrame;

/**
 *
 * @author Frederik
 */
public class sequencingThread implements Runnable {
    private HCAFLIMPluginFrame frame;
    
    public sequencingThread(HCAFLIMPluginFrame frame_){
        // initialize new instance of HCAFLIMPluginFrame
        frame = frame_;
    }
    
    @Override
    public void run() {
        
        try {
            // calls function in frame, which is an instance of the class HCAFLIMPluginFrame
            frame.doSequenceAcquisition();
            
        } catch (InterruptedException ex) {
            System.out.println("Error: sequncingThread no acces to class HCAFLIMPluginFrame.");
        }
        
    }
    
    
}
