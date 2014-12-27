package com.edu.thss.smartdental;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.edu.thss.smartdental.db.LoginManager;


public class LoginFragment extends Fragment  {
	
	private FragmentManager fm2 = null; 
	private RadioGroup radioGroup;
	private Button login, signin;
	private EditText user, password;
	private String role = null; 
	private TextView warning;
	private String userstr, passwordstr;
	
	private String ServerAdd = "127.0.0.1";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.loginfragment, container,false);
		fm2 = getFragmentManager();
		//changeFragment(0);
		login = (Button)rootView.findViewById(R.id.login);
		signin = (Button)rootView.findViewById(R.id.signin);
		user = (EditText)rootView.findViewById(R.id.username);
		password = (EditText)rootView.findViewById(R.id.password);
		warning = (TextView)rootView.findViewById(R.id.warning);
		warning.setVisibility(View.GONE);
		
		userstr = user.getText().toString();
		passwordstr = password.getText().toString();
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					LoginManager lm = new LoginManager(userstr, passwordstr, ServerAdd, 8888, "login");
					if (lm.login()){
						role = lm.getReply();
						changeFragment();
					}
					else
					{
						changeFragment();
					}
				} catch (Throwable e) {
					changeFragment();
					;
				}
			}
		});
		
		signin.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					LoginManager lm = new LoginManager(userstr, passwordstr, ServerAdd, 8888, "signin");
					if (lm.login())
					{
						role = lm.getReply();
						changeFragment();
					}
				} catch (Throwable e) {
					;
				}
				
			}
		});
		return rootView;
	}
	
	private void changeFragment(){
		FragmentTransaction transaction = fm2.beginTransaction();
		Fragment tempfragment = new Toothhome();
		Bundle bundle = new Bundle();
		bundle.putString("fromUser", role);
		tempfragment.setArguments(bundle);
		if(tempfragment != null){
        	transaction.replace(R.id.content_frame, tempfragment);
        	transaction.commit();
        }
	}


}
