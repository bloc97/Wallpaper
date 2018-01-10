/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.DisplayPanelSingleThread;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class ScaleTest extends DisplayPanelSingleThread {
    private int divisor = 30;
        
    private final BufferedImage image = new BufferedImage(1920/divisor, 1080/divisor, BufferedImage.TYPE_INT_RGB);
    private final Graphics2D g2 = (Graphics2D) image.getGraphics();
    
    private final Random random = new Random();

    public ScaleTest(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
        g2.setFont(Font.decode("consolas-9"));
        this.addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
                image.setRGB(e.getX()/divisor, e.getY()/divisor, random.nextInt(0xFFFFFF));
            }
            
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                image.setRGB(e.getX()/divisor, e.getY()/divisor, random.nextInt(0xFFFFFF));
            }
            
        });
    }

    @Override
    public void prePaint(PreciseTime dt) {
        //g2.drawString("Hello world!", 2, 10);
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        //g2.scale(10, 10);
        //g2.drawArc(4, 4, 10, 10, 0, 360);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), 0, 0, image.getWidth(), image.getHeight(), g2.getBackground(), this);
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }
    
}
