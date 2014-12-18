package com.edu.thss.smartdental;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CalendarNotificationReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.C_ACTION".equals(intent.getAction()))
    	{
    	   Bundle bundle = intent.getExtras();
    	   Intent i = new Intent("android.intent.action.START_SREVER"); 
    	   i.putExtras(bundle);
    	   context.startService(i);
    	}
	}

}
