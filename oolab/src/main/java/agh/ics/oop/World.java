package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

import java.util.Arrays;

public class World {
    public static void main(String[] args){

        try {
            System.out.println("System started");

            Application.launch(App.class, args);

            System.out.println("System finished");
        } catch ( IllegalArgumentException ex) {
            System.err.println(ex);

        }

    }

}
