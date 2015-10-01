/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SequencingClasses.Classes;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author dk1109
 */
public class TableRenderer extends DefaultTableCellRenderer{
    private static DecimalFormat df = new DecimalFormat("#0.00");

    public TableRenderer(){};
//        public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
//            Component cell = super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
//
//            if(row >0&&column>0){
//                cell.setBackground(Color.GREEN);
//            }
//
//            return cell;
//        }
    
    @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            FOV val = (FOV) value;
            if (value == null) {
                value = "";
//                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//                cell.setBackground(Color.LIGHT_GRAY);
            } else {
//                this.setText(df.format(val.z_));
            }
//            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
}
