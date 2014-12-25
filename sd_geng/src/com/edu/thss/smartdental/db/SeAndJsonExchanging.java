package com.edu.thss.smartdental.db;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.edu.thss.smartdental.model.ScheduleElement;

public class SeAndJsonExchanging {
	public ScheduleElement JsonToSE(String json) throws JSONException{
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
	
	public String SEToJson(String operation, ScheduleElement se) throws JSONException {
		JSONObject object = new JSONObject();
		object.put("operation", operation);
		object.put("name", se.name);
		object.put("alertTime", se.alertTime);
		object.put("description", se.description);
		object.put("fromUser", se.fromUser);
		object.put("toUser", se.toUser);
		object.put("id", se.id);
		return object.toString();
	}
}
