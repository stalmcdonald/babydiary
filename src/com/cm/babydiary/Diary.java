package com.cm.babydiary;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Diary")
public class Diary extends ParseObject{
	public Diary(){
		
	}
	
	public boolean isCompleted(){
		return getBoolean("completed");
	}
	
	public void setCompleted(boolean complete){
		put("completed", complete);
	}
	
	public String getDescription(){
		return getString("entry");
	}
	
	public void setDescription(String entry){
		put("entry", entry);
	}

	public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}
}
