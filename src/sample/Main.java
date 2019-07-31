package sample;

import Connexion.Client;
import Connexion.NetworkConnection;
import Connexion.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.spec.ECField;

public class Main extends Application {
    TextBox txtbox;
    MessagePanel messagePanel;
    MainScene ms;
    private int theme = 0;

    private boolean isServer = false;
    private NetworkConnection connection = isServer ? createServer(7777) : createClient("127.0.0.1",7777);


    public void init() throws Exception {
        connection.startConnection();
    }

    private Scene createContent(){
        messagePanel = new MessagePanel();

        txtbox = new TextBox("Type your message...");

        txtbox.getSend().setOnAction(event -> {
            if(txtbox.getType().getCharacters().length() > 0) {
                System.out.println("SEND button clicked !");

                String content = txtbox.getType().getCharacters().toString();
                //Todo : append message boxes
                messagePanel.add(new Message("myself", content));

                try {
                    connection.send(content);
                } catch (Exception e) {
                    e.printStackTrace();
                    messagePanel.add(new Message("Server", "Sending failed"));
                }

                txtbox.getType().setText("");
                System.out.println("text cleared !");
            }
        });

        ms = new MainScene(txtbox, messagePanel);
        return new Scene(ms.getBorder(),800,600);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Main stage = window
        primaryStage.setTitle("Hello World");

        Scene scene = createContent();

        String fontSheet = fileToStylesheetString( new File ("resources/jmetro8/JMetroLightTheme.css") );

        if ( fontSheet == null ) {
            //Do Whatever you want with logging/errors/etc.
            System.out.println("CSS File not recognized");
        } else {
            scene.getStylesheets().add( fontSheet );
            System.out.println("CSS File Detected, applying...");
        }
        HBox buttonContainerTop = new HBox();
        Button themeBtn = new Button("Light on ");

        //Styling event on button press
        themeBtn.setOnAction(e -> {
            if(theme == 0 ) {
                String f = fileToStylesheetString(new File("resources/jmetro8/JMetroDarkTheme.css"));
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets().add( f);
                theme = 1;
            }
            else if(theme == 1){
                String a = fileToStylesheetString(new File("resources/jmetro8/JMetroLightTheme.css"));
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets().add( a);
                theme = 0;
            }

        });
        buttonContainerTop.getChildren().add(themeBtn);
        if(isServer)
            buttonContainerTop.getChildren().add(new Label("(Server side)"));
        else
            buttonContainerTop.getChildren().add(new Label("(Client side)"));
        buttonContainerTop.setSpacing(10);
        ms.getBorder().setTop(buttonContainerTop);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        //TODO : stop the server
        connection.closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public String fileToStylesheetString (File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }


    private Server createServer(int port){
        return new Server(port, data -> {
            Platform.runLater(() -> {
                messagePanel.add(new Message("Server",data.toString()));
            });
        });
    }

    private Client createClient(String ip, int port){
        return new Client(ip, port , data ->{
            Platform.runLater(() -> {
                messagePanel.add(new Message("Server",data.toString()));
            });
        });
    }
}
