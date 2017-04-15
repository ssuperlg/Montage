package com.montage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 爬虫
 */
public class Model {
	private static int i = 0 ;
	public static int getI(){
		return i ;
	}
	static int page = 1 ;
	public  static void nextPage(){
		page++ ;
	}
	static boolean UserPicGet = false ;
	public static void catchImg(String url) {
		BufferedOutputStream out = null;
		OkHttpClient client = new OkHttpClient();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements link = doc.select("[class]");
			for (Element e : link) {

				// 到索引的的第一个人的链接
				if (e.attr("class").equals("d-flex")) {
					String linkOne = e.child(0).attr("abs:href");
					// 此人的粉丝页面
					String followers = linkOne + "?page="+ page +"&tab=followers";

					Document doc2 = Jsoup.connect(followers).get();
					Elements link2 = doc2.select("[class]");
					// 粉丝节点
					for (Element e2 : link2) {
						// 用户图片链接
						if(e2.attr("class").equals("avatar width-full rounded-2") && !UserPicGet){
							String user = e2.attr("abs:src");
							out = new BufferedOutputStream(new FileOutputStream(new File("d:\\img\\src.jpg")));
							Request request1 = new Request.Builder().url(user).build();
							Response response1 = client.newCall(request1).execute();
							byte[] userData = response1.body().bytes();
							out.write(userData);
							out.flush();
							UserPicGet = true ;
						}
						if (e2.attr("class").equals("d-table-cell col-1 v-align-top")) {
							// 粉丝图片链接
							String followerImg = e2.child(0).child(0).attr("abs:src");
							Request request2 = new Request.Builder().url(followerImg).build();
							Response response2 = client.newCall(request2).execute();
							byte[] imgData = response2.body().bytes();
							// 粉丝头像图片
							out = new BufferedOutputStream(new FileOutputStream(new File("d:\\img\\" + i++ + ".jpg")));
							out.write(imgData);
							out.flush();
						}
					}
					out.close();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
