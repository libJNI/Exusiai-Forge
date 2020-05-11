package xyz.gucciclient.utils;



import java.awt.*;

public class TrayHelper {

    public static void displayTray() {
        try {
            if (SystemTray.isSupported()) {
                SystemTray systemTray = SystemTray.getSystemTray();
                Image image = Toolkit.getDefaultToolkit().getImage("images/tray.gif");
                TrayIcon trayIcon = new TrayIcon(image, String.valueOf(new StringBuilder().append("Exusiai!")));
                trayIcon.setImageAutoSize(true);
                systemTray.add(trayIcon);
                trayIcon.displayMessage(String.valueOf(new StringBuilder().append("Exusiai!")), "外挂已启动", TrayIcon.MessageType.INFO);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
