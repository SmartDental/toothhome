package com.edu.thss.smartdental;

import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.db.SeAndJsonExchanging;

import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

public class CalendarFunction
{
 	private static String serverIP = "192.168.1.100";
	private static int clientPort = 8888;
	private static final int serverPort = 9888;
	private static final int bufferSize = 40960;
	public static boolean send(ScheduleElement se,String order)
	{
		Thread t = new sendThread(se,order);
		t.start();
		return true;
	}
	public static class sendThread extends Thread
	{
		private ScheduleElement se;
		private String order;
		sendThread(ScheduleElement se,String order)
		{
			this.se = se;
			this.order = order;
		}
		public void run()
    	{
        try{
            String str;
            Socket socket = new Socket(serverIP,serverPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            str = "change";  
            out.write(str.getBytes());
            String data = null;
			try {
				data = SeAndJsonExchanging.SEToJson(order, se);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            out.write(data.getBytes());
            //other operations       
            socket.close();
        }
        catch(IOException e){
            System.out.println("IOException: " + e.getMessage());
        	}     
    	}	
	}
}