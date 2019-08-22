
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Provides link between CAViewer and CellAuto (model)
 */
public class CAController {
    private CellAuto ca;
    private RetroCA rca;
    private ArrayList<CAViewer> vs;
    private CAViewer v;
    private Timer timer;

    /**
     * Contains instances of both CellAuto and CAViewer and forms link between the two.
     */
    public CAController() {
        this.vs = new ArrayList<>();
        this.v = new CAViewer();
        this.ca = v.getCA();
        this.rca = v.getRCA();
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
     * When coarse-grain button is clicked in the CAViewer, the CellAuto is coarse-grained.
     * Does nothing if current cell/gen is fewer than 3.
     */
    class GrainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ca.getNumCells() < 3) {
                v.showError("Cells per gen is fewer than 3. Cannot coarse-grain further.");
                return;
            }
            ca.coarseGrain();
            v.setRCA(new RetroCA(ca.getRule(),ca.getNumCells()));
            rca = v.getRCA();
            v.repaint();
        }
    }

    /**
     * When run button is clicked, displays the CellAuto with dimensions in text-field of the CAViewer.
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
                ca = new CellAuto(rule, numgens, numcells);
            } catch (IllegalArgumentException iae) {
                v.showError("Invalid input. Expected range 0 <= rule <= 255.");
                return;
            }
            v.setCA(ca);
            v.setRCA(rca);
            v.repaint();
        }
    }

    /**
     * When reset button is clicked, resets the CellAuto shown in the viewer to the default (Rule 110).
     */
    class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            v.reset();
            ca = v.getCA();
            rca = v.getRCA();
        }
    }

    /**
     * When undo coarse-grain button is clicked, undoes coarse-grain to previous coarse-grain level.
     * Does nothing if there is not previous coarse-grain level.
     */
    class UndoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ca.getGrainlevel() > 0) {
                int grain = ca.getGrainlevel();
                ca = new CellAuto(ca.getRule(),ca.getNumGens(),
                         ca.getNumCells()*(int)Math.pow(3,grain));
                ca.multiGrains(grain-1);
                v.setCA(ca);
                v.setRCA(new RetroCA(ca.getRule(),ca.getNumCells()));
                rca = v.getRCA();
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
                ca = new CellAuto(rule, numgens, numcells);
                rca = new RetroCA(ca.getRule(),ca.getNumCells());
            } catch (IllegalArgumentException iae) {
                v.showError("Invalid input. Expected range 0 <= rule <= 255.");
                return;
            }
            v.setCA(ca);
            v.setRCA(rca);
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