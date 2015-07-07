package com.quickplay;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Globalvariable;
import com.parse.starter.HomeActivity;
import com.parse.starter.MessageAdapter;
import com.parse.starter.R;
import com.parse.starter.R.color;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class QuickplayActivity extends QuickbaseActivity  implements MessageClientListener  {
	
    private static final String TAG = "QuickplayActivity";
	String realname="";
	String usualplace="";
	String usualtime="";
	String userID="";
	Button Addfriend;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quickplay);
	    Addfriend =(Button)findViewById(R.id.addfriend);

		
		
		//ParseImageView personalprfile = (ParseImageView) findViewById(R.id.personalprfile);
		Addfriend.setOnClickListener(addfriend);
		
		TomakefriendView();
		
	
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
    public void onDestroy() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().removeMessageClientListener(this);
            getSinchServiceInterface().stopClient();
        }
        super.onDestroy();
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
	
	public void sendMessage(){
		
        String recipient = userID;           //aWKOK5Q2oh=>蔡足兒 1vuUpOQS32=>蔡育銓
        if (recipient.isEmpty()) {
            Toast.makeText(this, "No recipient added", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        getSinchServiceInterface().sendMessage(Globalvariable.recipient, "Hi"); 
        //*****由getSinchService去做sendmessage********
		
		
	}
	   @Override
	    public void onIncomingMessage(MessageClient client, Message message) {
	    	System.out.println("QuickplayActivity"+message.getTextBody());
	       // mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING); //mMessageAdapter
	    }

	    @Override    //MessageClientListener
	    public void onMessageSent(MessageClient client, Message message, String recipientId) { //client是自己 sinch的onMessageSent
	      
	    	System.out.println("QuickplayActivity"+message.getTextBody()+" "+recipientId);
	    	//  mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
	    }



	public void TomakefriendView(){
	final TextView   ParseRealname =(TextView)findViewById(R.id.ParseRealname);
	final TextView ParseUsualplace =(TextView)findViewById(R.id.ParseUsualplace);
	final TextView ParseUsualtime =(TextView)findViewById(R.id.ParseUsualtime);
    ParseUser currentUser = ParseUser.getCurrentUser();
    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");
    System.out.println("UserID"+currentUser.getObjectId());
    tablequery.whereNotEqualTo("UserID", currentUser.getObjectId());  //限制從自己id取得
    
    
    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取其他人的資料，看是否要交朋友，之後會顯示多朋友
    	public void done(List<ParseObject> me, ParseException e) {
    		if(e==null){
    			int i=0;
    			for(;i<me.size();i++){
    				realname=me.get(i).getString(com.parse.starter.Globalvariable.Realname).toString();
    				usualplace=me.get(i).getString(com.parse.starter.Globalvariable.UsualPlace).toString();
    				usualtime=me.get(i).getString(com.parse.starter.Globalvariable.Usualtime).toString();
    				userID  = me.get(i).getString(com.parse.starter.Globalvariable.StringUserID).toString();

    			}
				System.out.println("realname"+realname+" "+userID);
				ParseUsualtime.setText(usualtime);
				ParseUsualplace.setText(usualplace);
				ParseRealname.setText(realname);


				

    		}
    	}
    });
    
    

}
	
}
