/*
 * 作者：陈章政 孙浩 王安琪 三人结对
 */
package com.edu.thss.smartdental;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CaseHistory extends Fragment {
	
	TextView info;
	TextView schedule;
	static int RESULT_CODE = 1;
	private FragmentActivity ta = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ta = getActivity();
		View rootView = inflater.inflate(R.layout.case_history, container,false);
		info = (TextView)rootView.findViewById(R.id.message);
		schedule = (TextView)rootView.findViewById(R.id.calendar);
		info.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View v) {  
	                getActivity()  
	                        .getSupportFragmentManager()  
	                        .beginTransaction()  
	                        .replace(R.id.test_activity, new CheckInfo(),  
	                                "Fragmenttest2")  
	                        .commit();  
	  
	            }  
	        });
		schedule.setOnClickListener(new OnClickListener() {  
			  
            @Override  
            public void onClick(View v) {  
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
				        Fragment fragment  = new ScheduleFragment();
						if(fragment != null){
							fragment.setArguments(bundle);
							FragmentManager fragmentManager = ta.getSupportFragmentManager();
							fragmentManager.beginTransaction().replace(R.id.test_activity,fragment).commit();
						}
				    }

				};

				caldroidFragment.setCaldroidListener(listener);

				FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
				t.replace(R.id.test_activity, caldroidFragment);
				t.commit();
            }  
        });
		return rootView;
		
	}
	@Override  
    public void onActivityCreated(Bundle savedInstanceState) {  
        super.onActivityCreated(savedInstanceState);  
  
    }  
  
    @Override  
    public void onAttach(Activity activity) {  
      
        super.onAttach(activity);  
    }  
  
    @Override  
    public void onDestroy() {  
        
        super.onDestroy();  
    }  
  
    @Override  
    public void onDestroyView() {  
       
        super.onDestroyView();  
    }  
  
    @Override  
    public void onPause() {  
        
        super.onPause();  
    }  
  
    @Override  
    public void onResume() {  
      
        super.onResume();  
    }  
  
    @Override  
    public void onStart() {  
        
        super.onStart();  
    }  
  
    @Override  
    public void onStop() {  
         
        super.onStop();  
    }  
  
    @Override  
    public void onDetach() {  
      
        super.onDetach();  
    }  	
}
