package com.ellemcfarlane.cellauto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

/**
 * Provides visualization of CellAuto1D class
 */
public class DrawCA1D extends JPanel implements ActionListener {
    private CellAuto1D ca1d;
    private int panwidth;
    private int panheight;
    private BufferedImage oldim;
    private BufferedImage newim;

    /**
     * Constructor that sets given ca2d.
     * @param ca1d CellAuto1D to paint.
     */
    public DrawCA1D(CellAuto1D ca1d) {
        this.ca1d = ca1d;
        this.setBackground(Color.WHITE);
        // user's screen size is used to keep drawing in view
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.panwidth = (screenSize.width*6)/7;
        this.panheight = (screenSize.height*90)/100;
        this.oldim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
        this.newim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Sets current CellAuto1D to ca1d.
     * @param ca1d
     */
    public void setRCA(CellAuto1D ca1d) {
        this.ca1d = ca1d;
    }

    /**
     * Defines how cellular automaton data should be drawn.
     * "Living" cell is painted black, "dead" cell white.
     * When cell/gen or gen number is too large to fit on screen,
     * the display shifts to show cells further out in the x and y direction.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(oldim, 0, 0, this);
    }

    /** Calls addCell function to draw each generation of cells */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.ca1d.generate();
        this.addCell();
    }

    /**
     * Draws each generation as cell generation increases.
     * Used by actionPerformed method.
     */
    public void addCell() {
        int size = 1;
        int shiftl = ca1d.getNumCells() - panwidth > 0 ? ca1d.getNumCells() - panwidth : 0;
        // when cell generation equals panel height, start shifting view upward to see new cells
        if (ca1d.getGen() - oldim.getHeight() >= 0) {
            Graphics2D g2 = newim.createGraphics();
            g2.drawImage(oldim.getSubimage
                    (0, 1, oldim.getWidth(), oldim.getHeight()-1),0, 0,Color.BLUE,null);
            for (int i = 0; i < ca1d.getNumCells(); i++) {
                if (ca1d.getCells()[i] == 1) {
                    g2.setColor(Color.BLACK);
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.fillRect((i * size)-shiftl, oldim.getHeight()-1, size, size);
            }
            g2.dispose();
            BufferedImage temp = newim;
            newim = oldim;
            oldim = temp;
        }
        else {
            Graphics g = oldim.createGraphics();
            for (int i = 0; i < ca1d.getNumCells(); i++) {
                if (ca1d.getCells()[i] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((i * size)-shiftl, ca1d.getGen() * size, size, size);

            }
            g.dispose();
        }
        repaint();
    }

    /** Clears BufferedImage view to white */
    public void reset() {
        Graphics g = oldim.getGraphics();
        this.setBackground(Color.WHITE);
        g.fillRect(0,0,panwidth,panheight);
        this.oldim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
        this.newim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
        g.dispose();
    }
}
