package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IWorldMap {


    protected List<IMapElement> mapElements = new ArrayList<>();

    @Override
    public boolean canMoveTo(Vector2d position) {
        if (position == null)
            return false;
        return isOnMap(position);
    }

    abstract protected boolean isOnMap(Vector2d position);

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        Vector2d position = animal.getPosition();
        if (canMoveTo(position)) {

            mapElements.add(animal);
            return true;
        }
        throw new IllegalArgumentException(animal + " can't be placed on top of another animal");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return mapElements.stream().anyMatch(e -> e.isAt(position));
    }

    public List<IMapElement> getMapElements() {
        return mapElements;
    }


    public List<Animal> getAnimals() {
        return mapElements.stream()
                .filter(o -> o instanceof Animal)
                .map(o -> (Animal) o)
                .collect(Collectors.toList());
    }

    public List<Grass> getGrassList() {
        return mapElements.stream()
                .filter(o -> o instanceof Grass)
                .map(o -> (Grass) o)
                .collect(Collectors.toList());
    }

}
