package com.eaf.orig.ulo.app.view.form.manual;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.google.gson.Gson;

public class SearchAddressZipCodeForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(SearchAddressZipCodeForm.class);
	String ADDRESS_FORMAT_AUTO = SystemConstant.getConstant("ADDRESS_FORMAT_AUTO");
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	public static String ZIP_CODE_SESSION="ZIP_CODE_SESSION";
	@Override
	public Object getObjectForm() {
		logger.debug("getFormObject()..");
		request.getSession().setAttribute(SearchFormHandler.SearchForm, new SearchFormHandler(request));
		String ADDRESS_ELEMENT_ID = getRequestData("ADDRESS_ELEMENT_ID");
		String ADDRESS_TYPE =  getRequestData("ADDRESS_TYPE");
		String PERSONAL_TYPE =  getRequestData("PERSONAL_TYPE");
		String PERSONAL_SEQ =  getRequestData("PERSONAL_SEQ");
		String FUNCTION_POPUP =  getRequestData("FUNCTION_POPUP");
		if(Util.empty(ADDRESS_ELEMENT_ID)){
			ADDRESS_ELEMENT_ID =GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK);
		}
		HashMap<String , String> hData = new HashMap<String , String> ();
		hData.put("ADDRESS_TYPE", ADDRESS_TYPE);
		hData.put("PERSONAL_TYPE", PERSONAL_TYPE);
		hData.put("PERSONAL_SEQ", PERSONAL_SEQ);
		hData.put("FUNCTION_POPUP", FUNCTION_POPUP);
		hData.put("ADDRESS_ELEMENT_ID", ADDRESS_ELEMENT_ID);
		logger.debug("ADDRESS_ELEMENT_ID>>"+ADDRESS_ELEMENT_ID);
		logger.debug("ADDRESS_TYPE>>"+ADDRESS_TYPE);
		logger.debug("PERSONAL_TYPE>>"+PERSONAL_TYPE);
		logger.debug("PERSONAL_SEQ>>"+PERSONAL_SEQ);		
		
		request.getSession().setAttribute(ZIP_CODE_SESSION, hData);
		return null;
	}
	
	@Override
	public String processForm() {
		logger.debug("SearchAddressForm.processForm()..");		
		String ADDRESS_TYPE =  getRequestData("ADDRESS_TYPE");
		String SUFFIX_ADDRESS_TYPE ="";
		if(!Util.empty(ADDRESS_TYPE)){
			SUFFIX_ADDRESS_TYPE="_"+ADDRESS_TYPE;
		}
		JSONUtil json = new JSONUtil();		
		String SEARCH_ADDRESS_SELECT = request.getParameter("SEARCH_ADDRESS_SELECT");
		
		logger.debug("SEARCH_ADDRESS_SELECT VALUE>>"+SEARCH_ADDRESS_SELECT);
		AddressZipCode addressZipcode = new Gson().fromJson(SEARCH_ADDRESS_SELECT, AddressZipCode.class);
		
		
		
		//POPUP_TAMBOL_CARDLINK
		String POPUP_TAMBOL_CARDLINK = SystemConstant.getConstant("POPUP_TAMBOL_CARDLINK");	
		String FUNCTION = getRequestData("FUNCTION_POPUP");
		String IS_REKEY = getRequestData("IS_REKEY");
		if(MConstant.FLAG.YES.equals(IS_REKEY)){
			logger.debug("==set address of rekey==");
			String PERSONAL_TYPE = getRequestData("PERSONAL_TYPE");
			String PERSONAL_SEQ = getRequestData("PERSONAL_SEQ");
			String ADDRESS_ID = getRequestData("ADDRESS_ID");
			String prefixAddress = CompareSensitiveFieldUtil.getPrefixAddress(PERSONAL_TYPE, PERSONAL_SEQ, ADDRESS_TYPE);
			json.put(prefixAddress+"_TAMBOL",addressZipcode.getTambol());
			json.put(prefixAddress+"_AMPHUR",addressZipcode.getAmphur());
			json.put(prefixAddress+"_PROVINCE",addressZipcode.getProvince());
			json.put(prefixAddress+"_ZIPCODE",addressZipcode.getZipcode());
			
			if(ADDRESS_TYPE_DOCUMENT.contains(ADDRESS_TYPE)){
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("DOCUMENT_ZIPCODE", ADDRESS_ID),addressZipcode.getZipcode());
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("DOCUMENT_PROVINCE", ADDRESS_ID),addressZipcode.getProvince());
			}else if(ADDRESS_TYPE_CURRENT.contains(ADDRESS_TYPE)){
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("CURRENT_ZIPCODE", ADDRESS_ID),addressZipcode.getZipcode());
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("CURRENT_PROVINCE", ADDRESS_ID),addressZipcode.getProvince());
			}else if(ADDRESS_TYPE_WORK.contains(ADDRESS_TYPE)){
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("COMPANY_ZIPCODE", ADDRESS_ID),addressZipcode.getZipcode());
				json.put(CompareSensitiveFieldUtil.getAddressRekeyFieldName("COMPANY_PROVINCE", ADDRESS_ID),addressZipcode.getProvince());				
			}

		}else{
			String CardLink = "";
			if(POPUP_TAMBOL_CARDLINK.equals(FUNCTION)){
				CardLink = "CL_";
			}
				try {
					json.put(CardLink+"TAMBOL"+SUFFIX_ADDRESS_TYPE,addressZipcode.getTambol());
					json.put(CardLink+"AMPHUR"+SUFFIX_ADDRESS_TYPE,addressZipcode.getAmphur());
					json.put(CardLink+"PROVINCE"+SUFFIX_ADDRESS_TYPE,addressZipcode.getProvince());
					json.put(CardLink+"ZIPCODE"+SUFFIX_ADDRESS_TYPE,addressZipcode.getZipcode());
					json.put(CardLink+"ADDRESS_FORMAT"+SUFFIX_ADDRESS_TYPE,ADDRESS_FORMAT_AUTO);
					if(!Util.empty(CardLink)){
						json.put("CARD_LINK","Y");
					}
					
				} catch (Exception e) {
					logger.fatal("ERROR",e);
					e.printStackTrace();
				}
		}
		return json.getJSON();
	}
	
	public class AddressZipCode {
		private String zipcodeId;
		private String tambol;
		private String amphur;
		private String province;
		private String zipcode;
			 
		public String getZipcodeId() {
			return zipcodeId;
		}


		public void setZipcodeId(String zipcodeId) {
			this.zipcodeId = zipcodeId;
		}

		public String getTambol() {
			return tambol;
		}

		public void setTambol(String tambol) {
			this.tambol = tambol;
		}

		public String getAmphur() {
			return amphur;
		}

		public void setAmphur(String amphur) {
			this.amphur = amphur;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

	}
}