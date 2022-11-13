import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    public void testGrassField(){
        IWorldMap map = new GrassField(5);
        Animal animal1 = new Animal(map);
        assertNotNull(animal1);
        assertFalse( map.place(new Animal(map)));
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
        assertEquals(animal1.getPosition(), new Vector2d(2, 8));

        animal2.move(MoveDirection.LEFT);
        for (int i=0; i<5; i++) {

            Vector2d position = new Vector2d(2 + i, 2);
            assertEquals(animal2.getPosition(), position);
            assertEquals(map.objectAt(position), animal2);
            animal2.move(MoveDirection.BACKWARD);
            System.out.print(map.toString());
        }

        IWorldMap grassField = new GrassField(10);
        Animal animal3 = new Animal(grassField, new Vector2d(7, 6));
        Animal animal4 = new Animal(grassField, new Vector2d(0, 0));
        assertEquals(grassField.objectAt(animal4.getPosition()), animal4);


    }
}
