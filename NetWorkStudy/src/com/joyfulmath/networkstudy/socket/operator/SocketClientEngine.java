package com.joyfulmath.networkstudy.socket.operator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.joyfulmath.networkstudy.utils.TraceLog;

public class SocketClientEngine implements ISocketClient {

	Socket socket = null;
	OutputStream outputStream = null;

	@Override
	public void startConnect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			outputStream = socket.getOutputStream();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int writeStream(byte[] buffer) {
		int result = -2;
		try {
			if (outputStream != null) {
				outputStream.write(buffer);
				outputStream.flush();
				TraceLog.i(String.valueOf(buffer));
				return 0;
			}
		} catch (IOException e) {
			result = -1;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int writeStream(String filePath) {
		InputStream inputStream = readFromFile(filePath);
		return writeStream(inputStream);
	}

	@Override
	public int writeStream(InputStream inputStream) {
		int result = 0;
		byte buffer[] = new byte[4 * 1024];
		int temp = 0;

		if (outputStream == null) {
			return -2;
		}

		if (inputStream == null) {
			return -3;
		}
		// 循环读取文件
		try {
			while ((temp = inputStream.read(buffer)) != -1) {
				// 把数据写入到OuputStream对象中
				outputStream.write(buffer, 0, temp);
				outputStream.flush();
			}
		} catch (IOException e) {
			result = -1;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int writeStream(StringBuffer buffer) {
		return writeStream(buffer.toString().getBytes());
	}

	@Override
	public void disConnect() {
		if(socket!=null)
		{
			try {
				socket.shutdownOutput();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private InputStream readFromFile(String filePath) {
		try {
			InputStream inputstream = new FileInputStream(filePath);
			return inputstream;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
