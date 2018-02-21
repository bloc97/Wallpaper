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

public class Main {
    
        
    public static void main(String[] args) {
        
        
        Background background = new Background("Bloc Active Wallpaper", true);
        
        
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        
        Wallpaper wallpaper = new DesktopIcons();
        Worker worker = new SingleThreadWorker(wallpaper, executor, 30);
        
        background.setForeground(wallpaper);
        worker.start();
        
        
    }
}