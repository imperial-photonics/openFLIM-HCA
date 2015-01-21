/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JFrame; 
import javax.swing.JLabel;
import javax.swing.JPanel; 

/**
 *
 * @author Frederik
 */
public class DisplayImage extends JFrame implements ActionListener
{ 
   
    private JPanel		panelBottom;  
    private static final DisplayImage fINSTANCE =  new DisplayImage();
 
    public DisplayImage() 
    { 
        super("Current Image");  
    } 
     
    public static void main(String args[]){
        DisplayImage t = new DisplayImage(); 
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    } 
    
    public static DisplayImage getInstance() {
      
      return fINSTANCE;
  }

    public void display(byte[] imDemo){ 
        BufferedImage img=null;
        ImageIcon imgg=new ImageIcon(imDemo);
        try {
            img = ImageIO.read(new ByteArrayInputStream(imDemo));
        } catch (IOException ex) {
            try {
                img = ImageIO.read(new File("C:\\Users\\Frederik\\Desktop\\tattoo\\Kojote2010_cut.JPG"));
            } catch (IOException ex1) {
                System.out.print("nothing");
            }
        }


        JFrame f = new JFrame("Current image"); //creates jframe f
 //       f.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        f.getContentPane().add(emptyLabel, BorderLayout.CENTER);
 

        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //this is your screen size
        f.setUndecorated(true); //removes the surrounding border
        JLabel lbl = new JLabel(imgg); //puts the image into a jlabel
        f.getContentPane().add(lbl); //puts label inside the jframe
        f.setSize(200, 200);
//        f.setSize(image.getIconWidth(), image.getIconHeight()); //gets h and w of image and sets jframe to the size
        int x = (screenSize.width - f.getSize().width)/4; //These two lines are the dimensions
        int y = (screenSize.height - f.getSize().height)/4;//of the center of the screen
        f.setLocation(x, y); //sets the location of the jframe
     f.pack();
     f.setVisible(true); //makes the jframe visible
                //Display the window.
       
     //   f.setVisible(true);
    }
     

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
