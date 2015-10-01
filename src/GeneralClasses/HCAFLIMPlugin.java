/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GeneralClasses;

import GeneralGUIComponents.HCAFLIMPluginFrame;
import javax.swing.JFrame;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;
import org.micromanager.acquisition.AcquisitionEngine;
import org.micromanager.acquisition.AcquisitionWrapperEngine;
import org.micromanager.api.MMPlugin;
import org.micromanager.api.ScriptInterface;

/**
 *
 * @author dk1109
 */
public class HCAFLIMPlugin implements MMPlugin {
    
    public static final String menuName = "OpenFLIM-HCA Plugin";
    public static final String tooltipDescription = "Suite of controls for use with OpenFLIM-HCA instruments";
    
    public static JFrame frame_;
    static ScriptInterface si_;
    private CMMCore core_;
    private MMStudio gui_;
    private AcquisitionEngine acq_;
    

    public static AcquisitionWrapperEngine getAcquisitionWrapperEngine() 
    {
        AcquisitionWrapperEngine engineWrapper = (AcquisitionWrapperEngine) MMStudio.getInstance().getAcquisitionEngine();
        return engineWrapper;
    }
    
  
    @Override
    public void dispose() {
        
    }

    @Override
    public void setApp(ScriptInterface si) {
      gui_ = (MMStudio) si;
      core_ = si.getMMCore();
      acq_ = gui_.getAcquisitionEngine();
      
      frame_ = new HCAFLIMPluginFrame(core_);
      frame_.pack();
      
    }

    @Override
    public void show() {
//        gui_.showMessage("HELLO WORLD!");
                
        if (frame_ == null) {
            frame_ = new HCAFLIMPluginFrame(core_);
            gui_.addMMBackgroundListener(frame_);
//            frame_.setLocation(fa.controlFrame_.FrameXpos, fa.controlFrame_.FrameYpos);
        }
        frame_.setVisible(true);
        
    }

    @Override
    public String getDescription() {
        return "Imperial College London Photonics Group, OpenFLIM-HCA plugin";
    }

    @Override
    public String getInfo() {
        return "Imperial College London Photonics Group, OpenFLIM-HCA plugin";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public String getCopyright() {
        return "CopyNotics";
    }

   
}
