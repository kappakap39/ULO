package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class Add50TawiIncome implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(Add50TawiIncome.class);
	String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_50_TAWI_INCOME);
		try{
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = ((IncomeInfoDataM) moduleHandler.getObjectForm());
			String subFormID = request.getParameter("subformId");
			String incomeType = request.getParameter("incomeType");
			logger.info("subFormID : "+subFormID);
			logger.info("incomeType : "+incomeType);
			
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
			if(allIncomeList == null) {
				allIncomeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(allIncomeList);
			}
			String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
			logger.info("className : "+className);
			IncomeCategoryDataM incomeCategoryM = (IncomeCategoryDataM)(Class.forName(className)).newInstance();
			incomeCategoryM.setIncomeId(incomeM.getIncomeId());
			incomeCategoryM.setSeq(allIncomeList.size()+1);
			incomeCategoryM.setIncomeType(incomeM.getIncomeType());
			
			if(INC_TYPE_YEARLY_50TAWI.equalsIgnoreCase(incomeType)) {
				int defaultYear = IncomeTypeUtility.getDefaultThaiYearForTawi();
				logger.debug("tawi50year : "+defaultYear);
				YearlyTawi50DataM yearlyTawi50 = (YearlyTawi50DataM)incomeCategoryM;
					yearlyTawi50.setYear(defaultYear);	
			}
			allIncomeList.add(incomeCategoryM);
			logger.info("seq : "+allIncomeList.size());
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
