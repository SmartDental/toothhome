package com.edu.thss.smartdental;

import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.db.*;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

//加到main activity的case toothhome中
public class Client 
{
	private static String serverIP = "59.66.137.187";
	private static int clientPort = 8888;
	private static final int serverPort = 9888;
	private static final int bufferSize = 40960;
    public static boolean isConnected()
    {
        	 Thread t = new connecter();
             t.start();
             return true;
            //jump to fragment  
    }

    static class connecter extends Thread{
    	private static String serverIP = "59.66.137.187";
    	private static int clientPort = 8888;
    	private static final int serverPort = 9888;
    	private static final int bufferSize = 40960;
        public void run()
        {
            try{
                Socket socket = new Socket(serverIP, serverPort);
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                String str = "first";
                out.write(str.getBytes());
    		    System.out.println(str);
                byte[] buffer = new byte[bufferSize]; 
                int count = in.read(buffer);
                String data = new String(buffer, 0, count);
                //把data存到客户端本地       
                System.out.print(data);
                socket.close();

                Thread t = new listener();
                t.start();
                //jump to fragment  
            }
            catch(IOException e){
            	System.out.println("IOException: " + e.getMessage());
            }     	
        }
    }
	static class listener extends Thread
	{
	    public void run()
	    {
	        try{
	            Socket socket = new Socket(serverIP, serverPort);
	            InputStream in = socket.getInputStream();
	            OutputStream out = socket.getOutputStream();
	            String str = "listen";
	            out.write(str.getBytes());
	            byte[] buffer = new byte[bufferSize];
	            while(true)
	            {
	                int count = in.read(buffer);
	                String order = new String(buffer, 0, count);
	                String data;
	                ScheduleElement newOne;
	                ScheduleManager manager = new ScheduleManager(null);
					switch(order)
	                {
	                case "add":
	                    count = in.read(buffer);
	                    data = new String(buffer, 0, count);
	                    newOne = SeAndJsonExchanging.JsonToSE(data);
	                    manager.addSchedule(newOne);
	                    //添加操作
	                    
	                case "mod":
	                    count = in.read(buffer);
	                    data = new String(buffer, 0, count);
	                    newOne = SeAndJsonExchanging.JsonToSE(data);
	                    manager.editSchedule(newOne);
	                    //修改操作
	                case "del":
	                    count = in.read(buffer);
	                    data = new String(buffer, 0, count);
	                    newOne = SeAndJsonExchanging.JsonToSE(data);
	                    manager.deleteSchedule(newOne.id);
	                    //删除操作
	                }
	            }
	        }
	        catch(IOException e){
	            System.out.println("IOException: " + e.getMessage());
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}