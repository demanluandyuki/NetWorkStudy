package com.joyfulmath.networkstudy.socket.operator.impl;

import com.joyfulmath.networkstudy.socket.operator.ISocketClient;
import com.joyfulmath.networkstudy.socket.operator.SocketTCPClient;
import com.joyfulmath.networkstudy.socket.operator.SocketUDPClient;
import com.joyfulmath.networkstudy.utils.NetWorkUtils;

public class SocketClientFactory {
	
	public static ISocketClient create(String className)
	{
		ISocketClient client = null;
		if(className.equals(NetWorkUtils.SOCKET_MODE_1))
		{
			client = new SocketTCPClient();
		}
		else if(className.equals(NetWorkUtils.SOCKET_MODE_2))
		{
			client = new SocketUDPClient();
		}
		
		return client;
	}
}
