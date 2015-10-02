package com.joyfulmath.networkstudy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;


public class NetWorkUtils {
	
	public final static int PORT = 32345;
	public final static String ACTION_SERVER_SEND_CONTENT = "action.server.send.content";
	/**
     *�ж�wifi�Ƿ����� 
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
     * �õ�wifi���ӵ�IP��ַ
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
     * ����int �õ�String���͵�ip��ַ
     * @param i
     * @return
     */
    private static String int2string(int i){
 
        return (i & 0xFF)+ "." + ((i >> 8 ) & 0xFF) + "." + ((i >> 16 ) & 0xFF) +"."+((i >> 24 ) & 0xFF );
 
    }
}
