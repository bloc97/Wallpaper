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
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Wallpaper {
    
    public static void main(String[] args) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        
        JFrame frame = new JFrame("Bloc Active Wallpaper");
        frame.setFocusable(true);
        frame.setUndecorated(true); //Remove top bar
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Remove icon from taskbar
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        DisplayPanel panel = new DisplayPanel(toolkit.getScreenSize().width, toolkit.getScreenSize().height);
        
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
        
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                /*
                double milisFrameTime = 1000D / 60D;
                double nextTime = System.currentTimeMillis() + milisFrameTime;
                
                long nanoInSecond = 1000000000l;
                double nanosToSleep = nanoInSecond / 60D;
                
                long milis = (long)(nanosToSleep / 1000000);
                int nanos = (int)(nanosToSleep % 1000000);*/
                
                while (true) {
                    panel.tick();
                    
                    int FPS10 = 100;
                    int FPS30 = 33;
                    int FPS60 = 16;
                    
                    try {
                        Thread.sleep(FPS60);
                        //Thread.sleep(milis, nanos);
                        //Thread.sleep((long)(1000D/60));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Wallpaper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        while (true) {
            frame.toBack();
            try {
                Thread.sleep(1000);
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