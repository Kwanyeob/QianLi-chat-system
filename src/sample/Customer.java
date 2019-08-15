package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

public class Customer {
    public String name;
    public Date timestamp;
    private boolean checkStatus;

    public Customer(String name, Date timestamp) {
        this.name = name;
        this.timestamp = timestamp;
        checkStatus = false;
    }

    public VBox getDisplayBox(ObservableList<UsrPan> items) {
        VBox usrBox = new VBox();
        usrBox.getStyleClass().add("pending-usr-box");

        try {
            Image base = new Image(new FileInputStream("resources\\icons\\hollow-usr.png"));
            Image plus = new Image(new FileInputStream("resources\\icons\\hollow-usr-plus.png"));
            Image checked = new Image(new FileInputStream("resources\\icons\\hollow-usr-check.png"));
            ImageView icon = new ImageView(base);

            usrBox.setOnMouseEntered(e -> {
                if (checkStatus == false) {
                    icon.setImage(plus);
                }
            });
            usrBox.setOnMouseExited(e -> {
                if (checkStatus == false) {
                    icon.setImage(base);
                }
            });
            usrBox.setOnMouseClicked(e -> {
                if (checkStatus == false) {
                    icon.setImage(checked);
                    checkStatus = true;
                    //TODO : Increment with real value
                    items.add(new UsrPan("Customer#" + name));
                }
            });

            usrBox.getChildren().add(icon);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Label username = new Label("Customer#" + name);
        username.getStyleClass().add("usr-name");
        Label time = new Label(timestamp.toString());
        time.getStyleClass().add("usr-timestamp");

        usrBox.getChildren().addAll(username, time);

        return usrBox;
    }
}
