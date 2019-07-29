package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TextBox{
    String placeholder;

    public TextBox(String placeholder) {
        this.placeholder = placeholder;
    }

    public HBox addTextBox(){
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15, 12, 15, 12));

        Button send = new Button();
        send.setText("Send");

        TextField type = new TextField();
        hbox.setHgrow(type, Priority.ALWAYS);
        type.setPromptText(this.placeholder);

        hbox.getChildren().addAll(type, send);

        hbox.setPrefWidth(600);
        hbox.setPrefHeight(80);

        return hbox;
    }
}
