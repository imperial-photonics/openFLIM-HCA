/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import ij.ImagePlus;
import ij.io.Opener;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

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
        Opener opener = new Opener();  
        String imageFilePath = "somePath";
        ImagePlus imp = opener.openImage(imageFilePath);
    }
}
