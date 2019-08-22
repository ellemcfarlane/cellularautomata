
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

/**
 * Provides visualization of RetroCA class
 */
public class DrawRetro extends JPanel implements ActionListener {
    private RetroCA rca;
    private int panwidth;
    private int panheight;
    private BufferedImage oldim;
    private BufferedImage newim;

    /**
     * Constructor that sets given ca.
     * @param rca RetroCA to paint.
     */
    public DrawRetro(RetroCA rca) {
        this.rca = rca;
        this.setBackground(Color.WHITE);
        // user's screen size is used to keep drawing in view
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.panwidth = (screenSize.width*6)/7;
        this.panheight = (screenSize.height*90)/100;
        this.oldim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
        this.newim = new BufferedImage(panwidth,panheight,BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * Sets current RetroCA to rca.
     * @param rca
     */
    public void setRCA(RetroCA rca) {
        this.rca = rca;
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
        this.rca.generate();
        this.addCell();
    }

    /**
     * Draws each generation as cell generation increases.
     * Used by actionPerformed method.
     */
    public void addCell() {
        int size = 1;
        int shiftl = rca.getNumCells() - panwidth > 0 ? rca.getNumCells() - panwidth : 0;
        // when cell generation equals panel height, start shifting view upward to see new cells
        if (rca.getGen() - oldim.getHeight() >= 0) {
            Graphics2D g2 = newim.createGraphics();
            g2.drawImage(oldim.getSubimage
                    (0, 1, oldim.getWidth(), oldim.getHeight()-1),0, 0,Color.BLUE,null);
            for (int i = 0; i < rca.getNumCells(); i++) {
                if (rca.getCells()[i] == 1) {
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
            for (int i = 0; i < rca.getNumCells(); i++) {
                if (rca.getCells()[i] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect((i * size)-shiftl, rca.getGen() * size, size, size);

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
