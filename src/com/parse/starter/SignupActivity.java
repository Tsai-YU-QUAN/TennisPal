package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SignUpCallback;




public class SignupActivity extends Activity {
	/** Called when the activity is first created. */
	
	  private EditText usernameField;
	  private EditText passwordField;
	  private EditText confirmPasswordField;
	  private EditText emailField;
	  private EditText nameField;
	  private Button createAccountButton;
	  private static final int minPasswordLength = 6;
	  private static final String USER_OBJECT_NAME_FIELD = "name";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		
	    usernameField = (EditText) findViewById(R.id.signup_username_input);
	    passwordField = (EditText) findViewById(R.id.signup_password_input);
	    confirmPasswordField = (EditText) findViewById(R.id.signup_confirm_password_input);
	    emailField = (EditText) findViewById(R.id.signup_email_input);
	    createAccountButton = (Button) findViewById(R.id.create_account);
	    
	    createAccountButton.setOnClickListener(crateaccount);
		
	    

	}
	
	  protected String getLogTag() {
		    return null;
		  }
	  protected void showToast(int id) {
		    showToast(getString(id));
		  }
		  protected void showToast(CharSequence text) {
		    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
		  }
		  
		 /* protected boolean isActivityDestroyed() {
			    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			      return this.isDestroyed();
			    } else {
			      return this.isDestroyed();
			    }
			  }*/
		  
		  protected void debugLog(int id) {
			    debugLog(getString(id));
			  }
		  
		  protected void debugLog(String text) {
			    if (Parse.getLogLevel() <= Parse.LOG_LEVEL_DEBUG &&
			        Log.isLoggable(getLogTag(), Log.WARN)) {
			      Log.w(getLogTag(), text);
			    }
			  }
	
	private OnClickListener crateaccount =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		    ParseUser currentUser = ParseUser.getCurrentUser();
		    if (currentUser != null) {
		    	currentUser.logOut();
		    	System.out.println("要先登出anonymous");
			
		    String username = usernameField.getText().toString();
		    String password = passwordField.getText().toString();
		    String passwordAgain = confirmPasswordField.getText().toString();

		    String email = null;
		   /* if (config.isParseLoginEmailAsUsername()) {     //config
		      email = usernameField.getText().toString();
		    } */
		    
		     if (emailField != null) {
		      email = emailField.getText().toString();
		    }

		    /*String name = null;
		    if (nameField != null) {
		      name = nameField.getText().toString();
		    }*/

		    if (username.length() == 0) {
		      showToast(R.string.com_parse_ui_no_username_toast);
		    } else if (password.length() == 0) {
		      showToast(R.string.com_parse_ui_no_password_toast);
		    } else if (password.length() < minPasswordLength) {
		      showToast(getResources().getQuantityString(
		          R.plurals.com_parse_ui_password_too_short_toast,
		          minPasswordLength, minPasswordLength));
		    } else if (passwordAgain.length() == 0) {
		      showToast(R.string.com_parse_ui_reenter_password_toast);
		    } else if (!password.equals(passwordAgain)) {        //判斷pass是否和pass_again一樣
		      showToast(R.string.com_parse_ui_mismatch_confirm_password_toast);
		      confirmPasswordField.selectAll();
		      confirmPasswordField.requestFocus();
		    } else if (email != null && email.length() == 0) {
		      showToast(R.string.com_parse_ui_no_email_toast);
		    } else {
		      ParseUser user = new ParseUser();

		      // Set standard fields
		      user.setUsername(username);
		      user.setPassword(password);
		      user.setEmail(email);

		      // Set additional custom fields only if the user filled it out
		     /* if (name.length() != 0) {
		        user.put(USER_OBJECT_NAME_FIELD, name);
		      }*/

		      // loadingStart();
		      user.signUpInBackground(new SignUpCallback() {

		        @Override
		        public void done(com.parse.ParseException e) {
		         // if (isActivityDestroyed()) {
		         //   return;
		         // }

		          if (e == null) {
		        	  System.out.println("google");
		        	  Intent intent = new Intent();
		        	  intent.setClass(SignupActivity.this, FirstpersonalEditActivity.class);
		        	  startActivity(intent);
		           // loadingFinish();//parse.ui.ParseLoginFragmentBase.loadingFinish()
		           // signupSuccess();//parse.ui.ParseSignupFragment.signupSuccess()
		          } else {
		            //loadingFinish();
		            if (e != null) {
		              debugLog(getString(R.string.com_parse_ui_login_warning_parse_signup_failed) +
		                  e.toString());
		              switch (e.getCode()) {
		                case ParseException.INVALID_EMAIL_ADDRESS:  //無效的EMAIL_ADDRESS
		                  showToast(R.string.com_parse_ui_invalid_email_toast);
		                  break;
		                case ParseException.USERNAME_TAKEN: 
		                  showToast(R.string.com_parse_ui_username_taken_toast);
		                  break;
		                case ParseException.EMAIL_TAKEN:      //由parse 去判斷email有沒有重複
		                  showToast(R.string.com_parse_ui_email_taken_toast);  
		                  break;
		                default:
		                  showToast(R.string.com_parse_ui_signup_failed_unknown_toast);
		              }
		            }
		          }
		        }
		      });
		    }
			
		  }
		} //Onclick
	};
}
