/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.icons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.swing.filechooser.FileSystemView;
import wallpaper.PreciseTime;
import wallpaper.CustomWallpaper;

/**
 *
 * @author bowen
 */
public class DesktopIcons extends CustomWallpaper {

    private final FileSystemView fs = FileSystemView.getFileSystemView();
    
    private final Set<DesktopIcon> icons = new LinkedHashSet<>();
    
    public DesktopIcons() {
        
        this.getPanel().setOpaque(false);
        this.getPanel().setBackground(new Color(0, 0, 0, 0));
        
        int xc = 0;
        int yc = 0;
        
        File[] publicDesktopFiles = fs.getDefaultDirectory().getParentFile().getParentFile().listFiles((File pathname) -> pathname.getName().equals("Public"))[0].listFiles((File pathname) -> pathname.getName().equals("Desktop"))[0].listFiles();
        for (File file : publicDesktopFiles) {
            if (yc >= 1080) {
                yc = 0;
                xc += 32 + 8;
            }
            DesktopIcon icon = new DesktopIcon(new Point(10 + xc, 10 + yc), file); 
            icons.add(icon);
            yc += 32 + 8;
        }
        File[] desktopFiles = fs.getHomeDirectory().listFiles((File pathname) -> !pathname.getName().startsWith("::"));
        for (File file : desktopFiles) {
            if (yc >= 1080) {
                yc = 0;
                xc += 32 + 8;
            }
            DesktopIcon icon = new DesktopIcon(new Point(10 + xc, 10 + yc), file); 
            icons.add(icon);
            yc += 32 + 8;
        }
    }
    
    @Override
    public void prePaint(PreciseTime dt) {
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        ((Graphics2D) g).scale(2, 2);
        
        for (DesktopIcon icon : icons) {
            icon.painIcon(getPanel(), g, true);
            g.drawString(icon.getFile().getName(), icon.getX(), icon.getY() + 32 + 2);
        }
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }

    @Override
    public void onTick(PreciseTime dt) {
    }
    
}
