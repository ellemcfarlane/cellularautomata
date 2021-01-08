package com.ellemcfarlane.cellauto;

import java.util.Arrays;

/**
 * Class for a cellular automaton that keeps track of its previous generations and hence has
 * two dimensions, time (rows) and space (columns).
 *
 * This generation-tracking allows for coarse-graining of the cellular automata throughout time.
 */

public class CellAuto2D {
    private int[][] cells;
    private int rule;
    private int[] rules;
    private int numcells, numgens;
    private int grainlevel;

    public static void main(String[] args) {
        CellAuto2D ca2d = new CellAuto2D(110,400,20);
    }
    /**
     * Constructor for CellAuto2D
     * @param rule for cellular automaton in decimal, 0-255 (inclusive)
     * @param numgens number of generations
     * @param numcells cells per generation
     */
    public CellAuto2D(int rule, int numgens, int numcells) {
        this.rule = rule;
        if (this.rule < 0) {
            throw new IllegalArgumentException(
                    "rule out of range: " + this.rule +
                            " expected range 0 <= rule <= 255");
        }
        this.numgens = numgens;
        // converts rule number in base 10 to binary array
        this.rules = Binary.toBinarySet(this.rule);
        this.numcells = numcells;
        this.cells = new int[this.numgens][this.numcells];
        this.grainlevel = 0;
        if (this.numcells > 0) {
            this.cells[0][numcells/2] = 1; // Middle cell in gen0 is set to 1
            this.generate();
        }
    }

    /**
     * Alternate constructor. Default rule of 110 with 333 cells/gen for 333 gens.
     */
    public CellAuto2D() {
        this(110,333,333);
    }

    /**
     * @return CellAuto2D's cells, including all previous generations
     */
    public int[][] getCells() {
        return this.cells;
    }

    /**
     * @param gen
     * @return array of cell states in gen (row)
     */
    public int[] getCellsAtGen(int gen) {
        return this.cells[gen];
    }

    /**
     * @return CellAuto2D's rule
     */
    public int getRule() {
        return this.rule;
    }

    /**
     * @return CellAuto2D's number of cells per generation
     */
    public int getNumCells() {
        return this.numcells;
    }

    /**
     * @return CellAuto2D's number of generations
     */
    public int getNumGens() {
        return this.numgens;
    }

    /**
     * @return CellAuto2D's number of times it has been coarse-grained
     */
    public int getGrainlevel() {
        return this.grainlevel;
    }

    /**
     * @param cell index of cell
     * @return cell's state for all generations
     */
    public int[] getHistForCell(int cell) {
        if (cell >= this.numcells || cell < 0) {
            throw new IllegalArgumentException(
                    "cell out of range: " + cell +
                            " expected range 0 <= cell < " + this.numcells);
        }

        int[] cellHist = new int[this.numgens];
        for (int i = 0; i < this.numgens; i++) {
            cellHist[i] = this.getCellsAtGen(i)[cell];
        }
        return cellHist;
    }


    /**
     * Generates cells based off internal rule
     */
    private void generate() {
        int[] currCells = this.cells[0];
        int[] nextGen;
        for (int gen = 1; gen < numgens; gen++) {
            nextGen = this.cells[gen];
            for (int i = 1; i < currCells.length-1; i++) {
                int left = currCells[i-1];
                int mid = currCells[i];
                int right = currCells[i+1];
                nextGen[i] = rules(left, mid, right);
            }
            currCells = nextGen;
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
     * Performs coarse-grain via tuplet majority rules;
     * e.g. if the original cells are [1,1,0,1,0,0] the coarse-grain is [1,0]
     * because the first 3 cells are majority 1 and the second 3 are maj. 0
     *
     * If cells/gen is fewer than 3 it will not coarse-grain
     */
    public void coarseGrain() {
        if (this.numcells < 3) {
            System.out.println("Cells per gen is fewer than 3. Cannot coarse-grain further.");
            return;
        }
        this.grainlevel++;

        int[][] newcells = new int[this.numgens][this.numcells/3];
        for (int r = 0; r < this.numgens; r++) {
            // determines tuplet majority
            for (int c = 0; c < newcells[0].length; c++) {
                int total = cells[r][c*3] + cells[r][c*3+1] + cells[r][c*3+2];
                newcells[r][c] = total <= 1 ? 0 : 1;
            }
        }
        this.cells = newcells;
        this.numcells /= 3;
    }

    /**
     * Performs given number of coarse-grains on cells
     * @param grains number of coarse-grains to be performed
     *
     * If grains input is 0, no coarse-grain will be performed
     */
    public void multiGrains(int grains) {
        if (grains == 0) {
            return;
        }
        for (int i = 0; i < grains; i++) {
            this.coarseGrain();
        }
    }

    /**
     * @return array showing number of cells "alive" (in the 1 state) per generation, where generation is index
     */
    public int[] countCellsAlive() {

        int[] out = new int[cells.length];
        int i = 0;
        for (int[] row : cells) {
            int sum = 0;
            for (int num : row) {
                sum += num;
            }
            out[i++] = sum;
        }
        return out;
    }

    /**
     * prints all cell states in CellAuto2D
     */
    public void displayCells() {
        for (int gen = 0; gen < numgens; gen++) {
            System.out.println(Arrays.toString(cells[gen]));
        }
    }

    /**
     * prints CellAuto2D information
     */
    public void displayInfo() {
        System.out.println("Rule: " + this.rule +
                           ", gens: " + this.numgens +
                           ", cells/gen: " + this.numcells +
                           ", grain lvl: " + this.grainlevel);
    }
}
