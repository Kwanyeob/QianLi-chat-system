package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import java.util.ArrayList;
import java.util.Date;

public class CustomerChoicePanel{
    private ObservableList<UsrPan> items;
    private ArrayList<Customer> customers;
    HBox container;

    public CustomerChoicePanel(ObservableList<UsrPan> items) {
        this.items = items;
        container = new HBox();
        container.setId("usr-list-container");
        customers = new ArrayList<Customer>();
        customers.add(new Customer("lobby",new Date()));

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


        //Insert the container into the center part of our panel
        pane.setCenter(container);


        return pane;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public HBox getContainer() {
        return container;
    }
}
