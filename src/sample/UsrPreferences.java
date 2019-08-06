package sample;
import java.util.prefs.Preferences;

public class UsrPreferences {
    private Preferences prefs;

    public void initPreference() {
        // This will define a node in which the preferences can be stored
        prefs = Preferences.userRoot().node(this.getClass().getName());
        String USERNAME = "user";
        String LANG = "en";

        prefs.put(USERNAME,"user");
        prefs.put(LANG, "en");

    }

    public Preferences getPreferences() {
        return prefs;
    }

    public void setPreference(){

    }
}
