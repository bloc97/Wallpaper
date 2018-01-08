/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

/**
 *
 * @author bowen
 */
public abstract class DisplayPanelDualThread extends JPanel {
    
    private ScheduledFuture tickFuture;
    private ScheduledFuture updateFuture;
    
    private ScheduledExecutorService executorService;
    
    private volatile long lastTickTimeNanos;
    private int ups;
    private int fps;
    
    public DisplayPanelDualThread(ScheduledExecutorService executorService, int ups, int fps) {
        this.executorService = executorService;
        this.lastTickTimeNanos = System.nanoTime();
        this.ups = ups;
        this.fps = fps;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
        if (isTickRunning()) {
            stopTick();
            startTick();
        }
        if (isRenderRunning()) {
            stopRender();
            startRender();
        }
    }
    
    public boolean isRenderRunning() {
        if (updateFuture != null) {
            return !updateFuture.isDone();
        }
        return false;
    }
    
    public boolean isTickRunning() {
        if (tickFuture != null) {
            return !tickFuture.isDone();
        }
        return false;
    }
    
    public boolean startRender() {
        if (!isRenderRunning()) {
            updateFuture = executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            }, TimeUnit.SECONDS.toNanos(1)/(500), TimeUnit.SECONDS.toNanos(1)/fps, TimeUnit.NANOSECONDS);
            return true;
        }
        return false;
    }
    
    public boolean startTick() {
        if (!isTickRunning()) {
            this.lastTickTimeNanos = System.nanoTime();
            tickFuture = executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    long currentNanos = System.nanoTime();
                    onTick(new PreciseTime(currentNanos - lastTickTimeNanos, TimeUnit.NANOSECONDS));
                    lastTickTimeNanos = currentNanos;
                }
            }, 0, TimeUnit.SECONDS.toNanos(1)/ups, TimeUnit.NANOSECONDS);
            return true;
        }
        return false;
    }
    
    
    public boolean stopRender() {
        if (updateFuture != null) {
            if (updateFuture.cancel(true)) {
                updateFuture = null;
                return true;
            }
        }
        return false;
    }
    public boolean stopTick() {
        if (tickFuture != null) {
            if (tickFuture.cancel(true)) {
                tickFuture = null;
                return true;
            }
        }
        return false;
    }

    public boolean setUPS(int ups) {
        this.ups = ups;
        if (isTickRunning()) {
            stopTick();
            return startTick();
        }
        return true;
    }

    public boolean setFPS(int fps) {
        this.fps = fps;
        if (isRenderRunning()) {
            stopRender();
            return startRender();
        }
        return true;
    }

    public int getUPS() {
        return ups;
    }

    public int getFPS() {
        return fps;
    }
    
    public abstract void onTick(PreciseTime dt);
    
    public final void update() {
        PreciseTime dtSinceLastTick = new PreciseTime(System.nanoTime() - lastTickTimeNanos, TimeUnit.NANOSECONDS);
        //repaint();
        prePaint(dtSinceLastTick);
        Graphics g = getGraphics();
        if (g != null) {
            onPaint(g, dtSinceLastTick);
            Toolkit.getDefaultToolkit().sync();
            g.dispose();
        }
        postPaint(dtSinceLastTick);
    }
    
    public abstract void prePaint(PreciseTime dtSinceLastTick);
    public abstract void onPaint(Graphics g, PreciseTime dtSinceLastTick);
    public abstract void postPaint(PreciseTime dtSinceLastTick);
    
    
}
