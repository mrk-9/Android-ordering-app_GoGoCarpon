package com.gogocarpon.gogocarpon._app.models;

import java.io.Serializable;
import java.util.HashMap;

public class PayPal extends Object implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5688412464984347056L;
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	
	public String api_username;
	public String merchant_email;
	public String signature;
	
	public String country_code;
	public String currency_code;
	

	public PayPal()
	{
		
	}
	
	public PayPal(HashMap<String, String> value)
	{
		this.api_username = value.get("api_username");
		this.merchant_email = value.get("merchant_email");
		this.signature = value.get("signature");
		
		this.country_code = value.get("country_code");
		this.currency_code = value.get("currency_code");
		
	}
	
	
}
