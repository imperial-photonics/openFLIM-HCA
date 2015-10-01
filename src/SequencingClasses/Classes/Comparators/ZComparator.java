/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.Classes.Comparators;

import SequencingClasses.Classes.SeqAcqSetup;
import java.util.Comparator;

/**
 *
 * @author dk1109
 */
public class ZComparator implements Comparator<SeqAcqSetup>{

    @Override
    public int compare(SeqAcqSetup o1, SeqAcqSetup o2) {
        Double z1 = o1.getFOV().getZ();
        Double z2 = o2.getFOV().getZ();
        return z1.compareTo(z2);
    }
    
}
