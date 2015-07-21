package com.parse.starter;

import com.parse.ParseUser;
import com.sinch.android.rtc.SinchError;

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
	Button Setting ;
	
 

	   @Override
	    public void onStartFailed(SinchError error) {
	        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
	    }
	    @Override
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
        Setting    =(Button)findViewById(R.id.SettingButton);
        
		Personal_Profile.setOnClickListener(personal_profile);
		Quick_play.setOnClickListener(quick_play);
		Tournament.setOnClickListener(tournament);
		Setting.setOnClickListener(setting);

	}
	
	
	private OnClickListener personal_profile =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Globalvariable.Extrapersonal="personal";   //給FragmentChangeActivity
			
			Intent intent =new Intent();
			intent.setClass(HomeActivity.this, changefragment.FragmentChangeActivity.class);   
        	System.out.println("HomeActivity_personal");
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
            	
    			Globalvariable.ExtraQuickplay="quick_play";   //給FragmentChangeActivity
            	Intent intent =new Intent();
    			intent.setClass(HomeActivity.this, changefragment.FragmentChangeActivity.class);   
    			//先到FragmentChangeActivity，之後在分發你是哪一個
    			startActivity(intent);                              
            } else {
    			Globalvariable.ExtraQuickplay="quick_play";   //給FragmentChangeActivity
            	Intent intent =new Intent();
    			intent.setClass(HomeActivity.this, changefragment.FragmentChangeActivity.class);   
    			//先到FragmentChangeActivity，之後在分發你是哪一個
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
            	Globalvariable.ExtraTournament="tournament";
            	getSinchServiceInterface().startClient(currentUser.getObjectId());  //核心跟sinch server做連接
    			Intent intent =new Intent();
    			intent.setClass(HomeActivity.this, changefragment.FragmentChangeActivity.class);
    			startActivity(intent);                                 
            } else {
            	Globalvariable.ExtraTournament="tournament";
    			Intent intent =new Intent();  
    			intent.setClass(HomeActivity.this, changefragment.FragmentChangeActivity.class);
    			startActivity(intent);

            }
            /*
			Intent intent =new Intent();  
			intent.setClass(HomeActivity.this, MessagingActivity.class);
			startActivity(intent);*/
		}
	};
	
	private OnClickListener setting =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
		}
	};

}
