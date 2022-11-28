package agh.ics.oop.gui;

import agh.ics.oop.*;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class App extends Application {


    @Override
    public void start(Stage primaryStage) {

        //internals init, keeping inside function 4 now for scoping.
        String[] args = {}; //immiediately reassigned, but jaba be java.
        args = getParameters().getRaw().toArray(args);
        MoveDirection[] directions = OptionsParser.parse(args);
        GrassField map = new GrassField(10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run(); //put the animals at the correct positions.

        //parent grid to all map labels
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        //row/col amt in grid
        Vector2d ll = map.getLowerLeft();
        Vector2d ur = map.getUpperRight();

        //ur.follows(ll) == true => x_amt,y_amt >0
        int x_amt = ur.x - ll.x + 2; //1 for coordinate values,
        int y_amt = ur.y - ll.y + 2; //1 for if ll.x == ur.x (or y) we till need to show the row/col.

        System.out.println(x_amt);
        System.out.println(y_amt);

        //X-> col. Y-> row.
        //x==y==0 => x"y/x"
        grid.add(new Label("y/x"),0,0);

        //x==0,y!=0 => x coords
        for (int x = 1; x <x_amt; x++) {
            String coord = ((Integer)(ll.x+x-1)).toString(); //ll.x -> smallest x. + iteration variable-1, since we start from 1.
            Label tadd = new Label(coord);
            grid.add(tadd,x,0);
        }
        //y==0, x!= 0 => y coords
        for (int y = 1; y < y_amt; y++) {
            String coord = ((Integer)(ur.y-y+1)).toString(); //y vals must decrease when going downwards.
            Label tadd = new Label(coord);
            grid.add(tadd, 0, y);
        }
        //x*y != 0 => map objects.
        for (int x = 1; x < x_amt; x++) {
            for (int y = 1; y < y_amt; y++) {
                //Grid coords -> map coords conversion.
                int mapx = ll.x+x-1;
                int mapy = ur.y-y+1;
                Vector2d mapcoords = new Vector2d(mapx, mapy);
                Object mapObject = map.objectAt(mapcoords);
                Label tadd;
                if(mapObject == null)
                    tadd = new Label("");
                else
                    tadd = new Label(mapObject.toString());
                grid.add(tadd, x, y);
            }
        }

        //global foramatting
        int width = 20; //magic numbers the assignment doest tell you the values of, so I have to invent my own... AGAIN!
        int height = 20;
        for (int y = 0; y < y_amt; y++) {
            grid.getRowConstraints().add(new RowConstraints(height));
        }
        for (int x = 0; x < x_amt; x++) {
            grid.getColumnConstraints().add(new ColumnConstraints(width));
        }
        for (Node node : grid.getChildren()) {
            GridPane.setHalignment(node, HPos.CENTER);
        }
        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        /* Extra cols from the grid... odd. */
    }


}