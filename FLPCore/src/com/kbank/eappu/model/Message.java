package com.kbank.eappu.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.kbank.eappu.enums.MessageEnum;

/**
 * A super class for message.
 * @author Pinyo.L 
 **/
public class Message implements Serializable, Cloneable {
	
	private static Logger logger = Logger.getLogger(Message.class);
	private static final String patternOri = "<([a-zA-Z0-9\\s]*)>";
	private static final String patternEsc = "&lt;([a-zA-Z0-9\\s]*)&gt;";
	private static final String patternUc = "\u003c([a-zA-Z0-9\\s]*)\u003e";
	
	@Expose
	protected MessageHeader header;
	
	@Expose
	protected MessageData data;
	
	/**
	 * Payload attributes
	 **/
	private String cardType;
	private String creditLine;
	private String creditLimit;
	private String creditAmount;
	private String documents;
	private String kbankTelNo;
	private String productName;
	private String cardNo;
	private String accountLast4Digit;
	private String term;
	private String interestRate;
	private String installmentAmt;
	private String transferAmount;
	private String productName2lang;
	private String fullCardNo;

	/**
	 * To place value of non-exposed filed into exposed field then transform into json-string. 
	 * @return transformed json-string.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * */
	public String toJson() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		transform();
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(this.getData());
	}
	
	/** To clone itself and additional attribute in extended class. </br>
	 *  Clone self must be override if child class contains Collection as additional attribute.
	 * @return Cloned object from master template. 
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	public Message cloneSelf() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
		return (Message) BeanUtils.cloneBean(this);
	}
	
	/** To transform value of @Exposed field from value that wasn't exposed
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	public void transform() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		logger.info("transform:begin");
		try {
			//header section
			logger.info("transforming header");
			transformHeader();
			
			//data
			logger.info("transforming data");
			transformData();
		} finally {
			logger.info("transform:end");
		}
	}
	
	/**
	 * To apply all available payloads into MessageHeader's String-field and Json-field .
	 * @param field is a field of MessageData object that want to transform its contains from &lt;Some Tag&gt; into business value located on payload-field kept on Message class.
	 * @param messageData is MessageData object which kept value on it.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private void transformHeader() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Field> dataFields = FieldUtils.getAllFieldsList(MessageHeader.class);
		for(Field field: dataFields) {
			if(String.class == field.getType() 
					&& strFilContainsPayload(field, getHeader())) {
				//Case field is String and containsPayload
				strFilApplyPayload(field, getHeader());
			}else if(JsonObject.class == field.getType()
					&& jsonFilContainsPayload(field, getHeader())){
				//Case field is jsonObject and containsPayload
				jsonFilApplyPayload(field , getHeader());
			}
		}
	}
	
	/**
	 * To apply all available payloads into MessageData's String-field and Json-field.
	 * @param field is a field of MessageData object that want to transform its contains from &lt;Some Tag&gt; into business value located on payload-field kept on Message class.
	 * @param messageData is MessageData object which kept value on it.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private void transformData() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Field> dataFields = FieldUtils.getAllFieldsList(MessageData.class);
		for(Field field: dataFields) {
			//remove check null generate blank tag
//			if(String.class == field.getType() 
//					&& strFilContainsPayload(field, getData())) {
			if(String.class == field.getType()){
				//Case field is String and containsPayload.
				strFilApplyPayload(field, getData());
//			}else if(JsonObject.class == field.getType()
//					&& jsonFilContainsPayload(field, getData())){
			}else if(JsonObject.class == field.getType()){
				//Case field is jsonObject and containsPayload.
				jsonFilApplyPayload(field , getData());
			}else if(List.class == field.getType()) {
				//Case field is List<String> and containsPayload.
				Type type = field.getGenericType();
			    if (type instanceof ParameterizedType) {
			    	ParameterizedType pType = (ParameterizedType)type;
//			    	if(String.class == pType.getActualTypeArguments()[0]
//			    			&& strLsContainsPayload(field, getData())){
			    	if(String.class == pType.getActualTypeArguments()[0]){
			    		strLsFilApplyPayload(field, getData());
			    	}
			    }
			}
		}
	}


	private void strLsFilApplyPayload(Field field, Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Retrieve value.
		Method method =  genGetterMethod(field, obj); 
        List<String> strs = (List<String>) method.invoke(obj); 
        List<String> appliedStrs = new ArrayList<>(10);

        
        //Transform.
        if(strs!=null && !strs.isEmpty()) {
        	for(String str: strs) {
        		appliedStrs.add(transformStringByPayload(str));
            }
        }
        
        //Set back to object.
        genSetterMethodStrLs(field, obj).invoke(obj,appliedStrs); ;
	}
	
	
	/**
	 * To generate getter method of field on inputed object.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @return method object of field on inputed object.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private Method genSetterMethodStrLs(Field field, Object obj) throws NoSuchMethodException, SecurityException {
		String metNm = "set"+firstCharToUpperCase(field.getName());
		logger.debug("expectMethodName = "+metNm);
		return obj.getClass().getMethod(metNm,new Class[]{List.class});
	}

	/**
	 * To check whether inputed field's value contains   <...> or \u003c...\u003e or not.
	 * @param field of object that want to check.
	 * @param obj is an object that want to check. 
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private boolean strLsContainsPayload(Field field, Object obj) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method =  genGetterMethod(field, obj); 
        List<String> strs = (List<String>) method.invoke(obj); 

        if(strs!=null && !strs.isEmpty()) {
        	for(String str: strs) {
            	if(containsPayload(str)) {
            		return true;
            	}
            	
            }
        }
        		
		return false;
	}

	/**
	 * To apply all available payloads into String's field.
	 * @param field is a field of MessageData object that want to transform its contains from &lt;Some Tag&gt; into business value located on payload-field kept on Message class.
	 * @param messageData is MessageData object which kept value on it.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private void strFilApplyPayload(Field field, Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Retrieve old string.
		String strFromField = genGetterMethodAndGetStr(field, obj);
		
		//Transform it.
		String transformedStr = transformStringByPayload(strFromField);
		
		//Apply transformed string back to field.
		genSetterMethodAndSetStr(field, obj, transformedStr);
	}
	
	
	/**
	 * To apply all available payloads into String's field.
	 * @param field is a field of MessageData object that want to transform its contains from &lt;Some Tag&gt; into business value located on payload-field kept on Message class.
	 * @param messageData is MessageData object which kept value on it.
	 * @return transformed String that replace of tag with value from payloads.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private String transformStringByPayload(String strFromField) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(StringUtils.isEmpty(strFromField)) {
			return "";
		}
		
		String tranformingStr = strFromField;
		//Strip payloads from String, expect something like list that contains <Credit card>, <Customer Type>
		Set<String> payloadTags = getPayloadTags(tranformingStr);
		
		//For eachPayload tag found in this field, invoke its method to replace value into field's String. Example for tags is <Credit card>, <Customer Type>
		//Payload attribute located on Message's class itself.
		String payloadAttrNm = "";
		String payloadAttrNmStriped = "";
		Method payloadMttNm;
		String strFromPayload = "";
		try {
			for(String payloadTag: payloadTags) {
				payloadAttrNm = stripTagToCamelCase(payloadTag);
				payloadAttrNmStriped = stripTag(payloadTag);
				
				logger.debug("payloadAttrNm = "+payloadAttrNm);
				payloadMttNm = genGetterMethod(payloadAttrNm, this);
				if(payloadMttNm!=null) {
					strFromPayload = (String) payloadMttNm.invoke(this); 
					logger.debug("strFromPayload = "+strFromPayload);
					
					//Now we got value from payload, For example <Credit card> may transform into "KWave" , <Customer Type> may transform into "Individual", 
					//So, we'll replace all of its tag with value from payload
					//Since user not yet specify encode of < and > , we wll replace 3 of them (original form, unicode form, and escape sequence form)
					
					//Original
					logger.debug("Replacing tag = "+MessageEnum.oriLessThan+payloadAttrNmStriped+MessageEnum.oriMoreThan+" with value = "+strFromPayload);
					tranformingStr = tranformingStr.replaceAll(MessageEnum.oriLessThan+payloadAttrNmStriped+MessageEnum.oriMoreThan, strFromPayload);
					
					//Unicode
					logger.debug("Replacing tag = "+MessageEnum.ucLessThan+payloadAttrNmStriped+MessageEnum.ucMoreThan+" with value = "+strFromPayload);
					tranformingStr = tranformingStr.replaceAll(MessageEnum.oriLessThan+payloadAttrNmStriped+MessageEnum.oriMoreThan, strFromPayload);
					
					//Escape sequence
					logger.debug("Replacing tag = "+MessageEnum.escLessThan+payloadAttrNmStriped+MessageEnum.escMoreThan+" with value = "+strFromPayload);
					tranformingStr = tranformingStr.replaceAll(MessageEnum.oriLessThan+payloadAttrNmStriped+MessageEnum.oriMoreThan, strFromPayload);
				}
			}
		} catch (NullPointerException e) {
			logger.error("Value required by tag "+MessageEnum.oriLessThan+payloadAttrNmStriped+MessageEnum.oriMoreThan+" is null, please set this value in message's object." );
			throw e;
		}
		
		return tranformingStr;
	}

	/***
	 * To get payload tag from String field. <br/>
	 * For example: <br/>
	 * "This is message send to &lt;Customer name&gt; , your request have been reject due to &lt;Reject reason&gt;".<br/>
	 * Called this method and get Set of String contains the following string ( "&lt;Customer name&gt;", "&lt;Reject reason&gt;" ).
	 * @param strFromField is string that may contains tag.
	 * @return a set of string contains unique tag retrieved from inputed string. For example ( "&lt;Customer name&gt;", "&lt;Reject reason&gt;" ).
	 */
	public static Set<String> getPayloadTags(String strFromField) {
		Set<String> retPayloadTags = new HashSet<>(10);
		Set<String> payloadOri = getPayloadTagsByPattern(strFromField, patternOri);
		if(payloadOri!=null&&!payloadOri.isEmpty()) {
			retPayloadTags.addAll(payloadOri);
		}
		
		Set<String> payloadEsc = getPayloadTagsByPattern(strFromField, patternEsc);
		if(payloadEsc!=null&&!payloadEsc.isEmpty()) {
			retPayloadTags.addAll(payloadEsc);
		}
		
		Set<String> payloadUc = getPayloadTagsByPattern(strFromField, patternUc);
		if(payloadUc!=null&&!payloadUc.isEmpty()) {
			retPayloadTags.addAll(payloadUc);
		}
		
		return retPayloadTags;
	}

	/***
	 * To get payload tag from String field by pattern.<br/>
	 * Since tag character undefended. tag character could be one of original-string, escape sequence or unicode.
	 * @param str is string that may contains tag.
	 * @param patternStr is pattern that want to find and classify as payload. 
	 * @return a set of string contains unique tag retrieved from inputed string. For example ( "<Customer name>", "<Reject reason>" )
	 */
	public static Set<String> getPayloadTagsByPattern(String str, String patternStr) {
		Set<String> retPayloadTags = new HashSet<>();
		
	    Matcher matcher = Pattern.compile(patternStr).matcher(str);
	    boolean b = false;
	    while(b = matcher.find()) {
	    	retPayloadTags.add(matcher.group());
	    }
		return retPayloadTags;
	}


	/**
	 * To apply all available payloads into Json field.
	 * @param field is a field of object that want to apply payload.
	 * @param  obj is an object that wan't to apply payload.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private void jsonFilApplyPayload(Field field,  Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		JsonObject jsonObj = genGetterMethodAndGetJsonObj(field, obj);
		if(null == jsonObj){
			jsonObj = new JsonObject();
		}
//		if(jsonObj!=null) {
			String jsonString = jsonObj.toString();
			logger.debug("jsonString = "+jsonString);
//			if(StringUtils.isEmpty(jsonString)){
//				
//			}
			
			//Transform it
			String transformedStr = transformStringByPayload(jsonString);
			logger.debug("transformedStr = "+transformedStr);
			
			//Apply edited string back to field.
			JsonParser parser = new JsonParser();
			JsonObject transformedJsonObj = parser.parse(transformedStr).getAsJsonObject();
			genSetterMethodAndSetJson(field, obj, transformedJsonObj);
//		}
	}


	/**
	 * To check whether inputed field's value contains   <...> or \u003c...\u003e or not.
	 * @param field of object that want to check.
	 * @param obj is an object that want to check. 
	 * @return boolean represent that current field contains payload or not.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private boolean jsonFilContainsPayload(Field field, Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method =  genGetterMethod(field, obj); 
        JsonObject reflexJsonObj = (JsonObject) method.invoke(obj); 
        String reflexStr = null;
        if(reflexJsonObj!=null) {
        	reflexStr = reflexJsonObj.toString();
            logger.debug("reflexStr = "+reflexStr);
        }
		return containsPayload(reflexStr);
	}
	
	
	/**
	 * To check whether inputed field's value contains   <...> or \u003c...\u003e or not.
	 * @param str is string that want to check.
	 * @return boolean represent that current field contains payload or not.
	 */
	private boolean containsPayload(String str) {
		if(str==null) {
			return false;
		}else {
			return Pattern.compile(patternOri).matcher(str).find()
					|| Pattern.compile(patternEsc).matcher(str).find()
					|| Pattern.compile(patternUc).matcher(str).find();
		}
	}


	/**
	 * To separate inputed string with white-space then turn into camel-case.<br/>
	 * For example: <br/>
	 * input is "This is A Book" will transform into thisIsABook.
	 * @param input is String that want to transform into camel-case
	 * @return transformed camel-case string.
	 **/
	public static String toCamelCase(String input) {
        String[] splited = input.split(MessageEnum.whiteSpace);
        StringBuilder bd = new StringBuilder();
        int i=0;
        for (String str : splited) {
        	if(i==0) {
        		//To lower case
        		bd.append(str.toLowerCase());
        	}else {
        		//First character to upper case, other-character into lower case.
        		bd.append(str.substring(0, 1).toUpperCase());
                bd.append(str.substring(1, str.length()).toLowerCase());
        	}
            i++;
        }
        return bd.toString();
    }
	
	/**
	 * to transform first character of inputed string into upper-case.
	 * @param input is string that will transform  first character of inputed string into upper-case.
	 * @return transformed string which first character is upper-case.
	 **/
	public static String firstCharToUpperCase(String input) {
        StringBuilder bd = new StringBuilder();
		bd.append(input.substring(0, 1).toUpperCase());
        bd.append(input.substring(1, input.length()));
        return bd.toString();
    }
	
	/**
	 * To strip all tag in inputed string then transform into camelCase. A tag &lt; and tag &gt; could be original string, escape sequence and unicode.
	 * @param input is string that want to trasnform.
	 * @return tag-striped-camel-case string of inputed value.
	 **/
	public static String stripTagToCamelCase(String input) {
        String strippedStr = stripTag(input);
        
        strippedStr = strippedStr.trim();
        return toCamelCase(strippedStr);
    }
	
	/**
	 * To strip all tag in inputed string. A tag &lt; and tag &gt; could be original string, escape sequence and unicode.
	 * @param input is string that want to trasnform.
	 * @return tag-striped string of inputed value.
	 **/
	private static String stripTag(String input) {
		String strippedStr = input.replaceAll(MessageEnum.oriLessThan, "");
        strippedStr = strippedStr.replaceAll(MessageEnum.oriMoreThan, "");
        strippedStr = strippedStr.replaceAll(MessageEnum.escLessThan, "");
        strippedStr = strippedStr.replaceAll(MessageEnum.escMoreThan, "");
        strippedStr = strippedStr.replaceAll(MessageEnum.ucLessThan, "");
        strippedStr = strippedStr.replaceAll(MessageEnum.ucMoreThan, "");
		return strippedStr;
	}


	/**
	 * To check whether inputed field's value contains   <...> or \u003c...\u003e or not.
	 * @param field of object that want to check.
	 * @param obj is an object that want to check. 
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private boolean strFilContainsPayload(Field field, Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String reflexStr = genGetterMethodAndGetStr(field, obj);
		return containsPayload(reflexStr);
	}


	/**
	 * To generate getter method of field on inputed object.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private Method genGetterMethod(Field field, Object obj) throws NoSuchMethodException, SecurityException {
		return genGetterMethod(field.getName(), obj);
	}
	
	/**
	 * To generate getter method of field on inputed object and invoke it to get string result.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @return string result of generated getter method of field on inputed object
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private String genGetterMethodAndGetStr(Field field, Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String reflexStr = (String) genGetterMethod(field, obj).invoke(obj); 
        logger.debug("reflexStr = "+reflexStr);
        return reflexStr;
	}
	

	/**
	 * To generate setter method of field on inputed object and set invoke that method to set inputed string into it.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @param settingString is inputed string that want to set on generated method.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private void genSetterMethodAndSetStr(Field field, Object obj, String settingString) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String valBefSet = (String) genGetterMethod(field, obj).invoke(obj); 
		logger.debug("Value before set = "+valBefSet);
		genSetterMethodStr(field, obj).invoke(obj,settingString); 
        String valAftSet = (String) genGetterMethod(field, obj).invoke(obj); 
        logger.debug("Value after set = "+valAftSet);
        return ;
	}

	/**
	 * To generate getter method of field on inputed object and invoke it to get json-object result.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @return json-object of generated getter method of field on inputed object
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private JsonObject genGetterMethodAndGetJsonObj(Field field, Object obj) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        JsonObject jsonObj = (JsonObject) genGetterMethod(field, obj).invoke(obj); 
        logger.debug("jsonObj = "+jsonObj);
        return jsonObj;
	}
	
	/**
	 * To generate setter method of field on inputed object and set invoke that method to set inputed json-object into it.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @param jsonObject is inputed json-object that want to set on generated method.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 * @throws InvocationTargetException is exception from reflection.
	 * @throws IllegalArgumentException is exception from reflection.
	 * @throws IllegalAccessException is exception from reflection.
	 * @throws InstantiationException is exception from reflection.
	 **/
	private void genSetterMethodAndSetJson(Field field, Object obj, JsonObject jsonObject) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//String jsonStrBefSet = genGetterMethodAndGetJsonObj(field, obj).toString();
		//logger.debug("Value before set = "+jsonStrBefSet);
		genSetterMethodJsonObj(field, obj).invoke(obj,jsonObject); 
		//String jsonStrAftSet = genGetterMethodAndGetJsonObj(field, obj).toString();
        //logger.debug("Value after set = "+jsonStrAftSet);
        return ;
	}
	
	
	/**
	 * To generate getter method of field on inputed object.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @return method object of field on inputed object.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private Method genSetterMethodStr(Field field, Object obj) throws NoSuchMethodException, SecurityException {
		String metNm = "set"+firstCharToUpperCase(field.getName());
		logger.debug("expectMethodName = "+metNm);
		return obj.getClass().getMethod(metNm,new Class[]{String.class});
	}
	
	/**
	 * To generate getter method of field on inputed object.
	 * @param field of object that want to generate.
	 * @param obj is an object that want to generate. 
	 * @return method object of field on inputed object.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private Method genSetterMethodJsonObj(Field field, Object obj) throws NoSuchMethodException, SecurityException {
		String metNm = "set"+firstCharToUpperCase(field.getName());
		logger.debug("expectMethodName = "+metNm);
		return obj.getClass().getMethod(metNm,new Class[]{JsonObject.class});
	}

	/**
	 * @param fieldName is field name of string that wan't to generate method object.
	 * @param obj is object that will invoke generated method.
	 * @param type is type of method could be only 'get' or 'set'.
	 * @throws SecurityException is exception from reflection.
	 * @throws NoSuchMethodException is exception from reflection.
	 **/
	private Method genGetterMethod(String fieldName, Object obj) throws NoSuchMethodException, SecurityException {
		String metNm = "get"+firstCharToUpperCase(fieldName);
		logger.debug("expectMethodName = "+metNm);
		return obj.getClass().getMethod(metNm);
	}

	
	public MessageData getData() {
		return data;
	}

	public void setData(MessageData data) {
		this.data = data;
	}

	public MessageHeader getHeader() {
		return header;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		Message.logger = logger;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public static String getPatternori() {
		return patternOri;
	}

	public static String getPatternesc() {
		return patternEsc;
	}

	public static String getPatternuc() {
		return patternUc;
	}
	
	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}
	
	public String getKbankTelNo() {
		return kbankTelNo;
	}

	public void setKbankTelNo(String kbankTelNo) {
		this.kbankTelNo = kbankTelNo;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getAccountLast4Digit() {
		return accountLast4Digit;
	}

	public void setAccountLast4Digit(String accountLast4Digit) {
		this.accountLast4Digit = accountLast4Digit;
	}
	
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getInstallmentAmt() {
		return installmentAmt;
	}

	public void setInstallmentAmt(String installmentAmt) {
		this.installmentAmt = installmentAmt;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getProductName2lang() {
		return productName2lang;
	}

	public void setProductName2lang(String productName2lang) {
		this.productName2lang = productName2lang;
	}

	public String getFullCardNo() {
		return fullCardNo;
	}

	public void setFullCardNo(String fullCardNo) {
		this.fullCardNo = fullCardNo;
	}
	
}
