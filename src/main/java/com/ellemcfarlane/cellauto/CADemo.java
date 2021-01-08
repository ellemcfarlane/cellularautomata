package com.ellemcfarlane.cellauto;

import java.util.Arrays;

public class CADemo {
    public static void main(String[] args) {
        System.out.println("CA2D Demo");
        System.out.println("Growing rule 110 with 10 cells/gen for 3 generations");
        CellAuto2D ca2d = new CellAuto2D(110, 3, 10);
        ca2d.displayCells();
        ca2d.displayInfo();

        System.out.println("\nGrowing rule 110 with 900 cells/gen for 20 generations");
        ca2d = new CellAuto2D(110, 20, 900);
        ca2d.displayInfo();
        System.out.println("Get cell growth history for cell number 440");
        System.out.println(Arrays.toString(ca2d.getHistForCell(440)));
        System.out.println("Coarse grain once");
        ca2d.coarseGrain();
        ca2d.displayInfo();
        System.out.println("Coarse grain multiple times (twice)");
        ca2d.multiGrains(2);
        ca2d.displayInfo();
        
        System.out.println("\nCA1D Demo");
        System.out.println("Rule 110 with 10 cells");
        CellAuto1D ca1d = new CellAuto1D(110, 10);
        ca1d.displayCells();
        ca1d.displayInfo();
        System.out.println("Jump 10 generations ahead");
        ca1d.quickGen(10);
        ca1d.displayCells();
        ca1d.displayInfo();
    }
}