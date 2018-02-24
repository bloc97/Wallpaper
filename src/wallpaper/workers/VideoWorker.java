/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper.workers;

import java.awt.image.BufferedImage;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;
import wallpaper.ImageWallpaper;

/**
 *
 * @author bowen
 */
public class VideoWorker implements Worker {
    
    private final String video;
    
    private DirectMediaPlayerComponent mediaPlayerComponent;
    private DirectMediaPlayer mediaPlayer;
    
    private final ImageWallpaper imageWallpaper;
    
    public VideoWorker(String videoUrl, ImageWallpaper imageWallpaper) {
        boolean found = new NativeDiscovery().discover();
        video = videoUrl;
        this.imageWallpaper = imageWallpaper;
    }

    private class RenderCallbackAdapterEx extends RenderCallbackAdapter {
        private RenderCallbackAdapterEx() {
            super(new int[imageWallpaper.getPanel().getWidth() * imageWallpaper.getPanel().getHeight()]);
        }
        
        @Override
        protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
            
            // Simply copy buffer to the image and repaint
            int width = mediaPlayer.getVideoDimension().width;
            int height = mediaPlayer.getVideoDimension().height;
            
            if (imageWallpaper.getImage() == null || height != imageWallpaper.getImage().getHeight() || width != imageWallpaper.getImage().getWidth()) {
                imageWallpaper.setImage(new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR));
            }
            
            imageWallpaper.getImage().setRGB(0, 0, width, height, rgbBuffer, 0, width);
            
            imageWallpaper.paintTick();
        }
    }
    

    @Override
    public boolean start() {
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
        return isRunning();
    }

    @Override
    public boolean stop() {
        mediaPlayer.stop();
        return !isRunning();
    }

    @Override
    public boolean isRunning() {
        return mediaPlayer.isPlaying();
    }
    
}
