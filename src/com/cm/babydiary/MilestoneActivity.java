/*
 * Crystal McDonald
 * CPM 1407
 * 
 * Main Activity
 * Users enter baby milestones into diary
 */
package com.cm.babydiary;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;

import com.cm.babydiary.InternetConnection;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Toast;

public class MilestoneActivity extends Activity implements OnItemClickListener {
	
	private static final String PARSE_EXTRA_DATA = "com.parse.Data";
	private static final int ITEM_CREATE_ACTIVITY_REQ=100;
	private static final int ITEM_EDIT_ACTIVITY_REQ=101;
	
	
	private String tokenSaved;
	private String sortBy = "";
	private EditList listView;
	private MilestoneAdapter milestoneAdapter;
	private boolean sortOrder = false;

  	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ParseSetUp.setupParseSDK(this);
		ParseSetUp.trackAppOpened(this);
		
		boolean loggingIn = ParseSetUp.isUserLoggedIn(); 
		if ( !loggingIn ) startLoginActivity();
		else 
		{
			setupListView();
			ParseSetUp.fetchItemsInBackground(milestoneAdapter, sortBy, sortOrder);
			setupPushReceiver();
		}
		
	}
	
	private void startLoginActivity() {
		startActivity(new Intent(this, UserActivity.class));
		finish();
	}
	
	private void setupPushReceiver() {
		tokenSaved = UUID.randomUUID().toString();
		IntentFilter intentFilter = new IntentFilter( getString(R.string.push_intent_key) );
		BroadcastReceiver pushReceiver;
		pushReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
            	String tokenReceived="";
            	try {
            		JSONObject json = new JSONObject( intent.getExtras().getString(PARSE_EXTRA_DATA));
            		tokenReceived = (String)json.get( getString(R.string.push_token_key) );
            	}
            	catch (Exception ex) {
            		ex.printStackTrace();
            	}
            	
            	if ( !tokenReceived.equals(tokenSaved) ) {
            		ParseSetUp.fetchItemsInBackground(milestoneAdapter, sortBy, sortOrder);
            	}
            }
		};
		registerReceiver(pushReceiver, intentFilter);
	}
	
	private void setupListView() {
		milestoneAdapter = new MilestoneAdapter(this, new ArrayList<Diary>());
		listView = (EditList)findViewById(R.id.diaryListView);
		listView.setAdapter(milestoneAdapter);
		listView.setOnItemClickListener(this);
		listView.setSwipeListener(new EditList.OnHorizontalSwipeListener() {
			@Override
			public void OnSwipe(int deltaX, int position) {
				Diary d = milestoneAdapter.getItem(position);
				d.setRelevance( !d.getRelevance() );
				ParseSetUp.updateItem(MilestoneActivity.this, d, milestoneAdapter, null, tokenSaved, true, sortBy, sortOrder);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
        switch (item.getItemId()) 
        {
        	case R.id.action_logout:
	            ParseUser.logOut();
	            startLoginActivity();
	            break;
        	case R.id.action_add_item:
        		createNewTodoItem( item );
        		
        		break;
        	case R.id.action_refresh:
        		refresh();
        		break;
        }
        return true;
	}
	
	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if ( resultCode==RESULT_OK ) {
			Bundle extras = data.getExtras();
			Diary di = (Diary)extras.getSerializable(getString(R.string.attachment_key));
			switch ( requestCode ) {
			case ITEM_CREATE_ACTIVITY_REQ:
				ParseSetUp.updateItem(this, di, milestoneAdapter, null, tokenSaved, false, sortBy, sortOrder);
				break;
			case ITEM_EDIT_ACTIVITY_REQ:
				ParseSetUp.updateItem(this, di, milestoneAdapter, null, tokenSaved, true, sortBy, sortOrder);
				break;
			}
		}
	}
	
	public void createNewTodoItem( MenuItem item ) {
	    
		Intent intent = new Intent( this, DiaryEditActivity.class );
		startActivityForResult(intent, ITEM_CREATE_ACTIVITY_REQ);
		
	}
	
	public void refresh() {
		ParseSetUp.fetchItemsInBackground(milestoneAdapter, sortBy, sortOrder);
	}
	
	public void onItemDeleteClicked( View v ) {
		Diary d = (Diary) v.getTag();
		ParseSetUp.deleteItem(this, d, milestoneAdapter, v, tokenSaved);
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if ( !listView.getSwipeOccured() ) {
			Diary d = milestoneAdapter.getItem(position);
			Intent diaryintent = new Intent( this, DiaryEditActivity.class );
			diaryintent.putExtra(getString(R.string.attachment_key), d);
			diaryintent.putExtra( getString(R.string.position_key), position);
			startActivityForResult(diaryintent, ITEM_EDIT_ACTIVITY_REQ);
		}
	}
}