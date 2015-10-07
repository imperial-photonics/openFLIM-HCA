/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
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
    ImagePlus imp2 = null;
    
    public void showImageInIJ(String path){ 
        path=path+".ome.tiff";
        Opener opener = new Opener();
     /*   ImagePlus imp1 = opener.openImage("C:\\Users\\Frederik\\Desktop\\ape.JPG");
        imp1.show();
        imp1.hide();*/
        imp2 = opener.openImage(path);
        IJ.run(imp2, "Enhance Contrast", "saturated=1 normalize");
        WindowManager.setTempCurrentImage(imp2);
        IJ.run("Fire");
        imp2.show();
    }
    
    public void showImageInIJ1(){
        Opener opener = new Opener();
     /*   ImagePlus imp1 = opener.openImage("C:\\Users\\Frederik\\Desktop\\ape.JPG");
        imp1.show();
        imp1.hide();*/
        ImagePlus imp1 = opener.openImage("C:\\Users\\Frederik\\Desktop\\GFP.tiff");
        IJ.run(imp1, "Enhance Contrast", "saturated=1 normalize");
        WindowManager.setTempCurrentImage(imp1);
        IJ.run("Green");
        imp1.show();
        
        imp2 = opener.openImage("C:\\Users\\Frederik\\Desktop\\GFP.tiff");
        IJ.run(imp2, "Enhance Contrast", "saturated=1 normalize");
        WindowManager.setTempCurrentImage(imp2);
        IJ.run("Fire");
        imp2.show();
        
        ImagePlus imp3 = opener.openImage("C:\\Users\\Frederik\\Desktop\\GFP.tiff");
        IJ.run(imp3, "Enhance Contrast", "saturated=1 normalize");
        WindowManager.setTempCurrentImage(imp3);
        IJ.run("Green");
        imp3.show();
        
        ImagePlus imp4 = opener.openImage("C:\\Users\\Frederik\\Desktop\\GFP.tiff");
        IJ.run(imp4, "Enhance Contrast", "saturated=1 normalize");
        WindowManager.setTempCurrentImage(imp4);
        IJ.run("Red");
        imp4.show();
    }
    
    public void hideImageInIJ(){
        imp2.hide();
    }
}
