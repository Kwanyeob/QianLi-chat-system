package sample;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class MessagePanel extends ScrollPane{
    private ArrayList<Message> messages;

    public MessagePanel() {
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        messages = new ArrayList<Message>();

        messages.add(new Message("usr","Hello"));
        messages.add(new Message("usr","World"));
        messages.add(new Message("myself","Hello "));

        update();
    }

    private void update(){
        VBox panel = new VBox();
        panel.setSpacing(10);
        for (Message msg : messages) {
            panel.getChildren().add(msg.getDisplayBox());
        }

        this.setContent(panel);
    }

    public void add(Message m){
        this.messages.add(m);
        this.update();
    }


}
