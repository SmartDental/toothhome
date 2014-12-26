package com.edu.thss.smartdental;

import java.util.Date;

import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.ui.calendar.CalendarView.OnItemClickListener;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Schedule extends Fragment {

	CalendarView calendar;
	Button left;
	Button right;
	TextView title;
	TextView casehistory;
	TextView info;
	static int RESULT_CODE = 1;
	private OnClickListener clicklistener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(v.getId() == left.getId()){
				title.setText(calendar.clickLeftMonth());
			}
			if(v.getId() == right.getId()){
				title.setText(calendar.clickRightMonth());
			}
		}};
	
	
	public Schedule(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.member_schedule, container,false);
		info = (TextView)rootView.findViewById(R.id.message);
		casehistory = (TextView)rootView.findViewById(R.id.caseHistory);
		info.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View v) {  
//	                getActivity()  
//	                        .getSupportFragmentManager()  
//	                        .beginTransaction()  
//	                        .replace(R.id.content_frame, new CheckInfo(),  
//	                                "Fragmenttest2")  
//	                        .commit();  
	  
	            }  
	        });
		casehistory.setOnClickListener(new OnClickListener() {  
			  
            @Override  
            public void onClick(View v) {  
//                getActivity()  
//                        .getSupportFragmentManager()  
//                        .beginTransaction()  
//                        .replace(R.id.content_frame, new CaseHistory(),  
//                                "Fragmenttest2")  
//                        .commit();  
  
            }  
        });
		return rootView;
	}
	
	class calendarItemClickListener implements OnItemClickListener{

		@Override
		public void OnItemClick(Date date) {
			Intent intent = new Intent(getActivity(),OnedayAppointActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}  
	 }
}
