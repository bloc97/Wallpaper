/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import com.sun.jna.Pointer;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.GDI32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;
import java.awt.Component;
import jna.Graphics;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

/**
 *
 * @author bowen
 */
public class VideoWallpaper implements Wallpaper {
    
    private WinDef.HWND workerw = null;
    
    private final String video;
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    public VideoWallpaper(String videoUrl) {
        
        boolean found = new NativeDiscovery().discover();
            
        mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();
        
        video = videoUrl;
        /*
        WinDef.HWND desktop = User32.INSTANCE.FindWindow("test", "test");
        WinDef.HDC desktopWindow = User32.INSTANCE.GetDC(desktop);
        for (int i=0; i<1920; i++) {
            for (int j=0; j<1080; j++) {
                GDI32.INSTANCE.SetPixel(desktopWindow, i, j, 155);
            }
        }
        */
        
        
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
            
            WinDef.HDC desktopWindow = User32.INSTANCE.GetDC(workerw);
            Graphics g = Graphics.INSTANCE.FromHdc(desktopWindow.getPointer());
            
            /*
            WinNT.HANDLE image = User32.INSTANCE.LoadImage(null, "c:\\test.bmp", User32.IMAGE_BITMAP, 0, 0, User32.LR_LOADFROMFILE);
            
            WinDef.HDC desktopWindow = User32.INSTANCE.GetDC(workerw);
            WinDef.HDC hdcMem = GDI32.INSTANCE.CreateCompatibleDC(desktopWindow);
            
            WinNT.HANDLE oldBitmap = GDI32.INSTANCE.SelectObject(hdcMem, image);
            GDI32.INSTANCE.BitBlt(desktopWindow, 0, 0, 1000, 1000, hdcMem, 0, 0, GDI32.SRCCOPY);
            GDI32.INSTANCE.SelectObject(hdcMem, oldBitmap);
            
            GDI32.INSTANCE.DeleteDC(hdcMem);
            */
            
            
            User32.INSTANCE.ReleaseDC(workerw, desktopWindow);
            
            
            
            
            /*
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
            }*/
        }
        
    }
    
    public void start() {
        mediaPlayerComponent.getMediaPlayer().setRepeat(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(video);
        mediaPlayerComponent.getMediaPlayer().mute();
        mediaPlayerComponent.getMediaPlayer().setPlaySubItems(true);
    }
    
    @Override
    public Component getComponent() {
        return mediaPlayerComponent;
    }
}
