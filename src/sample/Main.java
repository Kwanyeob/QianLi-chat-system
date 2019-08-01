package sample;

import chat.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class Main extends Application implements ChatCallbackAdapter {
    TextBox txtbox;
    MessagePanel messagePanel;
    MainScene ms;
    String nickname = "Dev";
    private int theme = 0;

    private static final long serialVersionUID = 1580673677145725871L;
    private Chat chat;


    public void init() throws Exception {
        //Start connexion
    }

    private Scene createContent(){
        messagePanel = new MessagePanel();

        txtbox = new TextBox("Type your message...");

        txtbox.getSend().setOnAction(event -> {
            if(txtbox.getType().getCharacters().length() > 0) {
                System.out.println("SEND button clicked !");

                String content = txtbox.getType().getCharacters().toString();
                //Message boxes append
                messagePanel.add(new Message("myself", content));

                try {
                    //Send message content
                    chat.sendMessage(content);
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
            System.out.println("base CSS File not recognized");
        } else {
            scene.getStylesheets().add( fontSheet );
            System.out.println("CSS File Detected, applying...");
        }
        String styles = fileToStylesheetString( new File ("resources/style.css") );

        if ( styles == null ) {
            //Do Whatever you want with logging/errors/etc.
            System.out.println("custom CSS File not recognized");
        } else {
            scene.getStylesheets().add( styles );
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
                //scene.getStylesheets().add(styles);
                theme = 1;
            }
            else if(theme == 1){
                String a = fileToStylesheetString(new File("resources/jmetro8/JMetroLightTheme.css"));
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets().add( a);
                scene.getStylesheets().add(styles);
                theme = 0;
            }

        });
        //END OF GUI STYLING


        buttonContainerTop.getChildren().add(themeBtn);

        buttonContainerTop.setSpacing(10);
        ms.getBorder().setTop(buttonContainerTop);

        primaryStage.setScene(scene);
        primaryStage.show();

        startChat();

    }

    @Override
    public void stop() throws Exception {
        //TODO : stop the server
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



    public void startChat() {
        messagePanel.add(new Message("Server","Connecting..."));
        chat = new Chat(this);
        chat.start();
    }

    @Override
    public void callback(JSONArray data) throws JSONException {

    }

    @Override
    public void on(String event, JSONObject obj) {
        try {
            if (event.equals("user message")) {
                messagePanel.add(new Message(obj.getString("user"),obj.getString("message")));
            }

            else if (event.equals("announcement")) {
                messagePanel.add(new Message(obj.getString("user"),obj.getString("action")));
            }
            // Online users display list : Used as a label
            /*
            else if (event.equals("nicknames")) {
                JSONArray names = obj.names();
                UsrPan usr;
                for (int i=0; i < names.length(); i++) {
                    usr = new UsrPan(names.getString(i));
                    list.add(usr);
                }
                OnlineUsers.setText(str);
            }

             */
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void onMessage(JSONObject json) {

    }

    @Override
    public void onConnect() {
        chat.join(nickname);
        //messagePanel.add(new Message("Server","Info: You joined as "+nickname));
    }

    @Override
    public void onDisconnect() {
        messagePanel.add(new Message("Server","Error: Disconnected."));
    }

    @Override
    public void onConnectFailure() {
        messagePanel.add(new Message("Server","Error: Connect failure"));
    }
}
