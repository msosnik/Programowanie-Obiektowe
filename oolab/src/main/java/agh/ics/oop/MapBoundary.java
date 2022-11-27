package agh.ics.oop;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{

    private SortedSet<Vector2d> sortedElementsByX;
    private SortedSet<Vector2d> sortedElementsByY;

    public MapBoundary() {
        this.sortedElementsByX = new TreeSet<>( (v1, v2) -> Vector2d.compareX(v1, v2));
        this.sortedElementsByY = new TreeSet<>( (v1, v2) -> Vector2d.compareY(v1, v2));

    }

    public void addElement(Vector2d position) {
        this.sortedElementsByX.add(position);
        this.sortedElementsByY.add(position);

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        sortedElementsByX.remove(oldPosition);
        sortedElementsByX.add(newPosition);

        sortedElementsByY.remove(oldPosition);
        sortedElementsByY.add(newPosition);


    }

    public Vector2d getLoweLeft() {
        return new Vector2d(sortedElementsByX.first().x, sortedElementsByY.first().y);
    }

    public Vector2d getUpperRight() {
        return new Vector2d(sortedElementsByX.last().x, sortedElementsByY.last().y);
    }
}
