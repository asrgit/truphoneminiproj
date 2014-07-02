package com.truphone.miniproject.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;

public class FileHandler {
	
	private static final String TAG = "FileHandler";
	private static final String FILENAME = "CounterEntries";

	private File file;
	private Context context;
	
	public FileHandler() {
    	file = new File(FILENAME);
    	
	}

	public void setContext(Context c) {
		context = c;
	}
	
	public void writeToFile(String data) {
		Log.i(TAG, "write to file: " + data);
		try {
			FileOutputStream fop = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fop);
			outputStreamWriter.write(data+"#");
			outputStreamWriter.close();
			fop.close();
		} catch (IOException e) {
			Log.e(TAG, "File write failed: " + e.toString());
		}

	}
	 
	public String readFromFile() {

		String ret = "";
		try {
			InputStream inputStream = context.openFileInput(FILENAME);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}

				inputStream.close();
				ret = stringBuilder.toString();
			}
		} catch (FileNotFoundException e) {
			Log.e(TAG, "File not found: " + e.toString());
			return null;
		} catch (IOException e) {
			Log.e(TAG, "Can not read file: " + e.toString());
			return null;
		}

		return ret;
	}
	
	public String getLastEntry() {
		List<String> entries = getEntryList();
		if(entries == null) return null;
		return entries.get(entries.size() - 1);
	}

	public List<String> getEntryList() {
		String contents = readFromFile();
		if(contents == null) return null;
		
		List<String> entriesList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(contents, "#");
		while(st.hasMoreTokens()) {
			String item = st.nextToken();
			entriesList.add(item);
			Log.i(TAG, "Entry item: " + item);
		}
		return entriesList;
	}

}
