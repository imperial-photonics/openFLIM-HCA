/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import mmcorej.CMMCore;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.RichTextString;
import org.micromanager.MMStudio;

/**
 *
 * @author Frederik
 */
public class TimeCourseTableModel extends AbstractTableModel {

    public static final int TIME_INDEX = 0;
    public static final int LD_INDEX = 1;
    public static final int LDWELLS_INDEX = 2;
    final static String ul = "(" + "\u00B5" + "l)";

    private ArrayList<TimePoint> data_ = new ArrayList<TimePoint>();
    private String[] colNames_ = {"Time (s)", "Liquid dispension?",
        "Liquid dispension Well(s)"};
    private String prevWell="Z00";
    private Double prevTpTimeCell= (double)-1;
    CMMCore core_; 
    MMStudio gui_;

    public TimeCourseTableModel(String[] columnNames) {
        this.colNames_ = columnNames;
    }

    public TimeCourseTableModel(String[] columnNames, TimePoint times) {
        this.colNames_ = columnNames;
        this.data_.add(times);
    }

    public TimeCourseTableModel(TimePoint times) {
        this.data_.add(times);
    }

    @Override
    public int getRowCount() {
        return data_.size();
    }

    @Override
    public String getColumnName(int col) {
        return colNames_[col];
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        TimePoint f = data_.get(row);
        switch (col) {

            case TIME_INDEX:
                f.setTimeCell((Double) value);
                break;
            case LD_INDEX:
//                boolean bo=(Boolean) value;
                f.setLDState((Boolean) value);
                break;
            case LDWELLS_INDEX:
                //boolean boo = (Boolean) value;
                f.setLdWells((ArrayList<String>) value);
                break;

            default:
                break;
        }

        data_.set(row, f);
        validateTimes();
        fireTableCellUpdated(row, col);

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        TimePoint f = data_.get(rowIndex);
        switch (columnIndex) {
            case TIME_INDEX:
                return f.getTimeCell();
            case LD_INDEX:
                return f.getLDState();
            case LDWELLS_INDEX:
                return f.getLdWells();
            default:
                return f;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case TIME_INDEX:
                return getValueAt(0, column).getClass();
            case LD_INDEX:
                return getValueAt(0, column).getClass();
            case LDWELLS_INDEX:
                return getValueAt(0, column).getClass();
//                 return getValueAt(0, column).getClass();
            default:
                return TimePoint.class;
        }
    }

    public ArrayList<TimePoint> getData() {
        return data_;
    }

    public void addRow(TimePoint f) {
        int row = data_.size();
        data_.add(f);
        fireTableRowsInserted(row, row);
    }

    public void insertRow(int index, TimePoint f) {
        data_.add(index, f);
        System.out.println("new data: "+ data_);
        validateTimes();
//        fireTableRowsInserted(data_.size() - 1, data_.size() - 1);
        fireTableRowsInserted(data_.size(), data_.size());
    }

    public void removeRow(int row) {
        data_.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void addWholeData(ArrayList<TimePoint> data) {
        data_.clear();
        data_.addAll(data);
        validateTimes();
        fireTableDataChanged();
    }

    public void clearAllData() {
        data_.clear();
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    private void validateTimes() {

        // This is easiest if time points are in order; fortunately we want this
        // in any case...
        // Note further that this is only worth doing if length > 1
        if (data_.size() > 1) {
            Collections.sort(data_);

            ArrayList<TimePoint> newdata = new ArrayList<TimePoint>();

            for (TimePoint ts : data_){
                if(!newdata.contains(ts))
                    newdata.add(ts);
            }
            data_.clear();
            data_.addAll(newdata);
            fireTableDataChanged();
            
        }
    }
    
    public void saveTimeCourseTableModelAsSpreadsheet(){

    // Save FilterTable in third sheet of wb .xls
    // Initialize third sheet
        HSSFSheet sheet3 = HCAFLIMPluginFrame.wb.createSheet("TimeCourseSequencing");

        
    // Initialize first row with headers    
         int RowSize=data_.size();
            HSSFRow row0 = sheet3.createRow(0);
            HSSFCell cell00 = row0.createCell(0);
            HSSFCell cell01 = row0.createCell(1);
            HSSFCell cell02 = row0.createCell(2);
            cell00.setCellValue("Time (s)");
            cell01.setCellValue("Liquid dispense?");
            cell02.setCellValue("Liquid dispension well(s)");

            
    // write row for row from table to sheet        
       for(int RowNum=0; RowNum<RowSize;RowNum++){
            HSSFRow row = sheet3.createRow(RowNum+1);
            HSSFCell cell0 = row.createCell(0);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            cell0.setCellValue(data_.get(RowNum).getTimeCell());
            cell1.setCellValue(data_.get(RowNum).getLDState());
            cell2.setCellValue((RichTextString) data_.get(RowNum).getLdWells());
            
        }
       
        

        //To change body of generated methods, choose Tools | Templates.
    }
    
    public boolean doSyringe(TimePoint tp, String Well){
        boolean ret=false;
        TimePoint f = data_.get(2);
        ArrayList<String> data1= f.getLdWells();
        if (prevWell.equals(Well) && prevTpTimeCell==tp.getTimeCell() || !data1.contains(Well)){
            ret=false;
        } else {
            if(tp.getLDState()){
                ret=true;
                System.out.println("Add liquid to well "+Well+" at time point "+ tp.getTimeCell());
                /*try {
                    core_.setProperty("SyringePump","Liquid Dispersion", "Go");
                } catch (Exception ex) {
                    System.out.println("Error TimeCourseTableModel doSyringe. Couldn't set property at SyringPump.");
                }
                try {
                    wait(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Error TimeCourseTableModel doSyringe. Couldn't wait untill syring finished!");
                }*/
            } else if (tp.getLDState()==false){
                ret=false;
            } else {
                System.out.println("Error: TimeCourseTableModel doSyringe. Failure in checking liquid dispension state during time course aqcuisition!");
                ret=false;
            }
        }
        prevWell=Well;
        prevTpTimeCell=tp.getTimeCell();
        return ret;
    }

    /*public void loadTimeCourseTableModelfromSpreadsheet() {
        ArrayList<TimePoint> load=new ArrayList();
        HSSFSheet worksheet = HCAFLIMPluginFrame.wbLoad.getSheet("TimeCourseSequencing");
        int RowSize=worksheet.getPhysicalNumberOfRows();
        for(int RowNum=0; RowNum<RowSize-1;RowNum++){
            HSSFRow row = worksheet.getRow(RowNum+1);
            HSSFCell cell0 = row.getCell(0);
            HSSFCell cell1 = row.getCell(1);
            HSSFCell cell2 = row.getCell(2);
            
            TimePoint fov = new TimePoint(cell0.getNumericCellValue(),cell1.getBooleanCellValue(), cell2.getRichTextStringCellValue());
            load.add(fov);
        }
        data_=load;
        fireTableDataChanged();
        
    
    }*/

}
