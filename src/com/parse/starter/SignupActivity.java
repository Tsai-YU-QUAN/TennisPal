package com.parse.starter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sinch.android.rtc.SinchError;




public class SignupActivity extends BaseActivity implements SinchService.StartFailedListener  {
	/** Called when the activity is first created. */
	
	  private EditText usernameField;
	  private EditText passwordField;
	  private EditText confirmPasswordField;
	  private EditText emailField;
	  private EditText nameField;
	  private Button createAccountButton;
	  private static final int minPasswordLength = 6;
	  private static final String USER_OBJECT_NAME_FIELD = "name";
	  private ProgressDialog mSpinner;
	  private ProgressDialog dialog;


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
	
    /*@Override
    protected void onServiceConnected() {
    	createAccountButton.setEnabled(true);
        getSinchServiceInterface().setStartListener(this);
    }*/

    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }
	
    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }
	
    @Override
	public void onStarted() {     // 正常 1->3->4 
    	System.out.println("PleaseGO3");
    	
        /*Intent messagingActivity = new Intent();
        messagingActivity.setClass(SignupActivity.this,com.sinch.android.rtc.sample.messaging.MessagingActivity.class);
        startActivity(messagingActivity);*/
       
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
            dialog = ProgressDialog.show(SignupActivity.this,
                    "註冊中", "請 稍 等 . . . . ",true);
		    ParseUser currentUser = ParseUser.getCurrentUser();
		    if (currentUser != null) {
		    	ParseUser.logOut();
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
		      	  dialog.dismiss();
		      showToast(R.string.com_parse_ui_no_username_toast);
		    } else if (password.length() == 0) {
		      	  dialog.dismiss();
		      showToast(R.string.com_parse_ui_no_password_toast);
		    } else if (password.length() < minPasswordLength) {
		      	  dialog.dismiss();
		      showToast(getResources().getQuantityString(
		          R.plurals.com_parse_ui_password_too_short_toast,
		          minPasswordLength, minPasswordLength));
		    } else if (passwordAgain.length() == 0) {
		      	  dialog.dismiss();
		      showToast(R.string.com_parse_ui_reenter_password_toast);
		    } else if (!password.equals(passwordAgain)) {        //判斷pass是否和pass_again一樣
		      	  dialog.dismiss();
		      showToast(R.string.com_parse_ui_mismatch_confirm_password_toast);
		      confirmPasswordField.selectAll();
		      confirmPasswordField.requestFocus();
		    } else if (email != null && email.length() == 0) {
		      	  dialog.dismiss();
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
		        	    ParseUser currentUser = ParseUser.getCurrentUser();    //準備登入sinch系統
		        	    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");
		        	    System.out.println("UserID"+currentUser.getObjectId());
		                if (!getSinchServiceInterface().isStarted()) {
		                	System.out.println("PleaseGO");
		                	getSinchServiceInterface().startClient(currentUser.getObjectId());  //核心跟sinch server做連接
		                   // showSpinner();
				        	Intent intent = new Intent();
				        	intent.setClass(SignupActivity.this, FirstpersonalEditActivity.class);
				        	startActivity(intent);
				        	dialog.dismiss();
		                } else {
		                	System.out.println("PleaseGO2");
				        	Intent intent = new Intent();
				        	intent.setClass(SignupActivity.this, FirstpersonalEditActivity.class);
				        	startActivity(intent);
				        	dialog.dismiss();

		                }
		           // loadingFinish();//parse.ui.ParseLoginFragmentBase.loadingFinish()
		           // signupSuccess();//parse.ui.ParseSignupFragment.signupSuccess()
		          } else {
		            //loadingFinish();
		            if (e != null) {
		              debugLog(getString(R.string.com_parse_ui_login_warning_parse_signup_failed) +
		                  e.toString());
		              switch (e.getCode()) {
		                case ParseException.INVALID_EMAIL_ADDRESS:  //無效的EMAIL_ADDRESS
				          dialog.dismiss();
		                  showToast(R.string.com_parse_ui_invalid_email_toast);
		                  break;
		                case ParseException.USERNAME_TAKEN:
				          dialog.dismiss();
		                  showToast(R.string.com_parse_ui_username_taken_toast);
		                  break;
		                case ParseException.EMAIL_TAKEN:      //由parse 去判斷email有沒有重複
				          dialog.dismiss();
		                  showToast(R.string.com_parse_ui_email_taken_toast);  
		                  break;
		                default:
				          dialog.dismiss();
		                  showToast(R.string.com_parse_ui_signup_failed_unknown_toast);
		              }
		            }
		          }
		        }
		      });
		    }
			
		  }
		} 
	};    //Onclick
	
    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("正在登入");
        mSpinner.setMessage("請稍等...");
        mSpinner.show();
    }
	/*
    private void loginClicked() {

        if (!getSinchServiceInterface().isStarted()) {
        	System.out.println("PleaseGO");
            getSinchServiceInterface().startClient(userName);  //核心跟sinch server做連接
            showSpinner();
        } else {
        	System.out.println("PleaseGO2");
            openMessagingActivity();
        }
    }*/
}
