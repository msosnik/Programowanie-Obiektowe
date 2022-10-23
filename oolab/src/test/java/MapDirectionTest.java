import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

//    @Test
//    public void TestNext() {
//        assertEquals(MapDirection.next(MapDirection.NORTH), MapDirection.EAST);
//        assertEquals(MapDirection.next(MapDirection.EAST), MapDirection.SOUTH);
//        assertEquals(MapDirection.next(MapDirection.SOUTH), MapDirection.WEST);
//        assertEquals(MapDirection.next(MapDirection.WEST), MapDirection.NORTH);
//    }

    @Test
    public void TestNext() {
        assertEquals(MapDirection.NORTH.next(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.next(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.next(), MapDirection.NORTH);
    }

//    @Test
//    public void TestPrevious() {
//        assertEquals(MapDirection.previous(MapDirection.NORTH), MapDirection.WEST);
//        assertEquals(MapDirection.previous(MapDirection.EAST), MapDirection.NORTH);
//        assertEquals(MapDirection.previous(MapDirection.SOUTH), MapDirection.EAST);
//        assertEquals(MapDirection.previous(MapDirection.WEST), MapDirection.SOUTH);
//    }

    @Test
    public void TestPrevious() {
        assertEquals(MapDirection.NORTH.previous(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH);
    }

}
