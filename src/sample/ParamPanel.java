package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ParamPanel{
    Scene scene;
    FXMLLoader fxmlLoader;

    Stage stage;
    public ParamPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> mylist = new ArrayList<String>();
                    mylist.add("Country 1");
                    mylist.add("Country 2");
                    mylist.add("Country 3");
                    setupLang(mylist);
                }
            });
        } catch (Exception e){
            System.out.println("Ouvertures param impossible");
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void show(){
        stage.show();
    }

    public void setupLang(ArrayList<String> choices ) {
        ObservableList<String> list = FXCollections.observableArrayList();
        ChoiceBox language_selector = (ChoiceBox) scene.lookup("#language_selector");

        for (String lang : choices) {
            list.add(lang);
        }
        language_selector.setItems(list);
    }

    public void writeFile(String filepath){
        try {
            FileWriter fstream = new FileWriter(filepath);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("yes");
        }catch (Exception e){
            System.out.println("Error : "+e.getMessage());
        }
    }


}
