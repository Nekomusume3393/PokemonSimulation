/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.util;

import pokemon.sim.util.XBgm;
import pokemon.sim.util.XSfx;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author May5th
 */
public class TestXSound extends JFrame {
    
    public TestXSound() {
        setTitle("Test XSfx & XBgm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton btnPlaySfx = new JButton("Play SFX");
        JButton btnPlaySfxFail = new JButton("Play SFX (fail)");
        JButton btnPlayBgm = new JButton("Play BGM");
        JButton btnPauseBgm = new JButton("Pause BGM");
        JButton btnResumeBgm = new JButton("Resume BGM");
        JButton btnStopBgm = new JButton("Stop BGM");

        JSlider sliderVolume = new JSlider(0, 100, 100);
        sliderVolume.setPreferredSize(new Dimension(150, 40));
        JLabel lblVolume = new JLabel("Volume");

        // Event binding
        btnPlaySfx.addActionListener(e -> XSfx.play("click.wav")); // Exist
        btnPlaySfxFail.addActionListener(e -> XSfx.play("nope.wav")); // Doesn't exist

        btnPlayBgm.addActionListener(e -> XBgm.play("bgm.wav")); // Exist
        btnPauseBgm.addActionListener(e -> XBgm.pause());
        btnResumeBgm.addActionListener(e -> XBgm.resume());
        btnStopBgm.addActionListener(e -> XBgm.stop());

        sliderVolume.addChangeListener(e -> {
            float vol = sliderVolume.getValue() / 100f;
            XBgm.setVolume(vol);
        });

        add(btnPlaySfx);
        add(btnPlaySfxFail);
        add(btnPlayBgm);
        add(btnPauseBgm);
        add(btnResumeBgm);
        add(btnStopBgm);
        add(lblVolume);
        add(sliderVolume);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestXSound::new);
    }
}
