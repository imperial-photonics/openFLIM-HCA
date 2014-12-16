/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dk1109
 */
public class FilterTableModel extends AbstractTableModel {
   public static final int LABEL_INDEX = 0;
   public static final int EX_INDEX = 1;
   public static final int ND_INDEX = 2;
   public static final int DI_INDEX = 3;
   public static final int EM_INDEX = 4;
   public static final int CUBE_INDEX = 5;
   public static final int INT_INDEX = 6;
   public static final int DELS_INDEX = 7;
   
   private ArrayList<FilterSetup> data_ = new ArrayList<FilterSetup>();
   private String[] colNames_ = { "Label", "Ex filter", "ND filter", "Dichroic", "Em filter", "Filter Cube", "Camera integration (ms)", "Delays" };
   
   public FilterTableModel(String[] columnNames) {
         this.colNames_ = columnNames;
    }
   
   public FilterTableModel(String[] columnNames, FilterSetup filts){
       this.colNames_ = columnNames;
       this.data_.add(filts);
   }
   
   public FilterTableModel(FilterSetup filts){
       this.data_.add(filts);
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
        return 8;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        FilterSetup f = data_.get(row);
        switch (col){
            case INT_INDEX:
                int val;
                if (value.getClass() == String.class){
                    val = Integer.parseInt(value.toString());
                    // TODO: validate integration time - upper/lower lims? rounding?
                    f.setIntTime(val);
                }
                else if (value.getClass() == Integer.class){
                    f.setIntTime(Integer.parseInt(value.toString()));
                }
                break;
            case EX_INDEX:
                f.setExFilt(value.toString());
                break;
            case EM_INDEX:
                f.setEmFilt(value.toString());
                break;
            case ND_INDEX:
                f.setNDFilt(value.toString());
                break;
            case DI_INDEX:
                f.setDiFilt(value.toString());
                break;
            case CUBE_INDEX:
                f.setCube(value.toString());
            case LABEL_INDEX:
                f.setLabel(value.toString());
                break;
            case DELS_INDEX:
                ArrayList<Integer> dels = (ArrayList<Integer>) value;
                f.setDelays(dels);
                break;
            default: 
                break;
        }
               
           data_.set(row, f);
           fireTableCellUpdated(row, col);
        
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        FilterSetup f = data_.get(rowIndex);
        switch (columnIndex){
            case EX_INDEX:
                return f.getExFilt();
            case DI_INDEX:
                return f.getDiFilt();
            case EM_INDEX:
                return f.getEmFilt();
            case CUBE_INDEX:
                return f.getCube();
            case INT_INDEX:
                return f.getIntTime();
            case DELS_INDEX: 
                return f.getDelays();
            case ND_INDEX:
                return f.getNDFilt();
            case LABEL_INDEX:
                return f.getLabel();
            default: 
                return f;
        }
    }

    @Override
    public Class getColumnClass(int column) {
         switch (column) {
             case EX_INDEX:
             case DI_INDEX:
             case EM_INDEX:
             case LABEL_INDEX:
             case CUBE_INDEX:
             case ND_INDEX:
                return String.class;
             case INT_INDEX:
                 return Integer.class;
             default:
                return FilterSetup.class;
         }
     }
    
    public ArrayList<FilterSetup> getData(){
        return data_;
    }
    
    public void addRow(FilterSetup f) {
        int row = data_.size();
        data_.add(f);
        fireTableRowsInserted(row, row);
    }
   
    public void insertRow(int index, FilterSetup f){
        data_.add(index, f);
//        fireTableRowsInserted(data_.size() - 1, data_.size() - 1);
        fireTableRowsInserted(data_.size(), data_.size());
    }
    
    public void removeRow(int row) {
        data_.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    public void addWholeData(ArrayList<FilterSetup> data){
        data_.clear();
        data_.addAll(data);
        fireTableDataChanged();
    }
    
    public void clearAllData(){
        data_.clear();
        fireTableDataChanged();
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
        
}


