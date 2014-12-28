package com.edu.thss.smartdental;

import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.db.*;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.json.JSONException;

//加到main activity的case toothhome中
public class Client 
{
	private static String serverIP = "59.66.137.106";
	private static int clientPort = 8888;
	private static final int serverPort = 8888;
	private static final int bufferSize = 40960;
	private static Context context = null;
	public Client(Context context)
	{
		this.context = context;
	}
    public static boolean isConnected()
    {
        	 Thread t = new connecter();
             t.start();
             return true;
            //jump to fragment  
    }

    static class connecter extends Thread{
    	private static String serverIP = "59.66.137.106";
    	private static int clientPort = 8888;
    	private static final int serverPort = 8888;
    	private static final int bufferSize = 40960;
    	private final String DATABASE_PATH = android.os.Environment
    			.getExternalStorageDirectory().getAbsolutePath()
    			+ "/SmartDental";
    			private final String DATABASE_FILENAME = "database";
        public void run()
        {
            try{
            	ScheduleManager sm = new ScheduleManager(context);
                Socket socket = new Socket(serverIP, serverPort);
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                sm.deleteAll();
                String str = "first";
                out.write(str.getBytes());
                String path = DATABASE_PATH + "/" + DATABASE_FILENAME;
                File localfile = new File(path);
                try
                {
                	localfile.delete();
                    FileOutputStream fos = new FileOutputStream(localfile);
                    byte[] text = new byte[4096];
                    int bytenum;
                    while((bytenum = in.read(text)) != -1)
                    {
                        fos.write(text, 0, bytenum);
                    }
                    fos.close();               
                    in.close();
                }
                catch(FileNotFoundException e)
                {
                    System.out.println("FileNotFoundException: " + e.getMessage());
                }
                catch (IOException e)
                {
                    System.out.println("IOException: " + e.getMessage());
                }

                socket.close();
                try {
					sm.addAll();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	                    break;
	                case "mod":
	                    count = in.read(buffer);
	                    data = new String(buffer, 0, count);
	                    newOne = SeAndJsonExchanging.JsonToSE(data);
	                    manager.editSchedule(newOne);
	                    //修改操作
	                    break;
	                case "del":
	                    count = in.read(buffer);
	                    data = new String(buffer, 0, count);
	                    newOne = SeAndJsonExchanging.JsonToSE(data);
	                    manager.deleteSchedule(newOne.id);
	                    //删除操作
	                    break;
	                 default:
	                	 break;
	                }
	            }
	        }
	        catch(Exception e){
	            System.out.println("IOException: " + e.getMessage());
	        } 
	    }
	}
}