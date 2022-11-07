package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap {

    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private final int width;
    private final int height;
    private final Vector2d upperRight;


    private List<Animal> animals = new ArrayList<>();

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;
        upperRight = new Vector2d(this.width, this.height);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position == null)
            return false;
        if (isOnMap(position) && !isOccupied(position))
            return true;
        return false;
    }

    private boolean isOnMap(Vector2d position) {
        return position.follows(LOWER_LEFT) && position.precedes(upperRight);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal a : animals) {
            if (a.isAt(position))
                return a;
        }

        return null;
    }

    @Override
    public String toString() {
        MapVisualizer mapDrawer = new MapVisualizer(this);
        return mapDrawer.draw(LOWER_LEFT, upperRight);

    }
}
