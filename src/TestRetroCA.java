
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests various methods of CellAuto class.
 */

public class TestRetroCA {
    private RetroCA rac;

    @Test
    public void testConstr() {
        rac = new RetroCA(110, 15);
        assertEquals(110, rac.getRule());
        assertEquals(0, rac.getGen());
        assertEquals(15, rac.getNumCells());

        rac = new RetroCA();
        rac.generate();
        rac.generate();
        rac.generate();
        assertEquals(110, rac.getRule());
        assertEquals(3, rac.getGen());
        assertEquals(100, rac.getNumCells());
    }

    @Test
    public void testGenerate() {
        rac = new RetroCA(110, 10);
        rac.quickGen(101);
        int[] test = {0, 1, 0, 1, 1, 1, 0, 0, 0, 0};
        assertArrayEquals(test, rac.getCells());
    }

    @Test
    public void testRules() {
        rac = new RetroCA(110, 100);
        assertEquals(0, rac.rules(1, 1, 1));
        assertEquals(1, rac.rules(1, 1, 0));
        assertEquals(1, rac.rules(1, 0, 1));
        assertEquals(0, rac.rules(1, 0, 0));
        assertEquals(1, rac.rules(0, 1, 1));
        assertEquals(1, rac.rules(0, 1, 0));
        assertEquals(1, rac.rules(0, 0, 1));
        assertEquals(0, rac.rules(0, 0, 0));
    }
}
