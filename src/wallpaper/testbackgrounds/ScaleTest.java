/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.testbackgrounds;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import wallpaper.CustomWallpaper;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class ScaleTest extends CustomWallpaper {
    private int divisor = 30;
        
    private final BufferedImage image = new BufferedImage(1920/divisor, 1080/divisor, BufferedImage.TYPE_INT_RGB);
    private final Graphics2D g2 = (Graphics2D) image.getGraphics();
    
    private final Random random = new Random();

    public ScaleTest() {
        g2.setFont(Font.decode("consolas-9"));
        getPanel().addMouseMotionListener(new MouseAdapter() {
            
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e); //To change body of generated methods, choose Tools | Templates.
                image.setRGB(e.getX()/divisor, e.getY()/divisor, random.nextInt(0xFFFFFF));
            }
            
        });
        getPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.
                image.setRGB(e.getX()/divisor, e.getY()/divisor, random.nextInt(0xFFFFFF));
            }
            
        });
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        //g2.scale(10, 10);
        //g2.drawArc(4, 4, 10, 10, 0, 360);
        g.drawImage(image, 0, 0, getPanel().getWidth(), getPanel().getHeight(), 0, 0, image.getWidth(), image.getHeight(), g2.getBackground(), getPanel());
    }
    
}
