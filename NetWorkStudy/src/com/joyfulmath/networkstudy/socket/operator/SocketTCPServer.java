package com.joyfulmath.networkstudy.socket.operator;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCPServer implements ISocketServer {

	ServerSocket serverSocket = null;
	String result = null;
	@Override
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			InputStream inputStream = socket.getInputStream();
			byte buffer[] = new byte[1024*4];
			int temp = 0;
			StringBuilder builder = new StringBuilder();
			while ((temp = inputStream.read(buffer)) != -1) {  
//	            System.out.println(new String(buffer, 0, temp));  
				builder.append(new String(buffer, 0, temp));
	        }
			result = builder.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void stopServer() {
		if(serverSocket!=null)
		{
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String result() {
		// TODO Auto-generated method stub
		return result;
	}

}
