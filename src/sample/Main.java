package sample;

import chat.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.prefs.Preferences;

import static javafx.util.Duration.seconds;

public class Main extends Application implements ChatCallbackAdapter {
    TextBox txtbox;
    MessagePanel messagePanel;
    MainScene ms;
    String nickname = "Dev1";
    String room = "2";
    private int theme = 0;
    Preferences Prefs;
    static final String PREFS_PATH = "qianli/chat-client/prefs";
    static final String AUTO_TRANSLATE = "auto-translate";
    static final String LANG_SELECTED = "lang";
    static final String LANG_DEFAULT = "en";

    private boolean minimized = false;
    private boolean focused = true;

    Stage primary;
    private Chat chat;
    private Notifier notifier;

    private static final long serialVersionUID = 1580673677145725871L;


    public void init() throws Exception {
        Prefs = Preferences.userRoot().node(PREFS_PATH);

        //Notifications.create().title("Error").text("Failed to capture the Screen!").showError();
        notifier = new Notifier("resources/icon.png");
    }

    private Scene createContent(){
        messagePanel = new MessagePanel();

        txtbox = new TextBox("Type your message...");

        final EventHandler<ActionEvent> sendAction = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
            }
        };

        txtbox.getSend().setOnAction(sendAction);
        txtbox.getType().setOnAction(sendAction);

        ms = new MainScene(txtbox, messagePanel);
        return new Scene(ms.getBorder(),800,600);
    }

    public void applyTheme(Scene scene,String baseStyle, String generalStyle, String style){
        //Here we define the number of styles
        int nbstyle = 8;

        String base = fileToStylesheetString(new File(baseStyle));
        String general = fileToStylesheetString(new File(generalStyle));
        String personal = fileToStylesheetString(new File(style));

        scene.getStylesheets().clear();
        setUserAgentStylesheet(null);

        if ( base == null ) {
            System.out.println("base CSS File not recognized");
        } else {
            scene.getStylesheets().add(base);

            if(general == null){
                System.out.println("generic CSS File not recognized");
            }
            else{
                scene.getStylesheets().add(general);

                if(personal == null) {
                    System.out.println("personal CSS File not recognized");
                }
                else{
                    scene.getStylesheets().add(personal);
                }

                if(theme < nbstyle-1){
                    theme++;
                }
                else {
                    theme = 0;
                }
                System.out.println(theme);

            }

        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Main stage = window
        primaryStage.setTitle("QianLiSpace Chat - Client");
        primaryStage.getIcons().add(new Image(new FileInputStream("resources\\icon.png")));;

        Scene scene = createContent();

        String baseLight = "resources/jmetro8/JMetroLightTheme.css";
        String baseDark = "resources/jmetro8/JMetroDarkTheme.css";

        String generic = "resources/style.css";

        applyTheme(scene, baseLight, generic, "resources/styleLight.css" );

        HBox buttonContainerTop = new HBox();
        Button themeBtn = new Button("Light on ");

        //Styling event on button press
        themeBtn.setOnAction(e -> {
            switch(theme){
                case 1:
                    //Dark theme
                    applyTheme(scene, baseDark, generic, "resources/styleDark.css" );
                    themeBtn.setText("Light off");
                    break;
                case 2:
                    //Gluon light
                    applyTheme(scene, baseLight, generic, "resources/styleGluonLight.css" );
                    themeBtn.setText("Gluon light");
                    break;
                case 3:
                    //Gluon dark
                    applyTheme(scene, baseDark, generic, "resources/styleGluonDark.css" );
                    themeBtn.setText("Gluon dark");
                    break;
                case 4:
                    //Mint dark
                    applyTheme(scene, baseDark, generic, "resources/styleMintDark.css" );
                    themeBtn.setText("Mint Dark");
                    break;
                case 5:
                    //Rose dark
                    applyTheme(scene, baseDark, generic, "resources/styleRoseDark.css" );
                    themeBtn.setText("Rose Dark");
                    break;
                case 6:
                    //Mint light
                    applyTheme(scene, baseLight, generic, "resources/styleMintLight.css" );
                    themeBtn.setText("Mint Light");
                    break;
                case 7:
                    //Rose light
                    applyTheme(scene, baseLight, generic, "resources/styleRoseLight.css" );
                    themeBtn.setText("Rose Light");
                    break;
                case 0:
                    applyTheme(scene, baseLight, generic, "resources/styleLight.css" );
                    themeBtn.setText("Light on");
                    break;

            }
        });
        //END OF GUI STYLING

        buttonContainerTop.setSpacing(5);
        buttonContainerTop.setId("top-btn-container");
        ms.getBorder().setTop(buttonContainerTop);

        Button settings = new Button("Settings");
        settings.setOnAction(e -> {
            Platform.runLater(() -> {
                String lang = Prefs.get(LANG_SELECTED,LANG_DEFAULT);
                ParamPanel pan = new ParamPanel(lang);
                pan.show();
            });
        });

        buttonContainerTop.getChildren().addAll(themeBtn, settings);

        //LOGO OF QIANLI
        FileInputStream inputstream = new FileInputStream("resources\\qianDark.png");
        Image image = new Image(inputstream,192,40,true,true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(80); imageView.setFitHeight(17);

        buttonContainerTop.getChildren().add(imageView);
        //END LOGO

        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                System.out.println("minimized:" + t1.booleanValue());
                minimized = t1.booleanValue();
            }
        });

        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                focused = t1.booleanValue();
            }
        });

        primary = primaryStage;

        //startChat();
        ms.getUsrItems().addListener(new ListChangeListener<UsrPan>() {
            @Override
            public void onChanged(Change<? extends UsrPan> change) {
                if(chat == null){
                    startChat();
                }
            }
        });
    }

    @Override
    public void stop() throws Exception {
        //Event on window close
        if(chat != null) {
            chat.leave();
        }
        notifier.delete();
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
                            String msg = obj.getString("message");
                            if(msg != null) {
                                //Display the message
                                messagePanel.add(new Message(obj.getString("user"), msg));

                                //Notify the user
                                if(minimized==true || focused == false) {
                                    String notifyText = "";
                                    int limit = 16;
                                    if (msg.length() > limit) {
                                        notifyText = msg.substring(0, limit) + "...";
                                    } else {
                                        notifyText = msg;
                                    }
                                    Translator t = new Translator();
                                    String lang = Prefs.get(LANG_SELECTED, LANG_DEFAULT);
                                    String title = t.translate("en", lang, "New message");
                                    if (title == null) title = "New message";
                                    if (SystemTray.isSupported()) {
                                        notifier.display("QianLi : " + title, notifyText);
                                        notifier.getTrayIcon().addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(java.awt.event.ActionEvent e) {
                                                if(focused == false) {
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //javaFX operations should go here
                                                            primary.toFront();
                                                            focused = true;
                                                        }
                                                    });
                                                }
                                                if(minimized == true){
                                                    Platform.runLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //javaFX operations should go here
                                                            primary.setIconified(false);
                                                            minimized = false;
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    } else {
                                        System.err.println("System tray not supported!");
                                    }

                                    //Auto translation
                                    if (Prefs.getBoolean(AUTO_TRANSLATE, false) == true) {
                                        messagePanel.getTrslt().fire();
                                    }
                                }
                            }
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
            /*
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

             */

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
        chat.join(nickname, room);
        Platform.runLater(() -> {
            messagePanel.add(new Message("Server","Connected: You joined "+room +" as "+nickname));
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
