package com.truphone.miniproject.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.truphone.miniproject.R;
import com.truphone.miniproject.data.CountGenerator;
import com.truphone.miniproject.fragments.Callback;
import com.truphone.miniproject.fragments.ContentFragment;

public abstract class BaseActivity extends FragmentActivity implements Callback {

	private static final String TAG = "BaseActivity";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		CountGenerator.getInstance().setContext(this);
	    CountGenerator.getInstance().startTicking();		
	}
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		CountGenerator.getInstance().stopTicking();
	}

	protected void loadFragment(int targetResid) {

		// get an instance of FragmentTransaction from your Activity
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		Fragment cFragment = ContentFragment.newInstance();
		fragmentTransaction.replace(targetResid, cFragment);		
    	fragmentTransaction.commit();
		Log.d(TAG, "fragment loaded");
    }

	@Override
	public void onAttach(Fragment fragment) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDetach(Fragment fragment) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(Fragment fragment, View view) {
		Log.d(TAG, "hello.. clicked");
		switch(view.getId()) {
			case R.id.exit_button: 
				Log.d(TAG, "closing activity and quitting app..");
				finish();
				break;
			case R.id.switch_activity_button:
				showActivityChooserDialog();
				Log.d(TAG, "Switch activity..");
				break;
		}		
	}
	
	private void showActivityChooserDialog() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		String items[] = new String[] {"Activity1", "Activity2", "Activity3", "Activity4"};
		int selIndex = 0;
		Toast.makeText(this, "Class name: " + this.getClass().getSimpleName(),
				Toast.LENGTH_LONG).show();

		for(int i=0; i<items.length; i++) {
			if(items[i].equals(this.getClass().getSimpleName())) {
				selIndex = i;
				break;
			}
		}
		
		adb.setSingleChoiceItems(items, selIndex, new OnClickListener() {

		        @Override
		        public void onClick(DialogInterface d, int n) {
		        	Intent i = null;
		        	Class targetClass = null;
		        	switch(n){
		        		case 0:
		        			targetClass = Activity1.class;
		        			break;
		        		case 1: 
		        			targetClass = Activity2.class;
		        			break;
		        		case 2:
		        			targetClass = Activity3.class;
		        			break;
		        		case 3:
		        			targetClass = Activity4.class;
		        			break;
		        	}

			        //Launch new activity if only the selection is not for the current activity.
			        if(BaseActivity.this.getClass() != targetClass) {
			        	i = new Intent(BaseActivity.this, targetClass);
			        	BaseActivity.this.finish();
			        	startActivity(i);
			        }
		        	d.dismiss();
		        }
		});
		adb.setNegativeButton("Cancel", null);
		adb.setTitle("Select an activity");
		adb.show();		
	}
    
}
