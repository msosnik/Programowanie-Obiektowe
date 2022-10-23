import agh.ics.oop.MoveDirection;
import agh.ics.oop.OptionsParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OptionParserTest {

    String[] case1 = {"a", "b", "c", "d", "e", "f"};
    String[] case2 = {"f", "f", "l", "b", "r"};
    String[] case3 = {"a", "b", "c", "d", "e"};
    String[] case4 = {"f", "g", "h", "i", "j", "k", "l", "r"};
    String[] case5 = {"b"};

    MoveDirection[] case1return = {MoveDirection.BACKWARD, MoveDirection.FORWARD};
    MoveDirection[] case2return = {MoveDirection.FORWARD, MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.BACKWARD, MoveDirection.RIGHT};
    MoveDirection[] case3return = {MoveDirection.BACKWARD};
    MoveDirection[] case4return = {MoveDirection.FORWARD, MoveDirection.LEFT, MoveDirection.RIGHT};
    MoveDirection[] case5return = {MoveDirection.BACKWARD};
    public static MoveDirection[] case6return;

    @Test
    public void testParse(){

        assertArrayEquals(OptionsParser.parse(case5), case5return);
        assertArrayEquals(OptionsParser.parse(case2), case2return);
    }
}
