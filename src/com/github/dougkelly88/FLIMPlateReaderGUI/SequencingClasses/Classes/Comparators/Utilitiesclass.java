/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.Comparators;

/**
 *
 * @author Sunil
 */
public final class Utilitiesclass {
    
    /* Apparently we have to make a constructor that can never be called (private constructor)
    *  to allow this to be made static sensibly? http://stackoverflow.com/questions/1844355/java-static-class
    */  
    
    public Utilitiesclass() {
        //throw new AssertionError("Instantiation of a utility class is probably a Bad Thing");
    }
    
    //Declare General member variables up here
    
    public static String reverseString (String input){
        String reversed_string = new StringBuilder(input).reverse().toString();
        return reversed_string;
    }
    
    public static String padLeadingZeros (String input, int final_length){
        String padded_string = String.format("%0"+ (final_length - input.length() )+"d%s",0 ,input);         
        return padded_string;
    }
    
    public static String[] splitWell (String input){
        // Split numbers, letters: http://stackoverflow.com/questions/8270784/how-to-split-a-string-between-letters-and-digits-or-between-digits-and-letters 
        String[] subsets = input.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)"); 
        return subsets;
    }
    
    public int WellLetterstoNumber(String wellletters){
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Double totalvalue=0.0;
        for (int i=0;i<(wellletters.length());i++){
            char thischar = wellletters.charAt(i);
            String thisletter = Character.toString(thischar);
            int position = alphabet.indexOf(thisletter)+1;
            totalvalue+=position*(Math.pow(26,i));
        }
        return totalvalue.intValue();
     }
    
    public String NumbertoWellLetters(int number){
        //Bit of a mess, but it should work - there's probably some more elegant method
        double num_dbl=number;
        int i=0;
        Double rel_value=0.0;
        int division=0;
        String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        do{
            i++;
            rel_value=Math.pow(26,i);
            division=number/rel_value.intValue();
        }while (division>0);
        Double thisletter=0.0;
        String output="";
        Character currentletter;
        for(int k=i-1;k>=0;k--){
            thisletter=(num_dbl/Math.pow(26,k));
            int thislett_int=thisletter.intValue();
            num_dbl=num_dbl-(Math.pow(26,k)*thislett_int);
            currentletter=(alphabet.charAt(thislett_int-1));
            output=output.concat(currentletter.toString());
        }
        return output;
    }
}
