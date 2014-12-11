/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators;

import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SeqAcqSetup;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author dk1109
 * http://www.codejava.net/java-core/collections/sorting-a-list-by-multiple-attributes-example
 * 
 */
public class SeqAcqSetupChainedComparator implements Comparator<SeqAcqSetup> {
    
    private List<Comparator<SeqAcqSetup>> listComparators;
    
    
    public SeqAcqSetupChainedComparator(Comparator<SeqAcqSetup>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }
    
    public SeqAcqSetupChainedComparator(List<Comparator<SeqAcqSetup>> comparatorList){
        this.listComparators = comparatorList;
    }

    @Override
    public int compare(SeqAcqSetup sas1, SeqAcqSetup sas2) {
        for (Comparator<SeqAcqSetup> comparator : listComparators) {
            int result = comparator.compare(sas1, sas2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }
    
}
