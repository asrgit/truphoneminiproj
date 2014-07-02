package com.truphone.miniproject.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

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
