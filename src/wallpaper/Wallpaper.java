/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

/**
 *
 * @author bowen
 */
public abstract class Wallpaper {
    
    private volatile long lastTickTimeNanos;
    private PreciseTime currentdt;
    
    protected JPanel panel = new JPanel(true) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onPaint(g, currentdt);
        }
    };
    
    public void mainTick() {
        long currentNanos = System.nanoTime();
        PreciseTime dt = new PreciseTime(currentNanos - lastTickTimeNanos, TimeUnit.NANOSECONDS);
        currentdt = dt;
        lastTickTimeNanos = currentNanos;
        onTick(currentdt);
    }
    
    public void paintTick() {
        prePaint(currentdt);
        panel.repaint();
        postPaint(currentdt);
    }
    
    public abstract void onTick(PreciseTime dt); //Dt is time since last tick, if Singlethreaded, tick runs before prePaint
    
    public abstract void prePaint(PreciseTime dt);
    public abstract void onPaint(Graphics g, PreciseTime dt); //Dt is time since last tick
    public abstract void postPaint(PreciseTime dt);
    
}
