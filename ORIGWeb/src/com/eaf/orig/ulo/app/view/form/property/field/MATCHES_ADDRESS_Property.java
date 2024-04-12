package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;

public class MATCHES_ADDRESS_Property extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(MATCHES_ADDRESS_Property.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object objectForm) {
		logger.debug("MATCHES_ADDRESS_Property...");
		AddressDataM currentAddress = (AddressDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(!AddressUtil.ValidateMatchesAddress(currentAddress)){
			
			if((SystemConstant.getConstant("ADDRESS_TYPE_WORK")).equals(currentAddress.getAddressType())){
				formError.error(MessageErrorUtil.getText(request, "ERROR_OFFICE_ADDRESS"));
			}else if((SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT")).equals(currentAddress.getAddressType())){
				formError.error(MessageErrorUtil.getText(request, "ERROR_DOCUMENT_ADDRESS"));
			}else{
				formError.error(MessageErrorUtil.getText(request, "ERROR_CURRENT_ADDRESS"));
			}
			
		}

		return formError.getFormError();
	}
}
