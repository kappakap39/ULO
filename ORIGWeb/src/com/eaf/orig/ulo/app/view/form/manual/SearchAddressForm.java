package com.eaf.orig.ulo.app.view.form.manual;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.util.JSONUtil;

public class SearchAddressForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(SearchAddressForm.class);
	String ADDRESS_FORMAT_AUTO = SystemConstant.getConstant("ADDRESS_FORMAT_AUTO");
	@Override
	public Object getObjectForm() {	
		logger.debug("getFormObject()..");
		
		return null;
	}
	@Override
	public String processForm() {
		logger.debug("SearchAddressForm.processForm()..");		
		String functionId = getRequestData("functionId");
		JSONUtil json = new JSONUtil();		
		String[] ADDRESS_SELECT = request.getParameterValues("SEARCH_ADDRESS_SELECT");
		//POPUP_TAMBOL_CARDLINK
		String POPUP_TAMBOL_CARDLINK = SystemConstant.getConstant("POPUP_TAMBOL_CARDLINK");	
		String FUNCTION = getRequestData("FUNCTION_POPUP");
//		logger.debug("SEARCH_ADDRESS_SELECT Size >> "+ADDRESS_SELECT.length);	
		logger.debug("SEARCH_ADDRESS_SELECT >> "+ADDRESS_SELECT[0]);	
		String CardLink = "";
		if(POPUP_TAMBOL_CARDLINK.equals(FUNCTION)){
			CardLink = "CL_";
		}
		
		for(String ele : ADDRESS_SELECT){
			try {
				JSONObject jOb = new JSONObject(ele);

				json.put(CardLink+"TAMBOL",jOb.getString("tumbol"));
				json.put(CardLink+"AMPHUR",jOb.getString("amphur"));
				json.put(CardLink+"PROVINCE",jOb.getString("province"));
				json.put(CardLink+"ZIPCODE",jOb.getString("zipCode"));
				json.put(CardLink+"ADDRESS_FORMAT",ADDRESS_FORMAT_AUTO);
				if(!Util.empty(CardLink)){
					json.put("CARD_LINK","Y");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		return json.getJSON();
	}
}
