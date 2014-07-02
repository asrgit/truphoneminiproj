package com.truphone.miniproject.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CountGenerator {

	private static final String TAG = "CountGenerator";
	private static final int MILLIS = 1000;
	
	private static CountGenerator INSTANCE = null;
	private int counterHour = 0;
	private int counterMin = 0;
	private int counterSec = 0;
	private List<NotifyCounter> listeners = new ArrayList<NotifyCounter>();
	
	Context context;
	DatabaseHandler dbHandler;
	FileHandler fHandler;
	
	private CountGenerator(){
		dbHandler = new DatabaseHandler(null);
		fHandler = new FileHandler();
//		if(fHandler.readFromFile() == null) {
//			fHandler.writeToFile("00:00:00s");
//		}
	}
	
	public static synchronized final CountGenerator getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new CountGenerator();
		}
		return INSTANCE;
	}
	
	public void setContext(Context c) {
		context = c;
		fHandler.setContext(c);
	}
	
	public void addListener(NotifyCounter listener) {
		listeners.add(listener);
	}
	
	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
		
			String counterString = getCounterString();
			// notify listeners; UI and store the value in database
			for (NotifyCounter l : listeners) {
				l.update(counterString);
			}
//			dbHandler.insertNormal(counterString);
			fHandler.writeToFile(counterString);
		}
	};
		
	private String getCounterString() {
		StringBuilder sb = new StringBuilder();
		if(counterSec < 10) {
			sb.append(0);
		}
		sb.append(counterSec).append("s");
		
		if (counterMin > 0) {
			sb.insert(0, ":");
			sb.insert(0, counterMin);
			if (counterHour > 0) {
				sb.insert(0, ":");
				sb.insert(0, counterHour);
			}
		}
		return sb.toString();
	}

	// parse counter string in the format hh:mm:sss and loads the values in
	// three int variables that represents hh, mm and ss
	// eg: 19:21:21s will be stored as values 19, 21 and 21 in three integer
	// variables
	private void parseCounterString(String counterString) {
		//format xx:xx:xxs or xx:xxs
		if(counterString.indexOf(":") != -1) {
			StringTokenizer st =  new StringTokenizer(counterString, ":");
			if (st.hasMoreElements()) {
				int tokenCount = st.countTokens();
				/**
				 * Switch for the count, if count is 3 all cases from 3 to 1
				 * will be handled, if count is 2 all cases from 2 to 1 and so
				 * on.
				 */
				switch(tokenCount) {
					case 3: 
						counterHour = Integer.parseInt(st.nextToken());
					case 2:
						counterMin = Integer.parseInt(st.nextToken());
					case 1:
						String secsString = st.nextToken(); 
						secsString = secsString.substring(0, secsString.indexOf("s"));
						counterSec = Integer.parseInt(secsString);
						break;
				}
			}
		} else {//format: xxs
			counterSec = Integer.parseInt(counterString.substring(0, counterString.length()-1));
		}
		Log.i(TAG, "counterHour: " + counterHour + " CounterMin: " + counterMin  + " CounterSec: " + counterSec);
	}
	
	/**
	 * Increments the hh, mm, ss fields as required
	 */
	private void incrementCounter() {
		counterSec++;
		if(counterSec > 59) {
			counterSec = 0;
			counterMin++;
			if(counterMin > 59) {
				counterMin = 0;
				counterHour++;
			}
		}
		Log.i(TAG, "counterHour: " + counterHour + " CounterMin: " + counterMin  + " CounterSec: " + counterSec);
	}

	private Runnable runnableTick = new Runnable() {
		@Override
		public void run() {
			incrementCounter();
			handler.sendEmptyMessage(0);
			handler.postDelayed(this, MILLIS);
		}
	};
	
	/**
	 * This method starts the counter. Looks in the database if the last value is available.
	 * If found, increment from there, else starts from 0.
	 */
	public void startTicking() {
		//read from database. if there is an entry, keep it as the base, else start with 0.
		String lastCounter = fHandler.getLastEntry(); //null;// new DatabaseHandler(null).getLastCounterEntry();
		Log.i(TAG, "############lastCounter:" + lastCounter);
		if(lastCounter==null) {
			lastCounter = "00s";
		}
		parseCounterString(lastCounter);
		incrementCounter();
		handler.post(runnableTick);
	}
	
	/**
	 * This method stops the running counter.
	 */
	public void stopTicking() {
		handler.removeCallbacks(runnableTick);
	}
	
	public List<String> getEntriesList() {
		return fHandler.getEntryList();
	}
}
