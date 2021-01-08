package com.ellemcfarlane.cellauto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// class for saving cell states to CSV or TSV files.

public class Export {
    /**
     * Converts current state of cells (1's and 0's) to be in CSV format while maintaining generation
     * architecture, e.g. if ca2d has 20 cells per generation, and 10 generations,
     * output file will have 10 rows and 20 columns
     * @param filename file will be saved exactly as filename plus ".csv" at the end
     * @param ca2d com.elle.cellularautomata.CellAuto2D to be exported
     */
    public static void caToCSV(String filename, CellAuto2D ca2d) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(filename + ".csv"));
            StringBuilder sb = new StringBuilder();
            // converts each array in 2D array to a string using toString
            for (int[] i : ca2d.getCells()) {
                String elem = Export.toStringCSV(i);
                sb.append(elem);
                sb.append("\n");
            }
            br.write(sb.toString());
            br.close();
        }catch(IOException e){System.out.println("IOException. It's possible file directory does not exist."); return;}
        System.out.println("File saved as: " + filename + ".csv");
    }

    public static void arrayToCSV(String filename, int[] arr) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(filename + ".csv"));
            StringBuilder sb = new StringBuilder();
            // converts each array in 2D array to a string using toString
            for (int i : arr) {
                sb.append(i);
                sb.append(",");
            }
            br.write(sb.toString());
            br.close();
        }catch(IOException e){System.out.println("IOException. It's possible file directory does not exist."); return;}
        System.out.println("File saved as: " + filename + ".csv");
    }

    private static String toStringCSV(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i]);
            sb.append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    /**
     * Converts current state of cells (1's and 0's) to be in TSV format as a single column
     * WARNING: does not maintain original architecture, e.g. if ca2d has 20 cells per generation and 10 generations,
     * output file will have 200 rows and 1 column
     * @param filename
     * @param ca2d
     */
    public static void caToTSV(String filename, CellAuto2D ca2d) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(filename + ".tsv"));
            StringBuilder sb = new StringBuilder();
            // converts each array in 2D array to a string using toString
            for (int[] i : ca2d.getCells()) {
                String elem = Export.toStringTSV(i);
                sb.append(elem);
                sb.append("\n");
            }
            br.write(sb.toString());
            br.close();
        }catch(IOException e){System.out.println("IOException. It's possible file directory does not exist."); return;}
        System.out.println("File saved as: " + filename + ".tsv");
    }

    public static String toStringTSV(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i]);
            sb.append("\n");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }
}
