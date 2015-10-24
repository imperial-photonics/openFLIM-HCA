/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.SeqAcqProps;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Variable;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.SyringeTableModel;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.TimePoint;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.TimeCourseTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import mmcorej.CMMCore;
import org.micromanager.MMStudio;

/**
 *
 * @author Frederik
 */
public class TimeCourseSequencing extends javax.swing.JPanel {
    
    public TimeCourseTableModel tableModel_;
    JTable timeTable_;
    SyringeTableModel syringeTableModel_;
    JTable syringeTable_;
    HCAFLIMPluginFrame parent_;
    SeqAcqProps sap_;
    Variable var_;
    CMMCore core_; 
    MMStudio gui_;
    int selectedRow_;
    ArrayList<String> init = new ArrayList<>();
    
    
    /**
     * Creates new form SpectralSequencing
     */
    public TimeCourseSequencing() {
        initComponents();
        setControlDefaults();
        init.add("A1");
        init.add("B2");
        init.add("C3");
        init.add("D4");
        init.add("E5");
    }
    
    private void setControlDefaults(){
       
        try{
            gui_ = MMStudio.getInstance();
            core_ = gui_.getCore();
        }
        catch (Exception e){
            System.out.println("Exception = " + e.getMessage());
        }
        
        sap_ = SeqAcqProps.getInstance();
       
        
//        tableModel_ = new FilterTableModel(new FilterSetup("GFP", "465/30",
//                "ND 1.0","473/561","525/30",100,sap_.getDelaysArray().get(0)));
        tableModel_ = new TimeCourseTableModel(new TimePoint(0.0, false,
                init));
        tableModel_.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
        timeTable_ = new JTable();
        timeTable_.setModel(tableModel_);
        timeTable_.setSurrendersFocusOnKeystroke(true);
        timeTable_.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane scroller = new javax.swing.JScrollPane(timeTable_);
        timeTable_.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        timeCourseSeqBasePanel.setLayout(new BorderLayout());
        timeCourseSeqBasePanel.add(scroller, BorderLayout.CENTER);
        
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete time config");
        deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = timeTable_.getSelectedRow();
                tableModel_.removeRow(r);
            }
        });
        JMenuItem addItem = new JMenuItem("Add time config");
        addItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = timeTable_.getSelectedRow();
                tableModel_.insertRow(r+1, new TimePoint(-1.0, false,
                        init));
            }
        });
        
        
        popupMenu.add(addItem);
        popupMenu.add(deleteItem);
    //    popupMenu.add(setDels);
        timeTable_.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());

                    if (!source.isRowSelected(row)) {
                        source.changeSelection(row, column, false, false);
                    }

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        tableModel_ = new TimeCourseTableModel(new TimePoint(0.0, false,
                init));
        tableModel_.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
        
        tableModel_ = new TimeCourseTableModel(new TimePoint(0.0, false,
                init));
        tableModel_.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
        //---------------------------------------------------------------------------------------
        syringeTableModel_ = new SyringeTableModel("Liquid Dispension Wells:",init);
        syringeTableModel_.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
        syringeTable_ = new JTable(){
             @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component comp = super.prepareRenderer(renderer, row, column);
                        int modelRow = convertRowIndexToModel(row);
                        int modelColumn = convertColumnIndexToModel(column);
                        if (modelColumn != 0 && modelRow != 0) {
                            comp.setBackground(Color.GREEN);
                        }
                        //syringeTableModel_.validateData();
                        return comp;
                    }
         };
        syringeTable_.setModel(syringeTableModel_);
        syringeTable_.setSurrendersFocusOnKeystroke(true);
        syringeTable_.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane scroller2 = new javax.swing.JScrollPane(syringeTable_);
        syringeTable_.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        syringeTablePanel.setLayout(new BorderLayout());
        syringeTablePanel.add(scroller2, BorderLayout.CENTER);
        
        final JPopupMenu popupMenu2 = new JPopupMenu();
        JMenuItem deleteItem2 = new JMenuItem("Unselect well from liquid dispension");
        deleteItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = syringeTable_.getSelectedRow();
                syringeTableModel_.removeRow(r);
            }
        });
        JMenuItem addItem2 = new JMenuItem("Add well to liquid dispension");
        addItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = syringeTable_.getSelectedRow();
                syringeTableModel_.insertRow(r+1, "Z0");
            }
        });
        
        
        popupMenu2.add(addItem2);
        popupMenu2.add(deleteItem2);
    //    popupMenu.add(setDels);
        syringeTable_.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JTable source = (JTable) e.getSource();
                    int row = source.rowAtPoint(e.getPoint());
                    int column = source.columnAtPoint(e.getPoint());

                    if (!source.isRowSelected(row)) {
                        source.changeSelection(row, column, false, false);
                    }

                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        
    }
    
    private void setParent(HCAFLIMPluginFrame frame){
        parent_ = frame;
    }
    
    public ArrayList<TimePoint> getTimeTable(){
        return tableModel_.getData();
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void myinitComponents() {

        timeCourseSeqBasePanel = new javax.swing.JPanel();

        timeCourseSeqBasePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout timeCourseSeqBasePanelLayout = new javax.swing.GroupLayout(timeCourseSeqBasePanel);
        timeCourseSeqBasePanel.setLayout(timeCourseSeqBasePanelLayout);
        timeCourseSeqBasePanelLayout.setHorizontalGroup(timeCourseSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 623, Short.MAX_VALUE)
        );
        timeCourseSeqBasePanelLayout.setVerticalGroup(timeCourseSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(timeCourseSeqBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(timeCourseSeqBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        timeCourseSeqBasePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        noTimePointsField = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        timeStepField = new javax.swing.JFormattedTextField();
        timeUnitsCombo = new javax.swing.JComboBox();
        popTimeCourseButton = new javax.swing.JButton();
        liquidDispensionWellsTableLable = new javax.swing.JLabel();
        liquidDispensionButton = new javax.swing.JButton();
        syringeTablePanel = new javax.swing.JPanel();

        timeCourseSeqBasePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout timeCourseSeqBasePanelLayout = new javax.swing.GroupLayout(timeCourseSeqBasePanel);
        timeCourseSeqBasePanel.setLayout(timeCourseSeqBasePanelLayout);
        timeCourseSeqBasePanelLayout.setHorizontalGroup(
            timeCourseSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        timeCourseSeqBasePanelLayout.setVerticalGroup(
            timeCourseSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        jLabel1.setText("Number of time points:");

        noTimePointsField.setText("5");

        jLabel2.setText("Time step:");

        timeStepField.setText("60");

        timeUnitsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seconds", "Minutes", "Hours" }));
        timeUnitsCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeUnitsComboActionPerformed(evt);
            }
        });

        popTimeCourseButton.setText("Populate time course");
        popTimeCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popTimeCourseButtonActionPerformed(evt);
            }
        });

        liquidDispensionWellsTableLable.setText("Liquid dispension well(s):");

        liquidDispensionButton.setText("Update");
        liquidDispensionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                liquidDispensionButtonActionPerformed(evt);
            }
        });

        syringeTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout syringeTablePanelLayout = new javax.swing.GroupLayout(syringeTablePanel);
        syringeTablePanel.setLayout(syringeTablePanelLayout);
        syringeTablePanelLayout.setHorizontalGroup(
            syringeTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 55, Short.MAX_VALUE)
        );
        syringeTablePanelLayout.setVerticalGroup(
            syringeTablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeCourseSeqBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(popTimeCourseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(noTimePointsField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(timeStepField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(timeUnitsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(liquidDispensionWellsTableLable, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(liquidDispensionButton, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(syringeTablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeCourseSeqBasePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(liquidDispensionWellsTableLable)
                            .addComponent(jLabel1)
                            .addComponent(noTimePointsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(liquidDispensionButton)
                            .addComponent(jLabel2)
                            .addComponent(timeStepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeUnitsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(popTimeCourseButton)
                        .addGap(0, 28, Short.MAX_VALUE))
                    .addComponent(syringeTablePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void timeUnitsComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeUnitsComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeUnitsComboActionPerformed

    
    private void popTimeCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popTimeCourseButtonActionPerformed
        
        int noTimePoints = Integer.parseInt(noTimePointsField.getText());
        double tStepSecs = Double.parseDouble(timeStepField.getText());
        String units = (String) timeUnitsCombo.getSelectedItem();
        
        if (units == "Minutes")
            tStepSecs = tStepSecs * 60;
        else if (units == "Hours")
            tStepSecs = tStepSecs * 60 * 60;
        
        tableModel_.clearAllData();
        
        for (int ind = 0; ind < noTimePoints; ind++){
            tableModel_.addRow(new TimePoint(ind * tStepSecs, false, syringeTableModel_.getData()));
        }
        tableModel_.fireTableDataChanged();
        System.out.println(tableModel_.getData());
    }//GEN-LAST:event_popTimeCourseButtonActionPerformed

    private void liquidDispensionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_liquidDispensionButtonActionPerformed
        syringeTableModel_.validateList();
        ArrayList<TimePoint> data=tableModel_.getData();
        for (TimePoint tp : data) {
            System.out.println(tp);
            tp.setLdWells(syringeTableModel_.getData());
        }
        tableModel_.addWholeData(data);
    }//GEN-LAST:event_liquidDispensionButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton liquidDispensionButton;
    private javax.swing.JLabel liquidDispensionWellsTableLable;
    private javax.swing.JFormattedTextField noTimePointsField;
    private javax.swing.JButton popTimeCourseButton;
    private javax.swing.JPanel syringeTablePanel;
    private javax.swing.JPanel timeCourseSeqBasePanel;
    private javax.swing.JFormattedTextField timeStepField;
    private javax.swing.JComboBox timeUnitsCombo;
    // End of variables declaration//GEN-END:variables

}
