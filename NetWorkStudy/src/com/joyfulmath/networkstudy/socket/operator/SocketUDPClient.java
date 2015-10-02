package com.joyfulmath.networkstudy.socket.operator;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.joyfulmath.networkstudy.utils.NetWorkUtils;

public class SocketUDPClient implements ISocketClient {

	DatagramSocket socket = null;
	String ip = null;
	@Override
	public void startConnect(String ip, int port) {
		try {
			socket = new DatagramSocket(port);
			this.ip = ip;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int writeStream(byte[] buffer) {
		int result = -1;
		InetAddress serverAddress;
		try {
			serverAddress = InetAddress.getByName(ip);
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length ,serverAddress ,NetWorkUtils.PORT);
			socket.send(packet);
			result = 0;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return result;
	}

	@Override
	public int writeStream(String filePath) {
		InputStream inputStream = NetWorkUtils.readFromFile(filePath);
		return writeStream(inputStream);
	}

	@Override
	public int writeStream(InputStream inputStream) {
		int result = -1;
		try {
			byte[] data = NetWorkUtils.toByteArray(inputStream);
			result = writeStream(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}
		return result;
	}

	@Override
	public int writeStream(StringBuffer buffer) {
		return writeStream(buffer.toString().getBytes());
	}

	@Override
	public void disConnect() {
		// TODO Auto-generated method stub

	}

}
