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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //This is the window content

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        //Main stage = window
        primaryStage.setTitle("Hello World");

        MainScene ms = new MainScene();

        Scene scene = new Scene(ms.getBorder(), 800, 600 );
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
