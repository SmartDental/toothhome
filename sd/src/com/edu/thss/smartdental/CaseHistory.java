package com.edu.thss.smartdental;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
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
                getActivity()  
                        .getSupportFragmentManager()  
                        .beginTransaction()  
                        .replace(R.id.test_activity, new Schedule(),  
                                "Fragmenttest2")  
                        .commit();  
  
            }  
        });
		return rootView;
		
	}
}
