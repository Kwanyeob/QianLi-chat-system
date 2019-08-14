package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomerChoicePanel{
    private ObservableList<UsrPan> items;

    public CustomerChoicePanel(ObservableList<UsrPan> items) {
        this.items = items;
    }

    public BorderPane getBorderPane(){
        BorderPane pane = new BorderPane();

        /*  ---   TOP CONTAINER  ---    */
        HBox blueTitlebox = new HBox();
        blueTitlebox.setPrefHeight(100);

        Label title = new Label("Pending Customers");
        title.setId("usr-title-label");
        blueTitlebox.getChildren().add(title);
        blueTitlebox.setId("usr-title-container");
        //Insert in the borderpane top
        pane.setTop(blueTitlebox);

        /*  ---   CENTER CONTAINER  ---    */
        HBox container = new HBox();
        container.setId("usr-list-container");

        //Do a loop with the available users:
        for (int i=0; i<3; i++) {
            VBox usrBox = new VBox();
            usrBox.getStyleClass().add("pending-usr-box");
            SettableBoolean checkStatus = new SettableBoolean();
            checkStatus.setValue(false);

            try {
                Image base = new Image( new FileInputStream("resources\\icons\\hollow-usr.png"));
                Image plus = new Image( new FileInputStream("resources\\icons\\hollow-usr-plus.png"));
                Image checked = new Image( new FileInputStream("resources\\icons\\hollow-usr-check.png"));
                ImageView icon = new ImageView(base);

                usrBox.setOnMouseEntered(e-> {
                    if(checkStatus.getValue() ==false) {
                        icon.setImage(plus);
                    }
                });
                usrBox.setOnMouseExited(e-> {
                    if(checkStatus.getValue() ==false) {
                        icon.setImage(base);
                    }
                });
                usrBox.setOnMouseClicked(e-> {
                    if(checkStatus.getValue() ==false) {
                        icon.setImage(checked);
                        checkStatus.setValue(true);
                        //TODO : Increment with real value
                        items.add(new UsrPan("User"));
                    }
                });

                usrBox.getChildren().add(icon);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Label username = new Label("User");
            username.getStyleClass().add("usr-name");
            Label timestamp = new Label("2 minutes");
            timestamp.getStyleClass().add("usr-timestamp");

            usrBox.getChildren().addAll(username,timestamp);

            //Add the created box to the users container
            container.getChildren().add(usrBox);
        }

        //Insert the container into the center part of our panel
        pane.setCenter(container);


        return pane;
    }

}
