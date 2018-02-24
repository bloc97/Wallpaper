/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.CustomWallpaper;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class RandomSquares extends CustomWallpaper {

    
    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.clearRect(0, 0, getPanel().getWidth(), getPanel().getHeight());
        //g.drawRect(5, 5, 100, 100);
        //g.setColor(Color.BLACK);
        for (int i=0; i<getPanel().getWidth(); i+=10) {
            for (int j=0; j<getPanel().getHeight(); j+=10) {
                if (Math.random() < 0.5d) {
                    g.fillRect(i, j, 10, 10);
                    //g.setColor(Color.WHITE);
                } else {

                    //g.setColor(Color.BLACK);
                }
            }
        }
    }
    
}
