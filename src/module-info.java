module JFX.UI {
    requires javafx.fxml;
    requires javafx.controls;
    requires socketio;
    requires java.desktop;
    requires language.detector;
    requires google.api.client.repackaged.com.google.common.base;
    requires guava;
    requires java.prefs;

    opens sample;

}