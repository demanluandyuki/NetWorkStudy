package com.joyfulmath.networkstudy.socket.activity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;


import com.joyfulmath.networkstudy.utils.NetWorkUtils;
import com.joyfulmath.networkstudy.utils.TraceLog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UDPService extends Service {
	
	DatagramSocket socket = null;
	private ServiceThread mServiceThread;
	public boolean allowRun;
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
		initUDPService();
		
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
	public void initUDPService() {
		try {
			socket = new DatagramSocket(NetWorkUtils.PORT);
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
				try {
				
					byte[] data = new byte[4*1024];
			        //创建一个DatagramPacket对象，并指定DatagramPacket对象的大小    
			        DatagramPacket packet = new DatagramPacket(data,data.length);
			        socket.receive(packet);
			        //把客户端发送的数据转换为字符串。    
			        //使用三个参数的String方法。参数一：数据包 参数二：起始位置 参数三：数据包长    
			        String result = new String(packet.getData(),packet.getOffset() ,packet.getLength());
			        TraceLog.i(result);
			        NetWorkUtils.sendBroadcast(UDPService.this,result);
				} catch (IOException e) {
					e.printStackTrace();
					allowRun = false;
				}finally{
					
				}
			}
		}
	}
}
