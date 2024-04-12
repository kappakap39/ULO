package com.ava.flp.eapp.iib.deserializer;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateDeserializer implements JsonDeserializer<Date>{

	@Override
	public Date deserialize(JsonElement json, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		// TODO Auto-generated method stub
		if(json==null) return null;
		String datestr=json.getAsString();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=null;
		try {
			date =  new Date(format.parse(datestr).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
		return date;
	}
	

}
