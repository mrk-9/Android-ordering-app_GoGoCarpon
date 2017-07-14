package com.gogocarpon.gogocarpon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.HTMLEntities;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;


public class WebViewDemoActivity extends Activity {
	/** Called when the activity is first created. */

	private WebView webView;
	private TextView lblTime;

	ProgressBar pd = null;

	private static int webviewContentWidth = 0;

	// Since we cant update our UI from a thread this Runnable takes care of
	// that!
	private Runnable changWebView = new Runnable() {
		@Override
		public void run() {
			// Cap nhat data cho cac UI

			// Done loading more.
			// Convert dp -> dx
			float density = WebViewDemoActivity.this.getResources()
					.getDisplayMetrics().density;
			int htmlHeight = Math.round(webviewContentWidth * density);

			LayoutParams params = webView.getLayoutParams();

			Log.i("LOG APP", "Chay vao cho nay : changWebView");

			if (params.height <= htmlHeight) {
				params.height = Math.round(htmlHeight);

				webView.setLayoutParams(params);

				// view.clearView();
				webView.requestLayout();
				webView.invalidate();

				Log.i("LOG APP", "change WebView height");
			}

			Log.i("LOG APP", "px = " + htmlHeight);

			// webView.setVisibility(WebView.VISIBLE);
			// Log.i("LOG APP", "px = " + Math.round(htmlHeight));

		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_webview_demo);

		webView = (WebView) findViewById(R.id.webViewDemo);
		lblTime = (TextView) findViewById(R.id.lblCountDownTime);

		// workaround so that the default browser doesn't take over
		// webView.setWebViewClient(new MyWebViewClient());

		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(false);

		webView.setVerticalScrollBarEnabled(false);
		webView.setHorizontalScrollBarEnabled(false);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setSaveEnabled(true);
		webView.addJavascriptInterface(new JavaScriptInterface(), "HTMLOUT");
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl("javascript:window.HTMLOUT.getContentHeight(document.getElementsByTagName('html')[0].scrollHeight);");
			}
		});

		pd = (ProgressBar) findViewById(R.id.webViewProgressBar);

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100 && pd.getVisibility() == ProgressBar.GONE) {
					pd.setVisibility(ProgressBar.VISIBLE);
				}
				pd.setProgress(progress);
				if (progress == 100) {
					pd.setVisibility(ProgressBar.GONE);
				}
			}
		});

		// webView.loadUrl("http://10.0.2.2/phpwebservice/content.html");

		String data = "&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;Fresh flavours of lobster tails have distracted humans since the beginning of time, only to be outdone by the enticing embrace of succulent crab claws. Succumb to crustacean charms with today's Groupon to &lt;a href=&quot;http://singaporeseafoodrepublic.com/&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;Singapore Seafood Republic&lt;/a&gt; at Resorts World Sentosa, Waterfront. Choose between 2 options:&lt;/p&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 1.5em; width: auto; height: auto; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; color: #666666; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #89b555; border-left-style: solid; border-left-width: 6px; border-left-color: #89b555; font-size: 14px; padding: 5px;&quot;&gt;&lt;strong&gt;Today's Groupon&lt;/strong&gt;&lt;/p&gt;&lt;ul style=&quot;list-style-position: initial; list-style-image: initial; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 5px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 20px; line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556;&quot;&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $20, you get $40 Worth of Food and Drinks.&lt;/li&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $40, you get $80 Worth of Food and Drinks.&lt;/li&gt;&lt;/ul&gt;";
		// String data =
		// "&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;Fresh flavours of lobster tails have distracted humans since the beginning of time, only to be outdone by the enticing embrace of succulent crab claws. Succumb to crustacean charms with today's Groupon to &lt;a href=&quot;http://singaporeseafoodrepublic.com/&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;Singapore Seafood Republic&lt;/a&gt; at Resorts World Sentosa, Waterfront. Choose between 2 options:&lt;/p&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 1.5em; width: auto; height: auto; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; color: #666666; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #89b555; border-left-style: solid; border-left-width: 6px; border-left-color: #89b555; font-size: 14px; padding: 5px;&quot;&gt;&lt;strong&gt;Today's Groupon&lt;/strong&gt;&lt;/p&gt;&lt;ul style=&quot;list-style-position: initial; list-style-image: initial; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; margin-left: 5px; padding-top: 0px; padding-right: 0px; padding-bottom: 0px; padding-left: 20px; line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556;&quot;&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $20, you get $40 Worth of Food and Drinks.&lt;/li&gt;&lt;li style=&quot;padding-top: 1px; padding-right: 0px; padding-bottom: 2px; padding-left: 0px; margin: 0px;&quot;&gt;For $40, you get $80 Worth of Food and Drinks.&lt;/li&gt;&lt;/ul&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 1.5em; width: auto; height: auto; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; color: #666666; border-bottom-style: solid; border-bottom-width: 1px; border-bottom-color: #89b555; border-left-style: solid; border-left-width: 6px; border-left-color: #89b555; font-size: 14px; padding: 5px;&quot;&gt;&lt;strong&gt;The Deal&lt;br /&gt;&lt;/strong&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;A treasure trove of oceanic delights awaits at Singapore Seafood Republic, with over 70 signature dishes from four of Singapore's best-loved seafood restaurants - Tung Lok Seafood, Palm Beach Seafood, JUMBO Seafood and The Seafood International Market &amp;amp; Restaurant. &lt;/span&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/94/28/1334571522894.jpg&quot; border=&quot;0&quot; alt=&quot;/94/28/1334571522894.jpg&quot; title=&quot;/94/28/1334571522894.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/82/57/1334561295782.jpg&quot; border=&quot;0&quot; alt=&quot;/82/57/1334561295782.jpg&quot; title=&quot;/82/57/1334561295782.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/41/79/1334561067941.jpg&quot; border=&quot;0&quot; alt=&quot;/41/79/1334561067941.jpg&quot; title=&quot;/41/79/1334561067941.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;span style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;Crustacean-lovers will be awashed with excitement by the myriad of crab, lobster and prawn dishes on offer, such as Award-winning Singapore-style Chilli Crab or live Sri Lankan King Crab stir-fried to a robust, peppery perfection.  &lt;/span&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/23/79/1334561067923.jpg&quot; border=&quot;0&quot; alt=&quot;/23/79/1334561067923.jpg&quot; title=&quot;/23/79/1334561067923.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/72/79/1334561067972.jpg&quot; border=&quot;0&quot; alt=&quot;/72/79/1334561067972.jpg&quot; title=&quot;/72/79/1334561067972.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;span style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;Let innocent tongues fall prey to a Creamy Whole Lobster Stew, or the Award-winning Salted Egg Golden Prawns. The former features fresh lobster stewed in a creamy herb broth accented with refreshing citrus notes, while the latter serves up crunchy prawns coated with an addictive salted egg sauce that's both savoury and spicy-sweet.&lt;/span&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/86/79/1334561067986.jpg&quot; border=&quot;0&quot; alt=&quot;/86/79/1334561067986.jpg&quot; title=&quot;/86/79/1334561067986.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/09/26/1334570512609.jpg&quot; border=&quot;0&quot; alt=&quot;/09/26/1334570512609.jpg&quot; title=&quot;/09/26/1334570512609.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; vertical-align: middle; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;span style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;Continue your oceanic culinary conquest with the Deep Fried Red Tilapia with Nonya Sauce or the Deshelled Prawns Deep Fried with Wasabi-Mayo for a delightful fusion taste. &lt;/span&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/31/57/1334561295731.jpg&quot; border=&quot;0&quot; alt=&quot;/31/57/1334561295731.jpg&quot; title=&quot;/31/57/1334561295731.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; vertical-align: middle; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;img src=&quot;https://static.groupon.sg/65/57/1334561295765.jpg&quot; border=&quot;0&quot; alt=&quot;/65/57/1334561295765.jpg&quot; title=&quot;/65/57/1334561295765.jpg&quot; width=&quot;100%&quot; style=&quot;line-height: normal; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; vertical-align: middle; padding: 0px; margin: 0px;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;span style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;The family-friendly atmosphere, combined with fresh ingredients and a modern interior makes the Singapore Seafood Republic a quintessential Singaporean seafood experience. Bring along friends and family for communal indulgence at the unique concept restaurant that aims to sate every seafood craving.&lt;/span&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;br style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot; /&gt;&lt;/p&gt;&lt;h2 style=&quot;line-height: 1.2em; margin-bottom: 10px; margin-top: 0px; font-size: 18px; color: #000000; background-color: #89b556;&quot;&gt;&lt;span style=&quot;width: auto; height: auto; color: #0981be;&quot;&gt;&lt;strong&gt;Reviews &amp;amp; Accolades&lt;/strong&gt;&lt;/span&gt;&lt;/h2&gt;&lt;p style=&quot;margin-top: 1.5em; margin-right: 0px; margin-bottom: 1.5em; margin-left: 0px; line-height: 18px; width: auto; height: auto; color: #000000; font-family: Arial, Helvetica, sans-serif; background-color: #89b556; padding: 0px;&quot;&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #929497;&quot;&gt;Singapore Seafood Republic has been featured in The Straits Times, Lianhe Zaobao, Shin Min Daily News, The New Paper, The Peak and Tropara Magazine.&lt;em&gt;&lt;br /&gt;&lt;/em&gt;&lt;/span&gt;&lt;/p&gt;&lt;blockquote style=&quot;color: #000000; font-family: Arial, Helvetica, sans-serif; line-height: 18px; background-color: #89b556;&quot;&gt;&lt;img src=&quot;https://static.groupon.fr/22/50/1299526165022.gif&quot; border=&quot;0&quot; alt=&quot;Retrait&quot; title=&quot;Retrait&quot; width=&quot;14&quot; height=&quot;11&quot; style=&quot;line-height: normal; padding: 0px; margin: 0px;&quot; /&gt; &lt;span style=&quot;width: auto; height: auto; display: inline; color: #929497;&quot;&gt;&lt;em&gt;The Deshelled Prawns Deep-Fried with Wasabi Mayo (from $22 for a small serving) makes for an excellent starter, every prawn bursting forth with tangy mayo and bouncy flesh... The Crispy Butter Floss crab (seasonal price) makes for an unusual take on the ubiquitous chilli crab dish, a monster of a creature at 1.4kg served with crispy floss; the flesh is incredibly fresh and sweet, falling off the shell easily. Tastes uncannily like cereal prawns, but with more oomph.&quot;&lt;/em&gt;&lt;br /&gt;&lt;/span&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #0981be;&quot;&gt;- &lt;a href=&quot;http://sg.lifestyleasia.com/en/features/wine-and-dine/singapore-seafood-republic-8657/&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;www.lifestyleasia.com&lt;/a&gt;&lt;/span&gt;&lt;strong&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #0981be;&quot;&gt;&lt;br /&gt;&lt;br /&gt;&lt;/span&gt;&lt;img src=&quot;https://static.groupon.fr/22/50/1299526165022.gif&quot; border=&quot;0&quot; alt=&quot;Retrait&quot; title=&quot;Retrait&quot; width=&quot;14&quot; height=&quot;11&quot; style=&quot;line-height: normal; padding: 0px; margin: 0px;&quot; /&gt;&lt;/strong&gt; &lt;span style=&quot;width: auto; height: auto; display: inline; color: #929497;&quot;&gt;&lt;em&gt;...we found every crab tasty, and some, truly lip-smacking... 1) Singapore Chilli Crab Good balance of hot, sweet, salty, savoury and prawny-sambal flavours...5) Crispy Butter Floss Crab No need to floss the pincer, just crack it and enjoy the sweet meat with the buttery strands...It requires repeat visits - there are just too many good dishes waiting for us to try. Overheard from a nearby table - the drunken prawns were good. And the heady sweet smell from a large Alaskan crab being sent to a private room seduced our nose. Ahhh...crab heaven indeed!&quot;&lt;/em&gt;&lt;br /&gt;&lt;/span&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #0981be;&quot;&gt;- &lt;a href=&quot;http://www.soshiok.com/article/13614&quot; target=&quot;_blank&quot; style=&quot;line-height: normal; width: auto; height: auto; display: inline; color: #1c4c7c; padding: 0px; margin: 0px;&quot;&gt;SoShiok.com&lt;/a&gt;&lt;/span&gt;&lt;span style=&quot;width: auto; height: auto; display: inline; color: #0981be;&quot;&gt;&lt;br /&gt;&lt;/span&gt;&lt;/blockquote&gt;";

		data = HTMLEntities.unhtmlAngleBrackets(data);
		data = HTMLEntities.unhtmlAmpersand(data);
		data = HTMLEntities.unhtmlSingleQuotes(data);
		data = HTMLEntities.unhtmlDoubleQuotes(data);
		data = HTMLEntities.unhtmlentities(data);

		String html = AppConfig.TEMPLATE_WEB_SOURCE.replace("%@", data);

		Log.i("LOG APP", html);

		// loadDataWithBaseURL(String baseUrl, String data, String mimeType,
		// String encoding, String historyUrl)
		webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

		long diff = 0;
		// Tao dong ho dem nguoc

		String date1 = "2012-05-26 11:00:00";
		String date2 = "2012-05-26 14:00:00";

		diff = Utils.totalSecondBetween2Date(date1, date2,
                AppConfig.SERVER_TIME_FORMAT);
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

				Log.i("LOG APP", "seconds remaining: " + millisUntilFinished
						/ 1000);
				lblTime.setText(days + " days " + hour + ":" + min + ":" + sec);

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				// Khi thoi gian het han hay thoi gian count down < 0
				lblTime.setText("done!");
			}
		}.start();

	}

	class JavaScriptInterface {

		public void getContentHeight(String value) {
			if (value != null) {
				webviewContentWidth = Integer.parseInt(value);

				Log.i("LOG APP", "Result from javascript: "
						+ webviewContentWidth);

				Toast.makeText(
						WebViewDemoActivity.this,
						"ContentWidth of webpage is: " + webviewContentWidth
								+ "px", Toast.LENGTH_SHORT).show();

				runOnUiThread(changWebView);
			}
		}
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);

			Log.i("LOG APP", "onPageStarted");

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);

			Log.i("LOG APP",
					"Chieu cao la view =  " + webView.getContentHeight());
			// Log.i("LOG APP", "Chieu cao la webView = " +
			// webView.getContentHeight());

			int height = webView.getContentHeight();

			// Convert dp -> dx
			float density = WebViewDemoActivity.this.getResources()
					.getDisplayMetrics().density;
			int htmlHeight = Math.round(height * density);

			// LayoutParams params = webView.getLayoutParams();
			LayoutParams params = webView.getLayoutParams();

			if (params.height <= htmlHeight) {
				params.height = Math.round(htmlHeight);

				webView.setLayoutParams(params);

				// view.clearView();
				webView.requestLayout();
				webView.invalidate();

				Log.i("LOG APP", "change WebView height");
			}

			Log.i("LOG APP", "px = " + htmlHeight);
			// Log.i("LOG APP", "px = " + Math.round(htmlHeight));

		}

	}

}