package com.quickplay;

import com.parse.starter.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class FriendActivity extends Activity {
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}

}
