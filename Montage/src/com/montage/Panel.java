package com.montage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * UI的背景图片
 * 
 * @author wph
 *
 */
public class Panel extends JPanel {
	private BufferedImage img;

	public Panel() {
		try {
			img = ImageIO.read(new File("bg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

}
