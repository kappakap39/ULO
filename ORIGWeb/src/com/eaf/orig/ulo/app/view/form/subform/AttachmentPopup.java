package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;

@SuppressWarnings("serial")
public class AttachmentPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(AttachmentPopup.class);
	String FILE_TYPE_OTHER = SystemConstant.getConstant("TITLE_OTHER");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm){
		String FILE_TYPE =    request.getParameter("FILE_TYPE");
		String OTHER_FIEL_TYPE = request.getParameter("OTHER_FILE_TYPE");
		String FILE_NAME = request.getParameter("FILE_NAME");		
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug(">>>FILE_TYPE>>>"+FILE_TYPE);
		logger.debug(">>>FILE_NAME>>>"+FILE_NAME);				
		logger.debug(">>>OTHER_FIEL_TYPE>>>"+OTHER_FIEL_TYPE);				
		formError.mandatoryElement("FILE_TYPE", "FILE_TYPE", FILE_TYPE, request);
		if(Util.empty(FILE_NAME)){
			formError.error("FILE_NAME",MessageErrorUtil.getText(request,"ERROR_ATTACHMENT_FILE"));
		}		
		if(FILE_TYPE_OTHER.equals(FILE_TYPE)){
			formError.mandatoryElement("OTHER_FILE_TYPE", "OTHER_FILE_TYPE", OTHER_FIEL_TYPE, request);
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {		
		return false;
	}

}
