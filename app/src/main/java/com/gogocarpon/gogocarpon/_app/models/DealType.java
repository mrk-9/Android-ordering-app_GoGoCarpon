package com.gogocarpon.gogocarpon._app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DealType extends Object implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String _id;
	public String _deal_id;
	public String _name;
	
	public float _origin_price;
	public float _price;
	public String _status;
	public String _code;
	public float _price_buy;
	public float _discount;
	
	public String _price_step_json;
	public ArrayList<HashMap<String,String>> _price_step;

	public DealType()
	{
		
	}
	
	public DealType(String deal_id,HashMap<String, String> value)
	{
		this._id = value.get("id");
		this._deal_id = deal_id;
		this._name = value.get("name");
		this._origin_price = Float.parseFloat(value.get("origin_price"));
		this._price = Float.parseFloat(value.get("price"));
		this._status = value.get("status");
		this._code = value.get("code");
		
		this._price_buy = Float.parseFloat(value.get("price_buy"));
		this._discount = Float.parseFloat(value.get("discount"));
		this._price_step_json = value.get("_price_step");
		
//		{"id":"336","name":"Hot Doc","price":"80","origin_price":"100","deal_id":"1",
//		"status":"1","code":"AT1212-00001","price_step":[],"price_buy":"80","discount":20}
		
	}
	
	@Override
	public String toString() {
		return "id = " + this._id + " ; name =  " + this._name;
	}

	
}
