package com.edu.thss.smartdental;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.edu.thss.smartdental.CalendarFunction;
import com.edu.thss.smartdental.AppointmentFragment.calendarItemClickListener;
import com.edu.thss.smartdental.model.ScheduleElement;
import com.edu.thss.smartdental.ui.calendar.CalendarView;
import com.edu.thss.smartdental.adapter.ScheduleListAdapter;
import com.edu.thss.smartdental.db.ScheduleManager;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.CountDownFormatter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.contextualundo.ContextualUndoAdapter.DeleteItemCallback;
import com.nhaarman.listviewanimations.swinginadapters.prepared.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.widget.DynamicListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScheduleFragment extends Fragment implements OnDismissCallback, DeleteItemCallback{
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private ScheduleManager sm = null;
	private ScheduleListAdapter mAdapter;
	private DynamicListView scheduleList;
	private View rootView;
	private Fragment cur = this;
	private String fromUser;
	private String toUser;

	void setDate(){
		String y = this.getArguments().getString("year");
		String m = this.getArguments().getString("month");
		String d = this.getArguments().getString("day");
		year = Integer.parseInt(y);
		month = Integer.parseInt(m);
		day = Integer.parseInt(d);		
	}
	
	Date getDate(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DAY_OF_MONTH,day);
		return  cal.getTime();
	}
	
	@SuppressWarnings("rawtypes")
	void setListView(){
		@SuppressWarnings("unchecked")
		ArrayList<ScheduleElement> se = new ArrayList(Arrays.asList(sm.ScheduleList(getDate(), toUser)));
		mAdapter = new ScheduleListAdapter(this, se, fromUser, toUser);
		scheduleList = (DynamicListView)rootView.findViewById(R.id.listView);
		scheduleList.setAdapter(mAdapter);
		setAdapters();
		setItemClickListner();
	}
	
	void setAdapters(){
		setAnimAdapter();
		setContextualUndoAdapter();
	}
	
	
	void setAnimAdapter(){
		AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(mAdapter);
		//animAdapter.setInitialDelayMillis(100);
		animAdapter.setAbsListView(scheduleList);
		scheduleList.setAdapter(animAdapter);
		//setAnimEvent(scheduleList, animAdapter);
	}
	
	void setContextualUndoAdapter() {
        ContextualUndoAdapter adapter = new ContextualUndoAdapter(mAdapter, R.layout.item_undo_row, R.id.undo_row_undobutton, this);
        adapter.setAbsListView(scheduleList);
        scheduleList.setAdapter(adapter);
    }
	
	void setAnimEvent(DynamicListView scheduleList, final AlphaInAnimationAdapter animAdapter){
		scheduleList.setOnItemMovedListener(new DynamicListView.OnItemMovedListener() {
            @Override
            public void onItemMoved(final int newPosition) {
                
            }
        });
	}
	
	Bundle setEditInfo(final ScheduleElement se){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(se.alertTime);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String hour = (Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        String min = (Integer.toString(cal.get(Calendar.MINUTE)));
    	Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(se.id));
        bundle.putString("action", "edit");
        bundle.putString("name", se.name);
        bundle.putString("time", hour+':'+min);
        bundle.putString("description", se.description);
        bundle.putString("year", String.valueOf(year));
        bundle.putString("month", String.valueOf(month));
        bundle.putString("day", String.valueOf(day));
        bundle.putString("fromUser", fromUser);
        bundle.putString("toUser", toUser);
        return bundle;
    }
    
    public void jumpToEdit(final ScheduleElement se){
    	Fragment fragment  = new ScheduleDetailFragment();
    	if(fragment != null){
    		FragmentManager fm = getActivity().getSupportFragmentManager();
    		fm.enableDebugLogging(true);
    	   fragment.setArguments(setEditInfo(se));
    	        		fm.beginTransaction()
    	        		.replace(R.id.content_frame, fragment)
    	        		.hide(cur)
    	        		.addToBackStack(null)
    	        		.commit();
    	}
    }
    
	void setItemClickListner(){
		scheduleList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
            	ScheduleElement tse = mAdapter.getItem(arg2);
            	jumpToEdit(tse);
            }
        });
	}
	
	Bundle getAddInfo(){
	  Bundle bundle = new Bundle();
      bundle.putString("action", "add");
      bundle.putString("year", String.valueOf(year));
      bundle.putString("month", String.valueOf(month));
      bundle.putString("day", String.valueOf(day));
      bundle.putString("fromUser", fromUser);
      bundle.putString("toUser", toUser);
      return bundle;
	}
	
	void jumpToAdd(){
		Fragment fragment  = new ScheduleDetailFragment();
		if(fragment != null){
			fragment.setArguments(getAddInfo());
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			fragmentManager.beginTransaction()
			.hide(cur)
			.replace(R.id.content_frame,fragment)
			.addToBackStack(null)
			.commit();
		}
	}
	
	void setAddButton(){
		final ImageView add = (ImageView)rootView.findViewById(R.id.schedule_add);
		add.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                switch (arg1.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    add.setAlpha(1.0f);
                    break;
                }
                case MotionEvent.ACTION_UP:{
                	add.setAlpha(0.5f);
                	jumpToAdd();
                	//jiazai
                    break;
                }
                }
                return true;
            }
        });
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setDate();
		fromUser = this.getArguments().getString("fromUser");
		toUser = this.getArguments().getString("toUser");
		rootView = inflater.inflate(R.layout.fragment_schedule, container,false);
		rootView.setFocusable(true);//这个和下面的这个命令必须要设置了，才能监听back事件。  
		rootView.setFocusableInTouchMode(true);  
		rootView.setOnKeyListener(backlistener);  
		sm = new ScheduleManager(getActivity());
		setAddButton();
		setListView();
		return rootView;
	}

    @Override
    public void onDismiss(final AbsListView listView, final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            mAdapter.remove(position);
        }
        Toast.makeText(getActivity(), "Removed positions: " + Arrays.toString(reverseSortedPositions), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteItem(final int position) {
        //jiazai
        ScheduleElement se = mAdapter.getItem(position);
        if(CalendarFunction.send(se, "del"))
        {
        	ScheduleManager manager = new ScheduleManager(getActivity());
        	manager.deleteSchedule(se.id);
        }
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }
    
    
	private View.OnKeyListener backlistener = new View.OnKeyListener() {
		  
        @Override  
        public boolean onKey(View view, int i, KeyEvent keyEvent) {  
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {  
                if (i == KeyEvent.KEYCODE_BACK) {  //表示按返回键 时的操作  
                	Fragment fragment  = new Toothhome();
                	FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                	fragmentManager.beginTransaction()
					.hide(cur)
					.replace(R.id.content_frame,fragment)
					.commit();
                    return false;    //已处理  
                }  
            }  
            return false;  
        }  
	};
}
