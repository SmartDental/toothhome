package com.edu.thss.smartdental.db;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlertManager {
	public long gettime(int year, int month, int day, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		calendar.set(Calendar.MINUTE, minute);  
		calendar.set(Calendar.HOUR_OF_DAY, hour);  
		calendar.set(Calendar.SECOND, 0);  
		calendar.set(Calendar.MILLISECOND, 0); 
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTimeInMillis();
	}
	
	public void setReminder(Context context, long time, String title, String text, int count) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
		Intent intent = new Intent("android.intent.action.C_ACTION");
		intent.putExtra("title", title);
		intent.putExtra("text", text);
		PendingIntent pi = PendingIntent.getBroadcast(context, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);
	}
	
	public void delReminder(Context context, int count) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); 
		Intent intent = new Intent("android.intent.action.C_ACTION");
		PendingIntent pi = PendingIntent.getBroadcast(context, count, intent, 0);
        am.cancel(pi);
	}
}
