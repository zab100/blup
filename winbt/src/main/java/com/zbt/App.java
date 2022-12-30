package com.zbt;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.PopupMenu;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.ImageIcon;

public class App 
{
    public static boolean logging = false;
    public static boolean snooping = false;

    public static void main( String[] args ) {
        System.out.println("Starting WinBT with arguments " + Arrays.toString(args));

        for(String argument : args){
            switch(argument){
                case "--logging":
                    logging = true;
                    new File("./logs").mkdirs();
                    break;
                case "--snoop":
                    snooping = true;
                    break;
                default:
                    break;
            }
        }

        NetworkingManager networkingManager = new NetworkingManager();

        if(!SystemTray.isSupported()){
            System.out.println("System tray not supported. Not running on Windows");
            System.exit(1);
        }

        PopupMenu popup = new PopupMenu();
        TrayIcon trayIcon = new TrayIcon(new ImageIcon("./TrayIcon.png").getImage());
        SystemTray systemTray = SystemTray.getSystemTray();

        MenuItem quit = new MenuItem("Quit");
        quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                networkingManager.closeAllConnections();
                System.exit(0);
            }
        });
        popup.add(quit);
        trayIcon.setPopupMenu(popup);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        networkingManager.beginLoop();
        System.out.println("Networking Manager Loop Begun");
    }
}
