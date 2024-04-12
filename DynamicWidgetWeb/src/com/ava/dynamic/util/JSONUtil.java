package com.ava.dynamic.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSONUtil {
	static final transient Logger log = LogManager.getLogger(JSONUtil.class);
	private static final Gson gson = new Gson();
	
	public static String toJson(Object o){
		return gson.toJson(o);
	}
	
	public static <T> T fromJson(String json, T t){
		if(json == null){
			return null;
		}
		try{
			t = gson.fromJson(json, new TypeToken<T>(){}.getType());
		}catch(Exception e){
			log.error("Parsing JSON error : "+e.getLocalizedMessage());
		}
		return t;
	}
}
