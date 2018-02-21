/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

/**
 *
 * @author bowen
 */

import wallpaper.workers.Worker;
import wallpaper.workers.SingleThreadWorker;
import java.awt.Color;
import wallpaper.icons.DesktopIcons;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import wallpaper.testbackgrounds.MovingBar;

public class Main {
    
        
    public static void main(String[] args) {
        
        Background background = new Background("Bloc Active Wallpaper", false);
        
        
        //ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);
        
        //CustomWallpaper icons = new DesktopIcons();
        //Worker worker = new SingleThreadWorker(icons, executor, 60);
        
        //CustomWallpaper movingBar = new MovingBar();
        //Worker wallpaperWorker = new SingleThreadWorker(movingBar, executor, 30);
        
        VideoWallpaper video = new VideoWallpaper("test.mp4");
        
        background.setBackground(video);
        video.start();
        //background.setForeground(movingBar);
        
        //worker.start();
        //wallpaperWorker.start();
        
    }
}