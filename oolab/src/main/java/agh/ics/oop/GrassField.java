package agh.ics.oop;

public class GrassField implements IWorldMap{

    int grassFieldsNumber;
    GrassField(int grassCount) {
        this.grassFieldsNumber = grassCount;

    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return null;
    }
}
