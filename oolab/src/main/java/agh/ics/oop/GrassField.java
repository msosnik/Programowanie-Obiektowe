package agh.ics.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {

    private int grassCount;
    private List<Grass> grassList;
    private static Random random = new Random();

    GrassField(int grassCount) {
        this.grassCount = grassCount;
        grassList = new ArrayList<>();

        while (grassList.size()<grassCount) {
            int x = random.nextInt(0, (int) sqrt(grassCount*10));
            int y = random.nextInt(0, (int) sqrt(grassCount*10));

            Vector2d position = new Vector2d(x, y);

            if (!isOccupied(position)) {
                Grass grass = new Grass(position);
                grassList.add(grass);
            }
        }
    }


    @Override
    protected boolean isOnMap(Vector2d position) {
        return true;
    }


    @Override
    public Object objectAt(Vector2d position) {
        Object result = super.objectAt(position);
        if (result == null) {
            for (Grass g : grassList) {
                if (g.getPosition().equals(position)) {
                    result = g;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    protected Vector2d getLowerLeft() {
        Vector2d v = super.getLowerLeft();
        int xMin = grassList.stream().mapToInt( g -> g.getPosition().x).min().getAsInt();
        int yMin = grassList.stream().mapToInt( g -> g.getPosition().y).min().getAsInt();
        Vector2d result = new Vector2d(xMin, yMin);
        return result.lowerLeft(v);
    }

    @Override
    protected Vector2d getUpperRight() {
        Vector2d v = super.getUpperRight();
        int xMax = grassList.stream().mapToInt( g -> g.getPosition().x).max().getAsInt();
        int yMax = grassList.stream().mapToInt( g -> g.getPosition().y).max().getAsInt();
        Vector2d result = new Vector2d(xMax, yMax);
        return result.upperRight(v);
    }
}
