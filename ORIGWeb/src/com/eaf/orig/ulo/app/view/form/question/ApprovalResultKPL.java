package com.eaf.orig.ulo.app.view.form.question;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ApprovalResultKPL  extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ApprovalResultKPL.class);
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String RECOMMEND_DECISION_APPROVED = SystemConstant.getConstant("RECOMMEND_DECISION_APPROVED");
	String RECOMMEND_DECISION_REJECTED = SystemConstant.getConstant("RECOMMEND_DECISION_REJECTED");
	String RECOMMEND_DECISION_CANCEL = SystemConstant.getConstant("RECOMMEND_DECISION_CANCEL");	
	String CONTACT_RESULT_APPROVE = SystemConstant.getConstant("CONTACT_RESULT_APPROVE");
	String CONTACT_RESULT_REQUEST_DOC = SystemConstant.getConstant("CONTACT_RESULT_REQUEST_DOC");
	String CONTACT_RESULT_CANCEL = SystemConstant.getConstant("CONTACT_RESULT_CANCEL");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/ApprovalResultKPL.jsp";
	}
	
	@Override
	public String getDisplayType(){
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		UserDetailM	userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = 	applicationGroup.filterFinalAppDecisionLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) {
				String APPROVAL_RESULT = request.getParameter("APPROVAL_RESULT_"+application.getProduct()+"_"+application.getApplicationRecordId());
				if(!Util.empty(APPROVAL_RESULT)){
					if(CONTACT_RESULT_APPROVE.equals(APPROVAL_RESULT)){
						application.setFinalAppDecision(RECOMMEND_DECISION_APPROVED);
					}else if(CONTACT_RESULT_REQUEST_DOC.equals(APPROVAL_RESULT)){
						application.setFinalAppDecision(RECOMMEND_DECISION_APPROVED);
					}else if(CONTACT_RESULT_CANCEL.equals(APPROVAL_RESULT)){
						application.setFinalAppDecision(RECOMMEND_DECISION_CANCEL);
					}
					logger.debug("Result Product KPL"+application.getFinalAppDecision());
					logger.debug("APPROVAL_RESULT>>"+APPROVAL_RESULT);
					application.setFinalAppDecisionBy(userM.getUserName());
					application.setFinalAppDecisionDate(ApplicationDate.getDate());
					application.setDiffRequestResult(APPROVAL_RESULT);
				}
			}
		}
		return null;
	}
}
