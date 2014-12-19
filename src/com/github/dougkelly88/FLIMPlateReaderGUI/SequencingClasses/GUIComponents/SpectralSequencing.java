/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.GUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.SeqAcqProps;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.VariableTest;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FilterSetup;
import com.github.dougkelly88.FLIMPlateReaderGUI.SequencingClasses.Classes.FilterTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import mmcorej.CMMCore;
import mmcorej.StrVector;
import org.micromanager.MMStudio;

/**
 *
 * @author dk1109
 */
public class SpectralSequencing extends javax.swing.JPanel {
    
    public FilterTableModel tableModel_;
    JTable filtTable_;
    HCAFLIMPluginFrame parent_;
    SeqAcqProps sap_;
    VariableTest var_;
    CMMCore core_; 
    MMStudio gui_;
    int selectedRow_;
    
    /**
     * Creates new form SpectralSequencing
     */
    public SpectralSequencing() {
        initComponents();
        setControlDefaults();
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
//                "1.0","473-561","520/35",100,initDummy()));
        tableModel_ = new FilterTableModel(new FilterSetup("", "",
                "","","","",100,1,initDummy()));
        tableModel_.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {

            }
        });
        filtTable_ = new JTable();
        filtTable_.setModel(tableModel_);
        filtTable_.setSurrendersFocusOnKeystroke(true);
        filtTable_.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane scroller = new javax.swing.JScrollPane(filtTable_);
        filtTable_.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
        filtSeqBasePanel.setLayout(new BorderLayout());
        filtSeqBasePanel.add(scroller, BorderLayout.CENTER);
        
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Delete filter config");
        deleteItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = filtTable_.getSelectedRow();
                tableModel_.removeRow(r);
            }
        });
        JMenuItem addItem = new JMenuItem("Add filter config");
        addItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = filtTable_.getSelectedRow();
//                tableModel_.insertRow(r+1, new FilterSetup("GFP","465/30",
//                        "ND 1.0","473/561","525/30",100,sap_.getDelaysArray().get(0)));
//                tableModel_.insertRow(r+1, new FilterSetup("GFP", "465/30",
//                "1.0","473-561","520/35",100,initDummy()));
                tableModel_.insertRow(r+1, new FilterSetup("", "","",
                "","","",100,1,initDummy()));
            }
        });
        JMenuItem setDels = new JMenuItem("Set delays to current values");
        setDels.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = filtTable_.getSelectedRow();
                ArrayList<Integer> newDelays = new ArrayList<Integer>();
                for (Integer delay : sap_.getDelaysArray().get(0)){
                    newDelays.add(delay);
                }
                tableModel_.setValueAt(newDelays, r, FilterTableModel.DELS_INDEX);
            }
            
        });
        
        
        popupMenu.add(addItem);
        popupMenu.add(deleteItem);
        popupMenu.add(setDels);
        filtTable_.addMouseListener(new MouseAdapter() {
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
        
        JComboBox exCombo = new JComboBox();
        populateCombos(exCombo, "SpectralFW", FilterTableModel.EX_INDEX);
        JComboBox ndCombo = new JComboBox();
        populateCombos(ndCombo, "NDFW", tableModel_.ND_INDEX);
        JComboBox diCombo = new JComboBox();
        populateCombos(diCombo, "CSUX-Dichroic Mirror", FilterTableModel.DI_INDEX);
        JComboBox emCombo = new JComboBox();
        populateCombos(emCombo, "CSUX-Filter Wheel", FilterTableModel.EM_INDEX);
        JComboBox cubeCombo = new JComboBox();
        populateCombos(cubeCombo, "FilterCube", FilterTableModel.CUBE_INDEX);
    }
    
    private void setParent(HCAFLIMPluginFrame frame){
        parent_ = frame;
    }
    
    private ArrayList<Integer> initDummy(){
        ArrayList<Integer> out = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++){
            out.add(i*1000);
        }
        return out;
    }
    
    private void populateCombos(JComboBox combo, String devLabel, final int col){
        try{
            StrVector vals = core_.getAllowedPropertyValues(devLabel, "Label");
            for (String str : vals){
                combo.addItem(str);
            }
            filtTable_.getColumnModel().getColumn(col).setCellEditor(new DefaultCellEditor(combo));
            combo.addItemListener(new ItemListener(){

                @Override
                public void itemStateChanged(ItemEvent event) {
                    
                    if (event.getStateChange() == ItemEvent.SELECTED){
                        Object item = event.getItem();
                        int r = filtTable_.getSelectedRow();
                        tableModel_.setValueAt(item, r, col);
                    }
                }
            });
        } catch (Exception ex) {
            String str = "Exception at = " + ex.getMessage();
            System.out.println(str);
        }
    }
    
    public ArrayList<FilterSetup> getFilterTable(){
        return tableModel_.getData();
    }
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filtSeqBasePanel = new javax.swing.JPanel();

        filtSeqBasePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout filtSeqBasePanelLayout = new javax.swing.GroupLayout(filtSeqBasePanel);
        filtSeqBasePanel.setLayout(filtSeqBasePanelLayout);
        filtSeqBasePanelLayout.setHorizontalGroup(
            filtSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );
        filtSeqBasePanelLayout.setVerticalGroup(
            filtSeqBasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 657, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(filtSeqBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 533, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(filtSeqBasePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filtSeqBasePanel;
    // End of variables declaration//GEN-END:variables
}
