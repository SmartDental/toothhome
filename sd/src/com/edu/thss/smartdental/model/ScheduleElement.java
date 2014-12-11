package com.edu.thss.smartdental.model;

import java.util.Date;

public class ScheduleElement {
	public String name;
	public Date alertTime;
	public String description;
	public String fromUser;
	public String toUser;
	public int id;
	
	public ScheduleElement(String n, Date a, String d, String f, String t){
		name = n;
		alertTime = a;
		description = d;
		fromUser = f;
		toUser = t;
	}
	public ScheduleElement(){

	}
}
