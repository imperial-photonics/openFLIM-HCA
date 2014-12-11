/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Frederik
 */
public class TimeCourseTableModel extends AbstractTableModel {

    public static final int TIME_INDEX = 0;
    public static final int LD_INDEX = 1;
    public static final int SA_INDEX = 2;
    final static String ul = "(" + "\u00B5" + "l)";

    private ArrayList<TimePoint> data_ = new ArrayList<TimePoint>();
    private String[] colNames_ = {"Time (s)", "Liquid dispense volume " + ul,
        "Sound alert?"};

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
                f.setLDVolume((Double) value);
                break;
            case SA_INDEX:
                boolean boo = (Boolean) value;
                f.setSAState(boo);
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
                return f.getLDVolume();
            case SA_INDEX:
                return f.getSAState();
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
            case SA_INDEX:
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

}
