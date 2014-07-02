package com.truphone.miniproject.adapter;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.truphone.miniproject.R;
import com.truphone.miniproject.utils.Utils;

/**
 * Class for List Adaptor
 * 
 * @author Ajith
 *
 */

public class EntryListAdapter extends BaseAdapter {

	private static final String TAG = EntryListAdapter.class.getName();
	private Activity iActivity;
	List<String> entries;
	
	public EntryListAdapter(Activity aActivity, List<String> entries) {
		iActivity = aActivity;
		this.entries = entries;
	}
	
	@Override
	public int getCount() {
		if(entries == null) return 0;
		return entries.size(); 
	}

	@Override
	public Object getItem(int aIndex) {
		if(entries == null) return null;
		return entries.get(aIndex);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int aPosition, View aConvertView, ViewGroup aParent) {
		View view = aConvertView;
		if(view == null) {
			view=LayoutInflater.from(iActivity).inflate(R.layout.list_row, null);
		}	
		TextView counterText = (TextView) view.findViewById(R.id.counterText);
		Button button = (Button) view.findViewById(R.id.viewButton);
		Log.i(TAG, "##########(String)getItem(aPosition): " + (String)getItem(aPosition));
		String counterVal = (String)getItem(aPosition);
		counterText.setText(counterVal);
		button.setText(iActivity.getResources().getString(R.string.view_seconds));
		
		//calculate the value in seconds and tag it to the view
		button.setTag(R.id.viewButton, Utils.getSeconds(counterVal));
		button.setOnClickListener(new ButtonClickListener());
		return view;
	}
	
	private class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			String toastText = null;
			if (v.getTag(R.id.viewButton) != null) {
				toastText = new StringBuilder("Seconds: ").append(v.getTag(R.id.viewButton)).toString();
			} else {
				toastText = "Value not found!";
			}
			Toast.makeText(iActivity, toastText, Toast.LENGTH_LONG).show();
		}
	}
}
