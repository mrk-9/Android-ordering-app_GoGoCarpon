package com.gogocarpon.gogocarpon._app.baseclass;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager.BadTokenException;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.ui.listeners.IGrabRequestUrlListener;


/*
 * http://www.vogella.com/articles/ApacheHttpClient/article.html
try {
      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
      nameValuePairs.add(new BasicNameValuePair("Email", "youremail"));
      nameValuePairs.add(new BasicNameValuePair("Passwd", "yourpassword"));
      nameValuePairs.add(new BasicNameValuePair("accountType", "GOOGLE"));

      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
      HttpResponse response = client.execute(post);
      
    } catch (IOException e) {
      e.printStackTrace();
    } 
*/

public class BasicGrabRequestUrl extends AsyncTask<String, Void, Void> {

	private final String LOG_TAG = "GrabRequestURL";
	public static final int DEFAULT_ERROR_ID = 1024;

	public static final int URL_ERROR_ID = DEFAULT_ERROR_ID + 1;
	public static final int PROTOCOL_ERROR_ID = URL_ERROR_ID + 1;
	public static final int CONNECT_INTERNET_ERROR_ID = PROTOCOL_ERROR_ID + 1;
	public static final int SESSION_ERROR_ID = CONNECT_INTERNET_ERROR_ID + 1;

	private IGrabRequestUrlListener<String, Integer> callback;

	private String Content;
	private String Error = null;
	private int errorCode = DEFAULT_ERROR_ID;
	private Context context = null;

	private List<NameValuePair> nameValuePairs;

	private int messageID = DEFAULT_ERROR_ID;

	private ProcessDialogCustom _dialogLoading;
	private boolean _showLoading;

	public BasicGrabRequestUrl(Context context,
			IGrabRequestUrlListener<String, Integer> callbackClass,
			List<NameValuePair> _nameValuePairs) {
		this.context = context;
		this.callback = callbackClass;
		nameValuePairs = _nameValuePairs;
	}

	public void executeRequest(String URL, int messageID) {
		this.executeRequest(URL, messageID, true);
	}

	public void executeRequest(String URL, int messageID, boolean showLoading) {
		_showLoading = showLoading;
		this.messageID = messageID;
		this.execute(URL);
	}

	public Map<String, String> getQueryMap(String query) {
		try {
			String[] params = query.split("&");
			Map<String, String> map = new HashMap<String, String>();
			for (String param : params) {
				String name = param.split("=")[0];
				String value = param.split("=")[1];
				map.put(name, value);
			}
			return map;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

	}

	protected void onPreExecute() {

		// Call back begin task
		this.callback.onAsyncTaskBegin();

		try {
			
			if (_showLoading == true) {
				_dialogLoading = new ProcessDialogCustom(this.context);
				_dialogLoading.initDialog("", this.context.getString(R.string.notify_loading));
				_dialogLoading.showDialog();
			}

		} catch (BadTokenException e) {
			// TODO: handle exception
			Log.d("My Log", "BadTokenException");
			_dialogLoading = null;
			_showLoading = false;
		}

	}

	protected Void doInBackground(String... urls) {

		// Call back do background task
		this.callback.onAsyncTaskDoBackground(urls);

		try {

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urls[0]);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Content = httpclient.execute(httppost, responseHandler);

		} catch (ClientProtocolException e) {
			Log.d(LOG_TAG, "In Exception1");
			Error = e.getMessage();
			// Loi sai duong dan url
			errorCode = URL_ERROR_ID;
		} catch (IOException e) {
			Log.d(LOG_TAG, "In Exception2");
			Error = e.getMessage();
			// Loi khong co ket noi internet
			errorCode = DEFAULT_ERROR_ID;
		} catch (Exception e) {
			Log.d(LOG_TAG, "In Exception3");
			// Loi khong co http
			Error = e.getMessage();
			errorCode = PROTOCOL_ERROR_ID;
			cancel(true);
			e.printStackTrace();
		}
		return null;
	}

	// got data and parse data from server
	protected void onPostExecute(Void unused) {

		if (_showLoading == true) {
			_dialogLoading.dismissMyDialog();
		}

		if (Error != null) {
			Log.d(LOG_TAG, "BasicGrabRequestUrl Request Error - " + Error);
			callback.onAsyncTaskError(Error, errorCode);
		} else {
			Log.d(LOG_TAG, "BasicGrabRequestUrl Request result - " + Content);

			try {
				JSONObject oJSON = new JSONObject(Content);

				if (Content.equalsIgnoreCase("ERROR")) // Have Result but it's
				{
					callback.onAsyncTaskError(oJSON.getString("Message"),
							this.messageID);
					return;
				}

				// A Simple JSONObject Creation
				String strStatus = oJSON.getString("Type");
				if (!strStatus.equals("OK")) {
					callback.onAsyncTaskError(oJSON.getString("Message"),
							this.messageID);
					return;
				} else {
					if (oJSON.has("Data"))
						callback.onAsyncTaskComplete(oJSON.getString("Data"),
								this.messageID);
					else
						callback.onAsyncTaskComplete(Content, this.messageID);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				callback.onAsyncTaskError(e.toString(), DEFAULT_ERROR_ID);
			}

		}
	}

}
