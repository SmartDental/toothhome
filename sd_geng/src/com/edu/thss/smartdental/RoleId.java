package com.edu.thss.smartdental;

import java.io.*;

public class RoleId {
	private String role;
	private int id;
	
	RoleId() {
		this.role = "";
		this.id = 0;
	}
	
	public void readFile(String filename) throws Throwable {
		try {
			File file = new File(filename);
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuffer buffer = new StringBuffer();
			int tmp;
			while ((tmp = input.read()) != 32)
				buffer.append((char)tmp);
			role = buffer.toString();
			buffer.setLength(0);
			while ((tmp = input.read()) != -1)
				buffer.append((char)tmp);
			id = Integer.parseInt(buffer.toString());
			input.close();
		} catch (IOException e) {
			;
		}
	}
	
	public String getRole() {
		return this.role;
	}
	
	public int getId() {
		return this.id;
	}
}