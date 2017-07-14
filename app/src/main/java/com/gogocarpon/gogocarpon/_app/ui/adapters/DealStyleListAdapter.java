package com.gogocarpon.gogocarpon._app.ui.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.models.DealType;


public class DealStyleListAdapter extends BaseAdapter{

	private Activity activity;
	private ArrayList<DealType> data;
	private static LayoutInflater inflater = null;

	
	public DealStyleListAdapter(Activity a, ArrayList<DealType> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
		public TextView name, price, priceDesc, priceStep;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		ViewHolder holder;
		if (convertView == null) {
			vi = inflater.inflate(
					R.layout.row_item_deal_style, null);
			holder = new ViewHolder();
			holder.name = (TextView) vi.findViewById(R.id.lblName);
			holder.price = (TextView) vi.findViewById(R.id.lblPrice);
			holder.priceDesc = (TextView) vi.findViewById(R.id.lblPriceDesc);
			holder.priceStep = (TextView) vi.findViewById(R.id.lblPriceStep);
			
			vi.setTag(holder);
		} else
			holder = (ViewHolder) vi.getTag();

		DealType temp = data.get(position);
		
		holder.name.setText(temp._name.toString());
		holder.price.setText(temp._price_buy + activity.getString(R.string.em_price_signal));
		String deal_type_desc = activity.getResources().getString(R.string.em_deal_type_desc);
		deal_type_desc = deal_type_desc.replace("@1", temp._origin_price + "");
		deal_type_desc = deal_type_desc.replace("@2", temp._discount + "");
		deal_type_desc = deal_type_desc.replace("@3", (temp._origin_price - temp._price_buy) + "");
		holder.priceDesc.setText(Html.fromHtml(deal_type_desc));
		
		if (temp._price_step.size() > 0) {
			holder.priceStep.setVisibility(View.VISIBLE);
			String str_temp = activity.getResources().getString(R.string.em_deal_price_step);
			for (HashMap<String,String> price_step : temp._price_step) {
				
				String str = str_temp.replace("@1", price_step.get("price"));
				str = str.replace("@2", price_step.get("quantity"));
				holder.priceStep.setText(Html.fromHtml(str));
				
			}
		}
		
		return vi;
	}
	
	
	
	
}