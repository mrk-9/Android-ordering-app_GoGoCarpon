package com.gogocarpon.gogocarpon;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;


public class DemoDealListActivity extends BaseActivity {

	protected ListView mainListView;
	protected DealListAdapter dealListAdapter;
	protected ArrayList<Deal> arrData;
	protected ImageLoader imageLoader;

	protected DemoDealListActivity DealListActivityRef = null;

	public static final int REQUEST_DEALS = 1;

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
		
		dealListAdapter = new DealListAdapter(this, arrData);
        // Set the ArrayAdapter as the ListView's adapter.  
		mainListView.setAdapter(dealListAdapter);

	}

	@Override
	protected void onResume() {

		Log.i("LOG APP", "onResume : DealListActivity");
		// TODO Auto-generated method stub
		super.onResume();

		Log.i("LOG APP", "onResume : DealListActivity");

	}

	protected void loadDealFromDatabase() {

		// Tao du lieu ao
		for (int i = 0; i < 20; i++) {
			Deal obj = new Deal();

			obj._name = "AllDeal -- ID : "
					+ i
					+ " -- $35 for 4-Course Italian Meal at Covelli Italian Bistro & Wine Bar in Orchard (Worth $80)";

			obj._pic_dir = "http://enmasse30.demo.matamko.com/components/com_enmasse/upload/3231ba shu fish.jpg";
			obj._end_at = "2012-06-30 11:00:00";

			obj._price = 35.00f;
			obj._origin_price = 80.00f;
			obj._discount = 57.00f;
			obj._prepay_percent = 0;

			if (i % 2 == 0) {
				obj._pic_dir = "http://enmasse30.demo.matamko.com/components/com_enmasse/upload/857215 for whitening.jpg";
				obj._end_at = "2012-06-29 11:00:00";

				obj._price = 35.00f;
				obj._origin_price = 80.00f;
				obj._discount = 57.00f;
				obj._prepay_percent = 20.00f;
			}

			if (i % 3 == 0) {
				obj._pic_dir = "http://enmasse30.demo.matamko.com/components/com_enmasse/upload/49235 for 4.jpg";
				obj._end_at = "2012-06-27 11:00:00";

				obj._price = 35.00f;
				obj._origin_price = 80.00f;
				obj._discount = 57.00f;
				obj._prepay_percent = 35.00f;
			}

			obj._short_desc = "&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #929497;&quot;&gt;Singapore Seafood Republic has been featured in The Straits Times, Lianhe Zaobao, Shin Min Daily News, The New Paper, The Peak and Tropara Magazine.&lt;em&gt;&lt;br /&gt;&lt;/em&gt;&lt;/span&gt;&lt;/p&gt;";
			obj._highlight = "&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;Fresh flavours of lobster tails have distracted humans since the beginning of time, only to be outdone by the enticing embrace of succulent crab claws. Succumb to crustacean charms with today's Groupon to &lt;a href=&quot;http://singaporeseafoodrepublic.com/&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;Singapore Seafood Republic&lt;/a&gt; at Resorts World Sentosa, Waterfront. Choose between 2 options:&lt;/p&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 1.5em; width: auto; height: auto; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; color: #666666; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #89b555; border-left-style: solid; border-left-width: 6px; border-left-color: #89b555; font-size: 14px; padding: 5px;&quot;&gt;&lt;strong&gt;Today's Groupon&lt;/strong&gt;&lt;/p&gt;&lt;ul style=&quot;list-style-position: initial; list-style-image: initial; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 5px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 20px; line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556;&quot;&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $20, you get $40 Worth of Food and Drinks.&lt;/li&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $40, you get $80 Worth of Food and Drinks.&lt;/li&gt;&lt;/ul&gt;";

			arrData.add(obj);
		}

//		dealListAdapter.notifyDataSetChanged();

	}


}