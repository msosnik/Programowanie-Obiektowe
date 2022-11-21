import agh.ics.oop.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {

    IWorldMap map = new RectangularMap(5, 4);
//    Animal animal1 = new Animal(map);
//    Animal animal2 = new Animal(map, new Vector2d(1, 3));
//
//    Vector2d[] positions = {animal1.getPosition(), animal2.getPosition()};
    Vector2d[] pos2 = {new Vector2d(1, 2), new Vector2d(3, 2)};

    String[] moves = {"f", "b", "l", "b", "b", "r", "b", "f"};


    IEngine engine = new SimulationEngine(OptionsParser.parse(moves), map, pos2);

    @Test
    public void testSimulationEngine() {
        IWorldMap map = new RectangularMap(5, 4);
        Vector2d[] positions = {new Vector2d(1, 2), new Vector2d(3, 2)};

        String[] moves = {"f", "b", "l", "b", "b", "r", "b", "f"};

        IEngine engine = new SimulationEngine(OptionsParser.parse(moves), map, positions);
        engine.run();
        System.out.println(map.toString());

        Vector2d animal1Pos = new Vector2d(3, 3);
        Vector2d animal2Pos = new Vector2d(0, 4);

        assertTrue(map.isOccupied(animal1Pos));
        assertFalse(map.isOccupied(new Vector2d(0, 0)));
        assertNull(map.objectAt(new Vector2d(2, 4)));
        assertNotNull(map.objectAt(animal1Pos));
        assertEquals(map.place(new Animal(map, animal2Pos)), false);

        GrassField gMap = new GrassField(17);
        Vector2d[] gPositions = {new Vector2d(1, 2), new Vector2d(3, 2)};
        String[] gMoves = {"f", "b", "l", "b", "b", "r", "b", "f"};

        IEngine gEngine = new SimulationEngine(OptionsParser.parse(gMoves), gMap, gPositions);
        engine.run();

        assertEquals(1, 1);

    }
}
