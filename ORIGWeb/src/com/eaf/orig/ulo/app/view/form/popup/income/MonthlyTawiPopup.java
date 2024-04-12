package com.eaf.orig.ulo.app.view.form.popup.income;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;

public class MonthlyTawiPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(MonthlyTawiPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
		
		Calendar calen = Calendar.getInstance();
		Date appDate = mainForm.getObjectForm().getApplicationDate();
		if(Util.empty(appDate)) {
			appDate = ApplicationDate.getDate();
		}
		if(!Util.empty(monthlyList)) {
			for(IncomeCategoryDataM monthlyM: monthlyList) {
				if(monthlyM instanceof MonthlyTawi50DataM) {
					MonthlyTawi50DataM monthlyData = (MonthlyTawi50DataM) monthlyM;
					String companyName = request.getParameter("COMPANY_NAME_"+monthlyData.getSeq());
					String tawiIncomeType = request.getParameter("INCOME_DESC_"+monthlyData.getSeq());
					String mon1Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_1");
					String mon2Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_2");
					String mon3Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_3");
					String mon4Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_4");
					String mon5Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_5");
					String mon6Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_6");
					
					logger.debug("companyName >>"+companyName);
					logger.debug("tawiIncomeType >>"+tawiIncomeType);
					logger.debug("mon1Income >>"+mon1Income);
					logger.debug("mon2Income >>"+mon2Income);
					logger.debug("mon3Income >>"+mon3Income);
					logger.debug("mon4Income >>"+mon4Income);
					logger.debug("mon5Income >>"+mon5Income);
					logger.debug("mon6Income >>"+mon6Income);
					
					monthlyData.setCompanyName(companyName);
					monthlyData.setTawiIncomeType(tawiIncomeType);
					
					ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyData.getMonthlyTaxi50Details();
					if(Util.empty(detailList)) {
						detailList = new ArrayList<MonthlyTawi50DetailDataM>();
						monthlyData.setMonthlyTaxi50Details(detailList);
						for(int i = 0;i<MonthlyTawi50DetailDataM.MONTH_COUNT; i++) {
							MonthlyTawi50DetailDataM monthDtl = new MonthlyTawi50DetailDataM();
							monthDtl.setSeq(i+1);
							detailList.add(monthDtl);
						}
					}
					
					for(int i = 0; i < detailList.size(); i++) {
						MonthlyTawi50DetailDataM monthDtl = detailList.get(i);
						if(monthDtl == null) {
							monthDtl = new MonthlyTawi50DetailDataM();
							detailList.set(i, monthDtl);
						}
								
						Date prevMonth = Util.getPreviousMonth(appDate, i);
						calen.setTime(prevMonth);
						String year = Util.getYear(prevMonth);	
						monthDtl.setAmount(FormatUtil.toBigDecimal(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_"+(i+1)), true));
						monthDtl.setMonth(FormatUtil.toString(calen.get(Calendar.MONTH)+1));
						monthDtl.setYear(year);
					}
				} else {
					continue;
				}
			}
		}
		//to check if final value from screen match with previous or not
		// Moved all to Yearly subform
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		// Moved all to Yearly subform
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
