package agh.ics.oop;

import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private int grassCount;
    private static Random random = new Random();

    public GrassField(int grassCount) {
        this.grassCount = grassCount;

        while (this.mapElements.size()<grassCount) {
            Vector2d position = generateGrassPosition();

            Grass grass = new Grass(position);
            mapElements.put(position, grass);
        }
    }

    private Vector2d generateRandomPosition() {
        int x = random.nextInt(0, (int) sqrt(this.grassCount *10));
        int y = random.nextInt(0, (int) sqrt(this.grassCount *10));

        Vector2d position = new Vector2d(x, y);
        return position;
    }


    @Override
    protected boolean isOnMap(Vector2d position) {
        return true;
    }

    public Vector2d generateGrassPosition() {
        Vector2d newPosition = generateRandomPosition();

        while (isOccupied(newPosition)) {

            newPosition = generateRandomPosition();
        }
        return newPosition;

    }
}
