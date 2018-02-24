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
import wallpaper.workers.VideoWorker;

public class Main {
    
        
    public static void main(String[] args) {
        Background background = new Background("Bloc Active Wallpaper", false);
        
        boolean flip = true;
        
        if (flip) {
        
            ImageWallpaper animatedBackground = new ImageWallpaper();
            VideoWorker worker = new VideoWorker("01 - La planete Omega.avi", animatedBackground);
            background.setBackground(animatedBackground);
            worker.start();
        
        } else {

            CustomVideoWallpaperMerge video = new CustomVideoWallpaperMerge("01 - La planete Omega.avi");
            background.setBackground(video);
            video.start();
        
        }
        
        
    }
}