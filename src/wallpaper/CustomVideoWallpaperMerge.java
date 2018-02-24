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
public class CustomVideoWallpaperMerge extends CustomWallpaper {
    
    private final String video;
    
    private DirectMediaPlayerComponent mediaPlayerComponent;
    private DirectMediaPlayer mediaPlayer;
    
    private volatile BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR);
    
    private double scale = 1;
    
    private int c = 0;
    
    /*
    private final JPanel panel = new JPanel() {
        
        
        @Override
        public void paintComponent(Graphics g) {
        }
    };*/
    
    public CustomVideoWallpaperMerge(String videoUrl) {
        
        boolean found = new NativeDiscovery().discover();
        getPanel().setBackground(Color.BLACK);
        video = videoUrl;
        
    }

    @Override
    public void onPaint(Graphics g, PreciseTime dt) {
        
        double xTrans = (getPanel().getWidth() - image.getWidth() * scale) / scale / 2;
        ((Graphics2D) g).scale(scale, scale);
        g.drawImage(image, (int)xTrans, 0, getPanel());
    }

    private class RenderCallbackAdapterEx extends RenderCallbackAdapter {
        private RenderCallbackAdapterEx() {
            super(new int[getPanel().getWidth() * getPanel().getHeight()]);
        }
        
        @Override
        protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
            // Simply copy buffer to the image and repaint
            
            int height = mediaPlayer.getVideoDimension().height;
            int width = mediaPlayer.getVideoDimension().width;
            
            if (height != image.getHeight() || width != image.getWidth()) {
                image = new BufferedImage((int)mediaPlayer.getVideoDimension().getWidth(), (int)mediaPlayer.getVideoDimension().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
                
                if (height != getPanel().getHeight() || width != getPanel().getWidth()) {
                    scale = Math.min((double)(getPanel().getHeight() + Background.MAXIMIZE_OFFSET) / height, (double)getPanel().getWidth()/ width);
                }
            }
            
            image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbBuffer, 0, image.getWidth());
            
            paintTick();
        }
    }
    
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
        return mediaPlayer.isPlaying();
    }
    
}
