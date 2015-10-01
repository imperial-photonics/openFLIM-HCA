/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.Classes;

/**
 *
 * @author Frederik
 */

public class TimePoint implements Comparable<TimePoint> {
    private Double time_ = 10.0;
    private Double ld_ = 100.0;
    private boolean sa_ = false;
    
    public TimePoint(Double time, Double ld, boolean sa){
        time_ = time;
        ld_ = ld;
        sa_ = sa;
   
    }
    
    public TimePoint (Double time){
        time_ = time;
        ld_ = 0.0;
        sa_ = false;
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

    public Double getLDVolume() {
        return ld_;
    }

    public void setLDVolume(Double LDState) {
        this.ld_ = LDState;
    }

    public boolean getSAState() {
        return sa_;
    }

    public void setSAState(boolean SAState) {
        this.sa_ = SAState;
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
                + ", Liquid dispensing volume = " + this.ld_; 
    }
    
}
