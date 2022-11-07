import agh.ics.oop.MoveDirection;
import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OptionParserTest {

    String[] case1 = {"a", "b", "c", "d", "e", "f"};
    String[] case2 = {"f", "f", "l", "b", "r"};
    String[] case3 = {"a", "b", "c", "d", "e"};
    String[] case4 = {"f", "g", "h", "i", "j", "k", "l", "r"};
    String[] case5 = {"l"};
    String[] case6 = {"c", "d", "h", "s"};

    MoveDirection[] case1return = {MoveDirection.BACKWARD, MoveDirection.FORWARD};
    MoveDirection[] case2return = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.RIGHT};
    MoveDirection[] case3return = {MoveDirection.BACKWARD};
    MoveDirection[] case4return = {MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT};
    MoveDirection[] case5return = {MoveDirection.LEFT};
    MoveDirection[] case6return = {};

    @Test
    public void testParse(){

        assertArrayEquals(OptionsParser.parse(case1), case1return);
        assertArrayEquals(OptionsParser.parse(case2), case2return);
        assertArrayEquals(OptionsParser.parse(case3), case3return);
        assertArrayEquals(OptionsParser.parse(case4), case4return);
        assertArrayEquals(OptionsParser.parse(case5), case5return);
        assertArrayEquals(case6return, OptionsParser.parse(case6));
        assertFalse(OptionsParser.parse(case1).equals(case1return));
    }
}
