package com.joyfulmath.networkstudy.socket.activity;

import com.joyfulmath.networkstudy.R;
import com.joyfulmath.networkstudy.socket.operator.impl.SocketClientImpl;
import com.joyfulmath.networkstudy.utils.NetWorkUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SocketClientActivity extends Activity {

	Button mBtnStart = null;
	Button mBtnTransfer = null;
	EditText mEditIp = null;
	EditText mEditPort = null;
	EditText mEditTransferStr = null;
	SocketClientImpl clientImpl = null;
	String ip = null;
	int port = 0;
	String mTransferStr = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_socket_client);
		mBtnStart = (Button) findViewById(R.id.btn_socketclient_start);
		mBtnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startConnect();
			}
		});
		
		mBtnTransfer = (Button) findViewById(R.id.btn_socketclient_transfer);
		mBtnTransfer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				readStringFromView();
				clientImpl.transferStreamAsync(mTransferStr);
			}
		});
		if(mEditIp == null)
		{
			mEditIp = (EditText) findViewById(R.id.edit_socketclient_ip);
			mEditPort = (EditText) findViewById(R.id.edit_socketclient_port);
			mEditTransferStr = (EditText) findViewById(R.id.edit_socketclient_str);
			mEditIp.setText("192.168.0.3");
			mEditPort.setText(String.valueOf(NetWorkUtils.PORT));
			mEditTransferStr.setText("This is a test string from client");
		}
		clientImpl = new SocketClientImpl(NetWorkUtils.SOCKET_MODE);
		clientImpl.startImpl();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeConnect();
	}
	
	private void startConnect()
	{
		readStringFromView();
		clientImpl.startConnectAsync(ip, port);

	}
	
	private void closeConnect()
	{
		if(clientImpl!=null)
		{
			clientImpl.closeAsync();
		}
	}
	
	private void readStringFromView()
	{

		try{
			ip = mEditIp.getEditableText().toString();
			
			port =Integer.parseInt(mEditPort.getEditableText().toString());
			
			mTransferStr = mEditTransferStr.getEditableText().toString();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
