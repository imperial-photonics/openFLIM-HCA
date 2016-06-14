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
public class Objective_object {
    String name;
    Double X_offset;
    Double Y_offset;
    Double Z_offset;
    
    public Objective_object(String Obj_name, double Obj_Xoff, double Obj_Yoff, double Obj_Zoff){
        name = Obj_name;
        X_offset = Obj_Xoff;
        Y_offset = Obj_Yoff;
        Z_offset = Obj_Zoff;
    } 
    
    public Objective_object(){
        name = "UNKNOWN";
        X_offset = 0.0;
        Y_offset = 0.0;
        Z_offset = 0.0;
    } 
    
    public Double[] getObjectiveOffsets()
    {
        Double offsets[] = {X_offset, Y_offset, Z_offset};
        return offsets;
    }
    
    public String getObjectiveName(){
        return name;
    }
    
    public Objective_object getObjectiveInfo()
    {
        return this;
    }
}
