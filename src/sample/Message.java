package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.desktop.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        this.box.setSpacing(0);
        this.box.setPadding(new Insets(6, 12, 8, 12));

        if(content.contains("http://") || content.contains("https://")){
            int index = content.indexOf("http");
            StringBuilder buffer = new StringBuilder();
            char c = 'h';
            while (c != ' ' && index < content.length()){
                c = content.charAt(index);
                index++;
                if(c != ' ')
                    buffer.append(c);
            }
            if(buffer != null){
                String url = buffer.toString();
                String website = url.split("//")[1];
                Linker link = new Linker("Visit "+website,url);
                this.box.getChildren().add(link);
            }
        }
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
