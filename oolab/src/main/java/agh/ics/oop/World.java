package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.util.Arrays;

public class World {
    public static void main(String[] args){

        try {
            System.out.println("System started");

            Application.launch(App.class, args);

            System.out.println("System finished");
        } catch ( IllegalArgumentException ex) {
            System.err.println(ex);

        }

    }
    static void run(MoveDirection[] directions, String[] zwierzaki){
        for(MoveDirection direction: directions) {
            if (MoveDirection.FORWARD.equals(direction)) {
                System.out.print("Zwierzak idzie do przodu ");
            }
            if (MoveDirection.BACKWARD.equals(direction)) {
                System.out.print("Zwierzak idzie do tyłu ");
            }
            if (MoveDirection.RIGHT.equals(direction)) {
                System.out.print("Zwierzak skręca w prawo ");
            }
            if (MoveDirection.LEFT.equals(direction)) {
                System.out.print("Zwierzak skręca w lewo ");
            }
            System.out.print(zwierzaki[0]);
            for (int i = 1; i < zwierzaki.length; i++) {
                System.out.print(", " + zwierzaki[i]);
            }
            System.out.println();
        }
    }
    private static MoveDirection[] stringsToDirections(String[] args){
        int length = args.length;
        MoveDirection[] result = new MoveDirection[length];
        int i = 0;
        for (String arg: args) {
            if (arg.equals("f")) {
                result[i] = MoveDirection.FORWARD;
                i++;
            }
            if (arg.equals("b")) {
                result[i] = MoveDirection.BACKWARD;
                i++;
            }
            if (arg.equals("l")) {
                result[i] = MoveDirection.LEFT;
                i++;
            }
            if (arg.equals("r")) {
                result[i] = MoveDirection.RIGHT;
                i++;
            }

        }
        return Arrays.copyOfRange(result, 0, i);
    }
}
