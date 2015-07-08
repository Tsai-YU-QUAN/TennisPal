package com.quickplay;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.Globalvariable;
import com.parse.starter.MessagingActivity;
import com.parse.starter.R;

public class FriendActivity extends Activity {   ////Activity 和 Listactivity會和xml有很大的關係，也有可能造成error
	
	String table_name="friend";
	String friendID;
    private LinkedList<String> mListItems =new LinkedList<String>();
    private ListView listView;
	ArrayAdapter adapter;

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);
		listView=(ListView)findViewById(R.id.Listview);
		
	    ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query =ParseQuery.getQuery(table_name);
		query.whereEqualTo(Globalvariable.StringUserID, currentUser.getObjectId());
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> friends, ParseException e) {
				// TODO Auto-generated method stub
				if(e==null){
					for(int i=0;i<friends.size();i++){
						friendID=friends.get(i).getString(com.parse.starter.Globalvariable.StringFriendID).toString();
					    ParseQuery<ParseObject> tablequery = ParseQuery.getQuery("personaltable");

						   tablequery.whereEqualTo("UserID", friendID);  //從friend_table 到 personal_table
						                                                // id to friendname
						            
						    tablequery.findInBackground(new FindCallback<ParseObject>() {        //讀取文字
						    	public void done(List<ParseObject> me, ParseException e) {
						    		if(e==null){
						    			
						    			//for(int i=0;i<me.size();i++){
						    			System.out.println("MessagingActivity"+" "+" "+me.size());
						    			Globalvariable.receiptname=me.get(0).get(Globalvariable.Realname).toString();//可能會因為Realname==null造成crash
										mListItems.add(Globalvariable.receiptname);
						    			//mTxtRecipient.setText(Globalvariable.realname);
						    			
						    			
						    			//if(realname!=null){
							    			System.out.println("MessagingActivity"+" "+Globalvariable.receiptname);
						    			//}
						    			//}
						    			
						    			//ParseRealname.setText(realname);
						    		}
						    		else{
						    			System.out.println("MessagingActivityerror");
						    		}
						    		
									//Target.add(String_Name);
									ArrayAdapter<String>  adapter = new 
											ArrayAdapter(FriendActivity.this,android.R.layout.simple_list_item_1, mListItems);
							         //simple_list_item_1 一行text
							         //simple_list_item_2 一行text較大，一行text較小
									listView.setAdapter(adapter);
									adapter.notifyDataSetChanged();
						    	}
						    	
						    
						    
						    
						    });			
						
						    

						
						

					}
					
				}
				else{
					System.out.println("SthError");
				}
				
			}
		});
		
		
		
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {   //friend list 去選擇你要的朋友聊天
        	 @Override                                                            //以後java要做ID to Realname
        	 
        	 public void onItemClick(AdapterView<?> parent, View view, int position,
                     long id){
        		 
        		 Intent intent =new Intent();
        		 intent.setClass(FriendActivity.this, MessagingActivity.class);
        		 intent.putExtra("receiptname", mListItems.get(position));   ///取得名字傳到MessageActivity
        		 intent.putExtra("friendID", friendID);
        		 startActivity(intent);
        	 }
        	
        });
		

		
	}
}
