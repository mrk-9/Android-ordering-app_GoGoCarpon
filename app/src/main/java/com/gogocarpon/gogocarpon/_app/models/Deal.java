package com.gogocarpon.gogocarpon._app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Deal extends Object implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String _id;
	public String _deal_code;
	public String _deal_status;
	
	public String _name;
	public String _highlight;
	public String _desc;
	public String _terms;
	public String _short_desc;
	public String _pic_dir;
	
	public float _origin_price;
	public float _price;
	public float _discount;
	public float _prepay_percent;
	
	public String _start_at;
	public String _end_at;
	
	public String _merchant_id;
	public String _merchant_name;
	public String _merchant_address;
	public String _merchant_telephone;
	public String _merchant_long;
	public String _merchant_lat;
	
	public int _use_dynamic;
	public int _hot_deal;
	public String _video_desc;
	public float _price_buy;
	public String _cat_ids;
	
	public String _price_step_json;
	public ArrayList<HashMap<String,String>> _price_step;	
	
	public String _deal_types_json;
	public String _currency_code;
	public ArrayList<DealType> _deal_types;

	public Deal()
	{
		
	}
	
	public Deal(HashMap<String, String> value)
	{
		this._id = value.get("id");
		this._deal_status = value.get("deal_status");
		this._deal_code = value.get("deal_code");
		this._name = value.get("name");
		this._highlight = value.get("highlight");
		
		this._desc = value.get("description");
		this._terms = value.get("terms");
		
		this._short_desc = value.get("short_desc");
		this._pic_dir = value.get("pic_dir");
		
		this._origin_price = Float.parseFloat(value.get("origin_price"));
		this._price = Float.parseFloat(value.get("price"));
		this._discount = Float.parseFloat(value.get("discount"));
		this._prepay_percent = Float.parseFloat(value.get("prepay_percent"));
		
		this._start_at = value.get("start_at");
		this._end_at = value.get("end_at");
		
		this._use_dynamic = Integer.parseInt(value.get("use_dynamic"));
		this._hot_deal = Integer.parseInt(value.get("hot_deal"));
		this._video_desc = value.get("video_desc");
		this._price_buy = Float.parseFloat(value.get("price_buy"));
		this._cat_ids = value.get("cat_ids");
		
		this._price_step_json = value.get("price_step");
		this._deal_types_json = value.get("deal_types");
		this._currency_code = value.get("currency_code");
		
	}
	
	public ArrayList<DealType> loadDealType(String deal_types_json)
	{
		if(deal_types_json.equals("")) 
			return null;
		
		try {
			
			ArrayList<DealType> arrDealType = new ArrayList<DealType>();
			
			JSONArray jArrDealType = new JSONArray(deal_types_json);
			
			for (int i = 0; i < jArrDealType.length(); ++i) {

				JSONObject rec = jArrDealType.getJSONObject(i);

//				id":"336","name":"Hot Doc","price":"80","origin_price":"100",
//				"deal_id":"1","status":"1","code":"AT1212-00001","price_step":[],"price_buy":"80","discount":20
				
				DealType obj = new DealType();

				obj._id = rec.getString("id");
				obj._status = rec.getString("status");
				obj._code = rec.getString("code");
				obj._name = rec.getString("name");
				obj._deal_id = rec.getString("deal_id");

				obj._price = (float) rec.getDouble("price");
				obj._price_buy = (float) rec.getDouble("price_buy");
				obj._origin_price = (float) rec.getDouble("origin_price");
				obj._discount = (float) rec.getDouble("discount");
				
				obj._price_step_json = rec.getString("price_step");
				obj._price_step = this.loadPriceStep(rec.getString("price_step"));
				
				arrDealType.add(obj);
			}			

			return arrDealType;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<HashMap<String,String>> loadPriceStep(String price_step_json)
	{
		if(price_step_json.equals("")) 
			return null;
		
		try {
			JSONArray jArrDealType = new JSONArray(price_step_json);
			
//			:[{"quantity":80,"price":"10"},{"quantity":70,"price":"30"}]
			
			ArrayList<HashMap<String,String>> price_step = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < jArrDealType.length(); ++i) {

				JSONObject rec = jArrDealType.getJSONObject(i);

				HashMap<String,String> m = new HashMap<String,String>();
				
				m.put("quantity", rec.getString("quantity"));
				m.put("price", rec.getString("price"));
				
				price_step.add(m);
			}			
			
			return price_step;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
