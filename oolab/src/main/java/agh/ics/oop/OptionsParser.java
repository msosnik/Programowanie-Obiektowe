package agh.ics.oop;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class OptionsParser {

    public static void main(String[] args){
        String[] text = {"a"};
        System.out.println(OptionsParser.parse(text).length);
        System.out.println("działa");
    }

    public static MoveDirection[] parse(String[] directions){
        int length = directions.length;
        MoveDirection[] result = new MoveDirection[length];
        int i = 0;
        for (String arg: directions) {
            MoveDirection move = MoveDirection.fromString(arg);
            if (move!=null){
                result[i] = move;
                i++;
            }
        }
        MoveDirection[] retval = Arrays.copyOfRange(result, 0, i);
        return retval;
    }

    public static MoveDirection[] parse2(String[] directions){
        return Arrays.stream(directions).map(s -> MoveDirection.fromString(s)).filter(Objects::nonNull).collect(Collectors.toList()).toArray(new MoveDirection[0]);

    }

}
