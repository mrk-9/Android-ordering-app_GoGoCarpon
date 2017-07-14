package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.ui.listeners.CallbackOnListViewClickListener;
import com.gogocarpon.gogocarpon._app.ui.listeners.OnCustomListViewClickListener;


public class CategoryListItemAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	private OnCustomListViewClickListener callback; 

	public CategoryListItemAdapter(Activity a, ArrayList<HashMap<String, String>> d, OnCustomListViewClickListener callback) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		imageLoader = new ImageLoader(activity.getApplicationContext());
		imageLoader = ImageLoader.getInstance(activity.getApplicationContext());
		this.callback = callback;
		
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
//		return position;
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView title, header;
		public ImageView image;
		public ImageView imageHaveIt;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.activity_list_item_custom_row_category, null);
			holder = new ViewHolder();
			holder.title = (TextView) vi.findViewById(R.id.itemTitle);
			holder.header = (TextView) vi.findViewById(R.id.itemHeader);
			holder.image = (ImageView) vi.findViewById(R.id.itemImage);
			holder.imageHaveIt = (ImageView) vi.findViewById(R.id.itemImageHaveIt);

			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

//		HashMap<String, String> temp = new HashMap<String, String>();
//		temp.put("id", e.getString("id"));
//		temp.put("name", e.getString("name"));
//		temp.put("category_id", e.getString("category_id"));
//		temp.put("headline", e.getString("headline"));
//		temp.put("sub_headline", e.getString("sub_headline"));
//		temp.put("images", e.getString("images"));
//		temp.put("description", e.getString("description"));
		
		HashMap<String, String> temp = (HashMap<String, String>) data.get(position);
		
		holder.title.setText(temp.get("name"));
		holder.header.setText(temp.get("headline"));
//		holder.image.setTag(data[position]);
		
		String images = temp.get("images");
		
		String url = "http://10.0.2.2/swapanywhere/";
		String img_url = url + "templates/swapanywhere/images/img_no_photo.jpg";
		
		if(images.trim().length() > 0)
		{
			String[] img = images.split(",");
			if(img.length > 0)
				img_url = url + "SWAP/PRODUCT/" + img[0];
		}
		
		Log.d("My Log", img_url);
		
		holder.image.setTag(img_url);
		imageLoader.DisplayImage(img_url, activity, holder.image, null);
		
		if (temp.containsKey("star")) {
			if (temp.get("star").equals("0")) {
				holder.imageHaveIt.setImageResource(R.drawable.star_empty);
				holder.imageHaveIt.invalidate();
			} else if (temp.get("star").equals("1")) {
				holder.imageHaveIt.setImageResource(R.drawable.star_full);
				holder.imageHaveIt.invalidate();
			} else {
				holder.imageHaveIt.setVisibility(View.GONE);
			}
		} else
			holder.imageHaveIt.setVisibility(View.GONE);
		
		Log.d("My Log", "Position : " + position + " ----- star : " + temp.get("star"));
		
//		holder.imageHaveIt.setTag(temp);
		holder.imageHaveIt.setOnClickListener(new CallbackOnListViewClickListener(callback, position ,0));
		
		return vi;
	}
}