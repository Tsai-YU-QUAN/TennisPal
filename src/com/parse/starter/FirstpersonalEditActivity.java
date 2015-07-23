package com.parse.starter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class FirstpersonalEditActivity extends Activity{    //要注意參數值空值或是要不要填的問題
	
	  private EditText Realname;
	  private EditText  UsualPlace;
	  private EditText Usualtime;
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
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.firstpersonaledit);
	
    Button Pickphoto = (Button)findViewById(R.id.Pickphoto);
    Button editfinal = (Button)findViewById(R.id.editfinal);
    personalview =(ImageView) findViewById(R.id.firstpersonalprfile);
    
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
            dialog = ProgressDialog.show(FirstpersonalEditActivity.this,
                    "個人資料註冊中", "請 稍 等 . . . . ",true);
		    EditText Realname = (EditText)findViewById(R.id.Realname);
		    EditText UsualPlace = (EditText)findViewById(R.id.UsualPlace);
		    EditText Usualtime = (EditText)findViewById(R.id.Usualtime);
		    EditText Handness = (EditText)findViewById(R.id.Handness);
		    EditText introduction = (EditText)findViewById(R.id.introduction);
		    EditText NTRP = (EditText)findViewById(R.id.NTRP);
		    
			final Editable GetRealname=Realname.getText();
			final Editable GetUsualPlace=UsualPlace.getText();
			final Editable GetUsualtime=Usualtime.getText();
			final Editable GetHandness=Handness.getText();
			final Editable Getintroduction=introduction.getText();
			final Editable GetNTRP=NTRP.getText();
			
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if(bitmap==null){
            	  dialog.dismiss();
                    Toast.makeText(v.getContext(), "還未上傳照片...", Toast.LENGTH_LONG).show();               
            }
            else if(GetRealname.toString().length()<1){
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] photodata = stream.toByteArray();
        	final ParseFile photoFile= new ParseFile("data.jpg",photodata); //bitmap to jpg
        	
			photoFile.saveInBackground(new SaveCallback() {     ///上傳照片

				@Override
				public void done(final ParseException e) {
					if(e==null){
		    			ParseObject testObject = new ParseObject(table_name);
						testObject.put(StringPhoto, photoFile);
						
						testObject.put(StringRealname,GetRealname.toString());
						testObject.put(StringUsualPlace,GetUsualPlace.toString());
						testObject.put(StringUsualtime,GetUsualtime.toString());
						
						testObject.put(StringHandedness,GetHandness.toString());
						testObject.put(StringIntroduction,Getintroduction.toString());
						testObject.put(StringNTRP,GetNTRP.toString());
						
						testObject.put(StringUserID,currentUser.getObjectId());
						System.out.println("currentUsergetObjectId()"+currentUser.getObjectId());//使用者id上傳parse
						
						testObject.saveInBackground();
						System.out.println("UpYesPhoto");//可能之後要做請等待的標語
						Intent intent =new Intent();
						intent.setClass(FirstpersonalEditActivity.this,HomeActivity.class);
						startActivity(intent);
		            	  dialog.dismiss();
					}
					else{
						System.out.println("Parseerror");
					}
					
				}
			});  

			//testObject.saveInBackground();
			
			
            }}

			
	};
	
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode"+requestCode);
        
        if(resultCode==RESULT_OK){
            // 取得檔案的 Uri
            Uri uri = data.getData();
            if( uri != null )
            {
                // 利用 Uri 顯示 ImageView 圖片
            	
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);//uri to bitmap
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
	

}
