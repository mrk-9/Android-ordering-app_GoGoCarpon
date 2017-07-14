package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;


public class DiscussListAdapter extends BaseAdapter {

	private Activity activity;
	@Override
	public boolean areAllItemsEnabled() {
		// TODO Auto-generated method stub
//		return super.areAllItemsEnabled();
		return false;		
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
//		return super.isEnabled(position);
		return false;
	}

	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;

	public DiscussListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
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
		public TextView content, name, date;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.row_item_discuss, null);
			holder = new ViewHolder();
			holder.content = (TextView) vi.findViewById(R.id.lblContent);
			holder.name = (TextView) vi.findViewById(R.id.lblName);
			holder.date = (TextView) vi.findViewById(R.id.lblDate);
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		HashMap<String, String> temp = (HashMap<String, String>) data.get(position);
		
		holder.content.setText(temp.get("content"));
		
		holder.name.setText(activity.getString(R.string.em_discuss_by) + temp.get("name"));
		holder.date.setText(activity.getString(R.string.em_discuss_date) + temp.get("date"));
		
		return vi;
	}
	
//	boolean  areAllItemsEnabled() {
//	    return false;
//	}
	

//	boolean  isEnabled(int position) {
//	   // return true for clickable, false for not
//		return false;
//	}	
	
}
