package com.truphone.miniproject.activities;

import android.os.Bundle;

import com.truphone.miniproject.R;

public class Activity2 extends BaseActivity {

	private static final String TAG = "Activity2";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
        
        loadFragment(R.id.timerfragment2);
    }
}
