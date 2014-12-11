/*
 * ×÷Õß£º¹¢ÕýÁØ
 */
package com.edu.thss.smartdental;

import com.edu.thss.smartdental.adapter.CalendarAdapter;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

public class CalendarFragment extends CaldroidFragment {

	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		// TODO Auto-generated method stub
		return new CalendarAdapter(getActivity(), month, year,
				getCaldroidData(), extraData);
	}

}
