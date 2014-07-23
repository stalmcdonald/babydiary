/*
 * Crystal McDonald
 * CPM 1407
 * 
 * Parse info
 */
package com.cm.babydiary;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Diary")
public class Diary extends ParseObject implements Serializable {
	
	public Diary() {
	}
	
	public Diary 
	( 
		String header, 
		String details, 
		boolean relevance, 
		ParseUser user 
	)
	{
		setHeader(header);
		setDetails(details);
		setRelevance(relevance);
		setUser(user);
	}
	
	public String getHeader(){
		return getString("header");
	}
	
	public Diary setHeader(String header) {
		put("header", header);
		return this;
	}
	
	public String getDetails(){
		return getString("details");
	}
	
	public Diary setDetails(String details){
		put("details", details);
		return this;
	}
	
	public boolean getRelevance(){
		return getBoolean("relevance");
	}
	
	public Diary setRelevance(boolean closed){
		put("relevance", closed);
		return this;
	}
	
	public ParseUser getUser() {
		return getParseUser("user");
	}

	public Diary setUser(ParseUser currentUser) {
		put("user", currentUser);
		return this;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(getString("header"));
		out.writeObject(getString("details"));
		out.writeBoolean(getBoolean("relevance"));
		out.writeObject(getObjectId());
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		put("header",(String)in.readObject());
		put("details",(String)in.readObject());
		put("relevance",in.readBoolean());
		setObjectId((String)in.readObject());
	}

	private void readObjectNoData() throws ObjectStreamException {
	}


	
}
