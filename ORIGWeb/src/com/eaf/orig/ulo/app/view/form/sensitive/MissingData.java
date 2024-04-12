package com.eaf.orig.ulo.app.view.form.sensitive;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
//import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class MissingData extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(MissingData.class);	
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {			
		CompareDataM compareData = (CompareDataM)objectElement;
		logger.debug("MissingData : "+compareData);
		StringBuilder HTML = new StringBuilder();
		String errMessage = "";
		if(CompareDataM.RefLevel.PERSONAL.equals(compareData.getRefLevel())) {
//			String personalType = CompareSensitiveFieldUtil.getPersonalTypeByRefId(compareData);
//			if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
//				errMessage = MessageErrorUtil.getText(request, "ERROR_NO_OF_SUP_NOT_EQUAL");
//			}
		}else if(CompareDataM.RefLevel.APPLICATION.equals(compareData.getRefLevel())) {
			errMessage = MessageErrorUtil.getText(request, "ERROR_NO_OF_CARD_NOT_EQUAL");
		}else if(CompareDataM.RefLevel.CARD.equals(compareData.getRefLevel())) {
			errMessage = MessageErrorUtil.getText(request, "ERROR_NO_OF_CARD_NOT_EQUAL");
		}else if(CompareDataM.RefLevel.PAYMENT_METHOD.equals(compareData.getRefLevel())) {
			errMessage = MessageErrorUtil.getText(request, "ERROR_NO_OF_PAYMENT_NOT_EQUAL");
		}
		if(!Util.empty(errMessage)){
			HTML.append("<div class='col-sm-12'>")
			.append("<div class='alert alert-warning alert-dismissible fade in' role='alert'>")
			.append("<div>"+errMessage+"</div>")
			.append("</div></div>")
			.append("<div class='clearfix'></div>");		
			logger.info(HTML.toString());
		}
		return HTML.toString();
	}
}
