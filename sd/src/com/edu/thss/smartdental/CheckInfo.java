package com.edu.thss.smartdental;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CheckInfo extends Fragment {
	
	TextView casehistory;
	TextView schedule;
	static int RESULT_CODE = 1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.checkinfo, container,false); 
		casehistory = (TextView)rootView.findViewById(R.id.caseHistory);
		schedule = (TextView)rootView.findViewById(R.id.calendar);
		casehistory.setOnClickListener(new OnClickListener() {  
	  
	            @Override  
	            public void onClick(View v) {  
	                getActivity()  
	                        .getSupportFragmentManager()  
	                        .beginTransaction()  
	                        .replace(R.id.test_activity, new CaseHistory(),  
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
