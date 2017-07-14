package com.gogocarpon.gogocarpon._app;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.HTMLEntities;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;


public class MainActivity extends BaseActivity {

	private WebView webDescription;

	private static int webviewContentHeight = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String url = "http://enmasse30.demo.matamko.com/components/com_enmasse/upload/3231ba%20shu%20fish.jpg";
		ImageView imageView = (ImageView) findViewById(R.id.imgProduct);
		imageView.setTag(url);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		ImageLoader loader = ImageLoader.getInstance(getApplicationContext());
		loader.DisplayImage(url, this, imageView, null);

		float density = getApplicationContext().getResources()
				.getDisplayMetrics().density;

		float px = 200 * density;
		float dp = 200 / density;

		Log.i("LOG APP", "density = " + density);
		Log.i("LOG APP", "px = " + px);
		Log.i("LOG APP", "dp = " + dp);
		Log.i("LOG APP",
				"heightPixels = "
						+ getApplicationContext().getResources()
								.getDisplayMetrics().heightPixels);

		Log.i("LOG APP",
				"widthPixels = "
						+ getApplicationContext().getResources()
								.getDisplayMetrics().widthPixels);

		Log.i("LOG APP",
				"xdpi = "
						+ getApplicationContext().getResources()
								.getDisplayMetrics().xdpi);

		Log.i("LOG APP",
				"ydpi = "
						+ getApplicationContext().getResources()
								.getDisplayMetrics().ydpi);

		Log.i("LOG APP", "da load xong data");

		webDescription = (WebView) findViewById(R.id.webDescription);
//		webDescription = new WebView(getApplicationContext());

		webDescription.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webDescription.setScrollbarFadingEnabled(false);

		webDescription.setVerticalScrollBarEnabled(false);
		webDescription.setHorizontalScrollBarEnabled(false);

		webDescription.setHorizontalScrollBarEnabled(false);
		webDescription.setVerticalScrollBarEnabled(false);

		webDescription.getSettings().setJavaScriptEnabled(true);
		webDescription.setSaveEnabled(true);

		// webview.setInitialScale(1);
		// webview.getSettings().setDefaultZoom(ZoomDensity.FAR);
		// webview.loadUrl("http://10.0.2.2/phpwebservice/content.html");

		webDescription.addJavascriptInterface(new JavaScriptInterface(),
				"HTMLOUT");
		webDescription.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webDescription
						.loadUrl("javascript:window.HTMLOUT.getContentHeight(document.getElementById('main_div').offsetHeight);");

//				webDescription
//						.loadUrl("javascript:window.HTMLOUT.getContentHeight(document.getElementsByTagName('html')[0].scrollHeight);");

			}
		});
		
		String data = "&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;Fresh flavours of lobster tails have distracted humans since the beginning of time, only to be outdone by the enticing embrace of succulent crab claws. Succumb to crustacean charms with today's Groupon to &lt;a href=&quot;http://singaporeseafoodrepublic.com/&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;Singapore Seafood Republic&lt;/a&gt; at Resorts World Sentosa, Waterfront. Choose between 2 options:&lt;/p&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 1.5em; width: auto; height: auto; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; color: #666666; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #89b555; border-left-style: solid; border-left-width: 6px; border-left-color: #89b555; font-size: 14px; padding: 5px;&quot;&gt;&lt;strong&gt;Today's Groupon&lt;/strong&gt;&lt;/p&gt;&lt;ul style=&quot;list-style-position: initial; list-style-image: initial; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 5px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 20px; line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556;&quot;&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $20, you get $40 Worth of Food and Drinks.&lt;/li&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $40, you get $80 Worth of Food and Drinks.&lt;/li&gt;&lt;/ul&gt;";

		data = HTMLEntities.unhtmlAngleBrackets(data);
		data = HTMLEntities.unhtmlAmpersand(data);
		data = HTMLEntities.unhtmlSingleQuotes(data);
		data = HTMLEntities.unhtmlDoubleQuotes(data);
		data = HTMLEntities.unhtmlentities(data);

		String html = AppConfig.TEMPLATE_WEB_SOURCE.replace("%@", data);

		Log.i("LOG APP", html);

		// loadDataWithBaseURL(String baseUrl, String data, String mimeType,
		// String encoding, String historyUrl)
		webDescription.loadDataWithBaseURL(null, html, "text/html", "utf-8",
				null);

		
		Button btnDisplayMap = (Button) findViewById(R.id.btnDisplayMap);
		btnDisplayMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
//				String uri = "geo:"+ latitude + "," + longitude;
//				Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")
//				String uri = "geo:20.344,34.34";
//				Intent intent = new Intent(
//						android.content.Intent.ACTION_VIEW,
//						Uri.parse(uri));
//				startActivity(intent);

				// https://maps.google.com/maps?q=10.806497,106.66347
//				String uri = "geo:"+ latitude + "," + longitude + "?q=my+street+address";
				
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("geo:0,0?q=10.806497,106.66347 (11/9 nguyen van mai)"));
						startActivity(intent);				
				
				
			}
		});
		
		
		
	}

	class JavaScriptInterface {

		public void getContentHeight(String value) {
			if (value != null) {
				
				Log.i("LOG APP", "value: "
						+ value);
				
				webviewContentHeight = Integer.parseInt(value);

				Log.i("LOG APP", "Result from javascript: "
						+ webviewContentHeight);

				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// Cap nhat data cho cac UI

						// Done loading more.
						// Convert dp -> dx
						float density = MainActivity.this.getResources()
								.getDisplayMetrics().density;
						int htmlHeight = Math.round(webviewContentHeight
								* density);

						LayoutParams params = webDescription.getLayoutParams();

						Log.i("LOG APP", "Chay vao cho nay : changWebView");

						if (params.height <= htmlHeight) {
							params.height = Math.round(htmlHeight);

							webDescription.setLayoutParams(params);

							// view.clearView();
							webDescription.requestLayout();
							webDescription.invalidate();

							Log.i("LOG APP", "change WebView height");
						}

						Log.i("LOG APP", "px = " + htmlHeight);

						// webView.setVisibility(WebView.VISIBLE);
						// Log.i("LOG APP", "px = " + Math.round(htmlHeight));

					}
				});
			}
		}
	}

	public void showPopupWindow(View v) {

		DiscussPopupWindow popup = new DiscussPopupWindow(MainActivity.this,null);
		popup.show();

		// Toast.makeText(getApplicationContext(),
		// "hien thi ra",Toast.LENGTH_LONG).show();
		// initiatePopupWindow();

	}

	// private PopupWindow pw;
	// private Dialog dialog;
	// private void initiatePopupWindow() {
	// //set up dialog
	// dialog = new Dialog(MainActivity.this);
	// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	// dialog.setContentView(R.layout.wpopup_discuss_form);
	// dialog.setTitle(null);
	// dialog.setCancelable(true);
	// //there are a lot of settings, for dialog, check them all out!
	//
	// //set up button
	// Button btnCommit = (Button) dialog.findViewById(R.id.btnCommit);
	// btnCommit.setOnClickListener(commit_button_click_listener);
	// Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
	// btnCancel.setOnClickListener(cancel_button_click_listener);
	// //now that the dialog is set up, it's time to show it
	// dialog.show();
	// }
	//
	// private OnClickListener cancel_button_click_listener = new
	// OnClickListener() {
	// public void onClick(View v) {
	// dialog.dismiss();
	// }
	// };
	//
	// private OnClickListener commit_button_click_listener = new
	// OnClickListener() {
	// public void onClick(View v) {
	// // dialog.dismiss();
	// // Toast.makeText(getApplicationContext(),
	// "Hien thi sau khi popup",Toast.LENGTH_LONG).show();
	// Utils.ShowNotification(MainActivity.this, "Hien thi", "Thong bao loi");
	// }
	// };

}