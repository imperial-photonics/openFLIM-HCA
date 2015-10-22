/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Variable;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Frederik
 */
public class SyringeTableModel extends AbstractTableModel {
    private ArrayList<String> data_ = new ArrayList<String>();
    private String colNames_ = "Liquid Dispension Wells:";
    private Variable var_;
    
    
    public SyringeTableModel(){
    var_= Variable.getInstance();
    }
    
    public SyringeTableModel(String columnNames) {
         this.colNames_ = columnNames;
    }
    
    public SyringeTableModel(String columnNames, ArrayList<String> ldWells){
        this.colNames_ = columnNames;
        for (int i = 0; i < ldWells.size(); i++){
            ldWells.set(i, validateData(ldWells.get(i)));
        }
        this.data_ = ldWells;
        
        fireTableDataChanged();
//        sap_ = SeqAcqProps.getInstance().setDelaysArray(0, delays);
    }
    
 
    @Override
    public String getColumnName(int col) {
      return colNames_;
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        return ((String) data_.get(row));
    }
    @Override
    public int getColumnCount() {
        return 1;
    }
    @Override
    public int getRowCount() {
        return data_.size();
    }
    
    public ArrayList<String> getData(){
        return data_;
    }
 
    public void addRow(String rowData) {
        int row = data_.size();
        data_.add(rowData);
        fireTableRowsInserted(row, row);
//        sap_.setDelaysArray(0, data_);
    }
    
    public void insertRow(int index, String rowData){
        data_.add(index, rowData);
        fireTableRowsInserted(data_.size() - 1, data_.size() - 1);
//        sap_.setDelaysArray(0, data_);
    }
    
    public void removeRow(int row) {
        data_.remove(row);
        fireTableRowsDeleted(row, row);
//        sap_.setDelaysArray(0, data_);
    }
    
    public void addWholeData(ArrayList<String> data){
        data_.clear();
        for (int i = 0; i < data.size(); i++){
            data.set(i, validateData(data.get(i)));
        }
        data_.addAll(data);
        
        fireTableDataChanged();
//        sap_.setDelaysArray(0, data_);
//        this.addEmptyRow();
    }
    
    public void clearAllData(){
        data_.clear();
        fireTableDataChanged();
//        sap_.setDelaysArray(0, data_);
    }
    
   
    @Override
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        
        String val;
        
        if (value.getClass() == String.class){
            
            if (!(value.toString().isEmpty())){
            val = value.toString();
            val = validateData(val);
            data_.set(row, val);
            }
            else {
//                data_.set(row, null);
                data_.remove(row);
            }
            
        }
        
        fireTableCellUpdated(row, col);
        
//        sap_.setDelaysArray(0, data_);
//        if (!this.hasEmptyRow())
//        {
//            this.addEmptyRow();
//        }
    }
    
    public String validateData(String val){
        int length=val.length();
        String row=val.substring(1);
        String column=null;
        if (length==2){
            column=val.substring(1, 2);
        } else if (length==3){
            column=val.substring(1, 3);
        } else{
            System.out.println("Error: validateData in SyringeTableModel!");
            System.out.println("Well input invalid! Please chose as row a letter between A-H and a column between 1-12.");
        }
        if (length==2){
            column=val.substring(1, 2);
        } else if (length==3){
            column=val.substring(1, 3);
        } else{
            System.out.println("Error: validateData in SyringeTableModel!");
            System.out.println("Well input invalid! Please chose as row a letter between A-H and a column between 1-12.");
        }
        row=row.toUpperCase();
        return val;
    }

}
