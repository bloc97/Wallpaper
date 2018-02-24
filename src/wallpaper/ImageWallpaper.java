/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author bowen
 */
public class ImageWallpaper extends CustomWallpaper {

    private BufferedImage image;
    
    private double scale = 1;

    public ImageWallpaper() {
        getPanel().setBackground(Color.BLACK);
    }
    
    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        if (image != null) {
            double xTrans = (getPanel().getWidth() - image.getWidth() * scale) / scale / 2;
            double yTrans = (getPanel().getHeight()- image.getHeight() * scale) / scale / 2;
            ((Graphics2D) g).scale(scale, scale);
            g.drawImage(image, (int)xTrans, (int)yTrans, getPanel());
        }
    }
    
    public void setImage(BufferedImage image) {
        
        if (image.getHeight() != getPanel().getHeight() || image.getWidth() != getPanel().getWidth()) {
            scale = Math.min((double)(getPanel().getHeight() + Background.MAXIMIZE_OFFSET) / image.getHeight(), (double)getPanel().getWidth() / image.getWidth());
        }
        
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
    
}
