/**
 * Create Date Mar 21, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.owasp.esapi.ESAPI;
import com.eaf.cache.MandatoryFieldCache;
import com.eaf.cache.ORIGCache;
import com.eaf.orig.cache.CacheDataInf;
import com.eaf.orig.cache.properties.MainProductProperties;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.cache.properties.UserNameProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.ulo.pl.eai.util.EAITool;
/**
 * @author Sankom
 */
public class HTMLRenderUtil {
		
	static Logger logger = Logger.getLogger(HTMLRenderUtil.class);
	
	public static final String DISPLAY_MODE_VIEW = "VIEW";
	public static final String DISPLAY_MODE_EDIT = "EDIT";
	public static final String DISPLAY_MODE_HIDEN = "HIDEN";
	public static final String COMPARE_CHECKBOX_VALUE = "Y";
	public static final String DISABLED = "disabled";
	public static String READ_ONLY = "readOnly";
	
	public class RadioBoxCompare{
		public static final String NotReceiveDoc = "N";
		public static final String ReceiveDoc = "R";
		public static final String OverrideDoc = "O";
		public static final String TrackDoc = "T";
		public static final String NotCheck = "N";
		public static final String True = "T";
		public static final String False = "F";
	}
	public class CssClass{
		public static final String CLASS_SENSATIVE = "sensitive";
	}
	public static String doubleQuote = "\"";		
	
	public static String displayHTML(Object obj) {
		if (obj == null || ((String) obj).trim().equals("null")||((String) obj).trim().length() == 0) {
			return "";
		} else {
			return ESAPI.encoder().encodeForHTML(obj.toString().trim());
		}
	}
	
	public static String replaceNull(Object obj) {
		if (obj == null || ((String) obj).trim().equals("null")||((String) obj).trim().length() == 0) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	
	public static String displayHTMLCacheDesc(String str ,String cacheName){		
		if(OrigUtil.isEmptyString(str)){
			return "";
		}		
		ORIGCacheUtil catchUtil = new ORIGCacheUtil();		
		return displayHTML(catchUtil.getORIGMasterDisplayNameDataM(cacheName,str));
	}
	
	public static String displayHTMLFieldIDDesc(String str ,int fieldID){		
		if(OrigUtil.isEmptyString(str)){
			return "";
		}		
		ORIGCacheUtil catchUtil  = new ORIGCacheUtil();		
		return displayHTML(catchUtil.getNaosCacheDisplayNameDataM(fieldID,str));
	}
	
	
	public static String replaceQuote (String inputString){
		 return replaceSingleQuote(inputString);		
	}
		  
	public static String replaceSingleQuote(String inputString) {
	  	String patternString = "'";
	  	String replaceString = "\\\'";
	  		if(inputString == null){
	  			return inputString;
	  		}			
		    StringBuilder result = new StringBuilder();			    
		    int startIdx = 0;
		    int idxOld = 0;			
		    try{			
		        while ((idxOld = inputString.indexOf(patternString, startIdx)) >= 0) {
		            result.append(inputString.substring(startIdx, idxOld));
		            result.append(replaceString);
		            startIdx = idxOld + patternString.length();
		        }			
		        result.append(inputString.substring(startIdx));
		    } catch (Exception e) {
		    }			
		    return result.toString();
	}    

	public static String replaceDoubleQuote(String inputString) {
		  	String patternString = "\"";
		  	String replaceString = "\\\"";			
	  		if(OrigUtil.isEmptyString(inputString)){
	  			return inputString;
	  		}		  		
		    StringBuilder result = new StringBuilder();		    
		    int startIdx = 0;
		    int idxOld = 0;		
		    try{		
		        while ((idxOld = inputString.indexOf(patternString, startIdx)) >= 0) {
		            result.append(inputString.substring(startIdx, idxOld));
		            result.append(replaceString);
		            startIdx = idxOld + patternString.length();
		        }		
		        result.append(inputString.substring(startIdx));
		    } catch (Exception e) {
		    }		
		    return result.toString();
	}
	
	public static String displayRadioTagScriptAction(String value,String mode,String inputFieldName,String compareValue, 
										String equalStr,String notEqualStr,String script) {
		StringBuilder str = new StringBuilder();
		if (OrigUtil.isEmptyString(value)){
			value = "";
		}
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
			return "";
		}
		str.append("<input ");
		str.append(" type=").append(doubleQuote).append("radio").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		if(DISPLAY_MODE_EDIT.equalsIgnoreCase(mode))
			str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);		
		str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
		str.append(" ").append(script);		
		if (value.equals(compareValue)) {
			str.append(" checked ");
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append(" disabled ");
		}
		str.append("/>");		
		return str.toString();
	}
	
	public static String DisplayRadio(String value,String mode,String inputFieldName,String compareValue ,String script) {
		StringBuilder str = new StringBuilder();
		if (OrigUtil.isEmptyString(value)){
			value = "";
		}
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
			return "";
		}
		str.append("<input ");
		str.append(" type=").append(doubleQuote).append("radio").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		if(DISPLAY_MODE_EDIT.equalsIgnoreCase(mode))
			str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);		
		str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
		str.append(" ").append(script);		
		if (value.equals(compareValue)) {
			str.append(" checked ");
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append(" disabled ");
		}
		str.append("/>");		
		return str.toString();
	}	
	
	public static String DisplayRadioMandatory(ORIGCacheDataM listBoxM,String mode,String inputFieldName,String compareValue ,String script) {
		StringBuilder str = new StringBuilder();
		String value = listBoxM.getCode();
		if (OrigUtil.isEmptyString(value)) value = "";		
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) return "";		
		str.append("<input ");
		str.append(" type=").append(doubleQuote).append("radio").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		if(DISPLAY_MODE_EDIT.equalsIgnoreCase(mode))
			str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);		
		str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
		str.append(" mondatory=").append(doubleQuote).append(listBoxM.getSystemID3()).append(doubleQuote);
		str.append(" reason=").append(doubleQuote).append(listBoxM.getSystemID6()).append(doubleQuote);
		str.append(" fieldID=").append(doubleQuote).append(listBoxM.getFileID()).append(doubleQuote);
		str.append(" thDesc=").append(doubleQuote).append(listBoxM.getThDesc()).append(doubleQuote);
		str.append(" ").append(script);		
		if (value.equals(compareValue)){
			str.append(" checked ");
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append(" disabled ");
		}
		str.append("/>");		
		str.append("");
		str.append(listBoxM.getThDesc());				
		return str.toString();
	}
	
	
	
	public static String displayRadioTagScriptAction(String value,String mode,String inputFieldName,String compareValue,String script){			
			if (OrigUtil.isEmptyString(value)) {
				value = "";
			}			
			if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
				return "";
			}
			StringBuilder str = new StringBuilder();
			str.append("<input ");
			str.append(" type=").append(doubleQuote).append("radio").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			if(DISPLAY_MODE_EDIT.equalsIgnoreCase(mode))
				str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);		
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(compareValue)).append(doubleQuote);
			str.append(" ").append(script);		
			if (value.equals(compareValue)) {
				str.append(" checked ");
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append(" disabled ");
			}
			str.append("/>");		
		return str.toString();		 
	}
	
	public static String displayRadioDoc(String value,String mode,String inputFieldName,String compareValue,String script){			
		if (OrigUtil.isEmptyString(value)) {
			value = "";
		}			
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
			return "";
		}
		StringBuilder str = new StringBuilder();
		str.append("<input ");
		str.append(" type=").append(doubleQuote).append("radio").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		if(DISPLAY_MODE_EDIT.equalsIgnoreCase(mode))
			str.append(" class=").append(doubleQuote).append("radio-doc "+CssClass.CLASS_SENSATIVE).append(doubleQuote);		
		str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
		str.append(" value=").append(doubleQuote).append(displayHTML(compareValue)).append(doubleQuote);
		str.append(" ").append(script);		
		if(value.equals(compareValue)){
			str.append(" checked ");
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append(" disabled ");
		}
		str.append("/>");		
		return str.toString();		 
	}	
	
	public static String displaySelectTagScriptActionAndCode_ORIG(Vector v,String selectedValue,String listName,String mode,String scriptAction) {
			
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}
			StringBuilder str = new StringBuilder();				
			CacheDataInf obj = null;
			String value = null;
			String name = null;
			
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
						}else{
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
							str.append(value).append(" - ").append(name).append("</option>");
						}
					}
				}
				str.append("</select>");
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
				boolean isFind = false;
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox-view").append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				if (v != null){
					for(int i = 0; i < v.size(); i++){
						obj = (CacheDataInf) v.get(i); 
						value = obj.getCode(); 
						name = obj.getThDesc();		
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
					}
					if(!isFind){
						str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					}
				}else{
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
				str.append("</select>");
				return str.toString();
			}
		return "";
	}
	
	public static String displaySelectTagScriptAction_ORIG(int fileID,String busClassID,String selectedValue,String listName,String mode,String scriptAction){
		  
			if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
				return "";
			}	
			StringBuilder str = new StringBuilder();
			CacheDataInf obj = null;
			String value = null;
			String name = null;		
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
				ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
				Vector v = (Vector)origc.getNaosCacheDataMs(busClassID, fileID);
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
						}else{
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
							str.append(name).append("</option>");
						}
					}
				}
				str.append("</select>");
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
				ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
				Vector v = (Vector)origc.getNaosCacheDataMs(busClassID, fileID,null);
				boolean isFind = false;
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
				str.append(" ").append(" >");
				if (v != null){
					for(int i = 0; i < v.size(); i++){
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
					}
					if(!isFind){
						str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					}
				}else{
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
				str.append("</select>");
				return str.toString();
			}
			
		return "";
	}
	public static String displaySelectTagScriptActionORIG(String cacheName,String selectedValue,String listName,String mode,String scriptAction){
		  
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
			return "";
		}	
		StringBuilder str = new StringBuilder();
		CacheDataInf obj = null;
		String value = null;
		String name = null;		
		if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
			ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
			Vector v = (Vector)origc.loadCacheByActive(cacheName);
			str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" ").append(scriptAction).append(" >");
			str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					obj = (CacheDataInf) v.get(i);
					value = obj.getCode();
					name = obj.getThDesc();
					if ((value != null && value.equals(selectedValue))) {
						str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
						str.append(" ").append("selected>").append(name).append("</option>");
					}else{
						str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
						str.append(name).append("</option>");
					}
				}
			}
			str.append("</select>");
			return str.toString();
		}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
			ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
			Vector v = (Vector)origc.loadCacheByName(cacheName);
			boolean isFind = false;
			str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
			str.append(" ").append(" >");
			if (v != null){
				for(int i = 0; i < v.size(); i++){
					obj = (CacheDataInf) v.get(i);
					value = obj.getCode();
					name = obj.getThDesc();
					if(value != null && value.equals(selectedValue)){
						str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
						str.append(" ").append("selected>").append(name).append("</option>");
						isFind =true;
						break;
					}
				}
				if(!isFind){
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
			}else{
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
			}
			str.append("</select>");
			return str.toString();
		}		
		return "";
	}
	public static String displaySelectTagScriptAction_ORIG(Vector v,String selectedValue,String listName,String mode,String scriptAction){
		  
			if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) {
				return "";
			}	
			StringBuilder str = new StringBuilder();
			CacheDataInf obj = null;
			String value = null;
			String name = null;		
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
						}else{
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
							str.append(name).append("</option>");
						}
					}
				}
				str.append("</select>");
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
				boolean isFind = false;
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
				str.append(" ").append(" >");
				if (v != null){
					for(int i = 0; i < v.size(); i++){
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
					}
					if(!isFind){
						str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					}
				}else{
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
				str.append("</select>");
				return str.toString();
			}
			
		return "";
	}
	  
	public static String displayInputTagScriptAction( String value,String mode,String inputSize,String inputFieldName,
																		String inputStyle,String scriptAction,String maxLength){		 
			if (value == null) value = "";
			StringBuilder str = new StringBuilder();				
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);				
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" textboxReadOnly").append(doubleQuote);				
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				return str.toString();
			} 
		return "";
	}
	public static String DisplayButton(String attrName ,String value,String displayMode,String buttonStyle){
		StringBuilder str = new StringBuilder();
//		logger.debug("DisplayButton ===="+attrName+" === "+displayMode);
		if (displayMode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)) {
			str.append(" <input ").append("type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(attrName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(attrName).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ");
			str.append(" disabled>");		
		}else if (displayMode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)) {
			str.append(" <input ").append("type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(attrName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(attrName).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" >");
		}
		return str.toString();
	}
	public static String DisplayInputXrules(String value,String mode,String inputFieldName,String inputStyle,String maxLength){		 
		if (value == null) value = "";
		StringBuilder str = new StringBuilder();				
		if (mode != null && HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(mode)) {
			str.append("<input ");
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);				
			str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);				
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" readOnly />");
			return str.toString();
		}else if (mode != null && HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(mode)) {
			str.append("<input ");
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			return str.toString();
		} 
		return "";
	}
	public static String DisplayIconResult(String attrName){
		StringBuilder str = new StringBuilder();			
		str.append("<img ");
		str.append(" class=").append(doubleQuote).append("imgResult").append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(attrName).append(doubleQuote);
		str.append(" src=").append(doubleQuote).append("images/data2.png").append(doubleQuote);
		str.append(" align=").append(doubleQuote).append("bottom").append(doubleQuote);
		str.append("/>");
		return str.toString();
	}
	
	public static String DisplayInputButtonReset(String value,String mode,String fieldName,String buttonName,String inputStyle,String maxLength){		 
		if (value == null) value = "";
			StringBuilder str = new StringBuilder();				
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input");
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(fieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(fieldName).append(doubleQuote);				
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);				
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");
				str.append("&nbsp;");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<input");
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(fieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(fieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<img align=\"bottom\" src=\"images/reset.png\" onmouseover=\"style.cursor='pointer'\" ");
				str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
				str.append(" />");
				return str.toString();
			} 
		return "";
	} 
	
	public static String displayInputTagScriptActionWithResetButton( String value,String mode,String inputSize,String inputFieldName,
															String inputStyle,String scriptAction,String maxLength,String buttonJs){		 
			if (value == null) value = "";
			StringBuilder str = new StringBuilder();				
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);				
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);				
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");
				str.append("&nbsp;");
				str.append("<img align=\"bottom\" src=\"images/reset.png\"  onmouseover=\"style.cursor='pointer'\" ");
				str.append(buttonJs);
				str.append(" />");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<img align=\"bottom\" src=\"images/reset.png\"  onmouseover=\"style.cursor='pointer'\" ");
				str.append(buttonJs);
				str.append(" />");
				return str.toString();
			} 
		return "";
	}  
	
	
	public static String displayCheckBox(String value,String name,String compare,String jScript){
		StringBuilder str = new StringBuilder();
		str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
		if (compare.equals(value)) {
			str.append(" checked");
		}
		str.append(" ").append(jScript).append(" />");
		return str.toString();
	}
	
	public static String displayCheckBox(String value,String name,String compare,String displayMode,String jScript){		
		StringBuilder str = new StringBuilder();
		if (!OrigUtil.isEmptyString(displayMode) && DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(compare)).append(doubleQuote);
			if ((!OrigUtil.isEmptyString(value)&&  !OrigUtil.isEmptyString(compare) && compare.equals(value.trim()) )){
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" disabled >");			
			return str.toString();				
		}else if (!OrigUtil.isEmptyString(displayMode) && DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)){				
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(name).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(compare)).append(doubleQuote);
			if ((!OrigUtil.isEmptyString(value)&&  !OrigUtil.isEmptyString(compare) && compare.equals(value.trim()) )){
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" ").append(jScript).append(" />");			
			return str.toString();
		}		
		return "";
	}
	
	public static String displayCheckBoxValues(String value,String name,String compare,String jScript) {
		StringBuilder str = new StringBuilder();
		str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
		if (compare.equals(value)) {
			str.append(" checked");
		}	
		str.append(" ").append(jScript).append(" />");
		return str.toString();
	}
	public static String displayCheckBoxValues(String value,String name,String compare,String className,String jScript) {
		StringBuilder str = new StringBuilder();
		str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
		str.append(" name=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" id=").append(doubleQuote).append(name).append(doubleQuote);
		str.append(" class=").append(doubleQuote).append(className).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
		if (compare.equals(value)) {
			str.append(" checked");
		}
		str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
		str.append(" ").append(jScript).append(" />");
		return str.toString();
	}
/**
 * @deprecated 
 * {@link #DisplayInputCurrency(BigDecimal, String, String, String, String, String, String)}
 * #SeptemWi
 * */
	@Deprecated
	public static String displayInputCurrencyTagScriptAction(BigDecimal value, String mode, String inputSize 
												,String inputFieldName, String inputStyle, String scriptAction,	String maxLength) {		
			if(value == null){
				value = new BigDecimal("0.00");
			}
			StringBuilder str = new StringBuilder();
			
		try{
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumber(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");							
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){					
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumber(value)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");							
				return str.toString();
			}				
		}catch (Exception e) {
			return "";
		}
		return "";
	}	
	public static String DisplayInputNumber(BigDecimal value, String mode, String format ,String minValue ,String maxValue
												,String inputName, String className, String scriptAction,String maxLength ,boolean nullEmpty) {		
			StringBuilder str = new StringBuilder();		
			if(minValue == null) minValue = "";
			if(maxValue == null) maxValue = "";
			try{
				if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
					str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
					str.append(" minvalue=").append(doubleQuote).append(minValue).append(doubleQuote);
					str.append(" maxvalue=").append(doubleQuote).append(maxValue).append(doubleQuote);
					str.append(" nullempty=").append(doubleQuote).append((nullEmpty)?"Y":"N").append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("textbox-number-view").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(DataFormatUtility.DisplayNumber(value,format,nullEmpty)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" readOnly />");							
					return str.toString();
				}else if(mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){					
					str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
					str.append(" minvalue=").append(doubleQuote).append(minValue).append(doubleQuote);
					str.append(" maxvalue=").append(doubleQuote).append(maxValue).append(doubleQuote);
					str.append(" nullempty=").append(doubleQuote).append((nullEmpty)?"Y":"N").append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(className).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(DataFormatUtility.DisplayNumber(value,format,nullEmpty)).append(doubleQuote);
					str.append(" ").append(scriptAction);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" />");							
					return str.toString();
				}				
			}catch (Exception e) {
				return "";
			}
		return "";
	}	
	public static String DisplayInputCurrency(BigDecimal value, String mode, String format 
													,String inputName, String className, String scriptAction,String maxLength ,boolean nullEmpty) {		
		if(value == null && !nullEmpty){
			value = new BigDecimal("0.00");
		}
		StringBuilder str = new StringBuilder();		
		try{
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
				str.append(" nullempty=").append(doubleQuote).append((nullEmpty)?"Y":"N").append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(className).append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumber(value,nullEmpty)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");							
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){					
				str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
				str.append(" nullempty=").append(doubleQuote).append((nullEmpty)?"Y":"N").append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(className).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumber(value,nullEmpty)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");							
				return str.toString();
			}				
		}catch (Exception e) {
			return "";
		}
		return "";
	}
	
	public static String DisplayInputCurrencyEmptyNull(BigDecimal value, String mode, String format, String inputName, String className, String scriptAction,
			String maxLength, boolean nullEmpty) {
		if (value == null && !nullEmpty) {
			value = new BigDecimal("0.00");
		}
		StringBuilder str = new StringBuilder();
		try {
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
				str.append(" nullempty=").append(doubleQuote).append((nullEmpty) ? "Y" : "N").append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(className).append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumberEmptyNull(value, nullEmpty)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly />");
				return str.toString();
			} else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append(" <input format=").append(doubleQuote).append(format).append(doubleQuote);
				str.append(" nullempty=").append(doubleQuote).append((nullEmpty) ? "Y" : "N").append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(className).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(DataFormatUtility.displayCommaNumberEmptyNull(value, nullEmpty)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				return str.toString();
			}
		} catch (Exception e) {
			return "";
		}
		return "";
	}
	
	/**
	 * @deprecated 
	 * {@link #replaceNull(String) for remove null String}
	 * {@link #displayHTML(String) to encode String for display on web page}
	 * #Wichaya
	 * */
	@Deprecated
	public static String displayText(String text){
		  	if(OrigUtil.isEmptyString(text)) return "";
			if(!OrigUtil.isEmptyString(text)){
				StringBuilder buf = new StringBuilder(text);
				for (int i = buf.length() - 1; i >=0; i--) {
					char c = buf.charAt(i);
					Character cW = new Character(c);
					Character enterStr = new Character((char)10);
					Character enterStr2 = new Character((char)13);
					Character doubleQuote = new Character((char)34);
					Character singleQuote = new Character((char)39);
					if (cW.equals(enterStr)) {
						buf.replace(i, i + 1, "");
					}else if (cW.equals(enterStr2)) {
						buf.replace(i, i + 1, "<br>");
					}else if (cW.equals(singleQuote)) {
						buf.replace(i, i + 1, "\\"+"\'");
					}else if (cW.equals(doubleQuote)) {
						buf.replace(i, i + 1, "&quot;");
				    }					
				}
				return buf + "";
			}
			return "";
	}
	
	public static String displayPopUpTagScriptAction(String value,String mode,String inputSize,
									String inputFieldName,String inputStyle,String scriptAction,
										String maxLength,String popupLabel,	String popupStyle,
											String popupAction,	String cacheName) {
		StringBuilder str = new StringBuilder();
		if (value == null) {
			value = "";
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id= ").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append("textboxReadOnly").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			/**str.append(" ").append(scriptAction); Not Action Script For Mode View #SeptemWi*/
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" readOnly />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append("onClick=").append(doubleQuote);
			str.append("javascript:popupscript('"+popupAction+"','"+inputFieldName+"','"+cacheName+"')").append(doubleQuote);
			str.append(" disabled ").append(" />");
			return str.toString();
		}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ").append(scriptAction);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append("onClick=").append(doubleQuote);
			str.append("javascript:popupscript('"+popupAction+"','"+inputFieldName+"','"+cacheName+"')").append(doubleQuote);
			str.append(" />");
			return str.toString();
		}
		return "";
	 }
	
	 public static String getMandatory_ORIG(String customerType, Vector userRoles, String formID, String fieldName){
			String result = "";
			boolean flag = false;
			flag = MandatoryFieldCache.isMandatory_ORIG(customerType,userRoles,formID,fieldName);
//			logger.debug("flag is "+flag);
			if(flag){
				result = "<font color=\"#ff0000\">*</font>";
			}
			return result;
	}	    		

	 public static String CreateParam(String[] paramFields ,String inputFieldName){
		String strParam = "";
			if(paramFields != null && paramFields.length > 0){
				boolean hasMainField = false;
				if(inputFieldName.equals(paramFields[0])){
					hasMainField = true;
				}
				strParam = strParam + "'" + paramFields[0] + "'";
				for(int i = 1; i < paramFields.length; i++){
					if(paramFields[i] != null){
						if(inputFieldName.equals(paramFields[i])){
							hasMainField = true;
						}
						strParam = strParam + ",'" + paramFields[i] + "'";
					}
				}
				if(!hasMainField){
					strParam = strParam + ",'" + inputFieldName + "'";
				}
			}else{
				strParam = "'" + inputFieldName + "'";
			}
		return strParam;
	 }
	 public static String displayPopUpTagScriptAction(String value,String mode,String inputSize,String inputFieldName,
									String inputStyle,String scriptAction,String maxLength,	String popupLabel,
										String popupStyle,String popupAction,String[] paramFields,String popupScriptAction) {
		StringBuilder str = new StringBuilder();
		if (value == null) {
			value = "";
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ").append(" disabled ");
			str.append(scriptAction);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append(" disabled ");
			str.append(" />");
			return str.toString();
		}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ").append(scriptAction);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append(" ");
			str.append("onClick=").append(doubleQuote);
			str.append("javascript:popupscriptFields('"+popupAction+"',new Array("+CreateParam(paramFields,inputFieldName)+"),'','','"+inputFieldName+"')");
			str.append(doubleQuote);
			str.append(" ").append(popupScriptAction);
			str.append(" />");
			return str.toString();
		} 
		return "";
	}
	public static String displayPopUpTagScriptAction(String value,String mode,String inputSize,String inputFieldName,
								String inputStyle,String scriptAction,String maxLength,String popupLabel,String popupStyle,
									String popupAction,String[] paramFields,String width,String height,	String top,String left,
										String popupScriptAction) {
		StringBuilder str = new StringBuilder();
		if (value == null) {
			value = "";
		}
		if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ").append(" disabled ");
			str.append(scriptAction);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append(" disabled ");
			str.append(" />");
			return str.toString();
		}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" ").append(scriptAction);
			str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
			str.append(" />");
			str.append("&nbsp;");
			str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
			str.append(" ");
			str.append("onClick=").append(doubleQuote);
			str.append("popupscriptFieldsLoc('"+popupAction+"',new Array("+CreateParam(paramFields,inputFieldName)+"),"+width+","+height+","+top+","+left+")");
			str.append(doubleQuote);
			str.append(" ").append(popupScriptAction);
			str.append(" />");
			return str.toString();
		} 
		return "";
	}
	
	public static String displaySelectScriptAutoSelectListBox(Vector v,	String selectedValue, String listName, String mode,	String scriptAction){			
		if (mode != null && DISPLAY_MODE_HIDEN.equalsIgnoreCase(mode)) return "";
			CacheDataInf obj = null;
			String value = null;
			String name = null;	
			StringBuilder str = new StringBuilder();
		if (mode != null && DISPLAY_MODE_EDIT.equals(mode)){					
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");		
				if(v != null){
					if(v.size()==1){
					/**Return Select Value if Vector.size=1 @author Pipe*/
						obj = (CacheDataInf) v.get(0);
						value = obj.getCode();
						name = obj.getThDesc();	
						str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
						str.append(" selected>").append(name).append("</option>");
						str.append("</select>");						
						return str.toString();
					}
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();	
								if ((value != null && value.equals(selectedValue))) {
									str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
									str.append(" selected>").append(name).append("</option>");
								} else {
									str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
									str.append(" >").append(name).append("</option>");
								}
							}
					}
			str.append("</select>");
			return str.toString();
		}else if (mode != null && DISPLAY_MODE_VIEW.equals(mode)){
			boolean isFind = false;
			str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
			str.append(" ").append(scriptAction).append(" >");
			if (v != null){
				for(int i = 0; i < v.size(); i++){
					obj = (CacheDataInf) v.get(i); value = obj.getCode(); name = obj.getThDesc();		
					if ((value != null && value.equals(selectedValue))) {
						str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
						str.append(" selected>").append(name).append("</option>");
						isFind =true;
						break;
					}
				}	
				if(!isFind){
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");	
				}
			}else{
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
			}
			str.append("</select>");
			return str.toString();
		}
		return "";
	}	
	public static String displaySelectBox(String busClass,int fieldID,String selectedValue,String listName,String mode,String scriptAction) {
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		}			
			ORIGCacheUtil origCache = new ORIGCacheUtil();			
			Vector v = (Vector)origCache.getNaosCacheDataMs(busClass,fieldID);				
			StringBuilder str = new StringBuilder();			
			CacheDataInf obj = null;
			String value = null;
			String name = null;	
			
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					if(v != null){
						for (int i = 0; i < v.size(); i++) {
							obj = (CacheDataInf) v.get(i);
							value = obj.getCode();
							name = obj.getThDesc();
							if ((value != null && value.equals(selectedValue))) {
								str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
								str.append(" ").append("selected>").append(name).append("</option>");
							}else{
								str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
								str.append(name).append("</option>");
							}
						}
					}
				str.append("</select>");
				return str.toString();
		}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
			boolean isFind = false;
			str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append("combobox-view").append(doubleQuote);
			str.append(" ").append(scriptAction).append(" >");
			if (v != null){
				for(int i = 0; i < v.size(); i++){
					obj = (CacheDataInf) v.get(i); 
					value = obj.getCode(); 
					name = obj.getThDesc();		
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
				}
				if(!isFind){
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
			}else{
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
			}
			str.append("</select>");
			return str.toString();
		}
		return "";
	}
	
	public static String displaySelectTagScriptActionBusClassFieldID(String busClass,int fieldID,String selectedValue,
												String listName,String mode,String scriptAction) {
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}			
			ORIGCacheUtil origCache = new ORIGCacheUtil();			
			StringBuilder str = new StringBuilder();			
			CacheDataInf obj = null;
			String value = null;
			String name = null;	
			
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){			
				Vector v = (Vector)origCache.getNaosCacheDataMs(busClass,fieldID);	
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
						}else{
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
							str.append(name).append("</option>");
						}
					}
				}
				str.append("</select>");
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){			
				Vector v = (Vector)origCache.getNaosCacheDataMs(busClass,fieldID,null);	
				boolean isFind = false;
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				if (v != null){
					for(int i = 0; i < v.size(); i++){
						obj = (CacheDataInf) v.get(i); 
						value = obj.getCode(); 
						name = obj.getThDesc();		
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
					}
					if(!isFind){
						str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					}
				}else{
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
				str.append("</select>");
				return str.toString();
			}
		return "";
	}
	
	
	public static String DisplayBlockCodeSelectTag(String busClass,int fieldID,String selectedValue,String listName,String mode,String scriptAction,String reasonBlockCode){
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}			
			ORIGCacheUtil origCache = new ORIGCacheUtil();			
			Vector v = (Vector)origCache.getNaosCacheDataMs(busClass,fieldID);				
			StringBuilder str = new StringBuilder();			
			CacheDataInf obj = null;
			String value = null;
			String name = null;	
			
			if(mode != null && DISPLAY_MODE_EDIT.equals(mode)){
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				
				str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();	
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
						}else{
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(">");
							str.append(name).append("</option>");
						}
					}
				}
				str.append("</select>");
				return str.toString();
			}else if(mode != null && DISPLAY_MODE_VIEW.equals(mode)){
				boolean isFind = false;
				str.append("<select name=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(listName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("combobox").append(" comboboxReadOnly").append(doubleQuote);
				str.append(" ").append(scriptAction).append(" >");
				if (v != null){
					for(int i = 0; i < v.size(); i++){
						obj = (CacheDataInf) v.get(i); 
						value = obj.getCode(); 
						name = obj.getThDesc();		
						if(value != null && value.equals(selectedValue)){
							str.append("<option value =").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
							str.append(" ").append("selected>").append(name).append("</option>");
							isFind =true;
							break;
						}
					}
					if(!isFind){
						str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
					}
				}else{
					str.append("<option value=").append(doubleQuote).append(doubleQuote).append(">").append("\u0E01\u0E23\u0E38\u0E13\u0E32\u0E40\u0E25\u0E37\u0E2D\u0E01").append("</option>");
				}
				str.append("</select>");
				return str.toString();
			}
		return "";
	}
	
	
	/**
	 * @author septemwi
	 * Function displayInputTagJsDate Used For Field Date
	 * */
	public static String displayInputTagJsDate2(String formName,String value,String mode,String inputSize
													,String inputFieldName,String optionalFuncClass,String popupLocation,String inputScript){
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){				
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar").append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(" ").append( " readOnly>");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){
				str.append("<table cellpadding=\"0\" cellspacing=\"0\" >");
				str.append("<tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=" ).append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar");
				str.append(" ").append(optionalFuncClass).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(inputScript).append(" >").append("</td>");
				str.append("<td valign=\"middle\">&nbsp;");
				str.append("<img class=\"date_trigger\" src=\"images/calendar.gif\" width=\"21px\" height=\"21px\"");
				str.append(" onclick=\"popUpCalendar(this,'"+inputFieldName+"','dd/mm/yyyy','','','','"+popupLocation+"')\">");
				str.append("</td></tr>");
				str.append("</table>");
				return str.toString();
			}		
		return "";
	}
	
	/**
	 * @author Wichaya
	 * Function displayInputTagJsDate Used For Field Date
	 * Specific Era 'BE' is Buddhist era, 'AD' is Christian era
	 * */
	public static String displayInputTagJsDate(String formName,String value,String mode,String inputSize
													,String inputFieldName,String optionalFuncClass,String popupLocation,String inputScript, String era){
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){				
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar").append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(" ").append( " readOnly>");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){
				str.append("<table cellpadding=\"0\" cellspacing=\"0\" >");
				str.append("<tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=" ).append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar");
				str.append(" ").append(optionalFuncClass).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(inputScript).append(" >").append("</td>");
				str.append("<td valign=\"middle\">&nbsp;");
				str.append("<img class=\"date_trigger\" src=\"images/calendar.gif\" width=\"21px\" height=\"21px\"");
				str.append(" onclick=\"popUpCalendar(this,'"+inputFieldName+"','dd/mm/yyyy','','','','"+popupLocation+"','"+era+"')\">");
				str.append("</td></tr>");
				str.append("</table>");
				return str.toString();
			}		
		return "";
	}
	
	/**Function Display Popup With Check PopupFlag #SeptemWi*/
	public static String displayInputTagScriptResultPopupAction(String value,String mode,String inputSize,String inputFieldName,
										String inputStyle,String scriptAction,String maxLength,	String resultAction,String popUpFlag) {
		StringBuilder str = new StringBuilder();			
			if (value == null) {
				value = "";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<table cellpadding=\"0\" cellspacing=\"0\"><tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=" ).append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" readOnly>");
				str.append("</td><td valign=\"bottom\">&nbsp;");
				if(OrigConstant.FLAG_Y.equals(popUpFlag))
					str.append("<img class=\"imgResult\""+resultAction+"src=\"images/data2.png\" align=\"bottom\" />");				
				str.append("</td></tr>").append("</table>");
				return str.toString();		
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<table cellpadding=\"0\" cellspacing=\"0\"><tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(scriptAction).append(" readOnly>");
				str.append("</td><td valign=\"bottom\">&nbsp;");
				if(OrigConstant.FLAG_Y.equals(popUpFlag))
					str.append("<img class=\"imgResult\""+resultAction+"src=\"images/data2.png\" align=\"bottom\" />");				
				str.append("</td></tr>").append("</table>");
				return str.toString();
			}
		return "";
	}
	public static String displayHiddenField(String value ,String inputFieldName){
		StringBuilder str = new StringBuilder();			
		if (value == null) value = "";
			str.append("<input type=").append(doubleQuote).append("hidden").append(doubleQuote);
			str.append("id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append("name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			str.append(" />");
		return str.toString();
	}
	
	public static String displayPopUpOneTextBoxTagScriptAction(String value,String mode,String inputSize,
												String inputFieldName1,	String inputStyle,String scriptAction,String maxLength,
													String popupLabel,String popupStyle,String popupAction,String inputFieldName2,
															String cacheName,String inputRelateField1,String inputRelateField2){
		StringBuilder str = new StringBuilder();
		if (value == null) {
				value = "";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" readOnly");
				str.append(" maxlength=").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName2).append("Popup").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName2).append("Popup").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
				str.append(" disabled ").append(" />");				
				return str.toString();				
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append("onblur=").append(doubleQuote);
				str.append("javascript:setTitleDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName2+ "Popup"+"','"+inputRelateField1+"','"+inputRelateField2+"')").append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName2).append("Popup").append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName2).append("Popup").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
				str.append(" onclick=").append(doubleQuote);
				str.append("javascript:popupscriptOneTextBox('" + popupAction + "','" + inputFieldName2 + "','','" + inputFieldName1 + "')").append(doubleQuote);
				str.append(" />");				
				return str.toString();
			}
			
		return "";
	}
	
	public static String displayCheckBoxTagDesc(String value,String checkedValue, String mode, String inputFieldName, String action, String text){		
		StringBuilder str = new StringBuilder();
		if(value == null) value = "";
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)){
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName+"_view").append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName+"_view").append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(checkedValue)).append(doubleQuote);
			if((value != null && checkedValue != null && !"".equals(checkedValue) && value.trim().equals(checkedValue.trim()))){
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" disabled />").append(text);
			str.append(displayHiddenField(value, inputFieldName));
			return str.toString();
				
		}else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)){
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(checkedValue)).append(doubleQuote);
			if((value != null && checkedValue != null && !"".equals(checkedValue) && value.trim().equals(checkedValue.trim()))){
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" ").append(action);
			str.append("/>").append(text);
			return str.toString();
		}	
		return "";
	}
	
	public static String displayCheckBoxTagDesc(String value,String checkedValue, String mode, String inputFieldName, String action, String text, String className){		
		StringBuilder str = new StringBuilder();
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)){
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			if ((value != null && checkedValue != null &&  !checkedValue.equals("")  && value.trim().equals(checkedValue.trim()) )) {
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" disabled />").append(text);
			return str.toString();
				
		}else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)){
			str.append("<input type=").append(doubleQuote).append("checkbox").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			if (className != null &&  !className.equals("")) {
				str.append(" class=").append(doubleQuote).append(className).append(doubleQuote);
			} else {
				str.append(" class=").append(doubleQuote).append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			}
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
			if ((value != null && checkedValue != null &&  !checkedValue.equals("")  && value.trim().equals(checkedValue.trim()) )) {
				str.append(" checked=").append(doubleQuote).append("checked").append(doubleQuote);
			}
			str.append(" ").append(action);
			str.append("/>").append(text);
			return str.toString();
		}	
		return "";
	}

	public static String displayInputTag(String value,String mode,String inputSize,String inputFieldName,String inputStyle) {
		StringBuilder str = new  StringBuilder();
		if (value == null){
			value = "";
		}
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" textboxReadOnly ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" maxlength = ").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append("readOnly />");
			return str.toString();
		}else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
			str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
			str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
			str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
			str.append(" maxlength = ").append(doubleQuote).append(inputSize).append(doubleQuote);
			str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append("/>");
			return str.toString();
		}
		return "";
	}
	public static String displayEmptyString(String value){
		if( value == null) return "";
		return value;
	}
	
	public static String displayPopUpTagScriptAction(String value,String mode,String inputSize,String inputFieldName,
										String inputStyle,String scriptAction,String maxLength,	String popupLabel,
												String popupStyle,String popupAction,String[] paramFields,String popupScriptAction,String inputFieldName2) {
			StringBuilder str = new StringBuilder();
			if (value == null) {
				value = "";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ").append(" disabled ");
				str.append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
				str.append(" disabled ");
				str.append(" />");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ").append(scriptAction);
				str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
				str.append(" />");
				str.append("&nbsp;");
				str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
				str.append(" ");
				str.append("onClick=").append(doubleQuote);
				str.append("javascript:popupscriptFields('"+popupAction+"',new Array("+CreateParam(paramFields,inputFieldName)+"),'','"+inputFieldName2+"','"+inputFieldName+"','')");
				str.append(doubleQuote);
				str.append(" ").append(popupScriptAction);
				str.append(" />");
				return str.toString();
			} 
		return "";
	}
	
	@Deprecated
	public static String DisplayButtonExecute(String buttonName ,String value,String displayMode ,PLApplicationDataM plApplicationM){
//		StringBuilder str = new StringBuilder();
//		PLOrigXrulesUtil xRulesUtil = new PLOrigXrulesUtil();
//		String buttonStyle = xRulesUtil.ButtonExecuteStyle(plApplicationM, buttonName);
//		if (displayMode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)) {
//			str.append(" <input ").append("type=").append(doubleQuote).append("button").append(doubleQuote);
//			str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
//			str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
//			str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
//			str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
//			str.append(" ");
//			str.append(" >");		
//		}
//		if (displayMode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)) {
//			str.append(" <input ").append("type=").append(doubleQuote).append("button").append(doubleQuote);
//			str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
//			str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
//			str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
//			str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
//			str.append(" ");
//			str.append("onclick=").append(doubleQuote).append("executeXrulesService('"+buttonName+"')").append(doubleQuote);
//			str.append(" >");
//		}
//		return str.toString();
		return null;
	}
	public static String displayPopUpTagScriptAction2(
			String value,
			String mode,
			String inputSize1,
			String inputFieldName1,
			String inputSize2,
			String inputFieldName2,
			String inputStyle,
			String scriptAction,
			String maxLength,
			String popupLabel,
			String popupStyle,
			String popupAction,
			String[] paramFields,
			String popupScriptAction,
			String value2,
			String cacheName) {
//			String returnStr = null;
			if (value == null) {
				value = "";
			}
			String disabledButton = "";
			StringBuilder returnStr = new  StringBuilder();
			if(READ_ONLY.equalsIgnoreCase(scriptAction) || DISABLED.equalsIgnoreCase(scriptAction)){
				disabledButton = "disabled";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {				
				returnStr.append("<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>");
				returnStr.append("<INPUT size=").append(doubleQuote).append(inputSize1).append(doubleQuote);
				returnStr.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				returnStr.append(doubleQuote).append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				returnStr.append(doubleQuote).append(" id=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(" ").append(" readOnly ");
				returnStr.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote).append(">");
				returnStr.append("</td>");
				returnStr.append("<td>");
				
				String strParam = "";
				if(paramFields != null && paramFields.length > 0){
					boolean hasMainField = false;
					if(inputFieldName1.equals(paramFields[0])){
						hasMainField = true;
					}
					strParam = strParam + "'" + paramFields[0] + "'";
					for(int i = 1; i < paramFields.length; i++){
						if(paramFields[i] != null){
							if(inputFieldName1.equals(paramFields[i])){
								hasMainField = true;
							}
							strParam = strParam + ",'" + paramFields[i] + "'";
						}
					}
					if(!hasMainField){
						strParam = strParam + ",'" + inputFieldName1 + "'";
					}
				}else{
					strParam = "'" + inputFieldName1 + "'";
				}
				returnStr.append("<INPUT type=").append(doubleQuote).append("button").append(doubleQuote);
				returnStr.append(" name=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
				returnStr.append(" id=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);							 
				returnStr.append(" Disabled ");
				returnStr.append(" onClick=\"javascript:popupscriptFields('").append(popupAction).append("',new Array(").append(strParam).append("))\" ");
				returnStr.append(popupScriptAction).append(" > </td><td width='150'>");
				returnStr.append("<INPUT size=").append(doubleQuote).append(inputSize2).append(doubleQuote);
				returnStr.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				returnStr.append(" name=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
				returnStr.append(" id=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(value2)).append(doubleQuote);
				returnStr.append(" ").append(" readOnly ").append(">").append("</td>");
				returnStr.append("</tr>").append("</table>");

				return returnStr.toString();
			} else if (mode != null || DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				returnStr.append("<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>");
				returnStr.append("<INPUT size=").append(doubleQuote).append(inputSize1).append(doubleQuote);
				returnStr.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				returnStr.append(doubleQuote).append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				returnStr.append(doubleQuote).append(" id=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append(inputStyle).append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote).append(" ").append(scriptAction);
				returnStr.append(" maxlength=").append(doubleQuote).append(maxLength).append(doubleQuote);
				returnStr.append(" onblur=\"javascript:setDescription(this.value,'\" ").append(inputFieldName2).append("','");
				returnStr.append(cacheName).append("','").append(inputFieldName1).append("Popup").append("')\"").append(">");
				returnStr.append("</td>");
				returnStr.append("<td>");
				
				String strParam = "";
				if(paramFields != null && paramFields.length > 0){
					boolean hasMainField = false;
					if(inputFieldName1.equals(paramFields[0])){
						hasMainField = true;
					}
					strParam = strParam + "'" + paramFields[0] + "'";
					for(int i = 1; i < paramFields.length; i++){
						if(paramFields[i] != null){
							if(inputFieldName1.equals(paramFields[i])){
								hasMainField = true;
							}
							strParam = strParam + ",'" + paramFields[i] + "'";
						}
					}
					if(!hasMainField){
						strParam = strParam + ",'" + inputFieldName1 + "'";
					}
				}else{
					strParam = "'" + inputFieldName1 + "'";
				}
				returnStr.append("<INPUT type=").append(doubleQuote).append("button").append(doubleQuote);
				returnStr.append(" name=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
				returnStr.append(" id=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);							 
				returnStr.append(" onClick=\"javascript:popupscriptFields('").append(popupAction).append("',new Array( " ).append(strParam).append(" ))\" ");
				returnStr.append(cacheName).append("','").append(inputFieldName2).append("','").append(inputFieldName1).append("','").append(value).append("')\" ");
				returnStr.append(popupScriptAction).append(" ").append(disabledButton).append(">");
				returnStr.append("</td><td width='150'>");
				returnStr.append("<INPUT size=").append(doubleQuote).append(inputSize2).append(doubleQuote);
				returnStr.append(" style=").append(doubleQuote).append("width:150").append(doubleQuote);
				returnStr.append(" type=").append(doubleQuote).append(" text ").append(doubleQuote);
				returnStr.append(" name=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
				returnStr.append(" id=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
				returnStr.append(" class=").append(doubleQuote).append("textboxReadOnly").append(doubleQuote);
				returnStr.append(" value=").append(doubleQuote).append(displayHTML(value2)).append(doubleQuote);
				returnStr.append(" ").append(" readOnly ");
				returnStr.append("> </td>").append("</tr>");
				returnStr.append("</table>");
				
				return returnStr.toString();
//			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
//				return "";
			} else
				return "";
		}
	public static String displayPriority(String priority){
		if(null == priority) return "";
		return displayPriority(Integer.valueOf(priority));
	}
	public static String displayPriority(int priority){
		String result;
		switch (priority) {
			case 0:		result="Normal";
				break;
			case 1:		result="High";
				break;
			case 2:		result="Urgent";
				break;			
			default:	result="Normal";
				break;
		}
		return result;
	}
	public static String displayButtonTagScriptAction(String value, String mode, String type,String buttonName,String buttonStyle,String scriptAction,String disableMode) {
			if (value == null) {
				value = "";
			}
			if("submit".equalsIgnoreCase(type))
				type="submit";
			if("reset".equalsIgnoreCase(type))
				type="reset";
			if(OrigUtil.isEmptyString(type)||"button".equalsIgnoreCase(type))
				type="button";
			
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				str.append("<input ").append(" type=").append(doubleQuote).append(type).append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append("  ");
				str.append(scriptAction);
				str.append(" ");
				str.append(disableMode);
				str.append(" disabled=\"disabled\" >");
				return str.toString();
			}else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {			
				str.append("<input ").append(" type=").append(doubleQuote).append(type).append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
				str.append(" ");
				str.append(scriptAction);
				str.append(" ");
				str.append(disableMode);
				str.append(">");
				return str.toString();
			} 
			return  displayHTML("&nbsp;");
	}
	public static String displayInputTextAreaTag_Orig(String value,String inputFieldName,String row,String column,String mode,String javascript) {
    	StringBuilder str = new StringBuilder();
    		if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
    			str.append("<textarea name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
    			str.append(" rows=").append(doubleQuote).append(row).append(doubleQuote);
    			str.append(" cols=").append(doubleQuote).append(column).append(doubleQuote);
    			str.append(" ");
    			str.append(javascript).append(" ");
    			str.append(" class=").append(doubleQuote).append("TextAreaBox").append(doubleQuote);
    			str.append(">");
    			str.append(displayHTML(value));
    			str.append(" </textarea>");
    			return str.toString();
    		}else if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
    		    int colomLength=25;	    		    
    		    try {
                    colomLength=Integer.parseInt(column);
                }catch(NumberFormatException e){
                }
                StringBuilder warpStr = new StringBuilder();
                int valueLength=value.length();
                try {
                    for(int i=0;i<valueLength;i=i+colomLength){
                      if((i+colomLength)<valueLength){  
                       String line= value.substring(i,i+colomLength);
                       if(line.indexOf("\n")!=-1){                             
                    	   warpStr.append(line.replaceAll("\n","<br>"));
                       }else{
                    	   warpStr.append(line);
                           warpStr.append("<br>");
                       }
                      }else{
                    	  warpStr.append( value.substring(i));   
                      }
                    }
                }catch(RuntimeException e1){
                    warpStr=new StringBuilder();
                    warpStr.append(value);
                }
    			return warpStr.toString();
    		}
    		return "";
    }
	public static String displayInputTextAreaTag(String value,String inputFieldName,String row,String column,String mode ,int maxLength) {
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){
    			str.append("<textarea name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
    			str.append(" rows=").append(row);
    			str.append(" cols=").append(column);
    			str.append(" class=").append(doubleQuote).append("TextAreaBox").append(doubleQuote);
    			str.append(" maxlength=").append(doubleQuote).append(maxLength).append(doubleQuote);
    			str.append(" ").append("onkeyup=\"return ismaxlength(this)\"");
    			str.append(">");
    			str.append(displayHTML(value));
    			str.append(" </textarea>");
				return replaceQuote(str.toString());
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){
				return value;
			}
		return "";
	}
	public static String displayInputTextAreaTag(String value,String inputFieldName,String row,String column,String mode,String javascript) {
		StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<textarea name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
    			str.append(" rows=").append(doubleQuote).append(row).append(doubleQuote);
    			str.append(" cols=").append(doubleQuote).append(column).append(doubleQuote);
    			str.append(" ");
    			str.append(javascript).append(" ");
    			str.append(" class=").append(doubleQuote).append("TextAreaBox").append(doubleQuote);
    			str.append(">");
    			str.append(displayHTML(value));
    			str.append(" </textarea>");
    			return str.toString();
			} 
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {
				return value;
			} 
			return "";
		}
	
	public static String stringDateTimeValueListForThai(String strDateTime) {
		if (strDateTime != null && !strDateTime.isEmpty()) {
			String[] arrDateTime = strDateTime.split(" ");
			String strDate = arrDateTime[0];
			String strTime = arrDateTime[1];
			
			String[] arrDate = strDate.split("-");
			String[] arrTime = strTime.split(":");
			if (arrTime[2] != null && arrTime[2].length() >= 2) {
				arrTime[2] = arrTime[2].substring(0, 2);
			}
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]), Integer.parseInt(arrTime[0]), Integer.parseInt(arrTime[1]), Integer.parseInt(arrTime[2]));
			DecimalFormat dformat = new DecimalFormat("00");
			
			StringBuffer date = new StringBuffer();
			date.append(arrDate[2]).append("/");
			date.append(arrDate[1]).append("/");
			date.append(Integer.parseInt(arrDate[0])+543);
			
			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "";
		}
	}
	
	public static String CreateReasonTable(String reasonCode, String displayMode ,String role, Vector<PLReasonDataM> reasonVect ,String busClass,String searchType) {
		StringBuilder str = new StringBuilder("");
		if(!OrigUtil.isEmptyString(reasonCode)){
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			Vector<ORIGCacheDataM> decisionVect = cacheUtil.GetReasonDecision(Integer.parseInt(reasonCode),busClass);
			str.append("<table class=\"FormFrame\" ");
			if(!OrigUtil.isEmptyVector(decisionVect)){
				int size = decisionVect.size();
				for (ORIGCacheDataM origCacheM : decisionVect) {
					String value = "";
					str.append("<tr>");
							str.append("<td class=\"textL\">");						
							if(!OrigUtil.isEmptyVector(reasonVect)){
								value = GetReasonCheck(reasonVect,origCacheM.getFileID(),origCacheM.getCode(),role);
							}
							//septemwi set decision
							if(size == 1 && !HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
								value = origCacheM.getCode();
							}
							str.append(displayCheckBoxTagDesc(value,origCacheM.getCode(), displayMode,"reasonOption"," ", origCacheM.getThDesc()));
							str.append("</td>");
					str.append("</tr>");
				}
			}
			str.append("</table>");
		}
		str.append("");
		str.append(displayHiddenField(reasonCode, "reasonType"));
		str.append(displayHiddenField(role, "reasonRole"));
		return str.toString();
	}
	
	/**#septem fix check reason by role */	
	public static String GetReasonCheck(Vector<PLReasonDataM> reasonVect, int fieldID,String choiceNo ,String role){
		String result = "";
		for (PLReasonDataM reasonM : reasonVect){					
			if(null != reasonM && !OrigUtil.isEmptyString(choiceNo) && choiceNo.equals(reasonM.getReasonCode())
					&& !OrigUtil.isEmptyString(reasonM.getReasonType()) && fieldID == Integer.valueOf(reasonM.getReasonType())
						&& !OrigUtil.isEmptyString(role) && role.equals(reasonM.getRole())){
				result = reasonM.getReasonCode();
				logger.debug("result >> "+result);
				return result;
			}
		}	
		return "";
	}
	
	/*** Use for Display Veto Status Pipe*/
	public static String DisplayVetoStatus(String status){
		String result="No";
		if(OrigUtil.isEmptyString(status)||status.equalsIgnoreCase("N")){
			return result;
		}else{
			result="Yes";
			return result;
		}
	}
	/*** Use for Display NCB Status Pipe*/
	public static String DisplayNCBStatus(String status){
		String result = "";
		if(OrigUtil.isEmptyString(status)){
			return result;
		}else if(status.equalsIgnoreCase("SB")){
			result="Back";
			return result;
		}else if(status.equalsIgnoreCase("RQ") || status.equalsIgnoreCase("WD")){
			result="No";
			return result;
		}else{
			result="Yes";
			return result;
		}
	}
	public static String DisplayReplaceLineWithNull(String value){
		if(value==null ||value.length()==0){
			value="-";
		}
		return displayHTML(value);
	}
	
	public static String displayTypeColor(String typeCode){
		String result = "";
		if(typeCode == null || "".equals(typeCode)){
			//return "";
			result = "<img src=\"images/cycleGreen.png\">";
		}else if (OrigConstant.typeColor.typeGreen.equals(typeCode)){
			result = "<img src=\"images/cycleGreen.png\">";
		}else if (OrigConstant.typeColor.typeYellow.equals(typeCode)){
			result = "<img src=\"images/cycleYellow.png\">";
		}else if (OrigConstant.typeColor.typeRed.equals(typeCode)){
			result = "<img src=\"images/cycleRed.png\">";
		}
		return result;
	}

	public static String displayFlagYesNo(String flag){
		if(OrigConstant.FLAG_Y.equals(flag)){
			return OrigConstant.STR_FLAG_Y;
		}else {
			return OrigConstant.STR_FLAG_N;
		}
	}

	public static String displaySaleTypeCash(String saleType, String cashDayName){
		String result = "";
		if(saleType == null || "".equals(saleType)){
			return "";
		}else if(cashDayName == null || "".equals(cashDayName)){
			return ORIGCacheUtil.getInstance().getORIGMasterDisplayNameDataM(OrigConstant.CacheName.CACHE_NAME_SALE_TYPE, saleType);
		}else if(OrigConstant.cashDayType.CASH_DAY_1.equals(cashDayName) || OrigConstant.cashDayType.CASH_DAY_5.equals(cashDayName)){
			result = ORIGCacheUtil.getInstance().getORIGMasterDisplayNameDataM(OrigConstant.CacheName.CACHE_NAME_SALE_TYPE, saleType) 
			         +"/"
			         + ORIGCacheUtil.getInstance().getNaosCacheDisplayNameDataM(OrigConstant.fieldId.CASH_DAT_TYPE,cashDayName);
		}else{
			result = ORIGCacheUtil.getInstance().getORIGMasterDisplayNameDataM(OrigConstant.CacheName.CACHE_NAME_SALE_TYPE, saleType);
		}
		return result;
	}
	
	public static String DisplayTextBoxButtonSearchDesc(String codeValue ,int fieldID ,String valueTextbox,String mode
													,String textboxSearchName ,String textboxBlankName ,String buttonName){
		StringBuilder str = new StringBuilder();
		String desc = null;
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		if(!OrigUtil.isEmptyString(codeValue)){
			desc = cacheUtil.getNaosCacheDisplayNameDataM(fieldID,codeValue);
		}
		if(mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){
			str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
					str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
						str.append("<input ");
						str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
						str.append(" name=").append(doubleQuote).append(textboxSearchName).append(doubleQuote);
						str.append(" id= ").append(doubleQuote).append(textboxSearchName).append(doubleQuote);
						str.append(" class=").append(doubleQuote).append("textboxReadOnly").append(doubleQuote);
						str.append(" value=").append(doubleQuote).append(displayHTML(desc)).append(doubleQuote);						
						str.append(" readOnly />");
					str.append("</td>");
					str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
						str.append("&nbsp;");
						str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
						str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
						str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
						str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
						str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
						str.append(" />");
						str.append("&nbsp;");
					str.append("</td>");
					str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
						str.append("<input ");
						str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
						str.append(" name=").append(doubleQuote).append(textboxBlankName).append(doubleQuote);
						str.append(" id=").append(doubleQuote).append(textboxBlankName).append(doubleQuote);				
						str.append(" class=").append(doubleQuote).append("textbox").append(doubleQuote);				
						str.append(" value=").append(doubleQuote).append(displayHTML(valueTextbox)).append(doubleQuote);
						str.append(" readOnly />");
					str.append("</td>");
				str.append("</tr>");
			str.append("</table>");
			return str.toString();
		}else if(mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){
			str.append("<table align='left' cellpadding='0' cellspacing='0'>");
			str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(textboxSearchName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(textboxSearchName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("textbox-searchcode").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(desc)).append(doubleQuote);						
					str.append("/>");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(buttonName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(buttonName).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
					str.append(" />");
					str.append("&nbsp;");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(textboxBlankName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(textboxBlankName).append(doubleQuote);				
					str.append(" class=").append(doubleQuote).append("textbox").append(doubleQuote);				
					str.append(" value=").append(doubleQuote).append(displayHTML(valueTextbox)).append(doubleQuote);
					str.append("/>");
				str.append("</td>");
			str.append("</tr>");
			str.append("</table>");
			return str.toString();
		}
		return "";
	}
	
	
	public static String DisplayPopUpUserName(String value ,String displayMode,String fieldName,String fieldStyle
													,String maxlength ,String hiddenName){
			StringBuilder str = new StringBuilder("");
			if(value == null){
				value = "";
			}
						
			if (displayMode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(fieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("textbox-code-view").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("").append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxlength).append(doubleQuote);
					str.append(" readOnly />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(fieldName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
					str.append(" disabled ").append(" />");
					str.append("&nbsp;");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append(displayHiddenField(value, hiddenName));					
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}else if(displayMode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)) {
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");				
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(fieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(fieldStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("").append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxlength).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
				str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(fieldName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
					str.append(" />");
				str.append("&nbsp;");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append(displayHiddenField(value, hiddenName));					
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}
		return "";
	}
	
	public static String DisplayPopUpTextBoxDescFieldIDAndTextBox(String value ,int fieldID ,String displayMode,String fieldIDName,String styleFieldID
											,String maxLFieldID ,String valueTextBox, String textBoxName ,String sytleTextBox, String maxLTextBox 
												,String hiddenName ,String action,String title){
			StringBuilder str = new StringBuilder("");
			if(value == null){
				value = "";
			}
			
			String objValue = String.valueOf(fieldID)+"|"+HTMLRenderUtil.replaceNull(fieldIDName)
										+"|"+HTMLRenderUtil.replaceNull(textBoxName)+"|"+HTMLRenderUtil.replaceNull(hiddenName)
											+"|"+HTMLRenderUtil.replaceNull(action)+"|"+HTMLRenderUtil.replaceNull(title);
			
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			if (displayMode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(displayMode)){
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldIDName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(fieldIDName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("textbox-code-view").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(cacheUtil.getORIGCacheDisplayNameDataM(fieldID, value)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLFieldID).append(doubleQuote);
					str.append(" readOnly />");				
					str.append(displayHiddenField(objValue, fieldIDName+"_obj"));
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldIDName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(fieldIDName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
					str.append(" disabled ").append(" />");
					str.append("&nbsp;");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");	
					str.append(displayInputTagScriptAction(valueTextBox, displayMode, maxLTextBox, textBoxName,sytleTextBox,"",maxLTextBox));
					str.append(displayHiddenField(value, hiddenName));					
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			} else	if (displayMode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)) {
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input ");
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldIDName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(fieldIDName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(styleFieldID).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(cacheUtil.getORIGCacheDisplayNameDataM(fieldID, value)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLFieldID).append(doubleQuote);
					str.append(" />");
					str.append(displayHiddenField(objValue, fieldIDName+"_obj"));
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(fieldIDName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(fieldIDName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append("...").append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("button-popup").append(doubleQuote);
					str.append(" />");
					str.append("&nbsp;");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append(displayInputTagScriptAction(valueTextBox, displayMode, maxLTextBox, textBoxName,sytleTextBox,"",maxLTextBox));
					str.append(displayHiddenField(value, hiddenName));
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}
		return "";
	}
			
	public static String DisplayPopUpProjectCode(String value,String mode,String inputSize,
									String inputFieldName,String inputStyle,String maxLength
										,String buttonLabel,String buttonStyle) {
			StringBuilder str = new StringBuilder();
			if (value == null){
				value = "";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append("textbox-projectcode-view").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" readOnly />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(buttonLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
					str.append(" disabled ").append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("&nbsp;");
					str.append("&nbsp;<img id='reset-projectcode' align='bottom' src='images/reset.png' onmouseover=\"style.cursor='pointer'\" />");
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			} else	if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append("_popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append("_popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(buttonLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(buttonStyle).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("&nbsp;");
					str.append("<img id='reset-projectcode' align='bottom' src='images/reset.png' onmouseover=\"style.cursor='pointer'\" />");
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}
		return "";
	}
	
	public static String displayPopUpTagNotSetScriptAction(String value,String mode,String inputSize,
			String inputFieldName,String inputStyle,String scriptAction,
			String maxLength,String popupLabel,	String popupStyle) {
			StringBuilder str = new StringBuilder();
			if (OrigUtil.isEmptyString(value)) {
				value = "";
			}
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)) {		
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle).append(" textboxReadOnly").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" readOnly />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
				str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
					str.append(" disabled ").append(" />");
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");
					str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value)).append(doubleQuote);
					str.append(" ").append(scriptAction);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName).append("Popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				return str.toString();
			}
		return "";
	}
	
	public static String displayPopUpWith2TextboxNotTagScriptAction( 
								String value1,String inputSize1,String inputFieldName1,String maxLength1,
									String inputStyle1,String value2,String inputSize2,String inputFieldName2,
										String maxLength2,String inputStyle2,String popupLabel,String popupStyle,String mode){		
			if (OrigUtil.isEmptyString(value1)){
				value1 = "";
			}
			if (OrigUtil.isEmptyString(value2)){
				value2 = "";
			}
			
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){				
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(">");				
					str.append("<input size=").append(doubleQuote).append(inputSize1).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle1).append(" textboxReadOnly").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value1)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength1).append(doubleQuote);
					str.append(" readOnly />");				
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");				
				str.append("&nbsp;");				
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
					str.append(" disabled ").append(" />");	
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' align='left' ").append(">");				
					str.append("<input size=").append(doubleQuote).append(inputSize2).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle2).append(" textboxReadOnly").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value2)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength2).append(doubleQuote);
					str.append(" readOnly />");				
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");	
				
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)) {
				
				str.append("<table align='left' cellpadding='0' cellspacing='0'>");
				str.append("<tr>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" >");
					str.append("<input size=").append(doubleQuote).append(inputSize1).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName1).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle1).append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value1)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength1).append(doubleQuote);
					str.append(" />");					
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' ").append(">");	
					str.append("&nbsp;");
					str.append("<input type=").append(doubleQuote).append("button").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
					str.append(" id=").append(doubleQuote).append(inputFieldName1).append("Popup").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(popupLabel)).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(popupStyle).append(doubleQuote);
					str.append(" />");
				str.append("</td>");
				str.append("<td ").append("nowrap=").append(doubleQuote).append("nowrap").append(doubleQuote).append(" valign='top' align='left' ").append(">");		
					str.append("<input size=").append(doubleQuote).append(inputSize2).append(doubleQuote);
					str.append(" type=").append(doubleQuote).append("text").append(doubleQuote);
					str.append(" name=").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
					str.append(" id= ").append(doubleQuote).append(inputFieldName2).append(doubleQuote);
					str.append(" class=").append(doubleQuote).append(inputStyle2).append(" textboxReadOnly").append(doubleQuote);
					str.append(" value=").append(doubleQuote).append(displayHTML(value2)).append(doubleQuote);
					str.append(" maxlength = ").append(doubleQuote).append(maxLength2).append(doubleQuote);
					str.append(" readOnly/>");
				str.append("</td>");
				str.append("</tr>");
				str.append("</table>");
				return str.toString();
			}
		return "";
	}
	public static String DisplayProduct(String product){
		String result = "";
		if(!OrigUtil.isEmptyString(product)){
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			MainProductProperties properties = cacheUtil.getMainProduct(product);
			return properties.getThDesc();
		}else{
			return result;
		}
	}
	
	public static String displaySaleType(String saleType){
		String result ="";
		if(OrigUtil.isEmptyString(saleType)){return result;}

		if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.NORMAL)) {
			return OrigConstant.FullSaleType.NORMAL;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.X_SELL)) {
			return OrigConstant.FullSaleType.X_SELL;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.INCREASE)) {
			return OrigConstant.FullSaleType.INCREASE;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.DECREASE)) {
			return OrigConstant.FullSaleType.DECREASE;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD)) {
			return OrigConstant.FullSaleType.BUNDLING_CREDIT_CARD;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.BUNDLING_CREDIT_CARD_GENERIC)) {
			return OrigConstant.FullSaleType.BUNDLING_CREDIT_CARD_GENERIC;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.BUNDLING_HOME_LOAN)) {
			return OrigConstant.FullSaleType.BUNDLING_HOME_LOAN;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.BUNDLING_AUTO_LOAN)) {
			return OrigConstant.FullSaleType.BUNDLING_AUTO_LOAN;
		} else if (saleType.equalsIgnoreCase(OrigConstant.ShortSaleType.BUNDLING_SME)) {
			return OrigConstant.FullSaleType.BUNDLING_SME;
		} else {
			return result;
		}
	}
	
	
	public static String displayThFullName(String userName) throws Exception{	
//		HashMap h = TableLookupCache.getCacheStructure();
//		Vector<UserNameProperties> dataVect = (Vector) (h.get("UserNameCacheDataM"));
//		if (dataVect != null && dataVect.size() > 0) {
//			for (UserNameProperties userM : dataVect){
//				if(null != userM && OrigConstant.Status.STATUS_ACTIVE.equals(userM.getStatus())){
//					if (OrigConstant.SYSTEM.equalsIgnoreCase(userName) || OrigConstant.SCHEDULER.equalsIgnoreCase(userName)){
//						return OrigConstant.SYSTEM;
//					}else if(userName.equals(userM.getUserName())) {
//						return DataFormatUtility.StringNullToSpecific(userM.getThFirstName(),"")+"  "+DataFormatUtility.StringNullToSpecific(userM.getThLastName(),"");
//					}
//				}
//			}
//		}
		
		if (OrigConstant.SYSTEM.equalsIgnoreCase(userName) || OrigConstant.SCHEDULER.equalsIgnoreCase(userName)){
			return OrigConstant.SYSTEM;
		}
		UserNameProperties userM = ORIGCache.getUserCache().get(userName);
		if(null != userM && OrigConstant.Status.STATUS_ACTIVE.equals(userM.getStatus())){
			return DataFormatUtility.StringNullToSpecific(userM.getThFirstName(),"") 
					+"  "+DataFormatUtility.StringNullToSpecific(userM.getThLastName(),"");
		}
		
		return "-";
	}
	
	public static String displayThFullName(String userName, Vector<UserNameProperties> userVect){
		try{
//			if(!OrigUtil.isEmptyVector(userVect) && !(OrigUtil.isEmptyString(userName))){
//				for (int i = 0; i < userVect.size(); i++) {
//					UserNameProperties userPro = userVect.get(i);
//					if (OrigConstant.SYSTEM.equalsIgnoreCase(userName) || OrigConstant.SCHEDULER.equalsIgnoreCase(userName)){
//						return OrigConstant.SYSTEM;
//					} else if (userName.equals(userPro.getUserName())) {
//						return DataFormatUtility.StringNullToSpecific(userPro.getThFirstName(),"") + "  " + DataFormatUtility.StringNullToSpecific(userPro.getThLastName(),"");
//					}
//				}
//			}
			if (OrigConstant.SYSTEM.equalsIgnoreCase(userName) || OrigConstant.SCHEDULER.equalsIgnoreCase(userName)){
				return OrigConstant.SYSTEM;
			}
			UserNameProperties userM = ORIGCache.getUserCache().get(userName);
			if(null != userM){
				return DataFormatUtility.StringNullToSpecific(userM.getThFirstName(),"") 
						+"  "+DataFormatUtility.StringNullToSpecific(userM.getThLastName(),"");
			}			
		}catch(Exception e){
			return "-";
		}
		return "-";
	}
	
	public static String displayDecisionAction(String decision) throws Exception {
		if (!OrigUtil.isEmptyString(decision)) {
			StringBuilder sDecision = new StringBuilder();
			decision = decision.replace(" ", "_").toUpperCase();
			sDecision.append("ACTION_").append(decision);
			return sDecision.toString();
		}
		return null;
	}
	
	public static String displayAppStatusForCS(String status) throws Exception {
		if(!OrigUtil.isEmptyString(status)){
			StringBuilder KEY = new StringBuilder("");
			if(status.equals(OrigConstant.ApplicationStatus.REJECTED) || status.equals(OrigConstant.ApplicationStatus.POST_REJECTED)) {
				KEY.append("APPSTATUS_REJECTED_CS");
			}else if(status.equals(OrigConstant.ApplicationStatus.APPROVED) || status.equals(OrigConstant.ApplicationStatus.POST_APPROVED)) {
				KEY.append("APPSTATUS_APPROVED_CS");
			}else if(status.equals(OrigConstant.ApplicationStatus.CANCELLED)){
				KEY.append("APPSTATUS_CANCELLED_CS");
			}else{
				KEY.append("APPSTATUS_OTHERWISE_CS");
			}
			return KEY.toString();
		}
		return "";
	}
	
	public static String displayCreditLineForCS(String status, String creditLine) throws Exception {
		String creditLineStr = "-";
		if(!OrigUtil.isEmptyString(status)) {
			if (OrigConstant.ApplicationStatus.APPROVED.equals(status) || OrigConstant.ApplicationStatus.POST_APPROVED.equals(status)) {
				creditLineStr = DataFormatUtility.displayCommaNumber(DataFormatUtility.StringToBigDecimalEmptyNull(creditLine));
			}
		}
		return creditLineStr;
	}
	
	public static String DisplayReasonDesc(Vector<PLReasonDataM> reasonVect){
		if(OrigUtil.isEmptyVector(reasonVect))
			return "";
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < reasonVect.size(); i++) {
	 		PLReasonDataM reasonM = (PLReasonDataM) reasonVect.get(i);
	 		str.append(cacheUtil.getORIGCacheDisplayNameDataM(OrigUtil.stringToInt(reasonM.getReasonType()), reasonM.getReasonCode()));
			if (i < (reasonVect.size() - 1)) {
	 			str.append(", ");
		 	}
		}
		return str.toString();
	}
	
	public static String DisplayReasonDesc(Vector<PLReasonDataM> reasonVect, PLApplicationDataM applicationM){
		logger.debug("Decision >> "+applicationM.getAppDecision());
		logger.debug("Job State >> "+applicationM.getJobState());
		
		if((OrigConstant.Action.CONFIRM_REJECT.equals(applicationM.getAppDecision())
				|| OrigConstant.Action.REJECT.equals(applicationM.getAppDecision())
				|| OrigConstant.Action.REJECT_SKIP_DF.equals(applicationM.getAppDecision())) 
		    && OrigUtil.isEmptyVector(reasonVect)){
			reasonVect = PLORIGEJBService.getORIGDAOUtilLocal().getConfirmRejectReasons(applicationM.getAppRecordID(), new UserDetailM());
		}
		if(OrigUtil.isEmptyVector(reasonVect)){
			return "";
		}
		
		if(OrigConstant.Action.SAVE_DRAFT.equals(applicationM.getAppDecision())){
			return "";
		}
		
		
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		GeneralParamUtil paramUtil = new GeneralParamUtil();
		if(paramUtil.CheckJobState(OrigConstant.generalParam_JobState.CANCEL_ALL
						, applicationM.getBusinessClassId(), applicationM.getJobState())
				||(!OrigUtil.isEmptyString(applicationM.getAppDecision())
						&& applicationM.getAppDecision().lastIndexOf(OrigConstant.Action.CANCEL) > -1)
				){			
			logger.debug("Display Reason Cancle ... ");
			StringBuilder str = new StringBuilder();
			for(int i = 0; i < reasonVect.size(); i++){
				PLReasonDataM reasonM = (PLReasonDataM) reasonVect.get(i);
				if("33".equals(reasonM.getReasonType())){
			 		str.append(cacheUtil.getORIGCacheDisplayNameDataMAllStatus(OrigUtil.stringToInt(reasonM.getReasonType()), reasonM.getReasonCode()));
					if(i < (reasonVect.size() - 1)){
			 			str.append(", ");
				 	}
				}
			}
			return str.toString();
		}
		
		if(paramUtil.CheckJobState(OrigConstant.generalParam_JobState.REJECT_ALL
						, applicationM.getBusinessClassId(), applicationM.getJobState())
				||(!OrigUtil.isEmptyString(applicationM.getAppDecision())
						&& applicationM.getAppDecision().lastIndexOf(OrigConstant.Action.REJECT) > -1)
					){			
			logger.debug("Display Reason Reject ... ");
			StringBuilder str = new StringBuilder();
			for(int i = 0; i < reasonVect.size(); i++){
				PLReasonDataM reasonM = (PLReasonDataM) reasonVect.get(i);
				if("34".equals(reasonM.getReasonType())){
			 		str.append(cacheUtil.getORIGCacheDisplayNameDataMAllStatus(OrigUtil.stringToInt(reasonM.getReasonType()), reasonM.getReasonCode()));
					if (i < (reasonVect.size() - 1)){
			 			str.append(", ");
				 	}
				}
			}
			return str.toString();
		}
		
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < reasonVect.size(); i++){
	 		PLReasonDataM reasonM = (PLReasonDataM) reasonVect.get(i);
	 		str.append(cacheUtil.getORIGCacheDisplayNameDataMAllStatus(OrigUtil.stringToInt(reasonM.getReasonType()), reasonM.getReasonCode()));
			if (i < (reasonVect.size() - 1)) {
	 			str.append(", ");
		 	}
		}
		return str.toString();
	}
	
	public static String displayInputTagJsDate(String formName,String value,String mode,String inputSize
			,String inputFieldName,String optionalFuncClass,String popupLocation,String inputScript){
			StringBuilder str = new StringBuilder();
			if (mode != null && DISPLAY_MODE_VIEW.equalsIgnoreCase(mode)){				
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar").append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(" ").append( " readOnly>");
				return str.toString();
			}else if (mode != null && DISPLAY_MODE_EDIT.equalsIgnoreCase(mode)){
				str.append("<table cellpadding=\"0\" cellspacing=\"0\" >");
				str.append("<tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=" ).append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar");
				str.append(" ").append(optionalFuncClass).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(inputScript).append(" >").append("</td>");
				str.append("<td valign=\"middle\">&nbsp;");
				str.append("<img class=\"date_trigger\" src=\"images/calendar.gif\" width=\"21px\" height=\"21px\"");
				str.append(" onclick=\"popUpCalendar(this,'"+inputFieldName+"','dd/mm/yyyy','','','','"+popupLocation+"')\">");
				str.append("</td></tr>");
				str.append("</table>");
				return str.toString();
			}		
		return "";
	}
	public static String displayInputTagDate(
			String formName,
			String value,
			String mode,
			String inputSize,
			String inputFieldName,
			String inputStyle,
			String optionalFuncClass,
			String maxLength) {
			StringBuilder str = new StringBuilder();
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar").append(" textboxReadOnly").append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote);
				str.append(" ").append( " readOnly>");
				return str.toString();
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				str.append("<table cellpadding=\"0\" cellspacing=\"0\" >");
				str.append("<tr><td>");
				str.append("<input size=").append(doubleQuote).append(inputSize).append(doubleQuote);
				str.append(" type=").append(doubleQuote).append(" text").append(doubleQuote);
				str.append(" name=").append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" id=" ).append(doubleQuote).append(inputFieldName).append(doubleQuote);
				str.append(" class=").append(doubleQuote).append("textboxCalendar");
				str.append(" ").append(optionalFuncClass).append(" ").append(CssClass.CLASS_SENSATIVE).append(doubleQuote);
				str.append(" value=").append(doubleQuote).append(value).append(doubleQuote);
				str.append(" maxlength =").append(doubleQuote).append("10").append(doubleQuote).append(" >").append("</td>");
				str.append("<td valign=\"middle\">&nbsp;");	
				str.append(" <a href=\"javascript:show_calendar('"+ inputFieldName+ "');\" onmouseover=\"window.status='Date Picker';return true;\" onmouseout=\"window.status='';return true;\"><img src=\"images/calendar.gif\" width=21 height=21 left=200 top=300 border=0></a>");
				str.append("</td></tr>");
				str.append("</table>");
				return str.toString();
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
		}
	public static String GetDisplayDuplicateReason(String reasonObj){
		StringBuilder str = new StringBuilder("");
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		if(!OrigUtil.isEmptyString(reasonObj)){
			String[] obj1 = reasonObj.split("\\|");
			if(null != obj1){
				for(int i=0;i<obj1.length;i++){
					String data1 = (String) obj1[i];
					if(!OrigUtil.isEmptyString(data1)){
						String[] obj2 = data1.split("\\-");
						str.append(cacheUtil.getDisplayNameCache(OrigUtil.stringToInt(obj2[0]), obj2[1]));	
					}	
					if(i<(obj1.length-1)){	 	
			 			str.append(", ");
				 	}
				}
			}
		}		
		return str.toString();
	}

	public static String displayExistingCreditLimitDate(String creditLimitDate){
//		if(creditLimitDate == null || "".equals(creditLimitDate)){
//			return "";
//		}else if(OrigConstant.EXISTING_NULL_DATE_STRING.equals(creditLimitDate)){
//			return "00/00/0000";
//		}
//		try {   	
//			SimpleDateFormat dateFormat = new SimpleDateFormat(EAITool.Constant.yyyyMMdd);		
//			Date date = dateFormat.parse(creditLimitDate);
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(date);
//			return DataFormatUtility.DateEnToStringDateTh(calendar.getTime(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
//		}catch (Exception e){		    	
//		}	    
		return "";
	}
	
}

