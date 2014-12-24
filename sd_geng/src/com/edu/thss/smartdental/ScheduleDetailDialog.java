package com.edu.thss.smartdental;
import com.edu.thss.smartdental.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class ScheduleDetailDialog extends DialogFragment{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_schedule_detail, container);
	        getDialog().setTitle("查看日程");

	        TextView description = (TextView)view.findViewById(R.id.detail_description);
	        String d = this.getArguments().getString("description");
	        description.setText(d);
	        description.setWidth(270);
	        return view;
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
