package agh.ics.oop.gui;


import agh.ics.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.InputStream;

public class GuiElementBox extends VBox{

    public GuiElementBox(IMapElement element)
    {
        super();
        if(element == null)
        {
            return;
        }
        update(element);
    }

    //Change the element this box is representing.
    public void update(IMapElement element)
    {
        this.getChildren().clear();
        if(element == null) return;

        InputStream is = getClass().getClassLoader().getResourceAsStream(element.getResourceName());
        Image image = new Image(is);
//        ImageView imageView = new ImageView(image);
//        imageView.setFitWidth(100);
//        imageView.setFitHeight(100);
        ImageView texture = new ImageView(image);
        texture.setFitHeight(20);
        texture.setFitWidth(20);
        Label positionLabel = new Label(element.getPosition().toString());
        this.getChildren().addAll(texture,positionLabel);
        this.setCenterShape(true);
    }
}
