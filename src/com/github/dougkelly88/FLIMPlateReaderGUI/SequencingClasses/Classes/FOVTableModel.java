/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.PlateProperties;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;

/**
 *
 * @author dk1109
 */
public class FOVTableModel extends AbstractTableModel {

    public static final int WELL_INDEX = 0;
    public static final int X_INDEX = 1;
    public static final int Y_INDEX = 2;
    public static final int Z_INDEX = 3;
    public static final int GROUP_INDEX = 4;

    final static String um = "(" + "\u00B5" + "m)";

    private ArrayList<FOV> data_ = new ArrayList<FOV>();
    private String[] colNames_ = {"Well", "X " + um, "Y" + um, "Z" + um, "Group"};

    private PlateProperties pp_;

//    private SeqAcqProps sap_;
    //TODO: remove duplicates!
    public FOVTableModel(PlateProperties pp) {
        pp_ = pp;
    }

    public FOVTableModel(String[] columnNames, PlateProperties pp) {
        this.colNames_ = columnNames;
        pp_ = pp;
    }

    @Override
    public String getColumnName(int col) {
        return colNames_[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        FOV fov = data_.get(row);
        switch (col) {
            case WELL_INDEX:
                return fov.getWell();
            case X_INDEX:
                return fov.getX();
            case Y_INDEX:
                return fov.getY();
            case Z_INDEX:
                return fov.getZ();
            case GROUP_INDEX:
                return fov.getGroup();
            default:
                return fov;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case WELL_INDEX:
            case GROUP_INDEX:
                return String.class;
            case X_INDEX:
            case Y_INDEX:
            case Z_INDEX:
                return Double.class;
            default:
                return FOV.class;
        }
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getRowCount() {
        return data_.size();
    }

    public ArrayList<FOV> getData() {
        return data_;
    }

    public void addRow(FOV fov) {
        int row = data_.size();
        fov = validateData(fov);
        if (!data_.contains(fov)) {
            data_.add(fov);
            fireTableRowsInserted(row, row);
        }

//        sap_.setDelaysArray(0, data_);
    }

    public void insertRow(int index, FOV fov) {
        fov = validateData(fov);
        if (!data_.contains(fov)) {
            data_.add(index, fov);
            fireTableRowsInserted(data_.size() - 1, data_.size() - 1);
        }
//        sap_.setDelaysArray(0, data_);
    }

    public void removeRow(int row) {
        data_.remove(row);
        fireTableRowsDeleted(row, row);
//        sap_.setDelaysArray(0, data_);
    }

    public void addWholeData(ArrayList<FOV> data) {
        data_.clear();

        data_.addAll(data);

        fireTableDataChanged();
//        sap_.setDelaysArray(0, data_);
//        this.addEmptyRow();
    }

    public void clearAllData() {
        data_.clear();
        fireTableDataChanged();
//        sap_.setDelaysArray(0, data_);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        switch (col) {
            case WELL_INDEX:
                return false;
            case X_INDEX:
            case Y_INDEX:
            case Z_INDEX:
            case GROUP_INDEX:
                return true;
            default:
                return true;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        FOV fov = (FOV) data_.get(row);
        switch (column) {
            case WELL_INDEX:
                fov.setWell((String) value);
                break;
            case X_INDEX:
                value = validateX((Double) value, fov.getPlateProps());
                fov.setX((Double) value);
                break;
            case Y_INDEX:
                value = validateY((Double) value, fov.getPlateProps());
                fov.setY((Double) value);
                break;
            case Z_INDEX:
                fov.setZ((Double) value);
                break;
            case GROUP_INDEX:
                fov.setGroup((String) value);
                break;
            default:
                System.out.println("invalid index");
        }
        fireTableCellUpdated(row, column);
    }

    public void setValueAtRow(FOV fov, int row) {
        fov = validateData(fov);
        data_.set(row, fov);
    }

    private FOV validateData(FOV fov) {
        PlateProperties pp = fov.getPlateProps();
        double xmin = pp.getTopLeftWellOffsetH() - pp.getWellSpacingH() / 2;
        double xmax = pp.getTopLeftWellOffsetH()
                + (pp.getPlateColumns() - 0.5) * pp.getWellSpacingH();
        double ymin = pp.getTopLeftWellOffsetV() - pp.getWellSpacingV() / 2;
        double ymax = pp.getTopLeftWellOffsetV()
                + (pp.getPlateRows() - 0.5) * pp.getWellSpacingV();

        if (fov.getX() < xmin) {
            fov.setX(xmin);
        }
        if (fov.getX() > xmax) {
            fov.setX(xmax);
        }
        if (fov.getY() < ymin) {
            fov.setY(ymin);
        }
        if (fov.getY() > ymax) {
            fov.setY(ymax);
        }

        return fov;
    }

    private double validateX(double x, PlateProperties pp) {

        double xmin = pp.getTopLeftWellOffsetH() - pp.getWellSpacingH() / 2;
        double xmax = pp.getTopLeftWellOffsetH()
                + (pp.getPlateColumns() - 0.5) * pp.getWellSpacingH();

        if (x < xmin) {
            x = xmin;
        }
        if (x > xmax) {
            x = xmax;
        }

        return x;
    }

    private double validateY(double y, PlateProperties pp) {

        double ymin = pp.getTopLeftWellOffsetV() - pp.getWellSpacingV() / 2;
        double ymax = pp.getTopLeftWellOffsetV()
                + (pp.getPlateRows() - 0.5) * pp.getWellSpacingV();

        if (y < ymin) {
            y = ymin;
        }
        if (y > ymax) {
            y = ymax;
        }

        return y;
    }

    public void setPlateProps(PlateProperties pp) {
        pp_ = pp;
    }

    public void saveFOVTableModelAsSpreadsheet(){

    //    HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet1 = HCAFLIMPluginFrame.wb.createSheet("XYSequencing");
    //    HSSFSheet sheet2 = wb.createSheet("SpectralSequencing");
    //    HSSFSheet sheet3 = wb.createSheet("TimeCourseSequencing");
        
        
         int RowSize=data_.size();
            HSSFRow row0 = sheet1.createRow(0);
            HSSFCell cell00 = row0.createCell(0);
            HSSFCell cell01 = row0.createCell(1);
            HSSFCell cell02 = row0.createCell(2);
            HSSFCell cell03 = row0.createCell(3);
            HSSFCell cell04 = row0.createCell(4);
            cell00.setCellValue("Well");
            cell01.setCellValue("X " + um);
            cell02.setCellValue("Y" + um);
            cell03.setCellValue("Z" + um);
            cell04.setCellValue("Group");
       for(int RowNum=0; RowNum<RowSize;RowNum++){
            HSSFRow row = sheet1.createRow(RowNum+1);
            HSSFCell cell0 = row.createCell(0);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            cell0.setCellValue(data_.get(RowNum).getWell());
            cell1.setCellValue(data_.get(RowNum).getX());
            cell2.setCellValue(data_.get(RowNum).getY());
            cell3.setCellValue(data_.get(RowNum).getZ());
            cell4.setCellValue(data_.get(RowNum).getGroup());
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("C:\\Users\\Frederik\\Desktop\\OpenHCAFLIM_Sequenzing.xls");
            HCAFLIMPluginFrame.wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FOVTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FOVTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        //To change body of generated methods, choose Tools | Templates.
    }

}
