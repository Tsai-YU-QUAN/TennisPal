package changefragment;

import com.parse.starter.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Personal extends Fragment {
	//private int mColorRes = -1;
	private View v;

	
/*	public Personal() {
		//mColorRes = colorRes;
		//setRetainInstance(true);
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //LayoutInflater lf = getActivity().getLayoutInflater();   
		//if (savedInstanceState != null)
			//mColorRes = savedInstanceState.getInt("mColorRes");
		//int color = getResources().getColor(mColorRes);
		// construct the RelativeLayout
		  v = inflater.inflate(R.layout.personal, container, false);
		      TextView textView =(TextView) v.findViewById(R.id.Realname);
		      textView.setText("GOODing");
		      
		      if(getArguments() != null) {
		            Float result=getArguments().getFloat("RESULT");
		            textView.setText(String.valueOf(result));
		        } else {
		        	//textView.setText("result not included");
		        }
		      
		      return v;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putInt("mColorRes", mColorRes);
	}
	
}
