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
public class XY_simul_Comparator implements Comparator<SeqAcqSetup>{

    @Override
    public int compare(SeqAcqSetup o1, SeqAcqSetup o2) {
        Double x1 = o1.getFOV().getX();
        Double x2 = o2.getFOV().getX();
        Double y1 = o1.getFOV().getY();
        Double y2 = o2.getFOV().getY();
        String xy1=Double.toString(x1)+Double.toString(y1);
        String xy2=Double.toString(x2)+Double.toString(y2);
        return xy1.compareTo(xy2);
    }
    
}
