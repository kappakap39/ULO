package com.ava.flp.eapp.iib.serializer;

import java.lang.reflect.Type;

import org.joda.time.DateTime;

import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeSerializer implements JsonSerializer<DateTime>{

	@Override
	public JsonElement serialize(DateTime arg0, Type arg1,
			JsonSerializationContext arg2) {
		String datestr=DecisionServiceUtil.dateToString(arg0.toDate(), "yyyy-MM-dd'T'HH:mm:ss.SSS'+0000'");
		return new JsonPrimitive(datestr);
	}

}
