package agh.ics.oop;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class RectangularGrassField extends AbstractWorldMap {
    private static Random random = new Random();

    private static final Vector2d LOWER_LEFT = new Vector2d(0, 0);
    private final Vector2d upperRight;

    public RectangularGrassField(int width, int height, int grassCount) {

        upperRight = new Vector2d(width-1, height-1);

        Map<Vector2d, Grass> grassMap = new HashMap<>();
        plantGrass(grassCount, grassMap);

    }

    public void plantGrass(int grassCount, Map<Vector2d, Grass> grassMap) {
        int fieldSize = getWidth() * getHeight();
        int counter = 0;
        while (counter < grassCount && grassMap.size() < fieldSize) {
            Vector2d position = generateElementPosition(grassMap);
            Grass grass = new Grass(position);
            this.mapElements.add(grass);
            grassMap.put(position, grass);
            counter++;
        }
    }

    public Map<Vector2d, Grass> getPositionToGrassMap() {
        Map<Vector2d, Grass> grassMap = new HashMap<>();
        for (Grass grass : getGrassList()) {
            grassMap.put(grass.getPosition(), grass);
        }
        return grassMap;
    }
    public Vector2d generateRandomPosition() {
        int x = random.nextInt(0, getWidth());
        int y = random.nextInt(0, getHeight());

        Vector2d position = new Vector2d(x, y);
        return position;
    }

    public Vector2d generateRandomGrassPosition() {
        int x = random.nextInt(0, getWidth());

        int sizeOfRainForrest = getHeight() / 5;
        if (getHeight() % 5 != 0)
            sizeOfRainForrest += 1;
        int rainForestStartIndex = (getHeight() - sizeOfRainForrest) / 2;
        int newHeight = getHeight() + sizeOfRainForrest * 3;
        int yRandomNumber = random.nextInt(0, newHeight);
        int y = yRandomNumber < getHeight() ?
                yRandomNumber :
                rainForestStartIndex + (yRandomNumber-getHeight()) % sizeOfRainForrest;

        Vector2d position = new Vector2d(x, y);
        return position;
    }

    public int getHeight() {
        return upperRight.y + 1;
    }

    public int getWidth() {
        return upperRight.x + 1;
    }

    public Vector2d generateElementPosition(Map<Vector2d, Grass> grassMap) {
        Vector2d newPosition = generateRandomGrassPosition();
        while (grassMap.containsKey(newPosition)) {
            newPosition = generateRandomGrassPosition();
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

    public int getEmptyFieldCount() {
        int allFields = this.getWidth() * this.getHeight();
        int occupiedFields = mapElements.stream().map(e->e.getPosition()).collect(Collectors.toSet()).size();
        return allFields-occupiedFields;
    }

    public Map.Entry<String, Integer>  getMostPopularGenes() {
        Map<String, Integer> genomeOccurences = new HashMap<>();
        for (Animal a : getAnimals()) {
            String key = a.getGenome().toString();
            genomeOccurences.computeIfPresent(key, (k, v)-> v+1);
            genomeOccurences.computeIfAbsent(key, k-> 1);
        }
        Map.Entry<String, Integer> result = genomeOccurences.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null);
        return result;
    }
}
