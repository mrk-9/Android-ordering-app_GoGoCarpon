package com.gogocarpon.gogocarpon._app;

import android.database.Cursor;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.models.Category;
import com.gogocarpon.gogocarpon._app.models.CategoryDatabaseHelper;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealDatabaseHelper;
import com.gogocarpon.gogocarpon._app.models.DealType;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


public class TestCatMenuActivity extends BaseActivity implements CategoryFragmentActivity.CategoryFragment.CategoryListSelectedListener, DealStyleFragmentActivity.DealStyleListFragment.DealStyleListSelectedListener {

	private TestCatMenuActivity TestCatMenuActivityRef = null;
	

	
	private DialogFragment newFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TestCatMenuActivityRef = this;

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);
		/*
		// configure the SlidingMenu
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
		menu.setShadowDrawable(R.drawable.slidingmenu_shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.frm_menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.frm_menu_frame, new CategoryFragmentActivity.CategoryFragment())
		.commit();
        */
		Button btnDisplayMap = (Button) findViewById(R.id.btnDisplayMap);
		btnDisplayMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
		        if (prev != null) {
		            ft.remove(prev);
		        }
		        ft.addToBackStack(null);

		        // Create and show the dialog.
		        newFragment = new DealStyleFragmentActivity.DealStyleListFragment();
		        
		     // Supply num input as an argument.
	            Bundle args = new Bundle();
	            args.putInt("num", 9999);
	            newFragment.setArguments(args);		        
		         
		        newFragment.show(ft, "dialog");				
				
			}
		});
		
		Button btnLargeButton = (Button) findViewById(R.id.btnLargeButton123);
		btnLargeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				MultipartEntity reqEntity = new MultipartEntity();

				 try {
//					 UserInfo info = UserInfo.getInstance();
//					 Log.i("LOG APP", "info.city_id = " + info.city_id);
//					 reqEntity.addPart("city_id", new StringBody(info.city_id));
					 reqEntity.addPart("city_id", new StringBody("1"));
				 } catch (UnsupportedEncodingException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }

				GrabRequestUrl urlrequest = new GrabRequestUrl(TestCatMenuActivityRef,
						TestCatMenuActivity.this, reqEntity);

				Log.i("LOG APP", "http://samuel-test.demo.matamko.com/index.php");
				
				urlrequest.executeRequest("http://samuel-test.demo.matamko.com/index.php?option=com_enmasse&controller=webserviceuser&task=getcatsanddeals", 2, false);				
				
			}
		});
		
		Button btnReadData = (Button) findViewById(R.id.btnReadData);
		btnReadData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				DealDatabaseHelper dealHepler = new DealDatabaseHelper(
						TestCatMenuActivityRef);
				
				try {

					Cursor c = dealHepler.getAll(1,1);
					int numRows = c.getCount();
					
					Log.i("LOG APP", "So dong tim duoc numRows :" + numRows);
					
//					c.moveToFirst();
//					for (int i = 0; i < numRows; ++i) {
//						
//						Deal obj = new Deal();
//
//						obj._id = c.getString(c.getColumnIndex(DealDatabaseHelper.colID));
//						obj._deal_status = c.getString(c.getColumnIndex(DealDatabaseHelper.colDealStatus));
//						
//						c.moveToNext();
//					}
					c.close();
				} catch (SQLException e) {
					Log.e("Exception on query", e.toString());
				}				
				

				Deal objDeal = dealHepler.getRowById(1);
				
				Log.i("LOG APP", "" + objDeal._name);
				
				objDeal._price_step = objDeal.loadPriceStep(objDeal._price_step_json);
				objDeal._deal_types = objDeal.loadDealType(objDeal._deal_types_json);
				
				Log.i("LOG APP", "_price_step = " + objDeal._price_step.toString());
				Log.i("LOG APP", "_deal_types =" + objDeal._deal_types.toString());
				
				for (int i = 0; i < objDeal._deal_types.size(); ++i) {

					DealType dt = objDeal._deal_types.get(i);
					Log.i("LOG APP", "--------------------------------------------------------");
					
					Log.i("LOG APP", "_name = " + dt._name);
					Log.i("LOG APP", "_deal_id = " + dt._deal_id);
					Log.i("LOG APP", "_price_buy = " + dt._price_buy);
					Log.i("LOG APP", "_discount = " + dt._discount);
					Log.i("LOG APP", "_price_step = " + dt._price_step.toString());
					
					if (dt._price_step != null) {
						
						// Size == 0 , khong co gia tri NULL
						// Cach 2: 
						for (HashMap<String,String> ps : dt._price_step) {
							
							Log.i("LOG APP", "--------------");
							Log.i("LOG APP", "quantity = " + ps.get("quantity"));
							Log.i("LOG APP", "price = " + ps.get("price"));
						}

						// Cach 1:
//						for (int j = 0; j < dt._price_step.size(); ++j) {
//							
//							HashMap<String,String> ps = (HashMap<String,String>) dt._price_step.get(j);
//						
//							Log.i("LOG APP", "--------------");
//							Log.i("LOG APP", "quantity = " + ps.get("quantity"));
//							Log.i("LOG APP", "price = " + ps.get("price"));
//						}
						
					} else {
						Log.i("LOG APP", "--------------");
						Log.i("LOG APP", "KHONG CO THANG NAO CA");
					}
					
					
				}
				
			}
		});
		
		
	}
	
	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == 2) {

			try {
				JSONObject jsonRoot = new JSONObject(contentData);
				
//				Log.i("LOG APP", "" + jsonRoot.getString("cates"));

				// Done
				JSONArray jArrCats = new JSONArray(jsonRoot.getString("cates"));
				this.insertCategoryIndoDb(jArrCats);

				// Done
				JSONArray jArrDeals = new JSONArray(jsonRoot.getString("deals"));				
				this.insertDealIndoDb(jArrDeals);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return;
		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	// insertDealIndoDb
	private void insertDealIndoDb(JSONArray jArrDeals){
		
		if (jArrDeals.length() <= 0)
			return;
		
		try {

			DealDatabaseHelper dealHepler = new DealDatabaseHelper(
					TestCatMenuActivityRef);

			// Clear all data
			dealHepler.deleteAll();

			for (int i = 0; i < jArrDeals.length(); ++i) {

				JSONObject rec = jArrDeals.getJSONObject(i);

				Deal obj = new Deal();

				obj._id = rec.getString("id");
				obj._deal_status = rec.getString("deal_status"); // Deal today
				obj._deal_code = rec.getString("deal_code");
				obj._name = rec.getString("name");

				obj._pic_dir = rec.getString("pic_dir");
				obj._start_at = rec.getString("start_at");
				obj._end_at = rec.getString("end_at");

				obj._price = (float) rec.getDouble("price");
				obj._origin_price = (float) rec.getDouble("origin_price");
				obj._discount = (float) rec.getDouble("discount");
				obj._prepay_percent = (float) rec.getDouble("prepay_percent");
				
				Log.i("LOG APP", ") = " + rec.getString("highlight"));
				
				obj._short_desc = rec.getString("short_desc");
				obj._highlight = rec.getString("highlight");
				obj._desc = rec.getString("description");
				obj._terms = rec.getString("terms");
				
				obj._merchant_id = rec.getString("merchant_id");
				
				if(Integer.parseInt(obj._merchant_id) > 0) 
				{
					JSONObject m_info = rec.getJSONObject("merchant_info");
					
					obj._merchant_name = m_info.getString("merchant_name");
					obj._merchant_address = m_info.getString("merchant_address");
					obj._merchant_telephone = m_info.getString("merchant_telephone");
					obj._merchant_long = m_info.getString("merchant_long");
					obj._merchant_lat = m_info.getString("merchant_lat");
				}
				
				obj._price_buy = (float) rec.getDouble("price_buy");
				obj._hot_deal = (int) rec.getInt("hot_deal");
				
				obj._use_dynamic = (int) rec.getInt("use_dynamic");
				obj._video_desc = rec.getString("video_desc");
				obj._cat_ids = rec.getString("cat_ids");
				
				obj._price_step_json = rec.getString("price_step");
				obj._deal_types_json = rec.getString("deal_types");

				dealHepler.addDeal(obj);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	// insertCategoryIndoDb
	private void insertCategoryIndoDb(JSONArray jArrCats){
		
		if (jArrCats.length() <= 0)
			return;
		
		// Load category
		CategoryDatabaseHelper catHepler = new CategoryDatabaseHelper(
				TestCatMenuActivityRef);

		// Clear all data
		catHepler.deleteAll();
		
		try {
			for (int i = 0; i < jArrCats.length(); ++i) {
				
				JSONObject rec = jArrCats.getJSONObject(i);
				
				Category obj = new Category();
	
				obj._id = rec.getString("id");
				obj._name = rec.getString("name");
				obj._desc = "";
				obj._created_at = "";
				obj._updated_at = "";
			
				catHepler.addDeal(obj);
			
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	// De no phia duoi
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		ActionBar ab = getSupportActionBar();
		
		ab.setHomeButtonEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
		getSupportActionBar().setIcon(R.drawable.emu_category);
		
		return super.onCreateOptionsMenu(menu);
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
	
	@Override
	public void onCategorySelected(Category cat, int position) {
		// TODO Auto-generated method stub
		Log.i("LOG APP", "");
		Toast.makeText(getApplicationContext(), "Gia tri ban chon : " + position,Toast.LENGTH_LONG).show();
		// Close menu
		//menu.toggle();
	}
	
	@Override
	public void onDealStyleSelected(DealType rowDealStyle, int position) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "Name rowDealStyle = " + rowDealStyle._name ,Toast.LENGTH_LONG).show();
		
		newFragment.dismiss();
	}
	
	/*
	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			super.onBackPressed();
		}
	}
    */


}