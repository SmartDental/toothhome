package com.edu.thss.smartdental.adapter;

import com.edu.thss.smartdental.R;
import com.edu.thss.smartdental.ScheduleDetailFragment;
import com.edu.thss.smartdental.ScheduleFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.db.ScheduleManager;
import com.edu.thss.smartdental.model.ScheduleElement;

public class UserAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private FragmentActivity fa = null;
	private List<String> userList = null;
	private Date date = null;
	
	public UserAdapter(FragmentActivity f){
		fa = f;
		inflater = LayoutInflater.from(fa);
		userList = new ArrayList<String>();
		userList.add("dad");
		userList.add("mom");
		userList.add("child");
	}

	
	@Override
    public int getCount() {
        // TODO Auto-generated method stub
        return userList.size();
    }

    @Override
    public String getItem(int arg0) {
        // TODO Auto-generated method stub
        return userList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            arg1 = inflater.inflate(R.layout.user_cell, arg2,false);
        }
        String u = getItem(arg0);
        TextView username = (TextView)arg1.findViewById(R.id.userName);
        ImageView userpic= (ImageView)arg1.findViewById(R.id.user_pic);
        String name = "";
        if (u.equals("mom")){
        	username.setText("����");
        	userpic.setImageResource(R.drawable.mom);
        }
        else if (u.equals("child")){
        	username.setText("����");
        	userpic.setImageResource(R.drawable.child);
        }
        else{
        	username.setText("�ְ�");
        	userpic.setImageResource(R.drawable.dad);
        }
      return arg1;
    }

}
