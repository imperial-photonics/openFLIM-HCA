/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.SaveData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mmcorej.StrVector;

/**
 *
 * @author Frederik
 */
public class VariableTest {
// All variables define here:
    //Test variables:
    public AtomicInteger value = new AtomicInteger(10);
    public volatile String tet1="Got it!";
    public int tet2;
    
    // General variables:
    public String basepath= System.getProperty("user.home");//Default basepath
    public String entireFileText;
    
    // LightPathControl variables:
    public StrVector DichroicComboBox;
    public String DichroicComboBoxSelectedItem;
    public StrVector EmissionComboBox;
    public String EmissionComboBoxSelectedItem;
    public StrVector ExcitationComboBox;
    public String ExcitationComboBoxSelectedItem;
    public StrVector NDFWComboBox;
    public String NDFWComboBoxSelectedItem;
    public StrVector ObjectiveomboBox;
    public String ObjectiveComboBoxSelectedItem;
    public StrVector FilterCubeComboBox;
    public String FilterCubeComboBoxSelectedItem;
    public StrVector SwitchPortComboBox;
    public String SwitchPortComboBoxSelectedItem;
    
    //FLIMPanel:
    public double mcpSlider;
    public double gatewidthSlider;
    public double fastDelaySlider;
    public double slowDelaySlider;
    public boolean scanDelCheck;
    public boolean fastBoxCalibratedCheck;
    public boolean slowBoxCalibratedCheck;
    public ArrayList<Integer> delays;
    
    //ProSetings
    public double th1=0.5;
    public double th2=0.5;
    public double laserIntensity=0;

  // PRIVATE

  /**
  * Single instance created upon class loading.
  */
  private static final VariableTest fINSTANCE =  new VariableTest();
  /**
  * Private constructor prevents construction outside this class.
  */
 
    /**
     * Private constructor prevents construction outside this class.
     * @return
     */
  public String saveMetadata(){
      //Saving the all data in basepath+ConfigSoftware.txt
      // Check if basepath is defined. If yes saves, if not open dialog.
        if (basepath==null){
        JOptionPane.showMessageDialog(null,"Please choose a base path!");
        }
        else{
        PrintWriter writer=null;
        try {
            writer = new PrintWriter(basepath+"\\ConfigSoftware.txt", "UTF-8"); 
    //Data to write. Please write in the form [writer.println("Label: "+PropertyToSave+";");]. This structure is important for the loading process.
        writer.println("Data Document");
        writer.println();
        writer.println("-----------------------------------------------------------------------------");
        writer.println("General");
        writer.println("Base path: "+basepath+";");
        writer.println();
        writer.println("-----------------------------------------------------------------------------");
        writer.println("LightPathControl:");
        writer.println("Dichroic: "+DichroicComboBoxSelectedItem+";");
        writer.println("Emission: "+EmissionComboBoxSelectedItem+";");
        writer.println("Excitation: "+ExcitationComboBoxSelectedItem+";");
        writer.println("Neutral Density Filter: "+NDFWComboBoxSelectedItem+";");
        writer.println("Objective: "+ObjectiveComboBoxSelectedItem+";");
        writer.println("Filter Cube: "+FilterCubeComboBoxSelectedItem+";");
        writer.println("Light Path Prism: "+SwitchPortComboBoxSelectedItem+";");
        writer.println();
        writer.println("-----------------------------------------------------------------------------");
        writer.println("FLIM:");
        writer.println();
        writer.println("Fast Delay Box:");
        writer.println("Calibrate? (fastBox): "+fastBoxCalibratedCheck+";");
        writer.println("Fast Current Delay Setting: "+fastDelaySlider+";");
        writer.println("Enable Fast Delay Sequences?: "+scanDelCheck+";");
        writer.println("Delay Sequence in [ps]: "+delays+";");
        writer.println();
        writer.println("Slow Delay Box:");
        writer.println("Calibrate? (slowBox): "+slowBoxCalibratedCheck+";");
        writer.println("Slow Current Delay Setting: "+slowDelaySlider+";");
        writer.println();
        writer.println("HRI Controls:");
        writer.println("MCP Voltage: "+mcpSlider+";");
        writer.println("Gate Width: "+gatewidthSlider+";");
        writer.println("-----------------------------------------------------------------------------");
        writer.println("ProSettings:");
        writer.println();
        writer.println("Threshold analog input A0: "+th1+";");
        writer.println("Threshold analog input A1: "+th2+";");
        writer.println("Laser intensity: "+laserIntensity+";");
        writer.println();
        writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        }
        String ok="OK!";
        return ok;
  }
  
  
  public static VariableTest getInstance() {
      
      return fINSTANCE;
  }    
  
  public String findLabelOfProperty(String searchedProperty){
            //String entireFileText= new Scanner(new File("C:\\Users\\Frederik\\Desktop\\ConfigSoftware.txt"))
             // .useDelimiter("\\A").next();
        String label=null;
      // Check if basepath is defined. If yes contiue, if not open dialog.        
        if (basepath==null){
        JOptionPane.showMessageDialog(null,"Please choose a base path!");
        }
        else{
        PrintWriter writer=null;
            try {
               
                entireFileText = new Scanner(new File(basepath+"\\ConfigSoftware.txt"))
                .useDelimiter("\\A").next();
        
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int lengthProperty=searchedProperty.length();
            int a=entireFileText.indexOf(searchedProperty);
            String substr=entireFileText.substring(a);
            int b=substr.indexOf(";");
            label=substr.substring(lengthProperty+2, b);
        }
        return label; 
  }
  
  public ArrayList<Integer> findLabelOfPropertyForArrayList(String searchedProperty){

        String label=null;
        ArrayList<Integer> delaysVar= new ArrayList<Integer>();
        int count=0;
      // Check if basepath is defined. If yes contiue, if not open dialog.        
        if (basepath==null){
        JOptionPane.showMessageDialog(null,"Please choose a base path!");
        }
        else{
        PrintWriter writer=null;
            try {
               
                entireFileText = new Scanner(new File(basepath+"\\ConfigSoftware.txt"))
                .useDelimiter("\\A").next();
        
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Searching property in txt file    
            int lengthProperty2=searchedProperty.length();
            int aa=entireFileText.indexOf(searchedProperty);
            String subStrAll=entireFileText.substring(aa);
            String findStr = ",";
            int lastIndex = 0;
        // counting entries in txt file for property
            while(lastIndex != -1){

                lastIndex = subStrAll.indexOf(findStr,lastIndex);

                if( lastIndex != -1){
                  count ++;
                    lastIndex+=findStr.length();
                }
            }
        // Cut out the values
            int a=count;
            int b=subStrAll.indexOf(":");
            int bb=subStrAll.indexOf(";");
            int bbb=0;
            int labelInt=0;
            String subStr1=subStrAll.substring(b+3,bb-1);
        // writes every single value into arrayList
            for(int i=0; i<count; i++)
            {
                bbb=subStr1.indexOf(",");
                label=subStr1.substring(0,bbb);
                labelInt= Integer.parseInt(label);
                delaysVar.add(labelInt);
                subStr1=subStr1.substring(bbb+2);
                               
              
            }
            label=subStr1;
            labelInt=Integer.parseInt(label);
            delaysVar.add(labelInt);
    }
        
        return delaysVar; 
  }
}
