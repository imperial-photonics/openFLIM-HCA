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
public class TComparator implements Comparator<SeqAcqSetup>{

    @Override
    public int compare(SeqAcqSetup o1, SeqAcqSetup o2) {
        Double t1 = o1.getTimePoint().getTimeCell();
        Double t2 = o2.getTimePoint().getTimeCell();
        return t1.compareTo(t2);
    }
    
}
