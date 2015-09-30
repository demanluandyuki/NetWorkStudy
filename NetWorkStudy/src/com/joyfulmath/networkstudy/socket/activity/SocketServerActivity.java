package com.joyfulmath.networkstudy.socket.activity;

import com.joyfulmath.networkstudy.R;
import com.joyfulmath.networkstudy.socket.operator.impl.SocketServerImpl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SocketServerActivity extends Activity implements SocketServerImpl.ServerResult{

	Button mBtnStart = null;
	EditText mEditTransferStr = null;
	SocketServerImpl serverImpl = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket_server);
		mBtnStart = (Button) findViewById(R.id.btn_socketserver_start);
		mBtnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startServer();
			}
		});
		mEditTransferStr = (EditText) findViewById(R.id.edit_socketserver_str);
	}



	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopServer();
	}
	
	protected void startServer() {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				serverImpl = new SocketServerImpl();
				serverImpl.startServer(8080,SocketServerActivity.this);
			}
		}).start();
	}
	
	protected void stopServer()
	{
		serverImpl.stopServer();
	}



	@Override
	public void onResult(String str) {
		mEditTransferStr.setText(str);
	}
}
