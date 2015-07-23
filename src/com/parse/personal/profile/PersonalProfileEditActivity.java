package com.parse.personal.profile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.R.anim;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.Globalvariable;
import com.parse.starter.HomeActivity;
import com.parse.starter.R;

public class PersonalProfileEditActivity extends Activity{
	

    private EditText Realname;
    private EditText UsualPlace;
    private  EditText Usualtime;
    private EditText Handness;
    private EditText introduction;
    private EditText NTRP;
	  private Button editfinal;
	  private Button  Pickphoto;
	  String table_name="personaltable";
	  String StringRealname="Realname";
	  String StringUsualPlace="UsualPlace";
	  String StringUsualtime="Usualtime";
	  String StringHandedness="Handedness";
	  String StringIntroduction="Introduction";
	  String StringNTRP="NTRP";
	  String StringPhoto="Photo";
	  String StringUserID="UserID";
      Bitmap bitmap = null;
      ProgressDialog dialog;
      ImageView personalview;
	  ParseUser currentUser = ParseUser.getCurrentUser();
	  

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.personalprofiledit);
	        
		    Realname = (EditText)findViewById(R.id.realname);
		    UsualPlace = (EditText)findViewById(R.id.usualplace);
		    Usualtime = (EditText)findViewById(R.id.usualtime);
		    Handness = (EditText)findViewById(R.id.handedness);
		    introduction = (EditText)findViewById(R.id.introduction);
		    NTRP = (EditText)findViewById(R.id.ntrp);
		    Button Pickphoto = (Button)findViewById(R.id.pickphoto);
		    Button editfinal = (Button)findViewById(R.id.editfinal);
		    personalview =(ImageView)findViewById(R.id.firstpersonalprfile);
		    
		    Realname.setText(Globalvariable.tempRealname);
		    UsualPlace.setText(Globalvariable.tempUsualplace);
		    Usualtime.setText(Globalvariable.tempUsaultime);
		    Handness.setText(Globalvariable.tempHandness);
		    introduction.setText(Globalvariable.tempIntroduction);
		    NTRP.setText(Globalvariable.tempNTRP);
		    
		    Pickphoto.setOnClickListener(photo);
		    editfinal.setOnClickListener(edit);
	        
	    }
		
		private OnClickListener photo =new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(Intent.ACTION_PICK);
				intent.setType("image/*");
	            Intent destIntent = Intent.createChooser( intent, "選擇檔案" );
	            startActivityForResult( destIntent, 0 );  //選擇照片和上傳照片

				
			}
		};
		
		private OnClickListener edit =new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	            dialog = ProgressDialog.show(PersonalProfileEditActivity.this,
	                    "個人資料編輯中", "請 稍 等 . . . . ",true);

			    
				final Editable GetRealname=Realname.getText();
				final Editable GetUsualPlace=UsualPlace.getText();
				final Editable GetUsualtime=Usualtime.getText();
				final Editable GetHandness=Handness.getText();
				final Editable Getintroduction=introduction.getText();
				final Editable GetNTRP=NTRP.getText();
				

	            ByteArrayOutputStream stream = new ByteArrayOutputStream();
	            /*if(bitmap==null){   //之後要傳一個URI下來給EditActivity
	            	  dialog.dismiss();
	                    Toast.makeText(v.getContext(), "還未上傳照片...", Toast.LENGTH_LONG).show();               
	            }*/
	           if(GetRealname.toString().length()<1){
	            	  dialog.dismiss();
	                Toast.makeText(v.getContext(), "還未輸入姓名...", Toast.LENGTH_LONG).show();
	            }
	            else if(GetUsualPlace.toString().length()<1){
	          	  dialog.dismiss();
	                Toast.makeText(v.getContext(), "還未輸入出沒的地點...", Toast.LENGTH_LONG).show();
	            }
	            else if(GetUsualtime.toString().length()<1){
	          	  dialog.dismiss();
	                Toast.makeText(v.getContext(), "還未輸入出沒時間...", Toast.LENGTH_LONG).show();
	            }
	            else if(GetHandness.toString().length()<1){
	            	  dialog.dismiss();
	                  Toast.makeText(v.getContext(), "還未輸入慣用手...", Toast.LENGTH_LONG).show();
	            }
	            else if(Getintroduction.toString().length()<1){
	            	  dialog.dismiss();
	                  Toast.makeText(v.getContext(), "還未輸入自我介紹...", Toast.LENGTH_LONG).show();
	            	
	            }
	            else if(GetNTRP.toString().length()<1){
	            	  dialog.dismiss();
	                  Toast.makeText(v.getContext(), "還未輸入NTRP...", Toast.LENGTH_LONG).show();
	            	
	            }
	            else{
	        //    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	        //    byte[] photodata = stream.toByteArray();
	       // final ParseFile photoFile= new ParseFile("data.jpg",photodata); //bitmap to jpg
	        	
			//	photoFile.saveInBackground(new SaveCallback() {     ///上傳照片

			//		@Override
				//	public void done(final ParseException e) {
				//		if(e==null){
	            	
				    ParseUser currentUser = ParseUser.getCurrentUser();
				    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");
				    System.out.println("UserID"+currentUser.getObjectId());
				    tablequery.whereEqualTo("UserID", currentUser.getObjectId());  //限制從自己id取得
				    
				    tablequery.getFirstInBackground(new GetCallback<ParseObject>() {
				    	public void done(ParseObject datachangeobject, ParseException e){
				    		if(e==null){
				    			
								
								//testObject.put(StringPhoto, photoFile);        //之後要更新photoFile是否使用者用更新後面要傳filepath
								System.out.println("toStringtempRealname)"
										+GetRealname.toString()+" "+Globalvariable.tempRealname);
								if(!GetRealname.toString().equals(Globalvariable.tempRealname)){
									datachangeobject.put(StringRealname,GetRealname.toString());
								}
								if(!GetUsualPlace.toString().equals(Globalvariable.tempUsualplace)){
									datachangeobject.put(StringUsualPlace,GetUsualPlace.toString());
						        }
								if(!GetUsualtime.toString().equals(Globalvariable.tempUsaultime)){

									datachangeobject.put(StringUsualtime,GetUsualtime.toString());
								}
								if(!GetHandness.toString().equals(Globalvariable.tempHandness)){
									datachangeobject.put(StringHandedness,GetHandness.toString());
								}
								if(!Getintroduction.toString().equals(Globalvariable.tempIntroduction)){
									datachangeobject.put(StringIntroduction,Getintroduction.toString());
								}
								if(!GetNTRP.toString().equals(Globalvariable.tempNTRP)){
									datachangeobject.put(StringNTRP,GetNTRP.toString());
								}
								
								datachangeobject.saveInBackground();
								System.out.println("UpYesPhoto");//可能之後要做請等待的標
				    			
				    		}
				    		else{
				    			System.out.println("datachangeobjectError");
				    		}
				    		
				    	}
					});

							
							
							
							Globalvariable.Extrapersonal="personal";   //給FragmentChangeActivity
							
							Intent intent =new Intent();
							intent.setClass(PersonalProfileEditActivity.this, changefragment.FragmentChangeActivity.class);   
				        	System.out.println("HomeActivity_personal");
				        	startActivity(intent);
						}
					//	else{
					//		System.out.println("Parseerror");
					//	}
						
					//}
				//});  

				//testObject.saveInBackground();
				
				
	           // }
			}

				
		};
		
		
		@Override
		public void onActivityResult(int requestCode,int resultCode,Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        System.out.println("requestCode"+requestCode);
	        
	        if(resultCode==Activity.RESULT_OK){
	            // 取得檔案的 Uri
	            Uri uri = data.getData();
	            if( uri != null )
	            {
	                // 利用 Uri 顯示 ImageView 圖片
	            	
					try {
						bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);//uri to bitmap
						personalview.setImageURI(uri);
						

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	               /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
	                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	                byte[] photodata = stream.toByteArray();
	            	final ParseFile photoFile= new ParseFile("data.jpg",photodata); //bitmap to jpg
	            	
					photoFile.saveInBackground(new SaveCallback() {     ///上傳照片

						@Override
						public void done(final ParseException e) {
							if(e==null){
				    			ParseObject testObject = new ParseObject(table_name);
								testObject.put(StringPhoto, photoFile);
								testObject.put(StringUserID,currentUser.getObjectId());
								System.out.println("currentUsergetObjectId()"+currentUser.getObjectId());//使用者id上傳parse
								testObject.saveInBackground();
								System.out.println("UpYesPhoto");
							}
							else{
								System.out.println("Parseerror");
							}
							
						}
					});    */        	
	            }
	            else
	            {
	            	System.out.println("無效的檔案路徑 !!");

	            }
	        }
		}
		
	 
	  
	  
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.firstpersonaledit);
	
    Button Pickphoto = (Button)findViewById(R.id.Pickphoto);
    Button editfinal = (Button)findViewById(R.id.editfinal);
    personalview =(ImageView) findViewById(R.id.firstpersonalprfile);
    
    Pickphoto.setOnClickListener(photo);
    editfinal.setOnClickListener(edit);
	

	
	
	}*/


}
