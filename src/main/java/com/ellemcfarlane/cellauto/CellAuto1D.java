package com.ellemcfarlane.cellauto;

/**
 * Class for cellular automaton that does not "remember" its previous generation and thus is always
 * one-dimensional (hence the name CellAuto1D).
 *
 * Created with guide from Daniel Shiffman's The Nature of Code, Chapter 7. Cellular Automata
 * https://natureofcode.com/book/chapter-7-cellular-automata/
 */

public class CellAuto1D {
    private int[] cells;
    private int rule;
    private int[] rules;
    private int numcells, currgen;

    /**
     * Constructor
     * @param rule for cellular automaton in decimal, 0-255 (inclusive)
     * @param numcells in generation
     */
    public CellAuto1D(int rule, int numcells) {
        this.rule = rule;
        if (this.rule < 0) {
            throw new IllegalArgumentException(
                    "rule out of range: " + this.rule +
                            " expected range 0 <= rule <= 255");
        }
        this.rules = Binary.toBinarySet(rule);
        this.numcells = numcells;
        this.cells = new int[numcells];
        this.currgen = 0;
        if (this.numcells > 0) {
            this.cells[numcells/2] = 1; // Middle cell in gen0 is set to 1
        }
    }

    /**
     * Alternate constructor. Default rule of 110 with 333 cells
     */
    public CellAuto1D() {
        this.rule = 110;
        this.rules = Binary.toBinarySet(this.rule);
        this.numcells = 100;
        this.currgen = 0;
        this.cells = new int[numcells];
        this.cells[numcells/2] = 1;
    }

    public void clear() {
        this.cells = new int[this.numcells];
        this.cells[numcells/2] = 1;
        this.currgen = 0;
    }

    /**
     * @return array showing current state of cells (expressed in 0's and 1's)
     */
    public int[] getCells() {
        return this.cells;
    }

    /**
     * @return current generation of cells (0-based)
     */
    public int getGen() {
        return this.currgen;
    }

    /**
     * @return decimal rule the cells are following
     */
    public int getRule() {
        return this.rule;
    }

    /**
     * @return number of cells in a generation (includes both dead/alive cells)
     */
    public int getNumCells() {
        return this.numcells;
    }

    /**
     * Generates cells based off internal rule
     */
    public void generate() {
        int[] nextGen = new int[this.numcells];
        for (int i = 1; i < this.numcells-1; i++) {
            int left = this.cells[i-1];
            int mid = this.cells[i];
            int right = this.cells[i+1];
            nextGen[i] = rules(left, mid, right);
        }
        this.cells = nextGen;
        this.currgen++;
    }

    /**
     * Generates cell to desired number of generations ahead.
     * If numgens is 0, does not generate.
     * @param numgens number of times to generate cells
     */
    public void quickGen(int numgens) {
        if (numgens == 0) return;
        for (int i = 0; i < numgens; i++) {
            this.generate();
        }
    }

    /**
     * Determines death/survival of individual cell based off internal rule
     * @param a left cell
     * @param b current cell (middle)
     * @param c right cell
     * @return 0 or 1 (death or survival of b cell)
     */
    public int rules(int a, int b, int c) {
        String nbrhood = "" + a + b + c;
        int index = 7 - Integer.parseInt(nbrhood,2);
        return rules[index];
    }


    /**
     * prints all cell states
     */
    public void displayCells() {
        for (int cell = 0; cell < numcells; cell++) {
            System.out.print(this.cells[cell] + " ");
        }
        System.out.print("\n");
    }

    /**
     * prints data information
     */
    public void displayInfo() {
        System.out.println("Rule: " + this.rule +
                " gen: " + this.currgen +
                " cell count: " + this.numcells);
    }

}
