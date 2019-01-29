package com.montage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * UI的背景图片
 *
 * @author wph
 */
public class Panel extends JPanel {

	private BufferedImage img;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

}
