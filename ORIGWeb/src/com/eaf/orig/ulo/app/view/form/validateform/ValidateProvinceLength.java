package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;

public class ValidateProvinceLength extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateProvinceLength.class);
	int SEARCH_ADDRESS_PROVINCE_MAX_LENGTH = SystemConstant.getIntConstant("SEARCH_ADDRESS_PROVINCE_MAX_LENGTH");
	int SEARCH_ADDRESS_ZIPCODE_MAX_LENGTH = SystemConstant.getIntConstant("SEARCH_ADDRESS_ZIPCODE_MAX_LENGTH");
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		logger.debug("ValidateProvinceLength.validateForm");
//		var $data = 'PROVINCE='+SEARCH_PROVINCE.length;SEARCH_FIELDS_REQUIRED_ADDRESS_4
//		$data += 'ZIPCODE='+SEARCH_ZIPCODE.length;SEARCH_FIELDS_REQUIRED_ADDRESS
		String PROVINCE = request.getParameter("SEARCH_PROVINCE");
		String ZIPCODE = request.getParameter("SEARCH_ZIPCODE");
		
		logger.debug("PROVINCE >>"+PROVINCE);
		logger.debug("ZIPCODE >>"+ZIPCODE);
		int lengthProvince = FormatUtil.getLength(PROVINCE);
		int lengthZipcode = FormatUtil.getLength(ZIPCODE);
		FormErrorUtil formError = new FormErrorUtil();	
		if(lengthProvince<SEARCH_ADDRESS_PROVINCE_MAX_LENGTH && lengthProvince!=0){
		formError.error("SEARCH_PROVINCE", MessageErrorUtil.getText(request, "ERROR_LENGTH_OF_FIELD_SEARCH_PROVINCE"));
		}
		if(lengthZipcode<SEARCH_ADDRESS_ZIPCODE_MAX_LENGTH && lengthZipcode!=0){
		formError.error("SEARCH_ZIPCODE", MessageErrorUtil.getText(request, "ERROR_LENGTH_OF_FIELD_SEARCH_ZIPCODE"));
		}
		return formError.getFormError();
		
	}
}