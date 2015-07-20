package com.parse.personal.profile;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

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
	    System.out.println("UserID"+currentUser.getObjectId());
	    tablequery.whereEqualTo("UserID", currentUser.getObjectId());  //限制從自己id取得
/*	    
	    tablequery.getInBackground(currentUser.getObjectId(), new GetCallback<ParseObject>() {
			
			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				if(e==null){
					realname=object.get(com.parse.starter.Globalvariable.Realname).toString();
					usualplace=object.get(com.parse.starter.Globalvariable.UsualPlace).toString();
					usualtime=object.get(com.parse.starter.Globalvariable.Usualtime).toString();
					
	    			ParseRealname.setText(realname);
	    			ParseUsualplace.setText(usualplace);
	    			ParseUsualtime.setText(usualtime);
	    			System.out.println("realname"+realname);

					
				}else{
	    			System.out.println("personalproflierror");

					
				}
				
			}
		});*/
	    
	    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取文字
	    	public void done(List<ParseObject> me, ParseException e) {
	    		if(e==null){
	    			realname=me.get(0).getString(com.parse.starter.Globalvariable.Realname).toString();
	    			usualplace=me.get(0).getString(com.parse.starter.Globalvariable.UsualPlace).toString();
	    			usualtime=me.get(0).getString(com.parse.starter.Globalvariable.Usualtime).toString();

	    			ParseRealname.setText(realname);
	    			ParseUsualplace.setText(usualplace);
	    			ParseUsualtime.setText(usualtime);
	    			System.out.println("realname"+realname);
	    			
	    		    final ParseFile image =(ParseFile)me.get(0).get("Photo");
	    		    		// ((ParseObject) me).getParseFile("data");
	    		   // final ParseImageView imageView = (ParseImageView) findViewById(R.id.personalprfile);
	    		   // imageView.setParseFile(image);
	    		   // System.out.println("image"+image);
	    		   // if(image!=null){
	    		    image.getDataInBackground(new GetDataCallback() {
						
						@Override
						public void done(byte[] data, ParseException e) {
							// TODO Auto-generated method stub
							if(e==null){
				    			System.out.println("personalprofile"+" "+data.length);
                                final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                                // Get the ImageView from main.xml
                                //ImageView image = (ImageView) findViewById(R.id.ad1);
                                final ParseImageView imageView = (ParseImageView) findViewById(R.id.personalprfile);

                               // ImageView imageView=(ImageView) findViewById(R.id.personalprfile);
                                // Set the Bitmap into the
                                // ImageView
                                imageView.setParseFile(image);
                                imageView.setImageBitmap(bmp);
                               /* imageView.loadInBackground(new GetDataCallback() {
                                    public void done(byte[] data, ParseException e) {
                                    // The image is loaded and displayed!                    
                                    int oldHeight = imageView.getHeight();
                                    int oldWidth = imageView.getWidth();     
                                    System.out.println("imageView height = " + oldHeight);
                                    System.out.println("imageView width = " + oldWidth);
                                    imageView.setImageBitmap(bmp);


                                   // Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                                   // Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px      
                                    }
                                });*/
								
							}
							else{
				    			System.out.println("personalprofilerror");

							}
							
						}
					});
	    		  //  }else{
		    		//	System.out.println("imageerror"); 	
	    		   // }
	    		    /*imageView.loadInBackground(new GetDataCallback() {
	    		         public void done(byte[] data, ParseException e) {
	    		         // The image is loaded and displayed!                    
	    		         int oldHeight = imageView.getHeight();
	    		         int oldWidth = imageView.getWidth(); 
	    		         System.out.println("imageView height = " + oldHeight);
	    		         System.out.println("imageView width = " + oldWidth);
	    		             
	    		         }
	    		    });*/ 
	    			
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
