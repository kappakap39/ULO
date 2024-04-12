package com.eaf.core.ulo.common.rest;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTypeAdapter implements JsonSerializer<Date>,JsonDeserializer<Date>{
	@Override
	public JsonElement serialize(Date src, Type arg1, JsonSerializationContext arg2){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US);
		String dateFormatAsString = format.format(src.getTime());
		return new JsonPrimitive(dateFormatAsString);
	}
	@Override
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
		if(!(json instanceof JsonPrimitive)){
			throw new JsonParseException("The date should be a string value");
		}
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US);
			java.util.Date date = format.parse(json.getAsString());
			return new Date(date.getTime());
		}catch(Exception e){
			throw new JsonParseException(e);
		}
	}
}
