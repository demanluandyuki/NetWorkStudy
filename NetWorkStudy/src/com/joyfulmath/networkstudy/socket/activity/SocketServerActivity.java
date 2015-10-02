package com.joyfulmath.networkstudy.socket.activity;

import com.joyfulmath.networkstudy.R;
import com.joyfulmath.networkstudy.utils.NetWorkUtils;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SocketServerActivity extends Activity{

	Button mBtnStart = null;
	Button mBtnStop = null;
	EditText mEditTransferStr = null;
	ServiceReceiver mReceiver = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket_server);
		mBtnStart = (Button) findViewById(R.id.btn_socketserver_start);
		mBtnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(NetWorkUtils.isWiFiConnected(getApplicationContext()))
				{
					NetWorkUtils.getWifiIP(getApplicationContext());
					startServer();
				}
			}


		});
		mBtnStop = (Button) findViewById(R.id.btn_socketserver_stop);
		mBtnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopService(new Intent(SocketServerActivity.this, TCPService.class));
			}
		});
		mEditTransferStr = (EditText) findViewById(R.id.edit_socketserver_str);
	}
	
	private void startServer() {
		if(NetWorkUtils.SOCKET_MODE.equals(NetWorkUtils.SOCKET_MODE_1))
		{
			startService(new Intent(SocketServerActivity.this, TCPService.class));
		}
		else
		{
			
		}startService(new Intent(SocketServerActivity.this, UDPService.class));
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		IntentFilter filter = new IntentFilter(NetWorkUtils.ACTION_SERVER_SEND_CONTENT); 
		mReceiver = new ServiceReceiver();
		registerReceiver(mReceiver, filter);
	}



	@Override
	protected void onStop() {
		super.onStop();
		if(mReceiver!=null)
		{
			unregisterReceiver(mReceiver);
		}

	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	public class ServiceReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			String s = intent.getStringExtra("content");
			mEditTransferStr.setText(s);
		}
		
	}
}
