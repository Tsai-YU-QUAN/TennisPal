package changefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.parse.personal.profile.FriendFragment;
import com.parse.personal.profile.PersonalprofileFragment;
import com.parse.starter.Globalvariable;
import com.parse.starter.R;
import com.quickplay.RandomFriendFragment;

public class FragmentChangeActivity extends SlidingFragmentActivity {   //實作SlidingFragmentActivity
	
	private Fragment mContent;
	String ExtraString="";
	
	public FragmentChangeActivity() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View

		System.out.println("ExtraString"+ExtraString);
		
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null){
			
			if(Globalvariable.Extrapersonal=="personal"){
				System.out.println("ExtraString2");
				Globalvariable.Extrapersonal="";
				mContent = new PersonalprofileFragment();	    //跑去PersonalprofileFragment
			}else if(Globalvariable.ExtraQuickplay=="quick_play"){
				System.out.println("ExtraString3");
				Globalvariable.ExtraQuickplay="";
				mContent = new RandomFriendFragment();	     //跑去RandomFriendFragment
			}else if(Globalvariable.ExtraTournament=="tournament"){
				System.out.println("ExtraString4");
				mContent = new FriendFragment();	     //跑去FriendFragment
				
			}
			else {
				mContent = new PersonalprofileFragment();	    //如果都找不到就跑去PersonalprofileFragment
			}
			


		}
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)             
		.commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);     //左邊list的值
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new SportMenuFragment())  // 左邊list的值 ColorMenuFragment
		.commit();
		
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);  
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSlidingMenu().setTouchModeAbove(SlidingMenu.RIGHT); //整個頁面會在拿一個頁面都會進行滑動
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		System.out.println("switch");
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();
		getSlidingMenu().showContent();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {       // list跑出來要按的鍵
		switch (item.getItemId()) {
		case android.R.id.home:
			System.out.println("toggleOK");
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
