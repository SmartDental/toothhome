package com.edu.thss.smartdental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class Toothhome extends Fragment {
	
	LinearLayout ok;
   
	private static final int REQUEST_CODE = 0; //ÇëÇóÂë

	
	public Toothhome(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_toothhome, container,false);
		this.ok = (LinearLayout) rootView.findViewById(R.id.class_ok);
		
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(),TestToothhome.class);
				startActivity(intent);
				
			}});
		
		//intent.putExtra("data",ok.getText() );
		return rootView;
	}
	
}