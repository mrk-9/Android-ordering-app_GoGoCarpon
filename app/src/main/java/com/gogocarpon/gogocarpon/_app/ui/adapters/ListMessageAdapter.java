package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;


@SuppressWarnings("unused")
public class ListMessageAdapter extends BaseAdapter {

	public ArrayList<HashMap<String, String>> list;
	Activity activity;
	
	private Context context;

	public ListMessageAdapter(Activity activity,
			ArrayList<HashMap<String, String>> list) {

		super();
		this.activity = activity;
		this.list = list;
		this.context = activity.getApplicationContext();
	}

	public int getCount() {
		return list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		ImageView imgView;
		TextView title, description, date;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = activity.getLayoutInflater();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_message_list, null);
			holder = new ViewHolder();

//			holder.imgView = (ImageView) convertView
//					.findViewById(R.id.table_icon);
//			holder.title = (TextView) convertView.findViewById(R.id.text_table);
//
//			holder.description = (TextView) convertView
//					.findViewById(R.id.text_table);
//
//			holder.date = (TextView) convertView
//					.findViewById(R.id.text_table);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<String, String> map = list.get(position);
		
		// Set Resource.
		holder.imgView.setImageResource(0);
		holder.title.setText(map.get("MESSAGE_COLUMN_TITLE"));
		holder.description.setText(map.get("MESSAGE_COLUMN_DESC"));
		holder.date.setText(map.get("MESSAGE_COLUMN_DATE"));

		return convertView;
	}

	public void setTableData(ArrayList<HashMap<String, String>> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
