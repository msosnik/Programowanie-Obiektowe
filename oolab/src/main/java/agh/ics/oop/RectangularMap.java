package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap {

    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private final Vector2d upperRight;

    public RectangularMap(int width, int height) {
        upperRight = new Vector2d(width, height);
    }

    @Override
    public boolean isOnMap(Vector2d position) {
        return position.follows(LOWER_LEFT) && position.precedes(upperRight);
    }

    protected Vector2d getLowerLeft() {
        return LOWER_LEFT;
    }

    protected Vector2d getUpperRight() {
        return upperRight;
    }

}
