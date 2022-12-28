package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine, Runnable{

    private final RectangularGrassField map;
    private int currentStep;
    private List<ISimulationStepObserver> observerList;
    private int moveDelay;
    private final int genomeLength;

    private boolean paused;


    public SimulationEngine(RectangularGrassField map, int animalCount, int genomeLength) {
        this.map = map;
        this.observerList = new ArrayList<>();
        this.moveDelay = 300;
        this.currentStep = 0;
        this.paused = true;
        this.genomeLength = genomeLength;
        initAnimals(animalCount);
    }

    private void initAnimals(int animalCount) {
        for (int i=0; i<animalCount; i++) {
            Vector2d p = map.generateElementPosition();
            Animal animal = new Animal(map, p, genomeLength);
        }
    }


    public void setAnimationStepDelay(int moveDelay) {
        this.moveDelay = moveDelay;
    }

    @Override
    public void run() {

        MoveDirection[] directions = {MoveDirection.FORWARD,
                MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARD, MoveDirection.BACKWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARD, MoveDirection.BACKWARD,
        };
        List<Animal> animalList = map.getAnimals();

        for (MoveDirection direction : directions) {
            for (Animal a : animalList) {
                a.move(direction);
            }

            notifyObservers(currentStep);
            currentStep++;

            if (paused)
                return;

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (paused)
                return;

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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean b) {
        paused = b;
    }
}
