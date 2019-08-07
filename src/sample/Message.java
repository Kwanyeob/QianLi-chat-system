package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Screen;

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
        this.box.setSpacing(5);
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
                String webname = url.split("//")[1];
                if(webname.startsWith("www.")){
                    webname = webname.substring(4);
                }
                String website = webname.split("/")[0];
                Linker link = new Linker("Visit "+website,url);
                HBox linkBox = new HBox();
                linkBox.getChildren().add(link);
                linkBox.setPadding(new Insets(5,5,5,5));
                linkBox.getStyleClass().add("msg-linkbox");
                this.box.getChildren().add(linkBox);
            }
        }
    }

    public HBox getDisplayBox(){
        Label label = new Label();
        label.setText(content);

        //label.setEditable(false);
        label.getStyleClass().add("msg-label");

        //label.setPrefRowCount(label.getParagraphs().size());
        Label hidden = new Label(getContent());

        box.getChildren().add( label);
        box.setHgrow(label, Priority.SOMETIMES);

        label.setWrapText(true);

        return box;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HBox getBox() {
        return box;
    }

    public void setBox(HBox box) {
        this.box = box;
    }
}
