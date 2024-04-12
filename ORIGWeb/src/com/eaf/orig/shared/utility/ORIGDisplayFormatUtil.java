/*
 * Created on Sep 19, 2007
 * Created by weeraya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.utility;

import java.math.BigDecimal;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.MandatoryFieldCache;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.cache.CacheDataInf;
import com.eaf.orig.shared.model.SLADataM;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;

/**
 * @author weeraya
 * Type: ORIGDisplayFormatUtil
*/

public class ORIGDisplayFormatUtil extends DisplayFormatUtil {
    
	private static Logger logger = Logger.getLogger(ORIGDisplayFormatUtil.class);
    
	static String doubleQuote = "\"";
	static String space = "&nbsp;" ;
	public static String READ_ONLY = "readOnly";
	public static String DISABLED = "disabled";
	
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
		
	public static String displayPopUpTagScriptAction(
		String value,
		String mode,
		String inputSize,
		String inputFieldName,
		String inputStyle,
		String scriptAction,
		String maxLength,
		String popupLabel,
		String popupStyle,
		String popupAction) {
		String returnStr = null;
		if (value == null) {
			value = "";
		}
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
			returnStr =
				"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote					
					+ " id="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " " + " Disabled "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ ">";
			returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " Disabled " + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName + "')\" "
											 + ">";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
			returnStr =
				"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ ">";
			returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName + "')\" "
											 + ">";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		} else
			return "";
	}
	
	public static String displayPopUpOneTextBoxTagScriptAction(
			String value,
			String mode,
			String inputSize,
			String inputFieldName1,
			String inputStyle,
			String scriptAction,
			String maxLength,
			String popupLabel,
			String popupStyle,
			String popupAction,
			String inputFieldName2,
			String cacheName,
			String inputRelateField1,
			String inputRelateField2) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " readOnly "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " id="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscriptOneTextBox('" + popupAction + "','" + inputFieldName2 + "','','" + inputFieldName1 + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ "onblur=\"javascript:setTitleDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName2+ "Popup"+"','"+inputRelateField1+"','"+inputRelateField2+"')\">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " id="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " onClick=\"javascript:popupscriptOneTextBox('" + popupAction + "','" + inputFieldName2 + "','','" + inputFieldName1 + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
	}
	
	
	/**
	 * @author septemwi
	 * @deprecated
	 * {@link HTMLRenderUtil#displayPopUpTagScriptAction(String, String, String, String, String, String, String, String, String, String, String)} 
	 * */
	@Deprecated
	public static String displayPopUpOneTextBoxNotSetTagScriptAction(
			String value,
			String mode,
			String inputSize,
			String inputFieldName1,
			String inputStyle,
			String scriptAction,
			String maxLength,
			String popupLabel,
			String popupStyle,
			String popupAction,
			String inputFieldName2,
			String cacheName) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " Disabled "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscriptOneTextBox('" + popupAction + "','" + inputFieldName2 + "','','" + inputFieldName1 + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName2 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " onClick=\"javascript:popupscriptOneTextBox('" + popupAction + "','" + inputFieldName2 + "','','" + inputFieldName1 + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
	}
	public static String displayPopUpTagScriptAction(
			String value,
			String mode,
			String inputSize,
			String inputFieldName,
			String inputStyle,
			String scriptAction,
			String maxLength,
			String popupLabel,
			String popupStyle,
			String popupAction,
			String cacheName) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " Disabled "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote 
												 + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName + "','" + cacheName + "')\" "
												 + ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
		}
	
	public static String displayPopUpTagScriptAction(
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
			String value2,
			String cacheName) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			String disabledButton = "";
			String[] scriptActions = scriptAction.split(" ");
			if(scriptActions != null){
				String str;
				for(int i=0; i<scriptActions.length; i++){
					str = scriptActions[i];
					if(str.equalsIgnoreCase(READ_ONLY) || str.equalsIgnoreCase(DISABLED)){
						disabledButton = "disabled";
						break;
					}
				}
			}
			
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
					+"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="+ doubleQuote + inputFieldName1 + doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " readOnly "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "</td><td width='30'>" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "','" + value + "')\" "
												 + "> </td> ";
				returnStr = returnStr + "<td width='150'>" + "<INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " id="+ doubleQuote + inputFieldName2 + doubleQuote
												+ " class="
												+ doubleQuote
												+ inputStyle
												+ doubleQuote
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " " + " readOnly "
												+ ">" + "</td></tr></table>";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
					+"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id=" + doubleQuote + inputFieldName1 + doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						//+ "onblur=\"javascript:setDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName1+ "Popup"+"')\""
						+">";
				returnStr = returnStr + "</td><td width='30'>" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "','" + inputFieldName2 + "','" + value + "')\" "
												 + disabledButton +">";
				returnStr = returnStr + "</td><td width='150'>" + "<INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " style='width:150' type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " id=" + doubleQuote + inputFieldName2 + doubleQuote
												+ " class=textboxReadOnly"
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " readOnly "
												+ "> </td></tr></table>";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
		}
	
	public static String displayPopUpTagScriptJSAction(
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
			String value2,
			String cacheName,
			String jsScript) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			String disabledButton = "";
			String[] scriptActions = scriptAction.split(" ");
			if(scriptActions != null){
				String str;
				for(int i=0; i<scriptActions.length; i++){
					str = scriptActions[i];
					if(str.equalsIgnoreCase(READ_ONLY) || str.equalsIgnoreCase(DISABLED)){
						disabledButton = "disabled";
						break;
					}
				}
			}
			
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " readOnly "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "')\" "
												 + ">";
				returnStr = returnStr + "&nbsp;" + "<INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " class="
												+ doubleQuote
												+ inputStyle
												+ doubleQuote
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " " + " readOnly "
												+ ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="+ doubleQuote + inputFieldName1 + doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ "onblur=\"javascript:setDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName1+ "Popup"+"')\">";
				returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " onClick=\"javascript:popupscript('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "','" + inputFieldName2 + "')\" "
												 + disabledButton +">";
				returnStr = returnStr + "&nbsp;" + "<INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " id="+ doubleQuote + inputFieldName2 + doubleQuote
												+ " class=textboxReadOnly"
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " " + " readOnly "
												+ " onChange=\"javascript:"+jsScript+"\" "
												+ ">";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
		}
	
	public static String displayPopUpTagScriptJSBusClassAction(
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
			String value2,
			String cacheName,
			String jsScript,
			String bus_class) {
			String returnStr = null;
			if (value == null) {
				value = "";
			}
			String disabledButton = "";
			String[] scriptActions = scriptAction.split(" ");
			if(scriptActions != null){
				String str;
				for(int i=0; i<scriptActions.length; i++){
					str = scriptActions[i];
					if(str.equalsIgnoreCase(READ_ONLY) || str.equalsIgnoreCase(DISABLED)){
						disabledButton = "disabled";
						break;
					}
				}
			}
			
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
				returnStr =
					"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
					+"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="+ doubleQuote + inputFieldName1 + doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " " + " readOnly "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ "> </td>";
				returnStr = returnStr + "<td width='30'> <INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " Disabled " + " onClick=\"javascript:popupscriptBusClass('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "','" + bus_class + "')\" "
												 + "> </td>";
				returnStr = returnStr + "<td width='150'> <INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " id="+ doubleQuote + inputFieldName2 + doubleQuote
												+ " style='width:150' class="
												+ doubleQuote
												+ inputStyle
												+ doubleQuote
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " " + " readOnly "
												+ "> </td></tr></table>";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
				returnStr =
					"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
					+"<INPUT size="
						+ doubleQuote
						+ inputSize1
						+ doubleQuote
						+ " type="
						+ doubleQuote
						+ " text "
						+ doubleQuote
						+ " name="
						+ doubleQuote
						+ inputFieldName1
						+ doubleQuote
						+ " id="+ doubleQuote + inputFieldName1 + doubleQuote
						+ " class="
						+ doubleQuote
						+ inputStyle
						+ doubleQuote
						+ " value="
						+ doubleQuote
						+ value
						+ doubleQuote
						+ " "
						+ scriptAction
						+ " maxlength = "
						+ doubleQuote
						+ maxLength
						+ doubleQuote
						+ "onblur=\"javascript:setDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName1+ "Popup"+"','"+bus_class+"')\"> </td>";
				returnStr = returnStr + "<td width='30'> <INPUT type=" 
												 + doubleQuote 
												 + "button" 
												 + doubleQuote
												 + " name="
												 + doubleQuote
												 + inputFieldName1 + "Popup"
												 + doubleQuote
												 + " value="
												 + doubleQuote
												 + popupLabel
												 + doubleQuote
												 + " class="
												 + doubleQuote
												 + popupStyle
												 + doubleQuote
												 + " onClick=\"javascript:popupscriptBusClass('" + popupAction + "','" + inputFieldName1 + "','" + cacheName + "','" + inputFieldName2 + "','" + bus_class + "')\" "
												 + disabledButton +"> </td>";
				returnStr = returnStr + "<td width='150'> <INPUT size="
												+ doubleQuote
												+ inputSize2
												+ doubleQuote
												+ " type="
												+ doubleQuote
												+ " text "
												+ doubleQuote
												+ " style='width:150' name="
												+ doubleQuote
												+ inputFieldName2
												+ doubleQuote
												+ " id="+ doubleQuote + inputFieldName2 + doubleQuote
												+ " class=textboxReadOnly"
												+ " value="
												+ doubleQuote
												+ value2
												+ doubleQuote
												+ " " + " readOnly "
												+ " onChange=\"javascript:"+jsScript+"\" "
												+ "> </td></tr></table>";
				//DebugUtil.println("displayInputTag>> " + returnStr);
				return returnStr;
			} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			} else
				return "";
		}
	
    public static String displayPopUpTagScriptAction(
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
		String returnStr = null;
		if (value == null) {
			value = "";
		}
		String disabledButton = "";
		if(READ_ONLY.equalsIgnoreCase(scriptAction) || DISABLED.equalsIgnoreCase(scriptAction)){
			disabledButton = "disabled";
		}
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
			returnStr =
				"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
				+"<INPUT size="
					+ doubleQuote
					+ inputSize1
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName1
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " " + " readOnly "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ "> </td><td width='30'>";
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
			returnStr = returnStr + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName1 + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " Disabled " + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "))\" "
											 + popupScriptAction + " > </td><td width='150'>";
			returnStr = returnStr + "<INPUT size="
											+ doubleQuote
											+ inputSize2
											+ doubleQuote
											+ " type="
											+ doubleQuote
											+ " text "
											+ doubleQuote
											+ " name="
											+ doubleQuote
											+ inputFieldName2
											+ doubleQuote
											+ doubleQuote
											+ " class="
											+ doubleQuote
											+ inputStyle
											+ doubleQuote
											+ " value="
											+ doubleQuote
											+ value2
											+ doubleQuote
											+ " " + " readOnly "
											+ "> </td></tr></table> ";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
			returnStr =
				"<table width='320' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='140'>"
				+"<INPUT size="
					+ doubleQuote
					+ inputSize1
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName1
					+ doubleQuote
					+ " id=" + doubleQuote + inputFieldName1 + doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					//+ "onblur=\"javascript:setDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName1+ "Popup"+"')\""
					+"> </td><td width='30'>";
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
			returnStr = returnStr + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName1 + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "),'" + cacheName + "','" + inputFieldName2 + "','" + inputFieldName1 + "','"+value+"')\" "
											 + popupScriptAction + " "+ disabledButton + "> </td><td width='150'>";
			returnStr = returnStr + "<INPUT size="
											+ doubleQuote
											+ inputSize2
											+ doubleQuote
											+ " style="+doubleQuote+"width:150"+doubleQuote+" type="
											+ doubleQuote
											+ " text "
											+ doubleQuote
											+ " name="
											+ doubleQuote
											+ inputFieldName2
											+ doubleQuote
											+ " id=" + doubleQuote + inputFieldName2 + doubleQuote
											+ " class=textboxReadOnly"
											+ " value="
											+ doubleQuote
											+ value2
											+ doubleQuote
											+ " " + " readOnly "
											+ "> </td></tr></table>";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		} else
			return "";
	}
    
    /**
     * @author septemwi
     * @deprecated
     * {@link HTMLRenderUtil#displayPopUpTagScriptAction(String, String, String, String, String, String, String, String, String, String, String[], String)}
     * */    
    public static String displayPopUpTagScriptAction(
		String value,
		String mode,
		String inputSize,
		String inputFieldName,
		String inputStyle,
		String scriptAction,
		String maxLength,
		String popupLabel,
		String popupStyle,
		String popupAction,
		String[] paramFields,
		String popupScriptAction) {
		String returnStr = null;
		if (value == null) {
			value = "";
		}
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
			returnStr =
				"<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>"
				+"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " " + " Disabled "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ "> </td>";
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
			returnStr = returnStr + "<td width='30'>" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " Disabled " + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "),'','','"+ inputFieldName +"')\" "
											 + popupScriptAction + " > </td></tr></table>";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
			returnStr =
				"<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>"
				+"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ "> </td>";
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
			returnStr = returnStr + "<td width='30'>" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "),'','','"+ inputFieldName +"')\" "
											 + popupScriptAction + " > </td></tr></table>";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		} else
			return "";
	}
    
    /**
     * @author septemwi
     * @deprecated
     * {@link HTMLRenderUtil#displayPopUpTagScriptAction(String, String, String, String, String, String, String, String, String, String, String[], String, String, String, String, String)}
     * */  
	public static String displayPopUpTagScriptAction(
		String value,
		String mode,
		String inputSize,
		String inputFieldName,
		String inputStyle,
		String scriptAction,
		String maxLength,
		String popupLabel,
		String popupStyle,
		String popupAction,
		String[] paramFields,
		String width,
		String height,
		String top,
		String left,
		String popupScriptAction) {
		String returnStr = null;
		if (value == null) {
			value = "";
		}
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
			returnStr =
				"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " "  + " Disabled "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ ">";
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
			returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " Disabled " + " onClick=\"javascript:popupscriptFieldsLoc('" + popupAction + "',new Array(" + strParam + ")," + width + "," + height + "," + top + "," + left + ")\" "
											 + popupScriptAction + " >";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
			returnStr =
				"<INPUT size="
					+ doubleQuote
					+ inputSize
					+ doubleQuote
					+ " type="
					+ doubleQuote
					+ " text "
					+ doubleQuote
					+ " name="
					+ doubleQuote
					+ inputFieldName
					+ doubleQuote
					+ " class="
					+ doubleQuote
					+ inputStyle
					+ doubleQuote
					+ " value="
					+ doubleQuote
					+ value
					+ doubleQuote
					+ " "
					+ scriptAction
					+ " maxlength = "
					+ doubleQuote
					+ maxLength
					+ doubleQuote
					+ ">";
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
			returnStr = returnStr + "&nbsp;" + "<INPUT type=" 
											 + doubleQuote 
											 + "button" 
											 + doubleQuote
											 + " name="
											 + doubleQuote
											 + inputFieldName + "Popup"
											 + doubleQuote
											 + " value="
											 + doubleQuote
											 + popupLabel
											 + doubleQuote
											 + " class="
											 + doubleQuote
											 + popupStyle
											 + doubleQuote
											 + " onClick=\"javascript:popupscriptFieldsLoc('" + popupAction + "',new Array(" + strParam + ")," + width + "," + height + "," + top + "," + left + ")\" "
											 + popupScriptAction + " >";
			//DebugUtil.println("displayInputTag>> " + returnStr);
			return returnStr;
		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		} else
			return "";
	}
	
  /**
   * @deprecated
   * @author septemwi
   * {@link HTMLRenderUtil#displaySelectTagScriptAction_ORIG(Vector, String, String, String, String)}
   * */
	@Deprecated	
	public static String displaySelectTagScriptAction_ORIG(
		Vector v,
		String selectedValue,
		String listName,
		String mode,
		String scriptAction){
		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
			return "";
		}		
		CacheDataInf obj = null;
		String value = null;
		String name = null;		
		if (mode == null || !mode.equals(DISPLAY_MODE_VIEW)) {
			String str =
				"<select name=\""
					+ listName
					+ "\" "
					+ " id=\""+listName+"\"" 
					+ " class=\"combobox\" "
					+ scriptAction
					+ "><option value=\"\">Please Select</option>";
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					obj = (CacheDataInf) v.get(i);
					value = obj.getCode();
					name = obj.getThDesc();	
					if ((value != null && value.equals(selectedValue))) {
						str =
							str
								+ "<option value = \""
								+ value
								+ "\" selected>"
								+ name
								+ "</option>";
					} else {
						str =
							str
								+ "<option value = \""
								+ value
								+ "\">"
								+ name
								+ "</option>";
					}
				}
			}
			return str + "</select>";
		}else{
			boolean isFind = false;
			String str =
				"<select name=\""
					+ listName
					+ "\" "
					+ " id=\""+listName+"\"" 
					+ " class=\"combobox\" "
					+ scriptAction
					+ ">";
			if (v != null){
				for(int i = 0; i < v.size(); i++){
					obj = (CacheDataInf) v.get(i); value = obj.getCode(); name = obj.getThDesc();		
					if ((value != null && value.equals(selectedValue))) {
						str =
							str
								+ "<option value = \""
								+ value
								+ "\" selected>"
								+ name
								+ "</option>";
						isFind =true;
						break;
					}
				}	
				if(!isFind) str = str+"<option value=\"\">Please Select</option>";
			}else{
				str = str+"<option value=\"\">Please Select</option>";
			}
			return str + "</select>";
		}
	}
	
	/**
	 * @deprecated
	 * {@link HTMLRenderUtil #displaySelectTagScriptActionBusClassFieldID(String, int, String, String, String, String)}
	 * @author septemwi
	 * */
	@Deprecated
	public static String displaySelectTagScriptActionBusClassFieldID(
			String busClass,
			int fieldID,
			String selectedValue,
			String listName,
			String mode,
			String scriptAction) {
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}
			
			ORIGCacheUtil origCache = new ORIGCacheUtil();
			
			Vector v = (Vector)origCache.getNaosCacheDataMs(busClass,fieldID);	
			
			StringBuilder str = new StringBuilder();
			
			CacheDataInf obj = null;
			String value = null;
			String name = null;
			
			if (mode == null || !mode.equals(DISPLAY_MODE_VIEW)) {
						str.append("<select name=\"");					
						str.append(listName);
						str.append( "\" ");
						str.append(" id=\""+listName+"\"");
						str.append(" class=\"combobox\" ");
						str.append(scriptAction);
						str.append("><option value=\"\">Please Select</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();	
						if ((value != null && value.equals(selectedValue))) {
							str.append( "<option value = \"");
							str.append(value);
							str.append("\" selected>");
							str.append(name);
							str.append("</option>");
						} else {
							str.append( "<option value = \"");
							str.append(value);
							str.append("\">");
							str.append(name);
							str.append("</option>");
						}
					}
				}
				return str.append("</select>").toString();
			} else {
				str.append("<select name=\"");
					str.append(listName);
					str.append("\" ");
					str.append(" id=\""+listName+"\"");
					str.append(" class=\"combobox\" ");
					str.append(scriptAction);
					str.append(" disabled><option value=\"\">Please Select</option>");
				if (v != null) {
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						
						if ((value != null && value.equals(selectedValue))) {
							str.append("<option value = \"");
							str.append(value);
							str.append("\" selected>");
							str.append(name);
							str.append("</option>");
						} else {
							str.append("<option value = \"");
							str.append(value);
							str.append("\">");
							str.append(name);
							str.append("</option>");;
						}
					}
				}
				return str.append("</select>").toString();
			}
	}
	
	public static String displaySelectTagScriptAction_ORIG_WithCheckStatus(
			Vector v,
			String selectedValue,
			String listName,
			String mode,
			String scriptAction) {
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}
			CacheDataInf obj = null;
			String value = null;
			String name = null;
			String status = null;
			String inAct = "I";
			if (mode == null || !mode.equals(DISPLAY_MODE_VIEW)) {
				String str =
					"<select name=\""
						+ listName
						+ "\" class=\"combobox\"   "
						+ scriptAction
						+ "><option value=\"\">Please Select</option>";
				if (v != null) {
					int vSize = v.size();
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						status = obj.getActiveStatus();
						//value = obj.serialize().substring(0,obj.serialize().indexOf("|"));
						//name = obj.serialize().substring(obj.serialize().indexOf("|"),obj.serialize().length()-1);		
						if ((value != null && value.equals(selectedValue))) {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\" selected>"
									+ name
									+ "</option>";
						} else if(value != null && !status.equals(inAct)){
							str =
								str
									+ "<option value = \""
									+ value
									+ "\">"
									+ name
									+ "</option>";
						}
					}
				}
				return str + "</select>";
			} else {
				String str =
					"<select name=\""
						+ listName
						+ "\" class=\"combobox\"   "
						+ scriptAction
						+ " disabled><option value=\"\">Please Select</option>";
				if (v != null) {
					int vSize = v.size();
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						//value = obj.serialize().substring(0,obj.serialize().indexOf("|"));
						//name = obj.serialize().substring(obj.serialize().indexOf("|"),obj.serialize().length()-1);		
						if ((value != null && value.equals(selectedValue))) {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\" selected>"
									+ name
									+ "</option>";
						} else {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\">"
									+ name
									+ "</option>";
						}
					}
				}
				return str + "</select>";
			}
		}
	
	public static String displaySelectTagScriptAction_SLA(
			Vector v,
			String selectedValue,
			String listName,
			String mode,
			String scriptAction) {
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}
			SLADataM obj = null;
			String value = null;
			String name = null;
			if (mode == null || !mode.equals(DISPLAY_MODE_VIEW)) {
				String str =
					"<select name=\""
						+ listName
						+ "\" class=\"combobox\"   "
						+ scriptAction
						+ "><option value=\"\">Please Select</option>";
				if (v != null) {
					int vSize = v.size();
					for (int i = 0; i < v.size(); i++) {
						obj = (SLADataM) v.get(i);
						value = obj.getQName();
						name = obj.getQName();
						//value = obj.serialize().substring(0,obj.serialize().indexOf("|"));
						//name = obj.serialize().substring(obj.serialize().indexOf("|"),obj.serialize().length()-1);		
						if ((value != null && value.equals(selectedValue))) {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\" selected>"
									+ name
									+ "</option>";
						} else {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\">"
									+ name
									+ "</option>";
						}
					}
				}
				return str + "</select>";
			} else {
				String str =
					"<select name=\""
						+ listName
						+ "\" class=\"combobox\"   "
						+ scriptAction
						+ " disabled><option value=\"\">Please Select</option>";
				if (v != null) {
					int vSize = v.size();
					for (int i = 0; i < v.size(); i++) {
						obj = (SLADataM) v.get(i);
						value = obj.getQName();
						name = obj.getQName();
						//value = obj.serialize().substring(0,obj.serialize().indexOf("|"));
						//name = obj.serialize().substring(obj.serialize().indexOf("|"),obj.serialize().length()-1);		
						if ((value != null && value.equals(selectedValue))) {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\" selected>"
									+ name
									+ "</option>";
						} else {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\">"
									+ name
									+ "</option>";
						}
					}
				}
				return str + "</select>";
			}
		}
	
	/***
	 * @param String number (1,200)
	 * @return BigDecimal number (1200)
	 */
	/***
	 * @deprecated
	 * {@link DataFormatUtility#StringToBigDecimal(String)}
	 */
	@Deprecated
	public static BigDecimal replaceCommaForBigDecimal(String num) {
		//logger.debug("num = "+num);
		if (null == num || num.equals("") ) {
			return new BigDecimal(0);
		}
		StringBuffer buf = new StringBuffer(num);
		int size = buf.length();
		for (int i = 0; i < buf.length(); i++) {
			char c = buf.charAt(i);
			//logger.debug("c"+c);
			Character cW = new Character(c);
			Character compare = new Character(',');
			if (cW.equals(compare)) {
				buf.replace(i, i + 1, "");
				//logger.debug("buf = "+buf);
			}
		}
		BigDecimal d = new BigDecimal(buf + "");
		return d;
	}
	
	/* Format Number to Comma Nunber( ex. 5,000.00, 2,514.25)
	*
	*/
	/**
	 * @deprecated 
	 * {@link DataFormatUtility#displayCommaNumber(BigDecimal)}
	 * */
	@Deprecated
	public static String displayCommaNumber(BigDecimal number) throws Exception {
	    if(number != null){
			String numberCommaFormat = "0.00";
			if (String.valueOf(number).trim().length() > 0) {
				String decFirst = "";
				String decLast = "";
				NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
				String commaFormat =
					format.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
				int indx = commaFormat.indexOf(".");
				if (indx != -1) {
					decFirst = commaFormat.substring(0, indx);
					decLast = commaFormat.substring(indx + 1);
					if (decLast.length() == 1) {
						decLast = decLast + "0";
					}
					numberCommaFormat = decFirst + "." + decLast;
				} else {
					numberCommaFormat = commaFormat + ".00";
				}
			} //if
			return numberCommaFormat;
	    }else{
	        return "0";
	    }
	}
	
	/** Remove double quot from String **/
    public static String replaceDoubleQuot(String txt){
		if(txt != null){
			//StringTokenizer token = new StringTokenizer(txt);
			String str = txt.replaceAll("\"","");
			//String str = txt.replaceAll("\"","\\\\\"");
			//logger.debug("replaceDoubleQuot-->" + str);
			return str;
		}else{
			return null;
		}
	}
    
    public static String getMandatory_ORIG(String customerType, Vector userRoles, String formID, String fieldName){
		String result = "";
		boolean flag = false;
				flag = MandatoryFieldCache.isMandatory_ORIG(customerType,userRoles,formID,fieldName);
				//logger.debug("flag is "+flag);
				if(flag){
					result = "*";
				}
		return result;
	}
    
    /***
	 * @param String to ("###,###.##")
	 * @param double number (685478)
	 * @return String number   (685,478.0) 
	 */
	public static String formatNumber(String to, BigDecimal number) {
		return new DecimalFormat(to).format(number);
	}

	
	/***
	 * @param BigDecimal bigDecZero  (0.0)
	 * @return String number   ("") 
	 */
	/**
	 * @author septemwi
	 * @deprecated
	 * {@link DataFormatUtility#displayBigDecimalZeroToEmpty(BigDecimal)}
	 */
	@Deprecated	
	public static String displayBigDecimalZeroToEmpty(BigDecimal bigDecZero) {
		try{
			String EmpString = "";
			if (String.valueOf(bigDecZero) == null || String.valueOf(bigDecZero).equalsIgnoreCase("null")) {
				EmpString = "";
			} else {
				EmpString = String.valueOf(displayCommaNumber(bigDecZero));
			}
			return EmpString;
		}catch(Exception e){
			logger.error("Error >>> ", e);
			return "";
		}
	}
	
	
	  /**
	   * @deprecated
	   * @author septemwi
	   * {@link HTMLRenderUtil#displaySelectTagScriptActionAndCode_ORIG(Vector, String, String, String, String)}
	   * */
	  @Deprecated
	public static String displaySelectTagScriptActionAndCode_ORIG(
			Vector v,
			String selectedValue,
			String listName,
			String mode,
			String scriptAction) {
			if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
				return "";
			}
			CacheDataInf obj = null;
			String value = null;
			String name = null;
			if (mode == null || !mode.equals(DISPLAY_MODE_VIEW)) {
				String str =
					"<select name=\""
						+ listName
						+ "\" class=\"combobox\"   "
						+ scriptAction
						+ "><option value=\"\">Please Select</option>";
				if (v != null) {
					int vSize = v.size();
					for (int i = 0; i < v.size(); i++) {
						obj = (CacheDataInf) v.get(i);
						value = obj.getCode();
						name = obj.getThDesc();
						//value = obj.serialize().substring(0,obj.serialize().indexOf("|"));
						//name = obj.serialize().substring(obj.serialize().indexOf("|"),obj.serialize().length()-1);		
						if ((value != null && value.equals(selectedValue))) {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\" selected>"
									+ name
									+ "</option>";
						} else {
							str =
								str
									+ "<option value = \""
									+ value
									+ "\">"
									+ value + " - "+name
									+ "</option>";
						}
					}
				}
				return str + "</select>";
			} else {
				if (selectedValue == null
					|| selectedValue.equals("null")
					|| selectedValue.equals("")) {
					return "";
				} else {
					if (v != null) {
						for (int i = 0; i < v.size(); i++) {
							obj = (CacheDataInf) v.get(i);
							if (obj.getCode() != null
								&& selectedValue != null
								&& obj.getCode().trim().equals(
									selectedValue.trim())) {
								name = obj.getThDesc();
								break;
							}
						}
					}
					return name;
				}
			}
		}
	
	public static String displayDashForEmptyString(String value){
		if(ORIGUtility.isEmptyString(value)){
			return "-";
		}else{
			return value;
		}
	}
	public static Date parseEngToThaiDate( Date sDate) {
		if (null != sDate) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(sDate);
			int year = ca.get(Calendar.YEAR) + 543;
			ca.set(Calendar.YEAR, year);

			sDate = new java.sql.Date(ca.getTime().getTime());
			return sDate;
		} else {

			return null;
		}
	}
	public static  Date parseThaiToEngDate( Date sDate) {
		if (null != sDate) {
			Calendar ca = Calendar.getInstance();
			ca.setTime(sDate);
			int year = ca.get(Calendar.YEAR) - 543;
			ca.set(Calendar.YEAR, year);

			sDate = new java.sql.Date(ca.getTime().getTime());
			return sDate;

		} else {

			return null;
		}

	}
	public static String dateTimetoStringForThai(Date dateValue) {
		if (dateValue != null) {
			DecimalFormat dformat = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			c.setTime(dateValue);
			StringBuffer date = new StringBuffer();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append(
				"/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append((c.get(Calendar.YEAR))+543);
			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(
				":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "";
		}
	}
	
	public static String stringDateTimeValueListForThai(String strDateTime) {
		if (strDateTime != null) {
			String[] arrDateTime = strDateTime.split(" ");
			String strDate = arrDateTime[0];
			String strTime = arrDateTime[1];
			
			String[] arrDate = strDate.split("/");
			String[] arrTime = strTime.split(":");
			
			Calendar c = Calendar.getInstance();
			c.set(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]), Integer.parseInt(arrTime[0]), Integer.parseInt(arrTime[1]), Integer.parseInt(arrTime[2]));
			
			DecimalFormat dformat = new DecimalFormat("00");
			
			StringBuffer date = new StringBuffer();
			date.append(dformat.format(c.get(Calendar.DAY_OF_MONTH))).append(
				"/");
			date.append(dformat.format(c.get(Calendar.MONTH) + 1)).append("/");
			date.append((c.get(Calendar.YEAR))+543);
			date.append(" ");
			date.append(dformat.format(c.get(Calendar.HOUR_OF_DAY))).append(
				":");
			date.append(dformat.format(c.get(Calendar.MINUTE))).append(":");
			date.append(dformat.format(c.get(Calendar.SECOND)));
			return date.toString();
		} else {
			return "";
		}
	}
	
	
	/**
	 * @deprecated
	 * @author septemwi
	 * {@link HTMLRenderUtil#displayCheckBoxTagDesc(String, String, String, String, String, String)}
	 * */
	public static String displayCheckBoxTagDesc(String value,String checkedValue, String mode, String inputFieldName, String action, String text) {
		
		String returnStr = ""; 

		if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)){

			returnStr =  "<input type=" + doubleQuote + "checkbox" + doubleQuote ;
			if (inputFieldName != null) {
			returnStr += " name=" + doubleQuote + inputFieldName + doubleQuote;
			}
			if (value != null) {
				returnStr += " value=" + doubleQuote + displayHTML(value) + doubleQuote;
			}
			if ((value != null && checkedValue != null &&  !checkedValue.equals("")  && value.trim().equals(checkedValue.trim()) )) {
				returnStr += " checked=" + doubleQuote + "checked" + doubleQuote;
			}
				returnStr += " " + action + " disabled >"+ text;
				
		}else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)){
				returnStr =  "<input type=" + doubleQuote + "checkbox" + doubleQuote ;
			if (inputFieldName != null) {
			returnStr += " name=" + doubleQuote + inputFieldName + doubleQuote;
			}
			if (value != null) {
				returnStr += " value=" + doubleQuote + displayHTML(value) + doubleQuote;
			}
			if ((value != null && checkedValue != null &&  !checkedValue.equals("")  && value.trim().equals(checkedValue.trim()) )) {
				returnStr += " checked=" + doubleQuote + "checked" + doubleQuote;
			}
				returnStr += " " + action + " >"+ text;
		}

		//DebugUtil.println("displayCheckBoxTag>> " + returnStr);
		return returnStr;
	}
	
	public static String forHTMLTag(String aTagFragment){
	    final StringBuffer result = new StringBuffer();

		if(aTagFragment==null || aTagFragment.equals("null")){
			return "";
		}else{
		    aTagFragment=aTagFragment.trim();
		    final StringCharacterIterator iterator = new StringCharacterIterator(aTagFragment);
		    char character =  iterator.current();
		    while (character != CharacterIterator.DONE ){
		      if (character == '<') {
		        result.append("&lt;");
		      }
		      else if (character == '>') {
		        result.append("&gt;");
		      }
		      else if (character == '\"') {
		        result.append("&quot;");
		      }
		      else if (character == '\'') {
		        result.append("&#039;");
		      }
		      else if (character == '\\') {
		         result.append("&#092;");
		      }
		      else if (character == '&') {
		         result.append("&amp;");
		      }
		      else {
		        //the char is not a special one
		        //add it to the result as is
		        result.append(character);
		      }
		      character = iterator.next();
		    }
		    return result.toString();
		}
	  }
	  
	  /**
	   * @deprecated
	   * @author septemwi
	   * {@link HTMLRenderUtil#displayText(String)}
	   * */
	  @Deprecated	
	  public static String displayText(String text){
		String result = "";
		if(text!=null && !text.equals("")){
			StringBuffer buf = new StringBuffer(text);
			int size = buf.length();
			for (int i = buf.length() - 1; i >=0; i--) {
				char c = buf.charAt(i);
				Character cW = new Character(c);
				Character enterStr = new Character((char)10);
				Character enterStr2 = new Character((char)13);
				Character doubleQuote = new Character((char)34);
				Character singleQuote = new Character((char)39);
				//System.out.println(">>> c :"+ c +" : "+(int)c);
				if (cW.equals(enterStr)) {
					buf.replace(i, i + 1, "");
				}else if (cW.equals(enterStr2)) {
					buf.replace(i, i + 1, "<br>");
				}else if (cW.equals(singleQuote)) {
					buf.replace(i, i + 1, "\\"+"\'");
				}else if (cW.equals(doubleQuote)) {
					buf.replace(i, i + 1, "&quot;");
			      }
				/*else if (cW.equals(doubleQuote)) {
					buf.replace(i, i + 1, "\\"+"\"");
				}*/
			}
			result = buf + "";
		}
		logger.debug(">>> result :"+result);
		return result;
	}
	  /*
	     * Format Number to Comma Nunber( ex. 5,000.00, 2,514.25) if number equal 0
	     * display empty string
	     *  
	     */
	    public static String displayCommaNumberZeroToEmpty(BigDecimal number)
	            throws Exception {
	        if (number != null) {
	            if (new BigDecimal(0).compareTo(number) != 0) {
	                String numberCommaFormat = "0.00";
	                if (String.valueOf(number).trim().length() > 0) {
	                    String decFirst = "";
	                    String decLast = "";
	                    NumberFormat format = NumberFormat
	                            .getInstance(Locale.ENGLISH);
	                    String commaFormat = format.format(number.setScale(2,
	                            BigDecimal.ROUND_HALF_UP));
	                    int indx = commaFormat.indexOf(".");
	                    if (indx != -1) {
	                        decFirst = commaFormat.substring(0, indx);
	                        decLast = commaFormat.substring(indx + 1);
	                        if (decLast.length() == 1) {
	                            decLast = decLast + "0";
	                        }
	                        numberCommaFormat = decFirst + "." + decLast;
	                    } else {
	                        numberCommaFormat = commaFormat + ".00";
	                    }

	                } //if
	                return numberCommaFormat;
	            } else {
	                return "";
	            }
	        } else {
	            return "";
	        }
	    }
	    /* remove leading whitespace */
	    public static String ltrim(String source) {
	        if(source!=null){ 
	            source = source.replaceAll("^\\s+", "");
	         } 
	        return source;
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {
	        if(source!=null){ 
	            source = source.replaceAll("\\s+$", "") ;
	         } 
	        return source;
	    }

	    /* replace multiple whitespaces between words with single blank */
	    public static String itrim(String source) {
	        if(source!=null){ 
	            source = source.replaceAll("\\b\\s{2,}\\b", " ");
	         } 
	        return source;
	    }

	    /* remove all superfluous whitespaces in source string */
	    public static String trim(String source) {
	        return itrim(ltrim(rtrim(source)));
	    }
	    /* remove leading and  trailing  whitespace */
	    public static String lrtrim(String source){
	        return ltrim(rtrim(source));
	    } 
	    public static String displayInputTextAreaTag_Orig(
	    		String value,
	    		String inputFieldName,
	    		String row,
	    		String column,
	    		String mode,
	    		String javascript) {
	    		String returnStr = null;
	    		if (mode == null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
	    			returnStr =
	    				" <textarea name="
	    					+ inputFieldName
	    					+ " rows="
	    					+ row
	    					+ " cols="
	    					+ column
	    					+ "  " + javascript  + " "
	    					+ " class=\"TextAreaBox\">"
	    					+ value
	    					+ "</textarea>";
	    			return returnStr;
	    		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
	    		    int colomLength=25;	    		    
	    		    try {
                        colomLength=Integer.parseInt(column);
                    } catch (NumberFormatException e) {                        
                       // e.printStackTrace();
                    }
                    StringBuffer warpStr=new StringBuffer();
                    int valueLength=value.length();
                    try {
                        for(int i=0;i<valueLength;i=i+colomLength){
                          //logger.debug("-----------------");
                         // logger.debug(" i "+i);
                          //logger.debug(" colum "+colomLength);
                          //logger.debug(" value length "+valueLength);
                          //logger.debug("-----------------");
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
                    } catch (RuntimeException e1) {                       
                        e1.printStackTrace();
                        warpStr=new StringBuffer();
                        warpStr.append(value);
                    }
	    			return warpStr.toString();
	    		} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
	    			return "";
	    		} else
	    			return "";
	    	}
	    /* Format Number to Comma Nunber( ex. 5,000 , 2,514 )
		*
		*/
		public static String displayCommaNumberInterger(BigDecimal number) throws Exception {
		    if(number != null){
				 String numberCommaFormat = "0";
				if (String.valueOf(number).trim().length() > 0) {
					String decFirst = "";
					String decLast = "";
					NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
					String commaFormat =
						format.format(number.setScale(0, BigDecimal.ROUND_HALF_UP));
					int indx = commaFormat.indexOf(".");
					/*if (indx != -1) {
						decFirst = commaFormat.substring(0, indx);
						decLast = commaFormat.substring(indx + 1);
						if (decLast.length() == 1) {
							decLast = decLast + "0";
						}
						numberCommaFormat = decFirst + "." + decLast;
					} else {*/
						numberCommaFormat = commaFormat ;//+ ".00";
					//}
				} //if
				return numberCommaFormat;
		    }else{
		        return "0";
		    }
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
				String returnStr = null;
				if (value == null) {
					value = "";
				}
				String disabledButton = "";
				if(READ_ONLY.equalsIgnoreCase(scriptAction) || DISABLED.equalsIgnoreCase(scriptAction)){
					disabledButton = "disabled";
				}
				if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_VIEW)) {
					returnStr =
						"<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>"+
						"<INPUT size="
							+ doubleQuote
							+ inputSize1
							+ doubleQuote
							+ " type="
							+ doubleQuote
							+ " text "
							+ doubleQuote
							+ " name="
							+ doubleQuote
							+ inputFieldName1
							+ doubleQuote
							+ " class="
							+ doubleQuote
							+ inputStyle
							+ doubleQuote
							+ " value="
							+ doubleQuote
							+ value
							+ doubleQuote
							+ " " + " readOnly "
							+ scriptAction
							+ " maxlength = "
							+ doubleQuote
							+ maxLength
							+ doubleQuote
							+ "></td><td width='30'>";
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
					returnStr = returnStr + "<INPUT type=" 
													 + doubleQuote 
													 + "button" 
													 + doubleQuote
													 + " name="
													 + doubleQuote
													 + inputFieldName1 + "Popup"
													 + doubleQuote
													 + " value="
													 + doubleQuote
													 + popupLabel
													 + doubleQuote
													 + " class="
													 + doubleQuote
													 + popupStyle
													 + doubleQuote
													 + " Disabled " + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "))\" "
													 + popupScriptAction + " > </td><td width='150'>";
					returnStr = returnStr + "<INPUT size="
													+ doubleQuote
													+ inputSize2
													+ doubleQuote
													+ " type="
													+ doubleQuote
													+ " text "
													+ doubleQuote
													+ " name="
													+ doubleQuote
													+ inputFieldName2
													+ doubleQuote
													+ doubleQuote
													+ " class="
													+ doubleQuote
													+ inputStyle
													+ doubleQuote
													+ " value="
													+ doubleQuote
													+ value2
													+ doubleQuote
													+ " " + " readOnly "
													+ "></td></tr></table> ";
					//DebugUtil.println("displayInputTag>> " + returnStr);
					return returnStr;
				} else if (mode != null || mode.equalsIgnoreCase(DISPLAY_MODE_EDIT)) {
					returnStr =
						"<table width='0' border='0' align='left' cellpadding='0' cellspacing='0'> <tr><td width='0'>"+
						"<INPUT size="
							+ doubleQuote
							+ inputSize1
							+ doubleQuote
							+ " type="
							+ doubleQuote
							+ " text "
							+ doubleQuote
							+ " name="
							+ doubleQuote
							+ inputFieldName1
							+ doubleQuote
							+ " id=" + doubleQuote + inputFieldName1 + doubleQuote
							+ " class="
							+ doubleQuote
							+ inputStyle
							+ doubleQuote
							+ " value="
							+ doubleQuote
							+ value
							+ doubleQuote
							+ " "
							+ scriptAction
							+ " maxlength = "
							+ doubleQuote
							+ maxLength
							+ doubleQuote
							+ "onblur=\"javascript:setDescription(this.value,'"+inputFieldName2+"','"+cacheName+"','"+inputFieldName1+ "Popup"+"')\""
							+"></td><td width='30'>";
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
					returnStr = returnStr + "<INPUT type=" 
													 + doubleQuote 
													 + "button" 
													 + doubleQuote
													 + " name="
													 + doubleQuote
													 + inputFieldName1 + "Popup"
													 + doubleQuote
													 + " value="
													 + doubleQuote
													 + popupLabel
													 + doubleQuote
													 + " class="
													 + doubleQuote
													 + popupStyle
													 + doubleQuote
													 + " onClick=\"javascript:popupscriptFields('" + popupAction + "',new Array(" + strParam + "),'" + cacheName + "','" + inputFieldName2 + "','" + inputFieldName1 + "','"+value+"')\" "
													 + popupScriptAction + " "+ disabledButton + "> </td><td width='150'>";
					returnStr = returnStr + "<INPUT size="
													+ doubleQuote
													+ inputSize2
													+ doubleQuote
													+ " style="+doubleQuote+"width:150"+doubleQuote+" type="
													+ doubleQuote
													+ " text "
													+ doubleQuote
													+ " name="
													+ doubleQuote
													+ inputFieldName2
													+ doubleQuote
													+ " id=" + doubleQuote + inputFieldName2 + doubleQuote
													+ " class=textboxReadOnly"
													+ " value="
													+ doubleQuote
													+ value2
													+ doubleQuote
													+ " " + " readOnly "
													+ "> </td></tr></table>";
					//DebugUtil.println("displayInputTag>> " + returnStr);
					return returnStr;
				} else if (mode != null && mode.equalsIgnoreCase(DISPLAY_MODE_HIDEN)) {
					return "";
				} else
					return "";
			}
}
