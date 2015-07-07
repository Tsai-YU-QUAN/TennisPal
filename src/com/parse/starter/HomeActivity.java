package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.quickplay.QuickplayActivity;
import com.sinch.android.rtc.SinchError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends  BaseActivity implements SinchService.StartFailedListener  {
	ImageButton Personal_Profile ;
	ImageButton Quick_play ;
	ImageButton Tournament ;
	
 

	   @Override
	    public void onStartFailed(SinchError error) {
	        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
	    }
	    public void onStarted() {     // 正常 1->3->4 
	    	System.out.println("PleaseGO3");
	    	
	        /*Intent messagingActivity = new Intent();
	        messagingActivity.setClass(SignupActivity.this,com.sinch.android.rtc.sample.messaging.MessagingActivity.class);
	        startActivity(messagingActivity);*/
	       
	    }
	    
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
			 //登入sinch系統模組
    	    ParseUser currentUser = ParseUser.getCurrentUser();    //準備登入sinch系統
    	    System.out.println("UserID"+currentUser.getObjectId());
            if (!getSinchServiceInterface().isStarted()) {
            	System.out.println("HomeActivity_quick_play");
            	getSinchServiceInterface().startClient(currentUser.getObjectId());  //核心跟sinch server做連接
            	Intent intent =new Intent();
    			intent.setClass(HomeActivity.this, com.quickplay.QuickplayActivity.class);
    			startActivity(intent);                              
            } else {
            	Intent intent =new Intent();
    			intent.setClass(HomeActivity.this, com.quickplay.QuickplayActivity.class);
    			startActivity(intent);

            }
			
			/*Intent intent =new Intent();
			intent.setClass(HomeActivity.this, com.quickplay.QuickplayActivity.class);
			startActivity(intent);*/
		}
	};
	private OnClickListener tournament =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			 //登入sinch系統模組
    	    ParseUser currentUser = ParseUser.getCurrentUser();    //準備登入sinch系統
    	    System.out.println("UserID"+currentUser.getObjectId());
            if (!getSinchServiceInterface().isStarted()) {
            	System.out.println("HomeActivity_tournament");
            	getSinchServiceInterface().startClient(currentUser.getObjectId());  //核心跟sinch server做連接
    			Intent intent =new Intent();  
    			intent.setClass(HomeActivity.this, MessagingActivity.class);
    			startActivity(intent);                                 
            } else {
    			Intent intent =new Intent();  
    			intent.setClass(HomeActivity.this, MessagingActivity.class);
    			startActivity(intent);

            }
            /*
			Intent intent =new Intent();  
			intent.setClass(HomeActivity.this, MessagingActivity.class);
			startActivity(intent);*/
		}
	};

}
