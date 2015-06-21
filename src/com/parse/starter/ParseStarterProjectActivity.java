package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;

import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	    
	    ParseUser user = new ParseUser();
	    user.setUsername("Tsai");
	    user.setPassword("pass");
	    user.setEmail("b3030826@gmail.com");

	    // other fields can be set just like with ParseObject
	   // user.put("phone", "650-253-0000");

	    user.signUpInBackground(new SignUpCallback() {
			
			@Override
			public void done(ParseException e) {
				// TODO Auto-generated method stub
				if(e==null){
					
				}
				
			}
		});

	}
}
