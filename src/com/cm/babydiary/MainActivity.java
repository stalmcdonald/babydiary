package com.cm.babydiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	Button loginBttn, signinBttn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		
		//set up onclick listeners for buttons
				findViewById(R.id.loginBttn).setOnClickListener(this);
				findViewById(R.id.signinBttn).setOnClickListener(this);

	}



	@Override
	public void onClick(View v) {
		
		// setup switch case for buttons
				switch (v.getId()){
				
				case R.id.loginBttn:
					Intent loginIntent = new Intent(this, com.cm.babydiary.UserActivity.class);
					loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(loginIntent);
					break;
					
				case R.id.signinBttn:
					Intent signupIntent = new Intent(this, com.cm.babydiary.SignUpActivity.class);
					signupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(signupIntent);
					break;				
				
				}//end switch
	}// end onclick

}//end
