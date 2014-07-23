/*
 * Crystal McDonald
 * CPM 1407
 * 
 * Parse Exceptions - Validating common input mistakes
 */
package com.cm.babydiary;

import java.util.HashMap;

import android.content.Context;

import com.parse.ParseException;

public class Validation {
	
	
	private static final HashMap<Integer,Integer> parsingError;
	static {
		parsingError = new HashMap<Integer, Integer>();
		parsingError.put(ParseException.USERNAME_TAKEN, R.string.username_taken);
		parsingError.put(ParseException.DUPLICATE_VALUE, R.string.header);
		parsingError.put(ParseException.USERNAME_MISSING, R.string.header);
		parsingError.put(ParseException.USERNAME_MISSING, R.string.details);
		parsingError.put(ParseException.USERNAME_MISSING, R.string.username_missing);
		parsingError.put(ParseException.PASSWORD_MISSING, R.string.password_missing);
		parsingError.put(ParseException.OBJECT_NOT_FOUND, R.string.object_not_found);
//		codeMap.put(ParseException.CONNECTION_FAILED, R.string.connection_failed);
//		codeMap.put(ParseException.TIMEOUT, R.string.connection_timeout);
	}
	
	public String resolve( Context c, ParseException e ) {
		if ( parsingError.containsKey(e.getCode()) ) {
			return c.getString(parsingError.get(e.getCode()));
		}
		else {
			return c.getString(R.string.connection_unknown_error);

		}
	}

}
