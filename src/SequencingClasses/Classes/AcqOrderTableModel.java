/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.Classes;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author dk1109
 */
public class AcqOrderTableModel extends AbstractTableModel {

//    public static final int POS_INDEX = 0;
    public static final int DESC_INDEX = 0;

    private ArrayList<String> data_ = new ArrayList<String>();
    private String[] colNames_ = {"Acquisition step order"};

    public AcqOrderTableModel() {
        // TODO: add default (i.e. xyz?)
    }

    public AcqOrderTableModel(String[] columnNames) {
        this.colNames_ = columnNames;
        // TODO: add default, i.e. xyz
    }

    @Override
    public String getColumnName(int col) {
        return colNames_[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case DESC_INDEX:
                return data_.get(row);
            default:
                return data_.get(row);
        }
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public int getRowCount() {
        return data_.size();
    }

    public ArrayList<String> getData() {
        return data_;
    }

    public void addRow(String desc) {
        int row = data_.size();
        data_.add(desc);
        fireTableRowsInserted(row, row);
    }

    public void insertRow(int index, String desc) {
        data_.add(index, desc);
        fireTableRowsInserted(data_.size() - 1, data_.size() - 1);
    }

    public void removeRow(int row) {
        data_.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void addWholeData(ArrayList<String> data) {
        data_.clear();
        data_.addAll(data);
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

    @Override
    public void setValueAt(Object value, int row, int column) {
        data_.set(row, (String) value);
        fireTableCellUpdated(row, column);
    }

    public void setValueAtRow(String desc, int row) {
        data_.set(row, desc);
    }

}
