package com.eaf.orig.ulo.pl.app.view.form.subform;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ava.bpm.model.response.WorkflowResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.ncb.util.NCBConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
//import com.eaf.orig.ulo.pl.app.rule.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.ORIGMapModelModule;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.cache.data.MatrixCache;
import com.eaf.xrules.cache.model.MTDisplayDetailDataM;
import com.eaf.xrules.cache.model.MTDisplayGroupDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
//import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
//import com.eaf.xrules.ulo.pl.tool.cache.XrulesCacheTool;
import com.orig.bpm.proxy.BpmProxyService;
import com.orig.bpm.ulo.model.WorkflowDataM;
import com.orig.bpm.ulo.proxy.BpmWorkflow;

public class VerificationSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(this.getClass());
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		String role = userM.getCurrentRole();
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if (null == applicationM) {
			applicationM = new PLApplicationDataM();
			formHandler.setAppForm(applicationM);
		}
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVer = personalM.getXrulesVerification();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVer);
		}
		PLXRulesDebtBdDataM xrulesDebtM = xrulesVer.getXRulesDebtBdDataM();
		if (null == xrulesDebtM) {
			xrulesDebtM = new PLXRulesDebtBdDataM();
			xrulesVer.setXRulesDebtBdDataM(xrulesDebtM);
		}
		
//		ORIGXRulesTool tool = new ORIGXRulesTool();
//		XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(appM, null, null);
//		XrulesCacheTool cache = new XrulesCacheTool();
//		Vector<MTDisplayGroupDataM> vect = cache.GetMatrixDisplay(requestM);
				
		Vector<MTDisplayGroupDataM> vect = MatrixCache.getMatrixDisplay().get(applicationM.getMatrixServiceID());
		
		WorkflowResponse wfResp = new WorkflowResponse();
		
		String decision = request.getParameter("decision_option");
		
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())){		
			if(!OrigUtil.isEmptyString(decision)){
				try{
					OrigObjectDAO origObjDAO = new OrigObjectDAO();
					Connection conn = origObjDAO.getConnection(OrigServiceLocator.WORKFLOW_DB);
					WorkflowDataM workFlowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
					workFlowM.setAction(decision);
					BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
					wfResp = bpmWorkflow.GetOfficialAction(workFlowM, conn);	
				}catch (Exception e) {
					logger.fatal("Exception >> ", e);
				}
			}
		}
		ORIGLogic origLogic = new ORIGLogic();
		if (!OrigUtil.isEmptyVector(vect)){
			for (MTDisplayGroupDataM groupM:vect){
				if (!OrigUtil.isEmptyVector(groupM.getDisplayVect())){						
					for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()){
						switch (detailM.getServiceID()) {
							case PLXrulesConstant.ModuleService.INCOME_DEBT:
								BigDecimal salary = DataFormatUtility.StringToBigDecimal(request.getParameter("salary"));
								BigDecimal cheuqeReturn = DataFormatUtility.StringToBigDecimal(request.getParameter("cheuqe-return"));
								BigDecimal loanIncome = DataFormatUtility.StringToBigDecimal(request.getParameter("loan-income"));
								BigDecimal otherIncome = DataFormatUtility.StringToBigDecimal(request.getParameter("other-income"));
								String otherIncomeRemark = request.getParameter("other-income-remark");
								BigDecimal totalIncome = DataFormatUtility.StringToBigDecimal(request.getParameter("total-income"));

//								BigDecimal ncbDebt = DataFormatUtility.StringToBigDecimal(request.getParameter("ncb-debt"));
								BigDecimal otherDebt = DataFormatUtility.StringToBigDecimal(request.getParameter("other-debt"));
								String otherDebtRemark = request.getParameter("other-debt-remark");
								BigDecimal totalDebt = DataFormatUtility.StringToBigDecimal(request.getParameter("total-debt"));

								xrulesDebtM.setSalary(salary);
								xrulesDebtM.setCheuqeReturn(cheuqeReturn);
								xrulesDebtM.setLoanIncome(loanIncome);
								xrulesDebtM.setOtherIncome(otherIncome);
								xrulesDebtM.setOtherIncomeRemark(otherIncomeRemark);
								xrulesDebtM.setTotalIncome(totalIncome);

//								xrulesDebtM.setNcbDebt(ncbDebt);
								xrulesDebtM.setOtherDebt(otherDebt);
								xrulesDebtM.setOtherDebtRemark(otherDebtRemark);
								xrulesDebtM.setTotalDebt(totalDebt);
								break;
							case PLXrulesConstant.ModuleService.TOT:
								String totCode = request.getParameter("result_1026");
								String totRemark = request.getParameter("remark_1026");
								xrulesVer.setWebTotCode(totCode);
								xrulesVer.setWebTotRemark(totRemark);
								break;
							case PLXrulesConstant.ModuleService.SSO:
								String ssoCode = request.getParameter("result_1027");
								String ssoRemark = request.getParameter("remark_1027");
								xrulesVer.setWebSsoCode(ssoCode);
								xrulesVer.setWebSsoRemark(ssoRemark);
								break;
							case PLXrulesConstant.ModuleService.RD:
								String rdCode = request.getParameter("result_1028");
								String rdRemark = request.getParameter("remark_1028");
								xrulesVer.setWebRdCode(rdCode);
								xrulesVer.setWebRdRemark(rdRemark);
								break;
							case PLXrulesConstant.ModuleService.DSS:
								String dssCode = request.getParameter("result_1029");
								String dssRemark = request.getParameter("remark_1029");
								xrulesVer.setWebDssKbankCode(dssCode);
								xrulesVer.setWebDssKbankRemark(dssRemark);
								break;
							case PLXrulesConstant.ModuleService.RETROSPECITVE_POSITIVE:
								String result_1030 = request.getParameter("result_1030");
								xrulesVer.setFinancialStatementLastCode(result_1030);
								break;
							case PLXrulesConstant.ModuleService.CIRCULATION:
								String result_1031 = request.getParameter("result_1031");
								xrulesVer.setSaleVolumeCode(result_1031);
								break;
							case PLXrulesConstant.ModuleService.DELAY_DOC:
								String result_1035 = request.getParameter("result_1035");
//								#septemwi comment change to FuNoTimeOutFlag
//								appM.setExceptionDoc(result_1035);
								applicationM.setFuNoTimeOutFlag(result_1035);
								break;
							case PLXrulesConstant.ModuleService.REQUEST_NCB_FICO:
								if (origLogic.LogicRequestCBDecision(wfResp, decision)
										|| origLogic.LogicSendToCB(role,decision,xrulesVer.getNCBCode())){
									xrulesVer.setNCBRequester(userM.getUserName());
								}
								break;
							case PLXrulesConstant.ModuleService.DOC_LIST:
								if(OrigConstant.wfProcessState.SEND_BACK.equals(decision)&& OrigConstant.ROLE_DC.equals(role)){
									if(!OrigUtil.isEmptyString(applicationM.getTempDocList())
											&& applicationM.getTempDocList().indexOf(OrigConstant.ROLE_DE)>0){
										applicationM.setDocListResult(applicationM.getTempDocList());
										applicationM.setDocListResultCode(applicationM.getTempDocListCode());
									}
								}
								break;
							default : break;
						}
					}
				}
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	private boolean result = false;
	@Override
	public boolean validateSubForm(HttpServletRequest request) {

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	
		logger.debug("VerificationSubForm SaveType >>> " + formHandler.getSaveType());

		PLApplicationDataM applicationM = formHandler.getAppForm();
		if (null == applicationM) applicationM = new PLApplicationDataM();

		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		
		String currentRole = userM.getCurrentRole();
		
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
//		ORIGXRulesTool tool = new ORIGXRulesTool();
//		XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);
		
		String decision = request.getParameter("decision_option");		
		
		//for de sup, dc sup, cai sup
		/* comment #Vikrom 20130111
		String decisionRefNo = request.getParameter("decision_ref_no");
		String ca_decisionRefNo = request.getParameter("ca_decision_refference");
		if(!OrigUtil.isEmptyString(decisionRefNo) || !OrigUtil.isEmptyString(ca_decisionRefNo)){
			if(OrigConstant.ROLE_DE_SUP.equals(currentRole) || OrigConstant.ROLE_DC_SUP.equals(currentRole)
					|| OrigConstant.roleJobState.CA_I_SUP.equals(appM.getJobState())){
				return false;
			}
		}*/
		
		if(OrigConstant.SUBMIT_TYPE_SEND.equals(formHandler.getSaveType())){			
			WorkflowResponse wfResp = new WorkflowResponse();
			try{
				OrigObjectDAO origObjDAO = new OrigObjectDAO();
				Connection conn = origObjDAO.getConnection(OrigServiceLocator.WORKFLOW_DB);
				WorkflowDataM workFlowM = ORIGMapModelModule.mappingModelWorkFlow(applicationM, userM);
				workFlowM.setAction(decision);
				BpmWorkflow bpmWorkflow = BpmProxyService.getBpmWorkflow();
				wfResp = bpmWorkflow.GetOfficialAction(workFlowM, conn);	
			}catch (Exception e) {
				logger.fatal("Exception >> ", e);
			}
			
//			XrulesCacheTool cache = new XrulesCacheTool();
//			Vector<MTDisplayGroupDataM> vect = cache.GetMatrixDisplay(requestM);
			
			Vector<MTDisplayGroupDataM> vect = MatrixCache.getMatrixDisplay().get(applicationM.getMatrixServiceID());
			
			if (!OrigUtil.isEmptyVector(vect)){
				for (MTDisplayGroupDataM groupM:vect){
					if (!OrigUtil.isEmptyVector(groupM.getDisplayVect())){						
						if (OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT == formHandler.getMandatoryType()){
							this.doMANDATORY_TYPE_SUMMIT(groupM, wfResp, userM, request);
						}else if(OrigConstant.Mandatory.MANDATORY_TYPE_DRAFT == formHandler.getMandatoryType()){
							this.doMANDATORY_TYPE_DRAFT(groupM, wfResp, userM, request);
						}else if(OrigConstant.Mandatory.MANDATORY_TYPE_SEND_BACK == formHandler.getMandatoryType()){
							this.doMANDATORY_TYPE_SEND_BACK(groupM, wfResp, userM, request);
						}
					}
				}
			}
		}
		return result;
	}
	private void doMANDATORY_TYPE_SEND_BACK(MTDisplayGroupDataM groupM , WorkflowResponse wfResp,UserDetailM userM ,HttpServletRequest request){
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		String errorMsg = "";
		if (null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		String currentRole = userM.getCurrentRole();
//		logger.debug("Current role >> "+currentRole);
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
		String decision = request.getParameter("decision_option");	
		String jobState = applicationM.getJobState();
		
		for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()){
			switch (detailM.getServiceID()){
				case PLXrulesConstant.ModuleService.REQUEST_NCB_FICO:
//					#septemwi comment not mandatory
//					if(ORIGLogic.MandatorySendBackRequestNcb(decision,currentRole,jobState)){
//						if(ORIGLogic.LogicResultNCBData(xrulesVer.getNCBCode())){
//							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CANNOT_SENDBACK_NCB");
//							formHandler.PushErrorMessage("", errorMsg);
//							result = true;
//						}
//					}
					break;
				case PLXrulesConstant.ModuleService.RETRIEVE_OLD_NCB:
//					#septemwi comment not mandatory
//					if(ORIGLogic.MandatorySendBackRequestNcb(decision,currentRole,jobState)){
//						if(ORIGLogic.LogicResultNCBData(xrulesVer.getNCBCode())){
//							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CANNOT_SENDBACK_NCB");
//							formHandler.PushErrorMessage("", errorMsg);
//							result = true;
//						}
//					}
					break;
				case PLXrulesConstant.ModuleService.WAIVED_NCB:
//					#septemwi comment not mandatory
//					if(ORIGLogic.MandatorySendBackRequestNcb(decision,currentRole,jobState)){
//						if(ORIGLogic.LogicResultNCBData(xrulesVer.getNCBCode())){
//							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CANNOT_SENDBACK_NCB");
//							formHandler.PushErrorMessage("", errorMsg);
//							result = true;
//						}
//					}
					break;
				default:
					break;
			}
		}	
	}
	private void doMANDATORY_TYPE_DRAFT(MTDisplayGroupDataM groupM , WorkflowResponse wfResp,UserDetailM userM ,HttpServletRequest request){
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		String errorMsg = "";
		if (null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		String currentRole = userM.getCurrentRole();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
		String decision = request.getParameter("decision_option");	
		
		ORIGLogic origLogic = new ORIGLogic();
		for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()){
			switch (detailM.getServiceID()){
				case PLXrulesConstant.ModuleService.DOC_LIST:
					if (origLogic.LogicMandatoryDocList(wfResp,currentRole,decision)){
						if(OrigUtil.isEmptyString(applicationM.getDocListResultCode())){
							logger.debug("Cannot Request FU Please Check Doc List Status ... ");
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CANNOT_REQUESTFU");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}
					break;						 
				default:
					break;
			}
		}			
	}	
	private void doMANDATORY_TYPE_SUMMIT(MTDisplayGroupDataM groupM , WorkflowResponse wfResp,UserDetailM userM ,HttpServletRequest request){		

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		String errorMsg = "";
		if (null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		String currentRole = userM.getCurrentRole();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
		
		String role = userM.getCurrentRole();		
		String decision = request.getParameter("decision_option");	
		
		ORIGLogic origLogic = new ORIGLogic();
		for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()){
			switch (detailM.getServiceID()) {
				case PLXrulesConstant.ModuleService.DSS:							
					if(origLogic.LogicMandatoryDssKbank(xrulesVer.getListedCompanyCode(),xrulesVer.getBankruptcyCode()
															,xrulesVer.getAmcTamcCode(),decision)){
						String dssResult = request.getParameter("result_1029");
						if(OrigUtil.isEmptyString(dssResult)){											
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_DSS_KBANK");
							formHandler.PushErrorMessage("result_1029", errorMsg);
							result = true;
						}
					}
					break;
				case PLXrulesConstant.ModuleService.DOC_LIST:
					if (origLogic.LogicMandatoryDocList(wfResp,currentRole,decision)){
						if(OrigUtil.isEmptyString(applicationM.getDocListResultCode())){
							logger.debug("Cannot Request FU Please Check Doc List Status ... ");
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_CANNOT_REQUESTFU");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}
					break;
				case PLXrulesConstant.ModuleService.INCOME_DEBT:
					BigDecimal zero = new BigDecimal("0.00");
					if (origLogic.LogicMandatoryIncomeDebt(decision)) {
						BigDecimal otherIncome = DataFormatUtility.StringToBigDecimal(request.getParameter("other-income"));
						String otherIncomeRemark = request.getParameter("other-income-remark");
						if (OrigUtil.isEmptyString(otherIncomeRemark) && otherIncome.compareTo(zero) > 0) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_OTHERINCOMEREMARK");
							formHandler.PushErrorMessage("other-income-remark", errorMsg);
							result = true;
						}
						BigDecimal otherDebt = DataFormatUtility.StringToBigDecimal(request.getParameter("other-debt"));
						String otherDebtRemark = request.getParameter("other-debt-remark");
						if (OrigUtil.isEmptyString(otherDebtRemark) && otherDebt.compareTo(zero) > 0) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_OTHERDEBTREMARK");
							formHandler.PushErrorMessage("other-debt-remark", errorMsg);
							result = true;
						}
						BigDecimal salary = DataFormatUtility.StringToBigDecimal(request.getParameter("salary"));
						if (salary.compareTo(zero) <= 0) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_SALARY");
							formHandler.PushErrorMessage("salary", errorMsg);
							result = true;
						}
					}
					break;
				case PLXrulesConstant.ModuleService.BUTTON_EXECUTE1:									
					if(origLogic.LogicMandatoryButtonExecute1(decision, currentRole)
							&& !origLogic.LogicWaivedRefferenceNo(request, applicationM.getJobState(), currentRole ,xrulesVer.getExecute1Result())){										
						if (OrigUtil.isEmptyString(xrulesVer.getExecute1Result())
								|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVer.getExecute1Result())) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_EXECUTE1");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}										
					}
					break;
				case PLXrulesConstant.ModuleService.BUTTION_EXECUTE2:									
					if (origLogic.LogicMandatoryButtonExecute2(decision, currentRole)
							&& !origLogic.LogicWaivedRefferenceNo(request, applicationM.getJobState(), currentRole ,xrulesVer.getExecute2Result())){										
						if (OrigUtil.isEmptyString(xrulesVer.getExecute2Result())
								|| !PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVer.getExecute2Result())) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_EXECUTE2");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}										
					}
					
					break;
				case PLXrulesConstant.ModuleService.REQUEST_NCB_FICO:																								 
					if (origLogic.LogicRequestCBDecision(wfResp, decision)
							|| origLogic.LogicSendToCB(role, decision,xrulesVer.getNCBCode())){
						Vector<PLNCBDocDataM> ncbDocVect = personlM.getNcbDocVect();
						if (OrigUtil.isEmptyVector(ncbDocVect) || ncbDocVect.size() < 2) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_DOC_NCB");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}										
					} 	
					break;
				case PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE:
					if (origLogic.LogicMandatoryBehavior(decision, applicationM.getSaleType())) {
						if (OrigUtil.isEmptyString(xrulesVer.getBehaviorRiskGradeCode())) {
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_BEHAVIOR_RISKGRADE");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}	
					break;
				case PLXrulesConstant.ModuleService.RETRIEVE_OLD_NCB: 
					if( OrigUtil.isEmptyString(xrulesVer.getNCBCode())
							|| NCBConstant.ncbResultCode.WAITING_NCB_DATA.equals(xrulesVer.getNCBCode())){
						errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_NCB");
						formHandler.PushErrorMessage("", errorMsg);
						result = true;
					}
					break;
				case PLXrulesConstant.ModuleService.WAIVED_NCB: 
					 if(OrigConstant.SaleType.BUNDING_CREDIT_CARD.equals(applicationM.getSaleType())
							 || OrigConstant.SaleType.BUNDING_CREDIT_CARD_GENERIC.equals(applicationM.getSaleType())) {	
						if( OrigUtil.isEmptyString(xrulesVer.getNCBCode())){
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_NCB");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}	                                   
					 }
					break;	
				case PLXrulesConstant.ModuleService.PAY_ROLL:
					if(!origLogic.LogicWaivedRefferenceNo(request, applicationM.getJobState(), currentRole ,xrulesVer.getExecute1Result())){
						if(PLXrulesConstant.ResultCode.CODE_WARNING.equals(xrulesVer.getPayrollCode())){
							errorMsg = ErrorUtil.getShortErrorMessage(request, "PAYROLL_WARNING");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}
					break;	
				case PLXrulesConstant.ModuleService.VERIFY_CUSTOMER:
					String result_1032 = request.getParameter("result_1032");
					if(origLogic.LogicMandatoryVerCustomer(currentRole, decision)
							|| origLogic.LogicMandatoryVerCustomerCG(currentRole, decision, applicationM.getBusinessClassId())){
						if(OrigUtil.isEmptyString(result_1032)){										
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_VERCUST");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}
					break;		
				case PLXrulesConstant.ModuleService.VERIFY_HR:
					String result_1033 = request.getParameter("result_1033");
					if(origLogic.LogicMandatoryVerHR(currentRole, decision)
							|| origLogic.LogicMandatoryVerHRCG(currentRole, decision, applicationM.getBusinessClassId())){
						if(OrigUtil.isEmptyString(result_1033)){										
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_REQUIRE_VERHR");
							formHandler.PushErrorMessage("", errorMsg);	
							result = true;
						}
					}
					break;
				case PLXrulesConstant.ModuleService.KBANK_EMPLOYEE:
					if(!origLogic.LogicWaivedRefferenceNo(request, applicationM.getJobState(), currentRole ,xrulesVer.getExecute1Result())){
						if(PLXrulesConstant.ResultCode.CODE_WARNING.equals(xrulesVer.getkBankEmployeeCode())){
							errorMsg = ErrorUtil.getShortErrorMessage(request, "MSG_XRULES_REQUIRE_KBANKEMPLOYEE");
							formHandler.PushErrorMessage("", errorMsg);
							result = true;
						}
					}
					break;								 
				default:
					break;
			}
		}			
	}
	
}
