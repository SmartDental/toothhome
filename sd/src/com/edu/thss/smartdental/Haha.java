package com.edu.thss.smartdental;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Haha extends android.support.v4.app.Fragment {

	private static final String TAG = "FragmentTest1---------";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println(TAG + "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.member_schedule, null);
//		Button jumpBtn = (Button) view.findViewById(R.id.btn_jump);
//		jumpBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				getActivity()
//						.getSupportFragmentManager()
//						.beginTransaction()
//						.replace(R.id.fragment_main, new Fragmenttest2(),
//								"Fragmenttest2")
//						.addToBackStack("Fragmenttest2").commit();
//
//			}
//		});
		return view;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		System.out.println(TAG + "onActivityCreated");
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		System.out.println(TAG + "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onDestroy() {
		System.out.println(TAG + "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		System.out.println(TAG + "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onPause() {
		System.out.println(TAG + "onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		System.out.println(TAG + "onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		System.out.println(TAG + "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		System.out.println(TAG + "onStop");
		super.onStop();
	}

	@Override
	public void onDetach() {
		System.out.println(TAG + "onDetach");
		super.onDetach();
	}

}
