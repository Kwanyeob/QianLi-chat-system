package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class UsrPan extends GridPane {
    String username;

    public UsrPan(String username) {
        super();
        this.username = username;

        Circle icon = new Circle();
        icon.setRadius(8);
        icon.setFill(randColor());

        Label label = new Label(this.username);

        this.add(icon, 0,0,1,1);
        this.add(label,1,0,1,1);
        this.setHgap(6);
    }

    private Color randColor(){
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

        Color rgb = new Color(r,g,b,1);

        return rgb;
    }
}
