

import org.junit.jupiter.api.Test;
import agh.ics.oop.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimEngTest {

    @Test
    void run() {
        IWorldMap map = new RectangularMap(4, 4);
        String[] move = {"f", "f", "f", "r", "r", "b"};
        MoveDirection[] dir = OptionsParser.parse(move);
        Vector2d[] sPos = {new Vector2d(2,2)};
        IEngine engine = new SimulationEngine(dir, map, sPos);
        engine.run();

        String output = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  5: -----------" + System.lineSeparator() +
                "  4: | | |S| | |" + System.lineSeparator() +
                "  3: | | | | | |" + System.lineSeparator() +
                "  2: | | | | | |" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | | | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertEquals(output, map.toString());

    }
    @Test
    void run3() {
        IWorldMap map = new RectangularMap(5, 5);
        String[] move = {"f", "r", "f", "r", "f", "b"};
        MoveDirection[] dir = OptionsParser.parse(move);
        Vector2d[] sPos = {new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(dir, map, sPos);
        engine.run();

        String output = " y\\x  0 1 2 3 4 5" + System.lineSeparator() +
                "  6: -------------" + System.lineSeparator() +
                "  5: | | | | |S| |" + System.lineSeparator() +
                "  4: | | | | | | |" + System.lineSeparator() +
                "  3: | | | | | | |" + System.lineSeparator() +
                "  2: | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | |" + System.lineSeparator() +
                " -1: -------------" + System.lineSeparator();
        assertEquals(output, map.toString());

    }
    @Test
    void run4() {
        IWorldMap map = new RectangularMap(4, 7);
        String[] move = {"l", "f", "r", "r", "r", "b", "f", "r", "f", "r", "f", "b"};
        MoveDirection[] dir = OptionsParser.parse(move);
        Vector2d[] sPos = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(dir, map, sPos);
        engine.run();

        String output = " y\\x  0 1 2 3 4" + System.lineSeparator() +
                "  8: -----------" + System.lineSeparator() +
                "  7: | | | | | |" + System.lineSeparator() +
                "  6: | | | | | |" + System.lineSeparator() +
                "  5: | | | |W| |" + System.lineSeparator() +
                "  4: | | | | | |" + System.lineSeparator() +
                "  3: | | | | | |" + System.lineSeparator() +
                "  2: | | | | |E|" + System.lineSeparator() +
                "  1: | | | | | |" + System.lineSeparator() +
                "  0: | | | | | |" + System.lineSeparator() +
                " -1: -----------" + System.lineSeparator();
        assertEquals(output, map.toString());

    }
    @Test
    void run5() {
        IWorldMap map = new RectangularMap(6, 10);
        String[] move = {"f", "f", "f","l", "f", "r", "r", "r", "b", "f", "r", "f", "r", "f", "b", "r", "r", "b"};
        MoveDirection[] dir = OptionsParser.parse(move);
        Vector2d[] sPos = {new Vector2d(2, 1), new Vector2d(3, 7), new Vector2d(4,4)};
        IEngine engine = new SimulationEngine(dir, map, sPos);
        engine.run();

        String output = " y\\x  0 1 2 3 4 5 6" + System.lineSeparator() +
                " 11: ---------------" + System.lineSeparator() +
                " 10: | | | | | | | |" + System.lineSeparator() +
                "  9: | | | | | | | |" + System.lineSeparator() +
                "  8: | | | |W| | | |" + System.lineSeparator() +
                "  7: | | | | | | | |" + System.lineSeparator() +
                "  6: | | | | | | | |" + System.lineSeparator() +
                "  5: | | |E| | | | |" + System.lineSeparator() +
                "  4: | | | | | | | |" + System.lineSeparator() +
                "  3: | | |S| | | | |" + System.lineSeparator() +
                "  2: | | | | | | | |" + System.lineSeparator() +
                "  1: | | | | | | | |" + System.lineSeparator() +
                "  0: | | | | | | | |" + System.lineSeparator() +
                " -1: ---------------" + System.lineSeparator();
        assertEquals(output, map.toString());

    }
}