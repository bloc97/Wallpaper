/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

/**
 *
 * @author bowen
 */
import java.awt.Color;
import wallpaper.icons.DesktopIcons;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import wallpaper.testbackgrounds.MovingBarST;

public class Wallpaper {
    
    private static volatile long lastTickTimeNanos = System.nanoTime();
        
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        
        JFrame frame = new JFrame("Bloc Active Wallpaper");
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setFocusable(true);
        frame.setUndecorated(true); //Remove top bar
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Remove icon from taskbar
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height - 40);
        //DisplayPanel panel = new DisplayPanel(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        //frame.setSize(300, 300);
        //ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        DisplayPanelSingleThread backPanel = new MovingBarST(executor, 30);
        DesktopIcons panel = new DesktopIcons(executor, 60);
        frame.add(panel);
        panel.setBackground(backPanel);
        
        frame.setFocusable(true);
        frame.setFocusableWindowState(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                frame.toBack();
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e); //To change body of generated methods, choose Tools | Templates.
                frame.toBack();
            }
            
        });
        
        frame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e); //To change body of generated methods, choose Tools | Templates.
                frame.toBack();
            }
            
        });
        
        frame.validate();
        frame.setVisible(true);
        
        
        panel.startRender();
        
        frame.toBack();
        
        for (int i=0; i<4; i++) {
            frame.toBack();
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                
            }
        }
    }
    
    
    public static void main2(String[] args) {
        JFrame frame = new JFrame("Hello!!");

        // Set's the window to be "always on top"
        //frame.setAlwaysOnTop(true);
        frame.setUndecorated(true); //Remove top bar
        frame.setFocusable(true);
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Remove icon from taskbar
        //frame.setLocationByPlatform(true);
        frame.add(new JLabel("  Isn't this annoying?"));
        frame.pack();
        frame.setVisible(true);
        frame.addFocusListener(new FocusAdapter() {
            
            @Override
            public void focusGained(FocusEvent e) {
                frame.toBack();
            }
        });
        while (true) {
            frame.toBack(); //Keep in background
        }
    }
}