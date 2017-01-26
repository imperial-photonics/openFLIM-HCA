/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators;

import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SeqAcqSetup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Frederik
 */
public class SnakeOrderer {
    
    public static SnakeOrderer getInstance() {
        return fINSTANCE;
    }
    //MMStudio gui_;
    //CMMCore core_;
    private static final SnakeOrderer fINSTANCE =  new SnakeOrderer();
    
    public List<SeqAcqSetup> snakeOrdererHorizontalFast(List<SeqAcqSetup> listIn){
    
        List<SeqAcqSetup> listOut1=new ArrayList<SeqAcqSetup>();
        int i=0;
        int k=0;
        // check already sorted input list for columns 
        for(String wellLetter : new String[] {"A","B","C","D","E","F","G","H"}){
            List<SeqAcqSetup> listOut2=new ArrayList<SeqAcqSetup>();
            k=0;
            // input list contains letter -> FOV added to seperate list (listOut2) 
            for (SeqAcqSetup curFOV : listIn){
                if (curFOV.getFOV().getWell().contains(wellLetter)){
                    listOut2.add(curFOV);
                }
                
            }
            // i gives information which colums are selected (importent if one column in between is not selected)
            if (!listOut2.isEmpty()){
                i++;
            }
            // if i even and not previouse i, list is reversed
            if (  (i&1)==0 && i!=k ){
                Collections.reverse(listOut2);
            }
            // et voila Snake mode
            listOut1.addAll(listOut2); 
            k=i;
        }
        System.out.print("'Snake (horizontal fast axis)' as acquisition mode selected");
        return listOut1;
    
    }
    
}
