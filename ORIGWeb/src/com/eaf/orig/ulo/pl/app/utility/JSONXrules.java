package com.eaf.orig.ulo.pl.app.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.shared.util.OrigUtil;

public class JSONXrules{
	private JSONArray array;
	public JSONXrules(){
		array = new JSONArray();
	}
	public void doDisplayEmpty(String serviceID,String code ,String value){
		JSONObject obj = new JSONObject();
//			obj.put("type","service");
//			obj.put("id",serviceID);
//			obj.put("code",isEmpty(code));
//			obj.put("value",isEmpty(value));
//			obj.put("style",ORIGLogic.LogicColorStyleResult(code,value));
		array.put(obj);
	}
	
	public void doDisplayEmpty(String serviceID,String value){
		JSONObject obj = new JSONObject();
//			obj.put("type","service");
//			obj.put("id",serviceID);
//			obj.put("code","");
//			obj.put("value",isEmpty(value));
//			obj.put("style","");
		array.put(obj);
	}
	public void doDisplayEmptyCode(String serviceID,String value){
		JSONObject obj = new JSONObject();
//			obj.put("type","service");
//			obj.put("id",serviceID);
//			obj.put("code",isEmpty(value));
//			obj.put("value","");
//			obj.put("style","");
		array.put(obj);
	}
	public void doDisplayEmpty(String serviceID,String code ,String value,String fieldID01){
		JSONObject obj = new JSONObject();
//			obj.put("type","service");
//			obj.put("id",serviceID);
//			obj.put("code",isEmpty(code));
//			obj.put("result",isEmpty(value));
//			obj.put("field01",isEmpty(fieldID01));
//			obj.put("style",ORIGLogic.LogicColorStyleResult(code,value));
		array.put(obj);
	}
	public String isEmpty(String value){
		if(OrigUtil.isEmptyString(value)) return "";
		return value;
	}
	public String response(){
		return array.toString();
	}
	public void doButtonExecute(String button){		
		JSONObject obj = new JSONObject();
//		obj.put("type","button");
//		obj.put("id",button);
//		obj.put("style","button-red");
		array.put(obj);
	}
	public void doButtonEdit(String button){		
		JSONObject obj = new JSONObject();
//		obj.put("type","edit");
//		obj.put("id",button);
//		obj.put("style","button");
		array.put(obj);
	}
}
