/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import javax.swing.JPanel;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

/**
 *
 * @author bowen
 */
public class CustomVideoWallpaper implements Wallpaper {
    
    private final String video;
    
    private DirectMediaPlayerComponent mediaPlayerComponent;
    private DirectMediaPlayer mediaPlayer;
    
    private volatile BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
    
    private double scale = 1;
    
    
    private final JPanel panel = new JPanel() {
        
        private int c = 0;
        
        @Override
        public void paintComponent(Graphics g) {
            superPaint(g);
            double xTrans = (panel.getWidth() - image.getWidth() * scale) / scale / 2;
            ((Graphics2D) g).scale(scale, scale);
            g.drawImage(image, (int)xTrans, 0, this);
        }
        
        public void superPaint(Graphics g) {
            if (c%1000 == 0) {
                super.paintComponent(g);
                c = 0;
            }
            c++;
        }
    };
    
    public CustomVideoWallpaper(String videoUrl) {
        
        boolean found = new NativeDiscovery().discover();
        panel.setBackground(Color.BLACK);
        video = videoUrl;
        
    }

    private class RenderCallbackAdapterEx extends RenderCallbackAdapter {
        private RenderCallbackAdapterEx() {
            super(new int[panel.getWidth() * panel.getHeight()]);
        }
        
        @Override
        protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
            // Simply copy buffer to the image and repaint
            int height = mediaPlayer.getVideoDimension().height;
            int width = mediaPlayer.getVideoDimension().width;
            
            if (height != image.getHeight() || width != image.getWidth()) {
                image = new BufferedImage((int)mediaPlayer.getVideoDimension().getWidth(), (int)mediaPlayer.getVideoDimension().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                
                if (height != panel.getHeight() || width != panel.getWidth()) {
                    scale = Math.min((double)(panel.getHeight() + Background.MAXIMIZE_OFFSET) / height, (double)panel.getWidth()/ width);
                }
            }
            
            image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbBuffer, 0, image.getWidth());
            
            panel.repaint();
        }
    }
    
    public void start() {
        
        
        mediaPlayerComponent = new DirectMediaPlayerComponent(RV32BufferFormat::new) {
            @Override
            protected RenderCallback onGetRenderCallback() {
                return new RenderCallbackAdapterEx();
            }
        };
        mediaPlayer = mediaPlayerComponent.getMediaPlayer();
        
        mediaPlayer.setRepeat(true);
        mediaPlayer.playMedia(video);
        //mediaPlayer.mute();
        mediaPlayer.setPlaySubItems(true);
    }
    
    @Override
    public Component getComponent() {
        return panel;
    }

    public JPanel getPanel() {
        return panel;
    }
}


        /*
        WinDef.HWND desktop = User32.INSTANCE.FindWindow("test", "test");
        WinDef.HDC desktopWindow = User32.INSTANCE.GetDC(desktop);
        for (int i=0; i<1920; i++) {
            for (int j=0; j<1080; j++) {
                GDI32.INSTANCE.SetPixel(desktopWindow, i, j, 155);
            }
        }
        */
        /*
        
        WinDef.HWND progman = User32.INSTANCE.FindWindow("Progman", null);
        
        
        User32.INSTANCE.PostMessage(progman, 0x052C, new WinDef.WPARAM(0), new WinDef.LPARAM(0));
        User32.INSTANCE.SendMessageTimeout(progman, 0x052C, new WinDef.WPARAM(0), new WinDef.LPARAM(0), User32.SMTO_NORMAL, 1000, new WinDef.DWORDByReference());
        

        User32.INSTANCE.EnumWindows((hwnd, pntr) -> {

            WinDef.HWND p = User32.INSTANCE.FindWindowEx(hwnd, new WinDef.HWND(Pointer.createConstant(0)), "SHELLDLL_DefView", null);

            if (p != null) {
                workerw = User32.INSTANCE.FindWindowEx(new WinDef.HWND(Pointer.createConstant(0)), hwnd, "WorkerW", null);
            }

            return true;
        }, Pointer.createConstant(0));
        
        if (workerw != null) {
            WinNT.HANDLE image = User32.INSTANCE.LoadImage(null, "c:\\test.bmp", User32.IMAGE_BITMAP, 0, 0, User32.LR_LOADFROMFILE);

            WinDef.HDC desktopWindow = User32.INSTANCE.GetDC(workerw);
            WinDef.HDC hdcMem = GDI32.INSTANCE.CreateCompatibleDC(desktopWindow);
            
            WinNT.HANDLE oldBitmap = GDI32.INSTANCE.SelectObject(hdcMem, image);
            GDI32.INSTANCE.BitBlt(desktopWindow, 0, 0, 64, 64, hdcMem, 0, 0, GDI32.SRCCOPY);
            GDI32.INSTANCE.SelectObject(hdcMem, oldBitmap);
            
            GDI32.INSTANCE.DeleteDC(hdcMem);
            User32.INSTANCE.ReleaseDC(workerw, desktopWindow);
            
            
            
            
            
            WinDef.RECT rect = new WinDef.RECT();
            User32.INSTANCE.GetWindowRect(workerw, rect);
            
            //GDI32.INSTANCE.BitBlt(desktopWindow, 0, 0, 500, 500, desktopWindow, 400, 400, GDI32.SRCCOPY);
            
            WinDef.HBITMAP bitmap = GDI32.INSTANCE.CreateCompatibleBitmap(desktopWindow, 800, 800);
            
            for (int i=0; i<1920 * 1; i++) {
                bitmap.getPointer().setInt(i, 210);
            }
            
            for (int i=0; i<1920; i++) {
                for (int j=0; j<1080; j++) {
                    GDI32.INSTANCE.SetPixel(desktopWindow, i, j, 100);
                }
            }
        }*/