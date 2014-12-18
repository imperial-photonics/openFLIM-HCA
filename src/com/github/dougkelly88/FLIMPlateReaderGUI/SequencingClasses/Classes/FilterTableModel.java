/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import static com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FOVTableModel.um;
import com.google.common.base.Functions;
import static com.google.common.collect.DiscreteDomain.integers;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static ucar.unidata.util.Format.i;

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
    
    public void saveFilterTableModelAsSpreadsheet(){
    //    HSSFWorkbook wb = new HSSFWorkbook();
    //    HSSFSheet sheet1 = wb.createSheet("XYSequencing");
        HSSFSheet sheet2 = HCAFLIMPluginFrame.wb.createSheet("SpectralSequencing");
    //    HSSFSheet sheet3 = wb.createSheet("TimeCourseSequencing");
        
        
        int RowSize=data_.size();
            HSSFRow row0 = sheet2.createRow(0);
            HSSFCell cell00 = row0.createCell(0);
            HSSFCell cell01 = row0.createCell(1);
            HSSFCell cell02 = row0.createCell(2);
            HSSFCell cell03 = row0.createCell(3);
            HSSFCell cell04 = row0.createCell(4);
            HSSFCell cell05 = row0.createCell(5);
            HSSFCell cell06 = row0.createCell(6);
            HSSFCell cell07 = row0.createCell(7);
            
            cell00.setCellValue("Label");
            cell01.setCellValue("Ex filter");
            cell02.setCellValue("ND filter");
            cell03.setCellValue("Dichroic");
            cell04.setCellValue("Em filter");
            cell05.setCellValue("Filter Cube");
            cell06.setCellValue("Camera integration (ms)");
            cell07.setCellValue("Delays");
        for(int RowNum=0; RowNum<RowSize;RowNum++){
            HSSFRow row = sheet2.createRow(RowNum+1);
            HSSFCell cell0 = row.createCell(0);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            
            cell0.setCellValue(data_.get(RowNum).getLabel());
            cell1.setCellValue(data_.get(RowNum).getExFilt());
            cell2.setCellValue(data_.get(RowNum).getNDFilt());
            cell3.setCellValue(data_.get(RowNum).getDiFilt());
            cell4.setCellValue(data_.get(RowNum).getEmFilt());
            cell5.setCellValue(data_.get(RowNum).getCube());
            cell6.setCellValue(data_.get(RowNum).getIntTime());
           
            ArrayList<Integer> a = new ArrayList<Integer>();
            a=data_.get(RowNum).getDelays();
            List<String> newList = new ArrayList<String>(a.size()); 
            for (Integer myInt : a) { 
                newList.add(String.valueOf(myInt)); 
            }
        /*    double[] b=null;
            Iterator<Double> iterator = a.iterator();
            for(int i = 0; i < a.size(); i++)
            {
            b[i] = iterator.next();
            }*/
            String b="[";
            for (String s : newList)
                {
                   b += s + ", ";
                }
            int s=b.length();
            b=b.substring(0, s-2);
            b=b+"]";
            cell7.setCellValue(b);
            
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
    }
        

        
}


