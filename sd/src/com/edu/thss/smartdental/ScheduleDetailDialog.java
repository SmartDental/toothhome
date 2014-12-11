/*
 * 作者：耿正霖
 */
package com.edu.thss.smartdental;
import com.edu.thss.smartdental.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

public class ScheduleDetailDialog extends DialogFragment{
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.fragment_schedule_detail, container);
	        getDialog().setTitle("查看日程");
	        TextView name = (TextView)view.findViewById(R.id.detail_name);
	        TextView description = (TextView)view.findViewById(R.id.detail_description);
	        TextView time = (TextView)view.findViewById(R.id.detail_time);
	        String n = this.getArguments().getString("name");
	        String d = this.getArguments().getString("description");
	        String t = this.getArguments().getString("time");
	        name.setText(n);
	        description.setText(d);
	        time.setText(t);
	        
	        description.setWidth(270);

	        return view;
	    }
}
