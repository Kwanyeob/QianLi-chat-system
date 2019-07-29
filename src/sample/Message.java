package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Message {
    private String sender;
    private String content;
    private HBox box;

    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
        this.box = new HBox();
        if (sender.equals("myself")) {
            box.setStyle(("-fx-background-color: #336699;"));
        }
        else{
            box.setStyle(("-fx-background-color: #E8E7E3;"));
        }
        this.box.setPadding(new Insets(6, 12, 8, 12));
    }

    public HBox getDisplayBox(){
        Label label = new Label(content);
        if(sender.equals("myself")){
            label.setTextFill(Color.WHITE);
        }
        box.getChildren().add( label);
        return box;
    }
}
