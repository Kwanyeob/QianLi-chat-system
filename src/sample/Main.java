package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //This is the window content

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Main stage = window
        primaryStage.setTitle("Hello World");

        MainScene ms = new MainScene();

        Scene scene = new Scene(ms.getBorder(), 800, 600 );

        String fontSheet = fileToStylesheetString( new File ("resources/style.css") );

        if ( fontSheet == null ) {
            //Do Whatever you want with logging/errors/etc.
            System.out.println("CSS File not recognized");
        } else {
            scene.getStylesheets().add( fontSheet );
            System.out.println("CSS File Detected, applying...");
        }

        System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
