package com.joyfulmath.networkstudy.socket.operator.impl;

import com.joyfulmath.networkstudy.socket.operator.ISocketServer;
import com.joyfulmath.networkstudy.socket.operator.SocketServerEngine;

public class SocketServerImpl {

	ISocketServer server = null;
	public void startServer(int port,ServerResult resultInterface)
	{
		server = new SocketServerEngine();
		server.startServer(port);
		if(resultInterface!=null)
		{
			String str = server.result();
			resultInterface.onResult(str);
		}
	}
	
	public void stopServer()
	{
		server.stopServer();
	}
	
	public interface ServerResult{
		void onResult(String str);
	}
}
