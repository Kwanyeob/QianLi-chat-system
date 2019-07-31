package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MessagePanel extends ScrollPane{
    private ArrayList<Message> messages;
    private VBox viewport;

    public MessagePanel() {
        this.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        messages = new ArrayList<Message>();
        this.setId("msgPane");

        viewport = new VBox();
        viewport.setId("MsgViewport");
        viewport.setPadding(new Insets(15, 12, 15, 12));

        viewport.prefWidthProperty().bind(this.widthProperty().add(-15));

        this.setContent(viewport);
        viewport.setSpacing(10);
        this.vvalueProperty().bind(viewport.heightProperty());
    }

    public void add(Message m){
        this.messages.add(m);
        viewport.getChildren().add(m.getDisplayBox());
    }


}
