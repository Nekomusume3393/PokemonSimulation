/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author May5th
 */
public class XBgm {
    
    private static Clip bgmClip;
    private static FloatControl volumeControl;
    private static boolean isPaused = false;
    private static int pausePosition = 0;
    
    public static void play(String name) {
        stop();
        try {
            URL url = XBgm.class.getResource("/pokemon/sim/bgm/" + name);
            if (url == null) throw new IOException("Cannot find the BGM file: " + name);
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioIn);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            
            if (bgmClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) bgmClip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("[XBgm] Cannot play BGM: " + name);
            e.printStackTrace();
        }
    }
    
    public static void stop() {
        if (bgmClip != null) {
            bgmClip.stop();
            bgmClip.close();
            bgmClip = null;
            isPaused = false;
        }
    }
    
    public static void pause() {
        if (bgmClip != null && bgmClip.isRunning()) {
            pausePosition = bgmClip.getFramePosition();
            bgmClip.stop();
            isPaused = true;
        }
    }
    
    public static void resume() {
        if (bgmClip != null && isPaused) {
            bgmClip.setFramePosition(pausePosition);
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();
            isPaused = false;
        }
    }
    
    public static boolean isPlaying() {
        return bgmClip != null && bgmClip.isRunning();
    }
    
    public static void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float gain = min + (max - min) * Math.max(0f, Math.min(1f, volume));
            volumeControl.setValue(gain);
        }
    }
}
