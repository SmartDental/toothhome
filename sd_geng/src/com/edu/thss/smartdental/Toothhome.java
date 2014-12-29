package com.edu.thss.smartdental;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.edu.thss.smartdental.adapter.UserAdapter;
import com.roomorama.caldroid.CaldroidFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Toothhome extends Fragment {
	
	private Fragment cur = this;
	private String fromUser;
	private String toUser;
	
	void setUserList(){
		
	}
	
	void jumpToCalendar(){
		
	}
	
	void setCalendarListener(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_user, container,false);
		fromUser = this.getArguments().getString("fromUser");
		final UserAdapter ua = new UserAdapter(getActivity(), fromUser);
		ListView scheduleList = (ListView)rootView.findViewById(R.id.userList);
		scheduleList.setAdapter(ua);

		scheduleList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	
            	FragmentManager fm = getActivity().getSupportFragmentManager();
            	toUser = ua.getItem(arg2);
            	CalendarFragment caldroidFragment = new CalendarFragment();
				Bundle args = new Bundle();
				Calendar cal = Calendar.getInstance();
				args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
				args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
				args.putString("fromUser", fromUser);
				args.putString("toUser",toUser);
				caldroidFragment.setArguments(args);
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