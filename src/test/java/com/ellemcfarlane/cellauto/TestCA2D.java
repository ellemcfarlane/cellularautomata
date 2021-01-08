package com.ellemcfarlane.cellauto;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests various methods of CellAuto2D class.
 */

public class TestCA2D {
    private CellAuto2D ca2d;

    @Test
    public void testConstr() {
        ca2d = new CellAuto2D(110, 15, 50);
        assertEquals(110, ca2d.getRule());
        assertEquals(15, ca2d.getNumGens());
        assertEquals(50, ca2d.getNumCells());

        ca2d = new CellAuto2D();
        assertEquals(110, ca2d.getRule());
        assertEquals(333, ca2d.getNumGens());
        assertEquals(333, ca2d.getNumCells());
    }

    @Test
    public void testGenerate() {
        ca2d = new CellAuto2D(110, 4, 6);
        int[][] test = {{0,0,0,1,0,0},{0,0,1,1,0,0},{0,1,1,1,0,0},{0,1,0,1,0,0}};
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));
    }

    @Test
    public void testGetCellAtGen() {
        ca2d = new CellAuto2D(110,100,100);
        int[] test = {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0,
                      1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
                      0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1,
                      1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(test,ca2d.getCellsAtGen(99));
    }

    @Test
    public void testRules() {
        ca2d = new CellAuto2D(110,100,100);
        assertEquals(0,ca2d.rules(1,1,1));
        assertEquals(1,ca2d.rules(1,1,0));
        assertEquals(1,ca2d.rules(1,0,1));
        assertEquals(0,ca2d.rules(1,0,0));
        assertEquals(1,ca2d.rules(0,1,1));
        assertEquals(1,ca2d.rules(0,1,0));
        assertEquals(1,ca2d.rules(0,0,1));
        assertEquals(0,ca2d.rules(0,0,0));
        ca2d = new CellAuto2D(124,100,100);
        assertEquals(0,ca2d.rules(1,1,1));
        assertEquals(1,ca2d.rules(1,1,0));
        assertEquals(1,ca2d.rules(1,0,1));
        assertEquals(1,ca2d.rules(1,0,0));
        assertEquals(1,ca2d.rules(0,1,1));
        assertEquals(1,ca2d.rules(0,1,0));
        assertEquals(0,ca2d.rules(0,0,1));
        assertEquals(0,ca2d.rules(0,0,0));
    }

    @Test
    public void testCG() {
        ca2d = new CellAuto2D(110,4,6);
        int[][] test = {{0,0},{0,0},{1,0},{0,0}};
        ca2d.coarseGrain();
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));
        ca2d.coarseGrain();
        System.out.println("Testing coarseGrain method: ");
        assertEquals(1,ca2d.getGrainlevel());

        ca2d = new CellAuto2D(110,3,9);
        ca2d.coarseGrain();
        test = new int[][]{{0,0,0},{0,1,0},{0,1,0}};
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));
        ca2d.coarseGrain();
        test = new int[][]{{0},{0},{0}};
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));
        assertEquals(2,ca2d.getGrainlevel());

        ca2d = new CellAuto2D(110,300,3000);
        ca2d.coarseGrain();
        assertEquals(1000,ca2d.getNumCells());
        ca2d.coarseGrain();
        assertEquals(333,ca2d.getNumCells());
        ca2d.coarseGrain();
        assertEquals(111,ca2d.getNumCells());
    }

    @Test
    public void testMultiGrain() {
        ca2d = new CellAuto2D(110,3,9);
        ca2d.multiGrains(0);
        int[][] test = {{0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 0, 0, 0}};
        assertEquals(0,ca2d.getGrainlevel());
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));

        ca2d = new CellAuto2D(110,3,9);
        ca2d.multiGrains(2);
        test = new int[][]{{0},{0},{0}};
        assertTrue(Arrays.deepEquals(test,ca2d.getCells()));
        assertEquals(2,ca2d.getGrainlevel());

        ca2d = new CellAuto2D(110,300,3000);
        ca2d.multiGrains(7);
        assertEquals(7,ca2d.getGrainlevel());
        assertEquals(1,ca2d.getNumCells());
    }

    @Test
    public void testCollapseCells() {
        ca2d = new CellAuto2D(110, 10, 9);
        int[] test = {1,2,3,3,4,2,3,4,2,3};
        assertArrayEquals(test,ca2d.countCellsAlive());
    }

    @Test
    public void testGetHistForCell() {
        ca2d = new CellAuto2D(110, 20, 20);
        int[] test = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        assertArrayEquals(test,ca2d.getHistForCell(10));
        int[] test2 = {0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0};
        assertArrayEquals(test2,ca2d.getHistForCell(9));
    }

}
