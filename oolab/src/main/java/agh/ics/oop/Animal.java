package agh.ics.oop;

public class Animal extends AbstractMapElement{

//    public static final Vector2d MAP_BOTTOM = new Vector2d(0, 0);
//    public static final Vector2d MAP_TOP = new Vector2d(4, 4);
    private static Vector2d DEFAULT_POSITION = new Vector2d(2, 2);
    private IWorldMap map;
    private MapDirection orientation = MapDirection.NORTH;

    public Animal(IWorldMap map) {
        this(map, DEFAULT_POSITION);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        eatGrass(initialPosition);
        this.map.place(this);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
//        return "Animal position: " + position.toString() + " Animal orientation: " + orientation.toString();
        return orientation.toString();
    }

    public void move(MoveDirection direction) {

        Vector2d newPosition = null;
        switch (direction) {
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
        if (map.canMoveTo(newPosition)) {
            eatGrass(newPosition);
//        if (newPosition!=null && newPosition.follows(MAP_BOTTOM) && newPosition.precedes(MAP_TOP)){
            this.position = newPosition;
        }
    }

    private void eatGrass(Vector2d newPosition) {
        if (map.objectAt(newPosition) instanceof Grass) {
            Grass grass = (Grass) map.objectAt(newPosition);
            GrassField grassField = (GrassField) this.map;
            Vector2d newGrassPosition = grassField.generateGrassPosition();
            grass.setPosition(newGrassPosition);
        }
    }

}
