package project.mapElements;

import project.map.IWorldMap;
import project.map.MapDirection;
import project.map.Vector2d;
import project.gui.ESimulationProperty;
import project.gui.SimulationPropertyFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private static Random random = new Random();


    //    public static final Vector2d MAP_BOTTOM = new Vector2d(0, 0);
//    public static final Vector2d MAP_TOP = new Vector2d(4, 4);
    private IWorldMap map;
    private MapDirection orientation;

    final private List<Integer> genome; // przydałaby się osobna klasa na genom
    private int currentGeneIndex;

    private final int birthDay;
    private int energy;
    private int childrenCount = 0;

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) { // czy upublicznienie tej metody jest najlepszym rozwiązaniem?
        this.energy = energy;
    }

    public List<Integer> getGenome() {
        return genome; // dehermetyzacja
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int genomeLength, int startEnergy) {
        this(map, initialPosition, generateRandomGenome(genomeLength), startEnergy, 0);
    }

    public Animal(IWorldMap map, Vector2d initialPosition, List<Integer> genome, int startEnergy, int currentDy) {
        this.map = map;
        this.position = initialPosition;
        this.map.place(this);
        this.genome = genome;
        this.currentGeneIndex = random.nextInt(genome.size());
        this.orientation = MapDirection.values()[random.nextInt(8)];

        this.energy = startEnergy;
        this.birthDay = currentDy;
    }

    private static List<Integer> generateRandomGenome(int genomeLength) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < genomeLength; i++) {
            int gene = random.nextInt(0, 8);
            result.add(gene);
        }
        return result;
    }

    public String toString() {
//        return "Animal position: " + position.toString() + " Animal orientation: " + orientation.toString();
        return orientation.toString();
    }


    public void move(SimulationPropertyFile simulationPropertyFile) {

        // rotate
        this.orientation = this.orientation.rotate(genome.get(currentGeneIndex));

        if (simulationPropertyFile.getIntValue(ESimulationProperty.wariantZachowaniaZwierzakow) == 1) { // if nie jest najlepszym rozwiązaniem; polglish
            //pełna predestynacja
            currentGeneIndex = (currentGeneIndex + 1) % this.genome.size();

        } else {
            //trochę szaleństwa
            currentGeneIndex = (currentGeneIndex + random.nextInt(2, this.genome.size())) % this.genome.size();

        }

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
