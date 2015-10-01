/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.Classes.Comparators;

/**
 *
 * @author dk1109
 */
public class DistanceComparator {
    // TODO: Implement pointwise distance calculator
    // It will be useful in future to be able, at each step in an XY sequence, 
    // to go to the nearest XY point (c.f. snake acquisition in old LabVIEW). 
    // This should minimise time spent focussing by decreasing the Z offsets
    // between adjacent XY positions caused by lack of flatness in the plate. 
    
    // It should be possible, given a starting point, to find the XY position 
    // with the minimum distance away. This new point should then be used as a 
    // comparison for the next subset of points, and so on until all points
    // are allocated. 
}
