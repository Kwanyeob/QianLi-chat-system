package sample;

import chat.Chat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;

import static javafx.application.Application.*;

public class MainScene {
    private BorderPane border;
    private TextBox textBox;
    private MessagePanel msg;
    private HBox hbox;
    private ObservableList<UsrPan> items;
    private CustomerChoicePanel custPan;

    public MainScene(TextBox textbox, MessagePanel msgPanel, Chat chat) {
        border = new BorderPane();
        //CenterPane = MessagesPanel
        BorderPane centerPane = new BorderPane();

        //CENTER SIDE
        //Input pane
        textBox = textbox;
        centerPane.setBottom(textBox.addTextBox());

        //Messages pane
        msg = msgPanel;

        centerPane.setCenter(msg);

        ListView<UsrPan> list = new ListView<UsrPan>();
        items = FXCollections.observableArrayList ();

        UsrPan seeCustom = new UsrPan("Pending customers");
        try {
            seeCustom.getChildren().set(0,new ImageView(new Image(new FileInputStream("resources\\icons\\hollow-usr-more.png"))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            seeCustom.getChildren().set(0,new Label("+"));
        }

        items.add(seeCustom);
        list.setItems(items);
        list.setPrefWidth(160);
        list.setFixedCellSize(50);
        list.getStyleClass().add("linear-grad1");

        items.addListener(new ListChangeListener<UsrPan>() {
            @Override
            public void onChanged(Change<? extends UsrPan> change) {
                for (int i = 1; i < items.size() ; i++) {
                    UsrPan pan = items.get(i);
                    pan.setOnMouseClicked(e ->{
                        String username = pan.getUsername();
                        String roomname = username.substring(username.indexOf("#")+1);
                        if(roomname != null) {
                            System.out.println("Joining " + roomname);
                            chat.changeRoom(roomname);
                            msgPanel.clear();
                            border.setCenter(centerPane);
                        }
                    });
                }
            }
        });

        custPan = new CustomerChoicePanel(items);

        BorderPane userPane = custPan.getBorderPane();

        seeCustom.setOnMouseClicked(e ->{
            border.setCenter(userPane);
        });

        border.setLeft( list );
        border.setCenter(userPane);

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

    public ObservableList<UsrPan> getUsrItems(){
        return items;
    }

    public CustomerChoicePanel getCustPan() {
        return custPan;
    }
}
