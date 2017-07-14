package com.gogocarpon.gogocarpon._app;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealDatabaseHelper;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;

import java.util.ArrayList;


public class DealListFragmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();

        // Create the list fragment and add it as our sole content.
        if (fm.findFragmentById(android.R.id.content) == null) {
        	DealListFragment list = new DealListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    public static class DealListFragment extends Fragment {
    	
    	protected View viewLayout;
    	protected ListView mainListView;
    	protected DealListAdapter dealListAdapter;
    	protected ArrayList<Deal> arrData;
    	protected ImageLoader imageLoader;

    	protected int dealStatus = 1;
    	protected int dealCat = 0;
    	
        // Container Activity must implement this interface
    	protected DealListSelectedListener mCallback;
        public interface DealListSelectedListener {
            public void onDealSelected(Deal rowDeal, int position);
            public void setDevice();
        }

        public static DealListFragment newInstance(int position) {
            DealListFragment f = new DealListFragment();
            Bundle b = new Bundle();
            b.putInt("dealStatus", position+1);
            f.setArguments(b);
            return f;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (DealListSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        	viewLayout = inflater.inflate(R.layout.act_deal_list,
                                         container, false);
    		// Find the ListView resource.
    		mainListView = (ListView) viewLayout.findViewById(R.id.mainListView);
            mainListView.setDividerHeight(30);
            mainListView.getDivider().setColorFilter(Color.LTGRAY, PorterDuff.Mode.CLEAR);
    		arrData = new ArrayList<Deal>();
            
        	return viewLayout;
        }
        
    	@Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            
            dealStatus = getArguments().getInt("dealStatus");
//            dealCat = getArguments().getInt("dealCat");
            dealCat = Integer.parseInt(UserInfo.getInstance().cat_id);
            
            
            Log.i("LOG APP", "dealStatus = " + dealStatus);

            // Give some text to display if there is no data.  In a real
            // application this would come from a resource.
//            setEmptyText("No phone numbers");

            // We have a menu item to show in action bar.
            setHasOptionsMenu(true);
            
            Log.i("LOG APP", "onCreate : FeatureDealActivity");
            
    		dealListAdapter = new DealListAdapter(getActivity(), arrData);
    		mainListView.setAdapter(dealListAdapter);
            mCallback.setDevice();
    		
    		mainListView.setOnItemClickListener(new OnItemClickListener() {
    			public void onItemClick(AdapterView<?> parent, View view,
    					int position, long id) {

    				Deal rowDeal = arrData.get(position);
    				
    				// Call back to parent
    				mCallback.onDealSelected(rowDeal, position);
    				
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

    		this.loadDealFromDatabase();
    		
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
			Log.i("LOG APP : onResume", "dealStatus = " + dealStatus);
		}
        
    	protected void loadDealFromDatabase() {

    		Log.i("LOG APP", "dealStatus == " + dealStatus);
    		
    		DealDatabaseHelper db = new DealDatabaseHelper(getActivity());

    		// Empty arrData
    		arrData.clear();
    		
    		try {

    			Cursor c = db.getAll(dealStatus,dealCat);
    			int numRows = c.getCount();
    			c.moveToFirst();
    			for (int i = 0; i < numRows; ++i) {
    				
    				Deal obj = new Deal();

    				obj._id = c.getString(c.getColumnIndex(DealDatabaseHelper.colID));
    				obj._deal_status = c.getString(c.getColumnIndex(DealDatabaseHelper.colDealStatus));
    				obj._deal_code = c.getString(c.getColumnIndex(DealDatabaseHelper.colDealCode));
    				obj._name = c.getString(c.getColumnIndex(DealDatabaseHelper.colName));

    				obj._pic_dir = c.getString(c.getColumnIndex(DealDatabaseHelper.colPicDir));
    				
    				obj._start_at = c.getString(c.getColumnIndex(DealDatabaseHelper.colStartAt));
    				obj._end_at = c.getString(c.getColumnIndex(DealDatabaseHelper.colEndAt));

    				obj._price = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colPrice));
    				obj._origin_price = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colOriginPrice));
    				obj._discount = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colDiscount));
    				obj._prepay_percent = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colPrepayPercent));

    				obj._short_desc = c.getString(c.getColumnIndex(DealDatabaseHelper.colShortDect));
    				obj._highlight = c.getString(c.getColumnIndex(DealDatabaseHelper.colHighlight));
    				
    				obj._desc = c.getString(c.getColumnIndex(DealDatabaseHelper.colDest));
    				obj._terms = c.getString(c.getColumnIndex(DealDatabaseHelper.colTerms));
    				
    				obj._merchant_id = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantID));
    				obj._merchant_name = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantName));
    				obj._merchant_address = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantAddress));
    				obj._merchant_telephone = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantTelephone));
    				obj._merchant_long = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantLong));
    				obj._merchant_lat = c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantLat));
    				
//    				Log.i("LOG APP -- _merchant_name", c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantName)));
    				
    				obj._price_buy = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colPriceBuy));
//    				Log.i("LOG APP", "1");
    				
    				obj._use_dynamic = c.getInt(c.getColumnIndex(DealDatabaseHelper.colUserDynamic));
//    				Log.i("LOG APP", "2");
    				
    				obj._hot_deal = c.getInt(c.getColumnIndex(DealDatabaseHelper.colHotDeal));
//    				Log.i("LOG APP", "3");
    				
    				obj._video_desc = c.getString(c.getColumnIndex(DealDatabaseHelper.colVideoDesc));
//    				Log.i("LOG APP", "4");
    				
    				obj._price_step = obj.loadPriceStep(c.getString(c.getColumnIndex(DealDatabaseHelper.colPriceStep)));
//    				Log.i("LOG APP", "5");
    				
    				obj._deal_types = obj.loadDealType(c.getString(c.getColumnIndex(DealDatabaseHelper.colDealTypes)));
//    				Log.i("LOG APP", "6");
//    				obj._currency_code = c.getString(c.getColumnIndex(DealDatabaseHelper.currency_code));
    				arrData.add(obj);
    				
    				c.moveToNext();
    			}
    			c.close();
    			db.close();
    		} catch (SQLException e) {
    			Log.e("Exception on query", e.toString());
    		}

    		dealListAdapter.notifyDataSetChanged();

    	}

    }

}
