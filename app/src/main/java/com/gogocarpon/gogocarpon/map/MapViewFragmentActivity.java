package com.gogocarpon.gogocarpon.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragmentActivity extends FragmentActivity implements OnMapReadyCallback{

	//private GoogleMap mMapView;
	float mLatitude = 10.8057823f;
	float mLongtitude = 106.663599f;
	String address = "";
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		IntentManager _intentManager = new IntentManager(this);
		ActParameter _intentParams = _intentManager.getIntentParams();

		address = (String) _intentParams.get("address");
		String lng = (String)_intentParams.get("longitude");
		String lat = (String)_intentParams.get("latitude");
		if(!TextUtils.isEmpty(lng)&& !TextUtils.isEmpty(lat))
		{
			mLongtitude = Float.valueOf(lng);
			mLatitude = Float.valueOf(lat);
		}
//		mLongtitude = Float.valueOf((String)_intentParams.get("longitude"));
//		mLatitude = Float.valueOf((String)_intentParams.get("latitude"));
		
		setContentView(R.layout.mapview);
		/*
		mMapView = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapview_map)).getMap();
		//setUpMap();
		*/
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapview_map);
		mapFragment.getMapAsync(this);
	}
	/*
	private void setUpMap() {
		mMapView.setMyLocationEnabled(true);
		mMapView.getMyLocation();
		Double myLat = mLatitude * 1E6;
		Double myLon = mLongtitude * 1E6;
//		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.point);
		Log.i("mapview", "lat"+ mLatitude+"/lon:"+ mLongtitude);
		
//		Bitmap.Config conf = Bitmap.Config.ARGB_8888; 
//		Bitmap bmp = Bitmap.createBitmap(200, 50, conf); 
//		Canvas canvas = new Canvas(bmp);
//		Paint paint = new Paint();
//		paint.setColor(Color.BLACK);
//		canvas.drawText("A", 0, 50, paint);
//		Marker marker = (new MarkerOptions().position(new LatLng(mLatitude, mLongtitude))
//				.title(address));
		
		Marker marker = mMapView.addMarker(new MarkerOptions().position(new LatLng(mLatitude, mLongtitude))
				.title(address)
				);
		marker.showInfoWindow();
		LatLng cinema = new LatLng(mLatitude, mLongtitude);
		mMapView.moveCamera(CameraUpdateFactory.newLatLngZoom(cinema, 14));
		mMapView.animateCamera(CameraUpdateFactory.zoomIn());
	}
	*/
	@Override
	public void onMapReady(GoogleMap googleMap) {
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongtitude), 16));
		googleMap.addMarker(new MarkerOptions()
				.title(address)
				.position(new LatLng(mLatitude, mLongtitude)));
	}
}
