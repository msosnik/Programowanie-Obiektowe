package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {


    protected List<Animal> animals = new ArrayList<>();


    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position == null)
            return false;
        boolean isOccupiedByAnimal = objectAt(position) instanceof Animal;
        if (isOnMap(position) && !isOccupiedByAnimal)
            return true;
        return false;
    }

    abstract protected boolean isOnMap(Vector2d position);

    @Override
    public boolean place(Animal animal) {
        if (!isOccupied(animal.getPosition()) && isOnMap(animal.getPosition())) {
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
        return mapDrawer.draw(getLowerLeft(), getUpperRight());

    }
    protected Vector2d getLowerLeft() {
        int xMin = animals.stream().mapToInt( g -> g.getPosition().x).min().getAsInt();
        int yMin = animals.stream().mapToInt( g -> g.getPosition().y).min().getAsInt();
        return new Vector2d(xMin, yMin);
    }
    
    protected Vector2d getUpperRight() {
        int xMax = animals.stream().mapToInt( g -> g.getPosition().x).max().getAsInt();
        int yMax = animals.stream().mapToInt( g -> g.getPosition().y).max().getAsInt();
        return new Vector2d(xMax, yMax);
    }
}
