package agh.ics.oop;

import java.util.Arrays;

public class World {
    public static void main(String[] args){

        System.out.println("System started");
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map.toString());

        System.out.println("System finished");

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
