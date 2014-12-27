package com.edu.thss.smartdental.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.edu.thss.smartdental.model.ScheduleElement;

public class SeAndJsonExchanging {
	public static ScheduleElement JsonToSE(String json) throws JSONException{
		JSONObject object = new JSONObject(json);
		ScheduleElement se = new ScheduleElement();
		se.name = object.getString("name");
		se.alertTime = (Date) object.get("alertTime");
		se.description = object.getString("description");
		se.fromUser = object.getString("fromUser");
		se.toUser = (String) object.getString("toUser");
		se.id = object.getInt("id");
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
