package agh.ics.oop.gui;

import agh.ics.oop.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application implements ISimulationStepObserver{

    private GrassField map;
    private GridPane grid;

    private Button startSimulationButton;
    private TextField directionsInput;

    private SimulationEngine engine;

    @Override
    public void init() throws Exception {
        super.init();
        //internals init, keeping inside function 4 now for scoping.
        String[] args = getParameters().getRaw().toArray(new String[0]);
        MoveDirection[] directions = OptionsParser.parse(args);
        map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };

        engine = new SimulationEngine(map, positions);
        engine.addObserver(this);
        engine.setMoveDelay(1000);
//        Thread engineThread = new Thread(engine);
//        engineThread.start();
    }

    public static void main(String[] args){

        try {
            System.out.println("System started");

            Application.launch(App.class, args);

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

        startSimulationButton = new Button("Start");
        directionsInput = new TextField();

        VBox options = new VBox(directionsInput, startSimulationButton);
        options.setMinWidth(400);
        HBox contents = new HBox(options, grid);

        //global foramatting
        Scene scene = new Scene(contents, 1200, 800);

        drawMap(map, grid);


        primaryStage.setScene(scene);
        primaryStage.show();

        startSimulationButton.setOnAction(event ->
                setOptionsAndStartSimulation()
        );
    }

    private void setOptionsAndStartSimulation() {
        engine.setDirections(OptionsParser.parse(directionsInput.getText().split(" ")));
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }

    private static void drawMap(GrassField map, GridPane grid) {
        grid.getChildren().clear();

        //row/col in grid
        Vector2d lowerLeft = map.getLowerLeft();
        Vector2d upperRight = map.getUpperRight();

        //upperRight.follows(lowerLeft) == true => gridColumnCount,gridRowCount >0
        int gridColumnCount = upperRight.x - lowerLeft.x + 2; //1 for coordinate values,
        int gridRowCount = upperRight.y - lowerLeft.y + 2; //1 for if lowerLeft.x == upperRight.x (or y) we till need to show the row/col.

        System.out.println("gridColumnCount:" + gridColumnCount);
        System.out.println("gridRowCount: " + gridRowCount);

        int columnWidth = GRID_WIDTH/(2+ upperRight.x - lowerLeft.x); //related to scene size
        int rowHeight = GRID_HEIGHT/(2+ upperRight.y - lowerLeft.y); //related to scene size;

        System.out.println("field size: " + columnWidth + ", " + rowHeight);

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
            Integer coord = lowerLeft.x+x-1;
            Label label = new Label(coord.toString());
//            label.setMinSize(columnWidth, rowHeight);
            grid.add(label, x,0);
        }
        //y==0, x!= 0 => y coords
        for (int y = 1; y < gridRowCount; y++) {
            Integer coord = upperRight.y-y+1;
            Label label = new Label(coord.toString());
//            label.setMinSize(columnWidth, rowHeight);
            grid.add(label, 0, y);
        }

        System.out.println("grid size: " + grid.getWidth() + ", " + grid.getHeight());

        for (Node node : grid.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
            GridPane.setValignment(node, VPos.CENTER);
        }
        //x*y != 0 => map objects.
        for (int x = 1; x < gridColumnCount; x++) {
            for (int y = 1; y < gridRowCount; y++) {
                // Grid coords -> map coords conversion.
                int mapX = lowerLeft.x+x-1;
                int mapY = upperRight.y-y+1;
                Vector2d mapCoords = new Vector2d(mapX, mapY);
                IMapElement mapObject = (IMapElement) map.objectAt(mapCoords);
                if (mapObject != null) {
                    Paint green = new Color(0.3, 1, 0.1, 0.5);

                    if (mapObject instanceof Grass) {
                        Rectangle grass = new Rectangle(columnWidth, rowHeight, green);
                        grid.add(grass, x, y);
                    }
                    if (mapObject instanceof Animal) {
                        int radius = Math.min(columnWidth, rowHeight)/2;
                        Paint color = new Color(0.7, 0.5, 0, 0.5);
                        Circle circle = new Circle(radius, color);
                        grid.add(circle, x, y);
                    }
                }
            }
        }
    }


    @Override
    public void stepCompleted(int completedStep) {
        if (map != null && grid != null) {
            Platform.runLater(() -> drawMap(map, grid));
//            drawMap(this.map, this.grid);
        }
    }
}