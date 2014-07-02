package com.truphone.miniproject.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.truphone.miniproject.R;
import com.truphone.miniproject.data.CountGenerator;
import com.truphone.miniproject.data.NotifyCounter;

public class ContentFragment extends Fragment implements NotifyCounter {

	private static final String TAG = "ContentFragment";
	
	private Callback listener;
	private TextView counter;
	private TextView revCounter;
	private Button exit;
	private Button switchActivity;
	
	public static ContentFragment newInstance() {
		ContentFragment f = new ContentFragment();
		Bundle args = new Bundle();
////		args.putParcelable(EXTRA_PERSON_MODEL, detailsObj);
//		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_content, null);
		//register activity as listener
		Activity activity = getActivity();
		if(activity instanceof Callback) {
			listener = (Callback)activity;
		}
		CountGenerator.getInstance().addListener(this);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		counter = (TextView) getView().findViewById(R.id.counter_text);
		revCounter = (TextView) getView().findViewById(R.id.counter_text_reverse);
		switchActivity = (Button) getView().findViewById(R.id.switch_activity_button);
		exit = (Button) getView().findViewById(R.id.exit_button);
		
		addListeners();
	}	

	private void addListeners() {
		OnClickListener buttonClickListener = new ButtonClickListener();
		exit.setOnClickListener(buttonClickListener);
		switchActivity.setOnClickListener(buttonClickListener);
	}
	
	/**
	 * Update UI with new counter value
	 */
	public void update(String counterString) {
		counter.setText(counterString);
		revCounter.setText(new StringBuffer(counterString).reverse().toString());
	}
	
	class ButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			listener.onClick(ContentFragment.this, view);
		}
	}
}

