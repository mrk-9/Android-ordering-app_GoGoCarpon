package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;


public class UpcomingDealActivity extends DealListActivity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.dealStatus = 2; 
        super.onCreate(savedInstanceState);
        
        Log.i("LOG APP", "onCreate : UpcomingDealActivity");
        
		super.dealListAdapter = new DealListAdapter(this, super.arrData);
          
        // Set the ArrayAdapter as the ListView's adapter.  
		super.mainListView.setAdapter(super.dealListAdapter);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Deal rowDeal = arrData.get(position);
				
				ActParameter params = new ActParameter();
				params.put("rowDeal", rowDeal);
				
				Log.i("LOG APP", "deal_status = " + rowDeal._deal_status);
				
				_intentManager.OpenActivity(IntentManager.DEAL_DETAIL_ACTIVITY, params);

			}
		});		
		
		Log.i("LOG APP", " END onCreate : UpcomingDealActivity");
    }
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Log.i("LOG APP", "onStart : UpcomingDealActivity");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		Log.i("LOG APP", "onResume : UpcomingDealActivity");
		super.onResume();
	}
    
    
}