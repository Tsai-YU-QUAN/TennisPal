package changefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.personal.profile.FriendFragment;
import com.parse.personal.profile.PersonalprofileFragment;
import com.parse.starter.R;
import com.quickplay.RandomFriendFragment;


public class SportMenuFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] sport_names = getResources().getStringArray(R.array.sport_names);  //給list有個名字
		ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, android.R.id.text1, sport_names);
		setListAdapter(colorAdapter);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {   //左邊可以選擇是哪一個item
		Fragment newContent = null;
		switch (position) {
		case 0:
			System.out.println("準備進入Personal");
			newContent = new PersonalprofileFragment();  //跳到Personal
			break;
		case 1:
			System.out.println("準備進入tournament");
			newContent=new RandomFriendFragment();
			break;
			
		case 2:
			System.out.println("準備進入tournament");
			newContent=new FriendFragment();
			break;
			
		}
		if (newContent != null)
			switchFragment(newContent);   //選完list的其中一項後，就跳到某一個fragment
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);   //連到FragmentChangeActivity
		} 
	}
	


}
