/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Component;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

/**
 *
 * @author bowen
 */
public abstract class CustomWallpaper implements Wallpaper {
    
    private volatile long lastTickTimeNanos;
    private PreciseTime currentdt;
    
    private CustomWallpaper overlay;

    private JPanel panel = new JPanel(true) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            internalPaint(g, currentdt);
        }
    };
    
    public final void mainTick() {
        long currentNanos = System.nanoTime();
        PreciseTime dt = new PreciseTime(currentNanos - lastTickTimeNanos, TimeUnit.NANOSECONDS);
        currentdt = dt;
        lastTickTimeNanos = currentNanos;
        internalTick(currentdt);
    }
    
    public final void paintTick() {
        internalPrePaint(currentdt);
        panel.repaint();
        internalPostPaint(currentdt);
    }
    
    private void internalTick(PreciseTime dt) {
        onTick(dt);
        if (overlay != null) {
            overlay.onTick(dt);
        }
    }
    
    private void internalPrePaint(PreciseTime dt) {
        prePaint(dt);
        if (overlay != null) {
            overlay.internalPrePaint(dt);
        }
    }
    private void internalPaint(Graphics g, PreciseTime dt) {
        onPaint(g.create(), dt);
        if (overlay != null) {
            overlay.internalPaint(g, dt);
        }
    }
    private void internalPostPaint(PreciseTime dt) {
        postPaint(dt);
        if (overlay != null) {
            overlay.internalPostPaint(dt);
        }
    }
    
    public void onTick(PreciseTime dt) { //Dt is time since last tick, if Singlethreaded, tick runs before prePaint
    }
    
    public void prePaint(PreciseTime dt) {
    }
    public abstract void onPaint(Graphics g, PreciseTime dt); //Dt is time since last tick
    public void postPaint(PreciseTime dt) {
    }
    
    @Override
    public final Component getComponent() {
        return panel;
    }
    
    public final JPanel getPanel() {
        return panel;
    }
    
    public final boolean setOverlay(CustomWallpaper overlay) {
        if (!this.equals(overlay)) {
            this.overlay = overlay;
            return true;
        }
        return false;
    }
}
