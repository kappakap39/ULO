package com.ava.flp.eapp.iib.serializer;

import java.lang.reflect.Type;
import java.sql.Date;

import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateSerializer implements JsonSerializer<Date>{

	@Override
	public JsonElement serialize(Date arg0, Type arg1,
			JsonSerializationContext arg2) {
		String datestr=DecisionServiceUtil.dateToString(arg0, "yyyy-MM-dd HH:mm:ss");
		return new JsonPrimitive(datestr);
	}

}
