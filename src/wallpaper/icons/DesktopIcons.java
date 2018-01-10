/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.icons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import sun.awt.shell.ShellFolder;
import wallpaper.DisplayPanelSingleThread;
import wallpaper.PreciseTime;

/**
 *
 * @author bowen
 */
public class DesktopIcons extends DisplayPanelSingleThread {

    private final FileSystemView fs = FileSystemView.getFileSystemView();
    
    private final Set<DesktopIcon> icons = new LinkedHashSet<>();
    
    private DisplayPanelSingleThread background = null;
    
    public DesktopIcons(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
        this.setOpaque(false);
        //this.setBackground(new Color(0x00000000, true));
        int yc = 0;
        
        File[] publicDesktopFiles = fs.getDefaultDirectory().getParentFile().getParentFile().listFiles((File pathname) -> pathname.getName().equals("Public"))[0].listFiles((File pathname) -> pathname.getName().equals("Desktop"))[0].listFiles();
        for (File file : publicDesktopFiles) {
            DesktopIcon icon = new DesktopIcon(new Point(10, 10 + yc), file); 
            icons.add(icon);
            yc += 32 + 8;
        }
        File[] desktopFiles = fs.getHomeDirectory().listFiles((File pathname) -> !pathname.getName().startsWith("::"));
        for (File file : desktopFiles) {
            DesktopIcon icon = new DesktopIcon(new Point(10, 10 + yc), file); 
            icons.add(icon);
            yc += 32 + 8;
        }
    }

    public void setBackground(DisplayPanelSingleThread background) {
        this.background = background;
    }
    
    @Override
    public void prePaint(PreciseTime dt) {
        if (background != null) {
            background.prePaint(dt);
        }
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        return;
        g.clearRect(0, 0, getWidth(), getHeight());
        if (background != null) {
            background.onPaint(g, dt);
        }
        ((Graphics2D) g).scale(2, 2);
        
        for (DesktopIcon icon : icons) {
            icon.painIcon(this, g, true);
            g.drawString(icon.getFile().getName(), icon.getX(), icon.getY() + 32 + 2);
        }
    }

    @Override
    public void postPaint(PreciseTime dt) {
        if (background != null) {
            background.postPaint(dt);
        }
    }
    
}
