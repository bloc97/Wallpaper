/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Graphics;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author bowen
 */
public class MovingBarST extends DisplayPanelSingleThread {

    private double x = 0;

    public MovingBarST(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
    }
    
    @Override
    public void prePaint(PreciseTime dt) {
        x += dt.getNanos() / 300000d;
        x %= 4000;
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.fillRect((int)(x), 0, 100, 3000);
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }
    
}
