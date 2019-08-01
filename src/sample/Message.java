package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class Message {
    private String sender;
    private String content;
    private HBox box;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.box = new HBox();
        box.getStyleClass().add("msg");
        if (sender.equals("myself")) {
            box.getStyleClass().add("msg-mine");
        }
        else{
            box.getStyleClass().add("msg-else");
        }
        this.box.setPadding(new Insets(6, 12, 8, 12));
    }

    public HBox getDisplayBox(){
        TextField label = new TextField();
        label.setText(content);
        label.setEditable(false);

        label.getStyleClass().add("msg-label");
        box.getChildren().add( label);
        box.setHgrow(label, Priority.SOMETIMES);
        return box;
    }
}
