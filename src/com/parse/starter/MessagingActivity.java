package com.parse.starter;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;


public class MessagingActivity extends BaseActivity implements MessageClientListener{

    private static final String TAG = "MessagingActivity";

    private MessageAdapter mMessageAdapter;
    private TextView mTxtRecipient;
    private EditText mTxtTextBody;
    private Button mBtnSend;
    String friendID;
    String receiptname;
  //  String realname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
    	System.out.println("PleaseGO4");
    	
    	Bundle bundle = getIntent().getExtras();
    	
    	receiptname=bundle.getString("receiptname");
    	friendID=bundle.getString("friendID");

    	
    	System.out.println("receiptname"+receiptname+" "+friendID);
        mTxtRecipient = (TextView) findViewById(R.id.txtRecipient);
        mTxtTextBody = (EditText) findViewById(R.id.txtTextBody);
		mTxtRecipient.setText(receiptname);

       // IDtoRealname();  //以後要改進，儘量存在Local上，儘量減少網路上的不必要的溝通  table?
       // RealnametoID();  //id->realname(find frirend)  realname->id(modelue)
        mMessageAdapter = new MessageAdapter(this);
        ListView messagesList = (ListView) findViewById(R.id.lstMessages);
        messagesList.setAdapter(mMessageAdapter);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();   //即送出訊息
            }
        });
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

    private void sendMessage() {
    	
        String recipient = friendID;           //aWKOK5Q2oh=>蔡足兒 1vuUpOQS32=>蔡育銓
        String textBody = mTxtTextBody.getText().toString(); 
        if (recipient.isEmpty()) {
            Toast.makeText(this, "No recipient added", Toast.LENGTH_SHORT).show();
            return;
        }
        if (textBody.isEmpty()) {
            Toast.makeText(this, "No text message", Toast.LENGTH_SHORT).show();
            return;
        }
        
        
        getSinchServiceInterface().sendMessage(recipient, textBody);   //由getSinchService去做sendmessage
        mTxtTextBody.setText("");
    }

    private void setButtonEnabled(boolean enabled) {
        mBtnSend.setEnabled(enabled);
    }

    @Override
    public void onIncomingMessage(MessageClient client, Message message) {
    	System.out.println("PleaseGO4訊息進來"+message);
        mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING); //mMessageAdapter
    }

    @Override    //MessageClientListener
    public void onMessageSent(MessageClient client, Message message, String recipientId) { //client是自己 sinch的onMessageSent
        mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
    }

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
    
   /* public void IDtoRealname(){
	    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");

	   tablequery.whereEqualTo("FriendID", friendID);  // 一堆陌生人的id  => 取得你想要交到朋友的id
	    
	    
	    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取文字
	    	public void done(List<ParseObject> me, ParseException e) {
	    		if(e==null){
	    			
	    			//for(int i=0;i<me.size();i++){
	    			System.out.println("MessagingActivity"+" "+" "+me.size());
	    			Globalvariable.realname=me.get(0).get(Globalvariable.Realname).toString();//可能會因為Realname==null造成crash
	    			mTxtRecipient.setText(Globalvariable.realname);
	    			
	    			
	    			//if(realname!=null){
		    			System.out.println("MessagingActivity"+" "+Globalvariable.realname);
	    			//}
	    			//}
	    			
	    			//ParseRealname.setText(realname);
	    		}
	    		else{
	    			System.out.println("MessagingActivityerror");
	    		}
	    	}});

    	
    }*/

    
    public void RealnametoID(){
    	
    }
    

}
