package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

import java.util.Random;

public class UsrPan extends GridPane {
    String username;

    public UsrPan(String username) {
        super();
        this.username = username;

        Circle icon = new Circle();
        icon.setRadius(9);
        icon.setFill(randColor());
        icon.getStyleClass().add("circle-icon");

        Label label = new Label(this.username);

        this.add(icon, 0,0,1,1);
        this.add(label,1,0,1,1);
        this.setHgap(6);
    }

    private LinearGradient randColor(){
        Random rand = new Random();
        float r1 = rand.nextFloat();
        float g1 = rand.nextFloat();
        float b1 = rand.nextFloat();

        Color rgb1 = new Color(r1,g1,b1,1);

        float r2 = rand.nextFloat();
        float g2 = rand.nextFloat();
        float b2 = rand.nextFloat();

        Color rgb2 = new Color(r2,g2,b2,1);

        Stop[] stops = new Stop[] { new Stop(0, rgb1), new Stop(1, rgb2)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        return lg1;
    }
}
