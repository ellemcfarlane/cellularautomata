
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests various methods of CellAuto class.
 */

public class TestCA {
    private CellAuto ca;

    @Test
    public void testConstr() {
        ca = new CellAuto(110, 15,50);
        assertEquals(110, ca.getRule());
        assertEquals(15, ca.getNumGens());
        assertEquals(50, ca.getNumCells());

        ca = new CellAuto();
        assertEquals(110, ca.getRule());
        assertEquals(333, ca.getNumGens());
        assertEquals(333, ca.getNumCells());
    }

    @Test
    public void testGenerate() {
        ca = new CellAuto(110,4,6);
        int[][] test = {{0,0,0,1,0,0},{0,0,1,1,0,0},{0,1,1,1,0,0},{0,1,0,1,0,0}};
        assertTrue(Arrays.deepEquals(test,ca.getCells()));
    }

    @Test
    public void testGetCellAtGen() {
        ca = new CellAuto(110,100,100);
        int[] test = {0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0,
                      1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,
                      0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1,
                      1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                      0, 0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(test,ca.getCellsAtGen(99));
    }

    @Test
    public void testRules() {
        ca = new CellAuto(110,100,100);
        assertEquals(0,ca.rules(1,1,1));
        assertEquals(1,ca.rules(1,1,0));
        assertEquals(1,ca.rules(1,0,1));
        assertEquals(0,ca.rules(1,0,0));
        assertEquals(1,ca.rules(0,1,1));
        assertEquals(1,ca.rules(0,1,0));
        assertEquals(1,ca.rules(0,0,1));
        assertEquals(0,ca.rules(0,0,0));
        ca = new CellAuto(124,100,100);
        assertEquals(0,ca.rules(1,1,1));
        assertEquals(1,ca.rules(1,1,0));
        assertEquals(1,ca.rules(1,0,1));
        assertEquals(1,ca.rules(1,0,0));
        assertEquals(1,ca.rules(0,1,1));
        assertEquals(1,ca.rules(0,1,0));
        assertEquals(0,ca.rules(0,0,1));
        assertEquals(0,ca.rules(0,0,0));
    }

    @Test
    public void testCG() {
        ca = new CellAuto(110,4,6);
        int[][] test = {{0,0},{0,0},{1,0},{0,0}};
        ca.coarseGrain();
        assertTrue(Arrays.deepEquals(test,ca.getCells()));
        ca.coarseGrain();
        System.out.println("Testing coarseGrain method: ");
        assertEquals(1,ca.getGrainlevel());

        ca = new CellAuto(110,3,9);
        ca.coarseGrain();
        test = new int[][]{{0,0,0},{0,1,0},{0,1,0}};
        assertTrue(Arrays.deepEquals(test,ca.getCells()));
        ca.coarseGrain();
        test = new int[][]{{0},{0},{0}};
        assertTrue(Arrays.deepEquals(test,ca.getCells()));
        assertEquals(2,ca.getGrainlevel());

        ca = new CellAuto(110,300,3000);
        ca.coarseGrain();
        assertEquals(1000,ca.getNumCells());
        ca.coarseGrain();
        assertEquals(333,ca.getNumCells());
        ca.coarseGrain();
        assertEquals(111,ca.getNumCells());
    }

    @Test
    public void testMultiGrain() {
        ca = new CellAuto(110,3,9);
        ca.multiGrains(0);
        int[][] test = {{0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0, 0, 0, 0}};
        assertEquals(0,ca.getGrainlevel());
        assertTrue(Arrays.deepEquals(test,ca.getCells()));

        ca = new CellAuto(110,3,9);
        ca.multiGrains(2);
        test = new int[][]{{0},{0},{0}};
        assertTrue(Arrays.deepEquals(test,ca.getCells()));
        assertEquals(2,ca.getGrainlevel());

        ca = new CellAuto(110,300,3000);
        ca.multiGrains(7);
        assertEquals(7,ca.getGrainlevel());
        assertEquals(1,ca.getNumCells());
    }

    @Test
    public void testCollapseCells() {
        ca = new CellAuto(110, 10, 9);
        int[] test = {1,2,3,3,4,2,3,4,2,3};
        assertArrayEquals(test,ca.countCellsAlive());
    }

    @Test
    public void testGetCellHistory() {
        ca = new CellAuto(110, 20, 20);
        int[] test = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        assertArrayEquals(test,ca.getCellHistory(10));
        int[] test2 = {0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0};
        assertArrayEquals(test2,ca.getCellHistory(9));
    }

}
