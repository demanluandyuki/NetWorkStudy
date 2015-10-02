package com.joyfulmath.networkstudy.socket.operator.impl;

import com.joyfulmath.networkstudy.socket.operator.ISocketClient;
import com.joyfulmath.networkstudy.socket.operator.SocketClientEngine;
import com.joyfulmath.networkstudy.utils.TraceLog;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

public class SocketClientImpl extends HandlerThread{
	public final static int MSG_SOCKET_IDLE = 100;
	public final static int MSG_SOCKET_CONNECT = 101;
	public final static int MSG_SOCKET_TRANSFER = 102;
	public final static int MSG_SOCKET_CLOSE = 103;
	
	Handler mHandler = null;
	ISocketClient client = null;
	
	public SocketClientImpl(String name) {
		super(name);
	}
	
	public void startImpl()
	{
		start();
	}
	
	public void startConnectAsync(String ip,int port)
	{
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_SOCKET_CONNECT;
		Bundle data = new Bundle();
		data.putString("ip", ip);
		data.putInt("port", port);
		msg.setData(data);
		mHandler.sendMessage(msg);
	}
	
	public void transferStreamAsync(String word)
	{
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_SOCKET_TRANSFER;
		Bundle data = new Bundle();
		data.putString("word", word);
		msg.setData(data);
		mHandler.sendMessage(msg);
	}
	
	public void closeAsync()
	{
		Message msg = mHandler.obtainMessage();
		msg.what = MSG_SOCKET_CLOSE;
		mHandler.sendMessage(msg);
	}
	
	@Override
	protected void onLooperPrepared() {
		super.onLooperPrepared();
		mHandler = new Handler(getLooper()){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what)
				{
				case MSG_SOCKET_CONNECT:
					Bundle data = msg.getData();
					String ip = data.getString("ip");
					int port = data.getInt("port");
					startConnect(ip,port);
					break;
				case MSG_SOCKET_TRANSFER:
					Bundle data2 = msg.getData();
					String word = data2.getString("word");
					transferStream(word);
					break;
				case MSG_SOCKET_CLOSE:
					close();
					break;
				}
			}
			
		};
	}


	void startConnect(String ip,int port)
	{
		TraceLog.i(ip+":"+port);
		client = new SocketClientEngine();
		client.startConnect(ip, port);
	}
	
	void transferStream(String word)
	{
		TraceLog.i(word);
		client.writeStream(new StringBuffer(word));
	}
	
	void close()
	{
		TraceLog.i();
		client.disConnect();
	}
}
