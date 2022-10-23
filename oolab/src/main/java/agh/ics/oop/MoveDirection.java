package agh.ics.oop;

public enum MoveDirection {
    FORWARD, BACKWARD, LEFT, RIGHT;

    public static MoveDirection fromString(String s) {
        MoveDirection result = null;
        if (s.equals("f") || s.equals("forward")) {
            result = MoveDirection.FORWARD;
        }
        if (s.equals("b") || s.equals("backward")) {
            result = MoveDirection.BACKWARD;
        }
        if (s.equals("l") || s.equals("left")) {
            result = MoveDirection.LEFT;
        }
        if (s.equals("r") || s.equals("right")) {
            result = MoveDirection.RIGHT;
        }
        return result;
    }
}
