import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {

    @Test
    public void testRectangularMap(){
        IWorldMap map = new RectangularMap(5, 5);
        Animal animal1 = new Animal(map);
        assertNotNull(animal1);
//        assertFalse( map.place(new Animal(map)));
        assertEquals(animal1, map.objectAt(animal1.getPosition()));
        assertTrue(map.isOccupied(animal1.getPosition()));
        assertFalse(map.canMoveTo(animal1.getPosition()));

        animal1.move(MoveDirection.FORWARD);

        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.canMoveTo(new Vector2d(2, 2)));
        Animal animal2 = new Animal(map);
        assertNotNull(animal2);
        assertEquals(animal1, map.objectAt(animal1.getPosition()));
        assertTrue(map.isOccupied(animal1.getPosition()));
        assertFalse(map.canMoveTo(animal1.getPosition()));

        for (int i =0; i<5; i++) animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(2, 5));

        animal2.move(MoveDirection.LEFT);
        for (int i=0; i<5; i++) {

            Vector2d position = new Vector2d(Math.min(2 + i, 5), 2);
            assertEquals(animal2.getPosition(), position);
            assertEquals(map.objectAt(position), animal2);
            animal2.move(MoveDirection.BACKWARD);
            System.out.print(map.toString());
        }

        RectangularMap rMap = new RectangularMap(5, 5);
//        Animal animal3 = new Animal(rMap, new Vector2d(7, 6));
        Animal animal4 = new Animal(rMap, new Vector2d(0, 0));
//        assertNull(rMap.objectAt(animal3.getPosition()));
        assertEquals(rMap.objectAt(animal4.getPosition()), animal4);


    }
}
