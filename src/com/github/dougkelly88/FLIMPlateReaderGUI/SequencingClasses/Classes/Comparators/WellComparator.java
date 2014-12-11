/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators;

import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SeqAcqSetup;
import java.util.Comparator;

/**
 *
 * @author dk1109
 */
public class WellComparator implements Comparator<SeqAcqSetup>{

    @Override
    public int compare(SeqAcqSetup o1, SeqAcqSetup o2) {
        String w1 = o1.getFOV().getWell();
        String w2 = o2.getFOV().getWell();
        return w1.compareTo(w2);
    }
    
}
