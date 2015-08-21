package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				HttpURLConnection connection = null;//此处无法直接new出一个URLConnection的实例，因为URLConnection为抽象类
				try{
					URL url = new URL(address);//HttpURLConnection继承自URLConnection,  HttpClient可以理解为增强版的HttpURLConnection
					//URLConnection与HttPURLConnection都是抽象类，无法直接实例化对象。其对象主要通过URL的openconnection方法获得。
					connection = (HttpURLConnection)url.openConnection();//URL所引用的远程对象的连接，此处URLConneciton向下转型为HttpURLConnection类
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();//InputStream为字节流，抽象基类，InputStreamReader为字符流
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						response.append(line);
					}
					if(listener != null){
						//回调onFinish方法
						listener.onFinish(response.toString());
					}
				}catch(Exception e){
					if(listener != null){
						//回调onError（）方法
						listener.onError(e);
					}
				}finally{
						if(connection != null){
							connection.disconnect();
						}
					}
				}
			}).start();
		}
	}


