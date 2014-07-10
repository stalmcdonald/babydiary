package com.cm.babydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;



public class UserActivity extends Activity{ //implements OnClickListener{
	
	private EditText mUsernameField;
	private EditText mPasswordField;
	private TextView mErrorField;
	//private Button register, loginBttn; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_user);

		//findViewById(R.id.sign_in).setOnClickListener(this);
		
		mUsernameField = (EditText) findViewById(R.id.login_username);
		mPasswordField = (EditText) findViewById(R.id.login_password);
		mErrorField = (TextView) findViewById(R.id.error_messages);
//		register = (Button)findViewById(R.id.registerButton);
//		loginBttn = (Button)findViewById(R.id.sign_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void signIn(final View v){
		v.setEnabled(false);
		ParseUser.logInInBackground(mUsernameField.getText().toString(), mPasswordField.getText().toString(), new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Intent intent = new Intent(UserActivity.this, MilestoneActivity.class);
					startActivity(intent);
					finish();
				} else {
					// Signup failed. Look at the ParseException to see what happened.
					switch(e.getCode()){
					case ParseException.USERNAME_TAKEN:
						mErrorField.setText("Sorry, this username has already been taken.");
						break;
					case ParseException.USERNAME_MISSING:
						mErrorField.setText("Sorry, you must supply a username to register.");
						break;
					case ParseException.PASSWORD_MISSING:
						mErrorField.setText("Sorry, you must supply a password to register.");
						break;
//					case ParseException.OBJECT_NOT_FOUND:
//						mErrorField.setText("Sorry, those credentials were invalid.");
//						break;
					default:
						mErrorField.setText(e.getLocalizedMessage());
						break;
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void showRegistration(View v) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
		finish();
	}

//	@Override
//	public void onClick(View v) {
//		// setup switch case for buttons
//		switch (v.getId()){
//		
//		case R.id.registerBttn:
//			Intent taskIntent = new Intent(this, com.cm.babydiary.SignUpActivity.class);
//			taskIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(taskIntent);
//			break;
//		}
//	}
}