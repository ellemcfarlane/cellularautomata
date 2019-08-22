package com.elle.cellularautomata;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Provides link between CAViewer and CellAuto2D (model)
 */
public class CAController {
    private CellAuto2D ca2d;
    private CellAuto1D ca1d;
    private ArrayList<CAViewer> vs;
    private CAViewer v;
    private Timer timer;

    /**
     * Contains instances of both CellAuto2D and CAViewer and forms link between the two.
     */
    public CAController() {
        this.vs = new ArrayList<>();
        this.v = new CAViewer();
        this.ca2d = v.getCA();
        this.ca1d = v.getRCA();
        this.timer = new Timer(2, new GrowHelper());
        this.v.addGrainListener(new GrainListener());
        this.v.addRunListener(new RunListener());
        this.v.addResetListener(new ResetListener());
        this.v.addUndoListener(new UndoListener());
        this.v.addGrowListener(new GrowListener());
        this.v.addPauseListener(new PauseListener());
    }

    /** Allows CAViewer to be seen */
    public void start() {
        this.v.setVisible(true);
    }

    /**
     * When coarse-grain button is clicked in the CAViewer, the CellAuto2D is coarse-grained.
     * Does nothing if current cell/gen is fewer than 3.
     */
    class GrainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ca2d.getNumCells() < 3) {
                v.showError("Cells per gen is fewer than 3. Cannot coarse-grain further.");
                return;
            }
            ca2d.coarseGrain();
            v.setRCA(new CellAuto1D(ca2d.getRule(),ca2d.getNumCells()));
            ca1d = v.getRCA();
            v.repaint();
        }
    }

    /**
     * When run button is clicked, displays the CellAuto2D with dimensions in text-field of the CAViewer.
     */
    class RunListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rule;
            int numgens;
            int numcells;
            try {
                rule = Integer.parseInt(v.getRulesInput());
                numgens = Integer.parseInt(v.getNumGensInput());
                numcells = Integer.parseInt(v.getNumCellsInput());
            } catch (NumberFormatException iae) {
                v.showError("Numerical entry expected.");
                return;
            }
            try {
                ca2d = new CellAuto2D(rule, numgens, numcells);
            } catch (IllegalArgumentException iae) {
                v.showError("Invalid input. Expected range 0 <= rule <= 255.");
                return;
            }
            v.setCA(ca2d);
            v.setRCA(ca1d);
            v.repaint();
        }
    }

    /**
     * When reset button is clicked, resets the CellAuto2D shown in the viewer to the default (Rule 110).
     */
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.reset();
            ca2d = v.getCA();
            ca1d = v.getRCA();
        }
    }

    /**
     * When undo coarse-grain button is clicked, undoes coarse-grain to previous coarse-grain level.
     * Does nothing if there is not previous coarse-grain level.
     */
    class UndoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ca2d.getGrainlevel() > 0) {
                int grain = ca2d.getGrainlevel();
                ca2d = new CellAuto2D(ca2d.getRule(),ca2d.getNumGens(),
                         ca2d.getNumCells()*(int)Math.pow(3,grain));
                ca2d.multiGrains(grain-1);
                v.setCA(ca2d);
                v.setRCA(new CellAuto1D(ca2d.getRule(),ca2d.getNumCells()));
                ca1d = v.getRCA();
            }
        }
    }

    /** Used in CAController's timer variable to dictate rate of cell growth initiated by grow button */
    class GrowHelper implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.getRCA().generate();
            v.getDR().addCell();
        }
    }

    /** Causes cells to grow (generations to form) when grow button is clicked */
    class GrowListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.getRCA().clear();
            v.getDR().reset();
            int rule;
            int numgens;
            int numcells;
            try {
                rule = Integer.parseInt(v.getRulesInput());
                numgens = Integer.parseInt(v.getNumGensInput());
                numcells = Integer.parseInt(v.getNumCellsInput());
            } catch (NumberFormatException iae) {
                v.showError("Numerical entry expected.");
                return;
            }
            try {
                ca2d = new CellAuto2D(rule, numgens, numcells);
                ca1d = new CellAuto1D(ca2d.getRule(),ca2d.getNumCells());
            } catch (IllegalArgumentException iae) {
                v.showError("Invalid input. Expected range 0 <= rule <= 255.");
                return;
            }
            v.setCA(ca2d);
            v.setRCA(ca1d);
            timer.restart();
        }
    }

    /** When pause button is clicked, causes growing cells to stop growing, and to continue growing when clicked again*/
    class PauseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (v.isPaused()) {
                timer.start();
            }
            else {
                timer.stop();
            }
        }
    }

}