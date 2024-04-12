package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class FinInstrumentPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(FinInstrumentPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> finInstrumentList = incomeM.getAllIncomes();
		String kIssuerName = null;
		if(SystemConstant.getConstant("INC_TYPE_FIN_INSTR_KBANK").equals(incomeM.getIncomeType())) {
			kIssuerName = SystemConstant.getConstant("ISSUER_NAME_KBANK");
		}
		if(!Util.empty(finInstrumentList)) {
			for(IncomeCategoryDataM incomeObj: finInstrumentList) {
				FinancialInstrumentDataM finInstrumentM = (FinancialInstrumentDataM) incomeObj;
				String openDate = request.getParameter("START_DATE_"+finInstrumentM.getSeq());
				String expireDate = request.getParameter("END_DATE_"+finInstrumentM.getSeq());
				String issuerName = request.getParameter("INSTR_ISSUED_NAME_"+finInstrumentM.getSeq());
				String instrumentType = request.getParameter("INSTRUMENT_TYPE_"+finInstrumentM.getSeq());
				String instrumentTypeOth = request.getParameter("INSTRUMENT_TYPE_OTH_"+finInstrumentM.getSeq());
				String holderName = request.getParameter("HOLDER_NAME_"+finInstrumentM.getSeq());
				String holdingRatio = request.getParameter("PORTION_"+finInstrumentM.getSeq());
				String currentBalance = request.getParameter("FIN_INSTRUMENT_AMT_"+finInstrumentM.getSeq());
				
				logger.debug("openDate >>"+openDate);	
				logger.debug("expireDate >>"+expireDate);	
				logger.debug("issuerName >>"+issuerName);	
				logger.debug("instrumentType >>"+instrumentType);	
				logger.debug("instrumentTypeOth >>"+instrumentTypeOth);	
				logger.debug("holderName >>"+holderName);
				logger.debug("holdingRatio >>"+holdingRatio);
				logger.debug("currentBalance >>"+currentBalance);
				
				finInstrumentM.setOpenDate(FormatUtil.toDate(openDate, FormatUtil.TH));
				finInstrumentM.setExpireDate(FormatUtil.toDate(expireDate, FormatUtil.TH));
				if(SystemConstant.getConstant("INC_TYPE_FIN_INSTR_KBANK").equals(incomeM.getIncomeType())) {
					finInstrumentM.setIssuerName(kIssuerName);
				} else {
					finInstrumentM.setIssuerName(issuerName);
				}
				finInstrumentM.setInstrumentType(instrumentType);
				finInstrumentM.setInstrumentTypeDesc(instrumentTypeOth);
				finInstrumentM.setHolderName(holderName);
				finInstrumentM.setHoldingRatio(FormatUtil.toBigDecimal(holdingRatio, true));
				finInstrumentM.setCurrentBalance(FormatUtil.toBigDecimal(currentBalance, true));
			}
		}
		

		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(incomeM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
			if(Util.empty(formError.getFormError())) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				incomeM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "FIN_INSTRUMENT_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> finInstrumentList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		if(!Util.empty(finInstrumentList)) {
			for(IncomeCategoryDataM incomeObj: finInstrumentList) {
				FinancialInstrumentDataM finInstrumentM = (FinancialInstrumentDataM) incomeObj;
				formError.mandatoryDate(subformId,"START_DATE","START_DATE_"+finInstrumentM.getSeq(),"", finInstrumentM.getOpenDate(),request);
				formError.mandatoryDate(subformId,"END_DATE","END_DATE_"+finInstrumentM.getSeq(),"", finInstrumentM.getExpireDate(),request);
				formError.mandatory(subformId,"INSTR_ISSUED_NAME","INSTR_ISSUED_NAME_"+finInstrumentM.getSeq(),"", finInstrumentM.getIssuerName(),request);
				formError.mandatory(subformId,"INSTRUMENT_TYPE","INSTRUMENT_TYPE_"+finInstrumentM.getSeq(),"", finInstrumentM.getInstrumentType(),request);
//				formError.mandatory(subformId,"INSTRUMENT_TYPE_OTH","INSTRUMENT_TYPE_OTH_"+finInstrumentM.getSeq(), finInstrumentM,request);
				formError.mandatory(subformId,"HOLDER_NAME","HOLDER_NAME_"+finInstrumentM.getSeq(),"", finInstrumentM.getHolderName(),request);
				formError.mandatory(subformId,"PORTION","PORTION_"+finInstrumentM.getSeq(),"", finInstrumentM.getHoldingRatio(),request);
				formError.mandatory(subformId,"FIN_INSTRUMENT_AMT","FIN_INSTRUMENT_AMT_"+finInstrumentM.getSeq(),"", finInstrumentM.getCurrentBalance(),request);
				formError.mandatory(subformId,"START_END_DATE","START_DATE_"+finInstrumentM.getSeq(), finInstrumentM,request);
			}
		} else {
			formError.error("FIN_INSTRUMENT",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		
		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
			/*if(ApplicationUtil.eApp(source)) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					
					if(Util.empty(formError.getFormError())) {
						incomeM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						incomeM.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}
			/*}*/
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
