/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.icons;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import sun.awt.shell.ShellFolder;

/**
 *
 * @author bowen
 */
public class DesktopIcon {
    private final Point position;
    private final File file;
    
    private final Icon smallIcon;
    private final Icon largeIcon;

    public DesktopIcon(File file) {
        this(new Point(), file);
    }
    
    public DesktopIcon(Point position, File file) {
        this.position = position;
        this.file = file;
        smallIcon = getSystemIcon(file, false);
        largeIcon = getSystemIcon(file, true);
    }
    
    public File getFile() {
        return file;
    }

    public Point getPosition() {
        return position;
    }
    
    public int getX() {
        return position.x;
    }
    
    public int getY() {
        return position.y;
    }

    public void setPosition(Point position) {
        this.position.setLocation(position);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DesktopIcon) {
            DesktopIcon di = (DesktopIcon) obj;
            return (getFile().equals(di.getFile()));
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.file);
        return hash;
    }
    
    public Icon getIcon(boolean isLargeIcon) {
        return isLargeIcon ? largeIcon : smallIcon;
    }
    
    public void painIcon(Component c, Graphics g, boolean isLargeIcon) {
        getIcon(isLargeIcon).paintIcon(c, g, getPosition().x, getPosition().y);
    }
    
    /**
     * Icon for a file, directory, or folder as it would be displayed in
     * a system file browser. Example from Windows: the "M:\" directory
     * displays a CD-ROM icon.
     *
     * The default implementation gets information from the ShellFolder class.
     *
     * @param f a <code>File</code> object
     * @param isLargeIcon if true, tries to return a 32x32 icon instead of the usual 16x16
     * @return an icon as it would be displayed by a native file chooser
     * @see JFileChooser#getIcon
     * @since 1.4
     */
    public static Icon getSystemIcon(File f, boolean isLargeIcon) {
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
