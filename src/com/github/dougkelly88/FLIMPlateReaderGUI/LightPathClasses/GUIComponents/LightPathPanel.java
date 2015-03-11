/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.GUIComponents;

import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Arduino;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.SeqAcqProps;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.VariableTest;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.SliderControl;
import com.github.dougkelly88.FLIMPlateReaderGUI.LightPathClasses.Classes.CurrentLightPath;
import com.github.dougkelly88.FLIMPlateReaderGUI.XYZClasses.GUIComponents.XYZPanel;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import mmcorej.CMMCore;
import mmcorej.DeviceType;
import mmcorej.StrVector;
import org.micromanager.MMStudio;
import org.micromanager.api.events.PropertyChangedEvent;

/**
 *
 * @author dk1109
 */
public class LightPathPanel extends javax.swing.JPanel {

    MMStudio gui_;
    CMMCore core_;
    PropertyChangedEvent event_;
    private SeqAcqProps sap_;
    private VariableTest var_;
    HCAFLIMPluginFrame parent_;
    SliderControl powerSlider_;
    CurrentLightPath currentLightPath_;
    private Arduino arduino_;
    private XYZPanel xYZPanel_;
    private static final LightPathPanel fINSTANCE =  new LightPathPanel();
    // TODO: replace var_ stuff with currentLightPath_
//    private SequencedAcquisitionProperties sap_;
    // TODO: generate a method that checks for spectral overlap between
    // filters based on central wavelength and bandpass. This method would 
    // return a false if there was (significant?) spectral overlap between
    // excitation and emission, risking overloading the HRI, and would 
    // be used to prevent such a filter change occuring without the shutter
    // first being closed. 

    /**
     * Creates new form FLIMControls
     */
    public LightPathPanel() {
        initComponents();
        gui_ = MMStudio.getInstance();
        sap_ = SeqAcqProps.getInstance();
        var_ = VariableTest.getInstance();
        arduino_ = Arduino.getInstance();
        xYZPanel_ = XYZPanel.getInstance();
        try {
            gui_.registerForEvents(this);
            core_ = gui_.getCore();
        } catch (Exception e) {
            //gui_.showMessage("Error in FLIMPanel constructor: " + e.getMessage());
        }
        currentLightPath_ = new CurrentLightPath();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        excitationSource = new javax.swing.JPanel();
        laserToggle = new javax.swing.JToggleButton();
        outputPowerPanel = new javax.swing.JPanel();
        laserRepRateLabel = new javax.swing.JLabel();
        laserTemperatureLabel = new javax.swing.JLabel();
        laserSerialNumberLabel = new javax.swing.JLabel();
        laserRunTimeLabel = new javax.swing.JLabel();
        ledToggle = new javax.swing.JToggleButton();
        Filters = new javax.swing.JPanel();
        ndFWComboBox = new javax.swing.JComboBox();
        excitationComboBox = new javax.swing.JComboBox();
        dichroicComboBox = new javax.swing.JComboBox();
        emissionComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Olympus = new javax.swing.JPanel();
        objectiveComboBox = new javax.swing.JComboBox();
        filterCubeComboBox = new javax.swing.JComboBox();
        switchPortComboBox = new javax.swing.JComboBox();
        ObjectiveLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        excitationSource.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Excitation source"));

        laserToggle.setText("Turn laser ON");
        laserToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laserToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout outputPowerPanelLayout = new javax.swing.GroupLayout(outputPowerPanel);
        outputPowerPanel.setLayout(outputPowerPanelLayout);
        outputPowerPanelLayout.setHorizontalGroup(
            outputPowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        outputPowerPanelLayout.setVerticalGroup(
            outputPowerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );

        laserRepRateLabel.setText("Laser repetition rate (MHz):");

        laserTemperatureLabel.setText("Laser temperature (oC): ");

        laserSerialNumberLabel.setText("Laser serial number:");

        laserRunTimeLabel.setText("Laser run time (mins):");

        ledToggle.setText("Turn brightfield light ON");
        ledToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ledToggleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout excitationSourceLayout = new javax.swing.GroupLayout(excitationSource);
        excitationSource.setLayout(excitationSourceLayout);
        excitationSourceLayout.setHorizontalGroup(
            excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(excitationSourceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ledToggle, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(laserToggle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputPowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(laserTemperatureLabel)
                    .addComponent(laserRepRateLabel)
                    .addComponent(laserSerialNumberLabel)
                    .addComponent(laserRunTimeLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        excitationSourceLayout.setVerticalGroup(
            excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(excitationSourceLayout.createSequentialGroup()
                .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(excitationSourceLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(excitationSourceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(excitationSourceLayout.createSequentialGroup()
                                .addComponent(laserRepRateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(laserTemperatureLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(laserSerialNumberLabel))
                            .addComponent(outputPowerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(laserRunTimeLabel))
                    .addGroup(excitationSourceLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(laserToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ledToggle)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Filters.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Filters"));

        ndFWComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ndFWComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ndFWComboBoxActionPerformed(evt);
            }
        });

        excitationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        excitationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excitationComboBoxActionPerformed(evt);
            }
        });

        dichroicComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dichroicComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dichroicComboBoxActionPerformed(evt);
            }
        });

        emissionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        emissionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emissionComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setText("Neutral density");

        jLabel2.setText("Excitation");

        jLabel3.setText("Dichroic");

        jLabel4.setText("Emission");

        javax.swing.GroupLayout FiltersLayout = new javax.swing.GroupLayout(Filters);
        Filters.setLayout(FiltersLayout);
        FiltersLayout.setHorizontalGroup(
            FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FiltersLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(ndFWComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(excitationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dichroicComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(emissionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        FiltersLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dichroicComboBox, emissionComboBox, excitationComboBox, ndFWComboBox});

        FiltersLayout.setVerticalGroup(
            FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FiltersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(FiltersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ndFWComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(excitationComboBox)
                    .addComponent(dichroicComboBox)
                    .addComponent(emissionComboBox))
                .addContainerGap())
        );

        Olympus.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Olympus"));

        objectiveComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        objectiveComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                objectiveComboBoxActionPerformed(evt);
            }
        });

        filterCubeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        filterCubeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterCubeComboBoxActionPerformed(evt);
            }
        });

        switchPortComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        switchPortComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchPortComboBoxActionPerformed(evt);
            }
        });

        ObjectiveLabel.setText("Objective");

        jLabel6.setText("Filter Cube");

        jLabel7.setText("Output port");

        javax.swing.GroupLayout OlympusLayout = new javax.swing.GroupLayout(Olympus);
        Olympus.setLayout(OlympusLayout);
        OlympusLayout.setHorizontalGroup(
            OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OlympusLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ObjectiveLabel)
                    .addComponent(objectiveComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 29, Short.MAX_VALUE)
                .addGroup(OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(filterCubeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 29, Short.MAX_VALUE)
                .addGroup(OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(switchPortComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        OlympusLayout.setVerticalGroup(
            OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OlympusLayout.createSequentialGroup()
                .addGroup(OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ObjectiveLabel)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(OlympusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(objectiveComboBox)
                    .addComponent(filterCubeComboBox)
                    .addComponent(switchPortComboBox)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Olympus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Filters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(excitationSource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(excitationSource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Filters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Olympus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void laserToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laserToggleActionPerformed
            
        if (laserToggle.isSelected()) {
            laserToggle.setText("Turn laser OFF");
            try{
                boolean abort=arduino_.checkSafety();;
                if (abort==false){
                    arduino_.setMode("shutter");
                    arduino_.setDigitalOutHigh();
                }
                } catch (Exception ex) {
            Logger.getLogger(HCAFLIMPluginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            laserToggle.setText("Turn laser ON");
            try {
                arduino_.setDigitalOutLow();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }//GEN-LAST:event_laserToggleActionPerformed

    private void dichroicComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dichroicComboBoxActionPerformed
        setByLabel(dichroicComboBox, "CSUX-Dichroic Mirror");
        currentLightPath_.setDichroicLabel((String) dichroicComboBox.getSelectedItem());
        var_.DichroicComboBoxSelectedItem = (String) dichroicComboBox.getSelectedItem();
    }//GEN-LAST:event_dichroicComboBoxActionPerformed

    private void ndFWComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ndFWComboBoxActionPerformed
        setByLabel(ndFWComboBox, "NDFW");
        currentLightPath_.setNdFilterLabel((String) ndFWComboBox.getSelectedItem());
        var_.NDFWComboBoxSelectedItem = (String) ndFWComboBox.getSelectedItem();
    }//GEN-LAST:event_ndFWComboBoxActionPerformed

    private void excitationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excitationComboBoxActionPerformed
        setByLabel(excitationComboBox, "SpectralFW");
        currentLightPath_.setExFilterLabel((String) excitationComboBox.getSelectedItem());
        var_.ExcitationComboBoxSelectedItem = (String) excitationComboBox.getSelectedItem();
    }//GEN-LAST:event_excitationComboBoxActionPerformed

    private void emissionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emissionComboBoxActionPerformed
        setByLabel(emissionComboBox, "CSUX-Filter Wheel");
        currentLightPath_.setEmFilterLabel((String) emissionComboBox.getSelectedItem());
        var_.EmissionComboBoxSelectedItem = (String) emissionComboBox.getSelectedItem();
    }//GEN-LAST:event_emissionComboBoxActionPerformed

    private void objectiveComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_objectiveComboBoxActionPerformed
        setByLabel(objectiveComboBox, "Objective");
        currentLightPath_.setObjectiveLabel((String) objectiveComboBox.getSelectedItem());
        var_.ObjectiveComboBoxSelectedItem = (String) objectiveComboBox.getSelectedItem();
        double magnification = 1;
//        if (!(objectiveComboBox.getSelectedItem() == null))
//            magnification = getMag((String) objectiveComboBox.getSelectedItem());
//        parent_.currentFOV_.setMagnification(magnification);
    }//GEN-LAST:event_objectiveComboBoxActionPerformed

    private double getMag(String desc) {
        // TODO: CHECK THIS WORKS!
        // TODO: make this more general/robust...
        // assumes no "x"s other than as magnification...
        double mag = 0;
        double multiplier = 1;
        int index = desc.indexOf("x") - 1;
        if (index > 0) {
            boolean isNum = isNumeric(Character.toString(desc.charAt(index)));
            while (isNum) {
                mag += multiplier * Double.parseDouble(Character.toString(desc.charAt(index)));
                index--;
                multiplier = multiplier * 10;
            }
        }
        return mag;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        // ref: http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-a-numeric-type-in-java
    }

    private void filterCubeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterCubeComboBoxActionPerformed
        setByLabel(filterCubeComboBox, "FilterCube");
        currentLightPath_.setFilterCubeLabel((String) filterCubeComboBox.getSelectedItem());
        var_.FilterCubeComboBoxSelectedItem = (String) filterCubeComboBox.getSelectedItem();
    }//GEN-LAST:event_filterCubeComboBoxActionPerformed

    private void switchPortComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchPortComboBoxActionPerformed
        setByLabel(switchPortComboBox, "LightPathPrism");
        currentLightPath_.setPortLabel((String) switchPortComboBox.getSelectedItem());
        var_.SwitchPortComboBoxSelectedItem = (String) switchPortComboBox.getSelectedItem();
    }//GEN-LAST:event_switchPortComboBoxActionPerformed

    private void ledToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ledToggleActionPerformed
        
        if (ledToggle.isSelected()) {
            try {
                core_.setProperty("LightPathPrism", "Label", (String) switchPortComboBox.getSelectedItem());
            } catch (Exception ex) {
                System.out.println("Not setting property for device LightPathPrism because combo hasn't yet been populated (setByLabel method)");
            }
            arduino_.setMode("led");
            ledToggle.setText("Turn brightfield light OFF");
            try{
                boolean abort=arduino_.checkSafety();
                if (abort==false){
                    arduino_.setDigitalOutHigh();
                }
                } catch (Exception ex) {
            Logger.getLogger(HCAFLIMPluginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else { 
            arduino_.setMode("shutter");
            ledToggle.setText("Turn brightfield light ON");
            try {
                boolean abort=arduino_.checkSafety();
                arduino_.setDigitalOutLow();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }   
       
    }//GEN-LAST:event_ledToggleActionPerformed

    public void setByLabel(JComboBox combo, String device) {
        try {
            String setval = (String) combo.getSelectedItem();
            // only send command if combo has been properly populated
            if (setval != null) {
                core_.setProperty(device, "Label", setval);
            } else {
                System.out.println("Not setting property for device " + device
                        + " because combo hasn't yet been populated (setByLabel method)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setLoadedSoftwareValues() {
        // searching label of prperty in SoftwareConfig and set values in LightPathControls
        dichroicComboBox.setSelectedItem(var_.findLabelOfProperty("Dichroic"));
        emissionComboBox.setSelectedItem(var_.findLabelOfProperty("Emission"));
        ndFWComboBox.setSelectedItem(var_.findLabelOfProperty("Neutral Density Filter"));
        excitationComboBox.setSelectedItem(var_.findLabelOfProperty("Excitation"));
        objectiveComboBox.setSelectedItem(var_.findLabelOfProperty("Objective"));
        filterCubeComboBox.setSelectedItem(var_.findLabelOfProperty("Filter Cube"));
        switchPortComboBox.setSelectedItem(var_.findLabelOfProperty("Light Path Prism"));
    }

    public void setLoadedHardwareValues() {
        //Intensity (NDFW) Load
        populateComboBoxes(ndFWComboBox, "NDFW");

        //Excitation (SpectralFW) Load
        populateComboBoxes(excitationComboBox, "SpectralFW");

        //Dichroic Load
        populateComboBoxes(dichroicComboBox, "CSUX-Dichroic Mirror");

        //Emission Load
        populateComboBoxes(emissionComboBox, "CSUX-Filter Wheel");

        //FilterCube Load
        populateComboBoxes(filterCubeComboBox, "FilterCube");

        //Objective Load
        populateComboBoxes(objectiveComboBox, "Objective");

        //SwitchPort Load
        populateComboBoxes(switchPortComboBox, "LightPathPrism");

        // set defaults
        setDefaultLightPath();

        // if Fianium present, load up these controls:
        try {
            String sn = core_.getProperty("FianiumSC", "LaserSerialNumber");
            String temp = "TBA";    // TODO: implement temperature monitor in DeviceAdapter!
            String rr = core_.getProperty("FianiumSC", "RepRate");
            String ot = core_.getProperty("FianiumSC", "OperatingTime(Mins)");
            laserSerialNumberLabel.setText("Laser serial number: " + sn);
            laserRepRateLabel.setText("Laser repetition rate: " + rr + " MHz");
            laserRunTimeLabel.setText("Laser run time: " + ot + " mins");
            laserTemperatureLabel.setText("Laser temperature: " + temp + "\u00b0C");

            powerSlider_ = new SliderControl("Laser output power (%): ", 0, 100, 0);
            outputPowerPanel.setLayout(new BorderLayout());
            outputPowerPanel.add(powerSlider_, BorderLayout.SOUTH);
            powerSlider_.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

                @Override
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    try {
                        if (laserToggle.isSelected()) {
                            int pcoutput = (int) powerSlider_.getValue().intValue();
                            core_.setProperty("FianiumSC", "Power output (%)", pcoutput);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });

            outputPowerPanel.revalidate();
            outputPowerPanel.repaint();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDefaultLightPath() {
        // make configurable? for now, use indices
        // rather than labels to avoid breaking things...
        // REMEMBER THAT COMBO BOXES END UP ORDERED!
        if (dichroicComboBox.isEnabled()) {
            dichroicComboBox.setSelectedIndex(1);
        }
        if (emissionComboBox.isEnabled()) {
            emissionComboBox.setSelectedIndex(2);
        }
        if (ndFWComboBox.isEnabled()) {
            ndFWComboBox.setSelectedIndex(5);
        }
        if (excitationComboBox.isEnabled()) {
            excitationComboBox.setSelectedIndex(1);
        }
        if (objectiveComboBox.isEnabled()) {
            objectiveComboBox.setSelectedIndex(1);
        }
        if (filterCubeComboBox.isEnabled()) {
            filterCubeComboBox.setSelectedIndex(1);
        }
        if (switchPortComboBox.isEnabled()) {
            switchPortComboBox.setSelectedIndex(1);
        }
    }

    private void populateComboBoxes(JComboBox combo, String device) {
        StrVector vals = new StrVector();
        try {
            vals = core_.getAllowedPropertyValues(device, "Label");
            System.out.println("Combo = " + combo);
            System.out.println("Item = " + (String) combo.getSelectedItem());

            combo.removeAllItems();
            for (String str : vals) {
                combo.addItem(str);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // if the combo can't be populated, chances are
            // the device isn't included in the .cfg, so disable 
            // the control. 
            // TODO: check whether this works if the device is in the .cfg, but
            // is not currently attached/accessible. 
            combo.setEnabled(false);
        }
    }

    public void setParent(Object o) {
        parent_ = (HCAFLIMPluginFrame) o;
    }
    
    
    public Object getFrameParent(){
        return parent_;
    }

    public CurrentLightPath getCurrentLightPath() {
        return this.currentLightPath_;
    }
    
    public static LightPathPanel getInstance() {
            return fINSTANCE;
    }
    
    public void updatePanel(){
         // do something when LightPathPanel is selected
        objectiveComboBox.setSelectedItem(var_.ObjectiveComboBoxSelectedItem);
    }
    
    public void setLaserToggleFalse(){
        laserToggle.setSelected(false);
    }
    
    public void setLaserToggleText(String text){
        laserToggle.setText(text);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Filters;
    private javax.swing.JLabel ObjectiveLabel;
    private javax.swing.JPanel Olympus;
    private javax.swing.JComboBox dichroicComboBox;
    private javax.swing.JComboBox emissionComboBox;
    private javax.swing.JComboBox excitationComboBox;
    private javax.swing.JPanel excitationSource;
    private javax.swing.JComboBox filterCubeComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel laserRepRateLabel;
    private javax.swing.JLabel laserRunTimeLabel;
    private javax.swing.JLabel laserSerialNumberLabel;
    private javax.swing.JLabel laserTemperatureLabel;
    private javax.swing.JToggleButton laserToggle;
    private javax.swing.JToggleButton ledToggle;
    private javax.swing.JComboBox ndFWComboBox;
    public javax.swing.JComboBox objectiveComboBox;
    private javax.swing.JPanel outputPowerPanel;
    private javax.swing.JComboBox switchPortComboBox;
    // End of variables declaration//GEN-END:variables

}
