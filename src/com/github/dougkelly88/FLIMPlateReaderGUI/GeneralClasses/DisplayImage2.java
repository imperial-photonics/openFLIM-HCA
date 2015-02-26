/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import ij.IJ;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import mmcorej.TaggedImage;
import org.micromanager.MMStudio;
import org.micromanager.utils.MMScriptException;

/**
 *
 * @author Frederik
 */
public class DisplayImage2 {

    public static DisplayImage2 getInstance() {
        return fINSTANCE;
    }
    MMStudio gui_;
    CMMCore core_;
    private static final DisplayImage2 fINSTANCE =  new DisplayImage2();
    
    public void showImageInIJ(){
        try {
            gui_ = MMStudio.getInstance();
            core_ = gui_.getCore();
            core_.snapImage();
            TaggedImage taggedImage = core_.getTaggedImage();
            gui_.displayImage(taggedImage);
 
        } catch (Exception ex) {
            Logger.getLogger(DisplayImage2.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
