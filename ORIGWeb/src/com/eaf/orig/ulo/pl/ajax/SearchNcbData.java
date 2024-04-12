package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.ncb.model.output.NCBOutputDataM;
//import com.eaf.ncb.service.NCBEJBService;
//import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.ejb.ORIGApplicationManager;
//import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.DisplayMatrixTool;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.dao.util.XRulesRemoteDAOUtilManager;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SearchNcbData implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(SearchNcbData.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
		logger.debug("personM.getPersonalID >> "+personM.getPersonalID());
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
		
		NCBOutputDataM ncbM = xrulesVerM.getNcbOutPutM();
		if(null == ncbM){
			ncbM = new NCBOutputDataM();
			xrulesVerM.setNcbOutPutM(ncbM);
		}
		
		try{
			JsonObjectUtil jObj = new JsonObjectUtil();
			if(!OrigUtil.isEmptyString(xrulesVerM.getNCBTrackingCode())){
											
				XRulesRemoteDAOUtilManager xrulesBean = PLORIGEJBService.getXRulesDAOUtilManager();
				PLXRulesVerificationResultDataM tmpXruleVerM = xrulesBean.loadPLXRulesVerificationResults(personM.getPersonalID());
				
//				NCBServiceManager ncbBean = NCBEJBService.getNCBServiceManager();
//				NCBOutputDataM ncbOutPutM = ncbBean.getNCBOutput(tmpXruleVerM.getNCBTrackingCode());	
//					xrulesVerM.setNcbOutPutM(ncbOutPutM);
				
				if(!OrigUtil.isEmptyString(tmpXruleVerM.getNCBCode())
						&& !PLXrulesConstant.ResultCode.CODE_NA.equals(tmpXruleVerM.getNCBCode())
							&& !NCBConstant.ncbResultCode.WAITING_NCB_DATA.equals(tmpXruleVerM.getNCBCode())){
					
//					this.MapResponseNcb(applicationM, ncbOutPutM, tmpXruleVerM);
					
					PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
					if(null == debtDataM){
						debtDataM = new PLXRulesDebtBdDataM();
						xrulesVerM.setXRulesDebtBdDataM(debtDataM);
					}
					
					//Set ncb reason
					if(OrigConstant.SummaryOverideRuleCode.FAIL.equals(xrulesVerM.getSummaryOverideRuleCode())){
						UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
						PLORIGApplicationManager manager = PLORIGEJBService.getPLORIGApplicationManager();
						applicationM.setExecuteReasonVect(manager.getPolicyRejectReasonVt(applicationM, userM));
					}
					jObj.CreateJsonObject("result_1040", xrulesVerM.getNCBResult());
					jObj.CreateJsonObject("code_1040", xrulesVerM.getNCBCode());
					DisplayMatrixTool matrixTool = new DisplayMatrixTool();
					jObj.CreateJsonObject("ncb-color",matrixTool.NcbStyleTextBox(xrulesVerM.getNCBCode()));
					jObj.CreateJsonObject("ncb-decision",xrulesVerM.getSummaryOverideRuleCode());
					
					logger.debug("getConsumerLoanDebtTotal().."+debtDataM.getConsumerLoanDebtTotal());
					logger.debug("getCommercialLoanDebtTotal().."+debtDataM.getCommercialLoanDebtTotal());
					
					jObj.CreateJsonObject("ncb-consumer",DataFormatUtility.displayCommaNumber(debtDataM.getConsumerLoanDebtTotal(),false));
					jObj.CreateJsonObject("ncb-commercial",DataFormatUtility.displayCommaNumber(debtDataM.getCommercialLoanDebtTotal(),false));
					
				}				
			}
			return jObj.returnJson();
		}catch (Exception e) {
			logger.fatal("Exception ",e);
		}
		return null;
	}
	
	public void MapResponseNcb(PLApplicationDataM applicationM ,NCBOutputDataM ncbOutPutM , PLXRulesVerificationResultDataM tmpXruleVerM){
		PLPersonalInfoDataM personM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
		PLXRulesVerificationResultDataM xrulesVerM = personM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personM.setXrulesVerification(xrulesVerM);
		}
			xrulesVerM.setNcbOutPutM(ncbOutPutM);
			xrulesVerM.setNCBResult(tmpXruleVerM.getNCBResult());
			xrulesVerM.setNCBCode(tmpXruleVerM.getNCBCode());
			
//			#septem comment set to NcbFicoCode
//			xrulesVerM.setFicoCode(tmpXruleVerM.getFicoCode());
//			xrulesVerM.setFicoResult(tmpXruleVerM.getFicoResult());	
			
			xrulesVerM.setNcbFicoCode(tmpXruleVerM.getNcbFicoCode());
			xrulesVerM.setNcbFicoResult(tmpXruleVerM.getNcbFicoResult());	
			xrulesVerM.setNcbRestructureCode(tmpXruleVerM.getNcbRestructureCode());
			xrulesVerM.setNcbFailHistoryCode(tmpXruleVerM.getNcbFailHistoryCode());
			xrulesVerM.setNcbFailHistoryReason(tmpXruleVerM.getNcbFailHistoryReason());
			xrulesVerM.setNcbOverdueHistoryCode(tmpXruleVerM.getNcbOverdueHistoryCode());
			xrulesVerM.setNcbOverdueHistoryReason(tmpXruleVerM.getNcbOverdueHistoryReason());
			xrulesVerM.setXRulesDebtBdDataM(tmpXruleVerM.getXRulesDebtBdDataM());
			xrulesVerM.setVXRulesNCBDataM(tmpXruleVerM.getVXRulesNCBDataM());
			xrulesVerM.setXrulesFICODataM(tmpXruleVerM.getXrulesFICODataM());
			xrulesVerM.setvXrulesNCBRestructureDebtVect(tmpXruleVerM.getvXrulesNCBRestructureDebtVect());
			xrulesVerM.setvXrulesNCBOverdueHistoryVect(tmpXruleVerM.getvXrulesNCBOverdueHistoryVect());
			xrulesVerM.setvXrulesNCBVerifyVect(tmpXruleVerM.getvXrulesNCBVerifyVect());
			xrulesVerM.setVNCBAdjust(tmpXruleVerM.getVNCBAdjust());
			xrulesVerM.setSummaryOverideRuleCode(tmpXruleVerM.getSummaryOverideRuleCode());
			xrulesVerM.setvXRulesPolicyRulesDataM(tmpXruleVerM.getvXRulesPolicyRulesDataM());
	}
	
}
