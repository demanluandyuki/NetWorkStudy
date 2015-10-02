package com.joyfulmath.networkstudy.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


public class NetWorkUtils {
	
	public final static int PORT = 32345;
	public final static String ACTION_SERVER_SEND_CONTENT = "action.server.send.content";
	public final static String SOCKET_MODE_1 = "TCP";
	public final static String SOCKET_MODE_2 = "UDP";
	public final static String SOCKET_MODE = SOCKET_MODE_2;
	/**
     *判断wifi是否连接 
     * @param context
     * @return 
     */
    public static boolean isWiFiConnected(Context context){
        ConnectivityManager connectManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(networkInfo.isConnected()){
            return true;
        }
        else{
            return false;
        }
 
    }
 
    /**
     * 得到wifi连接的IP地址
     * @param context
     * @return
     */
    public static String getWifiIP(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddr = wifiInfo.getIpAddress();
        String ipStr =  int2string(ipAddr);
        TraceLog.i(ipStr);
        return ipStr;
    }
 
    /**
     * 输入int 得到String类型的ip地址
     * @param i
     * @return
     */
    private static String int2string(int i){
 
        return (i & 0xFF)+ "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"."+((i >> 24 ) & 0xFF );
 
    }
    
    public static  InputStream readFromFile(String filePath) {
		try {
			InputStream inputstream = new FileInputStream(filePath);
			return inputstream;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
    
	public static void sendBroadcast(Context context,String s)
	{
		Intent intent = new Intent(NetWorkUtils.ACTION_SERVER_SEND_CONTENT);
		intent.putExtra("content", s);
		context.sendBroadcast(intent);
	}
}
