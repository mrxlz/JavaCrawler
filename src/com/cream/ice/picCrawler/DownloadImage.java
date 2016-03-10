package com.cream.ice.picCrawler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownloadImage {
	
	public static void Download(){
		Integer num=1;
		CloseableHttpClient httpClient;
		HttpGet httpGet;
		CloseableHttpResponse response = null;
		String url=null;
		
		while(PicVisitor.imgUrls.size() > 0){
			
			System.out.println("正在下载第"+num+"张图片...");
			
			url=PicVisitor.imgUrls.pollFirst();
			httpClient = HttpClients.createDefault();
			httpGet = new HttpGet(url);
			
			try {
				response = httpClient.execute(httpGet);
				HttpEntity entity=response.getEntity();
				OutputStream out=new FileOutputStream("H://images//"+num+".jpg");
				InputStream in=entity.getContent();
				byte buffer[]=new byte[1024*256];
				int i=in.read(buffer);
				while(i!=-1){
					out.write(buffer, 0, i);
					out.flush();
					i=in.read(buffer);
				}
				out.close();
				System.out.println("下载完成...");
			} catch (IOException e) {
				System.err.println("下载图片出错...");
				e.printStackTrace();
			}finally {
				try {
					if (response != null)
						response.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					try {
						if (httpClient != null)
							httpClient.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println();
			num++;
		}
		System.out.println("所有图片下载完成!");
	}
}
