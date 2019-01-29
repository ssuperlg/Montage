package com.montage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫
 */
public class Model {

	private static int i = 0;

	public static int getI() {
		return i;
	}

	static int page = 1;

	public static void nextPage() {
		page++;
	}

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	static boolean UserPicGet = false;

	public static void catchImg(String url) {
		OkHttpClient client = new OkHttpClient();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements user = doc.select("a[href]");
			for (Element e : user) {
				// 第一个用户方框
				if (e.attr("class").equals("d-table") && e.attr("data-hovercard-type").equals("user")) {
					String userName = e.attr("href");
					String userFanLink = "https://github.com" + userName + "?tab=followers";

					// fans列表页面
					Document fanDoc = Jsoup.connect(userFanLink).get();
					Elements fan = fanDoc.select("a[href]");
					for (Element element : fan) {
						// 用户大头像
						if (element.attr("itemprop").equals("image") && element.attr("class")
										.equals("u-photo d-block position-relative")) {
							executorService.execute(()->{
								try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("d:\\img\\src.jpg")))){
									String userImgUrl = element.attr("href");
									Request request1 = new Request.Builder().url(userImgUrl).build();
									Response response1 = client.newCall(request1).execute();
									byte[] userData = response1.body().bytes();
									out.write(userData);
									out.flush();
									UserPicGet = true;
								}catch (Exception e1){
									e1.printStackTrace();
								}
							});
						}

						// fans小头像
						if (element.attr("class").equals("d-inline-block") && element
										.attr("data-hovercard-type").equals("user")) {
							Element fansElement = element.child(0);
							String fansImgUrl = fansElement.attr("src");
							Request request2 = new Request.Builder().url(fansImgUrl).build();
							Response response2 = client.newCall(request2).execute();
								executorService.execute(()->{
									try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("d:\\img\\" + i++ + ".jpg"))){
									byte[] imgData = response2.body().bytes();
									// 粉丝头像图片
									out.write(imgData);
									out.flush();
									}catch (Exception e2){
										e2.printStackTrace();
									}
								});
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
