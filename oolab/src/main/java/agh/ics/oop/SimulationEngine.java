package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine {

    private final MoveDirection[] directions;
    private final IWorldMap map;
    private final Vector2d[] positions;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        this.map = map;
        this.positions = positions;
    }
    @Override
    public void run() {
        List<Animal> animalList = new ArrayList<>();
        for (Vector2d p : positions) {
            Animal animal = new Animal(map, p);
            animalList.add(animal);
        }

        int currentAnimal = 0;
        for(MoveDirection direction : directions){
            animalList.get(currentAnimal).move(direction);
            currentAnimal = (currentAnimal+1)%animalList.size();
//            System.out.println("Move direction: " + direction.toString());
//            System.out.println(map);
        }

    }
}
