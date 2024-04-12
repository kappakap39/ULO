package com.eaf.orig.ulo.app.view.form.popup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ReasonDataM;

@SuppressWarnings("serial")
public class CallCenterCancelReasonPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CallCenterCancelReasonPopup.class);
	String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("CallCenterCancelReasonPopup.....");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ReasonDataM reasonM = (ReasonDataM)appForm;
		reasonM.setCreateBy(userM.getUserName());
		reasonM.setUpdateBy(userM.getUserName());
		reasonM.setReasonCode(request.getParameter("REASON_CODE"));
		reasonM.setReasonOthDesc(request.getParameter("REASON_OTH_DESC"));
		reasonM.setReasonType(REASON_TYPE_CANCEL);
		reasonM.setRemark(request.getParameter("REMARK"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subFormId = "POPUP_CALLCENTER_CANCEL_REASON_SUBFORM";
		
		formError.mandatory(subFormId, "REASON", appForm, request);
		ReasonDataM reasonM = (ReasonDataM)appForm;
		logger.debug("REASON_CODE : "+reasonM.getReasonCode());
		logger.debug("REASON_OTH_DESC : "+reasonM.getReasonOthDesc());
		
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){		
		return false;
	}
}
