package com.parse.personal.profile;

import java.util.List;

import org.w3c.dom.Text;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.R.color;

public class PersonalprofileActivity extends Activity {

	String realname="";
	String usualplace="";
	String usualtime="";

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalproflie);
		final TextView ParseUsualplace =(TextView)findViewById(R.id.ParseUsualplace);
		final TextView ParseUsualtime =(TextView)findViewById(R.id.ParseUsualtime);
		final TextView ParseRealname =(TextView)findViewById(R.id.ParseRealname);
		//ParseImageView personalprfile = (ParseImageView) findViewById(R.id.personalprfile);

		

		

	    ParseUser currentUser = ParseUser.getCurrentUser();
	    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");
	    tablequery.whereEqualTo("UserID", currentUser.getObjectId());  //限制從自己id取得
	    
	    
	    tablequery.findInBackground(new FindCallback<ParseObject>() {
	    	public void done(List<ParseObject> me, ParseException e) {
	    		if(e==null){
	    			realname=me.get(0).getString(com.parse.starter.Globalvariable.Realname).toString();
	    			usualplace=me.get(0).getString(com.parse.starter.Globalvariable.UsualPlace).toString();
	    			usualtime=me.get(0).getString(com.parse.starter.Globalvariable.Usualtime).toString();

	    			ParseRealname.setText(realname);
	    			ParseUsualplace.setText(usualplace);
	    			ParseUsualtime.setText(usualtime);

	    			System.out.println("realname"+realname);
	    			
	    		}else{
	    			
	    		}
	    		
	    	}
		});
	    
	  /*  tablequery.findInBackground(new FindCallback<ParseUser>() {			
			@Override
			 public void done(List<ParseObject> Objects, com.parse.ParseException e) {
				// TODO Auto-generated method stub
				if(e==null){
						  System.out.println("meget(i)getUsernametoString()"
								  +me.get(0).getUsername().toString());
		                   // me.get(i).getUsername().toString();
						 
						 System.out.println("realname"+realname);
						 ParseRealname.setText(realname);
						// ParseUsualplace.setText(usualplace);
						//  ParseUsualtime.setText(usualtime);
						 
				}
				else{
					
				}
				
			}
		});*/
	    

		System.out.println("Successuser2"+currentUser);
		
		
	}

}
