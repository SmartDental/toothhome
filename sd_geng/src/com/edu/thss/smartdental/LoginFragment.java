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
		login = (Button)rootView.findViewById(R.id.login);
		user = (EditText)rootView.findViewById(R.id.username);
		password = (EditText)rootView.findViewById(R.id.password);
		  warning = (TextView)rootView.findViewById(R.id.warning);
		warning.setVisibility(View.GONE);
		warning.setText("登陆需要数秒时间，耐心等待，亲~");
	    warning.setVisibility(View.VISIBLE);
		login.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				try {
				    v.postInvalidate();
					userstr = user.getText().toString();
					passwordstr = password.getText().toString();
					LoginManager lm = new LoginManager(userstr, passwordstr, ServerAdd, serverPort, "login");
					int ls = lm.login();
					if (ls == 1){
						RoleId ri = new RoleId();
						ri.readFile("role.txt");
						ri.initCounter(ri.getId());
						role = ri.getRole();  
						changeFragment();
					}
					else if(ls == 0)
					{
						warning.setText("用户名或密码错误");
	                    warning.setVisibility(View.VISIBLE);
					}
					else
					{
						warning.setText("网络连接错误");
					   	warning.setVisibility(View.VISIBLE);
					}
				} catch (Throwable e) {
					   	warning.setText("网络连接错误");
					   	warning.setVisibility(View.VISIBLE);
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
