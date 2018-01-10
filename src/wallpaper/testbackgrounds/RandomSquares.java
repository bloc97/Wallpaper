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
import wallpaper.DisplayPanelSingleThread;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class RandomSquares extends DisplayPanelSingleThread {


    public RandomSquares(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
    }
    
    @Override
    public void prePaint(PreciseTime dt) {
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.clearRect(0, 0, getWidth(), getHeight());
        //g.drawRect(5, 5, 100, 100);
        //g.setColor(Color.BLACK);
        for (int i=0; i<getWidth(); i+=10) {
            for (int j=0; j<getHeight(); j+=10) {
                if (Math.random() < 0.5d) {
                    g.fillRect(i, j, 10, 10);
                    //g.setColor(Color.WHITE);
                } else {

                    //g.setColor(Color.BLACK);
                }
            }
        }
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }
    
}
