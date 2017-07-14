package com.gogocarpon.gogocarpon._app.baseclass;

import java.io.Serializable;
import java.util.HashMap;


public class ActParameter extends Object implements Serializable{

	/**
	 * Can not use : ActParameter extends HashMap<Object, Object> implements Serializable
	 * Error : ExceptionCastObject
	 */
	private static final long serialVersionUID = 4794742759278011350L;

	private HashMap<Object, Object> holder = null;
	
	public ActParameter() {
		super();
		holder = new HashMap<Object, Object>();
	}
	
	public ActParameter(HashMap<Object, Object> base) {
		super();
		holder = base;
	}
	
	public Object get(Object key) {
		return holder.get(key);
	}
	
	public Object put(Object key, Object value) {
		return holder.put(key, value);
	}

	public Object remove(Object key) {
		return holder.remove(key);
	}
	
    public boolean containsKey(Object key) {
    	return holder.containsKey(key);
    }
    
    public boolean containsValue(Object value) {
    	return holder.containsValue(value);
    }
	
    public int size() {
    	return holder.size();
    }
    
    public boolean isEmpty() {
    	return holder.isEmpty();
    }
	
	
}
