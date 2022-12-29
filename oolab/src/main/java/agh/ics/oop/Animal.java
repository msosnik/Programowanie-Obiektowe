package agh.ics.oop;

import agh.ics.oop.gui.ESimulationProperty;
import agh.ics.oop.gui.SimulationPropertyFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private static Random random = new Random();


    //    public static final Vector2d MAP_BOTTOM = new Vector2d(0, 0);
//    public static final Vector2d MAP_TOP = new Vector2d(4, 4);
    private static Vector2d DEFAULT_POSITION = new Vector2d(2, 2);
    private IWorldMap map;
    private MapDirection orientation = MapDirection.NORTH;

    final private List<Integer> genome;
    private int currentGeneIndex;

    private int birthDay;
    private int energy;


    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int genomeLength, int startEnergy) {
        this(map, initialPosition, generateRandomGenome(genomeLength), startEnergy, 0);
    }

    public Animal(IWorldMap map, Vector2d initialPosition, List<Integer>genome, int startEnergy, int currentDy) {
        this.map = map;
        this.position = initialPosition;
        this.map.place(this);
        this.genome = genome;
        this.currentGeneIndex = 0;

        this.energy = startEnergy;
        this.birthDay = currentDy;
    }

    private static List<Integer> generateRandomGenome(int genomeLength) {
        List<Integer> result = new ArrayList<>();
        for (int i =0; i<genomeLength; i++) {
            int gene = random.nextInt(0, 8);
            result.add(gene);
        }
        return result;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String toString() {
//        return "Animal position: " + position.toString() + " Animal orientation: " + orientation.toString();
        return orientation.toString();
    }


    public void move(SimulationPropertyFile simulationPropertyFile) {

        // rotate
        this.orientation = this.orientation.rotate(genome.get(currentGeneIndex));
        currentGeneIndex = (currentGeneIndex + 1) % this.genome.size();

        // move
        Vector2d newPosition = this.position.add(orientation.toUnitVector());

        if (map.canMoveTo(newPosition)) {
            this.position = newPosition;
        } else {
            if (simulationPropertyFile.getIntValue(ESimulationProperty.wariantMapy) == 1) {
                // kula ziemnska
                int y = newPosition.y;
                if ((y == -1) || (y == map.getHeight())) {
                    this.orientation = orientation.rotate(4);
                } else {
                    int x = newPosition.x;
                    if (x == -1) {
                        x = map.getWidth() - 1;
                    } else if (x == map.getWidth()) {
                        x = 0;
                    }
                    this.position = new Vector2d(x, y);
                }
            } else {
                // piekielny portal
                this.position = map.generateRandomPosition();
                this.energy -= simulationPropertyFile.getIntValue(ESimulationProperty.energiaRodzicowDoTworzeniaPotomka);
            }
        }
        this.energy--;
    }
    public int getBirthDay() {
        return birthDay;
    }
}
