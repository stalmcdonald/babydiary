package com.cm.babydiary;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQuery.CachePolicy;

public class MilestoneActivity extends Activity implements OnItemClickListener {

	private EditText milestoneInput;
	private ListView mListView;
	private EntryAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.milestone);

		Parse.initialize(this, "niSGcDAeojX5tAn6kL2AWeCbiJWDMPc2DtdiZWfc", "kKvud3umQuYlJYT0n97PfEzLfSUliZjA7G1mvOyI");
		ParseAnalytics.trackAppOpened(getIntent());
		ParseObject.registerSubclass(Diary.class);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser == null){
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		mAdapter = new EntryAdapter(this, new ArrayList<Diary>());

		milestoneInput = (EditText) findViewById(R.id.milestone_input);
		mListView = (ListView) findViewById(R.id.milestone_list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);

		updateData();
	}

	public void updateData(){
		ParseQuery<Diary> query = ParseQuery.getQuery(Diary.class);
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.setCachePolicy(CachePolicy.CACHE_THEN_NETWORK);
		query.findInBackground(new FindCallback<Diary>() {
			@Override
			public void done(List<Diary> tasks, ParseException error) {
				if(tasks != null){
					mAdapter.clear();
					for (int i = 0; i < tasks.size(); i++) {
						mAdapter.add(tasks.get(i));
					}
				}
			}
		});
	}
	public void createTask(View v) {
		if (milestoneInput.getText().length() > 0){
			Diary d = new Diary();
			d.setACL(new ParseACL(ParseUser.getCurrentUser()));
			d.setUser(ParseUser.getCurrentUser());
			d.setDescription(milestoneInput.getText().toString());
			d.setCompleted(false);
			d.saveEventually();
			mAdapter.insert(d, 0);
			milestoneInput.setText("");
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout: 
			ParseUser.logOut();
			Intent intent = new Intent(this, UserActivity.class);
			startActivity(intent);
			finish();
			return true; 
		} 
		return false; 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Diary entry = mAdapter.getItem(position);
		TextView taskDescription = (TextView) view.findViewById(R.id.milestone_entry);

		entry.setCompleted(!entry.isCompleted());

		if(entry.isCompleted()){
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		}else{
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}

		entry.saveEventually();
	}

}

