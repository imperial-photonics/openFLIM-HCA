/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLIMClasses.GUIComponents;

/**
 *
 * @author Frederik
 */
public class findMaxPointThread implements Runnable {
    private FLIMPanel fLIMPanel;
    
    public findMaxPointThread(FLIMPanel fLIMPanel_){
        fLIMPanel = fLIMPanel_;
    }
    
    
    @Override
    public void run() {
        fLIMPanel.findMaxPoint();
        
    }
    
}
