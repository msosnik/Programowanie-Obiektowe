package agh.ics.oop;

public class Grass extends AbstractMapElement {

    Grass(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }

    public void setPosition(Vector2d newGrassPosition) {
        this.position = newGrassPosition;
    }
}
