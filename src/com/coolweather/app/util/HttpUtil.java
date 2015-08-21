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
				HttpURLConnection connection = null;//�˴��޷�ֱ��new��һ��URLConnection��ʵ������ΪURLConnectionΪ������
				try{
					URL url = new URL(address);//HttpURLConnection�̳���URLConnection,  HttpClient�������Ϊ��ǿ���HttpURLConnection
					//URLConnection��HttPURLConnection���ǳ����࣬�޷�ֱ��ʵ���������������Ҫͨ��URL��openconnection������á�
					connection = (HttpURLConnection)url.openConnection();//URL�����õ�Զ�̶�������ӣ��˴�URLConneciton����ת��ΪHttpURLConnection��
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();//InputStreamΪ�ֽ�����������࣬InputStreamReaderΪ�ַ���
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						response.append(line);
					}
					if(listener != null){
						//�ص�onFinish����
						listener.onFinish(response.toString());
					}
				}catch(Exception e){
					if(listener != null){
						//�ص�onError��������
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


