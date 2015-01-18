/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import java.awt.BorderLayout; 
import java.awt.Container; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.image.BufferedImage;

import javax.swing.JFrame; 
import javax.swing.JPanel; 

import marvin.gui.MarvinImagePanel; 
import marvin.image.MarvinImage; 
import marvin.io.MarvinImageIO;
/**
 *
 * @author Frederik
 */
public class DisplayImage extends JFrame implements ActionListener
{ 
   
    private JPanel		panelBottom; 
    private MarvinImagePanel	imagePanel; 
    private MarvinImage		image,
				backupImage;
     
    public DisplayImage() 
    { 
        super("Current Image");
         
    
        
        // ImagePanel 
        imagePanel = new MarvinImagePanel();
         
        Container l_c = getContentPane(); 
        l_c.setLayout(new BorderLayout()); 
        l_c.add(imagePanel, BorderLayout.NORTH);
        
        // Load image
		
	image = MarvinImageIO.loadImage("C:\\Users\\Frederik\\Desktop\\tattoo\\Kojote2010_cut.JPG");
        backupImage = image.clone();
        double imHeight=backupImage.getHeight();
        double imWidth=backupImage.getWidth();
        int newWidth=600;
        double newheight=imHeight/imWidth*newWidth;
        int newHeight=(int) newheight;
        BufferedImage image2 = image.getBufferedImage(newWidth,newHeight);
        MarvinImage imageIcon = new MarvinImage(image2);
        imagePanel.setImage(imageIcon);
        setSize(newWidth+17,newHeight+40);
        setVisible(true);     
    } 
     
    public static void main(String args[]){
        DisplayImage t = new DisplayImage(); 
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    } 
    public void display(){
        DisplayImage t = new DisplayImage(); 
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    }
     

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
