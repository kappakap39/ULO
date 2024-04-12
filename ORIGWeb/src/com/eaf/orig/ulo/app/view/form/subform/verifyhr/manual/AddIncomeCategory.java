package com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class AddIncomeCategory implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddIncomeCategory.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug(" <<< AddIncomeCategory >>> ");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_INCOME_CATEGORY);
		try{
			String subFormID = request.getParameter("subformId");
			logger.debug("subFormID >> "+subFormID);
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			String INC_TYPE_OTH_INCOME = SystemConstant.getConstant("INC_TYPE_OTH_INCOME");
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)entityForm.getObjectForm();
			
			ArrayList<IncomeInfoDataM> incomeInfos = personalInfo.getIncomeInfos();
			if(Util.empty(incomeInfos)){
				incomeInfos = new ArrayList<IncomeInfoDataM>();
				personalInfo.setIncomeInfos(incomeInfos);
			}
			IncomeInfoDataM othIncome = personalInfo.getIncomeByType(INC_TYPE_OTH_INCOME);
			if(Util.empty(othIncome)) {
				othIncome = new IncomeInfoDataM();
				othIncome.setIncomeType(INC_TYPE_OTH_INCOME);
				othIncome.setSeq(incomeInfos.size()+1);
				incomeInfos.add(othIncome);
			}
			ArrayList<IncomeCategoryDataM> allIncomeList = othIncome.getAllIncomes();
			if(Util.empty(allIncomeList)) {
				allIncomeList = new ArrayList<IncomeCategoryDataM>();
				othIncome.setAllIncomes(allIncomeList);
			}
			String className = IncomeTypeUtility.getClassNameByIncomeType(othIncome.getIncomeType());
			logger.info("className : "+className);
			IncomeCategoryDataM incomeCategoryM = (IncomeCategoryDataM)(Class.forName(className)).newInstance();
			incomeCategoryM.setIncomeId(othIncome.getIncomeId());
			incomeCategoryM.setSeq(allIncomeList.size()+1);
			incomeCategoryM.setIncomeType(othIncome.getIncomeType());
			allIncomeList.add(incomeCategoryM);
			logger.info("seq : "+allIncomeList.size());
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}

}
