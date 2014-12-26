package com.edu.thss.smartdental;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Server{
	public static int i = 0;
	private static ServerSocket server;
	
	//-1:尚未使用或者链接终止，1：first链接，2：客户机用于监听的链接，3：客户机传输修改的链接
	public static int[] conditionFlag = new int[1024];
	
	//0：不需要传输修改信息，1：需要修改
	public static int[] changeFlag = new int[1024];

	public static int num_of_thread = 0;
	
  
	public static void main(String[] args) throws Throwable {
		System.out.println("FtpServer started.");
		for(int n=0; n < 1024; n++)
			conditionFlag[n] = -1;
			
		try{
			server = new ServerSocket(8888);
			while(true)
			{
				Socket client = server.accept();
				System.out.println("New Client has connected to the server. Its id is " + i + ". Its address is " + client.getInetAddress().getHostAddress() +":" + client.getPort() + ".");
				Thread t = new connection(client, i);
				t.start();
				i++;
				num_of_thread++;
			}
		}
		catch(IOException e){
			System.out.println("IOException: " + e.getMessage());
		}
	}
	
	public static class connection extends Thread{
	static Socket client;
	static int id;
	InputStream inputStream;
	BufferedReader in;
	OutputStream outputStream;
	PrintWriter out;
	
	String input;
	String reply;
	
	connection(Socket client, int id) throws IOException{
		this.client = client;
		this.id=id;
		this.inputStream = this.client.getInputStream();
		this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		this.outputStream = this.client.getOutputStream();
		this.out = new PrintWriter(outputStream,true);
	}
	
	public void run(){
		try{
			System.out.println("enter run");
			
			byte[] buffer = new byte[4096]; 
			int count = inputStream.read(buffer);  
			input = new String(buffer, 0, count);
			System.out.println("get : " + input);
			switch(input){
				case "first":
					//first连接：用于获取数据，并且显示在app中
					
					conditionFlag[this.id] = 1;
					first();
					break;
				case "listen":
					//listen连接：客户机用于监听的连接，需要修改的时候，从此连接发送修改内容
					
					conditionFlag[this.id] = 2;
					listen();
					break;
				case "change":
					//change连接：客户机用于发送修改请求的连接
					
					conditionFlag[this.id] = 3;
					change();
					break;
				default:
					break;
			}
			return;
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("IO: Client with id " + id + " " + e.getMessage());
		}
	}
	
	private void first(){
		
	}
	
	private void listen(){
		//
		while(true){
			if(changeFlag[this.id] == 0)
				continue;
			//--发送修改内容
			changeFlag[this.id] = 0;
		}
	}
	private void change(){
		//--接收客户机的请求
		try{
		while(true){
			byte[] buffer = new byte[4096]; 
			int count = inputStream.read(buffer);  
			input = new String(buffer, 0, count);
			System.out.println("get : " + input);
			
			//--修改，提示各监听进程
			for(int i = 0; i < 1024; i++){
				if(conditionFlag[i] == 2)
					changeFlag[i] = 1;
				
			}
			//--对应处理
			//void addSchedule(ScheduleElement Sch)
			//void deleteSchedule(int id)
			//void editSchedule(ScheduleElement Sch)

		}
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("IO: Client with id " + id + " " + e.getMessage());
		}
	}
	
}

}


//class solution extends Thread{}