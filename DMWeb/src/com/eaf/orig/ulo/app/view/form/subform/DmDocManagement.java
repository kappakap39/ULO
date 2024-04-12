package com.eaf.orig.ulo.app.view.form.subform;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;
import com.eaf.orig.ulo.model.dm.HistoryDataM;

@SuppressWarnings("serial")
public class DmDocManagement extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(DmDocManagement.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		DocumentManagementDataM dmManageDataM = (DocumentManagementDataM)appForm;
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String DM_DOC_MANAGEMENT_ACTION = request.getParameter("DM_DOC_MANAGEMENT");
		String DM_REQUESTED_USER = request.getParameter("DM_REQUESTED_USER");
		String DM_OFFICE_PHONE_NO = request.getParameter("DM_OFFICE_PHONE_NO");
		String DM_MOBILE_NO = request.getParameter("DM_MOBILE_NO");
		String DM_ACTION_DATE = request.getParameter("DM_ACTION_DATE");
		String DM_DUE_DATE = request.getParameter("DM_DUE_DATE");
		String DM_REMARK = request.getParameter("DM_REMARK");
		String DM_DEPARTMENT = request.getParameter("DM_DEPARTMENT");
		String DM_REQUESTED_USER_DESC = "";
		
		if(null != request.getParameter("DM_REQUESTED_USER_DESC")){
			String[] DM_REQUESTED_USER_DESC_ARR = request.getParameter("DM_REQUESTED_USER_DESC").split(":");
			if(!Util.empty(DM_REQUESTED_USER_DESC_ARR) && DM_REQUESTED_USER_DESC_ARR.length>1){
				DM_REQUESTED_USER_DESC = DM_REQUESTED_USER_DESC_ARR[1].trim();
			}
		}
		HistoryDataM history = dmManageDataM.getHistory();		
		if(Util.empty(history)){
			history = new HistoryDataM();
			dmManageDataM.setHistory(history);
			history.setCreateBy(userM.getUserName());
			history.setCreateDate(ApplicationDate.getTimestamp());
		}
		
		history.setAction(DM_DOC_MANAGEMENT_ACTION);
		history.setRequestedUser(DM_REQUESTED_USER);
		history.setPhoneNo(DM_OFFICE_PHONE_NO);
		history.setMobileNo(DM_MOBILE_NO);
		history.setActionDate(FormatUtil.toDate(DM_ACTION_DATE,FormatUtil.TH));
		history.setDueDate(FormatUtil.toDate(DM_DUE_DATE,FormatUtil.TH));
		history.setRemark(DM_REMARK);
		history.setRequesterDepartment(DM_DEPARTMENT);
		history.setRequesterName(DM_REQUESTED_USER_DESC);
		logger.debug("====DM_DOC_MANAGEMENT_ACTION=="+DM_DOC_MANAGEMENT_ACTION);
		
		if(MConstant.DM_MANAGEMENT_ACTION.BORROW_ACTION.equals(DM_DOC_MANAGEMENT_ACTION)){
			dmManageDataM.setStatus(MConstant.DM_STATUS.BORROWED);
			history.setStatus(MConstant.DM_TRANSACTION_STATUS.BORROWED);	
			
		}else if(MConstant.DM_MANAGEMENT_ACTION.WITHDRAW_ACTION.equals(DM_DOC_MANAGEMENT_ACTION)){
			dmManageDataM.setStatus(MConstant.DM_STATUS.WITHDRAWN);
			history.setStatus(MConstant.DM_TRANSACTION_STATUS.WITHDRAWN);	
			
		}else{
			dmManageDataM.setStatus(MConstant.DM_STATUS.AVAILABLE);
			history.setStatus(MConstant.DM_TRANSACTION_STATUS.AVAILABLE);	
		}
		dmManageDataM.setUpdateBy(userM.getUserName());
		dmManageDataM.setUpdateDate(ApplicationDate.getTimestamp());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		DocumentManagementDataM dmManageDataM = (DocumentManagementDataM)appForm;
		HistoryDataM history = dmManageDataM.getHistory();
		
		formError.mandatoryElement("DM_DOC_MANAGEMENT","DM_DOC_MANAGEMENT",history.getAction(),request);
		formError.mandatoryElement("DM_REQUESTED_USER","DM_REQUESTED_USER",history.getRequestedUser(),request);		
		formError.mandatoryElement("DM_ACTION_DATE","DM_ACTION_DATE",history.getActionDate(),request);		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
