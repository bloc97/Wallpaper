/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.sound.sampled.SourceDataLine;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

/**
 *
 * @author bowen
 */
public class VideoWallpaper implements Wallpaper {
    
    private final String video;
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    
    public VideoWallpaper(String videoUrl) {
        
        boolean found = new NativeDiscovery().discover();
            
        mediaPlayerComponent = new EmbeddedMediaListPlayerComponent();
        
        video = videoUrl;
        
    }
    
    public void start() {
        mediaPlayerComponent.getMediaPlayer().setRepeat(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(video);
    }
    
    @Override
    public Component getComponent() {
        return mediaPlayerComponent;
    }
}
