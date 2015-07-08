package com.parse.starter;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R.color;
import com.quickplay.QuickplayActivity;
import com.sinch.android.rtc.SinchError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends  BaseActivity implements SinchService.StartFailedListener{
	
	  private EditText usernameField;
	  private EditText passwordField;
	  private Button  LoginButton;
	  private Button  SignButton;
	  
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	    usernameField = (EditText) findViewById(R.id.login_username_input);
	    passwordField = (EditText) findViewById(R.id.login_password_input);
	    LoginButton = (Button) findViewById(R.id.parse_login_button);
	    SignButton = (Button) findViewById(R.id.parse_signup_button);
	    
	   // Intent intent = new Intent(LoginActivity.this, ReadWriteService.class);
	   // startService(intent);
	    
	    LoginButton.setOnClickListener(Loginaccount);
	    SignButton.setOnClickListener(Signaccount);
	}
	
	private OnClickListener Loginaccount =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    ParseUser currentUserlogout = ParseUser.getCurrentUser();
		    if (currentUserlogout != null) {
		    	currentUserlogout.logOut();
		    	System.out.println("要先登出anonymous");
		    String username = usernameField.getText().toString(); //username=>帳號
		    String password = passwordField.getText().toString();
		    
		    ParseUser.logInInBackground(username, password, new LogInCallback() {
				
				@Override
				public void done(ParseUser user, ParseException e) {
					// TODO Auto-generated method stub
					if(user!=null){
						
						 //登入sinch系統模組
		        	    ParseUser currentUser = ParseUser.getCurrentUser();    //準備登入sinch系統
		        	    System.out.println("UserID"+currentUser.getObjectId());
		                if (!getSinchServiceInterface().isStarted()) {
		                	System.out.println("LoginActivity1");
		                	getSinchServiceInterface().startClient(currentUser.getObjectId());  //核心跟sinch server做連接
				        	  Intent intent = new Intent();
				        	  intent.setClass(LoginActivity.this, HomeActivity.class);  //可切換要哪一個activity
				        	  startActivity(intent);                                    //
		                } else {
		                	System.out.println("LoginActivity2");
				        	  Intent intent = new Intent();
				        	  intent.setClass(LoginActivity.this, HomeActivity.class);  //可切換要哪一個activity
				        	  startActivity(intent);                                    //

		                }
						

						/*Intent intent = new Intent();
						intent.setClass(LoginActivity.this, HomeActivity.class);
						startActivity(intent);*/

						
					}else{
						System.out.println("erroruser"+e);
						
					}
					
				}
			});
		    
			
		   }
		}
	};

	
	private OnClickListener Signaccount =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, SignupActivity.class);
			startActivity(intent);
			
		}
	};
}
