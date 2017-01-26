/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.PlateProperties;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Variable;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author dk1109
 */
public class FOV implements Comparable<FOV> {

    double x_;
    double y_;
    double z_;
    double width_ = 217; // in um ffor 40x, ORCA
    double height_ = 165;   //40x obj
    
    String well_;
    String group_ = "Experiment";
    PlateProperties pp_;
    private Variable var_;
    // initialise with nonsense - remove entirely?
//    FOV(){
//        x_ = 0;
//        y_ = 0;
//        z_ = 0;
//        well_ = "Z99";
//    }
    /**
     * Initialise FOV with all fields
     *
     * @param x
     * @param y
     * @param z
     * @param well
     * @param pp
     */
    public FOV(double x, double y, double z, String well, PlateProperties pp) {
        x_ = x;
        y_ = y;
        z_ = z;
        well_ = well;
        pp_ = pp;
        var_ = Variable.getInstance();
    }

    /**
     * Initialise FOV with xyz pos, assign well given plate properties
     *
     * @param x
     * @param y
     * @param z
     * @param pp
     */
    public FOV(double x, double y, double z, PlateProperties pp) {
        x_ = x;
        y_ = y;
        z_ = z;
        pp_ = pp;
        int col = (int) Math.round((x
                - pp.getTopLeftWellOffsetH()) / pp.getWellSpacingH()) + 1;
        int row = (int) Math.round((y
                - pp.getTopLeftWellOffsetV()) / pp.getWellSpacingV()) + 1;
        well_ = Character.toString((char) (row + 64)) + col;
        var_ = Variable.getInstance();

    }

    /**
     * Initialise a FOV in the centre of a given well, given plate properties.
     *
     * @param well
     * @param pp
     * @param z
     */
    public FOV(String well, PlateProperties pp, double z) {
        // split well text into numeric data, dealing with possibility of multiple
        // letters in well label - break out to general classes? 
        // Make well an object?

        String wellLetter;
        int wellNumber;
        int letterIndex = 0;

        int i = 0;
        while (!Character.isDigit(well.charAt(i))) {
            i++;
        }

        wellLetter = well.substring(0, i);
        wellNumber = Integer.parseInt(well.substring(i, well.length()));
        for (int k = 0; k < i; k++) {
            letterIndex += (int) well.charAt(k) - 64;
        }

        well_ = well;
        x_ = pp.getTopLeftWellOffsetH()
                + (wellNumber - 1) * pp.getWellSpacingH();
        y_ = pp.getTopLeftWellOffsetV()
                + (letterIndex - 1) * pp.getWellSpacingV();
        z_ = z;
        pp_ = pp;
        var_ = Variable.getInstance();

    }

    public FOV getFOV() {
        return this;
    }

    public double getX() {
        return x_;
    }

    public double getY() {
        return y_;
    }

    public double getZ() {
        //z_=(double) round(z_,2);
        return z_;
    }

    public String getWell() {
        return well_;
    }
    
    public String getGroup() {
        return group_;
    }

    public void setX(double x) {
        x_ = x;
    }

    public void setY(double y) {
        y_ = y;
    }

    public void setZ(double z) {
        z_ = z;
    }

    public void setWell(String well) {
        well_ = well;
    }

    public void setPlateProps(PlateProperties pp) {
        pp_ = pp;
    }

    public PlateProperties getPlateProps() {
        return pp_;
    }

    public double getWidth_() {
        double newWidth=(((width_*40)/var_.magnification)*var_.relay); //Changed to x var_relay, not divide...
        return newWidth;
    }

    public double getHeight_() {
        double newheight=(((height_*40)/var_.magnification)*var_.relay); //Changed to x var_relay, not divide...
        return newheight;
    }

    public void setWidth_(double width_) {
        this.width_ = width_;
    }

    public void setHeight_(double height_) {
        this.height_ = height_;
    }
       
       
    public void setGroup(String group_) {
        this.group_ = group_;
    }

    public boolean isValid() {

        int wellNumber;
        int letterIndex = 0;

        int i = 0;
        while (!Character.isDigit(well_.charAt(i))) {
            i++;
        }

//        wellLetter = fov.well_.substring(0, i);
        wellNumber = Integer.parseInt(well_.substring(i, well_.length()));
        for (int k = 0; k < i; k++) {
            letterIndex += (int) well_.charAt(k) - 64;
        }

        // Replace rectangle well bounds with circle. Make more general class?
        class Circle {

            //circle defined by centre x,y and radius r. 

            public double x_, y_, r_;

            public Circle(double x, double y, double r) {
                x_ = x;
                y_ = y;
                r_ = r;
            }

            public boolean contains(Rectangle fov) {
                Point[] points = {new Point(fov.x, fov.y), new Point(fov.x, fov.y + fov.height),
                    new Point(fov.x + fov.width, fov.y), new Point(fov.x + fov.width, fov.y + fov.height)};

                for (Point p : points) {
                    if (Math.pow((x_ - p.x), 2) + Math.pow((y_ - p.y), 2)
                            > Math.pow(r_, 2)) {
                        return false;
                    }
                }
                return true;
            }
        }

        Rectangle fov = new Rectangle(
                (int) (this.x_ - this.width_ / 2), (int) (this.y_ - this.height_ / 2),
                (int) this.width_, (int) this.height_);
        if ("Circle".equals(pp_.getWellShape())) {
            Circle well = new Circle(pp_.getTopLeftWellOffsetH() + (wellNumber - 1) * pp_.getWellSpacingH(),
                    pp_.getTopLeftWellOffsetV() + (letterIndex - 1) * pp_.getWellSpacingV(),
                    pp_.getWellSize() / 2);
            return well.contains(fov);
        } else {
            Rectangle well = new Rectangle((int) (pp_.getTopLeftWellOffsetH() + (wellNumber - 1) * pp_.getWellSpacingH()),
                    (int) (pp_.getTopLeftWellOffsetV() + (letterIndex - 1) * pp_.getWellSpacingV()),
                    (int) pp_.getWellSize(), (int) pp_.getWellSize());
            return well.contains(fov);
        }

    }
    
   

    // Override compareTo so that FOV.sort orders by well value
    @Override
    public int compareTo(FOV fov) {
        final int GREATER = 1;
        final int LESS = -1;
        final int EQUAL = 0;

        if (this == fov) {
            return EQUAL;
        }

        String[] well = new String[2];
        String[] wellLetter = new String[2];
        int[] wellNumber = new int[2];
        int[] letterIndex = new int[2];
        well[0] = this.getWell();
        well[1] = fov.getWell();

        for (int ind = 0; ind < 2; ind++) {

            int i = 0;
            while (!Character.isDigit(well[ind].charAt(i))) {
                i++;
            }

            wellLetter[ind] = well[ind].substring(0, i);
            wellNumber[ind] = Integer.parseInt(well[ind].substring(i, well[ind].length()));
            for (int k = 0; k < i; k++) {
                letterIndex[ind] += (int) well[ind].charAt(k) - 64;
            }
        }

        if  (letterIndex[0] > letterIndex[1])
            return GREATER;
        if (letterIndex[0] < letterIndex[1])
            return LESS;
        if (letterIndex[0] == letterIndex[1]){
            if (wellNumber[0] > wellNumber[1])
                return GREATER;
            if (wellNumber[0] < wellNumber[1])
                    return LESS;
        }

        return EQUAL;
    }
    
    // Override equals so that FOV.contains checks LOGICAL equality, not 
    // reference equality; and ONLY for xy equality
    // http://users.csc.calpoly.edu/~kmammen/documents/java/howToOverrideEquals.html
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        
        if (this.getClass() != other.getClass())
            return false;
       
        double thisX = this.getX();
        double thisY = this.getY();
        double otherX = ((FOV) other).getX();
        double otherY = ((FOV) other).getY();
        
        if (thisX == otherX & thisY == otherY){
            return true;
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x_) ^ (Double.doubleToLongBits(this.x_) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y_) ^ (Double.doubleToLongBits(this.y_) >>> 32));
        return hash;
    }
    
    @Override
    public String toString(){
        return "FOV: Well = " + this.well_ + ", x = " + this.x_ 
                + ", y = " + this.y_ + ", z = " + this.z_
                + ", im_group = " + this.group_;
    }

}
