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
    /*    String imageFilePath = "C:\\Users\\Frederik\\Desktop\\08-03-2015(first time working)\\FLIMfitAnalyses\\GFP";
        Opener opener = new Opener();  
        ImagePlus imp = opener.openImage(imageFilePath);  

        imp.show();*/
        Opener opener = new Opener();  
        ImagePlus imp = opener.openImage("http://www2.unine.ch/files/content/users/merciers2/files/6-%20Lola_Bonobos_IMG_0466%20(Zanna%20Clay).JPG");  
        imp.show();
        
        ImagePlus imp1 = opener.openImage("C:\\Users\\Frederik\\Desktop\\ape.JPG");
        imp1.show();
       
        ImagePlus imp2 = opener.openImage("C:\\Users\\Frederik\\Desktop\\GFP.tiff");
        imp2.show();
    }
}
