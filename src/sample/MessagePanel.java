package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
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
        //Buttons container
        HBox opt = new HBox();
        opt.getStyleClass().add("msg-toolbar");
        opt.setPadding(new Insets(5, 5, 5, 5));
        opt.setSpacing(5);

        //Copy button
        Button cpy = new Button("ctrl+c");
        cpy.getStyleClass().add("msg-optbtn");
        cpy.setTooltip(new Tooltip("Copy to clipboard"));

        //Translate button
        Button trslt = new Button("\uD83C\uDF0E");
        trslt.getStyleClass().add("msg-optbtn");
        trslt.setTooltip(new Tooltip("Translate"));

        opt.getChildren().addAll(trslt, cpy);

        VBox msgbox = new VBox();
        msgbox.getChildren().addAll(m.getDisplayBox(), opt);

        //Cpy button action
        cpy.setOnAction(e ->{
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent cc = new ClipboardContent();
            cc.putString(m.getContent());
            clipboard.setContent(cc);
            System.out.println("Content copied");
        });

        //Action of button Translate
        trslt.setOnAction(e -> {
                    Translator http = new Translator();
                    if (m != null) {
                        String msg = m.getContent();
                        String result = http.translate(null, "fr", msg);
                        if (result != null && result.isEmpty() == false) {
                            //display OK
                            System.out.println("Message translated, result: '" + result + "'");
                            int mcpt = this.messages.indexOf(m);

                            Message NewMsg =  new Message(m.getSender(), result);
                            this.messages.set(mcpt, NewMsg);
                            int cpt = msgbox.getChildren().indexOf(m.getDisplayBox());
                            m.setContent(result);
                            msgbox.getChildren().set(cpt, NewMsg.getDisplayBox());
                        } else {
                            System.out.println("Translation failed...");
                        }
                    }
                });

        this.messages.add(m);
        viewport.getChildren().add(msgbox);
    }


}
