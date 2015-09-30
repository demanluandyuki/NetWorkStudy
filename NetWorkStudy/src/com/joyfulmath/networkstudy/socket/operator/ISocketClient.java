package com.joyfulmath.networkstudy.socket.operator;

import java.io.InputStream;

public interface ISocketClient {
	void startConnect(String ip,int port);
	int writeStream(byte[] buffer);
	int writeStream(String filePath);
	int writeStream(InputStream inputStream);
	int writeStream(StringBuffer buffer);
	void disConnect();
}
