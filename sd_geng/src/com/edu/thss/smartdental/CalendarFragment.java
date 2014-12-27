package com.edu.thss.smartdental;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.thss.smartdental.adapter.CalendarAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarFragment extends CaldroidFragment {
	private String fromUser;
	private String toUser;
	private CaldroidGridAdapter cga;
	
	void setCalendarListener(){
		final Fragment cur = this;
		final CaldroidListener listener = new CaldroidListener() {
		    @Override
		    public void onSelectDate(Date date, View view) {
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        int year = cal.get(Calendar.YEAR);
		        int month = cal.get(Calendar.MONTH);
		        int day = cal.get(Calendar.DAY_OF_MONTH);
		        Bundle bundle = new Bundle();
		        bundle.putString("fromUser", fromUser);
		        bundle.putString("toUser", toUser);
		        bundle.putString("year", String.valueOf(year));
		        bundle.putString("month", String.valueOf(month));
		        bundle.putString("day", String.valueOf(day));
		        FragmentManager fragmentManager = cur.getActivity().getSupportFragmentManager();
		        Fragment fragment  = new ScheduleFragment();
				if(fragment != null){
					fragment.setArguments(bundle);
					fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,fragment)
					.hide(cur)
					.addToBackStack(null)
					.commit();
				}
		    }
		};
		this.setCaldroidListener(listener);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fromUser = this.getArguments().getString("fromUser");
		toUser = this.getArguments().getString("toUser");
		setCalendarListener();
		View temp = super.onCreateView(inflater, container, savedInstanceState);
		return temp;
	}
	
	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		// TODO Auto-generated method stub
		
		return new CalendarAdapter(getActivity(), month, year,
				getCaldroidData(), extraData, fromUser, toUser);
	}

}
