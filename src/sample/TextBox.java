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

public class TextBox implements EventHandler<ActionEvent> {
    String placeholder;
    Button send;
    TextField type;

    public TextBox(String placeholder) {
        this.placeholder = placeholder;
    }

    public HBox addTextBox(){
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(15, 12, 15, 12));

        send = new Button();
        send.setText("Send");
        send.setOnAction(this);
        send.setId("send-btn");

        type = new TextField();
        type.setOnAction(this);

        hbox.setHgrow(type, Priority.ALWAYS);
        type.setPromptText(this.placeholder);

        hbox.getChildren().addAll(type, send);

        hbox.setPrefWidth(600);
        hbox.setPrefHeight(80);

        return hbox;
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource()==send || event.getSource()==type){
            //We check if the textfield contains something before sending
                //This allows avoiding sending an empty message
            if(type.getCharacters().length() > 0) {
                System.out.println("SEND button clicked !");

                String content = type.getCharacters().toString();
                //Todo : send text to server
                sendMessage(content);

                type.setText("");
                System.out.println("text cleared !");
            }
        }
    }

    private void sendMessage(String msg){
        System.out.println("message " + msg + " sent");
    }
}
