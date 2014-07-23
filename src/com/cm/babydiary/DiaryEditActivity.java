package com.cm.babydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DiaryEditActivity extends FragmentActivity {
	private Validation parseErrResolver;

	
  //checks network connection
  	 Boolean _connected = false;
  	 Context _context;
  	 Button save;
  	 
  	 
	private Intent incomingIntent;
	EditText editMilestone;

	private int editPosition;
	private Diary entryitem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_entry);
		_context = this;
		editMilestone =(EditText) findViewById(R.id.edit_milestone);
		save = (Button)findViewById(R.id.save_btn);
		

		
		parseErrResolver = new Validation();
		incomingIntent = getIntent();
		editPosition = incomingIntent.getIntExtra(getString(R.string.position_key), -1);
		if ( editPosition>=0 && incomingIntent!=null ) {
			entryitem = (Diary)incomingIntent.getSerializableExtra(getString(R.string.attachment_key));
			((EditText)findViewById(R.id.edit_milestone)).setText(entryitem.getHeader());
			((EditText)findViewById(R.id.details_edit)).setText(entryitem.getDetails());
			
		}
		else {
			entryitem = new Diary();
		}
	}
	


	public void onSaveClicked(final View v) {
		 		
		Intent entries = new Intent();
		Bundle bundle = new Bundle();
		String entry1 =  ((EditText)findViewById(R.id.edit_milestone)).getText().toString();
		String entry2 = ((EditText)findViewById(R.id.details_edit)).getText().toString();


		entryitem.setHeader(entry1);
		entryitem.setDetails(entry2);

		bundle.putSerializable(getString(R.string.attachment_key), entryitem);
		entries.putExtras( bundle );
		finishWithResult(entries);
		
		
	}


	public void onDiscardClicked( final View v) {
		finishWithResult(null);
	}
	
	private void finishWithResult( Intent entries ) {
		if ( entries != null ) {
			if (getParent() == null) {
			    setResult(Activity.RESULT_OK, entries);
			} else {
			    getParent().setResult(Activity.RESULT_OK, entries);
			}
		}
		finish();

	
	//Detects the network connection
		_connected = InternetConnection.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION ", InternetConnection.getConnnectionType(_context));
		}else{
			//notified if user isn't connected to the Internet
			Context context = getApplicationContext();
			CharSequence text = "No Network Detected, Milestone cannot be saved";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			Intent intent = new Intent(DiaryEditActivity.this, UserActivity.class);
			startActivity(intent);
		}	
}


}//end
