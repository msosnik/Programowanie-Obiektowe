package agh.ics.oop;

public class Animal {

    public static final Vector2d MAP_BOTTOM = new Vector2d(0, 0);
    public static final Vector2d MAP_TOP = new Vector2d(4, 4);
    private Vector2d position = new Vector2d(2, 2);
    private MapDirection orientation = MapDirection.NORTH;

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
        return "Animal position: " + position.toString() + " Animal orientation: " + orientation.toString();
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveDirection direction) {

        Vector2d newPosition = null;
        switch(direction) {
            case LEFT:
                this.orientation = orientation.previous();
                break;
            case RIGHT:
                this.orientation = orientation.next();
                break;
            case FORWARD:
                newPosition = this.position.add(this.orientation.toUnitVector());
                break;
            case BACKWARD:
                newPosition = this.position.subtract(this.orientation.toUnitVector());
                break;
        }
        if (newPosition!=null && newPosition.follows(MAP_BOTTOM) && newPosition.precedes(MAP_TOP)){
            this.position = newPosition;
        }
    }
}
