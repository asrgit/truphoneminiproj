package com.truphone.miniproject.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.truphone.miniproject.R;
import com.truphone.miniproject.adapter.EntryListAdapter;
import com.truphone.miniproject.data.CountGenerator;

public class Activity4 extends BaseActivity {

	private static final String TAG = "Activity4";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity4);
        
        loadFragment(R.id.timerfragment4);
        
      List<String> entries = CountGenerator.getInstance().getEntriesList();
//        List<String> entries = new ArrayList<String>();
        
        EntryListAdapter listviewAdapter = new EntryListAdapter(this, entries);
        ListView listView = (ListView) findViewById(R.id.counterList);
        listView.setAdapter(listviewAdapter);
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
}
