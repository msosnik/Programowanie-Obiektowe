package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{


    protected Map<Vector2d, IMapElement> mapElements = new HashMap<>();

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        IMapElement movedElement = mapElements.get(oldPosition);
        mapElements.remove(oldPosition);
        mapElements.put(newPosition, movedElement);

    }

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
        if (canMoveTo(animal.getPosition())) {
            mapElements.put(animal.getPosition(), animal);
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
                return mapElements.get(position);

    }

    @Override
    public String toString() {
        MapVisualizer mapDrawer = new MapVisualizer(this);
        return mapDrawer.draw(getLowerLeft(), getUpperRight());

    }
    protected Vector2d getLowerLeft() {
        int xMin = mapElements.keySet().stream().mapToInt(p -> p.x).min().getAsInt();
        int yMin = mapElements.keySet().stream().mapToInt(p -> p.y).min().getAsInt();
//        int xMin = mapElements.stream().mapToInt(g -> g.getPosition().x).min().getAsInt();
//        int yMin = mapElements.stream().mapToInt(g -> g.getPosition().y).min().getAsInt();
        return new Vector2d(xMin, yMin);
    }
    
    protected Vector2d getUpperRight() {

//        int xMax = mapElements.stream().mapToInt(g -> g.getPosition().x).max().getAsInt();
//        int yMax = mapElements.stream().mapToInt(g -> g.getPosition().y).max().getAsInt();
        int xMax = mapElements.keySet().stream().mapToInt(p -> p.x).max().getAsInt();;
        int yMax = mapElements.keySet().stream().mapToInt(p -> p.y).max().getAsInt();;
        return new Vector2d(xMax, yMax);
    }
}
