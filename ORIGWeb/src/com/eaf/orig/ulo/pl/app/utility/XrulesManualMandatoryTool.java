package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.cache.data.MatrixCache;
import com.eaf.xrules.cache.model.MTDisplayDetailDataM;
import com.eaf.xrules.cache.model.MTDisplayGroupDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
//import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
//import com.eaf.xrules.ulo.pl.tool.cache.XrulesCacheTool;

public class XrulesManualMandatoryTool {
	static Logger logger = Logger.getLogger(XrulesManualMandatoryTool.class);
	public void Mandatory(String buttonID ,JsonObjectUtil json ,PLApplicationDataM applicationM,HttpServletRequest request){
		logger.debug("Mandatory buttonID >> "+buttonID);
		if(PLXrulesConstant.ButtonID.BUTTON_7001.equals(buttonID)){
			this.ButtonExecute1Mandatory(json, applicationM, request);
		}else
		if(PLXrulesConstant.ButtonID.BUTTON_7002.equals(buttonID)){
			this.ButtonExecute2Mandatory(json, applicationM, request);
		}
	}
	public void ButtonExecute1Mandatory(JsonObjectUtil json ,PLApplicationDataM applicationM,HttpServletRequest request){
		if (null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
		String errorMsg = null;
		logger.debug("ButtonExecute1Mandatory >>");
		String saleType = request.getParameter("saleType");
		BigDecimal zero = new BigDecimal("0.00");
		if(OrigConstant.SaleType.BUNDING_SME.equals(saleType)){
			String kec_permit_result = request.getParameter("kec_permit_result");			
			BigDecimal kecPermitCash = DataFormatUtility.StringToBigDecimal(request.getParameter("kec_permit_cash"));
			if ("Pass".equals(kec_permit_result) && kecPermitCash.compareTo(zero) <= 0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_permit_cash", errorMsg);
			}	
		}else
		if(OrigConstant.SaleType.BUNDING_AUTO_LOAN.equals(saleType)){
//			BigDecimal loan_car_cash = DataFormatUtility.StringToBigDecimal(request.getParameter("loan_car_cash"));
			BigDecimal kecCashPayment = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter("kec_cash_payment"));
			if (null == kecCashPayment) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_cash_payment", errorMsg);
			}	
		}else
		if(OrigConstant.SaleType.BUNDING_HOME_LOAN.equals(saleType)){
			BigDecimal kecPermit = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter("kec_permit"));
			if (null == kecPermit) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_permit", errorMsg);
			}
		}
		
//		ORIGXRulesTool tool = new ORIGXRulesTool();
//		XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);
//		XrulesCacheTool cache = new XrulesCacheTool();
//		Vector<MTDisplayGroupDataM> vect = cache.GetMatrixDisplay(requestM);
		
		Vector<MTDisplayGroupDataM> vect = MatrixCache.getMatrixDisplay().get(applicationM.getMatrixServiceID());
		
		if (!OrigUtil.isEmptyVector(vect)) {
			for (MTDisplayGroupDataM groupM : vect) {
				if (!OrigUtil.isEmptyVector(groupM.getDisplayVect())) {
					for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()) {
						switch (detailM.getServiceID()) {
							case PLXrulesConstant.ModuleService.DSS:
								if(PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVer.getBankRuptcyCompanyCode())
										|| PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVer.getAmcTamcCompanyCode())){
									String result_1029 = request.getParameter("result_1029");
									if(OrigUtil.isEmptyString(result_1029)){
										errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_1029");
										json.CreateJsonObject("result_1029", errorMsg);
									}
								}
								break;
							case PLXrulesConstant.ModuleService.RETROSPECITVE_POSITIVE:	
								if(PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVer.getBankRuptcyCompanyCode())
										|| PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVer.getAmcTamcCompanyCode())){
									String result_1030 = request.getParameter("result_1030");
									if(OrigUtil.isEmptyString(result_1030)){
										errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_1030");
										json.CreateJsonObject("result_1030", errorMsg);
									}
								}
								break;
							default:break;
						}
					}
				}
			}
		}
		
	}
	public void ButtonExecute2Mandatory(JsonObjectUtil json ,PLApplicationDataM applicationM,HttpServletRequest request){
		logger.debug("ButtonExecute2Mandatory >>");
		if (null == applicationM) applicationM = new PLApplicationDataM();
		PLPersonalInfoDataM personlM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVer = personlM.getXrulesVerification();
		if (null == xrulesVer) {
			xrulesVer = new PLXRulesVerificationResultDataM();
			personlM.setXrulesVerification(xrulesVer);
		}
		String errorMsg = null;
		BigDecimal zero = new BigDecimal("0.00");
		
//		ORIGXRulesTool tool = new ORIGXRulesTool();
//		XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);
//		XrulesCacheTool cache = new XrulesCacheTool();
//		Vector<MTDisplayGroupDataM> vect = cache.GetMatrixDisplay(requestM);
		
		Vector<MTDisplayGroupDataM> vect = MatrixCache.getMatrixDisplay().get(applicationM.getMatrixServiceID());
		if (!OrigUtil.isEmptyVector(vect)) {
			for (MTDisplayGroupDataM groupM : vect) {
				if (!OrigUtil.isEmptyVector(groupM.getDisplayVect())) {
					for (MTDisplayDetailDataM detailM : groupM.getDisplayVect()) {
						switch (detailM.getServiceID()) {
						case PLXrulesConstant.ModuleService.INCOME_DEBT:							
							BigDecimal otherIncome = DataFormatUtility.StringToBigDecimal(request.getParameter("other-income"));
							String otherIncomeRemark = request.getParameter("other-income-remark");
							if (OrigUtil.isEmptyString(otherIncomeRemark) && otherIncome.compareTo(zero) > 0) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_OTHERINCOMEREMARK");
								json.CreateJsonObject("other-income-remark", errorMsg);
							}
							BigDecimal otherDebt = DataFormatUtility.StringToBigDecimal(request.getParameter("other-debt"));
							String otherDebtRemark = request.getParameter("other-debt-remark");
							if (OrigUtil.isEmptyString(otherDebtRemark) && otherDebt.compareTo(zero) > 0) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_OTHERDEBTREMARK");
								json.CreateJsonObject("other-debt-remark", errorMsg);
							}
							BigDecimal salary = DataFormatUtility.StringToBigDecimal(request.getParameter("salary"));
							if (salary.compareTo(zero) <= 0) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_SALARY");
								json.CreateJsonObject("salary", errorMsg);
							}
							break;
						case PLXrulesConstant.ModuleService.BUTTON_EXECUTE1:
							if (OrigUtil.isEmptyString(xrulesVer.getExecute1Result())) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_EXECUTE1");
								json.CreateJsonObject("", errorMsg);
							}
							break;
						case PLXrulesConstant.ModuleService.REQUEST_NCB_FICO:
							if (OrigUtil.isEmptyString(xrulesVer.getNCBCode()) || OrigUtil.isEmptyString(xrulesVer.getNcbFicoCode())) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_NCBFICO");					
								json.CreateJsonObject("", errorMsg);
							}
							break;
						case PLXrulesConstant.ModuleService.RETRIEVE_OLD_NCB:
							if (OrigUtil.isEmptyString(xrulesVer.getNCBCode()) || OrigUtil.isEmptyString(xrulesVer.getNcbFicoCode())) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_NCBFICO");
								json.CreateJsonObject("", errorMsg);
							}
							break;
						case PLXrulesConstant.ModuleService.WAIVED_NCB:
							if (OrigUtil.isEmptyString(xrulesVer.getNCBCode()) || OrigUtil.isEmptyString(xrulesVer.getNcbFicoCode())) {
								errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_RESULT_NCBFICO");
								json.CreateJsonObject("", errorMsg);
							}
							break;
						default:
							break;
						}
					}
				}
			}
		}
		
		String saleType = request.getParameter("saleType");
		if(OrigConstant.SaleType.BUNDING_SME.equals(saleType)){
			String kec_permit_result = request.getParameter("kec_permit_result");			
			BigDecimal kecPermitCash = DataFormatUtility.StringToBigDecimal(request.getParameter("kec_permit_cash"));
			if ("Pass".equals(kec_permit_result) && kecPermitCash.compareTo(zero) <= 0) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_permit_cash", errorMsg);
			}		
		}else
		if(OrigConstant.SaleType.BUNDING_AUTO_LOAN.equals(saleType)){			
//			BigDecimal loan_car_cash = DataFormatUtility.StringToBigDecimal(request.getParameter("loan_car_cash"));
			BigDecimal kecCashPayment = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter("kec_cash_payment"));
			if (null == kecCashPayment) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_cash_payment", errorMsg);
			}	
		}else
		if(OrigConstant.SaleType.BUNDING_HOME_LOAN.equals(saleType)){
			BigDecimal kecPermit = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter("kec_permit"));
			if (null == kecPermit) {
				errorMsg = ErrorUtil.getShortErrorMessage(request, "REQUIRE_KEC_PERMIT_CASH");
				json.CreateJsonObject("kec_permit", errorMsg);
			}
		}
		
	}
}
