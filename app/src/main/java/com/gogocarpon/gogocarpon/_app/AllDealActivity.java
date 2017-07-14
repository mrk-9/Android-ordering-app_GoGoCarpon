package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.ui.adapters.DealListAdapter;


public class AllDealActivity extends DealListActivity {

	// Properties
	private static AllDealActivity AllDealActivityRef = null;

	public static AllDealActivity getRef() {
		return AllDealActivityRef;
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.dealStatus = 1;
		super.onCreate(savedInstanceState);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		super.dealListAdapter = new DealListAdapter(this, super.arrData);

		// Set the ArrayAdapter as the ListView's adapter.
		super.mainListView.setAdapter(super.dealListAdapter);

		// Set the ArrayAdapter as the ListView's adapter.
		super.mainListView.setAdapter(super.dealListAdapter);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ActParameter params = new ActParameter();
				params.put("rowDeal", arrData.get(position));

				_intentManager.OpenActivity(IntentManager.DEAL_DETAIL_ACTIVITY, params);
			}
		});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
