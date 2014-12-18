package com.edu.thss.smartdental.db;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.edu.thss.smartdental.model.ScheduleElement;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;





public class ScheduleManager {
	private Context context;
	public ScheduleManager(Context context){
		this.context = context;
		
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
    	String file = getFromFile("schedule.txt");
    	newOne.id = addCounter();
    	String str = ScheduleElementToString(newOne);
    	file= file + str;
    	writeToFile("schedule.txt", file);
    	Calendar calendar = Calendar.getInstance();
    	Date now = new Date();
    	if (now.before(newOne.alertTime))
    	{calendar.setTime(newOne.alertTime);
    	setReminder(calendar.getTimeInMillis(), newOne.name, newOne.description, newOne.id);}
    }
    
    public ScheduleElement[] ScheduleList (Date time){
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
    }
    
    public void deleteSchedule(int id){
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
    	deleteSchedule(newOne.id);
    	addSchedule(newOne);
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
