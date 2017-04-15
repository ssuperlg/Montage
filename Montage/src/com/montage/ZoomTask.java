package com.montage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class ZoomTask implements Runnable {
	private BufferedImage[] bufImg;
	private String aimDir;
	private int width;
	private int num;
	private String srcdir;

	public ZoomTask(String srcdir, String aimDir, int width, int num) {
		this.srcdir = srcdir;
		this.aimDir = aimDir;
		this.width = width;
		this.num = num;
	}

	@Override
	public void run() {
		bufImg = new BufferedImage[num];
		OutputStream out = null;
		try {
			for (int i = 0; i < num; i++) {
				bufImg[i] = ImageIO.read(new File(srcdir + "\\" + i + ".jpg"));
				// 获取一个宽、长是原来scale的图像实例
				Image image = bufImg[i].getScaledInstance(width, width, Image.SCALE_DEFAULT);

				// 缩放图像
				BufferedImage tag = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);

				// 绘制缩小后的图
				Graphics2D g = tag.createGraphics();
				g.drawImage(image, 0, 0, null);
				g.dispose();

				// 输出
				out = new FileOutputStream(aimDir + "\\" + i + ".jpg");
				ImageIO.write(tag, "JPG", out);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
