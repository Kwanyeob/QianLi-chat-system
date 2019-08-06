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
import java.util.prefs.Preferences;

public class ParamPanel{
    Scene scene;
    FXMLLoader fxmlLoader;

    Stage stage;
    public ParamPanel(String lang) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();

            setupLang(lang);

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

    public void setupLang(String pref_lang) {
        System.out.println("set up lang with "+pref_lang);
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("English");     // English:                 en          0
        choices.add("简体中文");    // Simple chinese :          zh-CN       1
        choices.add("中國傳統的");   //Traditional chinese :     zh-TW       2
        choices.add("Français");    //French :                  fr          3
        choices.add("Deutsch");     //German :                  de          4
        choices.add("Italiano");    //Italian :                 it          5
        choices.add("Español");     //Spanish:                  es          6
        choices.add("Český");       //Czech :                   cs          7
        choices.add("Slovenský");   //Slovak:                   sk          8
        choices.add("日本人");      //Japanese:                  ja          9
        choices.add("한국의");      //Korean:                    ko          10
        choices.add("العربية");     //Arab:                     ar          11
        //End - improving possible, up to the 100 supported languages of google.

        //Corresponding codes array
        String[] langcodes = new String[12];
        langcodes[0] = "en";
        langcodes[1] = "zh-CN";
        langcodes[2] = "zh-TW";
        langcodes[3] = "fr";
        langcodes[4] = "de";
        langcodes[5] = "it";
        langcodes[6] = "es";
        langcodes[7] = "cs";
        langcodes[8] = "sk";
        langcodes[9] = "ja";
        langcodes[10] = "ko";
        langcodes[11] = "ar";

        ObservableList<String> list = FXCollections.observableArrayList();
        ChoiceBox language_selector = (ChoiceBox) scene.lookup("#language_selector");
        
        Platform.runLater(() -> {
            int index = 0;
            for (int i= 0; i< langcodes.length; i++){
                if (pref_lang.equals(langcodes[i])){
                    index = i;
                    System.out.println("Found correspondence at index "+index);
                    break;
                }
            }
            language_selector.getSelectionModel().select(index);
        });

        for (String lang : choices) {
            list.add(lang);
        }
        language_selector.setItems(list);
        language_selector.setOnAction(e -> {
            int id_chosen = language_selector.getSelectionModel().getSelectedIndex();
            String chosen = langcodes[id_chosen];

            Preferences myLangPref = Preferences.userRoot().node("qianli/chat-client/prefs");
            myLangPref.put("lang", chosen);
            System.out.println("Prefered language set to "+chosen);
        });
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