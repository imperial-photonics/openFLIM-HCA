/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XYZClasses.GUIComponents;

import GeneralClasses.PlateProperties;
import GeneralClasses.SeqAcqProps;
import SequencingClasses.Classes.FOV;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author dk1109
 */
public class WellMapDrawPanel extends JPanel {
    
    // TODO implement with FOV class - ensuring consistency with parent

        int currentX_ = 128;    
        int currentY_ = 128;    
        int currentFOVw_ = 12;
        int currentFOVh_ = 16;
        FOV currentFOV_;
        FOV drawFOV_;
        int r_ = 127;
        String currentWell_ = "C4";
        String wellShape_ = "Circle";
        double wellSizeUm = 6500;
        boolean enabled_ = false;   // start up disabled, enable upon loading plate stuff. 
        PlateProperties pp_ = new PlateProperties();
        SeqAcqProps sap_;
        double conversionFactor_ = pp_.getWellSize()/r_;    //N.B. might be useful to have this as 1x2 array with x, y conv, which could be negative...
        
        WellMapDrawPanel(Object parent) {
            // set a preferred size for the custom panel.
            
            XYZPanel panel = (XYZPanel) parent;
            setPreferredSize(new Dimension(255,255));
            sap_ = SeqAcqProps.getInstance();
            currentFOV_ = panel.getCurrentFOV();
//            this.setEnabled(false);     
            addMouseListener(new MouseAdapter() {
                
                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (enabled_){
                        double[] fov = sap_.getFLIMFOVSize();
                        int drawFovV = (int) (fov[0] / conversionFactor_);
                        int drawFovH = (int) (fov[1] / conversionFactor_);
                        
                        boolean inBounds = false;
                        Point p = e.getPoint();
                        // check if point is within bounds of well...
                        if ("Round".equals(pp_.getWellShape())){
                            inBounds = ( Math.pow((p.x + drawFovH/2 - r_),2) +
                                    Math.pow((p.y + drawFovV/2 - r_),2) < r_*r_ );
                        }
                        else{
                            inBounds = ( (Math.abs(p.x - r_) < r_) && (Math.abs(p.y - r_) < r_) );
                        }
                    
                        // move stage - instrument interacting functions...
                    
                        // redraw well
                        if (inBounds){
                            currentX_ = p.x;
                            currentY_ = p.y;
                            repaint();
                        }
                    }
                }
            });
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            double[] fov = sap_.getFLIMFOVSize();
            int drawFovV = (int) (fov[0] / conversionFactor_);
            int drawFovH = (int) (fov[1] / conversionFactor_);
            
            Font font = g.getFont();
            g.setFont(font.deriveFont(Font.PLAIN, 40));
            // centre text wrt well - need to get bounding box of text...
            Rectangle2D bounds = g.getFontMetrics().getStringBounds(currentWell_, g);
            g.drawString(currentWell_, (r_+1 - (int)(bounds.getWidth()/2)), 
                    (r_+1 + (int)(bounds.getHeight()/4))); 
    
            g.setColor(Color.CYAN);
            
            if ("Square".equals(pp_.getWellShape()))
                g.drawRect(0, 0, 2*r_, 2*r_);
            else 
                g.drawOval(0, 0, (1 + 2*r_), (1 + 2*r_));
            
            g.setColor(Color.RED);
            g.drawRect((currentX_-drawFovH/2), (currentY_-drawFovV/2), 
                    drawFovH, drawFovV);
        
            g.setColor(Color.GREEN);
            // draw saved FOVs
            
        }
        
        public void setCurrentX(double stagex){
            // identify which well we're (now) in, and position relative to 
            // centre of well, converting from um to pixels
            currentX_ = (int) stagex;
            repaint();
        }
        
        public int getCurrentX(){
            return currentX_;
        }
        
        public void setCurrentY(double stagey){
            // identify which well we're (now) in, and position relative to 
            // centre of well, converting from um to pixels
            currentY_ = (int) stagey;
            repaint();
        }
        
        public int getCurrentY(){
            return currentY_;
        }
        
        public void setCurrentWell(String well){
            currentWell_ = well;
            repaint();
        }
        
        public void setEnabled(boolean enabled, PlateProperties pp){
            enabled_ = enabled;
            pp_ = pp;
            // update conversion factor based on plate...
            conversionFactor_ = pp_.getWellSize()/r_;
            repaint();
        }
        
        @Override
        public boolean isEnabled(){
            return enabled_;
        }
        
//        public class PlateActionListener implements ActionListener{
//            String s;
//
//            public PlateActionListener(String shape){
//                s = shape;
//            }
//            
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                wellShape_ = this.s;
//            }
//        
//        }
        
        public void setCurrentFOV(FOV fov){
            this.currentFOV_ = fov;
        }
        
        public FOV getCurrentFOV(){
            return this.currentFOV_;
        }

     }

