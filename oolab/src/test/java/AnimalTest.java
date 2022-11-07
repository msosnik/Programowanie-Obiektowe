import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    IWorldMap worldMap = new RectangularMap(4,4);
    Animal animal1 = new Animal(worldMap);

    Vector2d startPos = new Vector2d(2, 2);

    @Test
    public void testMove() {
        assertEquals(animal1.getPosition(), startPos);
        assertEquals(animal1.getOrientation(), MapDirection.NORTH);

        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(2, 3));
        assertEquals(animal1.getOrientation(), MapDirection.NORTH);
        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(2, 4));
        assertEquals(animal1.getOrientation(), MapDirection.NORTH);
        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(2, 4));
        assertEquals(animal1.getOrientation(), MapDirection.NORTH);

        animal1.move(MoveDirection.RIGHT);
        assertEquals(animal1.getPosition(), new Vector2d(2, 4));
        assertEquals(animal1.getOrientation(), MapDirection.EAST);

        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(3, 4));
        assertEquals(animal1.getOrientation(), MapDirection.EAST);

        animal1.move(MoveDirection.RIGHT);
        assertEquals(animal1.getPosition(), new Vector2d(3, 4));
        assertEquals(animal1.getOrientation(), MapDirection.SOUTH);

        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(3, 3));
        assertEquals(animal1.getOrientation(), MapDirection.SOUTH);

        animal1.move(MoveDirection.BACKWARD);
        assertEquals(animal1.getPosition(), new Vector2d(3, 4));
        assertEquals(animal1.getOrientation(), MapDirection.SOUTH);
        animal1.move(MoveDirection.BACKWARD);
        assertEquals(animal1.getPosition(), new Vector2d(3, 4));
        assertEquals(animal1.getOrientation(), MapDirection.SOUTH);

        animal1.move(MoveDirection.LEFT);
        assertEquals(animal1.getPosition(), new Vector2d(3, 4));
        assertEquals(animal1.getOrientation(), MapDirection.EAST);

        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(4, 4));
        assertEquals(animal1.getOrientation(), MapDirection.EAST);
        animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(4, 4));
        assertEquals(animal1.getOrientation(), MapDirection.EAST);

        animal1.move(MoveDirection.RIGHT);
        animal1.move(MoveDirection.RIGHT);
        animal1.move(MoveDirection.BACKWARD);
        assertEquals(animal1.getPosition(), new Vector2d(4, 4));
        assertEquals(animal1.getOrientation(), MapDirection.WEST);

        for (int i=0; i<5; i++)
            animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(0, 4));
        assertEquals(animal1.getOrientation(), MapDirection.WEST);

        animal1.move(MoveDirection.LEFT);
        for (int i=0; i<5; i++)
            animal1.move(MoveDirection.FORWARD);
        assertEquals(animal1.getPosition(), new Vector2d(0, 0));
        assertEquals(animal1.getOrientation(), MapDirection.SOUTH);

    }
}
