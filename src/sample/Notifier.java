package sample;
import java.awt.TrayIcon;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.SystemTray;
import java.awt.TrayIcon.MessageType;

import static java.lang.Thread.sleep;

public class Notifier {
    private String img;
    TrayIcon trayIcon;

    public Notifier(String img)  throws AWTException {
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage(img);
        trayIcon = new TrayIcon(image, "QianLi");

        //Set tooltip text for the tray icon
        trayIcon.setToolTip("QianLi website chat");

        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);


        PopupMenu popup = new PopupMenu();

        // Create a pop-up menu components
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Quit");

        //Add components to pop-up menu
        popup.add(openItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        tray.add(trayIcon);
    }

    public void display(String title, String content) {
        trayIcon.displayMessage(title, content, MessageType.INFO);

    }

    public void delete() throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        tray.remove(trayIcon);

    }

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public void icon(String img) {
        this.img = img;
    }
}