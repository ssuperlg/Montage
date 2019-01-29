package com.montage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicPanel extends JPanel {

	private BufferedImage img;

	/**
	 * Create the panel.
	 */
	public PicPanel() {
		try {
			img = ImageIO.read(new File("d:\\target\\target.jpg"));
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
