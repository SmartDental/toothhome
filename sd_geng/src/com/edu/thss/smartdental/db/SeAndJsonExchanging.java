package com.edu.thss.smartdental.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;

import com.edu.thss.smartdental.model.ScheduleElement;

public class SeAndJsonExchanging {
	public static ScheduleElement JsonToSE(String json) throws JSONException, java.text.ParseException{
		JSONObject object = new JSONObject(json);
		
		DateFormat   df   =   new   SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		try {
			time = df.parse((String) object.get("alertTime"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String oper = (String)object.getString("operation");
		ScheduleElement se = new ScheduleElement(
				object.getString("name"),
				time,
				object.getString("description"),
				object.getString("fromUser"),
				(String) object.getString("toUser")
				);
		
		return se;
	}

	
	public static String SEToJson(String operation, ScheduleElement se) throws JSONException {
		JSONObject object = new JSONObject();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = df.format(se.alertTime);
		object.put("operation", operation);
		object.put("name", se.name);
		object.put("alertTime",d);
		object.put("description", se.description);
		object.put("fromUser", se.fromUser);
		object.put("toUser", se.toUser);
		object.put("id", se.id);
		return object.toString();
	}
}
