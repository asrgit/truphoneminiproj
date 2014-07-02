package com.truphone.miniproject.activities;

import android.os.Bundle;

import com.truphone.miniproject.R;

public class Activity1 extends BaseActivity {

	private static final String TAG = "Activity1";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity1);
        
        loadFragment(R.id.timerfragment1);
    }
}
