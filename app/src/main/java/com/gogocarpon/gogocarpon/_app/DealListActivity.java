package com.gogocarpon.gogocarpon._app;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealDatabaseHelper;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;


public class DealListActivity extends BaseActivity {

	protected ListView mainListView;
	protected DealListAdapter dealListAdapter;
	protected ArrayList<Deal> arrData;
	protected ImageLoader imageLoader;

	protected DealListActivity DealListActivityRef = null;

	public static final int REQUEST_DEALS = 1;
	
	protected int dealStatus = 1;	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_deal_list);

		DealListActivityRef = this;

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);
		arrData = new ArrayList<Deal>();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.i("LOG APP", "onStart : loadDealFromWebService");

		this.loadDealFromDatabase();
		
		// Display notify when arrData empty
		TextView txtNotify = (TextView) findViewById(R.id.txtNotify);
		if(arrData.isEmpty()) {
			mainListView.setVisibility(View.GONE);
			txtNotify.setVisibility(View.VISIBLE);
		} else {
			mainListView.setVisibility(View.VISIBLE);
			txtNotify.setVisibility(View.GONE);			
		}
		
	}

	@Override
	protected void onResume() {

		Log.i("LOG APP", "onResume : DealListActivity");
		// TODO Auto-generated method stub
		super.onResume();

		Log.i("LOG APP", "onResume : DealListActivity");

	}

	protected void loadDealFromDatabase() {

		Log.i("LOG APP", "dealStatus == " + dealStatus);
		
		DealDatabaseHelper db = new DealDatabaseHelper(DealListActivity.this);

		try {

			Cursor c = db.getAll(dealStatus);
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
				
//				obj._currency_code = c.getString(c.getColumnIndex(DealDatabaseHelper.currency_code));
				
				Log.i("LOG APP -- _merchant_name", c.getString(c.getColumnIndex(DealDatabaseHelper.colMerchantName)));
				
				obj._price_buy = c.getFloat(c.getColumnIndex(DealDatabaseHelper.colPriceBuy));
				Log.i("LOG APP", "1");
				obj._use_dynamic = c.getInt(c.getColumnIndex(DealDatabaseHelper.colUserDynamic));
				Log.i("LOG APP", "2");
				obj._hot_deal = c.getInt(c.getColumnIndex(DealDatabaseHelper.colHotDeal));
				Log.i("LOG APP", "3");
				
				obj._video_desc = c.getString(c.getColumnIndex(DealDatabaseHelper.colVideoDesc));
				Log.i("LOG APP", "4");
				
				obj._price_step = obj.loadPriceStep(c.getString(c.getColumnIndex(DealDatabaseHelper.colPriceStep)));
//				obj._deal_types = obj.loadDealType(c.getString(c.getColumnIndex(DealDatabaseHelper.colDealTypes)));
				
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, MNI_CHANGE_LANG, 0, "").setIcon(R.drawable.z_location_web_site)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        super.initActionBarControl(menu);
        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
        getSupportActionBar().setIcon(R.drawable.emu_category);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void invalidateOptionsMenu() {
        // TODO Auto-generated method stub
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menu.toggle();
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
