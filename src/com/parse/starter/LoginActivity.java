package com.parse.starter;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{
	
	  private EditText usernameField;
	  private EditText passwordField;
	  private Button  LoginButton;
	  private Button  SignButton;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	    usernameField = (EditText) findViewById(R.id.login_username_input);
	    passwordField = (EditText) findViewById(R.id.login_password_input);
	    LoginButton = (Button) findViewById(R.id.parse_login_button);
	    SignButton = (Button) findViewById(R.id.parse_signup_button);
	    
	    LoginButton.setOnClickListener(Loginaccount);
	    SignButton.setOnClickListener(Signaccount);
	}
	
	private OnClickListener Loginaccount =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    ParseUser currentUser = ParseUser.getCurrentUser();
		    if (currentUser != null) {
		    	currentUser.logOut();
		    	System.out.println("要先登出anonymous");
		    String username = usernameField.getText().toString(); //username=>帳號
		    String password = passwordField.getText().toString();
		    
		    ParseUser.logInInBackground(username, password, new LogInCallback() {
				
				@Override
				public void done(ParseUser user, ParseException e) {
					// TODO Auto-generated method stub
					if(user!=null){
					    ParseUser currentUser = ParseUser.getCurrentUser();

						System.out.println("Successuser"+currentUser);
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, HomeActivity.class);
						startActivity(intent);

						
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
