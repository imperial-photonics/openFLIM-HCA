/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dougkelly88.FLIMPlateReaderGUI.PrefindClasses.GUIComponents;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.Prefind;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents.HCAFLIMPluginFrame;
import com.github.dougkelly88.FLIMPlateReaderGUI.GeneralClasses.GeneralUtilities;
// Enable next 2 lines if needed for doing prefind image processing here.
//import ij.ImagePlus;
//import ij.process.ImageProcessor;
import ij.io.DirectoryChooser;


/**
 *
 * @author Sunil
 */
public class PrefindPanel extends javax.swing.JPanel {

    private HCAFLIMPluginFrame parent_;
    private GeneralUtilities GenUtils_;
    public Prefind prefind_;
    private String NumberRegex = "\\d+"; // Should be just the digits with this...
    //public ImageProcessor prefindPanelIP;
    //public ImagePlus rawPrefindImage;
    //public ImagePlus processedPrefindImage;
    
    /**
     * Creates new form PrefindPanel
     */
    public PrefindPanel() {
        initComponents();
    }
    
    public int SanityCheckInt(String input_text){
        int retval = (int) Math.round(SanityCheckDouble(input_text));
        return retval;
    }
    
    public double SanityCheckDouble(String input_text){
        String NumberRegex = "[^0-9]"; //The ^ at the start means "anything except whatever's next"
        String OriginalString=input_text;
        String[] SplitArray = OriginalString.split("\\."); 
        String Output="";
        //If do see a decimal point, assume it's a double, and say that only the last decimal point counts
        if(SplitArray.length!=0){
            for (int i=0;i<SplitArray.length;i++){
                if(i==(SplitArray.length-1)&&i>0){
                    Output=Output+".";
                }
                String RegexedString = SplitArray[i].replaceAll(NumberRegex, "");
                //System.out.println(RegexedString);
                Output=Output+RegexedString;
            }
        } else { //If we don't see a decimal point, assume it's an int
            Output=OriginalString.replaceAll(NumberRegex, "");
        }
        int res;
        res=Output.length();
        //OK, we should have a sensible string now (excluding if just a decimal)
        //but could still be a double if there's no digits before the decimal
        if(Output.equals(".")){
            Output="0";
        }
        Double retval=Double.parseDouble(Output);
        //System.out.println(retval);
        return retval;
    }
    

    public void setParent(Object o) {
        parent_ = (HCAFLIMPluginFrame) o;
    }
    
    public void UpdatePrefindPanel(){
        // First find out how many variables there are to be updated...
        
        // We know we have 4 sliders, so...
        
        boolean update_error=false;
        
        for(int i=1; i<=4; i++){
            if(!update_error){
                String thisvarname = parent_.prefind_.getvarname(i);
                if (thisvarname.equals("")){//Remember .equals, not == for strings?
                    update_error=true;
                }
                double[] thisvarlims = {0.0,0.0};
                Double min = thisvarlims[0];
                Double max = thisvarlims[1];
                String thisvarmin = min.toString();
                String thisvarmax = max.toString();
                int thisvarminint = min.intValue();
                int thisvarmaxint = max.intValue();  
                if(thisvarname.equals("")){
                    //Either the variable name is blank, there aren't enough variables described, or there aren't enough parameters for this one...
                    thisvarname="ERROR";
                } else {
                    //All fine - carry on!
                    thisvarlims = parent_.prefind_.getvarlimits(i);
                    min = thisvarlims[0];
                    max = thisvarlims[1];
                    thisvarmin = min.toString();
                    thisvarmax = max.toString();
                    thisvarminint = min.intValue();
                    thisvarmaxint = max.intValue();                
                }
                switch(i) {
                    case 1:
                        this.VarMin1.setText(thisvarmin);
                        this.VarMax1.setText(thisvarmax);
                        this.VarSlider1.setMinimum(thisvarminint);
                        this.VarSlider1.setMaximum(thisvarmaxint);
                        this.varLabel1.setText(thisvarname);
                        break;
                    case 2:
                        this.VarMin2.setText(thisvarmin);
                        this.VarMax2.setText(thisvarmax);
                        this.VarSlider2.setMinimum(thisvarminint);
                        this.VarSlider2.setMaximum(thisvarmaxint);
                        this.varLabel2.setText(thisvarname);
                        break;
                    case 3:   
                        this.VarMin3.setText(thisvarmin);
                        this.VarMax3.setText(thisvarmax);
                        this.VarSlider3.setMinimum(thisvarminint);
                        this.VarSlider3.setMaximum(thisvarmaxint);                    
                        this.varLabel3.setText(thisvarname);
                        break;
                    case 4: 
                        this.VarMin4.setText(thisvarmin);
                        this.VarMax4.setText(thisvarmax);
                        this.VarSlider4.setMinimum(thisvarminint);
                        this.VarSlider4.setMaximum(thisvarmaxint);                    
                        this.varLabel4.setText(thisvarname);
                        break;
                }
            }
        }
       
        //System.out.println(thisvarname+" -  Min: "+thisvarmin+"  Max: "+thisvarmax);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        VarSlider1 = new javax.swing.JSlider();
        VarSlider2 = new javax.swing.JSlider();
        VarSlider3 = new javax.swing.JSlider();
        VarSlider4 = new javax.swing.JSlider();
        VarMin1 = new javax.swing.JLabel();
        VarMin2 = new javax.swing.JLabel();
        VarMin3 = new javax.swing.JLabel();
        VarMin4 = new javax.swing.JLabel();
        VarMax1 = new javax.swing.JLabel();
        VarMax2 = new javax.swing.JLabel();
        VarMax3 = new javax.swing.JLabel();
        VarMax4 = new javax.swing.JLabel();
        varTextbox1 = new javax.swing.JTextField();
        varTextbox2 = new javax.swing.JTextField();
        varTextbox3 = new javax.swing.JTextField();
        varTextbox4 = new javax.swing.JTextField();
        varLabel1 = new javax.swing.JLabel();
        varLabel2 = new javax.swing.JLabel();
        varLabel3 = new javax.swing.JLabel();
        varLabel4 = new javax.swing.JLabel();

        VarSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                VarSlider1StateChanged(evt);
            }
        });

        VarSlider2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                VarSlider2StateChanged(evt);
            }
        });
        VarSlider2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                VarSlider2PropertyChange(evt);
            }
        });

        VarSlider3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                VarSlider3StateChanged(evt);
            }
        });

        VarSlider4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                VarSlider4StateChanged(evt);
            }
        });

        VarMin1.setText("UNSET");

        VarMin2.setText("UNSET");

        VarMin3.setText("UNSET");

        VarMin4.setText("UNSET");

        VarMax1.setText("UNSET");

        VarMax2.setText("UNSET");

        VarMax3.setText("UNSET");

        VarMax4.setText("UNSET");

        varTextbox1.setText("######");
        varTextbox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varTextbox1ActionPerformed(evt);
            }
        });

        varTextbox2.setText("######");
        varTextbox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varTextbox2ActionPerformed(evt);
            }
        });

        varTextbox3.setText("######");
        varTextbox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varTextbox3ActionPerformed(evt);
            }
        });

        varTextbox4.setText("######");
        varTextbox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                varTextbox4ActionPerformed(evt);
            }
        });

        varLabel1.setText("UNNAMED VAR1");

        varLabel2.setText("UNNAMED VAR2");

        varLabel3.setText("UNNAMED VAR3");

        varLabel4.setText("UNNAMED VAR4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(VarMin1, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(VarMin2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VarMin3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VarMin4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VarSlider4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VarMax4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VarSlider2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VarMax2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VarSlider3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VarMax3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(VarSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VarMax1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(varTextbox4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(varTextbox1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(varTextbox2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(varTextbox3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(varLabel3)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(VarMax1)
                            .addComponent(VarMin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VarSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(varTextbox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(varLabel2))
                            .addComponent(VarMax2)
                            .addComponent(VarMin2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VarSlider2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(VarMax3)
                                .addComponent(varTextbox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(varLabel3))
                            .addComponent(VarMin3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VarSlider3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(VarSlider4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(VarMin4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(VarMax4)
                                .addComponent(varTextbox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(varLabel4)))
                        .addGap(152, 152, 152))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(varTextbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(varLabel1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void varTextbox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varTextbox1ActionPerformed
        VarSlider1.setValue(this.SanityCheckInt(varTextbox1.getText()));
    }//GEN-LAST:event_varTextbox1ActionPerformed

    private void varTextbox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varTextbox2ActionPerformed
        VarSlider2.setValue(this.SanityCheckInt(varTextbox2.getText()));
    }//GEN-LAST:event_varTextbox2ActionPerformed

    private void varTextbox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varTextbox3ActionPerformed
        VarSlider3.setValue(this.SanityCheckInt(varTextbox3.getText()));
    }//GEN-LAST:event_varTextbox3ActionPerformed

    private void varTextbox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_varTextbox4ActionPerformed
        VarSlider4.setValue(this.SanityCheckInt(varTextbox4.getText()));
    }//GEN-LAST:event_varTextbox4ActionPerformed

    private void VarSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_VarSlider1StateChanged
        Integer intermedval = VarSlider1.getValue();
        varTextbox1.setText(intermedval.toString());
    }//GEN-LAST:event_VarSlider1StateChanged

    private void VarSlider2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_VarSlider2StateChanged
        Integer intermedval = VarSlider2.getValue();
        varTextbox2.setText(intermedval.toString());
    }//GEN-LAST:event_VarSlider2StateChanged

    private void VarSlider3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_VarSlider3StateChanged
        Integer intermedval = VarSlider3.getValue();
        varTextbox3.setText(intermedval.toString());
    }//GEN-LAST:event_VarSlider3StateChanged

    private void VarSlider4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_VarSlider4StateChanged
        Integer intermedval = VarSlider4.getValue();
        varTextbox4.setText(intermedval.toString());
    }//GEN-LAST:event_VarSlider4StateChanged

    private void VarSlider2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_VarSlider2PropertyChange
        Integer intermedval = VarSlider2.getValue();
        varTextbox2.setText(intermedval.toString());
    }//GEN-LAST:event_VarSlider2PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel VarMax1;
    private javax.swing.JLabel VarMax2;
    private javax.swing.JLabel VarMax3;
    private javax.swing.JLabel VarMax4;
    private javax.swing.JLabel VarMin1;
    private javax.swing.JLabel VarMin2;
    private javax.swing.JLabel VarMin3;
    private javax.swing.JLabel VarMin4;
    private javax.swing.JSlider VarSlider1;
    private javax.swing.JSlider VarSlider2;
    private javax.swing.JSlider VarSlider3;
    private javax.swing.JSlider VarSlider4;
    private javax.swing.JLabel varLabel1;
    private javax.swing.JLabel varLabel2;
    private javax.swing.JLabel varLabel3;
    private javax.swing.JLabel varLabel4;
    private javax.swing.JTextField varTextbox1;
    private javax.swing.JTextField varTextbox2;
    private javax.swing.JTextField varTextbox3;
    private javax.swing.JTextField varTextbox4;
    // End of variables declaration//GEN-END:variables
}
