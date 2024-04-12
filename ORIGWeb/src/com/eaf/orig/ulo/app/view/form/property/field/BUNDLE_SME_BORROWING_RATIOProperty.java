
package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;

public class BUNDLE_SME_BORROWING_RATIOProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BUNDLE_SME_BORROWING_RATIOProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("BUNDLE_SME_BORROWING_RATIOProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		BundleSMEDataM bundleM = (BundleSMEDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		String borrowing_type = SystemConstant.getConstant("LOAN_TYPE_COBORROWER_JOINT");
		if(!Util.empty(bundleM) && borrowing_type.equals(bundleM.getBorrowingType())){
			if(Util.empty(bundleM.getIndividualRatio()) && Util.empty(bundleM.getCorporateRatio())) {
				formError.error("INDIVIDUAL_RATIO",PREFIX_ERROR+LabelUtil.getText(request,"INDIVIDUAL_RATIO"));
				formError.error("CORPORATE_RATIO",PREFIX_ERROR+LabelUtil.getText(request,"CORPORATE_RATIO"));
			}
		}
		return formError.getFormError();
	}
}
