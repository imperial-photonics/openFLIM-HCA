/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

/**
 *
 * @author Sunil
 */
public class Port_object {
    String name;
    Double X_offset;
    Double Y_offset;
    Double Z_offset;
    
    public Port_object(String Port_name, double Port_Xoff, double Port_Yoff, double Port_Zoff){
        name = Port_name;
        X_offset = Port_Xoff;
        Y_offset = Port_Yoff;
        Z_offset = Port_Zoff;
    } 
    
    public Port_object(){
        name = "UNKNOWN";
        X_offset = 0.0;
        Y_offset = 0.0;
        Z_offset = 0.0;
    } 
    
    public Double[] getPortOffsets()
    {
        Double offsets[] = {X_offset, Y_offset, Z_offset};
        return offsets;
    }
    
    public String getPortName(){
        return name;
    }
    
    public Port_object getPortInfo()
    {
        return this;
    }
}
