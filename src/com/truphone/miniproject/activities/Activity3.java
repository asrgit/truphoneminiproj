package com.truphone.miniproject.activities;

import android.os.Bundle;

import com.truphone.miniproject.R;

public class Activity3 extends BaseActivity {

	private static final String TAG = "Activity3";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);
        
        loadFragment(R.id.timerfragment3);
    }
}
