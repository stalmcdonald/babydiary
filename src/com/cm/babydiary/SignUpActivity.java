package com.cm.babydiary;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SignUpActivity extends Activity implements OnClickListener{

	private EditText mUsernameField;
	private EditText mPasswordField;
	private TextView mErrorField;
	//private Button sign_in; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_user);

		//findViewById(R.id.sign_in).setOnClickListener(this);
		
		mUsernameField = (EditText) findViewById(R.id.register_username);
		mPasswordField = (EditText) findViewById(R.id.register_password);
		mErrorField = (TextView) findViewById(R.id.error_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	public void register(final View v){
		if(mUsernameField.getText().length() == 0 || mPasswordField.getText().length() == 0)
			return;

		v.setEnabled(false);
		ParseUser user = new ParseUser();
		user.setUsername(mUsernameField.getText().toString());
		user.setPassword(mPasswordField.getText().toString());
		mErrorField.setText("");

		user.signUpInBackground(new SignUpCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					Intent intent = new Intent(SignUpActivity.this, MilestoneActivity.class);
					startActivity(intent);
					finish();
				} else {
					// Sign up failed
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
					default:
						mErrorField.setText(e.getLocalizedMessage());
					}
					v.setEnabled(true);
				}
			}
		});
	}

	public void showLogin(View v) {
		Intent intent = new Intent(this, UserActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
