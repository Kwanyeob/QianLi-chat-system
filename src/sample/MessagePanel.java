package sample;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MessagePanel extends ScrollPane{
    private ArrayList<Message> messages;
    VBox viewport;

    public MessagePanel() {
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        messages = new ArrayList<Message>();

        messages.add(new Message("usr","Hello"));
        messages.add(new Message("usr","World"));
        messages.add(new Message("myself","Hello "));
        this.setId("msgPane");

        viewport = new VBox();
        viewport.setId("MsgViewport");
        viewport.setPadding(new Insets(15, 12, 15, 12));

        viewport.prefWidthProperty().bind(this.widthProperty().add(-15));

        this.setContent(viewport);

        update();
    }

    private void update(){
        viewport.setSpacing(10);
        for (Message msg : messages) {
            viewport.getChildren().add(msg.getDisplayBox());
        }
    }

    public void add(Message m){
        this.messages.add(m);
        this.update();
    }


}
