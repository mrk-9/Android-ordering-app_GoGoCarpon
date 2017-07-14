package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapLocationActivity extends BaseActivity implements OnMapReadyCallback {


	
	// Test data
	float dLatitude = 10.8057823f;
	float dLongtitude = 106.663599f;
	String address = "";

	public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.act_map_location); // bind the layout to the activity

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        IntentManager _intentManager = new IntentManager(this);
        ActParameter _intentParams = _intentManager.getIntentParams();

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

        address = (String) _intentParams.get("address");
        dLongtitude = Float.valueOf((String) _intentParams.get("longitude"));
        dLatitude = Float.valueOf((String) _intentParams.get("latitude"));

    }
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Overrides the default implementation for KeyEvent.KEYCODE_BACK so that
	 * all systems call onBackPressed().
	 */

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return super.onKeyUp(keyCode, event);
	}



	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng home = new LatLng(dLatitude, dLongtitude);
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 13));
        googleMap.addMarker(new MarkerOptions()
                .title("Home")
                .snippet("Sample of home")
                .position(home));
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
    public boolean onCreateOptionsMenu(Menu menu) {

            super.initActionBarControl(menu);
        return super.onCreateOptionsMenu(menu);
    }
}
