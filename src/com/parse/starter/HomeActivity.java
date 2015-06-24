package com.parse.starter;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
		
	    /*final ParseUser currentUser = ParseUser.getCurrentUser();
	    ParseQuery<ParseUser> query = ParseUser.getQuery();
	    query.whereEqualTo("objectId", currentUser.getObjectId());
	   
	    
	    query.findInBackground(new FindCallback<ParseUser>() {			
			@Override
			 public void done(List<ParseUser> me, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if(e==null){
						  System.out.println("meget(i)getUsernametoString()"
								  +me.get(0).getUsername().toString());
		                   // me.get(i).getUsername().toString();
		            
				}
				else{
					
				}
				
			}
		});
	    

		System.out.println("Successuser2"+currentUser);*/

	}

}
