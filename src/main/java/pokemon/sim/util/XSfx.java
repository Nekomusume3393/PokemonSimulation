/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author May5th
 */
public class XSfx {
    
    private static final Map<String, Clip> soundCache = new HashMap<>();
    
    public static void play(String name) {
        play(name, false);
    }
    
    public static void play(String name, boolean loop) {
        try {
            Clip clip = soundCache.get(name);
            if (clip == null || !clip.isOpen()) {
                URL soundURL = XSfx.class.getResource("/pokemon/sim/sfx/" + name);
                if (soundURL == null) throw new IOException("Cannot find the SFX file: " + name);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                soundCache.put(name, clip);
            }
            
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("[XSfx] Cannot play audio: " + name);
            e.printStackTrace();
        }
    }
    
    public static void stop(String name) {
        Clip clip = soundCache.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
    public static void stopAll() {
        for (Clip clip : soundCache.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            clip.close();
        }
        soundCache.clear();
    }
}
