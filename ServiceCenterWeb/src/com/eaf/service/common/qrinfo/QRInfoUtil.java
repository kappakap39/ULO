package com.eaf.service.common.qrinfo;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

public class QRInfoUtil {
	
	public static String prettyJSON(String json) {
		JsonParser parser = new JsonParser();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			JsonObject jsonObj = parser.parse(json).getAsJsonObject();
			return gson.toJson(jsonObj);
		}
		catch (Exception e) {
			return json;
		}
	}
	
	public static String toMsgLink(String module, int index, String inputStr) 
	{
		return "<a id=\"Msg" + module + index + "\" onclick=\"displayDiv(this);\"> Msg </a><div id=\"divMsg" + module + index + "\" style=\"display: none;\"><pre>" +  prettyJSON(inputStr) + "</pre></div>";
	}
	
	public static String getJSONFromStringHashMap(HashMap<String, String> inputMap) {
		Writer writer = new StringWriter();
		JsonGenerator jsonGenerator;
		String outputResponse = null;
		try {
			jsonGenerator = new JsonFactory().createJsonGenerator(writer);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(jsonGenerator, inputMap);
			jsonGenerator.close();
			outputResponse = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputResponse;
	}
	
	public static String tdFormatSimple(String... strs)
	{
		String formatStr = "";
		for(String str : strs)
		{
			formatStr += "<td>" + str + "</td>";
		}
		return "<tr>" + formatStr + "</tr>";
	}

}
