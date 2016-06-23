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
public class Insert_object {
    String name;
    Double X_offset;
    Double Y_offset;
    Double Z_offset;
    
    public Insert_object(String Insert_name, double Insert_Xoff, double Insert_Yoff, double Insert_Zoff){
        name = Insert_name;
        X_offset = Insert_Xoff;
        Y_offset = Insert_Yoff;
        Z_offset = Insert_Zoff;
    } 
    
    public Insert_object(){
        name = "UNKNOWN";
        X_offset = 0.0;
        Y_offset = 0.0;
        Z_offset = 0.0;
    } 
    
    public Double[] getInsertOffsets()
    {
        Double offsets[] = {X_offset, Y_offset, Z_offset};
        return offsets;
    }
    
    public String getInsertName(){
        return name;
    }
    
    public Insert_object getInsertInfo()
    {
        return this;
    }
}
