/*
 * ×÷Õß£º¹¢ÕýÁØ
 */
package com.edu.thss.smartdental;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class MyTimePicker extends DialogFragment
implements TimePickerDialog.OnTimeSetListener {
	private TextView tvDisplayTime;

	private int hour;
	private int minute;

	public MyTimePicker(TextView tv){
		tvDisplayTime = tv;
	}
@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
	final Calendar c = Calendar.getInstance();
	hour = c.get(Calendar.HOUR_OF_DAY);
	minute = c.get(Calendar.MINUTE);


// Create a new instance of DatePickerDialog and return it
return new TimePickerDialog(getActivity(), this, hour, minute, false);
}

//@Override
//public void onTimeSet(DatePicker view, int year, int month, int day) {
//Calendar c = Calendar.getInstance();
//c.set(year, month, day);
//
//
//
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//String formattedDate = sdf.format(c.getTime());    
//}

@Override
public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
	// TODO Auto-generated method stub
	hour = selectedHour;
	minute = selectedMinute;

	// set current time into textview
	tvDisplayTime.setText(new StringBuilder().append(pad(hour))
			.append(":").append(pad(minute)));

}

private static String pad(int c) {
	if (c >= 10)
		return String.valueOf(c);
	else
		return "0" + String.valueOf(c);
}
}