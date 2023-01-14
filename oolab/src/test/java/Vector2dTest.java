import project.map.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    Vector2d v00 = new Vector2d(0, 0);
    Vector2d v10 = new Vector2d(1, 0);
    Vector2d v01 = new Vector2d(0, 1);
    Vector2d vzero = new Vector2d(0, 0);
    Vector2d v11 = new Vector2d(1, 1);
    Vector2d v02 = new Vector2d(0, 2);

    @Test
    public void testEquals() {
        assertTrue(v00.equals(v00));
        assertTrue(vzero.equals(v00));
        assertTrue(v00.equals(vzero));
        assertFalse(v01.equals(v10));
        assertFalse(v01.equals(v02));

        assertFalse(v00.equals("text"));
        assertFalse(v00.equals(1));
        assertFalse(v00.equals(1.0));
        assertFalse(v00.equals(0));
        assertFalse(v00.equals(33000));
        assertFalse(v00.equals(true));
        assertFalse(v00.equals(false));
    }

    @Test
    public void testToString() {
        assertEquals(v00.toString(), "(0, 0)");
        assertEquals(vzero.toString(), "(0, 0)");
        assertEquals(v10.toString(), "(1, 0)");
        assertEquals(v01.toString(), "(0, 1)");
        assertEquals(v02.toString(), "(0, 2)");
        assertEquals(vzero.toString(), v00.toString());

        assertTrue(!v00.toString().equals("(0,0)"));
        assertTrue(!v00.toString().equals("(0 , 0)"));
        assertTrue(!vzero.toString().equals(v01.toString()));
    }

    @Test
    public void testPreceeds() {
        assertTrue(v00.precedes(v01));
        assertTrue(v00.precedes(v10));
        assertTrue(v00.precedes(v00));
        assertTrue(v00.precedes(vzero));
        assertTrue(v01.precedes(v02));
        assertFalse(v11.precedes(v01));
        assertFalse(v02.precedes(v10));
    }

    @Test
    public void testFollows() {
        assertFalse(v00.follows(v01));
        assertFalse(v00.follows(v10));
        assertTrue(v00.follows(v00));
        assertTrue(v00.follows(vzero));
        assertFalse(v01.follows(v02));
        assertTrue(v11.follows(v01));
        assertFalse(v02.follows(v10));
    }

    @Test
    public void testUpperRight() {
        assertEquals(v00.upperRight(v01), v01);
        assertEquals(v11.upperRight(v02), new Vector2d(1, 2));
        assertEquals(v10.upperRight(v01), v11);
        assertEquals(vzero.upperRight(v10), v10);

    }

    @Test
    public void testLowerLeft() {
        assertEquals(v10.lowerLeft(v01), v00);
        assertEquals(v11.lowerLeft(v02), v01);
        assertEquals(v11.lowerLeft(v11), v11);
        assertEquals(vzero.lowerLeft(v10), v00);

    }

    @Test
    public void testAdd() {
        assertEquals(v00.add(v01), v01);
        assertEquals(v10.add(v01), v11);
        assertEquals(v02.add(v11), new Vector2d(1, 3));
        assertEquals(v00.add(vzero), v00);
    }

    @Test
    public void testSubtract() {
        assertEquals(v01.subtract(v00), v01);
        assertEquals(v00.subtract(v11), new Vector2d(-1, -1));
        assertEquals(v02.subtract(v11), new Vector2d(-1, 1));
        assertEquals(v11.subtract(v01), v10);
    }
}
