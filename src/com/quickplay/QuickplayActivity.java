package com.quickplay;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Globalvariable;
import com.parse.starter.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;


public class QuickplayActivity extends QuickbaseActivity  implements MessageClientListener  {
	
    private static final String TAG = "QuickplayActivity";
	String realname="";
	String usualplace="";
	String usualtime="";
	String userID="";
	String SendLike;
	String ReceiveLike;
	String RecipientId;
	String table_name="friend";
	Boolean terminate=false;
	Boolean if_friend=false;
	Button Addfriend;
	Thread initThread;
	private Handler mUI_Handler =new Handler();
	private Handler mThHandler;
	private HandlerThread mThread;
	ParseFile image;
	
	ParseUser currentUser = ParseUser.getCurrentUser();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quickplay);
		Addfriend =(Button)findViewById(R.id.addfriend);
		
		//ParseImageView personalprfile = (ParseImageView) findViewById(R.id.personalprfile);
		Addfriend.setOnClickListener(addfriend);
		System.out.println("QuickplayActivity");
		
    	Bundle bundle = getIntent().getExtras();
    	
    	//realname=bundle.getString("realname");
    	userID=bundle.getString("userid");
    	
		if(!if_friend){                       //這一塊要不要成為好友，之後會移到RandomFriendActivity
		mThread =new HandlerThread("name");
		mThread.start();
		mThHandler=new Handler(mThread.getLooper());
		mThHandler.post(r1);
		}
		else {
		    TextView viewNewFriend =(TextView)findViewById(R.id.ViewNewFriend);
			Addfriend.setVisibility(View.INVISIBLE);
			viewNewFriend.setText("已成為好友");
			
		}

		
		TomakefriendView();
		
	
	}
	private Runnable r1 = new Runnable() {    //持續找是不是好友
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("QuickplayActivity2");
			while(!terminate){
				//System.out.println("LOOP");
					if(SendLike!=null && ReceiveLike!=null){
						ParseObject testObject = new ParseObject(table_name);    //上傳好朋友 id 和自己的ID
						testObject.put(Globalvariable.StringUserID,currentUser.getObjectId());   
						testObject.put(Globalvariable.StringFriendID,RecipientId);
						testObject.saveInBackground();
						terminate=true;   //找到即不用在持續找
						mUI_Handler.post(r2);


					      //老闆指定每隔幾秒要做一次工作1 (單位毫秒:1000等於1秒)
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}	
			}
			
		}
	};
	private Runnable r2 =new Runnable() {  //畫面控制button textview
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("QuickplayActivity3");

			
			    TextView viewNewFriend =(TextView)findViewById(R.id.ViewNewFriend);
				Addfriend.setVisibility(View.INVISIBLE);
				viewNewFriend.setText("已成為好友");
				//要存成已成為好友的state 在Quickplay裏面就不顯示這個人*************************
				if_friend=true;
				System.out.println("已成為好友");
			
			
		}
	};
	
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onPause(){
		if(if_friend==true){
			System.out.println("if_friend"+if_friend);
			if_friend=true;
		}else {
			System.out.println("if_friend"+if_friend);
			if_friend=false;
		}
		
		
		super.onPause();
	}
	
    @Override
    public void onDestroy() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().removeMessageClientListener(this);
            getSinchServiceInterface().stopClient();
        }
		if(SendLike!=null && ReceiveLike!=null){
			SendLike=null;
			ReceiveLike=null;
		}
        super.onDestroy();
    }
    @Override
    public void onStop() {
        Log.w(TAG, "App stopped");

        super.onStop();
    }
    
	
	private OnClickListener addfriend =new OnClickListener() {     //互相加入好友的機制
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
            sendMessage();   //既送出訊息

			
		 // Addfriend.setEnabled(false);
		}
		
	};
	
    @Override
    public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {
        // Left blank intentionally
    }
    
    @Override
    public void onMessageFailed(MessageClient client, Message message,
            MessageFailureInfo failureInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending failed: ")
                .append(failureInfo.getSinchError().getMessage());

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, sb.toString());
    }
    
    
    @Override
    public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {
        Log.d(TAG, "onDelivered");
    }

    @Override
    public void onServiceConnected() {
        getSinchServiceInterface().addMessageClientListener(this);
        setButtonEnabled(true);
    }

    @Override
    public void onServiceDisconnected() {
        setButtonEnabled(false);
    }
    private void setButtonEnabled(boolean enabled) {
        Addfriend.setEnabled(enabled);
    }
	
	public void sendMessage(){               //送的人ID
		
        String recipient = userID;           //aWKOK5Q2oh=>蔡足兒 1vuUpOQS32=>蔡育銓
        if (recipient.isEmpty()) {
            Toast.makeText(this, "No recipient added", Toast.LENGTH_SHORT).show();
            return;
        }
         
        getSinchServiceInterface().sendMessage(recipient, "Hi"); 
        //*****由getSinchService去做sendmessage********!!!!!!!!!!!!
        
        //finish();  //refresh頁面
        //startActivity(getIntent());
		
		
	}
	   @Override
	    public void onIncomingMessage(MessageClient client, Message message) {
	    	System.out.println("QuickplayActivity"+message.getTextBody()+client.toString());
	    	ReceiveLike=message.getTextBody();
	    }

	    @Override    //MessageClientListener
	    public void onMessageSent(MessageClient client, Message message, String recipientId) { //client是自己 sinch的onMessageSent
	      
	    	System.out.println("QuickplayActivityonMessageSent"+message.getTextBody()+" "+recipientId);
	    	SendLike=message.getTextBody();
	    	RecipientId=recipientId;
	    }



	public void TomakefriendView(){
	final TextView   ParseRealname =(TextView)findViewById(R.id.ParseRealname);
	final TextView ParseUsualplace =(TextView)findViewById(R.id.ParseUsualplace);
	final TextView ParseUsualtime =(TextView)findViewById(R.id.ParseUsualtime);
    ParseUser currentUser = ParseUser.getCurrentUser();
    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");
    System.out.println("UserID"+currentUser.getObjectId());
    tablequery.whereEqualTo("UserID",userID );  //限制得到你點的人的
    
    
    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取其他人的資料，看是否要交朋友，之後會顯示多朋友
    	@Override
		public void done(List<ParseObject> me, ParseException e) {
    		if(e==null){
    			int i=0;
    			for(;i<me.size();i++){
    				realname=me.get(i).getString(com.parse.starter.Globalvariable.Realname).toString();
    				usualplace=me.get(i).getString(com.parse.starter.Globalvariable.UsualPlace).toString();
    				usualtime=me.get(i).getString(com.parse.starter.Globalvariable.Usualtime).toString();
    				//userID  = me.get(i).getString(com.parse.starter.Globalvariable.StringUserID).toString();
    		        image =(ParseFile)me.get(i).get("Photo");	        

    			}
    			
    			
    		    image.getDataInBackground(new GetDataCallback() {
					
					@Override
					public void done(byte[] data, ParseException e) {
						// TODO Auto-generated method stub
						if(e==null){
			    			System.out.println("personalprofile"+" "+data.length);
                            final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                            // Get the ImageView from main.xml
                            //ImageView image = (ImageView) findViewById(R.id.ad1);
                            final ParseImageView imageView = (ParseImageView)findViewById(R.id.personalprfile);

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
    			

				System.out.println("realname"+realname+" "+userID);
				ParseUsualtime.setText(usualtime);
				ParseUsualplace.setText(usualplace);
				ParseRealname.setText(realname);


				

    		}
    	}
    });
    
    

}
	
}
