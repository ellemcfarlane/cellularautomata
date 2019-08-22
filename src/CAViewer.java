
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Provides interface for CellAuto class
 */
public class CAViewer extends JFrame {
    private CellAuto ca;
    private DrawPanel dp;
    private RetroCA rca;
    private DrawRetro dr;

    private JTextField ruletf;
    private JTextField numgenstf;
    private JTextField numcellstf;

    private JButton coarsebtn;
    private JButton runbtn;
    private JButton resetbtn;
    private JButton undobtn;
    private JButton growbtn;
    private JButton pausebtn;

    private boolean isPaused;

    /**
     * Initiates content of the CAViewer.
     * Default rule displayed is rule 110.
     */
    public CAViewer() {
        // main dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int cells = screenSize.width;
        int gens = screenSize.height;

        // initial data
        this.ca = new CellAuto(110,gens,cells);
        this.rca = new RetroCA(ca.getRule(),ca.getNumCells());

        // text-fields
        this.ruletf = new JTextField(5);
        this.ruletf.setPreferredSize(new Dimension(cells/7,20));
        this.ruletf.setMaximumSize(new Dimension(Integer.MAX_VALUE,this.ruletf.getPreferredSize().height));
        this.ruletf.setText("" + ca.getRule());

        this.numcellstf = new JTextField(5);
        this.numcellstf.setPreferredSize(new Dimension(cells/7,20));
        this.numcellstf.setMaximumSize(new Dimension(Integer.MAX_VALUE,this.numcellstf.getPreferredSize().height));
        this.numcellstf.setText("" + ca.getNumCells());

        this.numgenstf = new JTextField(5);
        this.numgenstf.setPreferredSize(new Dimension(cells/7,20));
        this.numgenstf.setMaximumSize(new Dimension(Integer.MAX_VALUE,this.numgenstf.getPreferredSize().height));
        this.numgenstf.setText("" + ca.getNumGens());

        // buttons
        coarsebtn = new JButton("coarse-grain");
        runbtn = new JButton("set params");
        resetbtn = new JButton("reset");
        undobtn = new JButton("undo coarse-grain");
        growbtn = new JButton("grow cells");
        pausebtn = new JButton("pause");
        isPaused = false;

        // main panel
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));

        // left panel with buttons and text-fields
        JPanel infocontent = new JPanel();
        infocontent.setLayout(new BoxLayout(infocontent, BoxLayout.Y_AXIS));
        infocontent.setPreferredSize(new Dimension(cells/7,gens));
        infocontent.add(new JLabel("Rule (0-255)"));
        infocontent.add(ruletf);
        infocontent.add(new JLabel("Cells/gen"));
        infocontent.add(numcellstf);
        infocontent.add(new JLabel("Gens"));
        infocontent.add(numgenstf);
        infocontent.add(runbtn);
        infocontent.add(coarsebtn);
        infocontent.add(undobtn);
        infocontent.add(growbtn);
        infocontent.add(pausebtn);
        infocontent.add(resetbtn);
        main.add(infocontent);

        CardLayout cl = new CardLayout(5,5);
        JPanel right = new JPanel(cl);
        // right panel with drawing of data (CellAuto)
        this.dp = new DrawPanel(ca);
        dp.setPreferredSize(new Dimension(ca.getNumGens(),ca.getNumCells()));
        right.add(dp,"static");

        this.dr = new DrawRetro(rca);
        dr.setBackground(Color.GREEN);
        dr.setPreferredSize(new Dimension(ca.getNumGens(),ca.getNumCells()));
        right.add(dr,"grow");

        growbtn.addActionListener(e -> cl.show(right,"grow"));
        coarsebtn.addActionListener(e-> cl.show(right,"static"));
        runbtn.addActionListener(e-> cl.show(right,"static"));
        resetbtn.addActionListener(e-> cl.show(right,"static"));
        undobtn.addActionListener(e-> cl.show(right,"static"));
        growbtn.addActionListener(e-> {
            cl.show(right,"static");
            pausebtn.setText("pause");
            this.isPaused = false;
        });
        pausebtn.addActionListener(e -> {
                    if (pausebtn.getText().equals("pause")) {
                        pausebtn.setText("unpause");
                        isPaused = true;
                    } else {
                        pausebtn.setText("pause");
                        isPaused = false;
                    }
                });


        main.add(right);


        // This class holds main panel
        this.setTitle("Cellular Automaton");
        this.add(main);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

    }

    /**
     * @return CellAuto currently displayed
     */
    public CellAuto getCA() {
        return this.ca;
    }

    /**
     * @return RetroCA with same rule as current CellAuto
     */
    public RetroCA getRCA() {
        return this.rca;
    }

    /**
     * @return panel showing growing RetroCA
     */
    public DrawRetro getDR() {
        return this.dr;
    }

    /**
     * Sets current CellAuto displayed
     * @param ca to display
     */
    public void setCA(CellAuto ca) {
        this.ca = ca;
        this.dp.setCA(ca);
        this.repaint();
    }

    /**
     * Sets rca to same rule as current ca
     * @param rca
     */
    public void setRCA(RetroCA rca) {
        this.rca = rca;
        this.dr.setRCA(rca);
    }

    /**
     * Resets CellAuto displayed to default, rule 110.
     */
    public void reset() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int cells = screenSize.width;
        int gens = screenSize.height;
        this.setCA(new CellAuto(110,gens,cells));
        this.setRCA(new RetroCA(ca.getRule(),ca.getNumCells()));
        this.ruletf.setText("" + ca.getRule());
        this.numcellstf.setText("" + ca.getNumCells());
        this.numgenstf.setText("" + ca.getNumGens());
        this.pausebtn.setText("pause");
        this.isPaused = false;
        this.repaint();
    }

    /**
     * Allows run button to display current CellAuto data
     * @param sal
     */
    public void addRunListener(ActionListener sal) {
        this.runbtn.addActionListener(sal);
    }

    /**
     * Allows coarse-grain button to coarse-grain current CellAuto
     * @param gal
     */
    public void addGrainListener(ActionListener gal) {
        this.coarsebtn.addActionListener(gal);
    }

    /**
     * Allows reset button to reset CellAuto to rule 110.
     * @param ral
     */
    public void addResetListener(ActionListener ral) {
        this.resetbtn.addActionListener(ral);
    }

    /**
     * Allows undo coarse-grain button to return to previous coarse-grain level of CellAuto
     * @param ual
     */
    public void addUndoListener(ActionListener ual) {
        this.undobtn.addActionListener(ual);
    }

    /**
     * Allows grow button to simulate the "growing" of a cellular automaton
     * @param gral
     */
    public void addGrowListener(ActionListener gral) {
        this.growbtn.addActionListener(gral);
    }

    public void addPauseListener(ActionListener pal) {
        this.pausebtn.addActionListener(pal);
    }

    /**
     * @return String for rule given by user in the text-field
     */
    public String getRulesInput() {
        return this.ruletf.getText();
    }

    /**
     * @return String for number of cells given by user in the text-field
     */
    public String getNumCellsInput() {
        return this.numcellstf.getText();
    }

    /**
     *
     * @return String for number of generations given by user in the text-field
     */
    public String getNumGensInput() {
        return this.numgenstf.getText();
    }

    /**
     * Displays given errorMess in message dialog
     * @param errorMess message to display
     */
    public void showError(String errorMess) {
        JOptionPane.showMessageDialog(this, errorMess);
    }

    /**Checks if view is paused with respect to its RetroCA (growing generations)*/
    public boolean isPaused() {
        return this.isPaused;
    }

    /**
     * Repaints CAViewer and its DrawPanel dp to reflect current CellAuto ca
     */
    @Override
    public void repaint() {
        this.dp.repaint();
        this.dr.repaint();
        this.ruletf.setText("" + ca.getRule());
        this.numcellstf.setText("" + ca.getNumCells());
        this.numgenstf.setText("" + ca.getNumGens());
    }

}
