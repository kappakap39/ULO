package com.eaf.orig.ulo.pl.app.utility;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonObjectUtil {
	
	private JSONArray jSONArray;
	
	Logger logger = Logger.getLogger(JsonObjectUtil.class);
	
	public JsonObjectUtil() {
		jSONArray = new JSONArray();
	}	
	public void CreateJsonObject(String id ,String value){
//		JSONObject _JSONObject = new JSONObject();
//		_JSONObject.put("id",id);
//		_JSONObject.put("value",value);
//		jSONArray.put(_JSONObject);
	}	
	public void CreateJsonObject(String id ,int value){
//		JSONObject _JSONObject = new JSONObject();
//		_JSONObject.put("id",id);
//		_JSONObject.put("value",value);
//		jSONArray.put(_JSONObject);
	}
	public void ResponseJsonArray (HttpServletResponse response){		
//		logger.debug("[ResponseJsonArray].."+jSONArray);
		try{
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();		
			out.println(jSONArray.toString());
			out.close();
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}
	}	
	public String returnJson(){
		return jSONArray.toString();
	}
	public void CreateJsonMessage(String messageType ,String value){
//		JSONObject _JSONObject = new JSONObject();
//		_JSONObject.put("message",messageType);
//		_JSONObject.put("value",value);
//		jSONArray.put(_JSONObject);
	}
	public JSONArray getjSONArray() {
		return jSONArray;
	}
	public void setjSONArray(JSONArray jSONArray) {
		this.jSONArray = jSONArray;
	}	
}
