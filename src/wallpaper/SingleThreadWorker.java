/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bowen
 */
public class SingleThreadWorker implements Worker {
    
    private final Wallpaper wallpaper;
    
    private ScheduledFuture updateFuture;
    private ScheduledExecutorService executorService;
    private int targetFPS;
    
    public SingleThreadWorker(Wallpaper wallpaper, ScheduledExecutorService executorService, int targetFPS) {
        this.wallpaper = wallpaper;
        this.executorService = executorService;
        this.targetFPS = targetFPS;
    }
    
    @Override
    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    @Override
    public boolean setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
        boolean isSuccessful = true;
        if (isRenderRunning()) {
            isSuccessful &= stopRender();
            isSuccessful &= startRender();
        }
        return isSuccessful;
    }

    @Override
    public boolean start() {
        return startRender();
    }

    @Override
    public boolean stop() {
        return stopRender();
    }

    @Override
    public boolean isRunning() {
        return isRenderRunning();
    }
    
    public boolean isRenderRunning() {
        if (updateFuture != null) {
            return !updateFuture.isDone();
        }
        return false;
    }
    
    public boolean startRender() {
        if (!isRenderRunning()) {
            updateFuture = executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    wallpaper.mainTick();
                    wallpaper.paintTick();
                }
            }, 0, TimeUnit.SECONDS.toNanos(1)/targetFPS, TimeUnit.NANOSECONDS);
            return true;
        }
        return false;
    }
    
    public boolean stopRender() {
        if (updateFuture != null) {
            if (updateFuture.cancel(true)) {
                updateFuture = null;
                return true;
            }
        }
        return false;
    }

    public boolean setTargetFPS(int fps) {
        targetFPS = fps;
        if (isRenderRunning()) {
            stopRender();
            return startRender();
        }
        return true;
    }

    public int getTargetFPS() {
        return targetFPS;
    }
    
    
}
