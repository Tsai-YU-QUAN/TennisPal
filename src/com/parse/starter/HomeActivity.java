package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends Activity {
	ImageButton Personal_Profile ;
	ImageButton Quick_play ;
	ImageButton Tournament ;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		Personal_Profile =(ImageButton)findViewById(R.id.Personal_Profile);
		Quick_play =(ImageButton)findViewById(R.id.Quick_play);
		Tournament =(ImageButton)findViewById(R.id.Tournament);

		Personal_Profile.setOnClickListener(personal_profile);
		Quick_play.setOnClickListener(quick_play);
		Tournament.setOnClickListener(tournament);


	}
	
	
	private OnClickListener personal_profile =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent =new Intent();
			intent.setClass(HomeActivity.this, com.parse.personal.profile.PersonalprofileActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener quick_play =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}
	};
	private OnClickListener tournament =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		}
	};

}
