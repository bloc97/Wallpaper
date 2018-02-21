/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallpaper;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author bowen
 */
public class VideoWallpaper implements Wallpaper {

    private BufferedImage image;
    private static SourceDataLine mLine;
    
    
    private JPanel panel = new JPanel(true) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                ((Graphics2D) g).scale((double)getWidth() / image.getWidth(), (double)(getHeight() + Background.MAXIMIZE_OFFSET) / image.getHeight());
                g.drawImage(image, 0, 0, this);
            }
        }
    };
    
    
    public VideoWallpaper(String videoUrl) {
        
        IMediaReader reader = ToolFactory.makeReader(videoUrl);
        reader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        
        for (int i=0, j=reader.getContainer().getNumStreams(); i<j; i++) {
            IStream stream = reader.getContainer().getStream(i);
            IStreamCoder coder = stream.getStreamCoder();
            
            if (coder.getCodecType().equals(ICodec.Type.CODEC_TYPE_AUDIO)) {
                openJavaSound(coder);
            }
        }
        
        
        
        MediaListenerAdapter adapter = new MediaListenerAdapter() {
            @Override
            public void onVideoPicture(IVideoPictureEvent event) {
                image = event.getImage();
                SwingUtilities.invokeLater(() -> {
                    panel.repaint();
                });
                try { 
                    Thread.sleep((int)reader.getContainer().getStream(event.getStreamIndex()).getFrameRate().getDouble()); 
                } catch (InterruptedException ex) { 
                    
                }
            }
            
            @Override
            public void onAudioSamples(IAudioSamplesEvent event) {
                if (mLine != null && event.getAudioSamples().getSize() >= 1) {
                    playJavaSound(event.getAudioSamples());
                }
            }
            
        };
        
        reader.addListener(adapter);
        
        new Thread(() -> {
            while (true) {
                while(reader.readPacket() == null) {
                    do {

                    } while (false);
                }
                reader.open();
            }
            //closeJavaSound();
        }).start();
        
    }
    
    @Override
    public JPanel getPanel() {
        return panel;
    }
    
    private void openJavaSound(IStreamCoder audioCoder) {
        AudioFormat audioformat = new AudioFormat(audioCoder.getSampleRate(), (int)IAudioSamples.findSampleBitDepth(audioCoder.getSampleFormat()), audioCoder.getChannels(), true, false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioformat);
        try {
            mLine = (SourceDataLine) AudioSystem.getLine(info);
            mLine.open(audioformat);
            mLine.start();
        } catch (LineUnavailableException ex) {
            throw new RuntimeException("Error opening audio line.");
        }
    }
    private void playJavaSound(IAudioSamples samples) {
        byte[] rawBytes = samples.getData().getByteArray(0, samples.getSize());
        mLine.write(rawBytes, 0, samples.getSize());
    }
    private void closeJavaSound() {
        if (mLine != null) {
            mLine.drain();
            mLine.close();
            mLine = null;
        }
    }
}
