package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TextBox {
    String placeholder;
    Button send;
    TextField type;

    public TextBox(String placeholder) {
        this.placeholder = placeholder;
        type = new TextField();
        send = new Button();
        send.setText("Send");
        send.setId("send-btn");
    }

    public HBox addTextBox(){
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15, 12, 15, 12));

        hbox.setHgrow(type, Priority.ALWAYS);
        type.setPromptText(this.placeholder);

        hbox.getChildren().addAll(type, send);

        hbox.setPrefWidth(600);
        hbox.setPrefHeight(80);

        return hbox;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Button getSend() {
        return send;
    }

    public TextField getType() {
        return type;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}
