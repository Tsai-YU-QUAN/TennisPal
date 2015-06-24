package com.parse.starter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
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

public class FirstpersonalEditActivity extends Activity{
	
	  private EditText Realname;
	  private EditText  UsualPlace;
	  private EditText Usualtime;
	  private Button editfinal;
	  private Button  Pickphoto;
	  String table_name="personaltable";
	  String StringRealname="Realname";
	  String StringUsualPlace="UsualPlace";
	  String StringUsualtime="Usualtime";
	  String StringPhoto="Photo";
	  String StringUserID="UserID";
	  ParseUser currentUser = ParseUser.getCurrentUser();


	 
	  
	  
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.firstpersonaledit);
	
    Button Pickphoto = (Button)findViewById(R.id.Pickphoto);
    Button editfinal = (Button)findViewById(R.id.editfinal);
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
		    EditText Realname = (EditText)findViewById(R.id.Realname);
		    EditText UsualPlace = (EditText)findViewById(R.id.UsualPlace);
		    EditText Usualtime = (EditText)findViewById(R.id.Usualtime);
		    
			Editable GetRealname=Realname.getText();
			Editable GetUsualPlace=UsualPlace.getText();
			Editable GetUsualtime=Usualtime.getText();

			ParseObject testObject = new ParseObject(table_name);    //上傳三項 +再加使用者id上傳parse
			testObject.put(StringRealname,GetRealname.toString());
			testObject.put(StringUsualPlace,GetUsualPlace.toString());
			testObject.put(StringUsualtime,GetUsualtime.toString());
			testObject.put(StringUserID,currentUser.getObjectId());
			System.out.println("currentUsergetObjectId()"+currentUser.getObjectId());//使用者id上傳parse
			testObject.saveInBackground();
			
			Intent intent =new Intent();
			intent.setClass(FirstpersonalEditActivity.this,HomeActivity.class);
			startActivity(intent);

			
		}
	};
	
	
	protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode"+requestCode);
        
        if(resultCode==RESULT_OK){
            // 取得檔案的 Uri
            Uri uri = data.getData();
            if( uri != null )
            {
                // 利用 Uri 顯示 ImageView 圖片
            	
                Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);//uri to bitmap

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] photodata = stream.toByteArray();
            	final ParseFile photoFile= new ParseFile("data.jpg",photodata); //bitmap to jpg
            	
				photoFile.saveInBackground(new SaveCallback() {     ///上傳照片

					@Override
					public void done(final ParseException e) {
						if(e==null){
			    			ParseObject testObject = new ParseObject(table_name);
							testObject.put(StringPhoto, photoFile);
							testObject.saveInBackground();
							System.out.println("UpYesPhoto");
						}
						else{
							System.out.println("Parseerror");
						}
						
					}
				});            	
            }
            else
            {
            	System.out.println("無效的檔案路徑 !!");

            }
        }
	}
	

}
