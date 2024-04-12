package com.eaf.orig.ulo.app.view.form.popup.income;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDataM;
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;

public class OtherOpenFundPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OtherOpenFundPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		Calendar calen = Calendar.getInstance();
		Date appDate = mainForm.getObjectForm().getApplicationDate();
		if(Util.empty(appDate)) {
			appDate = ApplicationDate.getDate();
		}
		
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> othOpnFundList = incomeM.getAllIncomes();
		if(!Util.empty(othOpnFundList)) {
			for(IncomeCategoryDataM incomeObj: othOpnFundList) {
				OpenedEndFundDataM othOpenFundM = (OpenedEndFundDataM) incomeObj;
				String bankCode = request.getParameter("FIN_INSTITUTION_"+othOpenFundM.getSeq());
				String fundName = request.getParameter("FUND_NAME_"+othOpenFundM.getSeq());
				String accountNo = request.getParameter("ACCOUNT_NO_"+othOpenFundM.getSeq());
				String accountName = request.getParameter("ACCOUNT_NAME_"+othOpenFundM.getSeq());
				String holdingRatio = request.getParameter("PORTION_"+othOpenFundM.getSeq());
				
				logger.debug("bankCode >>"+bankCode);
				logger.debug("fundName >>"+fundName);
				logger.debug("accountNo >>"+accountNo);
				logger.debug("accountName >>"+accountName);
				logger.debug("holdingRatio >>"+holdingRatio);
				
				othOpenFundM.setBankCode(bankCode);
				othOpenFundM.setFundName(fundName);
				othOpenFundM.setAccountNo(accountNo);
				othOpenFundM.setAccountName(accountName);
				othOpenFundM.setHoldingRatio(FormatUtil.toBigDecimal(holdingRatio, true));
				
				//For Monthly Acct details
				ArrayList<OpenedEndFundDetailDataM> detailList = othOpenFundM.getOpenEndFundDetails();
				if(Util.empty(detailList)) {
					detailList = new ArrayList<OpenedEndFundDetailDataM>();
					othOpenFundM.setOpenEndFundDetails(detailList);
					for(int i = 0;i<OpenedEndFundDetailDataM.MONTH_COUNT; i++) {
						OpenedEndFundDetailDataM monthDtl = new OpenedEndFundDetailDataM();
						monthDtl.setSeq(i+1);
						detailList.add(monthDtl);
					}
				}
				for(int i = 0; i < detailList.size(); i++) {
					OpenedEndFundDetailDataM monthDtl = detailList.get(i);
					if(monthDtl == null) {
						monthDtl = new OpenedEndFundDetailDataM();
						detailList.set(i, monthDtl);
					}
							
					Date prevMonth = Util.getPreviousMonth(appDate, i+1);
					calen.setTime(prevMonth);
					String year = Util.getYear(prevMonth);	
					monthDtl.setAmount(FormatUtil.toBigDecimal(request.getParameter("AMOUNT_"+othOpenFundM.getSeq()+"_"+(i+1)), true));
					monthDtl.setMonth(FormatUtil.toString(calen.get(Calendar.MONTH)+1));
					monthDtl.setYear(year);
				}
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
		String subformId = "OTH_OPEN_FUND_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> opnEndList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(opnEndList)) {
			for(IncomeCategoryDataM incomeObj: opnEndList) {
				OpenedEndFundDataM othOpenFundM = (OpenedEndFundDataM) incomeObj;
				formError.mandatory(subformId,"FIN_INSTITUTION","FIN_INSTITUTION_"+othOpenFundM.getSeq(), othOpenFundM.getBankCode(),request);	
				formError.mandatory(subformId,"FUND_NAME","FUND_NAME_"+othOpenFundM.getSeq(), othOpenFundM.getFundName(),request);
				formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+othOpenFundM.getSeq(), othOpenFundM.getAccountNo(),request);
				formError.mandatory(subformId,"ACCOUNT_NAME","ACCOUNT_NAME_"+othOpenFundM.getSeq(), othOpenFundM.getAccountName(),request);
				formError.mandatory(subformId,"PORTION","PORTION_"+othOpenFundM.getSeq(), othOpenFundM.getHoldingRatio(),request);
				formError.mandatory(subformId,"OPEN_FUND_AMT","", othOpenFundM,request);
			}
		} else {
			formError.error("OPEN_END_FUND",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
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
