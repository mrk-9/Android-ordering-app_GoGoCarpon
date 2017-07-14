package com.gogocarpon.gogocarpon._app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.HTMLEntities;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealType;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@SuppressLint("SetJavaScriptEnabled")
public class DealDetailActivity extends BaseActivity implements DealStyleFragmentActivity.DealStyleListFragment.DealStyleListSelectedListener {

	private DealDetailActivity DealDetailActivityRef;
	
	private DialogFragment newFragment;

	private Deal rowDeal;
	private DealType rowDealType;

	private ImageView imgProduct;
	private TextView lblName;
	private TextView lblPrice;
	private TextView lblValue;
	private TextView lblDiscount;
	private TextView lblYouSave;
	//
	private WebView webDescription;
	private TextView lblExpireTime;

	private Button btnDiscuss;
	private Button btnYoutube;
	private Button btnLocations;
	private Button btnBuyNow;
	private Button btnShare;
	private Button btnTweet;
    private Toolbar mToolbar;
    private ScrollView scrollView;
	
	OnClickListener ShareOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			String slug_name = rowDeal._name.replaceAll("[\\!\\@\\#\\$\\%\\^\\&\\(\\)\\-\\_\\+\\=\\`\\~\\{\\}\\|\\:\\;\\'\\,\\.\\/\\<\\>\\?\\*]", "");
			slug_name = slug_name.replaceAll("\\s", "_");
			
			String shareUrl = AppConfig.URI_SHARE_ACTION + "id=" + rowDeal._id + "&slug_name=" + slug_name;
			
			try {
				shareUrl = URLEncoder.encode(shareUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String launchUrl = "";
			if(v.getTag().equals("9999")) {
				launchUrl = "http://www.facebook.com/share.php?u=" + shareUrl;
			} else if(v.getTag().equals("10000")) {
				launchUrl = "http://twitter.com/share?url=" + shareUrl;
			}
			
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(launchUrl)));			
		}
	};
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_deal_detail);
		DealDetailActivityRef = this;
		setTitle(R.string.em_coupon_detail);

        scrollView = (ScrollView) findViewById(R.id.ScrollView01);

//		newFragment = new DialogFragment();
		lblName = (TextView) findViewById(R.id.lblName);
		lblPrice = (TextView) findViewById(R.id.lblPrice);

		lblValue = (TextView) findViewById(R.id.lblValue);
		lblDiscount = (TextView) findViewById(R.id.lblDiscount);
		lblYouSave = (TextView) findViewById(R.id.lblYouSave);
		//
		// imgProduct = (ImageView) findViewById(R.id.imgProduct);
		btnDiscuss = (Button) findViewById(R.id.btnDiscuss);
		btnLocations = (Button) findViewById(R.id.btnLocations);
		btnBuyNow = (Button) findViewById(R.id.btnBuyNow);
		btnYoutube = (Button) findViewById(R.id.btnYoutube);
		btnShare = (Button) findViewById(R.id.btnShare);
		btnTweet = (Button) findViewById(R.id.btnTweet);		

		webDescription = (WebView) findViewById(R.id.webDescription);
		lblExpireTime = (TextView) findViewById(R.id.lblExpireTime);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		// Bundle extras = getIntent().getExtras();
		// rowDeal = (Deal) extras.getSerializable("rowDeal");

		rowDeal = (Deal) _intentParams.get("rowDeal");
        String id = rowDeal._id;

		lblName.setText(rowDeal._name);
		lblPrice.setText(getString(R.string.em_price) + " : "
				+ getString(R.string.em_price_signal)
				+ Utils.decimalFormat(AppConfig.DECIMAL_NUMBER_FORMAT, rowDeal._price_buy));
        /*
		lblValue.setText(getString(R.string.em_price_signal)
				+ Utils.decimalFormatPrice(rowDeal._origin_price));
        */
		lblValue.setText(getString(R.string.em_price_signal)
		+ Utils.decimalFormat(AppConfig.DECIMAL_NUMBER_FORMAT,
		rowDeal._origin_price));

		lblDiscount.setText(Utils.decimalFormat(
				AppConfig.DECIMAL_NUMBER_FORMAT, rowDeal._discount) + " %");

        /*
		lblYouSave.setText(getString(R.string.em_price_signal)
				+ Utils.decimalFormatPrice(rowDeal._origin_price
						- rowDeal._price_buy));
        */
		lblYouSave.setText(getString(R.string.em_price_signal)
		 + Utils.decimalFormat(AppConfig.DECIMAL_NUMBER_FORMAT,
		 rowDeal._origin_price - rowDeal._price));

		imgProduct = (ImageView) findViewById(R.id.imgProduct);

		if (rowDeal._pic_dir.trim().length() > 0) {
			// Load image
			String img_url = rowDeal._pic_dir.replace(" ", "%20");
			imgProduct.setTag(img_url);
			ImageLoader imgLoader = ImageLoader
					.getInstance(getApplicationContext());
			imgLoader.DisplayImage(img_url, this, imgProduct, rowDeal);
		}
		
		btnShare.setOnClickListener(ShareOnClickListener);
		btnTweet.setOnClickListener(ShareOnClickListener);
		
		// Hide button when dont have youtube video 
		if (rowDeal._video_desc.equals("0")) {
			btnYoutube.setVisibility(View.GONE);
		}
		btnYoutube.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("vnd.youtube:" + rowDeal._video_desc));
					startActivity(intent);
				} catch (ActivityNotFoundException ex) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("http://www.youtube.com/watch?v="
									+ rowDeal._video_desc));
					startActivity(intent);
				}
				
			}
		});		
		
		// Hide button when dont have merchant 
		if (rowDeal._merchant_id.equals("0")) {
			btnLocations.setVisibility(View.GONE);
		}
		btnLocations.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				// https://maps.google.com/maps?q=10.806497,106.66347
				// String uri = "geo:"+ latitude + "," + longitude +
				// "?q=my+street+address";

//				String latitude = "10.806497";
//				String longitude = "106.66347";
//				String address = "11/9 Nguyen Van Mai, Q. Tan Binh, Tp. Ho Chi Minh";
				
				ActParameter params = new ActParameter();
				params.put("latitude", rowDeal._merchant_lat);
				params.put("longitude", rowDeal._merchant_long);
				params.put("address", rowDeal._merchant_address);
				
				_intentManager.OpenActivity(IntentManager.MAP_LOCATION_ACTIVITY, params);

			}
		});

		btnDiscuss.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_intentManager.OpenActivity(IntentManager.DISCUSS_ACTIVITY,
						_intentParams);

			}
		});

		if(rowDeal._deal_status.equals("2") || rowDeal._deal_status.equals("3")) {
			btnBuyNow.setVisibility(View.INVISIBLE);
		}
		
		
		if (rowDeal._deal_types.size() > 0) {
			
	        // Create and show the dialog.
	        newFragment = new DealStyleFragmentActivity.DealStyleListFragment();
	        
	     // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", 9999);
            args.putSerializable("rowDeal", rowDeal);
            newFragment.setArguments(args);		        
	         				
		}		
		
		btnBuyNow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

                if (UserInfo.getInstance().isLogin()) {

                    if (rowDeal._deal_types.size() > 0) {

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);

//			        // Create and show the dialog.
//			        newFragment = new DealStyleListFragment();
//			        
//			     // Supply num input as an argument.
//		            Bundle args = new Bundle();
//		            args.putInt("num", 9999);
//		            args.putSerializable("rowDeal", rowDeal);
//		            newFragment.setArguments(args);		        

                        newFragment.show(ft, "dialog");

                    } else {
                        rowDealType = null;
                        DealDetailActivityRef.buyDeal();
                    }

                }
                else    {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(DealDetailActivity.this);
                    dialog.setTitle("Notify");
                    dialog.setMessage("Please login before purchase order!");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            _intentManager.OpenActivity(
                                    IntentManager.LOGIN_ACTIVITY, _intentParams);
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        } });
                    dialog.show();
                }
            }

		});

		webDescription.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webDescription.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = webDescription.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);

        webDescription.setWebViewClient(new WebViewClient());
        webDescription.setWebChromeClient(new WebChromeClient());		

		webDescription.clearCache(true);
		webDescription.clearHistory();
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

		// Count down clock
		SimpleDateFormat sdf = new SimpleDateFormat(
				AppConfig.SERVER_TIME_FORMAT, Locale.ENGLISH);
		// Get current date
		Date now = new Date();
        scrollView.requestFocus(View.FOCUS_UP);

		long diff = Utils.totalSecondBetween2Date(sdf.format(now),
				rowDeal._end_at, AppConfig.SERVER_TIME_FORMAT);
		Log.i("LOG APP", "seconds remaining: " + diff);

		// Unit is : millis second
		new CountDownTimer(diff * 1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				long totalSecond = millisUntilFinished / 1000;

				int days = (int) (totalSecond / 86400);
				int hour = (int) (totalSecond % 86400) / 3600;
				int min = (int) ((totalSecond % 86400) % 3600) / 60;
				int sec = (int) ((totalSecond % 86400) % 3600) % 60;
                String min_zero;
                if (min < 10){
                    min_zero = "0";
                } else {
                    min_zero = "";
                }
                String sec_zero;
                if (sec < 10){
                    sec_zero = "0";
                } else {
                    sec_zero = "";
                }


				lblExpireTime.setText(days + getString(R.string.em_days) + hour + ":" + min_zero + min + ":" +
						sec_zero + sec);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				// Khi thoi gian het han hay thoi gian count down < 0
				lblExpireTime.setText("00" + getString(R.string.em_days) + "00:00:00");
			}
		}.start();
		// ---------------------------------

		webviewContentWidth = 0;

		// Load web view description
		this.loadWebViewDescription();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
            super.initActionBarControl(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void invalidateOptionsMenu() {
        // TODO Auto-generated method stub
        super.invalidateOptionsMenu();
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//	    if (item.getItemId() == android.R.id.home) {
//	    	// Overwrite button home function
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();
//	    	return true;
//	    }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDealStyleSelected(DealType rDealType, int position) {
		
		rowDealType = rDealType;
		this.buyDeal();
	}
	
	private void buyDeal() {
		
		// Check login status
		if (!UserInfo.getInstance().isLogin()) {
			Utils.ShowNotification(DealDetailActivityRef, getString(R.string.notify),
					getString(R.string.notify_request_login));
			return;
		}
		
		CharSequence[] items = { getString(R.string.em_buy_to_me), getString(R.string.em_buy_for_friend) };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(
				DealDetailActivity.this);
		builder.setTitle(getString(R.string.em_buy));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				_intentParams.put("buyType", item + "");
				
				if (rowDealType != null) {

					// Tranfer data of DealType into Deal
					rowDeal._origin_price =  rowDealType._origin_price;
					rowDeal._discount =  rowDealType._discount;
					rowDeal._price =  rowDealType._price_buy;
					
					// Override value
					_intentParams.put("rowDeal", rowDeal);
				}
				
				_intentParams.put("rowDealType", rowDealType);
				_intentManager.OpenActivity(
						IntentManager.BUY_DEAL_ACTIVITY, _intentParams);
                finish();
				
				if (newFragment !=null && newFragment.getShowsDialog()) {
					newFragment.dismiss();	
				}

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/*
	 * Load WebView
	 */

	private int webviewContentWidth = 0;

	private void loadWebViewDescription() {

		String _desc = rowDeal._desc.trim();
		_desc = HTMLEntities.unhtmlAngleBrackets(_desc);
		_desc = HTMLEntities.unhtmlAmpersand(_desc);
		_desc = HTMLEntities.unhtmlSingleQuotes(_desc);
		_desc = HTMLEntities.unhtmlDoubleQuotes(_desc);
		_desc = HTMLEntities.unhtmlentities(_desc);
		
		String _highlight = rowDeal._highlight.trim();
		_highlight = HTMLEntities.unhtmlAngleBrackets(_highlight);
		_highlight = HTMLEntities.unhtmlAmpersand(_highlight);
		_highlight = HTMLEntities.unhtmlSingleQuotes(_highlight);
		_highlight = HTMLEntities.unhtmlDoubleQuotes(_highlight);
		_highlight = HTMLEntities.unhtmlentities(_highlight);
		
		String _terms = rowDeal._terms.trim();
		_terms = HTMLEntities.unhtmlAngleBrackets(_terms);
		_terms = HTMLEntities.unhtmlAmpersand(_terms);
		_terms = HTMLEntities.unhtmlSingleQuotes(_terms);
		_terms = HTMLEntities.unhtmlDoubleQuotes(_terms);
		_terms = HTMLEntities.unhtmlentities(_terms);		

		String html = AppConfig.TEMPLATE_WEB_SOURCE.replace("[%1@]", _desc);
		html = html.replace("[%2@]", _highlight);
		html = html.replace("[%3@]", _terms);
		
//		Log.i("LOG APP", "" + data.length());
//		Log.i("LOG APP", html);

		webDescription.clearView();

		// loadDataWithBaseURL(String baseUrl, String data, String mimeType,
		// String encoding, String historyUrl)
		webDescription.loadDataWithBaseURL(null, html, "text/html", "utf-8",
				null);

		webDescription.addJavascriptInterface(new JavaScriptInterface(),
				"HTMLOUT");
		webDescription.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webDescription
						.loadUrl("javascript:window.HTMLOUT.getContentHeight(document.getElementById('main_div').offsetHeight);");
			}
		});

	}

	class JavaScriptInterface {

		public void getContentHeight(String value) {
			if (value != null) {
				webviewContentWidth = Integer.parseInt(value);

				Log.i("LOG APP", "Result from javascript: "
						+ webviewContentWidth);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// Cap nhat data cho cac UI

						// Done loading more.
						// Convert dp -> dx
						float density = DealDetailActivity.this.getResources()
								.getDisplayMetrics().density;
						
						Log.i("LOG APP", "density = " + density);

						// DucBui 11/27/2012: Fix 
						int htmlHeight = (int) Math.round(webviewContentWidth * density);
						
						LayoutParams params = webDescription.getLayoutParams();
						
						params.height = htmlHeight;
						params.width = LayoutParams.MATCH_PARENT;

						webDescription.setLayoutParams(params);

						webDescription.requestLayout();
						webDescription.invalidate();

						Log.i("LOG APP", "px = " + htmlHeight);

						// webView.setVisibility(WebView.VISIBLE);
						// Log.i("LOG APP", "px = " + Math.round(htmlHeight));

					}
				});
			}
		}
	}
	/*
	 * End Load Web View
	 */

}
