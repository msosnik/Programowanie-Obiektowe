package agh.ics.oop;

import java.util.Random;

public class RectangularGrassField extends AbstractWorldMap {
    private static Random random = new Random();

    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private final Vector2d upperRight;

    public RectangularGrassField(int width, int height, int grassCount) {

        upperRight = new Vector2d(width, height);

        while (this.mapElements.size()<grassCount) {
            Vector2d position = generateElementPosition();

            Grass grass = new Grass(position);
            mapElements.put(position, grass);
        }

    }
    private Vector2d generateRandomPosition() {
        int x = random.nextInt(0, upperRight.x);
        int y = random.nextInt(0, upperRight.y);

        Vector2d position = new Vector2d(x, y);
        return position;
    }

    public Vector2d generateElementPosition() {
        Vector2d newPosition = generateRandomPosition();

        while (isOccupied(newPosition)) {

            newPosition = generateRandomPosition();
        }
        return newPosition;
    }

    @Override
    public boolean isOnMap(Vector2d position) {
        return position.follows(LOWER_LEFT) && position.precedes(upperRight);
    }

    public Vector2d getLowerLeft() {
        return LOWER_LEFT;
    }

    public Vector2d getUpperRight() {
        return upperRight;
    }

}
