package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{

    private MoveDirection[] directions;
    private final IWorldMap map;
    private final Vector2d[] positions;
    private List<ISimulationStepObserver> observerList;
    private int moveDelay;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions) {
        this.directions = directions;
        this.map = map;
        this.positions = positions;
        this.observerList = new ArrayList<>();
        this.moveDelay = 300;
    }

    public SimulationEngine(IWorldMap map, Vector2d[] positions) {
        this(new MoveDirection[0], map, positions);
    }

    public void setDirections(MoveDirection[] moves) {
        this.directions = moves;
    }

    public void setMoveDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    @Override
    public void run() {
        List<Animal> animalList = new ArrayList<>();
        for (Vector2d p : positions) {
            Animal animal = new Animal(map, p);
            animalList.add(animal);
        }

        int currentAnimal = 0;
        int currentStep = 0;
        for(MoveDirection direction : directions){
            animalList.get(currentAnimal).move(direction);
            currentAnimal = (currentAnimal+1)%animalList.size();
            notifyObservers(currentStep);
            currentStep++;

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            System.out.println("Move direction: " + direction.toString());
//            System.out.println(map);
        }

    }

    public void addObserver(ISimulationStepObserver observer){
        observerList.add(observer);
    }

    public void removeObserver(ISimulationStepObserver observer) {
        observerList.remove(observer);
    }

    private void notifyObservers(int currentStep){
        for (ISimulationStepObserver o : observerList) {
            o.stepCompleted(currentStep);
        }
    }
}
