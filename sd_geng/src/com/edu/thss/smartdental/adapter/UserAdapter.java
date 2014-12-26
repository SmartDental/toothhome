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
import java.util.HashMap;
import java.util.List;

import com.edu.thss.smartdental.db.ScheduleManager;
import com.edu.thss.smartdental.model.ScheduleElement;

public class UserAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	private FragmentActivity fa = null;
	private List<String> userList = null;
	private Date date = null;
	private String fromUser;
	private HashMap<String, String> userName;
	private HashMap<String, Integer> userPic;
	
	public UserAdapter(FragmentActivity f, String fU){
		fa = f;
		inflater = LayoutInflater.from(fa);
		userList = new ArrayList<String>();
		fromUser = fU;
		userName = new HashMap<String, String>();
		userPic = new HashMap<String, Integer>();
		if (fU.equals("mom")){
			userName.put("mom", "��");
			userName.put("dad", "�Ϲ�");
			userName.put("child", "����");
			userList.add("mom");
			userList.add("dad");
			userList.add("child");
		}
		else if (fU.equals("child")){
			userName.put("mom", "����");
			userName.put("dad", "�ְ�");
			userName.put("child", "��");
			userList.add("child");
			userList.add("mom");
			userList.add("dad");
		}
		else{
			userName.put("dad", "��");
			userName.put("mom", "����");
			userName.put("child", "����");
			userList.add("dad");
			userList.add("mom");
			userList.add("child");
		}
		userPic.put("dad", R.drawable.dad);
		userPic.put("mom", R.drawable.mom);
		userPic.put("child", R.drawable.child);
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
        username.setText(userName.get(u));
        userpic.setImageResource(userPic.get(u));

      return arg1;
    }

}
