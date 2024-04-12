package com.eaf.orig.ulo.app.view.form.popup;

//import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ReasonDataM;
//import com.eaf.core.ulo.common.util.SessionControl;
//import com.eaf.core.ulo.common.util.Util;
//import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;
//import com.eaf.orig.ulo.model.app.ReasonLogDataM;
//import com.eaf.orig.ulo.model.control.FlowControlDataM;

@SuppressWarnings("serial")
public class CancelReasonPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CancelReasonPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
//		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
		String REASON_CODE = request.getParameter("REASON_CODE");
		String REASON_OTH_DESC = request.getParameter("REASON_OTH_DESC");
		String REMARK = request.getParameter("REMARK");
			
		logger.debug("REASON_TYPE_CANCEL : "+REASON_TYPE_CANCEL);
		logger.debug("REASON_CODE : "+REASON_CODE);
		logger.debug("REASON_CODE : "+REASON_CODE);
		logger.debug("REASON_CODE : "+REASON_CODE);
		
//		#rawi comment change logic cancel application!
//		ArrayList<ApplicationDataM> applicationInfos = (ArrayList<ApplicationDataM>)appForm;
//		if(!Util.empty(applicationInfos)){
//			for(ApplicationDataM applicationInfo : applicationInfos){
//				applicationInfo.setFinalAppDecision(REASON_TYPE_CANCEL);
//				
//				ArrayList<ReasonDataM> reasons = applicationInfo.getReasons();
//				if(Util.empty(reasons)){
//					reasons  = new ArrayList<ReasonDataM>();
//					applicationInfo.setReasons(reasons);
//				}
//				
//				ReasonDataM reasonM = new ReasonDataM() ;				 
//				reasonM.setReasonCode(REASON_CODE);
//				reasonM.setReasonOthDesc(REASON_OTH_DESC);
//				reasonM.setRemark(REMARK);
//				reasonM.setReasonType(REASON_TYPE_CANCEL);
//				reasonM.setRole(flowControl.getRole());
//				reasonM.setCreateBy(userM.getUserName());
//				reasonM.setUpdateBy(userM.getUserName());
//				reasons.add(reasonM);
//				
//				ArrayList<ReasonLogDataM> reasonLogs = applicationInfo.getReasonLogs();
//				if(Util.empty(reasonLogs)){
//					reasonLogs  = new ArrayList<ReasonLogDataM>();
//					applicationInfo.setReasonLogs(reasonLogs);
//				}
//				ReasonLogDataM reasonDataLog = new ReasonLogDataM();	 
//				reasonDataLog.setReasonType(reasonM.getReasonType());
//				reasonDataLog.setReasonCode(reasonM.getReasonCode()); 
//				reasonDataLog.setReasonOthDesc(reasonM.getReasonOthDesc());
//				reasonDataLog.setRole(reasonM.getRole());
//				reasonDataLog.setRemark(reasonM.getRemark());
//				reasonDataLog.setCreateBy(userM.getUserName());
//				reasonLogs.add(reasonDataLog);
//			}
//		}
		
		ReasonDataM reasonM = (ReasonDataM)appForm;
		reasonM.setReasonCode(REASON_CODE);
		reasonM.setReasonOthDesc(REASON_OTH_DESC);
		reasonM.setRemark(REMARK);
		reasonM.setReasonType(REASON_TYPE_CANCEL);
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {		 
		FormErrorUtil formError = new FormErrorUtil();		
		String REASON_CODE = request.getParameter("REASON_CODE");
		String REASON_OTH_DESC = request.getParameter("REASON_OTH_DESC");
		String REMARK = request.getParameter("REMARK");
//		formError.mandatoryElement("REASON_CODE", "REASON", REASON_CODE, request);	
		if(Util.empty(REASON_CODE)){
			formError.error("REASON_CODE", MessageErrorUtil.getText("ERROR_REASON_CANCLE_APPLICATION"));
		}
		if (SystemConstant.getConstant("REASON_CODE_OTH").equals(REASON_CODE)) {
//			formError.mandatoryElement("REASON_CODE", "REASON",REASON_OTH_DESC, request);
			if(Util.empty(REASON_OTH_DESC)){
				formError.error("REASON_CODE", MessageErrorUtil.getText("ERROR_REASON_CANCLE_APPLICATION"));
			}
		}	 
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){		
		return false;
	}
}