package com.elle.cellularautomata;

import javax.swing.*;
import java.awt.*;

/**
 * Provides visualization of CellAuto2D class
 */
public class DrawCA2D extends JPanel {
    private CellAuto2D ca2d;
    private int panwidth;
    private int panheight;

    /**
     * Constructor that sets given ca2d.
     * @param ca2d CellAuto2D to paint.
     */
    public DrawCA2D(CellAuto2D ca2d) {
        this.ca2d = ca2d;
        this.setBackground(Color.WHITE);
        // user's screen size is used to keep drawing in view
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.panwidth = (screenSize.width*6)/7;
        this.panheight = screenSize.height;
    }

    /**
     * Sets current CellAuto2D to ca2d.
     * @param ca2d
     */
    public void setCA(CellAuto2D ca2d) {
        this.ca2d = ca2d;
    }

    /**
     * Defines how cellular automaton data should be drawn.
     *
     * "Living" cell is painted black, "dead" cell white.
     * When cell/gen or gen number is too large to fit on screen,
     * the display shifts to show cells further out in the x and y direction.
     *
     * After a coarse-grain the width of the cell increases for viewing clarity.
     * @param g
     */
    public void paintComponent(Graphics g) {
        // scales size of cell width-wise after coarse-graining
        int size = (int)Math.pow(3,ca2d.getGrainlevel());
        super.paintComponent(g);
        int shiftd = ca2d.getNumGens() - panheight > 0 ? ca2d.getNumGens() - panheight : 0;
        int shiftl = ca2d.getNumCells() - panwidth > 0 ? ca2d.getNumCells() - panwidth : 0;
        for (int i = shiftd; i < ca2d.getNumGens(); i++) {
            for (int j = shiftl; j < ca2d.getNumCells(); j++) {
                if (ca2d.getCells()[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j*size-shiftl, i*1-shiftd, size, 1);
            }
        }
    }
}
