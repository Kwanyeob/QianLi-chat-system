package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainScene {
    BorderPane border;
    HBox hbox;

    public MainScene() {
        border = new BorderPane();
        BorderPane centerPane = new BorderPane();

        //CENTER SIDE
        //Input pane
        centerPane.setBottom(new TextBox("Type your message...").addTextBox());

        //Messages pane
        MessagePanel msg = new MessagePanel();

        centerPane.setCenter(msg);

        ListView<String> list = new ListView<String>();
        ObservableList<String> items = FXCollections.observableArrayList (
                "User1", "User1", "User2", "User3","...");
        list.setItems(items);
        list.setPrefWidth(160);

        border.setLeft( list );
        border.setCenter(centerPane);

    }

    public BorderPane getBorder() {
        return border;
    }
}
