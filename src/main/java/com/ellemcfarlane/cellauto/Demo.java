package com.ellemcfarlane.cellauto;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Growing rule 110 of 10 cells for 3 generations");
        CellAuto2D ca2d = new CellAuto2D(110, 3, 10);
        System.out.println("Display");
        ca2d.display();
        System.out.println("Display info");
        ca2d.displayInfo();
        System.out.println("Get cells");
        ca2d.getCells();
        System.out.println("Growing rule 110 of 900 cells for 20 generations");
        ca2d = new CellAuto2D(110, 20, 900);
        System.out.println("Course grain once");
        ca2d.coarseGrain();
        System.out.println("Course grain multiple times (twice)");
        ca2d.multiGrains(2);
        System.out.println("Get cell growth history");
        ca2d.getCellHistory(3);
        
        System.out.println("Rule 110 with 10 cells");
        CellAuto1D ca1d = new CellAuto1D(110, 10); // rule, num cells
        ca1d.display();
        ca1d.displayInfo();
        ca1d.quickGen(10);
    }
}