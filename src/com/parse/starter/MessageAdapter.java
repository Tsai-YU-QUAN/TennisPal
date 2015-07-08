/*package com.parse.starter;

import java.text.SimpleDateFormat;   //以後要做一個array 可以快速查詢id=>name
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.sinch.android.rtc.messaging.Message;

public class MessageAdapter extends BaseAdapter {

    public static final int DIRECTION_INCOMING = 0;

    public static final int DIRECTION_OUTGOING = 1;

    private List<Pair<Message, Integer>> mMessages;

    private SimpleDateFormat mFormatter;

    private LayoutInflater mInflater;
    Message message;
    String name ;
    
    TextView txtSender ;
    TextView txtMessage ;
    TextView txtDate ;
    public MessageAdapter(Activity activity) {
        mInflater = activity.getLayoutInflater();
        mMessages = new ArrayList<Pair<Message, Integer>>();
        mFormatter = new SimpleDateFormat("HH:mm");
    }

    public void addMessage(Message message, int direction) {
        mMessages.add(new Pair(message, direction));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return mMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i) {
        return mMessages.get(i).second;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int direction = getItemViewType(i);

        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
            }
            convertView = mInflater.inflate(res, viewGroup, false);
        }
         message = mMessages.get(i).first;
        // SenderIDtoRealname();
         Tolistmessage();
        txtSender = (TextView) convertView.findViewById(R.id.txtSender);
        txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);



        return convertView;
    }
    
    
    public void Tolistmessage(){
	//for(int i=0;i<me.size();i++){
	System.out.println("MessageAdapter"+" ");
	if(message.getSenderId()=="y2jHYT5M0O"){
		Globalvariable.sendername="蔡美兒";
	}else if(message.getSenderId()=="aWKOK5Q2oh"){
		Globalvariable.sendername="蔡足兒";
	}else if(message.getSenderId()=="1vuUpOQS32"){
		Globalvariable.sendername="蔡育銓";
	}
	
	
	//if(realname!=null){
         name = Globalvariable.sendername;
         txtSender.setText(name);  
         txtMessage.setText(message.getTextBody());
         txtDate.setText(mFormatter.format(message.getTimestamp()));
    }
    
    public void SenderIDtoRealname(){
	    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");

	   tablequery.whereEqualTo("UserID", message.getSenderId());  // 一堆陌生人的id和自己的id  
	    
	    
	    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取文字
	    	public void done(List<ParseObject> me, ParseException e) {
	    		if(e==null){
	    			
	    			//for(int i=0;i<me.size();i++){
	    			System.out.println("MessageAdapter"+" "+" "+me.size());
	    			Globalvariable.sendername=me.get(0).get(Globalvariable.Realname).toString();//可能會因為Realname==null造成crash
	    			
	    			
	    			//if(realname!=null){
		    			System.out.println("MessageAdapter"+" "+Globalvariable.sendername+" "
		    					+message.getTextBody());
		    	         name = Globalvariable.sendername;
		    	         txtSender.setText(name);  
		    	         txtMessage.setText(message.getTextBody());
		    	         txtDate.setText(mFormatter.format(message.getTimestamp()));

	    			//}
	    			//}
	    			
	    			//ParseRealname.setText(realname);
	    		}
	    		else{
	    			System.out.println("MessagingActivityerror");
	    		}
	    	}});

    	
    }
}
    */



package com.parse.starter;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinch.android.rtc.messaging.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    public static final int DIRECTION_INCOMING = 0;

    public static final int DIRECTION_OUTGOING = 1;

    private List<Pair<Message, Integer>> mMessages;

    private SimpleDateFormat mFormatter;

    private LayoutInflater mInflater;

    public MessageAdapter(Activity activity) {
        mInflater = activity.getLayoutInflater();
        mMessages = new ArrayList<Pair<Message, Integer>>();
        mFormatter = new SimpleDateFormat("HH:mm");
    }

    public void addMessage(Message message, int direction) {
        mMessages.add(new Pair(message, direction));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return mMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int i) {
        return mMessages.get(i).second;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int direction = getItemViewType(i);

        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
            }
            convertView = mInflater.inflate(res, viewGroup, false);
        }

        Message message = mMessages.get(i).first;
        String name = message.getSenderId();

        TextView txtSender = (TextView) convertView.findViewById(R.id.txtSender);
        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        if(name.equals("aWKOK5Q2oh")){     //之後要在FriendActivity就要存FriendID和USername的關係
        	name="蔡足兒";
        }else{
        	name="蔡育銓";

        }
        	     //aWKOK5Q2oh=>蔡足兒 1vuUpOQS32=>蔡育銓
        txtSender.setText(name);  
        txtMessage.setText(message.getTextBody());
        txtDate.setText(mFormatter.format(message.getTimestamp()));

        return convertView;
    }
}


