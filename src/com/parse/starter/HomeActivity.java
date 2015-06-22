package com.parse.starter;

import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class HomeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
	    ParseUser currentUser = ParseUser.getCurrentUser();

		System.out.println("Successuser2"+currentUser);

	}

}
