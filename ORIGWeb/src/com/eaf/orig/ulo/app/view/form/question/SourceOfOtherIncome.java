package com.eaf.orig.ulo.app.view.form.question;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.OtherIncomeDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SourceOfOtherIncome extends ElementHelper implements ElementInf  {
	private static transient Logger logger = Logger.getLogger(SourceOfOtherIncome.class);
	
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/subform/question/SourceOfOtherIncome.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		logger.debug("processElement >> "+SourceOfOtherIncome.class);
		String OTHER_INCOME_QUESTION = SystemConstant.getConstant("OTHER_INCOME_QUESTION");
		String INC_TYPE_OTH_INCOME = SystemConstant.getConstant("INC_TYPE_OTH_INCOME");
		String RESULT_CHECK = request.getParameter("RESULT_CHECK_"+OTHER_INCOME_QUESTION);
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		IdentifyQuestionDataM identifyQuestion = (IdentifyQuestionDataM)objectElement;
		IncomeInfoDataM incomeM = personalInfo.getIncomeByType(INC_TYPE_OTH_INCOME);
		
		ArrayList<IncomeCategoryDataM> incomeCategorys = incomeM.getAllIncomes();
		if(!Util.empty(incomeCategorys)){
			for(IncomeCategoryDataM incomeCategory: incomeCategorys){
				OtherIncomeDataM otherIncome = (OtherIncomeDataM) incomeCategory;
				int seq = otherIncome.getSeq();
				String incomeType = request.getParameter("INCOME_TYPE_"+seq);
				String incomeAmt = request.getParameter("INCOME_AMOUNT_"+seq);
				logger.debug("Seq >>> "+seq);
				logger.debug("incomeType >> "+incomeType);
				logger.debug("incomeAmt >> "+incomeAmt);
				otherIncome.setIncomeTypeDesc(incomeType);
				otherIncome.setIncomeAmount(FormatUtil.toBigDecimal(incomeAmt));
			}
		}
	
		identifyQuestion.setResult(RESULT_CHECK);
		return null;
	}
	
}
