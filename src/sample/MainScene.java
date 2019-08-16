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
import java.util.ArrayList;
import java.util.HashSet;

import static javafx.application.Application.*;

public class MainScene {
    private BorderPane border;
    private TextBox textBox;
    private MessagePanel msg;
    private HBox hbox;
    private ObservableList<UsrPan> items;
    private CustomerChoicePanel custPan;

    private ArrayList<MessagePanel> messagePanels;
    private int selectedPanel = 0;


    public MainScene(TextBox textbox, Chat chat) {
        border = new BorderPane();
        //CenterPane = MessagesPanel
        BorderPane centerPane = new BorderPane();

        //CENTER SIDE
        //Input pane
        textBox = textbox;
        centerPane.setBottom(textBox.addTextBox());

        //Messages pane
        messagePanels = new ArrayList<MessagePanel>();
        msg = new MessagePanel(0);
        messagePanels.add(msg);

        centerPane.setCenter(messagePanels.get(selectedPanel));

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

                        //At this point we have the room number
                        if(roomname != null) {
                            //Switch room
                            chat.changeRoom(roomname);

                            //Switch panel
                            //First we try to know if the panel exists
                            boolean exists = panelExists(roomname);
                            System.out.println("exists = "+exists);

                            //If it does we switch
                            if (exists == true){
                                //get panel index from its id
                                int pan_id = getPanelIdFromName(roomname);
                                //set our selection on this id
                                selectedPanel = pan_id;
                                //switch panels
                                centerPane.setCenter(messagePanels.get(selectedPanel));
                            }
                            //If it does not we create it
                            else {
                                //Instanciate new panel
                                MessagePanel nouveau = new MessagePanel(Integer.valueOf(roomname));
                                //Add panel to our panel array
                                messagePanels.add(nouveau);  //Increment the panel number
                                //Set selected panel to this increment
                                selectedPanel++;
                                System.out.println("Selected panel now = "+selectedPanel);
                                //Replace the old panel with the new one
                                centerPane.setCenter(messagePanels.get(selectedPanel));
                            }

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

    private boolean panelExists(String name){
        boolean ok = false;
        int nameId = Integer.valueOf(name);
        for (int i = 0; i < messagePanels.size() ; i++) {
            MessagePanel cur = messagePanels.get(i);
            if(cur.getNumericID() == nameId){
                ok = true;
                break;
            }
        }
        return ok;
    }

    private int getPanelIdFromName(String name){
        int id = -1;
        int nameId = Integer.valueOf(name);
        for (int i = 0; i < messagePanels.size() ; i++) {
            MessagePanel cur = messagePanels.get(i);
            if(cur.getNumericID() == nameId){
                id = i;
                break;
            }
        }
        return id;
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
    public MessagePanel getMsg() {
        return messagePanels.get(selectedPanel);
    }

    public void setMsg(MessagePanel msg) {
        this.msg = msg;
    }
}
