/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.PreciseTime;
import wallpaper.CustomWallpaper;

/**
 *
 * @author bowen
 */
public class MovingBar extends CustomWallpaper {

    private double x = 0;

    public MovingBar() {
        getPanel().setBackground(Color.WHITE);
    }
    
    
    @Override
    public void prePaint(PreciseTime dt) {
        x += dt.getNanos() / 300000d;
        x %= 4000;
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.fillRect((int)(x), 0, 100, 3000);
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }

    @Override
    public void onTick(PreciseTime dt) {
    }
    
}
