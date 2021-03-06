package com.edu.thss.smartdental;

import java.io.*;

import com.edu.thss.smartdental.db.ScheduleManager;

import android.content.Context;
import android.os.Environment;

public class RoleId {
	private String role;
	private int id;
	
	public RoleId() {
		this.role = "";
		this.id = 0;
	}
	
	@SuppressWarnings("resource")
	public void readFile(String filename){
			File file = new File(Environment.getExternalStorageDirectory(),filename);
			BufferedReader input = null;
			try {
				input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer buffer = new StringBuffer();
			int tmp = 0;
			try {
				while ((tmp = input.read()) != 32 )
				{
					if(tmp == -1)
					{
						role = "";
						return;
					}
					buffer.append((char)tmp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			role = buffer.toString();
			buffer.setLength(0);
			try {
				while ((tmp = input.read()) != -1)
					buffer.append((char)tmp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			id = Integer.parseInt(buffer.toString());
			try {
				input.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	public void initCounter(int counter)
	{
		File file2 = new File(Environment.getExternalStorageDirectory(),"counter.txt");
		try {
			@SuppressWarnings("resource")
			FileOutputStream fos = new FileOutputStream(file2);
			try {
				fos.write(String.valueOf(counter).getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String getRole() {
		return this.role;
	}
	
	public int getId() {
		return this.id;
	}
}