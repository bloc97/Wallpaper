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

public class Wallpaper {
    
    private static volatile long lastTickTimeNanos = System.nanoTime();
        
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        
        JFrame frame = new JFrame("Bloc Active Wallpaper");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setFocusable(true);
        frame.setUndecorated(true); //Remove top bar
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Remove icon from taskbar
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        //DisplayPanel panel = new DisplayPanel(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        //frame.setSize(300, 300);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        DisplayPanelSingleThread panel = new MovingBarST(executor, 60);
        
        frame.add(panel);
        
        //frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                frame.toBack();
            }
        });
        
        frame.setVisible(true);
        frame.toBack();
        
        int FPS = 60;
        int UPS = 60;
        
        //panel.startTick();
        panel.startRender();
        
        /*
        executor.scheduleAtFixedRate(new Runnable() {
            
            @Override
            public void run() {
                long currentNanos = System.nanoTime();
                panel.tick(new PreciseTime(currentNanos - lastTickTimeNanos, TimeUnit.NANOSECONDS));
                lastTickTimeNanos = currentNanos;
            }
        }, 0, TimeUnit.SECONDS.toNanos(1)/UPS, TimeUnit.NANOSECONDS);
        
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                panel.update(new PreciseTime(System.nanoTime() - lastTickTimeNanos, TimeUnit.NANOSECONDS));
            }
        }, TimeUnit.SECONDS.toNanos(1)/(UPS * 2), TimeUnit.SECONDS.toNanos(1)/FPS, TimeUnit.NANOSECONDS);
        */
        
        boolean isFlip = false;
        
        while (true) {
            frame.toBack();
            /*
            isFlip = !isFlip;
            if (isFlip) {
                panel.setFPS(30);
            } else {
                panel.setFPS(60);
            }*/
            
            try {
                Thread.sleep(300);
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