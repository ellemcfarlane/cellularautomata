
import javax.swing.*;
import java.awt.*;

/**
 * Provides visualization of CellAuto class
 */
public class DrawPanel extends JPanel {
    private CellAuto ca;
    private int panwidth;
    private int panheight;

    /**
     * Constructor that sets given ca.
     * @param ca CellAuto to paint.
     */
    public DrawPanel(CellAuto ca) {
        this.ca = ca;
        this.setBackground(Color.WHITE);
        // user's screen size is used to keep drawing in view
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.panwidth = (screenSize.width*6)/7;
        this.panheight = screenSize.height;
    }

    /**
     * Sets current CellAuto to ca.
     * @param ca
     */
    public void setCA(CellAuto ca) {
        this.ca = ca;
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
        int size = (int)Math.pow(3,ca.getGrainlevel());
        super.paintComponent(g);
        int shiftd = ca.getNumGens() - panheight > 0 ? ca.getNumGens() - panheight : 0;
        int shiftl = ca.getNumCells() - panwidth > 0 ? ca.getNumCells() - panwidth : 0;
        for (int i = shiftd; i < ca.getNumGens(); i++) {
            for (int j = shiftl; j < ca.getNumCells(); j++) {
                if (ca.getCells()[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j*size-shiftl, i*1-shiftd, size, 1);
            }
        }
    }
}
