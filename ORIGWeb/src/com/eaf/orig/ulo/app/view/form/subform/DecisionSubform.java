package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DecisionSubform  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(DecisionSubform.class);	
 	String  DECISION_FINAL_DECISION_REJECT = SystemConstant.getConstant("DECISION_FINAL_DECISION_REJECT");
 	String DECISION_ACTION_REJECT = SystemConstant.getConstant("DECISION_ACTION_REJECT");
	String DECISION_REJECT = SystemConstant.getConstant("FICO_DECISION_REJECTED");		
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String CA_REMARK = request.getParameter("CA_REMARK");
		applicationGroup.setCaRemark(CA_REMARK);
		
		ArrayList<String> products = applicationGroup.getProducts();
		
		
		if(!Util.empty(products)){
			for(String product:products){
				ArrayList<ApplicationDataM> getApplications = applicationGroup.filterApplicationLifeCycle(product);
				if(Util.empty(getApplications)){
					getApplications = new ArrayList<ApplicationDataM>();
				}
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DECISION,"DECISION_"+product+"_SUBFORM");
				element.processElement(request, getApplications);
			}
		}

				//#Move logic to processActionCA
//				boolean isRejectAllDecision = isRejectDecision(applicationGroup);
//				logger.debug("isRejectAllDecision>>"+isRejectAllDecision);
	}

	
	private boolean isRejectDecision(ApplicationGroupDataM applicationGroup){
		boolean rejectAction = false;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		int applicationSize = applications.size();
		int rejectSize = 0;
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(DECISION_FINAL_DECISION_REJECT.equals(application.getFinalAppDecision())){
					rejectSize++;
				}
			}
		}
		logger.debug(">>>applicationSize>>>"+applicationSize);
		logger.debug(">>>rejectSize>>>"+rejectSize);
		if(applicationSize == rejectSize){
			rejectAction = true;
			applicationGroup.setLastDecision(DECISION_ACTION_REJECT);
			applicationGroup.setLastDecisionDate(ApplicationDate.getDate());
			applicationGroup.setDecisionAction(DECISION_REJECT);
		}
		return rejectAction;		
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();		
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
			ArrayList<String> products = applicationGroup.getProducts();		
			if(!Util.empty(products)){
				for(String product:products){
					ArrayList<ApplicationDataM> getApplications = applicationGroup.filterApplicationLifeCycle(product);
					ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DECISION,"DECISION_"+product+"_SUBFORM");
					HashMap<String,Object>  hValidate =element.validateElement(request, getApplications);
					if(!Util.empty(hValidate)){
						formError.addAll(hValidate);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);		 
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
