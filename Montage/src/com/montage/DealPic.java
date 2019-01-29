package com.montage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 处理图片
 *
 * @author ssuperlg
 */
public class DealPic {

	private ZoomPic zoom;

	/**
	 * 模版图片
	 */
	private BufferedImage modelImg;

	/**
	 * 大图片
	 */
	private BufferedImage BigImg;

	/**
	 * 小图片数组
	 */
	private BufferedImage[] smallImg;

	private static int smallSize = 40;
	private int modelSize = 200;
	private String aimDir;
	private int num;
	private ZoomTask task;

	/**
	 * 构造方法
	 *
	 * @param srcDir 存放爬取的图片的路径
	 * @param aimDir 存放处理图片的路径
	 */
	public DealPic(String srcDir, String aimDir, int num) {
		this.num = num;
		this.aimDir = aimDir;
		//		task = new ZoomTask(srcDir, aimDir, smallSize, num);
		zoom = new ZoomPic(srcDir, aimDir, smallSize, num);
		smallImg = new BufferedImage[num];
		try {
			modelImg = ImageIO.read(new File(srcDir + "\\" + "src.jpg"));
			int bigSize = smallSize * modelSize;
			BigImg = new BufferedImage(bigSize, bigSize, BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行方法，得到处理后的大图片
	 */
	public void run() {
		try {

			//			 缩放小图片
			zoom.zoom();

			//			ExecutorService pool = Executors.newCachedThreadPool();
			//			pool.submit(task);
			//			pool.shutdown();

			BufferedImage image = convertImageTo(modelImg, modelSize, modelSize);
			int[][] arr = getImageMatrix(image);
			for (int i = 0; i < num; i++) {
				System.out.println(i);
				smallImg[i] = ImageIO.read(new File(aimDir + "\\" + i + ".jpg"));
			}
			BufferedImage[] newsmall = sortPic(smallImg);
			for (int j = 0; j < arr.length; j++) {
				for (int i = 0; i < arr[0].length; i++) {
					int index = arr[j][i] * newsmall.length / 255;
					index = index >= newsmall.length - 1 ? newsmall.length - 1 : index;
					importImg(newsmall[index], BigImg, i, j);
				}
			}
			File outFile = new File(aimDir + "\\" + "target" + ".jpg");
			ImageIO.write(BigImg, "jpg", outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 按灰度值排序小图片(冒泡排序 小-->大)
	 *
	 * @param img 小图片数组
	 * @return 排序后的图片数组
	 */
	public BufferedImage[] sortPic(BufferedImage[] img) {
		BufferedImage bufimg = null;
		for (int i = 0; i < img.length - 1; i++) {
			for (int j = 0; j < img.length - i - 1; j++) {
				if (getValue(img[j]) > getValue(img[j + 1])) {
					bufimg = img[j];
					img[j] = img[j + 1];
					img[j + 1] = bufimg;
				}
			}
		}
		return img;
	}

	/**
	 * 把小图片填充到大图片
	 *
	 * @param bufimg 小图片
	 * @param aimImg 大图片
	 * @param i      填充小图片的x坐标
	 * @param j      填充小图片的y坐标
	 * @throws IOException
	 */
	public void importImg(BufferedImage bufimg, BufferedImage aimImg, int i, int j)
					throws IOException {

		int width = bufimg.getWidth();
		int height = bufimg.getHeight();
		int[] ImageArray = new int[width * height];
		ImageArray = bufimg.getRGB(0, 0, width, height, ImageArray, 0, width);

		aimImg.setRGB(i * smallSize, j * smallSize, smallSize, smallSize, ImageArray, 0, width);
	}

	/**
	 * 计算平均灰度值
	 *
	 * @param img 待求灰度值的图片
	 * @return 灰度值
	 */
	public int getValue(BufferedImage img) {
		int rgbResult = 0;
		int w = img.getWidth();
		int h = img.getHeight();
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int pixel = img.getRGB(i, j);
				int r = (pixel & 0xff0000) >> 16;
				int g = (pixel & 0xff00) >> 8;
				int b = (pixel & 0xff);
				rgbResult += r + g + b / 3;
			}
		}
		rgbResult = rgbResult / w / h;
		return rgbResult;
	}

	/**
	 * 求一张图片每个点的灰度值
	 *
	 * @param img 该图片
	 * @return 灰度值组成的二维数组
	 */
	public int[][] getImageMatrix(BufferedImage img) {
		int w = img.getWidth();
		int h = img.getHeight();
		int result[][] = new int[h][w];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int pixel = img.getRGB(i, j);
				// 下面三行代码将一个数字转换为RGB数字
				int r = (pixel & 0xff0000) >> 16;
				int g = (pixel & 0xff00) >> 8;
				int b = (pixel & 0xff);
				int rgbResult = (int) (0.11 * r + 0.59 * g + 0.3 * b);
				result[j][i] = rgbResult;
			}
		}
		return result;
	}

	/**
	 * 改变图片的规格
	 *
	 * @param img 待改的图片
	 * @param w   改成的宽
	 * @param h   改成的高
	 * @return 改变大小后的图片
	 */
	public BufferedImage convertImageTo(BufferedImage img, int w, int h) {
		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(img, 0, 0, w, h, null);
		return result;
	}

}
