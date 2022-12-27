package agh.ics.oop.gui;


import agh.ics.oop.AbstractMapElement;
import agh.ics.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox extends VBox{

    public GuiElementBox(IMapElement element)
    {
        super();
        if(element == null)
        {
            return;
        }
        Update(element);
    }

    //Change the element this box is representing.
    public void Update(IMapElement element)
    {
        this.getChildren().clear();
        if(element == null) return;
        ImageView texture = new ImageView(element.getResourceName());
        texture.setFitHeight(20);
        texture.setFitWidth(20);
        Label positionLabel = new Label(element.getPosition().toString());
        this.getChildren().addAll(texture,positionLabel);
        this.setCenterShape(true);
    }
}
