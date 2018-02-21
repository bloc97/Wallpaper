/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author bowen
 */
public class Background {
    
    protected final JFrame frame;

    public Background(String name, boolean isTransparent) {
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        this.frame = new JFrame(name);
        
        frame.setFocusable(true); //Can click
        frame.setFocusableWindowState(false); //Prevent bring to foreground
        frame.setUndecorated(true); //Remove top bar
        frame.setType(javax.swing.JFrame.Type.UTILITY); //Remove icon from taskbar
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Exit app on close
        frame.setSize(toolkit.getScreenSize().width, toolkit.getScreenSize().height);// - 40); //Size minus taskbar
        
        frame.addWindowListener(new WindowAdapter() { //Send to back just in case
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                frame.toBack();
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                frame.toBack();
            }
        });
        frame.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                frame.toBack();
            }
        });
        
        if (isTransparent) {
            frame.setBackground(new Color(0, 0, 0, 0));
        }
        
        frame.validate();
        frame.setVisible(true);
        
        frame.toBack();
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        
        executor.schedule(() -> {
                for (int i=0; i<4; i++) {
                    frame.toBack();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {

                    }
                }
        }, 0, TimeUnit.MILLISECONDS);
        
    }
    
    public void setForeground(Wallpaper wallpaper) {
        frame.setGlassPane(wallpaper.panel);
        wallpaper.panel.setVisible(true);
    }
    
    public void setBackground(Wallpaper wallpaper) {
        frame.removeAll();
        frame.add(wallpaper.panel);
    }
    
}
