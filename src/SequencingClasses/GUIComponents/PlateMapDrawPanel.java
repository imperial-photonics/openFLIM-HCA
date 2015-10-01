/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SequencingClasses.GUIComponents;

import GeneralClasses.PlateProperties;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import SequencingClasses.GUIComponents.XYSequencing;
import java.util.Arrays;

/**
 *
 * @author dk1109
 */
public class PlateMapDrawPanel extends JPanel implements ActionListener {

    XYSequencing parent_;

    Point selectionStart_ = new Point();
    Point selectionEnd_ = new Point();
    Rectangle selection_ = new Rectangle();
    boolean isSelecting_ = false;
    ArrayList<ArrayList<Boolean>> wellsSelected_;
    Color transRed = new Color(128, 0, 0, 64);

    int[] plateSizePixels = {470, 313};
    String currentWell_ = "C4";
    String wellShape_ = "Square";
    double wellSizeUm_ = 6500;
    double wellSpacingUm_ = 9000;
    boolean enabled_ = false;   // start up disabled, enable upon loading plate stuff. 
    PlateProperties pp_ = new PlateProperties();
    double conversionFactor_ = ((pp_.getPlateColumns() + 1)
            * pp_.getWellSpacingH()) / plateSizePixels[0];    //N.B. might be useful to have this as 1x2 array with x, y conv, which could be negative...

    PlateMapDrawPanel(XYSequencing parent) {
        // set a preferred size for the custom panel.
        setPreferredSize(new Dimension(470, 313));
        
        selectionStart_ = new Point();
        selectionEnd_ = new Point();
        selection_ = new Rectangle();
//        wellsSelected_ = new ArrayList<ArrayList<Boolean>>();
        
        parent_ = parent;
//            this.setEnabled(false);     
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                selectionStart_ = e.getPoint();
                isSelecting_ = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (enabled_) {
                    isSelecting_ = false;
                    selectionEnd_ = e.getPoint();
                    getSelectedWells();
                    repaint();
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

//                
//                selection_= new Rectangle(x[0], y[0], x[1], y[1]);
                selection_ = new Rectangle(
                        selectionStart_.x,
                        selectionStart_.y,
                        e.getPoint().x - selectionStart_.x,
                        e.getPoint().y - selectionStart_.y
                );
//                getSelectedWells();
                repaint();
            }

        });

        wellsSelected_ = new ArrayList<ArrayList<Boolean>>();
        for (int cols = 0; cols < pp_.getPlateColumns(); cols++) {
            ArrayList<Boolean> temp = new ArrayList<Boolean>();
            for (int rows = 0; rows < pp_.getPlateRows(); rows++) {
                temp.add(false);
            }
            wellsSelected_.add(temp);
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int wellSpaceH = (int) (pp_.getWellSpacingH() / conversionFactor_);
        int wellSpaceV = (int) (pp_.getWellSpacingV() / conversionFactor_);
        int wellSize = (int) (pp_.getWellSize() / conversionFactor_);
        Font font = g.getFont();
//          font = font.deriveFont(Font.PLAIN, 16);
        g.setFont(font.deriveFont(Font.PLAIN, 11));

        for (int cols = 0; cols < pp_.getPlateColumns(); cols++) {

            ArrayList<Boolean> temp = wellsSelected_.get(cols);

            for (int rows = 0; rows < pp_.getPlateRows(); rows++) {

                g.setColor(Color.BLACK);

                String wellString = Character.toString((char) (65 + rows)) + Integer.toString(cols + 1);
                Rectangle2D bounds = g.getFontMetrics().getStringBounds(wellString, g);

                g.drawString(wellString,
                        (int) (wellSpaceH / 2) + cols * wellSpaceH - (int) (bounds.getWidth() / 2),
                        (int) (wellSpaceV / 2) + rows * wellSpaceV + (int) (bounds.getHeight() / 2));

                g.setColor(Color.CYAN);

                int x = (int) (wellSpaceH / 2) + cols * wellSpaceH - (int) (wellSize / 2);
                int y = (int) (wellSpaceV / 2) + rows * wellSpaceV - (int) (wellSize / 2);
                if ("Square".equals(pp_.getWellShape())) {
                    if (temp.get(rows)) {
                        g.setColor(transRed);
                        g.fillRect(x + 1, y + 1, wellSize - 2, wellSize - 2);
                    }
                    g.setColor(Color.CYAN);
                    g.drawRect(x, y, wellSize, wellSize);
                } else {
                    if (temp.get(rows)) {
                        g.setColor(transRed);
                        g.fillOval(x + 1, y + 1, wellSize - 2, wellSize - 2);
                    }
                    g.setColor(Color.CYAN);
                    g.drawOval(x, y, wellSize, wellSize);
                }

            }

        }

        if (isSelecting_) {
            g.setColor(transRed);
            g.fillRect(selection_.x, selection_.y,
                    selection_.width, selection_.height);

        }

    }

    private void getSelectedWells() {
        // convert selected region into list of selected wells
        // TODO: currently selects well IFF centre of well is enclosed in
        //      selection rectangle. Update such that well is selected if 
        //      any part is enclosed. 
        int wellSpaceH = (int) (pp_.getWellSpacingH() / conversionFactor_);
        int wellSpaceV = (int) (pp_.getWellSpacingV() / conversionFactor_);
        int wellSize = (int) (pp_.getWellSize() / conversionFactor_);

        int x[] = new int[2];
        x[0] = selectionStart_.x;
        x[1] = selectionEnd_.x;
        int y[] = new int[2];
        y[0] = selectionStart_.y;
        y[1] = selectionEnd_.y;
        Arrays.sort(x);
        Arrays.sort(y);
        selection_.x = x[0];
        selection_.width = x[1] - x[0];
        selection_.y = y[0];
        selection_.height = y[1] - y[0];

        for (int cols = 0; cols < pp_.getPlateColumns(); cols++) {
            ArrayList<Boolean> temp = wellsSelected_.get(cols);
            for (int rows = 0; rows < pp_.getPlateRows(); rows++) {
                int wellYPixels = (int) (wellSpaceV / 2) + rows * wellSpaceV;
                int wellXPixels = (int) (wellSpaceH / 2) + cols * wellSpaceH;

                if (selection_.contains(wellXPixels, wellYPixels)) {

                    temp.set(rows, !(temp.get(rows)));
                }
            }
            wellsSelected_.set(cols, temp);
        }
        parent_.generateFOVs();
    }

    public void setCurrentWell(String well) {
        currentWell_ = well;
        repaint();
    }

    public void clearAllWells() {

        wellsSelected_.clear();
        for (int cols = 0; cols < pp_.getPlateColumns(); cols++) {
            ArrayList<Boolean> temp = new ArrayList<Boolean>();
            for (int rows = 0; rows < pp_.getPlateRows(); rows++) {
                temp.add(false);
            }
            wellsSelected_.add(temp);
        }

        repaint();
    }

    public void addSelectedWell(String well) {

        String wellLetter;
        int wellNumber;
        int letterIndex = 0;

        int i = 0;
        while (!Character.isDigit(well.charAt(i))) {
            i++;
        }

        wellLetter = well.substring(0, i);
        wellNumber = Integer.parseInt(well.substring(i, well.length()));
        for (int k = 0; k < i; k++) {
            letterIndex += (int) well.charAt(k) - 64;
        }
        ArrayList<Boolean> temp = wellsSelected_.get(wellNumber - 1);
        temp.set(letterIndex - 1, true);
        wellsSelected_.set(wellNumber - 1, temp);
        repaint();
    }

    public void setEnabled(boolean enabled, PlateProperties pp) {
        enabled_ = enabled;
        pp_ = pp;
        // update conversion factor based on plate...
        conversionFactor_ = ((pp_.getPlateColumns() + 1)
                * pp_.getWellSpacingH()) / plateSizePixels[0];

        wellsSelected_.clear();
        for (int cols = 0; cols < pp_.getPlateColumns(); cols++) {
            ArrayList<Boolean> temp = new ArrayList<Boolean>();
            for (int rows = 0; rows < pp_.getPlateRows(); rows++) {
                temp.add(false);
            }
            wellsSelected_.add(temp);
        }

        repaint();
    }

    @Override
    public boolean isEnabled() {
        return enabled_;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
