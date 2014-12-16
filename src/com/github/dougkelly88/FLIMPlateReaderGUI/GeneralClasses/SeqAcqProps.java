/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses;

import java.util.ArrayList;
import java.util.Collections;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

/**
 *
 * @author dk1109
 */
// http://www.javapractices.com/topic/TopicAction.do?Id=46
// alternative method? http://stackoverflow.com/questions/15406717/can-i-use-the-builder-pattern-on-a-java-enum
// also useful: http://www.algosome.com/articles/builder-setter-methods-java.html
public final class SeqAcqProps {

    private boolean useScanFLIM;
    private boolean powerMonitoring;
    private ArrayList<ArrayList<Integer>> delaysArray;
    private double[] FLIMFOVSize = {600, 460};    // 20x obj, .7 relay
    private MMStudio gui_;
    private CMMCore core_;

    public static SeqAcqProps getInstance() {
        return fINSTANCE;
    }

  // PRIVATE
    /**
     * Single instance created upon class loading.
     */
    private static final SeqAcqProps fINSTANCE = new SeqAcqProps();

    /**
     * Private constructor prevents construction outside this class.
     */
    private SeqAcqProps() {
        try{
        gui_ = MMStudio.getInstance();
        core_ = gui_.getCore();
        } catch (Exception e){
            System.out.println("Error = " + e.getMessage());
        }
        useScanFLIM = false;
        powerMonitoring = false;
        delaysArray = initDelaysArray();
        

    }

    private ArrayList<ArrayList<Integer>> initDelaysArray() {
        ArrayList<Integer> delays = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> delArr;
        delays.add(0);
        delays.add(1000);
        delays.add(2000);
        delays.add(3000);
        delays.add(4000);
        delays.add(5000);
        delArr = new ArrayList<ArrayList<Integer>>();
        delArr.add(delays);
        return delArr;
    }

    // Setters
    public SeqAcqProps setUseScanFLIM(boolean usf) {
        useScanFLIM = usf;
        return this;
    }

    public SeqAcqProps setPowerMonitoring(boolean pm) {
        powerMonitoring = pm;
        return this;
    }

    public SeqAcqProps setDelaysArray(int ind, ArrayList<Integer> delays) {

        delays.removeAll(Collections.singleton(null));    // ensure that there are no null entries
        if (delaysArray.size() > ind) // replace existing delays
        {
            delaysArray.set(ind, delays);
        } else {
            // add empty arrays in indices up to chosen index, then add new delays
            for (int i = delaysArray.size(); i < ind; i++) {
                delaysArray.add(new ArrayList<Integer>());
            }
            delaysArray.add(delays);
            // DEBUG - check size
            int s = delaysArray.size();
        }

        return this;
    }

    public SeqAcqProps clearDelaysArray() {
        delaysArray.clear();
        return this;
    }

    public SeqAcqProps setFLIMFOVSize(double magnification) {
        // TODO: add relay magnification as a property in Kentech HRI devices?
        try {
            magnification = 27.0;
//      FLIMFOVSize[0] = x;
//      FLIMFOVSize[1] = y;
//            core_.getImageHeight();
//            core_.getImageWidth();
//            core_.getPixelSizeUm();
        } catch (Exception e){
            System.out.print("Error getting FOV size from equipment: "
                    + e.getMessage());
        }

        return this;
    }

    // Getters
    public SeqAcqProps getSeqAcqProps() {
        return this;
    }

    public boolean getUseScanFLIM() {
        return useScanFLIM;
    }

    public boolean getPowerMonitoring() {
        return powerMonitoring;
    }

    public ArrayList<ArrayList<Integer>> getDelaysArray() {
        delaysArray.removeAll(Collections.singleton(null));    // ensure that there are no null entries
        return delaysArray;
    }

    public double[] getFLIMFOVSize() {
        return FLIMFOVSize;
    }
}
