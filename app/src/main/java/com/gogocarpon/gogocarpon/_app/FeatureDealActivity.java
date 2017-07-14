package com.gogocarpon.gogocarpon._app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;


public class FeatureDealActivity extends DealListActivity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.dealStatus = 1; 
        super.onCreate(savedInstanceState);
        
        Log.i("LOG APP", "onCreate : FeatureDealActivity");
        
		super.dealListAdapter = new DealListAdapter(this, super.arrData);
          
        // Set the ArrayAdapter as the ListView's adapter.  
		super.mainListView.setAdapter(super.dealListAdapter);
		
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
		
		Log.i("LOG APP", " END onCreate : FeatureDealActivity");
    }
    
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		Log.i("LOG APP", "onStart : FeatureDealActivity");
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		Log.i("LOG APP", "onResume : FeatureDealActivity");
		super.onResume();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.initActionBarControl(menu);
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
}
