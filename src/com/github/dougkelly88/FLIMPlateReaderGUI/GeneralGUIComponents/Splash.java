/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.dougkelly88.FLIMPlateReaderGUI.GeneralGUIComponents;

/**
 *
 * @author dk1109
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash extends JFrame implements MouseListener {

    public Splash() {
        // show splash screen image
        ImageIcon icon = new ImageIcon(this.getClass().getResource("../Resources/HCAFLIM.png"));
        JLabel label = new JLabel(icon);
        getContentPane().add(label, BorderLayout.CENTER);

        // setup window correct
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        pack();

        // place it at the right position
        Dimension windowDimension = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (windowDimension.width-getWidth())/2;
        int y = (windowDimension.height-getHeight())/3;

        setLocation(x, y);

        // add the mouse listener
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        dispose();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        // do nothing
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // do nothing
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // do nothing
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // do nothing
    }
}
