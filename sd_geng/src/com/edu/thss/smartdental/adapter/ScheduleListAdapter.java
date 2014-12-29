package com.edu.thss.smartdental.adapter;

import com.edu.thss.smartdental.R;
import com.edu.thss.smartdental.ScheduleDetailDialog;
import com.edu.thss.smartdental.ScheduleDetailFragment;
import com.edu.thss.smartdental.ScheduleFragment;
import com.edu.thss.smartdental.TestToothhome;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import android.widget.Button;
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
import com.nhaarman.listviewanimations.ArrayAdapter;

public class ScheduleListAdapter extends ArrayAdapter<ScheduleElement> {
	
	private FragmentActivity mContext;
	private Fragment cur;
	private FragmentTransaction ft;
	private String fromUser;
	private String toUser;

	public ScheduleListAdapter(Fragment f,
			final ArrayList<ScheduleElement> items, String fU, String tU) {
		super(items);
		mContext = f.getActivity();
		cur = f;
		fromUser = fU;
		toUser = tU;
	}
	
	
	private String getLen2(String s){
		while(s.length() < 2){
			s = '0' + s;
		}
		return s;
	}

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return getItem(arg0).hashCode();
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        if(arg1==null)
        {
            arg1 = LayoutInflater.from(mContext).inflate(R.layout.schedule_item, arg2,false);
        }
        final ScheduleElement temp = getItem(arg0);
        
        setView(arg1, temp);
        setEditButton(arg1, temp);    
        return arg1;
    }
    
    void setView(View item, ScheduleElement se){
        TextView hour = (TextView)item.findViewById(R.id.hour);
        TextView min = (TextView)item.findViewById(R.id.min);
        TextView name = (TextView)item.findViewById(R.id.name);
        Calendar c = Calendar.getInstance(); 
        c.setTime(se.alertTime);
        int hourInt = c.get(Calendar.HOUR_OF_DAY);
        int minInt = c.get(Calendar.MINUTE);
        TextView tag = (TextView)item.findViewById(R.id.schedule_tag);
        String tagStr = "上午";
        if (hourInt < 6){
        	tagStr = "凌晨";
        }
        else if (hourInt < 12){
        	tagStr = "上午";
        }
        else if (hourInt < 18){
        	tagStr = "下午";
        }
        else{
        	tagStr = "晚上";
        }
        tag.setText(tagStr);
        Date d = se.alertTime;
        c.setTime(d);
        hour.setText(getLen2(Integer.toString(hourInt)));
        min.setText(':' + getLen2(Integer.toString(minInt)));
        name.setText(se.name);
        ImageView fu = (ImageView)item.findViewById(R.id.fromUser);
        String fromUser = se.fromUser;
        if (fromUser.equals("mom")){
        	fu.setImageResource(R.drawable.mom);
        }
        else if (fromUser.equals("child")){
        	fu.setImageResource(R.drawable.child);
        }
        else{
        	fu.setImageResource(R.drawable.dad);
        }
    }
    
    void showInfoDialog(final ScheduleElement se){
    	Bundle bundle = new Bundle();
    	bundle.putString("description", se.description);
      
  		FragmentManager fm = mContext.getSupportFragmentManager();
  		ScheduleDetailDialog infoDialog = new ScheduleDetailDialog();
  		infoDialog.setArguments(bundle);
  		infoDialog.show(fm, "fragment_schedule_detail");    
    }
    
    void setEditButton(View arg1, final ScheduleElement se){
      final Button edit = (Button)arg1.findViewById(R.id.schedule_info);
      edit.setOnClickListener(new OnClickListener() {
		   
		   @Override
		   public void onClick(View arg0) {
			   showInfoDialog(se);
		   }
		});
    }
    
    @Override
    public boolean hasStableIds() {
      return true;
    }

}
