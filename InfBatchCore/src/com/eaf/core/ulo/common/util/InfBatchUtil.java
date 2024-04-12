package com.eaf.core.ulo.common.util;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class InfBatchUtil {
	private static transient Logger logger = Logger.getLogger(InfBatchUtil.class);
    public static boolean empty(String data){
        return (data == null || "null".equals(data) || data.length() == 0 || data.trim().length() == 0);
    } 
    public static boolean empty(ArrayList<?> vList){
     	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    public static boolean empty(Vector<?> vVect){
    	return (null == vVect || vVect.size() == 0 || vVect.isEmpty());
    }
    public static boolean empty(BigDecimal num){
     	return (null == num);
    }
    public static boolean empty(HashMap<?,?> hashMap){
     	return (null == hashMap || hashMap.size() == 0 || hashMap.isEmpty());
    }
    public static boolean empty(Date date){
     	return (null == date);
    }
    public static boolean empty(List<?> vList){
     	return (null == vList || vList.size() == 0 || vList.isEmpty());
    }
    public static boolean empty(Set<?> vSet){
     	return (null == vSet || vSet.size() == 0 || vSet.isEmpty());
    }
    public static boolean empty(Object obj){    	
     	if(obj instanceof String){
     		return empty((String)obj);
     	}else if(obj instanceof ArrayList<?>){
     		return empty((ArrayList<?>)obj);
     	}else if(obj instanceof Vector<?>){
     		return empty((Vector<?>)obj);
     	}else if(obj instanceof BigDecimal){
     		return empty((BigDecimal)obj);
     	}else if(obj instanceof Date){
     		return empty((Date)obj);
     	}else if(obj instanceof HashMap<?,?>){
     		return empty((HashMap<?,?>)obj);
     	}else if(obj instanceof List<?>){
     		return empty((List<?>)obj);
     	} else if(obj instanceof Set<?>){
     		return empty((Set<?>)obj);
     	} else if(obj instanceof Integer) {
     		int val = ((Integer)obj).intValue();
     		if(val == 0 ){
     			return true;
     		}
     	}
     	return (null == obj);
    }
    public static boolean number(String num) {
         return (empty(num))?true:num.matches("-?\\d+(.\\d+)?");
    }
 	public static java.sql.Date getPreviousMonth(java.sql.Date inputDate, int prevIndex) {
 		Calendar calen = Calendar.getInstance();
 		calen.setTime(inputDate);
 		calen.set(Calendar.DATE, 1);
 		calen.add(Calendar.MONTH, -prevIndex);
 		return new java.sql.Date(calen.getTimeInMillis());
 	}
 	public static int compareBigDecimal(BigDecimal num1,BigDecimal num2) {
 	    return num1.compareTo(num2);
 	}
 	public static int stringToInt(String num) {
 		if(null!=num){
 			return Integer.valueOf(num);
 		}
 		return 0;
 	}
 	public static java.util.Date convertDate(String systemDate, String format) {
 		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
 		java.util.Date date = null;
 		if(!InfBatchUtil.empty(systemDate)){
 			try {
 				date = dateFormat.parse(systemDate);
 			} catch (ParseException e) {
 				date = null;
 				e.printStackTrace();
 			}
 		}
 		return date;
 	}
 	
 	public static String convertDate(java.util.Date systemDate, String format) {
 		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
 		String date = null;
 		if(!InfBatchUtil.empty(systemDate)){
 			date = dateFormat.format(systemDate);
 		}
 		return date;
 	}
 	
	public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
	    return iterable == null ? Collections.<T>emptyList() : iterable;
	}
	
	public static void resultAsJSON(Writer output, ResultSet resultSet) throws SQLException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String[] _charTypes = {"char","varchar","varchar2","nchar","nvarchar2","clob"};
		String[] _numericTypes = {"number"};
		String[] _datetimeTypes = {"date"};
		String[] _timestampTypes = {"timestamp"};
		List<String> charTypes = Arrays.asList(_charTypes);
		List<String> numericTypes = Arrays.asList(_numericTypes);
		List<String> datetimeTypes = Arrays.asList(_datetimeTypes);
		List<String> timestampTypes = Arrays.asList(_timestampTypes);
		
	    while (resultSet.next()) {
	        int total_cols = resultSet.getMetaData().getColumnCount();
	        JsonObject jsonObj = new JsonObject();
	        for (int i=0; i < total_cols; i++) {
	            String colName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            String colType = resultSet.getMetaData().getColumnTypeName(i + 1).toLowerCase();
	            //logger.debug("colType=" + colType);
	            if (numericTypes.contains(colType))
		            jsonObj.addProperty(colName, resultSet.getBigDecimal(i+1));
	            else if (datetimeTypes.contains(colType)) {
	            	Date date = resultSet.getDate(i+1);
		            jsonObj.addProperty(colName, (date!=null)?df.format(date):"null");
	            }
	            else if (timestampTypes.contains(colType)) {
	            	Timestamp timestamp = resultSet.getTimestamp(i+1);
		            jsonObj.addProperty(colName, (timestamp!=null)?df.format(timestamp):"null");
	            }
	            else if (charTypes.contains(colType))
	            	jsonObj.addProperty(colName, resultSet.getString(i+1));
	            else
	            	logger.error("Found unsupport data type => " + colType);
	        }
	        output.write(jsonObj.toString());
	        output.write("\n");
	    }
	}
	
	public static String resultAsJSONString(ResultSet resultSet) throws SQLException, IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		List<String> charTypes = Arrays.asList("char","varchar","varchar2","nchar","nvarchar2","clob");
		List<String> numericTypes = Arrays.asList("number");
		List<String> datetimeTypes = Arrays.asList("date");
		List<String> timestampTypes = Arrays.asList("timestamp");
		String jsonString = "";
	    while (resultSet.next()) {
	        int total_cols = resultSet.getMetaData().getColumnCount();
	        JsonObject jsonObj = new JsonObject();
	        for (int i=0; i < total_cols; i++) {
	            String colName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
	            String colType = resultSet.getMetaData().getColumnTypeName(i + 1).toLowerCase();
	            //logger.debug("colType=" + colType);
	            if (numericTypes.contains(colType))
		            jsonObj.addProperty(colName, resultSet.getBigDecimal(i+1));
	            else if (datetimeTypes.contains(colType)) {
	            	Date date = resultSet.getDate(i+1);
		            jsonObj.addProperty(colName, (date!=null)?df.format(date):"null");
	            }
	            else if (timestampTypes.contains(colType)) {
	            	Timestamp timestamp = resultSet.getTimestamp(i+1);
		            jsonObj.addProperty(colName, (timestamp!=null)?df.format(timestamp):"null");
	            }
	            else if (charTypes.contains(colType))
	            	jsonObj.addProperty(colName, resultSet.getString(i+1));
	            else
	            	logger.error("Found unsupport data type => " + colType);
	        }
	        jsonString += jsonObj.toString();
	        jsonString += "\n";
	    }
		return jsonString;
	}
	public static boolean eApp(String source){
		if(SystemConfig.containsGeneralParam("EAPP_SOURCE", source)){
			return true;
		}
		return false;
	}
}
