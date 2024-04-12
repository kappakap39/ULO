package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.ERROR;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.PLOrigUtility;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.XrulesResponseDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class DCBundleAction implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());	
	private PLORIGApplicationManager manager = null;
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		UserDetailM	userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		String MESSAGE = "Claim";
		String appRecID = request.getParameter("appRecId");
		
		logger.debug("appRecID.. "+appRecID);
		
		PLApplicationDataM applicationM =  null;
		try{			
			 manager = PLORIGEJBService.getPLORIGApplicationManager();
			 
			 applicationM = manager.loadPLApplicationDataM(appRecID);		
				
			if(WorkflowConstant.JobState.DC_BUNDLE_CQ.equals(applicationM.getJobState())){	
				PULLJOB(applicationM, userM, request);					        
		        MESSAGE = "Claim";
			}else{
				UtilityDAO utilDAO = new UtilityDAOImpl();	
				MESSAGE = createMessage(request,utilDAO.getApplicationNo(appRecID),ERROR.MSG_WORKING_APPLICATION);
			}
	        	        
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			if(ERROR.MSG_CANNOT_CLAIM.equals(e.getMessage())){
				UtilityDAO utilDAO = new UtilityDAOImpl();	
				MESSAGE = createMessage(request,utilDAO.getApplicationNo(appRecID),ERROR.MSG_CANNOT_CLAIM);
			}else{
//				#septem comment
//				MESSAGE =  ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR");	
				MESSAGE = Message.error();
			}
		}
		return MESSAGE;
	}
	
	public void PULLJOB(PLApplicationDataM applicationM ,UserDetailM userM ,HttpServletRequest request) throws Exception{
		
		String creditCardResult = request.getParameter("creditCardResult");
		String creditCardAppScore = request.getParameter("creditCardAppScore");		
		
		logger.debug("creditCardResult.. "+creditCardResult);
		logger.debug("creditCardAppScore.. "+creditCardAppScore);
		
		PLBundleCCDataM  bundleccM = applicationM.getBundleCCM();
		if(null == bundleccM){
			bundleccM = new PLBundleCCDataM();
			applicationM.setBundleCCM(bundleccM);
		}
		
		bundleccM.setAppRecId(applicationM.getAppRecordID());
		bundleccM.setCreateBy(userM.getUserName());
		bundleccM.setUpdateBy(userM.getUserName());
		bundleccM.setCreditCardResult(creditCardResult);
		bundleccM.setCreditCardAppScore(creditCardAppScore);
		
		applicationM.setAppDecision(OrigConstant.Action.PULL);		
		applicationM.setOwner(userM.getUserName());			

		ORIGXRulesTool origXrulesTool = new ORIGXRulesTool();
		XrulesRequestDataM xrulesRequest = origXrulesTool.MapXrulesRequestDataM(applicationM
													,PLXrulesConstant.ModuleService.BUNDLE_CREDIT_CARD_RESULT, userM);
		ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			XrulesResponseDataM response = xRulesService.ILOGServiceModule(xrulesRequest);	
        
        String businessClassID = response.getResultDesc();
        logger.debug("businessClassID >> "+businessClassID);
        
        if(!OrigUtil.isEmptyString(businessClassID)){
        	applicationM.setBusinessClassId(businessClassID);
			String saleType = PLOrigUtility.getSaleTypeFormBussClass(businessClassID);
			applicationM.setSaleType(saleType);
        }
        
		manager.pullJobBundling(applicationM, userM);
	}
	
	public String createMessage(HttpServletRequest request,String appNo,String message){
		StringBuilder STR = new StringBuilder("");
			STR.append(ErrorUtil.getShortErrorMessage(request, "APP_NO"));
			STR.append(" ");
			if(null != appNo){
				STR.append(appNo);
				STR.append(" ");
			}			
			STR.append(ErrorUtil.getShortErrorMessage(request,message));
		return STR.toString();
	}
}
