/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.Classes;

/**
 *
 * @author dk1109
 */
public class CurrentLightPath {
    String exFilterLabel;
    String emFilterLabel;
    String ndFilterLabel;
    String dichroicLabel;
    String objectiveLabel;
    String portLabel;
    String filterCubeLabel;
    double [] objectiveOffsets;
    double [] portOffsets;

    public CurrentLightPath(){
        exFilterLabel = "465/30";
        emFilterLabel = "525/35";
        ndFilterLabel = "0";
        dichroicLabel = "473-561";
        filterCubeLabel = "Empty (Sectioned)";
        objectiveLabel = "LUCPLFLN40X";
        portLabel = "Camera";
        objectiveOffsets = new double [] {0.0, 0.0, 0.0}; // X, Y, Z
        portOffsets = new double [] {0.0, 0.0, 0.0}; // X, Y, Z
    }
    
    /**
     *
     * @param exfilt Excitation filter
     * @param emfilt Emission filter
     * @param ndfilt ND filter
     * @param dichroic  (CSUX) Dichroic filter
     * @param objectiveOffsets Objective offsets in XYZ
     */
    public CurrentLightPath(String exfilt, String emfilt, String ndfilt, String dichroic, double[] objOffsets, double[] portOffsetvalues){
        exFilterLabel = exfilt;
        emFilterLabel = emfilt;
        ndFilterLabel = ndfilt;
        dichroicLabel = dichroic;
        filterCubeLabel = "Empty (Sectioned)";
        objectiveLabel = "LUCPLFLN40X";
        portLabel = "Camera";
        filterCubeLabel = "None (sectioned)";
        // added this to the constructor, but couldn't find it being used in the project - jsut the empty one...
        objectiveOffsets = objOffsets; // X, Y, Z
        portOffsets = portOffsetvalues; // X, Y, Z
    }
    

    public double[] getObjectiveOffsets(){
        return objectiveOffsets;
    }
    
    public void setObjectiveOffsets(double[] newOffsets){
        objectiveOffsets = newOffsets;
    }

    public double[] getPortOffsets(){
        return portOffsets;
    }
    
    public void setPortOffsets(double[] newOffsets){
        portOffsets = newOffsets;
    }
    
    public String getExFilterLabel() {
        return exFilterLabel;
    }

    public void setExFilterLabel(String exFilterLabel) {
        this.exFilterLabel = exFilterLabel;
    }

    public String getEmFilterLabel() {
        return emFilterLabel;
    }

    public void setEmFilterLabel(String emFilterLabel) {
        this.emFilterLabel = emFilterLabel;
    }

    public String getNdFilterLabel() {
        return ndFilterLabel;
    }

    public void setNdFilterLabel(String ndFilterLabel) {
        this.ndFilterLabel = ndFilterLabel;
    }

    public String getDichroicLabel() {
        return dichroicLabel;
    }

    public void setDichroicLabel(String dichroicLabel) {
        this.dichroicLabel = dichroicLabel;
    }

    public String getObjectiveLabel() {
        return objectiveLabel;
    }

    public void setObjectiveLabel(String objectiveLabel) {
        this.objectiveLabel = objectiveLabel;
    }

    public String getPortLabel() {
        return portLabel;
    }

    public void setPortLabel(String portLabel) {
        this.portLabel = portLabel;
    }

    public String getFilterCubeLabel() {
        return filterCubeLabel;
    }

    public void setFilterCubeLabel(String filterCubeLabel) {
        this.filterCubeLabel = filterCubeLabel;
    }
    
}
