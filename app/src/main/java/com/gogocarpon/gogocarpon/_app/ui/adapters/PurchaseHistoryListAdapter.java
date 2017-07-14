package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;


public class PurchaseHistoryListAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public PurchaseHistoryListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		imageLoader = new ImageLoader(activity.getApplicationContext());
		imageLoader = ImageLoader.getInstance(activity.getApplicationContext());

	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView name, date, status;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.row_item_purchase_history, null);
			holder = new ViewHolder();
			holder.name = (TextView) vi.findViewById(R.id.lblName);
			holder.date = (TextView) vi.findViewById(R.id.lblDate);
			holder.status = (TextView) vi.findViewById(R.id.lblStatus);

			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		HashMap<String, String> temp = (HashMap<String, String>) data.get(position);
	
		String status = temp.get("status");
		status = status.toLowerCase(Locale.ENGLISH);
		status.replace(' ', '_');
		
		String status_deal = AppConfig.ORDER_STATUS.get(status);
		
		holder.name.setText(temp.get("name"));
		holder.date.setText(activity.getString(R.string.em_purchase_history_date) + temp.get("date"));
//		holder.status.setText(activity.getString(R.string.em_purchase_history_status) + temp.get("status"));
		holder.status.setText(activity.getString(R.string.em_purchase_history_status) + status_deal);
		
		return vi;
	}
}
