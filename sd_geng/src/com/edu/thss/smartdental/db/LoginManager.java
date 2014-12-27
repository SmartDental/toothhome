package com.edu.thss.smartdental.db;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

import com.edu.thss.smartdental.RoleId;

public class LoginManager {
	private static String username;
	private static String password;
	private static String address;
	private static int port;
	private static String type;
	private static String reply;
	
	public LoginManager(String un, String pw, String add, int p, String t) {
		username = un;
		password = pw;
		address = add;
		port = p;
		type = t;
		reply = null;
	}
	
	public String getReply()
	{
		return this.reply;
	}
	
	
	public class loginTh extends Thread
	{
		public void run()
		{
			try {
				Socket socket = new Socket(address, port);
				InputStream in = socket.getInputStream();
		        OutputStream out = socket.getOutputStream();
		        out.write(type.getBytes());
		        JSONObject object = new JSONObject();
		        try {
					object.put("username", username);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        try {
					object.put("password", MD5(password.getBytes()));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        String sent = object.toString();
		        out.write(sent.getBytes());
		        byte[] buffer = new byte[4096]; 
		        int count = in.read(buffer);
		        reply = new String(buffer, 0, count);
		        socket.close();
			} catch (IOException e) {
				reply = "netError";
			}
			try {
				File role = new File(Environment.getExternalStorageDirectory(),"role.txt");
				role.delete();
				role.createNewFile();
				FileOutputStream is = new FileOutputStream(role, false);
				is.write(reply.getBytes(), 0, reply.length());
				is.close();
			} catch (FileNotFoundException e) {
				;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Boolean login() throws Throwable {
		Thread t = new loginTh();
		t.start();
		t.join();
		return loginSuccess();
	}
	
	private String MD5(byte[] src) {
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'};
		String s = null;
		try {
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.update(src);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			} 
			s = new String(str);
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return s;
	}
	
	private Boolean loginSuccess() throws Throwable {
		String[] success = {"dad", "mom", "child"};
		RoleId re = new RoleId();
		re.readFile("role.txt");
		for (int i = 0; i < success.length; i++) {
			if ((re.getRole()).equals(success[i]))
				return true;
		}
		return false;
	}
}
