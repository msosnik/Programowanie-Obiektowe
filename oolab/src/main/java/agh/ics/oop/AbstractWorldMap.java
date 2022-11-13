package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {


    protected List<IMapElement> mapElements = new ArrayList<>();


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
            mapElements.add(animal);
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
        for (IMapElement e : mapElements) {
            if (e.isAt(position))
                return e;
        }

        return null;
    }

    @Override
    public String toString() {
        MapVisualizer mapDrawer = new MapVisualizer(this);
        return mapDrawer.draw(getLowerLeft(), getUpperRight());

    }
    protected Vector2d getLowerLeft() {
        int xMin = mapElements.stream().mapToInt(g -> g.getPosition().x).min().getAsInt();
        int yMin = mapElements.stream().mapToInt(g -> g.getPosition().y).min().getAsInt();
        return new Vector2d(xMin, yMin);
    }
    
    protected Vector2d getUpperRight() {
        int xMax = mapElements.stream().mapToInt(g -> g.getPosition().x).max().getAsInt();
        int yMax = mapElements.stream().mapToInt(g -> g.getPosition().y).max().getAsInt();
        return new Vector2d(xMax, yMax);
    }
}
