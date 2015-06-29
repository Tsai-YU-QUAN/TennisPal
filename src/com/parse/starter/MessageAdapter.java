package com.parse.starter;

import java.text.SimpleDateFormat;
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
         SenderIDtoRealname();

        txtSender = (TextView) convertView.findViewById(R.id.txtSender);
        txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        txtDate = (TextView) convertView.findViewById(R.id.txtDate);



        return convertView;
    }
    
    
    
    
    public void SenderIDtoRealname(){
	    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");

	   tablequery.whereEqualTo("UserID", message.getSenderId());  // 一堆陌生人的id  => 取得你想要交到朋友的id
	    
	    
	    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取文字
	    	public void done(List<ParseObject> me, ParseException e) {
	    		if(e==null){
	    			
	    			//for(int i=0;i<me.size();i++){
	    			System.out.println("MessageAdapter"+" "+" "+me.size());
	    			Globalvariable.sendername=me.get(0).get(Globalvariable.Realname).toString();//可能會因為Realname==null造成crash
	    			
	    			
	    			//if(realname!=null){
		    			System.out.println("MessageAdapter"+" "+Globalvariable.sendername);
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
