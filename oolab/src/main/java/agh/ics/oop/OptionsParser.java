package agh.ics.oop;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class OptionsParser {

    public static MoveDirection[] parse(String[] directions){
        int length = directions.length;
        MoveDirection[] result = new MoveDirection[length];
        int i = 0;
        for (String arg: directions) {
            MoveDirection move = MoveDirection.fromString(arg);
            if (arg!=null){
                result[i] = move;
                i++;
            }
        }
        return Arrays.copyOfRange(result, 0, i);
    }
    public static MoveDirection[] parse2(String[] directions){
        return Arrays.stream(directions).map(s -> MoveDirection.fromString(s)).filter(Objects::nonNull).collect(Collectors.toList()).toArray(new MoveDirection[0]);

    }

}
