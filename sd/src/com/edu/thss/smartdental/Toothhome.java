package com.edu.thss.smartdental;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

public class Toothhome extends Fragment {
	
	Button ok;
   
	private static final int REQUEST_CODE = 0; //������
	
	public Toothhome(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_toothhome, container,false);
		this.ok = (Button) rootView.findViewById(R.id.class_ok);
		
		ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Fragment fragment  = new AppointmentFragment();
				if(fragment != null){
					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
					
//					mDrawerList.setItemChecked(position, true);
//					mDrawerList.setSelection(position);
//					setTitle(mNavMenuTitles[position]);
//					mDrawerLayout.closeDrawer(mDrawerList);
				}
				else{
					Log.e("MainActivity", "Error in creating fragment");
				}
			}});
		
		//intent.putExtra("data",ok.getText() );
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE && resultCode == MyTestActivity.RESULT_CODE){
			String result = data.getExtras().getString("result-data");
		}
	}
}
