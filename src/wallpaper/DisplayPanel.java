/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author bowen
 */
public final class DisplayPanel extends JPanel {
    
    
    private int x = 0;
    
    public DisplayPanel(int width, int height) {
        this.setSize(width, height);
        this.setOpaque(false);
    }

    public void tick() {
        //repaint();
        Graphics g = getGraphics();
        if (g != null) {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.fillRect(x, 0, 100, 600);
            g.dispose();
            Toolkit.getDefaultToolkit().sync();
        }
        x += 20;
        x %= 1000;
    }
    
    
}
