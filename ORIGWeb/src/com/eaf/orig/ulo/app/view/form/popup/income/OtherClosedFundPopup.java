package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ClosedEndFundDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class OtherClosedFundPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OtherClosedFundPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> othClosedList = incomeM.getAllIncomes();
		if(!Util.empty(othClosedList)) {
			for(IncomeCategoryDataM incomeObj: othClosedList) {
				ClosedEndFundDataM othClosedFundM = (ClosedEndFundDataM) incomeObj;
				String bankCode = request.getParameter("FIN_INSTITUTION_"+othClosedFundM.getSeq());
				String fundName = request.getParameter("FUND_NAME_"+othClosedFundM.getSeq());
				String accountNo = request.getParameter("ACCOUNT_NO_"+othClosedFundM.getSeq());
				String accountName = request.getParameter("ACCOUNT_NAME_"+othClosedFundM.getSeq());
				String holdingRatio = request.getParameter("PORTION_"+othClosedFundM.getSeq());
				String accountBalance = request.getParameter("OUTSTANDING_BALANCE_"+othClosedFundM.getSeq());
				
				logger.debug("bankCode >>"+bankCode);	
				logger.debug("fundName >>"+fundName);	
				logger.debug("accountNo >>"+accountNo);	
				logger.debug("accountName >>"+accountName);	
				logger.debug("holdingRatio >>"+holdingRatio);	
				logger.debug("accountBalance >>"+accountBalance);
				
				othClosedFundM.setBankCode(bankCode);
				othClosedFundM.setFundName(fundName);
				othClosedFundM.setAccountNo(accountNo);
				othClosedFundM.setAccountName(accountName);
				othClosedFundM.setHoldingRatio(FormatUtil.toBigDecimal(holdingRatio, true));
				othClosedFundM.setAccountBalance(FormatUtil.toBigDecimal(accountBalance, true));
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
		String subformId = "OTH_CLOSED_FUND_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> closedEndList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(closedEndList)) {
			for(IncomeCategoryDataM incomeObj: closedEndList) {
				ClosedEndFundDataM othClosedFundM = (ClosedEndFundDataM) incomeObj;
				formError.mandatory(subformId,"FIN_INSTITUTION","FIN_INSTITUTION_"+othClosedFundM.getSeq(),"", othClosedFundM.getBankCode(),request);	
				formError.mandatory(subformId,"FUND_NAME","FUND_NAME_"+othClosedFundM.getSeq(),"", othClosedFundM.getFundName(),request);
				formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+othClosedFundM.getSeq(),"", othClosedFundM.getAccountNo(),request);
				formError.mandatory(subformId,"ACCOUNT_NAME","ACCOUNT_NAME_"+othClosedFundM.getSeq(),"", othClosedFundM.getAccountName(),request);
				formError.mandatory(subformId,"PORTION","PORTION_"+othClosedFundM.getSeq(),"", othClosedFundM.getHoldingRatio(),request);
				formError.mandatory(subformId,"OUTSTANDING_BALANCE","OUTSTANDING_BALANCE_"+othClosedFundM.getSeq(),"", othClosedFundM.getAccountBalance(),request);
			}
		} else {
			formError.error("CLOSED_END_FUND",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		

		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
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
