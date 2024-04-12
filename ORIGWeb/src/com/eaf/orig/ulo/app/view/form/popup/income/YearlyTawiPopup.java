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
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class YearlyTawiPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(YearlyTawiPopup.class);
	String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
	String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> yearlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
		if(!Util.empty(yearlyList)) {
			for(IncomeCategoryDataM incomeObj: yearlyList) {
				if(incomeObj instanceof YearlyTawi50DataM) {
					YearlyTawi50DataM yearlyTawiM = (YearlyTawi50DataM) incomeObj;
					String companyName = request.getParameter("COMPANY_NAME_"+yearlyTawiM.getSeq());
					String year = request.getParameter("YEAR_"+yearlyTawiM.getSeq());
					String month = request.getParameter("ACCUMULATED_MONTH_"+yearlyTawiM.getSeq());
					String income401 = request.getParameter("INCOME_TYPE401_"+yearlyTawiM.getSeq());
					String income402 = request.getParameter("INCOME_TYPE402_"+yearlyTawiM.getSeq());
					String sumSso = request.getParameter("SSO_"+yearlyTawiM.getSeq());
					
					logger.debug("companyName >>"+companyName);
					logger.debug("year >>"+year);
					logger.debug("month >>"+month);
					logger.debug("income401 >>"+income401);	
					logger.debug("income402 >>"+income402);
					logger.debug("sumSso >>"+sumSso);
					
					yearlyTawiM.setCompanyName(companyName);
					yearlyTawiM.setYear(FormatUtil.getInt(year));
					yearlyTawiM.setMonth(FormatUtil.getInt(month));
					yearlyTawiM.setIncome401(FormatUtil.toBigDecimal(income401, true));
					yearlyTawiM.setIncome402(FormatUtil.toBigDecimal(income402, true));
					yearlyTawiM.setSumSso(FormatUtil.toBigDecimal(sumSso, true));
				}
			}
		}
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(incomeM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			incomeM.setIncomeType(INC_TYPE_YEARLY_50TAWI);
			formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
			incomeM.setIncomeType(INC_TYPE_MONTHLY_50TAWI);
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
		String chk50Tawi_YEAR = request.getParameter("chk50Tawi_YEAR");
		String chk50Tawi_MON = request.getParameter("chk50Tawi_MON");
		logger.info("chk50Tawi_YEAR >>"+chk50Tawi_YEAR);
		logger.info("chk50Tawi_MON >>"+chk50Tawi_MON);
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		
		if(Util.empty(chk50Tawi_YEAR) && Util.empty(chk50Tawi_MON)){
			formError.error("",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		
		ArrayList<IncomeCategoryDataM> yearlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
		if(Util.empty(yearlyList)){
			yearlyList = new ArrayList<IncomeCategoryDataM>();
		}
		if(INC_TYPE_YEARLY_50TAWI.equalsIgnoreCase(chk50Tawi_YEAR) && Util.empty(yearlyList)) {
			formError.error("YEARLY_TAWI",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
		if(Util.empty(monthlyList)){
			monthlyList = new ArrayList<IncomeCategoryDataM>();
		}
		if(INC_TYPE_MONTHLY_50TAWI.equalsIgnoreCase(chk50Tawi_MON) && Util.empty(monthlyList)) {
			formError.error("MONTHLY_TAWI",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		if(!Util.empty(monthlyList)) {
			String subformID = "MONTHLY_TAWI_POPUP";
			for(IncomeCategoryDataM incomeObj: monthlyList) {
				if(incomeObj instanceof MonthlyTawi50DataM) {
					MonthlyTawi50DataM monthlyDataM = (MonthlyTawi50DataM) incomeObj;
					String SEQ = FormatUtil.toString(monthlyDataM.getSeq());
					formError.mandatory(subformID,"COMPANY_NAME","COMPANY_NAME_"+SEQ,"",monthlyDataM.getCompanyName(),request);	
					formError.mandatory(subformID,"INCOME_DESC","INCOME_DESC_"+SEQ,"", monthlyDataM.getTawiIncomeType(),request);
					formError.mandatory(subformID,"MONTHLY_TAWI_AMT","MONTHLY_TAWI_AMT_"+SEQ,"",monthlyDataM,request);
				}
			}
		}
		if(!Util.empty(yearlyList)) {
			String subformId = "YEARLY_TAWI_POPUP";
			for(IncomeCategoryDataM incomeObj: yearlyList) {
				if(incomeObj instanceof YearlyTawi50DataM) {
					YearlyTawi50DataM yearlyDataM = (YearlyTawi50DataM) incomeObj;
					String SEQ = FormatUtil.toString(yearlyDataM.getSeq());
					formError.mandatory(subformId,"COMPANY_NAME","COMPANY_NAME_"+SEQ,"",yearlyDataM.getCompanyName(),request);	
					formError.mandatory(subformId,"YEAR","YEAR_"+SEQ,yearlyDataM,request);	
					formError.mandatory(subformId,"ACCUMULATED_MONTH","ACCUMULATED_MONTH_"+SEQ,"",yearlyDataM.getMonth(),request);	
					formError.mandatory(subformId,"INCOME_TYPE","INCOME_TYPE_"+SEQ,yearlyDataM,request);
				}
			}
		}

		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					if(!Util.empty(yearlyList)) {
						incomeM.setIncomeType(INC_TYPE_YEARLY_50TAWI);
						formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					}
					if(!Util.empty(monthlyList)) {
						incomeM.setIncomeType(INC_TYPE_MONTHLY_50TAWI);
						formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					}
					
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
