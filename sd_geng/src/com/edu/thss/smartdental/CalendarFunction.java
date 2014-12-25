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

public class CalendarFunction
{
 	private static String serverIP = "59.66.137.187";
	private static int clientPort = 8888;
	private static final int serverPort = 9888;
	private static final int bufferSize = 40960;
    public static boolean send(ScheduleElement se,String order) throws Throwable
    {
        try{
            String str;
            Socket socket = new Socket(serverIP,serverPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            str = "change";  
            out.write(str.getBytes());
            //System.out.println(str);   
            out.write(order.getBytes());
            System.out.println(order);
            String data = SeAndJsonExchanging.SEToJson(order, se);
            out.write(data.getBytes());
            //other operations       
            socket.close();
            return true;
        }
        catch(IOException e){
            System.out.println("IOException: " + e.getMessage());
            return false;
        }     
    }
}