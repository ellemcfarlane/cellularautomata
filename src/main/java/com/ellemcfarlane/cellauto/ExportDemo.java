package com.ellemcfarlane.cellauto;

public class ExportDemo {
    // demo for save cell data for particular rule to file
    public static void main(String[] args) {
        int rule = 22;
        int n_gens = 1000;
        int n_cells = 1000; // per gen
        CellAuto2D ca2d = new CellAuto2D(rule, n_gens, n_cells);
        // if change folder name, make sure ends with backslash
        String directory = "../../../../src/main/java/com/ellemcfarlane/cellauto/exported/";
        int n_coarse_grains = 5;
        // save initial state of 1000 cells after 1000 generations
        // and their states after each subsequent coarse grain
        for (int i = 0; i <= n_coarse_grains; i++) {
            String filename = "1krule" + ca2d.getRule() + "entropycg" + ca2d.getGrainlevel();
            Export.caToCSV(directory + filename, ca2d);
            ca2d.coarseGrain();
        }
    }
}