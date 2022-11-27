package agh.ics.oop;

import java.util.Random;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private int grassCount;
    private static Random random = new Random();

    private MapBoundary mapBoundary;

    public GrassField(int grassCount) {
        this.grassCount = grassCount;
        this.mapBoundary = new MapBoundary();

        while (this.mapElements.size()<grassCount) {
            Vector2d position = generateGrassPosition();

            Grass grass = new Grass(position);
            mapElements.put(position, grass);
            mapBoundary.addElement(position);
        }
    }

    private Vector2d generateRandomPosition() {
        int x = random.nextInt(0, (int) sqrt(this.grassCount *10));
        int y = random.nextInt(0, (int) sqrt(this.grassCount *10));

        Vector2d position = new Vector2d(x, y);
        return position;
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {
            eatGrassIfPresent(position);

            mapElements.put(position, animal);
            mapBoundary.addElement(position);
            return true;
        }
        throw new IllegalArgumentException(animal + " can't be placed on top of another animal");
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        eatGrassIfPresent(newPosition);
        super.positionChanged(oldPosition, newPosition);
        mapBoundary.positionChanged(oldPosition, newPosition);

    }



    @Override
    protected boolean isOnMap(Vector2d position) {
        return true;
    }


    private Vector2d generateGrassPosition() {
        Vector2d newPosition = generateRandomPosition();

        while (isOccupied(newPosition)) {

            newPosition = generateRandomPosition();
        }
        return newPosition;

    }

    private void eatGrassIfPresent(Vector2d oldGrassPosition) {
        if (this.objectAt(oldGrassPosition) instanceof Grass) {

            Grass grass = (Grass) mapElements.remove(oldGrassPosition);

            Vector2d newGrassPosition = this.generateGrassPosition();
            grass.setPosition(newGrassPosition);
            mapElements.put(newGrassPosition, grass);
            mapBoundary.positionChanged(oldGrassPosition, newGrassPosition);
        }
    }

    @Override
    protected Vector2d getLowerLeft() {
        return mapBoundary.getLoweLeft();
    }

    @Override
    protected Vector2d getUpperRight() {
        return mapBoundary.getUpperRight();
    }
}
