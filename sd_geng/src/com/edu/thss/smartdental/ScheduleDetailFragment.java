package com.edu.thss.smartdental;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.CalendarFunction;
import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.adapter.ScheduleListAdapter;
import com.edu.thss.smartdental.db.ScheduleManager;
import com.edu.thss.smartdental.db.SeAndJsonExchanging;
import com.edu.thss.smartdental.R;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class ScheduleDetailFragment extends Fragment {
	public Fragment cur = this;
	private View myView = null;
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private String fromUser;
	private String toUser;
	private String action;
	private String id="-1";
	ScheduleManager sm;

	public void addListenerOnButton() {
		final TextView tvDisplayTime = (TextView) myView.findViewById(R.id.edit_time);
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		setTime(tvDisplayTime, hour, minute);
		final ImageView btnChangeTime;
		btnChangeTime = (ImageView) myView.findViewById(R.id.time_btn);
		btnChangeTime.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                	btnChangeTime.setAlpha(1.0f);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	btnChangeTime.setAlpha(0.5f);
                	FragmentManager fm = getActivity().getSupportFragmentManager();
    				MyTimePicker tp = new MyTimePicker(tvDisplayTime);
                    tp.show(fm, "fragment_schedule_detail"); 
                    break;
                }
                }
                return true;
            }
        });

	}
	
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}
	
	public void setTime(TextView tv, int hour, int minute){
		final Calendar c = Calendar.getInstance();
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);

		// set current time into textview
		tv.setText(new StringBuilder().append(pad(hour)).append(":")
				.append(pad(minute)));
	}
	
	void getArgs(){
		String y = this.getArguments().getString("year");
		String m = this.getArguments().getString("month");
		String d = this.getArguments().getString("day");
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);
		fromUser = this.getArguments().getString("fromUser");
		toUser = this.getArguments().getString("toUser");
		action = this.getArguments().getString("action");
		id = this.getArguments().getString("id");
		if (id == null){
			id = "-1";
		}
	}
	
	void setPage(){
		if (action.equals("edit")){
			TextView name = (TextView)myView.findViewById(R.id.edit_name);
			TextView description = (TextView)myView.findViewById(R.id.edit_description);
			TextView time = (TextView)myView.findViewById(R.id.edit_time);
			String tn = this.getArguments().getString("name");
			String tt = this.getArguments().getString("time");
			String td = this.getArguments().getString("description");
			name.setText(tn);
			description.setText(td);
			time.setText(tt);
		}
	}
	
	Bundle getScheduleInfo(){
		Bundle bundle = new Bundle();
		bundle.putString("year", String.valueOf(year));
        bundle.putString("month", String.valueOf(month));
        bundle.putString("day", String.valueOf(day));
        bundle.putString("fromUser", fromUser);
        bundle.putString("toUser", toUser);
        return bundle;
	}
	
	void jumpToSchedule(){
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		Fragment fragment  = new ScheduleFragment();
		fragment.setArguments(getScheduleInfo());
		if(fragment != null){
			//FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			fragmentManager.popBackStack();
			fragmentManager.beginTransaction()
			.hide(cur)
			.replace(R.id.content_frame,fragment).commit();
			getActivity().getSupportFragmentManager().executePendingTransactions();
		}
	}
	
	void setCancelButton(){
		Button cancel = (Button)myView.findViewById(R.id.edit_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			   @Override
			   public void onClick(View arg0) {
				   jumpToSchedule();
			   }
			});
	}
	
	void setSubmitButton(){
		Button submit = (Button)myView.findViewById(R.id.edit_submit);
		submit.setOnClickListener   (new OnClickListener(){
			@Override
			   public void onClick(View arg0) {
					ScheduleElement se = getScheduleElement();
					if (action.equals("edit")){
						editSchedule(se);
					}
					else{
						addSchedule(se);
					}
					jumpToSchedule();
			   }
		});
	}
	
	ScheduleElement getScheduleElement(){
		TextView name = (TextView)myView.findViewById(R.id.edit_name);
		TextView description = (TextView)myView.findViewById(R.id.edit_description);
		TextView time = (TextView)myView.findViewById(R.id.edit_time);
		String[] tmp = time.getText().toString().split(":");
		int hour = Integer.parseInt(tmp[0]);
		int min = Integer.parseInt(tmp[1]);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DAY_OF_MONTH,day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE,min);
		Date d = cal.getTime();
		ScheduleElement se = new ScheduleElement(name.getText().toString(), d, description.getText().toString(), fromUser, toUser);
		se.id = Integer.parseInt(id);
		return se;
	}
	
	void editSchedule(ScheduleElement se){
		sm.editSchedule(se);
		//jiazai
		try {
			if(CalendarFunction.send(se, "mod"))
			{
//				ScheduleManager manager = new ScheduleManager(null);
//				manager.editSchedule(se);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void addSchedule(ScheduleElement se){
		sm.addSchedule(se);
		//jiazai
		try {
			if(CalendarFunction.send(se, "add"))
			{
//				ScheduleManager manager = new ScheduleManager(null);
//				manager.addSchedule(se);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getArgs();
		myView = inflater.inflate(R.layout.fragment_schedule_edit, container, false);
		sm = new ScheduleManager(getActivity());
		addListenerOnButton();
		setPage();
		setCancelButton();
		setSubmitButton();
		return myView;
	}
		

}
