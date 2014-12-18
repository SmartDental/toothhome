package com.edu.thss.smartdental;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class CalendarAlarmServer extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher; 
		CharSequence tickerText = "SmartDental";
		long when = System.currentTimeMillis(); 
		Notification notification = new Notification(icon, tickerText, when);
		notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        Context context = getApplicationContext();
        CharSequence contentTitle = "±êÌâ";
        CharSequence contentText = "ÄÚÈÝ";
        Bundle bundle = intent.getExtras();
        if (bundle != null) {  
        	contentTitle = bundle.getString("title");
        	contentText = bundle.getString("text");
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK); 
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent);  
        nm.notify(1, notification);
	}
}
