package com.truphone.miniproject.utils;

import java.util.StringTokenizer;

public class Utils {

	
	//input string format: xx:xx:xxs or xx:xxs or xxs
	public static long getSeconds(String counterString) {
		int hh = 0;
		int mm = 0;
		int ss = 0;
		long totalSecs = 0;
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
						hh = Integer.parseInt(st.nextToken());
					case 2:
						mm = Integer.parseInt(st.nextToken());
					case 1:
						String secsString = st.nextToken(); 
						secsString = secsString.substring(0, secsString.indexOf("s"));
						ss = Integer.parseInt(secsString);
						break;
				}
			}
		} else {//format: xxs
			ss = Integer.parseInt(counterString.substring(0, counterString.length()-1));
		}
		//calculate total seconds
		totalSecs = hh * 60 * 60 + mm * 60 + ss;
		return totalSecs;
	}
	
}
