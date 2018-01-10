/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Graphics;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.DisplayPanelDualThread;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class MovingBarMT extends DisplayPanelDualThread {

    private double x = 0;

    public MovingBarMT(ScheduledExecutorService executorService, int ups, int fps) {
        super(executorService, ups, fps);
    }
    
    @Override
    public void prePaint(PreciseTime dtSinceLastTick) {
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dtSinceLastTick) {
        g.clearRect(0, 0, getWidth(), getHeight());
        //g.fillRect((int)(x), 0, 100, 3000);
        g.fillRect((int)(x + (dtSinceLastTick.getNanos()/ 300000d)), 0, 100, 3000);
    }

    @Override
    public void postPaint(PreciseTime dtSinceLastTick) {
    }

    @Override
    public void onTick(PreciseTime dt) {
        x += dt.getNanos() / 300000d;
        x %= 4000;
    }
    
}
