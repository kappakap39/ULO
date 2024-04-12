package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ReprocessRemarkDataM;
@SuppressWarnings("serial")
public class SubmitApplicationReprocessPopup extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(SubmitApplicationReprocessPopup.class);
	@Override
	public void setProperties(HttpServletRequest request,Object appForm){
		ReprocessRemarkDataM reprocessRemarkData = (ReprocessRemarkDataM)appForm;
		String reprocessRemark = request.getParameter("REPROCESS_REMARK");
		logger.debug("reprocessRemark : "+reprocessRemark);
		reprocessRemarkData.setRemark(reprocessRemark);
	}
	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request,Object appForm){
		FormErrorUtil formError = new FormErrorUtil();
		String reprocessRemark = request.getParameter("REPROCESS_REMARK");
		logger.debug("reprocessRemark : "+reprocessRemark);
		if(Util.empty(reprocessRemark)){
			formError.error("REPROCESS_REMARK",MessageErrorUtil.getText(request, "ERROR_MUST_ENTER_REPROCESS_REMARKS"));
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
}