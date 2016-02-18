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
public class RowComparator implements Comparator<SeqAcqSetup>{


    @Override
    public int compare(SeqAcqSetup o1, SeqAcqSetup o2) {
        String w1 = o1.getFOV().getWell();
        String w2 = o2.getFOV().getWell();

        String[] wellref1 = Utilitiesclass.splitWell(w1);
        String[] wellref2 = Utilitiesclass.splitWell(w2);

        // Something is very wrong if we don't juat have one letter block and one number block...
        if (2!=wellref1.length | 2!=wellref2.length){
            return 0; // Hopefully this just means nothing will change due to this comparator?
            // Although perhaps only true if all entries are messed up?
        }
        else{
            String row1=wellref1[0];
            String row2=wellref2[0];
            // Adding leading zeros for sort: http://stackoverflow.com/questions/4051887/how-to-format-a-java-string-with-leading-zero
            int padded_length=8; // Should be plenty for any sensibly-sized plate...          
            if("None".equals(o1.getSnaketype())){
                return 0; // Think this should make it do nothing   
            }
            else if("Vertical".equals(o1.getSnaketype())){
                //Determine if row is 'Even'
                int colnum = Integer.parseInt(wellref1[1]);
                
                if(0==colnum%2){
                    return row1.compareTo(row2);   
                }
                else{
                    //Reversing order of operands should invert sort direction
                    return row2.compareTo(row1);                       
                }
            }
            else if("Horizontal".equals(o1.getSnaketype())){
                return row1.compareTo(row2);  // NO NEED TO ADD FLIP LOGIC FOR H-SNAKING IN ROWS
            }
            else{
                return 0;
            }
        }
    }
    
}