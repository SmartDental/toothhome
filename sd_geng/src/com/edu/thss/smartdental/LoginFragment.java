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
	private int serverPort = 8888;
	private String ServerAdd = "59.66.137.106";

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
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					userstr = user.getText().toString();
					passwordstr = password.getText().toString();
					LoginManager lm = new LoginManager(userstr, passwordstr, ServerAdd, serverPort, "login");
					if (lm.login()){
						role = lm.getReply();
						changeFragment();
					}
					else
					{
						warning.setText("用户名或密码错误。");
	                    warning.setVisibility(View.VISIBLE);
					}
				} catch (Throwable e) {
					   	warning.setText("网络连接错误。");
					   	warning.setVisibility(View.VISIBLE);
					   	role = "dad";
					   	changeFragment();
				}
			}
		});
		
		signin.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
					LoginManager lm = new LoginManager(userstr, passwordstr, ServerAdd, 8888, "signin");
					if (lm.login())
					{
						RoleId ri = new RoleId();
						role = ri.getRole();
						
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
		Client ct = new Client(getActivity());
		ct.isConnected();
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
