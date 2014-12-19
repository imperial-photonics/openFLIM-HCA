/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.dougkelly88.FLIMPlateReaderGUI.FLIMClasses.Classes;

import ij.process.ImageProcessor;
import ij.process.ImageStatistics;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mmcorej.CMMCore;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
import org.micromanager.MMStudio;
import org.micromanager.utils.ImageUtils;


/**
 *
 * @author dk1109
 */
public class FindMaxpoint {
//    MMStudio gui_;
//    CMMCore core_;
    private JFreeChart chart_;
    private XYDataset findMaxpointData_;
    private ArrayList<Integer> rawMaxpointData_;
    private XYDataset gatePositionData_;
    private ArrayList<Integer> rawGateData_;
    private int resolution_ = 200;
    private double lifetime_ = 0;
    private ArrayList<Integer> delays_;
    private int maxpointDelay_ = 1000;
//    private XYDataset dataset_;

    public FindMaxpoint(){
//        gui_ = MMStudio.getInstance();
//        core_ = gui_.getCore();
        findMaxpointData_ = createDummyMaxpointData(0);
        gatePositionData_ = createDummyGatingData();
        chart_ = createChart();
//        chart_ = createOverlaidChart(findMaxpointData_, gatePositionData_);
    }
    private XYDataset createDummyGatingData(){
        
        final XYSeries s1 = new XYSeries("DummyGating");
        s1.add(0, 0);
        s1.add(1000, 0);
        s1.add(2000, 0);
        s1.add(3000, 0);
        s1.add(4000, 0);
        s1.add(5000, 0);
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        
        return dataset;
    }
    
    private XYDataset createDummyMaxpointData(int offset){
        
        final XYSeries s1 = new XYSeries("DummyMaxpoint");
        for (int i = 0; i < 16666; i = i + resolution_){
            if (i<1000)
                s1.add(i,0);
            else{
                int num = (int) (4000 * exp( -( (double) ( (i - maxpointDelay_) )
                        /(2000)) ));
                s1.add(i, num);
            }
//                s1.add(i, 4000* exp(-((double) (i - maxpointDelay_))));
        }
//        delays_ = 
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        
        return dataset;
    }
    
    public XYDataset setData(ArrayList<Integer> delays, ArrayList<Double> data){
        final XYSeries s1 = new XYSeries("RealMaxpoint");
        for (int ind = 0; ind < delays.size(); ind++){
            s1.add(delays.get(ind), data.get(ind));
//                s1.add(i, 4000* exp(-((double) (i - maxpointDelay_))));
        }
//        delays_ = 
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        
        return dataset;
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    public JFreeChart createChart() {
        
        //http://www.java2s.com/Code/Java/Chart/JFreeChartDualAxisDemo2.htm
        String xlabel = "Delay (ps)";
        String ylabel = "Signal (DN)";
                
        // create the chart with findmaxpoint results
        final JFreeChart chart = ChartFactory.createXYLineChart(
            null,                   // chart title
            xlabel,                 // x axis label
            ylabel,                 // y axis label
            findMaxpointData_,      // data
            PlotOrientation.VERTICAL,
            false,                  // include legend
            true,                   // tooltips
            false                   // urls
        );
        
        final XYPlot plot = chart.getXYPlot();
        // deal with axes and add second dataset
        final NumberAxis yaxis1 = (NumberAxis) plot.getRangeAxis();
        yaxis1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        yaxis1.setLabelFont(new Font("Dialog", Font.PLAIN, 10));
        final NumberAxis yaxis2 = new NumberAxis(null);
        final NumberAxis xaxis = (NumberAxis) plot.getDomainAxis();
        xaxis.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        xaxis.setLabelFont(new Font("Dialog", Font.PLAIN, 10));
        plot.setRangeAxis(1,yaxis2);
        plot.setDataset(1, gatePositionData_);
        plot.mapDatasetToRangeAxis(1, 1);
        yaxis1.setRange(0,5000);
        yaxis2.setRange(-1,1);
        yaxis2.setTickLabelsVisible(false);
        xaxis.setRange(0,16666);

        // deal with visuals

        final XYLineAndShapeRenderer renderer1= new XYLineAndShapeRenderer(true, true);
        renderer1.setSeriesPaint(0, Color.RED);
        renderer1.setSeriesStroke(0, new BasicStroke(3));

//        renderer1.setBaseShapesVisible(true);
//        renderer1.setSeriesShape(0, ShapeUtilities.createDiagonalCross(4,1));

        plot.setRenderer(0, renderer1);
        
        
//        final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        final XYLineAndShapeRenderer renderer2= new XYLineAndShapeRenderer(false, true);
        renderer2.setSeriesPaint(0, Color.CYAN);
        renderer2.setSeriesShapesFilled(0, Boolean.TRUE);
        renderer2.setBaseShapesVisible(true);
        renderer2.setShape(new Rectangle(-2,-100,4,200));
        renderer2.setOutlineStroke(new BasicStroke(1));
        renderer2.setOutlinePaint(Color.GRAY);
        renderer2.setUseOutlinePaint(true);
        plot.setRenderer(1, renderer2);
        
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
//                
        return chart;
        
    }
            
    
    public JFreeChart getChart(){
        return chart_;
    }
    
    public void setGatingData(ArrayList<Integer> delays){
        final XYSeries s1 = new XYSeries("delays");
        for (Integer delay : delays) {
            s1.add((double) delay, 0);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        rawGateData_ = delays;
        gatePositionData_ = dataset;
        XYPlot plot = chart_.getXYPlot();
        plot.setDataset(1, gatePositionData_);
    }
    
    public void acqMaxpointData(ArrayList<Integer> delays, ArrayList<Double> means ){
//        ArrayList<Integer> signal = new ArrayList<Integer>();
//        ArrayList<Integer> delays = new ArrayList<Integer>();
 
        // instrument interacting fn, currently dummy
        // REMEMBER TO APPLY THRESHOLD
  
        
        
        // plot maxpoint data
//        findMaxpointData_ = createDummyMaxpointData(0);
        findMaxpointData_ = setData(delays, means);
        XYPlot plot = chart_.getXYPlot();
        plot.setDataset(0, findMaxpointData_);
        
        // assign maxpoint - easier when this is real data...
        ArrayList<XYDataItem> dummy = new ArrayList<XYDataItem>(((XYSeriesCollection) findMaxpointData_).getSeries(0).getItems());
        for (XYDataItem dummy1 : dummy) {
            delays.add((Integer) dummy1.getX().intValue());
            means.add((Double) dummy1.getY().doubleValue());
        }
        
        Double[] res = findMaxIndex(means);
        maxpointDelay_ = delays.get(res[0].intValue());
        
        // estimate lifetime
        means = new ArrayList<Double>( means.subList(res[0].intValue(), means.size()));
        delays = new ArrayList<Integer>( delays.subList(res[0].intValue(), delays.size()));
        double sumt2 = 0;
        double sumt = 0;
        double sumtlnI = 0;
        double sumlnI = 0; 
        
        for (int i = 0; i < means.size(); i++){
        
            sumt2 = sumt2 + delays.get(i) * delays.get(i);
            sumt = sumt + delays.get(i);
            sumlnI =  (sumlnI + log((double) means.get(i)));
            sumtlnI = sumtlnI + log((double) means.get(i)) * delays.get(i);
            
        }
        
        lifetime_ = -(delays.size() * sumt2 - sumt * sumt)/(
                delays.size() * sumtlnI - sumt * sumlnI);
        
    }  
  
    public void setResolution(int res){
        resolution_ = res;
    }
    
    public int getResolution(){
        return resolution_;
    }
    
    public int estimateLifetime(ArrayList<Integer> delays, ArrayList<Integer> signal){
        int lifetime = 0;
        
        lifetime_ = lifetime;
        return lifetime;
    }
    
    public void setLifetime(double lifetime){
        lifetime_ = lifetime;
    }
    
    public double getLifetime(){
        return lifetime_;
    }
    
    public ArrayList<Integer> genAutogates(){
        // use dialog to get number of gates + rising edge?
        int N = 5;
        ArrayList<Integer> gates = new ArrayList<Integer>();
        if (lifetime_ != 0){            // rising edge...
//            gates.add(maxpointDelay_ - core_.get());  // instrument interacting fn
            // decay
            for (int i = 0; i < N-1; i++){
                gates.add( maxpointDelay_ + 
                        (int) (lifetime_* log((double) (i + 1))/log((double) 2)));
            }
        }
        else{
        // show warning dialog
            for (int i = 0; i < N; i++){
                gates.add(i*1000);
            }
        }
        
        return gates;
    }
    
    private Double[] findMaxIndex(ArrayList<Double> list){
        int maxIndex = 0;
        Double maxVal = Double.MIN_VALUE;
        
        for(int i=0; i<list.size(); i++){
            if(list.get(i) > maxVal){
            maxVal = list.get(i);
            maxIndex = i;
            }
        }
        
        Double[] ret = {(double)maxIndex, maxVal};
        return ret;
    }

    

    public double getMeanValueOfImage(CMMCore core){
        double meanValue=-1;
//        MMStudio gui = MMStudio.getInstance();
//        CMMCore core = gui.getCore();
        
        // TODO: fix such that thresholding takes care of low value pixels

        try {
//            core.getImage();
            core.snapImage();
            ImageProcessor ip = ImageUtils.makeProcessor(core, core.getImage());
            ImageStatistics i = ip.getStatistics();
            meanValue = i.mean;
            
            
        } catch (Exception ex) {
            Logger.getLogger(FindMaxpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meanValue;
    }
}
