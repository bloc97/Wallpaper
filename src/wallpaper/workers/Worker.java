/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.workers;

import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author bowen
 */
public interface Worker {
    public boolean start();
    public boolean stop();
    public boolean isRunning();
}
