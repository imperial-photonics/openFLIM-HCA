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
        String wTest1="nope1";
        String wTest2="nope2";
        if (w1.length()==2){
            String firstSubstring=w1.substring(0,1);
            String secondSubstring="0";
            String thirdSubstring=w1.substring(1);
            w1=firstSubstring+secondSubstring+thirdSubstring;
        }
        if(w2.length()==2){
            String firstSubstring=w2.substring(0,1);
            String secondSubstring="0";
            String thirdSubstring=w2.substring(1);
            w2=firstSubstring+secondSubstring+thirdSubstring; 
        }
//55        System.out.print("w1="+w1+"\n"+"wTest1="+wTest1+"\n"+"w1.length="+w1.length()+"\n"); 
 //55       System.out.print("w2="+w2+"\n"+"wTest2="+wTest2+"\n"+"w2.length="+w2.length()+"\n");
        return w1.compareTo(w2);
    }
    
}
