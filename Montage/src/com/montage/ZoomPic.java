package com.montage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 缩放图片
 *
 * @author wph
 */
public class ZoomPic {

	/**
	 * 读入图像数组
	 */
	private BufferedImage[] bufImg;
	private String aimDir;
	private int width;
	private int num;

	/**
	 * 读入关注人的头像
	 *
	 * @param dir 存放关注人头像的路径
	 */
	public ZoomPic(String dir, String aimDir, int width, int num) {
		this.aimDir = aimDir;
		this.width = width;
		this.num = num;
		try {
			bufImg = new BufferedImage[num];
			for (int i = 0; i < num; i++) {
				bufImg[i] = ImageIO.read(new File(dir + "\\" + i + ".jpg"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转换图片大小
	 *
	 * @param dir 目标路径
	 */
	public void zoom() {

		try {
			OutputStream out = null;
			for (int i = 0; i < num; i++) {
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
