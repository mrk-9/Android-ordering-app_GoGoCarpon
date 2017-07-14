package com.gogocarpon.gogocarpon._app.models;

import java.io.Serializable;
import java.util.HashMap;

public class Category extends Object implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5688412464984347056L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	
	public String _id;
	public String _name;
	public String _desc;
	
	public String _created_at;
	public String _updated_at;
	

	public Category()
	{
		
	}
	
	public Category(HashMap<String, String> value)
	{
		this._id = value.get("id");
		this._name = value.get("name");
		this._desc = value.get("description");
		
		this._created_at = value.get("created_at");
		this._updated_at = value.get("updated_at");
		
	}
	
	
}
