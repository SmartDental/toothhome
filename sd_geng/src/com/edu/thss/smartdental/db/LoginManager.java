package com.edu.thss.smartdental.db;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginManager {
	private String username;
	private String password;
	private String address;
	private int port;
	private String type;
	private String reply;
	
	public LoginManager(String un, String pw, String add, int p, String t) {
		this.username = un;
		this.password = pw;
		this.address = add;
		this.port = p;
		this.type = t;
		this.reply = null;
	}
	
	public String getReply()
	{
		return this.reply;
	}
	public Boolean login() throws Throwable {
		try {
			Socket socket = new Socket(address, port);
			InputStream in = socket.getInputStream();
	        OutputStream out = socket.getOutputStream();
	        out.write(type.getBytes());
	        JSONObject object = new JSONObject();
	        object.put("username", username);
	        object.put("password", MD5(password.getBytes()));
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
			File role = new File("role.txt");
			FileOutputStream is = new FileOutputStream(role);
			is.write(reply.getBytes(), 0, reply.length());
		} catch (FileNotFoundException e) {
			;
		}
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
	
	private Boolean loginSuccess() {
		String[] success = {"dad", "mom", "child"};
		for (int i = 0; i < success.length; i++) {
			if (this.reply == success[i])
				return true;
		}
		return false;
	}
}
