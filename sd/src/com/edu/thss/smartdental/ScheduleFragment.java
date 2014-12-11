/*
 * ×÷Õß£º¹¢ÕýÁØ
 */
package com.edu.thss.smartdental;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.adapter.ScheduleListAdapter;
import com.edu.thss.smartdental.db.ScheduleManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class ScheduleFragment extends Fragment {
	private int year = 0;
	private int month = 0;
	private int day = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String y = this.getArguments().getString("year");
		String m = this.getArguments().getString("month");
		String d = this.getArguments().getString("day");
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);
		View rootView = inflater.inflate(R.layout.fragment_schedule, container,false);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DAY_OF_MONTH,day);

		Date date = cal.getTime();
		final ScheduleListAdapter sla = new ScheduleListAdapter(getActivity(), date);
		
		ListView scheduleList = (ListView)rootView.findViewById(R.id.listView);
		scheduleList.setAdapter(sla);

		
		scheduleList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	TextView th = (TextView)arg1.findViewById(R.id.hour);
            	TextView tm = (TextView)arg1.findViewById(R.id.min);
            	String time = th.getText().toString() +tm.getText().toString();
            	ScheduleElement tse = sla.getItem(arg2);
            	Bundle bundle = new Bundle();
                bundle.putString("name", tse.name);
                bundle.putString("description", tse.description);
		        bundle.putString("time", time);
		        
            	FragmentManager fm = getActivity().getSupportFragmentManager();
                ScheduleDetailDialog editNameDialog = new ScheduleDetailDialog();
                editNameDialog.setArguments(bundle);
                editNameDialog.show(fm, "fragment_schedule_detail");          

            }
        });
		
		final ImageView add = (ImageView)rootView.findViewById(R.id.schedule_add);
		add.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    add.setAlpha(1.0f);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	add.setAlpha(0.8f);
                	Fragment fragment  = new ScheduleDetailFragment();
                	Bundle bundle = new Bundle();
                    bundle.putString("action", "add");
                    bundle.putString("year", String.valueOf(year));
			        bundle.putString("month", String.valueOf(month));
			        bundle.putString("day", String.valueOf(day));
                    fragment.setArguments(bundle);
			        
        			if(fragment != null){
        				fragment.setArguments(bundle);
        				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        				fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
        			}
                    break;
                }
                }
                return true;
            }
        });
		return rootView;
	}
}
