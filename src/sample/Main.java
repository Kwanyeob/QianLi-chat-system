package sample;

import chat.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Paths;

public class Main extends Application implements ChatCallbackAdapter {
    TextBox txtbox;
    MessagePanel messagePanel;
    MainScene ms;
    String nickname = "Dev1";
    private int theme = 0;

    private static final long serialVersionUID = 1580673677145725871L;
    private Chat chat;


    public void init() throws Exception {

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
        primaryStage.setTitle("QianLiSpace Chat - Client");
        primaryStage.getIcons().add(new Image(new FileInputStream("resources\\icon.png")));;

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
        String darkStyle = fileToStylesheetString( new File ("resources/styleDark.css") );
        String lightStyle = fileToStylesheetString( new File ("resources/styleLight.css") );

        if ( styles == null || darkStyle==null || lightStyle == null ) {
            //Do Whatever you want with logging/errors/etc.
            System.out.println("custom CSS Files not recognized");
        } else {
            scene.getStylesheets().add( styles );
            scene.getStylesheets().add( lightStyle );
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
                scene.getStylesheets().add(styles);
                scene.getStylesheets().add(darkStyle);
                theme = 1;
                themeBtn.setText("Light off");
            }
            else if(theme == 1){
                String a = fileToStylesheetString(new File("resources/jmetro8/JMetroLightTheme.css"));
                scene.getStylesheets().clear();
                setUserAgentStylesheet(null);
                scene.getStylesheets().add( a);
                scene.getStylesheets().add(styles);
                scene.getStylesheets().add(lightStyle);
                theme = 0;
                themeBtn.setText("Light on ");
            }

        });
        //END OF GUI STYLING

        buttonContainerTop.setSpacing(5);
        ms.getBorder().setTop(buttonContainerTop);

        /*
        System.out.println(Paths.get("").toAbsolutePath().toString());
        FileInputStream inputstream = new FileInputStream("resources\\qianDark.png");
        Image image = new Image(inputstream,192,40,true,true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80); imageView.setFitHeight(17);

        buttonContainerTop.getChildren().add(imageView);

         */

        Button settings = new Button("Settings");
        settings.setOnAction(e -> {
            Platform.runLater(() -> {
                ParamPanel pan = new ParamPanel();
                pan.show();
            });
        });

        buttonContainerTop.getChildren().addAll(themeBtn, settings);

        primaryStage.setScene(scene);
        primaryStage.show();

        startChat();

    }

    @Override
    public void stop() throws Exception {
        //TODO : stop the server
        chat.leave();
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
            if (event.equals("user message")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            messagePanel.add(new Message(obj.getString("user"),obj.getString("message")));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            else if (event.equals("announcement")) {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            messagePanel.add(new Message(obj.getString("user"),obj.getString("action")));
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            // Online users display list : Used as a label

            else if (event.equals("nicknames")) {
                JSONArray names = obj.names();
                ObservableList<UsrPan> list = ms.getUsrItems();
                Platform.runLater(new Runnable() {
                    public void run() {
                        list.clear();
                    }
                });
                for (int i=0; i < names.length(); i++) {
                    try {
                        String name = names.getString(i);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                list.add(new UsrPan(name));
                            }
                        });
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                }
            }

    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(() -> {
            messagePanel.add(new Message("Server",message));
        });
    }

    @Override
    public void onMessage(JSONObject json) {
        Platform.runLater(() -> {
            messagePanel.add(new Message("Server",json.toString()));
        });
    }

    @Override
    public void onConnect() {
        chat.join(nickname);
        Platform.runLater(() -> {
            messagePanel.add(new Message("Server","Connected: You joined as "+nickname));
        });
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
