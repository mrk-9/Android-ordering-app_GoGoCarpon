package com.gogocarpon.gogocarpon._app;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealType;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealStyleListAdapter;


public class DealStyleFragmentActivity extends ActionBarActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
        	DealStyleListFragment list = new DealStyleListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }


    public static class DealStyleListFragment extends DialogFragment {
    	
    	protected View viewLayout;
    	protected ListView mainListView;
    	protected DealStyleListAdapter dealListAdapter;
    	protected ArrayList<DealType> arrData;
    	
        // Container Activity must implement this interface
    	protected DealStyleListSelectedListener mCallback;
        public interface DealStyleListSelectedListener {
            public void onDealStyleSelected(DealType rowDealStyle, int position);
        }
    	
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (DealStyleListSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            // Pick a style based on the num.
//            int style = DialogFragment.STYLE_NORMAL, theme = 0;
//            switch ((mNum-1)%6) {
//                case 1: style = DialogFragment.STYLE_NO_TITLE; break;
//                case 2: style = DialogFragment.STYLE_NO_FRAME; break;
//                case 3: style = DialogFragment.STYLE_NO_INPUT; break;
//                case 4: style = DialogFragment.STYLE_NORMAL; break;
//                case 5: style = DialogFragment.STYLE_NO_TITLE; break;
//                case 6: style = DialogFragment.STYLE_NO_FRAME; break;
//                case 7: style = DialogFragment.STYLE_NORMAL; break;
//            }
//            switch ((mNum-1)%6) {
//                case 2: theme = android.R.style.Theme_Panel; break;
//                case 4: theme = android.R.style.Theme; break;
//                case 5: theme = android.R.style.Theme_Light; break;
//                case 6: theme = android.R.style.Theme_Light_Panel; break;
//                case 7: theme = android.R.style.Theme_Light; break;
//            }

//            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Panel);

        }
        
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.setTitle("Choose your deal:");
            return dialog;
        }        
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        	
        	viewLayout = inflater.inflate(R.layout.act_deal_list,
                                         container, false);
    		// Find the ListView resource.
    		mainListView = (ListView) viewLayout.findViewById(R.id.mainListView);
    		
    		Deal rowDeal = (Deal) getArguments().getSerializable("rowDeal");
    		arrData = rowDeal._deal_types;
    		
//    		arrData = new ArrayList<DealType>();
//    		DealType d1 = new DealType();
//    		d1._id = "1";
//    		d1._name = "Deal style 1";
//    		d1._origin_price = 12.12f;
//    		arrData.add(d1);
//    		
//    		DealType d2 = new DealType();
//    		d2._id = "1";
//    		d2._name = "Deal style 2";
//    		d2._origin_price = 12.12f;
//    		arrData.add(d2);
//
//    		DealType d3 = new DealType();
//    		d3._id = "1";
//    		d3._name = "Deal style 3";
//    		d3._origin_price = 12.12f;
//    		arrData.add(d3);
            
        	return viewLayout;
        }
        
    	@Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
//            dealStatus = getArguments().getInt("dealStatus");

            // Give some text to display if there is no data.  In a real
            // application this would come from a resource.
//            setEmptyText("No phone numbers");

            // We have a menu item to show in action bar.
//            setHasOptionsMenu(true);
            
            Log.i("LOG APP", "onCreate : FeatureDealActivity");
            
    		dealListAdapter = new DealStyleListAdapter(getActivity(), arrData);
    		mainListView.setAdapter(dealListAdapter);
    		
    		mainListView.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View view,
    					int position, long id) {

    				DealType rowDealStyle = arrData.get(position);
    				
    				// Call back to parent
    				mCallback.onDealStyleSelected(rowDealStyle, position);
    				
//    				ActParameter params = new ActParameter();
//    				params.put("rowDeal", rowDeal);
//    				
//    				Log.i("LOG APP", "deal_status = " + rowDeal._deal_status);
//    				
//    				_intentManager.OpenActivity(IntentManager.DEAL_DETAIL_ACTIVITY, params);

    			}
    		});		
    		
//            setListAdapter(dealListAdapter);
//            setListAdapter(new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1, DealListFragment.CONTACTS_SUMMARY_PROJECTION));
        }
        
    	@Override
		public void onStart() {
    		// TODO Auto-generated method stub
    		super.onStart();
    		Log.i("LOG APP", "onStart : loadDealFromWebService");

//    		this.loadDealFromDatabase();
    		
    		// Display notify when arrData empty
    		TextView txtNotify = (TextView) viewLayout.findViewById(R.id.txtNotify);
    		if(arrData.isEmpty()) {
    			mainListView.setVisibility(View.GONE);
    			txtNotify.setVisibility(View.VISIBLE);
    		} else {
    			mainListView.setVisibility(View.VISIBLE);
    			txtNotify.setVisibility(View.GONE);			
    		}
    		
    	}
    	
        @Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}
        
    	protected void loadDealFromDatabase() {

    		dealListAdapter.notifyDataSetChanged();

    	}

    }

}
