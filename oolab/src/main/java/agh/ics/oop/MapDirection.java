package agh.ics.oop;

public enum MapDirection {
    NORTH(new Vector2d(0, 1), "North"),
    EAST(new Vector2d(1, 0), "East"),
    SOUTH(new Vector2d(0, -1), "South"),
    WEST(new Vector2d(-1, 0), "West");

    private final Vector2d unitVector;
    private final String displayName;

    MapDirection(Vector2d unitVector, String displayName) {
        this.unitVector = unitVector;
        this.displayName = displayName;
    }

    public String toStringPL() {
        return switch (this) {
            case NORTH -> "Północ";
            case EAST -> "Wschód";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
        };
    }

    public String toString() {
        return this.displayName;
    }
        public static MapDirection next(MapDirection direction) {
        MapDirection next = switch (direction) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
       return next;
    }

    public MapDirection next() {
        MapDirection next = switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
        return next;

    }

    public static MapDirection previous(MapDirection direction) {
        MapDirection prev = next(next(next(direction)));
        return  prev;
    }

    public MapDirection previous() {
        MapDirection prev = this.next().next().next();
        return prev;
    }

    public Vector2d toUnitVector(MapDirection direction) {
        Vector2d unitVector = switch (direction) {
            case NORTH -> new Vector2d(0, 1);
            case EAST ->  new Vector2d(1, 0);
            case SOUTH ->  new Vector2d(0, -1);
            case WEST ->  new Vector2d(-1, 0);
        };
        return unitVector;
    }

//    public Vector2d toUnitVector() {
//
//        Vector2d unitVector = switch (this) {
//            case NORTH -> new Vector2d(0, 1);
//            case EAST ->  new Vector2d(1, 0);
//            case SOUTH ->  new Vector2d(0, -1);
//            case WEST ->  new Vector2d(-1, 0);
//        };
//        return unitVector;
//    }
    public Vector2d toUnitVector() {
        return this.unitVector;
    }
}
