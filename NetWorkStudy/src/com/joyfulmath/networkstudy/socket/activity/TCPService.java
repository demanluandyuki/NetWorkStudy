package com.joyfulmath.networkstudy.socket.activity;


import java.io.IOException;
import java.io.InputStream;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.joyfulmath.networkstudy.utils.NetWorkUtils;
import com.joyfulmath.networkstudy.utils.TraceLog;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class TCPService extends Service {

	private ServerSocket mServerSocket;
	public boolean allowRun;
	public Socket client;
	public byte[] mByteBuffer = new byte[10*1024];
	private ServiceThread mServiceThread;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		allowRun = true;
		initTCPService();
	}
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(mServiceThread!=null)
		{
			mServiceThread.start();
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		allowRun = false;
	}

	/**
	 * 初始化ServerSocket，buffer分配内存
	 */
	public void initTCPService() {
		try {
			mServerSocket = new ServerSocket();
			mServerSocket.setReuseAddress(true);
			mServerSocket.bind(new InetSocketAddress(NetWorkUtils.PORT));
			TraceLog.i("Service启动监听...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mServiceThread == null) {
			mServiceThread = new ServiceThread();
		}
	}

	public class ServiceThread extends Thread {

		@Override
		public void run() {
			while (allowRun) {
				Socket connection = null;
				try {
					connection = mServerSocket.accept();
					TraceLog.i("Service监听到消息...");
					InputStream inputStream = connection.getInputStream();
//					BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//					String content = ""; 
//					if((content = in.readLine())!=null)
//					{
//						TraceLog.i(content);
//					}
					int temp = 0;
					while ((temp = inputStream.read(mByteBuffer)) != -1) {
	
						String s = new String(mByteBuffer, 0, temp);
						TraceLog.i("temp = " + temp + " = " + s);
//						notifyStr("temp = " + temp + " = " + s);
						inputStream.close();
						if (connection.isConnected()) {
							connection.close();
						}
						TraceLog.i("服务器:收到客户端的下载列表 = " + s);
						NetWorkUtils.sendBroadcast(TCPService.this,s);
					}
				} catch (IOException e) {
					e.printStackTrace();
					allowRun = false;
				}finally{
					if(connection!=null)
					{
						try {
							connection.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						connection = null;
					}
				}
			}
		}
	}
	
	public void notifyStr(String str)
	{
		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Notification.Builder builder = new Builder(this);
		builder.setContentTitle(str);
		manager.notify(0x111, builder.build());
	}
	

}
