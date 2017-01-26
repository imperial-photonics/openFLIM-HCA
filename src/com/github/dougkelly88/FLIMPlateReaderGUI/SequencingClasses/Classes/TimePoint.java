/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import java.util.ArrayList;

/**
 *
 * @author Frederik
 */

public class TimePoint implements Comparable<TimePoint> {
    private Double time_ = 10.0;
    private boolean ld_ = false;
    //private boolean sa_ = false;
    private ArrayList<String> ldWells_;
    
    public TimePoint(Double time, boolean ld, ArrayList<String> ldWells){
        time_ = time;
        ld_ = ld;
        ldWells_ = ldWells;
   
    }
    
    public TimePoint (Double time){
        time_ = time;
        ld_ = false;
        ldWells_ = new ArrayList<String>();
    }
    
    public TimePoint getTimePoint(){
        return this;
    }
    
    public Double getTimeCell() {
        return time_;   
    }

    public void setTimeCell(Double time) {
        this.time_ = time;
    }

    public boolean getLDState() {
        return ld_;
    }

    public void setLDState(boolean LDState) {
        this.ld_ = LDState;
    }

    public ArrayList<String> getLdWells() {
        return ldWells_;
    }

    public void setLdWells(ArrayList<String> LDWells) {
        this.ldWells_ = LDWells;
    }

    // Override compareTo so that TimePoint.sort orders by time value
    @Override
    public int compareTo(TimePoint tp) {
        final int GREATER = 1;
        final int LESS = -1;
        final int EQUAL = 0;
        
        if (this == tp) return EQUAL;
        
        if (this.getTimeCell() > tp.getTimeCell()) return GREATER;
        if (this.getTimeCell() < tp.getTimeCell()) return LESS;
        
        return EQUAL;
    }

    // Override equals so that TimePoint.contains checks LOGICAL equality, not 
    // reference equality. 
    // http://users.csc.calpoly.edu/~kmammen/documents/java/howToOverrideEquals.html
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        
        if (this.getClass() != other.getClass())
            return false;
        
        // base equality only on time value, not caring about other variables
        double thisTime = (double) this.getTimeCell();
        double otherTime = (double) ((TimePoint) other).getTimeCell();
        if (thisTime != otherTime)
            return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.time_ != null ? this.time_.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString(){
        return "Time point: Time (s) = " + this.time_ 
                + ", Liquid dispensing?= " + this.ld_
                + ", Liquid dispension Well(s)= " + this.ldWells_; 
    }
    
}
