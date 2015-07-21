package com.quickplay;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

public class RandomFriendFragment extends Fragment {   //// 之後要修正成真正的Random Friend，從這邊下手
	
	String table_name="personaltable";
	String realname;
	String id;
    private LinkedList<String> mListName =new LinkedList<String>();
    private LinkedList<String> mListID =new LinkedList<String>();
    private ListView listView;
	ArrayAdapter adapter;
	private View v;

	
	public RandomFriendFragment() {
		//mColorRes = colorRes;
		//setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LayoutInflater lf = getActivity().getLayoutInflater();   
		//if (savedInstanceState != null)
			//mColorRes = savedInstanceState.getInt("mColorRes");
		//int color = getResources().getColor(mColorRes);
		// construct the RelativeLayout
		  v = inflater.inflate(R.layout.randomfriend, container, false);
		  
			listView=(ListView)v.findViewById(R.id.Listview);
			
		    ParseUser currentUser = ParseUser.getCurrentUser();
			ParseQuery<ParseObject> query =ParseQuery.getQuery(table_name);
			query.whereNotEqualTo("UserID", currentUser.getObjectId());
			query.findInBackground(new FindCallback<ParseObject>() {
				
				@Override
				public void done(List<ParseObject> randomfriends, ParseException e) {
					// TODO Auto-generated method stub
					if(e==null){
						for(int i=0;i<randomfriends.size();i++){   //先從parse Load friend的資訊丟入QuickplayActivity
							realname=randomfriends.get(i).getString(com.parse.starter.Globalvariable.Realname).toString();
							id      =randomfriends.get(i).getString(com.parse.starter.Globalvariable.StringUserID).toString();
							mListName.add(realname);
							mListID.add(id);

		
							
							ArrayAdapter<String>  adapter = new 
									ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, mListName);
					         //simple_list_item_1 一行text
					         //simple_list_item_2 一行text較大，一行text較小
							listView.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							
							

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
	        		 System.out.println("mListID"+mListID.get(position));
	        		 Intent intent =new Intent();
	        		 intent.setClass(getActivity(), QuickplayActivity.class);
	        		 //intent.putExtra("realname", mListName.get(position));   ///取得名字傳到MessageActivity
	        		 intent.putExtra("userid", mListID.get(position));   ///取得名字傳到MessageActivity
	        		 
	        		 //intent.putExtra("friendID", friendID);
	        		 startActivity(intent);
	        	 }
	        	
	        });
		  
		  return v;
	}
	

}
