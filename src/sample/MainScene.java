package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;

import static javafx.application.Application.*;

public class MainScene {
    BorderPane border;
    TextBox textBox;
    MessagePanel msg;
    HBox hbox;

    public MainScene(TextBox textbox, MessagePanel msgPanel) {
        border = new BorderPane();
        BorderPane centerPane = new BorderPane();

        //CENTER SIDE
        //Input pane
        textBox = textbox;
        centerPane.setBottom(textBox.addTextBox());

        //Messages pane
        msg = msgPanel;

        centerPane.setCenter(msg);

        ListView<UsrPan> list = new ListView<UsrPan>();
        ObservableList<UsrPan> items = FXCollections.observableArrayList (
                new UsrPan("User1"), new UsrPan("User2"));
        list.setItems(items);
        list.setPrefWidth(160);
        list.setFixedCellSize(50);

        border.setLeft( list );
        border.setCenter(centerPane);

    }

    public BorderPane getBorder() {
        return border;
    }

    public TextBox getTextBox() {
        return textBox;
    }

    public ObservableList<Node> getChildren(){
        return border.getChildren();
    }
}
