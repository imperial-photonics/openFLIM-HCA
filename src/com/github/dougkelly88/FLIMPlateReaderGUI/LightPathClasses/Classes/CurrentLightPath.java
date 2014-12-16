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

    public CurrentLightPath(){
        exFilterLabel = "465/30";
        emFilterLabel = "525/35";
        ndFilterLabel = "0";
        dichroicLabel = "473-561";
        filterCubeLabel = "Empty (Sectioned)";
        objectiveLabel = "LUCPlanFLN 40x 0.6";
        portLabel = "Camera";
    }
    
    /**
     *
     * @param exfilt Excitation filter
     * @param emfilt Emission filter
     * @param ndfilt ND filter
     * @param dichroic  (CSUX) Dichroic filter
     */
    public CurrentLightPath(String exfilt, String emfilt, String ndfilt, String dichroic){
        exFilterLabel = exfilt;
        emFilterLabel = emfilt;
        ndFilterLabel = ndfilt;
        dichroicLabel = dichroic;
        filterCubeLabel = "Empty (Sectioned)";
        objectiveLabel = "LUCPlanFLN 40x 0.6";
        portLabel = "Camera";
        filterCubeLabel = "None (sectioned)";
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
