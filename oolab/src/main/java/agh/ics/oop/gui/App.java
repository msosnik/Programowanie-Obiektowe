package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Map;

public class App extends Application implements ISimulationStepObserver{

    private RectangularGrassField map;
    private GridPane grid;

    private Button startSimulationButton;

    private Button stepSimulationButton;

    Label animalCount;
    Label simulationDay;
    Label grassCount;

    Label emptyFieldCount;

    Label averageLifeLength;

    Label totalDeadAnimals;

    Label mostPopularGenotype;

    Label averageEnergy;

    private TextField directionsInput;

    private SimulationEngine engine;

    private File outputCSVFile;


    @Override
    public void init() throws Exception {
        super.init();
        String[] args = getParameters().getRaw().toArray(new String[0]);
        File propertyFile = args.length > 0 ? new File(args[0]) : new File("config1.properties");

        outputCSVFile = new File(propertyFile.getParentFile(), propertyFile.getName() + "-output.csv");
        SimulationResults.writeHeaderToCSV(outputCSVFile);
        SimulationPropertyFile simulationProperty = new SimulationPropertyFile(propertyFile);

        //MoveDirection[] directions = OptionsParser.parse(args);
        map = new RectangularGrassField(simulationProperty.getIntValue(ESimulationProperty.szerokoscMapy),
                simulationProperty.getIntValue(ESimulationProperty.wysokoscMapy),
                simulationProperty.getIntValue(ESimulationProperty.startowaLiczbaRoslin));

        engine = new SimulationEngine(map, simulationProperty);
        engine.addObserver(this);
//        Thread engineThread = new Thread(engine);
//        engineThread.start();
    }

    public static void main(String[] args){

        try {
            System.out.println("System started");

            launch(args);

            System.out.println("System finished");
        } catch ( IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public static int GRID_WIDTH = 800;
    public static int GRID_HEIGHT = 800;


    @Override
    public void start(Stage primaryStage) {

        //parent grid to all map labels
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setMinSize(GRID_WIDTH, GRID_HEIGHT);

        GridPane controlPanel = createControlPane();
        HBox contents = new HBox(controlPanel, grid);

        Scene scene = new Scene(contents, 1200, 800);

//        drawMap(map, map.getWidth(), map.getHeight(), grid);
        drawMap(map.getAnimals(), map.getGrassList(), map.getWidth(), map.getHeight(), grid);

        primaryStage.setScene(scene);
        primaryStage.show();

        startSimulationButton.setOnAction(event ->
                startStopSimulation()
        );

        stepSimulationButton.setOnAction(event ->
                stepForwardSimulation()
        );

    }

    private GridPane createControlPane() {
        startSimulationButton = new Button("Start");
//        directionsInput = new TextField();
        stepSimulationButton = new Button("Step");

        simulationDay = new Label(".");

        animalCount = new Label(".");
        grassCount = new Label(".");
        emptyFieldCount = new Label(".");
        averageEnergy = new Label(".");
        averageLifeLength = new Label(".");
        totalDeadAnimals = new Label(".");
        mostPopularGenotype = new Label(".");

        GridPane controlPanel = new GridPane();

        int rowHeight = 40;
        int rowIndex = 0;

        controlPanel.add(startSimulationButton, 0, rowIndex);
        controlPanel.add(stepSimulationButton, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Simulation Day:"), 0, rowIndex);
        controlPanel.add(simulationDay, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Animal Count:"), 0, rowIndex);
        controlPanel.add(animalCount, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Grass Count:"), 0, rowIndex);
        controlPanel.add(grassCount, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Empty Field Count:"), 0, rowIndex);
        controlPanel.add(emptyFieldCount, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Average Energy:"), 0, rowIndex);
        controlPanel.add(averageEnergy, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Average Life Length:"), 0, rowIndex);
        controlPanel.add(averageLifeLength, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Number of dead animas:"), 0, rowIndex);
        controlPanel.add(totalDeadAnimals, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Most Popular Genotype:"), 0, rowIndex);
        controlPanel.add(mostPopularGenotype, 1, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        rowIndex++;
        controlPanel.add(new Label("Color Legend"), 0, rowIndex);
        controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));

        for (int i =1; i<6; i++) {
            controlPanel.add(new Label(i<5 ? "energy = "+i : "energy >= " +i), 0, rowIndex+i);
            controlPanel.getRowConstraints().add(new RowConstraints(rowHeight));
            controlPanel.add(new Circle(rowHeight/2, getColor(i)), 1, rowIndex+i);
        }

        controlPanel.setMinWidth(400);
        return controlPanel;
    }

    private void stepForwardSimulation() {
        engine.setPaused(true);
        startSimulationButton.setText("Start");

        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    private void startStopSimulation() {

        if (engine.isPaused()) {
            engine.setPaused(false);
            Thread engineThread = new Thread(engine);
            engineThread.start();
            startSimulationButton.setText("Pause");
        } else {
            engine.setPaused(true);
            startSimulationButton.setText("Start");
        }
    }

//    private static void drawMap(RectangularGrassField map, int width, int height, GridPane grid) {
    private static void drawMap(List<Animal> animalList, List<Grass> grassList, int width, int height, GridPane grid) {
        grid.getChildren().clear();

        //row/col in grid
        int gridColumnCount = width + 1; //1 for coordinate values,
        int gridRowCount = height + 1; //1 for if lowerLeft.x == upperRight.x (or y) we till need to show the row/col.

//        System.out.println("gridColumnCount:" + gridColumnCount);
//        System.out.println("gridRowCount: " + gridRowCount);

        int columnWidth = GRID_WIDTH/gridColumnCount; //related to scene size
        int rowHeight = GRID_HEIGHT/gridRowCount; //related to scene size;
//        System.out.println("field size: " + columnWidth + ", " + rowHeight);

        for (int y = 0; y < gridRowCount; y++) {
            grid.getRowConstraints().add(new RowConstraints(rowHeight));
        }
        for (int x = 0; x < gridColumnCount; x++) {
            grid.getColumnConstraints().add(new ColumnConstraints(columnWidth));
        }

        //X-> col. Y-> row.
        //x==y==0 => x"y/x"
        grid.add(new Label("y/x"),0,0);

        //x==0,y!=0 => x coords
        for (int x = 1; x <gridColumnCount; x++) {
            Integer coord = x-1;
            Label label = new Label(gridRowCount <= 40 ? coord.toString() : ".");
//            label.setMinSize(columnWidth, rowHeight);
            grid.add(label, x,0);
        }
        //y==0, x!= 0 => y coords
        for (int y = 1; y < gridRowCount; y++) {
            Integer coord = height-y;
            Label label = new Label(gridRowCount <= 40 ? coord.toString() : ".");
//            label.setMinSize(columnWidth, rowHeight);
            grid.add(label, 0, y);
        }

//        System.out.println("grid size: " + grid.getWidth() + ", " + grid.getHeight());

        for (Node node : grid.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
        }

//        for (Grass mapElement : map.getGrassList()) {
        for (Grass mapElement : grassList) {
            Vector2d mapPosition = mapElement.getPosition();
            int gridX = mapPosition.x + 1;
            int gridY = height - mapPosition.y;
            Paint green = new Color(0.1, 0.5, 0.1, 1);
            Rectangle grass = new Rectangle(columnWidth, rowHeight, green);
            grid.add(grass, gridX, gridY);
        }
//        for (Animal mapElement : map.getAnimals()) {
        for (Animal mapElement : animalList) {
            Vector2d mapPosition = mapElement.getPosition();
            int gridX = mapPosition.x + 1;
            int gridY = height - mapPosition.y;
            int radius = Math.min(columnWidth, rowHeight)/2;
            int energy = mapElement.getEnergy();
//            Paint color = Color.rgb(100, 60, 0, 1);
            Paint color = getColor(energy);
//            Paint color = new Color(0.3, 0.2, 0, 1);
            Circle circle = new Circle(radius, color);
            grid.add(circle, gridX, gridY);
        }
    }

    private static Paint getColor(int energy) {
        Paint color = switch (energy) {
            case 0 -> Color.rgb(3, 2, 252);
            case 1 -> Color.rgb(42, 0, 213);
            case 2 -> Color.rgb(99, 0, 158);
            case 3 -> Color.rgb(161, 1, 93);
            case 4 -> Color.rgb(216, 0, 39);
            default -> Color.rgb(254, 0, 2);
        };
        if (energy < 0) {
            color = Color.rgb(3, 2, 252);
        }
        return color;
    }


    @Override
    public void stepCompleted(int completedStep) {
        if (map != null && grid != null) {
            int width = map.getWidth();
            int height = map.getHeight();
            List<Animal> animalList = map.getAnimals();
            List<Grass> grassList = map.getGrassList();

//            Platform.runLater(() -> drawMap(map, width, height, grid));
            Platform.runLater(() -> drawMap(animalList, grassList, width, height, grid));

            SimulationResults simulationResults = this.createSimulationResult();
            Platform.runLater(() -> updateControlPane(simulationResults));

            simulationResults.writeLineToCSV(outputCSVFile);
        }
    }

    private void updateControlPane(SimulationResults simulationResults) {
        simulationDay.setText(""+simulationResults.day);
        animalCount.setText(""+simulationResults.animalCount);
        grassCount.setText(""+simulationResults.grassCount);
        emptyFieldCount.setText(""+simulationResults.emptyFieldCount);
        averageLifeLength.setText(simulationResults.averageLifeLength > 0 ?
                String.format( "%.1f", simulationResults.averageLifeLength) : "-");
        totalDeadAnimals.setText(""+simulationResults.totalDeadAnimals);
        double avgEnergy = simulationResults.averageEnergy;
        averageEnergy.setText(avgEnergy>0 ? String.format("%.1f", avgEnergy) : "-");
        mostPopularGenotype.setText(simulationResults.mostPopularGenomeCount > 0 ?
                simulationResults.mostPopularGenomeCount+" = " + simulationResults.mostPopularGenome : "-");
    }

    public SimulationResults createSimulationResult() {
        SimulationResults simulationResults = new SimulationResults();

        simulationResults.day = engine.getCurrentStep();
        simulationResults.animalCount = map.getAnimals().size();
        simulationResults.grassCount = map.getGrassList().size();
        simulationResults.emptyFieldCount = map.getEmptyFieldCount();

        simulationResults.averageLifeLength = engine.averageLifeLength().orElse(-1);
        simulationResults.totalDeadAnimals = engine.totalDeadAnimal();
        simulationResults.averageEnergy = map.getAnimals().stream().mapToDouble(a->a.getEnergy()).average().orElse(-1);
        Map.Entry<String, Integer> mostPopularGene = map.getMostPopularGenes();
        if (mostPopularGene != null) {
            simulationResults.mostPopularGenomeCount = mostPopularGene.getValue();
            simulationResults.mostPopularGenome = mostPopularGene.getKey();
        } else {
            simulationResults.mostPopularGenomeCount = 0;
            simulationResults.mostPopularGenome = "";
        }
        return simulationResults;
    }



}