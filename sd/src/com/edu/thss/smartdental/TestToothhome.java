/*
 * ×÷Õß£ºÅíÓÑ
 */
package com.edu.thss.smartdental;

import android.os.Bundle;  
import android.support.v4.app.FragmentActivity;  
import android.support.v4.app.FragmentManager;  
import android.support.v4.app.FragmentTransaction;  
import android.view.Menu;
import android.view.MenuItem;

public class TestToothhome extends FragmentActivity {
	public FragmentManager fragmentManager;  
	public FragmentTransaction fragmentTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setTitle("233");
		setContentView(R.layout.activity_test_toothhome); 
        fragmentManager = getSupportFragmentManager();  
        fragmentTransaction = fragmentManager.beginTransaction();  
  
        CheckInfo fragmentTest1 = new CheckInfo();  
  
        fragmentTransaction.replace(R.id.test_activity, fragmentTest1,  
                "fragmentTest1");  
  
        fragmentTransaction.commit();  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_toothhome, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	   @Override  
	    public void onBackPressed() {  
	      
	            super.onBackPressed();  
	  
	    }  
}
