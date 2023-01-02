package project.simulation;

import project.map.Vector2d;
import project.map.RectangularGrassField;
import project.mapElements.Animal;
import project.mapElements.Grass;
import project.gui.ESimulationProperty;
import project.gui.SimulationPropertyFile;

import java.util.*;

public class SimulationEngine implements IEngine, Runnable{

    private static Random random = new Random();
    private final RectangularGrassField map;

    private int currentStep;
    private List<ISimulationStepObserver> observerList;
    private int moveDelay;
    private boolean paused;
    private int grassEnergy;
    private int reproductionEnergy;
    private int minMutations;
    private int maxMutations;

    private List<Integer> deadAnimalsAgeList;

    private SimulationPropertyFile simulationPropertyFile;
    private int mutationVariant;


    public SimulationEngine(RectangularGrassField map, SimulationPropertyFile propertyFileLoader) {

        this.simulationPropertyFile = propertyFileLoader;
        this.map = map;
        this.observerList = new ArrayList<>();
        this.moveDelay = propertyFileLoader.getIntValue(ESimulationProperty.animationStepDelay);
        this.grassEnergy = propertyFileLoader.getIntValue(ESimulationProperty.energiaZapewnianaPrzezZjedzenieJednejRosliny);
        this.reproductionEnergy = propertyFileLoader.getIntValue(ESimulationProperty.energiaRodzicowDoTworzeniaPotomka);
        this.minMutations = propertyFileLoader.getIntValue(ESimulationProperty.minimalnaLiczbaMutacji);
        this.maxMutations = propertyFileLoader.getIntValue(ESimulationProperty.maksymalnaLiczbaMutacji);
        this.mutationVariant = propertyFileLoader.getIntValue(ESimulationProperty.wariantMutacji);
        this.currentStep = 0;
        this.paused = true;
        this.deadAnimalsAgeList = new ArrayList<>();
//        this.genomeLength = genomeLength;
        initAnimals();
    }

    private void initAnimals() {
        int genomeLength = this.simulationPropertyFile.getIntValue(ESimulationProperty.dlugoscGenomuZwierzakow);
        int animalCount = this.simulationPropertyFile.getIntValue(ESimulationProperty.startowaLiczbaZwierzakow);
        int startEnergy = this.simulationPropertyFile.getIntValue(ESimulationProperty.startowaEnergiaZwierzakow);
        for (int i=0; i<animalCount; i++) {
            Vector2d position = map.generateRandomPosition();
            Animal animal = new Animal(map, position, genomeLength, startEnergy);
        }
    }


    @Override
    public void run() {
        do {
            List<Animal> animalList = map.getAnimals();
            List<Animal> animalsToDie = new ArrayList<>();
            for (Animal a : animalList) {
                // if energy == 0 -> animal dies
                if (a.getEnergy() <= 0) {
                    animalsToDie.add(a);
                } else {
                    a.move(simulationPropertyFile);
                }
            }

            for (Animal a : animalsToDie) {
                deadAnimalsAgeList.add(currentStep-a.getBirthDay());
                animalList.remove(a);
                map.getMapElements().remove(a);
            }

            // Grass on their positions
            Map<Vector2d, Grass> grassMap = this.map.getPositionToGrassMap();

            // Animals on their positions
            Map<Vector2d, List<Animal>> animalMap = new HashMap<>();
            for (Animal animal: animalList) {
                animalMap.computeIfAbsent(animal.getPosition(), k -> new ArrayList<>())
                        .add(animal);
            }
            // eat grass
            for (Vector2d position : animalMap.keySet()) {
                if (grassMap.keySet().contains(position)) {
                    List<Animal> animalsOnPosition = animalMap.get(position);
                    animalsOnPosition.sort(Comparator.comparingInt(Animal::getEnergy).reversed()
                            .thenComparingInt(Animal::getBirthDay)
                            .thenComparingInt(Animal::getChildrenCount).reversed());

                    Animal winner = animalsOnPosition.get(0);
                    //real grass eating is here
                    winner.setEnergy(winner.getEnergy() + this.grassEnergy);
                    this.map.getMapElements().remove(grassMap.get(position));
                    grassMap.remove(position);
                }
            }

            // reproduce
            for (Vector2d position : animalMap.keySet()) {

                List<Animal> animalsOnPosition = animalMap.get(position);
                if (animalsOnPosition.size() > 1) {
                    animalsOnPosition.sort(Comparator.comparingInt(Animal::getEnergy).reversed()
                            .thenComparingInt(Animal::getBirthDay)
                            .thenComparingInt(Animal::getChildrenCount).reversed());
                    Animal winner = animalsOnPosition.get(0);
                    Animal secondParent = animalsOnPosition.get(1);
                    //...
                    if (secondParent.getEnergy() > this.simulationPropertyFile.getIntValue(ESimulationProperty.energiaZwierzakaGotowegoDoRozmnazania))
                        animalReproduction(position, winner, secondParent);
                }
            }

            // grass grows
            int grassCount = this.simulationPropertyFile.getIntValue(ESimulationProperty.liczbaRoslinWyrastajacaKazdegoDnia);
            map.plantGrass(grassCount, grassMap);


            notifyObservers(currentStep);
            currentStep++;

            if (paused)
                return;

            try {
                Thread.sleep(moveDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            System.out.println("Move direction: " + direction.toString());
//            System.out.println(map);
        } while (!paused);

        }



    private void animalReproduction(Vector2d position, Animal winner, Animal secondParent) {
        int genomeLength = this.simulationPropertyFile.getIntValue(ESimulationProperty.dlugoscGenomuZwierzakow);
        List<Integer> childGenome = new ArrayList<>();
        //też liczba genów jaka odziedziczy po silniejszym rodzicu
        int cutIndex = genomeLength * winner.getEnergy()/ (winner.getEnergy()+secondParent.getEnergy());

        //losowanie strony silniejszego osobnika
        int side = random.nextInt(2);
        //lewa strona wylosowana
        if (side == 0) {
            childGenome.addAll(winner.getGenome().subList(0, cutIndex));
            childGenome.addAll(secondParent.getGenome().subList(cutIndex, genomeLength));
        }
        //prawa -> bierzemy pierwsze geny od drugiego rodzica
        else {
            cutIndex = genomeLength-cutIndex;

            childGenome.addAll(secondParent.getGenome().subList(0, cutIndex));
            childGenome.addAll(winner.getGenome().subList(cutIndex, genomeLength));
        }

        geneticMutation(genomeLength, childGenome);

//        System.out.println("reproduction result: ");
//        System.out.println("W: " + winner.getGenome());
//        System.out.println("R: " + childGenome);
//        System.out.println("L: " + secondParent.getGenome());

        Animal childAnimal = new Animal(this.map, position, childGenome, 2*this.reproductionEnergy, currentStep);
        winner.setEnergy(winner.getEnergy() - this.reproductionEnergy);
        secondParent.setEnergy(secondParent.getEnergy() - this.reproductionEnergy);
        winner.setChildrenCount(winner.getChildrenCount()+1);
        secondParent.setChildrenCount(secondParent.getChildrenCount()+1);
    }

    private void geneticMutation(int genomeLength, List<Integer> childGenome) {
        //mutacje genów
        //liczba mutacji
        int mutations = random.nextInt(this.minMutations, this.maxMutations+1);
        //wybrane geny które się zmienią
        Set<Integer> mutationIndexSet = new HashSet<>();


        while (mutationIndexSet.size()<mutations) {
            int newIndex = random.nextInt(0, genomeLength);
            mutationIndexSet.add(newIndex);
        }
//        List<Integer> genes = new ArrayList<>();
//        for (int i = 0; i<=7; i++) {
//            genes.add(i);
//        }
//
//        for (int index : mutationIndexSet) {
//            int currentGene = childGenome.get(index);
//            genes.remove(currentGene);
//            int newGene = genes.get(random.nextInt(genes.size()));
//            currentGene = newGene;
//        }

        //wariant1: Pełna losowość
        if (this.mutationVariant==1) {
            for (int index : mutationIndexSet) {
                int newGene = (childGenome.get(index) + random.nextInt(1, 8)) % 8;
                childGenome.set(index, newGene);
            }
        }
        // wariant2: lekka korekta
        else {
            for (int index : mutationIndexSet) {
                int newGene = (childGenome.get(index) - 3 + 2 * random.nextInt(1, 3)) % 8;
                childGenome.set(index, newGene);
            }
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
    public int getCurrentStep() {
        return currentStep;
    }
    public OptionalDouble averageLifeLength() {
        return deadAnimalsAgeList.stream().mapToDouble(e -> e).average();
//        double result = 0;
//        for (int age : deadAnimalsAgeList) {
//            result = result+age;
//        }
//        result = result/deadAnimalsAgeList.size();
//        return result;
    }
    public int totalDeadAnimal(){
        return deadAnimalsAgeList.size();
    }

}
