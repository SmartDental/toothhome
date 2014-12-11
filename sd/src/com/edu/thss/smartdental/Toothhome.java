/*
 * 修改者：陈章政 孙浩 王安琪 三人结对
 */
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
	LinearLayout ok1;
	LinearLayout ok2;
   
	private static final int REQUEST_CODE = 0; //ÇëÇóÂë

	
	public Toothhome(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_toothhome, container,false);
		this.ok = (LinearLayout) rootView.findViewById(R.id.class_ok);
		this.ok1 = (LinearLayout) rootView.findViewById(R.id.class_ok_l1);
		this.ok2 = (LinearLayout) rootView.findViewById(R.id.class_ok_l2);
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(),TestToothhome.class);
				startActivity(intent);
				
			}});
		ok1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(),TestToothhome.class);
				startActivity(intent);
				
			}});
		ok2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(getActivity(),TestToothhome.class);
				startActivity(intent);
				
			}});
		
		//intent.putExtra("data",ok.getText() );
		return rootView;
	}
	
}

