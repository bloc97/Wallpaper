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
public class DualThreadWorker implements Worker {
    
    private final Wallpaper wallpaper;
    
    private ScheduledFuture tickFuture;
    private ScheduledFuture updateFuture;
    private ScheduledExecutorService executorService;
    private int targetUPS;
    private int targetFPS;
    
    public DualThreadWorker(Wallpaper wallpaper, ScheduledExecutorService executorService, int targetUPS, int targetFPS) {
        this.wallpaper = wallpaper;
        this.executorService = executorService;
        this.targetUPS = targetUPS;
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
        if (isTickRunning()) {
            isSuccessful &= stopTick();
            isSuccessful &= startTick();
        }
        if (isRenderRunning()) {
            isSuccessful &= stopRender();
            isSuccessful &= startRender();
        }
        return isSuccessful;
    }

    @Override
    public boolean start() {
        boolean isSuccessful = true;
        isSuccessful &= startTick();
        isSuccessful &= startRender();
        if (!isSuccessful) {
            stop();
        }
        return isSuccessful;
    }

    @Override
    public boolean stop() {
        boolean isSuccessful = true;
        isSuccessful &= stopTick();
        isSuccessful &= stopRender();
        return isSuccessful;
    }

    @Override
    public boolean isRunning() {
        return isRenderRunning() || isTickRunning();
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
                    wallpaper.paintTick();
                }
            }, TimeUnit.SECONDS.toNanos(1)/targetUPS / 4, TimeUnit.SECONDS.toNanos(1)/targetFPS, TimeUnit.NANOSECONDS);
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

    public int getTargetFPS() {
        return targetFPS;
    }

    public boolean setTargetFPS(int fps) {
        targetFPS = fps;
        if (isRenderRunning()) {
            stopRender();
            return startRender();
        }
        return true;
    }
    
    
    public boolean isTickRunning() {
        if (tickFuture != null) {
            return !tickFuture.isDone();
        }
        return false;
    }
    
    public boolean startTick() {
        if (!isTickRunning()) {
            tickFuture = executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    wallpaper.mainTick();
                }
            }, 0, TimeUnit.SECONDS.toNanos(1)/targetUPS, TimeUnit.NANOSECONDS);
            return true;
        }
        return false;
    }
    
    public boolean stopTick() {
        if (tickFuture != null) {
            if (tickFuture.cancel(true)) {
                tickFuture = null;
                return true;
            }
        }
        return false;
    }

    public int getTargetUPS() {
        return targetUPS;
    }

    public boolean setTargetUPS(int ups) {
        targetUPS = ups;
        if (isTickRunning()) {
            stopTick();
            return startTick();
        }
        return true;
    }
}
