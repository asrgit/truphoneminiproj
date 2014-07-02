package com.truphone.miniproject.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Base class for fragments to notify parent activity when click event is performed on
 * a view in the fragment layout
 * 
 * @author Ajith 
 *
 */
public class BaseFragment extends Fragment {

	/** 
	 * Activity callback interface
	 *
	 */
	public interface Callback {
		/**
		 * Notify fragment is attached to parent activity
		 */
		void onAttach(Fragment fragment);
		
		/**
		 * Notify fragment is detached to parent activity
		 */
		void onDetach(Fragment fragment);
		
		/**
		 * Notify that back button has been clicked
		 */
		void onClick(Fragment fragment, View view);
	}

	/**
	 * Listener interface to send notification to parent activity
	 */
	protected Callback listener;

	public BaseFragment() {
		super();
	}

	/**
	 * Attaches callback listening mechanism to derived fragment
	 * @param activity parent activity that is interested in notifications. 
	 * The activity must implement {@link Callback} interface to receive notification  
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof Callback) {
			listener = (Callback) activity;
			listener.onAttach(this);
		}
	}

	/**
	 * Unsubscribe from notifications 
	 */
	@Override
	public void onDetach() {
		super.onDetach();
		listener.onDetach(this);
		listener = null;
	}
}