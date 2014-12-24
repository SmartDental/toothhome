package com.edu.thss.smartdental;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.adapter.ScheduleListAdapter;
import com.edu.thss.smartdental.adapter.UserAdapter;
import com.edu.thss.smartdental.db.ScheduleManager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

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

public class UserList extends Fragment {
	private Fragment cur = this;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_user, container,false);

		final UserAdapter ua = new UserAdapter(getActivity());
		
		ListView scheduleList = (ListView)rootView.findViewById(R.id.userList);
		scheduleList.setAdapter(ua);

		
		scheduleList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	
		        Bundle bundle = new Bundle();
		        bundle.putString("user", ua.getItem(arg2));
            	FragmentManager fm = getActivity().getSupportFragmentManager();
            	CalendarFragment caldroidFragment = new CalendarFragment();
				Bundle args = new Bundle();
				Calendar cal = Calendar.getInstance();
				args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
				args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
				caldroidFragment.setArguments(args);
				final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
				
				final CaldroidListener listener = new CaldroidListener() {

				    @Override
				    public void onSelectDate(Date date, View view) {
				        Calendar cal = Calendar.getInstance();
				        cal.setTime(date);
				        int year = cal.get(Calendar.YEAR);
				        int month = cal.get(Calendar.MONTH);
				        int day = cal.get(Calendar.DAY_OF_MONTH);
				        Bundle bundle = new Bundle();
				        bundle.putString("year", String.valueOf(year));
				        bundle.putString("month", String.valueOf(month));
				        bundle.putString("day", String.valueOf(day));
				        FragmentManager fragmentManager = cur.getActivity().getSupportFragmentManager();
				        Fragment fragment  = new ScheduleFragment();
						if(fragment != null){
							fragment.setArguments(bundle);
							//FragmentManager fragmentManager = ta.getSupportFragmentManager();
							fragmentManager
							.beginTransaction()
							.replace(R.id.content_frame,fragment)
							.hide(cur)
							.addToBackStack(null)
							.commit();
							
						}
//				        ScheduleElement se = new ScheduleElement();
//				        jumpToEdit(se);
				        
				    }

				};

				caldroidFragment.setCaldroidListener(listener);

				FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
				t.replace(R.id.content_frame, caldroidFragment)
				.addToBackStack(null)
				.hide(cur)
				.commit();         

            }
        });

		return rootView;
	}
}
