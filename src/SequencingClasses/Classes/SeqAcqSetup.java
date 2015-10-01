/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.Classes;

/**
 *
 * @author dk1109
 */
//public class SeqAcqSetup implements Comparable<SeqAcqSetup> {
public class SeqAcqSetup {

    FOV fov_;
    TimePoint tp_;
    FilterSetup fs_;

    public SeqAcqSetup(FOV fov, TimePoint tp, FilterSetup fs) {
        fov_ = fov;
        tp_ = tp;
        fs_ = fs;
    }

    public SeqAcqSetup getSeqAcqSetup() {
        return this;
    }

    public FOV getFOV() {
        return fov_;
    }

    public TimePoint getTimePoint() {
        return tp_;
    }

    public FilterSetup getFilters() {
        return fs_;
    }

    public void setFOV(FOV fov) {
        this.fov_ = fov;
    }

    public void setTimePoint(TimePoint tp) {
        this.tp_ = tp;
    }

    public void setFilterSetup(FilterSetup fs) {
        this.fs_ = fs;
    }
    
    @Override
    public String toString(){
        return "Acquisition point: " + this.fov_.toString() 
                + "; " + this.fs_.toString()
                + "; " + this.tp_.toString();
    }
//
//    // Override compareTo so that FOV.sort orders by well value
//    @Override
//    public int compareTo(SeqAcqSetup fov) {
//        final int GREATER = 1;
//        final int LESS = -1;
//        final int EQUAL = 0;
//
//        if (this == fov) {
//            return EQUAL;
//        }
//
//        String[] well = new String[2];
//        String[] wellLetter = new String[2];
//        int[] wellNumber = new int[2];
//        int[] letterIndex = new int[2];
//        well[0] = this.getWell();
//        well[1] = fov.getWell();
//
//        for (int ind = 0; ind < 2; ind++) {
//
//            int i = 0;
//            while (!Character.isDigit(well[ind].charAt(i))) {
//                i++;
//            }
//
//            wellLetter[ind] = well[ind].substring(0, i);
//            wellNumber[ind] = Integer.parseInt(well[ind].substring(i, well[ind].length()));
//            for (int k = 0; k < i; k++) {
//                letterIndex[ind] += (int) well[ind].charAt(k) - 64;
//            }
//        }
//
//        if  (letterIndex[0] > letterIndex[1])
//            return GREATER;
//        if (letterIndex[0] < letterIndex[1])
//            return LESS;
//        if (letterIndex[0] == letterIndex[1]){
//            if (wellNumber[0] > wellNumber[1])
//                return GREATER;
//            if (wellNumber[0] < wellNumber[1])
//                    return LESS;
//        }
//
//        return EQUAL;
//    }
//    
//    // Override equals so that FOV.contains checks LOGICAL equality, not 
//    // reference equality; and ONLY for xy equality
//    // http://users.csc.calpoly.edu/~kmammen/documents/java/howToOverrideEquals.html
//    @Override
//    public boolean equals(Object other){
//        if (other == null)
//            return false;
//        
//        if (this.getClass() != other.getClass())
//            return false;
//       
//        double thisX = this.getX();
//        double thisY = this.getY();
//        double otherX = ((SeqAcqSetup) other).getX();
//        double otherY = ((SeqAcqSetup) other).getY();
//        
//        if (thisX == otherX & thisY == otherY)
//            return true;
//        
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x_) ^ (Double.doubleToLongBits(this.x_) >>> 32));
//        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y_) ^ (Double.doubleToLongBits(this.y_) >>> 32));
//        return hash;
//    }

}
