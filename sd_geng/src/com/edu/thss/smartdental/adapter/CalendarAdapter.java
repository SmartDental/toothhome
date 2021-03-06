package com.edu.thss.smartdental.adapter;

import hirondelle.date4j.DateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TimeZone;

import com.edu.thss.smartdental.R;
import com.edu.thss.smartdental.db.ScheduleManager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CalendarAdapter extends CaldroidGridAdapter {
	private Context mycontext;
	private String toUser;
	private String fromUser;
	private Integer[] hasSchedule;
	private int mymonth = -1;
	private int myyear = -1;
	public CalendarAdapter(Context context, int month, int year,
			HashMap<String, Object> caldroidData,
			HashMap<String, Object> extraData, String fU, String tU) {
		super(context, month, year, caldroidData, extraData);
		mycontext = context;
		fromUser = fU;
		toUser = tU;
		if (month != mymonth || year != myyear){
			ScheduleManager sm = new ScheduleManager(context);
			hasSchedule = toObject(sm.get_All_days(year, month-1, toUser));
			mymonth = month;
			myyear = year;
		}
	}
	Integer[] toObject(int[] intArray) {
		 
		Integer[] result = new Integer[intArray.length];
		for (int i = 0; i < intArray.length; i++) {
			result[i] = Integer.valueOf(intArray[i]);
		}
		return result;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}

		int topPadding = cellView.getPaddingTop();
		int leftPadding = cellView.getPaddingLeft();
		int bottomPadding = cellView.getPaddingBottom();
		int rightPadding = cellView.getPaddingRight();

		TextView tv1 = (TextView) cellView.findViewById(R.id.tv1);

		tv1.setTextColor(Color.BLACK);

		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);
		Resources resources = context.getResources();

		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			tv1.setTextColor(resources
					.getColor(com.caldroid.R.color.caldroid_darker_gray));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

			tv1.setTextColor(CaldroidFragment.disabledTextColor);
			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.disable_cell);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
			}

			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
			}

		} else {
			shouldResetDiabledView = true;
		}

		// Customize for selected dates
		if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
			if (CaldroidFragment.selectedBackgroundDrawable != -1) {
				cellView.setBackgroundResource(CaldroidFragment.selectedBackgroundDrawable);
			} else {
				cellView.setBackgroundColor(resources
						.getColor(com.caldroid.R.color.caldroid_sky_blue));
			}

			tv1.setTextColor(CaldroidFragment.selectedTextColor);

		} else {
			shouldResetSelectedView = true;
		}

		if (shouldResetDiabledView && shouldResetSelectedView) {
			// Customize for today
			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border);
			} else {
				cellView.setBackgroundResource(com.caldroid.R.drawable.cell_bg);
			}
		}

		tv1.setText("" + dateTime.getDay());
		ImageView star = (ImageView)cellView.findViewById(R.id.img1);
		
		//Integer[] temp = new Integer[]{2,5,6,9,17,22,27};
		if (dateTime.getMonth() == month && Arrays.asList(hasSchedule).contains(dateTime.getDay())){
			star.setVisibility(View.VISIBLE);
			if (dateTime.lt(DateTime.today(TimeZone.getDefault()))){
				star.setAlpha(0.5f);
			}
		}
		// Somehow after setBackgroundResource, the padding collapse.
		// This is to recover the padding
//		cellView.setPadding(0,0,rightPadding,bottomPadding);
//		tv1.setPadding(leftPadding, topPadding, 0, 0);

		// Set custom color if required
		setCustomResources(dateTime, cellView, tv1);

		return cellView;
	}

}
