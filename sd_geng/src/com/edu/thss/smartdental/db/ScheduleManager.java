package com.edu.thss.smartdental.db;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.edu.thss.smartdental.R;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.model.general.SDPatient;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.TimeZone;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;





public class ScheduleManager {
	private Context context;
	private SQLiteDatabase db;
	private static final String DB_NAME = "schedule.db";
	private static final String PACKAGE_NAME="com.edu.thss.smartdental";
	public static final String DB_PATH="/data"+Environment.getDataDirectory().getAbsolutePath()+"/"+PACKAGE_NAME;
	private final int BUFFER_SIZE = 400000;
	     
	public ScheduleManager(Context context){
			this.context = context;
			
	}
	 
	public SQLiteDatabase getDatabase() {
	        return db;
	}
	 
	public void setDatabase(SQLiteDatabase database) {
	        this.db = database;
	}
	
	public void openDatabase() {
	    	Log.i("open", DB_PATH + "/" + DB_NAME);
	        this.db = this.openDatabase(DB_PATH + "/" + DB_NAME);
	}
	 
	private SQLiteDatabase openDatabase(String dbfile) {
	 
	        try {
	            if (!(new File(dbfile).exists())) {
	                //判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
	                InputStream is = this.context.getResources().openRawResource(
	                        R.raw.schedule); //欲导入的数据库
	                FileOutputStream fos = new FileOutputStream(dbfile);
	                byte[] buffer = new byte[BUFFER_SIZE];
	                int count = 0;
	                while ((count = is.read(buffer)) > 0) {
	                    fos.write(buffer, 0, count);
	                }
	                fos.close();
	                is.close();
	            }
	 
	            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
	            return db;
	 
	        } catch (FileNotFoundException e) {
	            Log.e("Database", "File not found");
	            e.printStackTrace();
	        } catch (IOException e) {
	            Log.e("Database", "IO exception");
	            e.printStackTrace();
	        }
	        return null;
	}

	     /**
		 * 关闭数据库
		 * */
	public void closeDB(){
			db.close();
	}
	
	public int addCounter(){
		String tmp = getFromFile("counter.txt");
		 tmp = tmp.replaceAll("\n", "");
		int counter = 0;
		if (tmp.equals("")) counter = 0;
		else{
			counter = Integer.parseInt(tmp);
		}
		counter ++;
		writeToFile("counter.txt", String.valueOf(counter));
		return counter;
	}
	
    public String getFromFile(String fileName){
    	
		try {
			FileInputStream in;
			in = context.openFileInput(fileName);
			InputStreamReader inputStreamReader = new InputStreamReader(in);
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = bufferedReader.readLine()) != null) {
	        	line = line + "\n";
	            sb.append(line);
	            
	        }
	        return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
        

//    	String res = "";
//
//    	try {  
//            FileInputStream inputStream = context.openFileInput(fileName);  
//            byte[] bytes = new byte[1024];  
//            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
//            while (inputStream.read(bytes) != -1) {  
//                arrayOutputStream.write(bytes, 0, bytes.length);  
//            }  
//            inputStream.close();  
//            arrayOutputStream.close();  
//            String content = new String(arrayOutputStream.toByteArray());  
//            return content;
//  
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }
//		return res;  
    }
    


	public void writeToFile(String fileName, String str){
    	try{
    		FileOutputStream out = context.openFileOutput(fileName, Activity.MODE_PRIVATE);
    		
    		out.write(str.getBytes());
    		out.flush();
    		out.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}

    }
    
	public String ScheduleElementToString(ScheduleElement sch){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = df.format(sch.alertTime);
		String str = sch.id + "```" +  
	             sch.name + "```" +  
			     d + "```" +
			     sch.description + "```" + 
			     sch.fromUser + "```" + 
			     sch.toUser  + "\n";
		return str;
	}
	
	public ScheduleElement StringToScheduleElement(String str) throws ParseException{
		String[] tmp = null;
		try{
//			String dir = context.getFilesDir().getAbsolutePath();
//			File f0 = new File(dir, "schedule.txt");
//			f0.delete();
			tmp = str.split("```");
		}
		catch(Exception e){
			return null;
		}
		
		if (tmp.length < 4) return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ScheduleElement sch = new ScheduleElement();
		try{
		sch.id =  Integer.parseInt(tmp[0]);  }
		catch(Exception e){
		}
	    sch.name = tmp[1];
	    
		sch.alertTime = sdf.parse(tmp[2]);
	    sch.description = tmp[3]; 
	    sch.fromUser = tmp[4];
	    sch.toUser = tmp[5];
		return sch;
	}
	
	
    public void addSchedule(ScheduleElement newOne){
    	openDatabase();
    	
        DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String time =sdf.format(newOne.alertTime);
 		String date =df.format(newOne.alertTime);
    	
 		newOne.id = addCounter();
 		
    	ContentValues values = new ContentValues();
    	values.put("id", newOne.id);
    	values.put("name", newOne.name);
    	values.put("description", newOne.description);
    	values.put("fromUser", newOne.fromUser);
    	values.put("toUser", newOne.toUser);
    	values.put("alertTime", time);
    	values.put("alertDate", date);
    	db.insert("schedule", null, values);
    	db.close();
    	/*
    	String file = getFromFile("schedule.txt");
    	newOne.id = addCounter();
    	String str = ScheduleElementToString(newOne);
    	file= file + str;
    	writeToFile("schedule.txt", file);
    	*/
    	
    	Calendar calendar = Calendar.getInstance();
    	Date now = new Date();
    	if (now.before(newOne.alertTime))
    	{calendar.setTime(newOne.alertTime);
    	setReminder(calendar.getTimeInMillis(), newOne.name, newOne.description, newOne.id);}
    	
    }
    
    public int[] get_All_id (){
    	openDatabase();
    	int len = 0;
    	Cursor c = db.rawQuery("SELECT * FROM schedule", null);
		while(c.moveToNext()){
			len++;
		}
		c.close();
		
		int[] list = new int[len];
		
		int i = 0;
	  	c = db.rawQuery("SELECT * FROM schedule", null);
		while(c.moveToNext()){
			list[i++] =  Integer.parseInt(c.getString(c.getColumnIndex("id")));
		}
		c.close();
		
    	db.close();
    	return list;
    }
    
    public int[] get_All_days(int year, int month, String toUser)
    {
    	openDatabase();
    	
    	DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   		//String date =df.format(time);
    	
    	boolean[] day_flag = new boolean[32];
    	for (int i = 1; i<=31; i++)
    		day_flag[i] = false;
    	
    	Cursor c = db.rawQuery("SELECT * FROM schedule", null);
    	
    	if(c.getCount()>0){
			c.moveToFirst();
			while(c.isAfterLast() == false)
			{
				Date time = new Date();
				try {
					time = sdf.parse(c.getString(c.getColumnIndex("alertTime")));
				} catch (ParseException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				Calendar cal = Calendar.getInstance();
		        cal.setTime(time);
		        int y = cal.get(Calendar.YEAR);
		        int m = cal.get(Calendar.MONTH);
		        int d = cal.get(Calendar.DAY_OF_MONTH);
		        String user = c.getString(c.getColumnIndex("toUser"));
		        if (y == year && m == month && user.equals(toUser))
		        {
		        	day_flag[d] = true;
		        }
				c.moveToNext();
			}
    	}
    	c.close();
    	db.close();
    	
    	int len = 0;
    	for (int i = 1; i<=31; i++)
    		if (day_flag[i])
    		{
    			len++;
    		}
    	
    	int[] list = new int [len];
    	int j = 0;
    	for (int i = 1; i<=31; i++)
    		if (day_flag[i])
    		{
    			list[j++] = i;
    		}
    	return list;
    }
    
    public ScheduleElement[] ScheduleList (Date time, String toUser){
	    openDatabase();
    	
    	Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String date =df.format(time);
    	//ScheduleElement[] list = new ScheduleElement[len];
//		String[] columns = {"id","name","alertTime","alertDate","description","fromUser","toUser"};
//		String[] selection = new String[]{date};
		int len = 0;
 		Cursor c = db.rawQuery("select * from schedule where alertDate = '"+date+"'" + " and toUser ='"+ toUser +"'",null);
 		if(c.getCount()>0){
 			c.moveToFirst();
 			while (c.isAfterLast() == false)
 			{
 				c.moveToNext();
 				len ++;
 			}
 		}
		c.close();
		
		ScheduleElement[] list = new ScheduleElement[len];
		
		int i = 0;
		c = db.rawQuery("select * from schedule where alertDate = '"+date+"'" + " and toUser ='"+ toUser +"'",null);
 		if(c.getCount()>0){
 			 c.moveToFirst();
 			while(c.isAfterLast() == false)
 			{
	 			ScheduleElement newone = new ScheduleElement ();
	 			newone.id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
	 			try {
					newone.alertTime = sdf.parse(c.getString(c.getColumnIndex("alertTime")));
				} catch (ParseException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
	 			newone.description = c.getString(c.getColumnIndex("description"));
	 			newone.name = c.getString(c.getColumnIndex("name"));
	 			newone.fromUser = c.getString(c.getColumnIndex("fromUser"));
	 			newone.toUser = c.getString(c.getColumnIndex("toUser"));
	 			list[i++] = newone;
	 			c.moveToNext();
 			}
 		}
		c.close();
		db.close();
		return list;
    	/*
    	String file = getFromFile("schedule.txt");
    	String[] tmp = file.split("\n");
    	int len = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
    	for (int i=0; i < tmp.length; i++)
    	{
    		try {
				ScheduleElement sch =  StringToScheduleElement(tmp[i]);
				if (sch == null) continue;
		        cal.setTime(sch.alertTime);
		        int year = cal.get(Calendar.YEAR);
		        int month = cal.get(Calendar.MONTH);
		        int day = cal.get(Calendar.DAY_OF_MONTH);
				if (year == y && month == m && day == d)
				{
					len++;
				}
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
    		
    	}
    	ScheduleElement[] list = new ScheduleElement[len];
    	int n = 0;
    	for (int i=0; i < tmp.length; i++)
    	{
    		try {
				ScheduleElement sch =  StringToScheduleElement(tmp[i]);
				if (sch == null) continue;
				cal.setTime(sch.alertTime);
		        int year = cal.get(Calendar.YEAR);
		        int month = cal.get(Calendar.MONTH);
		        int day = cal.get(Calendar.DAY_OF_MONTH);
				if (year == y && month == m && day == d)
				{
					list[n++] = sch;
				}
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
    		
    	}
    	return list;
    	*/
    }
    
    public void deleteSchedule(int id){
    	openDatabase();
    	String selection = "id LIKE ?";
    	String[] selectionArgs = { String.valueOf(id) };
    	    
    	db.delete("schedule", selection, selectionArgs);
    	db.close();
    	/*
    	String file = getFromFile("schedule.txt");
    	String[] tmp = file.split("\n");
    	String newFile = "";
    	for (int i=0; i < tmp.length; i++)
    	{
    		try {
				ScheduleElement sch =  StringToScheduleElement(tmp[i]);
				if (sch == null) continue;
				if (sch.id != id)
				{
					newFile += tmp[i]+ "\n";
				}
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
    		
    	}
    	writeToFile("schedule.txt", newFile);
    	*/
    	delReminder(id);
    }
    
    public void editSchedule(ScheduleElement newOne){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(newOne.alertTime);
    	delReminder(newOne.id);
    	Date now = new Date();
    	if (now.before(newOne.alertTime))
    	{calendar.setTime(newOne.alertTime);
    	setReminder(calendar.getTimeInMillis(), newOne.name, newOne.description, newOne.id);}
    	
    	openDatabase();
    	
        DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String time =sdf.format(newOne.alertTime);
 		String date =df.format(newOne.alertTime);
    	
    	ContentValues values = new ContentValues();
    	values.put("id", newOne.id);
    	values.put("name", newOne.name);
    	values.put("description", newOne.description);
    	values.put("fromUser", newOne.fromUser);
    	values.put("toUser", newOne.toUser);
    	values.put("alertTime", time);
    	values.put("alertDate", date);
    	
    	String selection = "id LIKE ?";
        String[] selectionArgs = { String.valueOf(newOne.id) };
     
        db.update("schedule", values, selection, selectionArgs);
    	
    	db.close();
    	/*
    	deleteSchedule(newOne.id);
    	addSchedule(newOne);
    	*/
    	
    }
    
    private void setReminder(long time, String title, String text, int count) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
		Intent intent = new Intent("android.intent.action.C_ACTION");
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		PendingIntent pi = PendingIntent.getBroadcast(context, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
	
	private void delReminder(int count) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
		Intent intent = new Intent("android.intent.action.C_ACTION");
		PendingIntent pi = PendingIntent.getBroadcast(context, count, intent, 0);
        am.cancel(pi);
	}
}
