/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.concurrent.ScheduledExecutorService;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import sun.awt.shell.ShellFolder;

/**
 *
 * @author bowen
 */
public class DesktopIcons extends DisplayPanelSingleThread {

    private final FileSystemView fs = FileSystemView.getFileSystemView();
    
    public DesktopIcons(ScheduledExecutorService executorService, int fps) {
        super(executorService, fps);
    }

    @Override
    public void prePaint(PreciseTime dt) {
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        g.clearRect(0, 0, getWidth(), getHeight());
        ((Graphics2D) g).scale(2, 2);
        int xc = 0;
        File[] publicDesktopFiles = fs.getDefaultDirectory().getParentFile().getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().equals("Public");
            }
        })[0].listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().equals("Desktop");
            }
        })[0].listFiles();
        for (File file : publicDesktopFiles) {
            Icon icon = getSystemIcon(file, true);
            icon.paintIcon(this, g, 10, 10 + xc);
            xc += icon.getIconWidth() + 8;
            g.drawString(file.getName(), 10, 10 + xc);
        }
        File[] desktopFiles = fs.getHomeDirectory().listFiles((File pathname) -> {
            return true;//(pathname.getName().endsWith(".lnk"));
        });
        for (File file : desktopFiles) {
            Icon icon = getSystemIcon(file, true);
            icon.paintIcon(this, g, 10, 10 + xc);
            xc += icon.getIconWidth() + 8;
            g.drawString(file.getName(), 10, 10 + xc);
        }
    }

    @Override
    public void postPaint(PreciseTime dt) {
    }
    
    /**
     * Icon for a file, directory, or folder as it would be displayed in
     * a system file browser. Example from Windows: the "M:\" directory
     * displays a CD-ROM icon.
     *
     * The default implementation gets information from the ShellFolder class.
     *
     * @param f a <code>File</code> object
     * @return an icon as it would be displayed by a native file chooser
     * @see JFileChooser#getIcon
     * @since 1.4
     */
    public Icon getSystemIcon(File f, boolean isLargeIcon) {
        if (f == null) {
            return null;
        }

        ShellFolder sf;

        try {
            sf = ShellFolder.getShellFolder(f);
        } catch (FileNotFoundException e) {
            return null;
        }

        Image img = sf.getIcon(isLargeIcon);

        if (img != null) {
            return new ImageIcon(img, sf.getFolderType());
        } else {
            return UIManager.getIcon(f.isDirectory() ? "FileView.directoryIcon" : "FileView.fileIcon");
        }
    }
}
