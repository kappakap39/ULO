package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class Recheck50TawiIncome implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(Recheck50TawiIncome.class);
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
	String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");	
	@SuppressWarnings("rawtypes")
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.RECHECK_50TAWI_INCOME);
		try{
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = ((IncomeInfoDataM) moduleHandler.getObjectForm());			
			EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
			if(Util.empty(workAddress)) {
//				DIHProxy dihProxy = new DIHProxy();
//				dihProxy.personalDataMapper(personalInfo.getCisNo(), personalInfo, new HashMap<String, CompareDataM>());
				workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
				if(null == workAddress) {
					workAddress = new AddressDataM(); 
				}
			}
			logger.debug("COMPANY_NAME : "+workAddress.getCompanyName());			
			String incomeType = request.getParameter("incomeType");
			String element = request.getParameter("element");
			String chk50Tawi_MON = request.getParameter("chk50Tawi_MON");
			String chk50Tawi_YEAR = request.getParameter("chk50Tawi_YEAR");
			logger.info("incomeType : "+incomeType);
			logger.info("element : "+element);
			logger.info("chk50Tawi_MON : "+chk50Tawi_MON);
			logger.info("chk50Tawi_YEAR : "+chk50Tawi_YEAR);	
			String className = "";
			if("chk50Tawi_YEAR".equalsIgnoreCase(element)) {
				request.getSession().setAttribute("chk50Tawi_YEAR", incomeType);
				className = IncomeTypeUtility.getClassNameByIncomeType(INC_TYPE_YEARLY_50TAWI);
				incomeM.setIncomeType(INC_TYPE_YEARLY_50TAWI);
				
			}else{
				request.getSession().setAttribute("chk50Tawi_MON", incomeType);
				className = IncomeTypeUtility.getClassNameByIncomeType(INC_TYPE_MONTHLY_50TAWI);
				incomeM.setIncomeType(INC_TYPE_MONTHLY_50TAWI);
			}
			logger.info("className : "+className);
			Class myClass = Class.forName(className);			
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
			if(allIncomeList == null) {
				allIncomeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(allIncomeList);
			}			
			boolean isExist = false;
			if(!Util.empty(allIncomeList)){
				Iterator<IncomeCategoryDataM> iterator = allIncomeList.iterator();
				while(iterator.hasNext()){
					IncomeCategoryDataM categoryM = iterator.next();
					if(categoryM.getClass() == myClass){
						isExist = true;
						iterator.remove();	
					}
				}
				incomeM.setAllIncomes(allIncomeList);
			}			
			if(!Util.empty(incomeType)) {
				if(!isExist) {
					logger.debug("NOT EXIST!");
					IncomeCategoryDataM incomeCategoryM = (IncomeCategoryDataM)(myClass).newInstance();
					incomeCategoryM.setIncomeId(incomeM.getIncomeId());
					incomeCategoryM.setSeq(allIncomeList.size()+1);
					incomeCategoryM.setIncomeType(incomeM.getIncomeType());
					if(INC_TYPE_YEARLY_50TAWI.equalsIgnoreCase(incomeType)) {
						int defaultYear = IncomeTypeUtility.getDefaultThaiYearForTawi();
						logger.debug("tawi50year : "+defaultYear);						
						YearlyTawi50DataM yearlyTawi50 = (YearlyTawi50DataM)incomeCategoryM;
						yearlyTawi50.setCompanyName(workAddress.getCompanyName());
						yearlyTawi50.setYear(defaultYear);	
					}else{
						MonthlyTawi50DataM monthlyTawi50 = (MonthlyTawi50DataM)incomeCategoryM;
						monthlyTawi50.setCompanyName(workAddress.getCompanyName());
					}
					allIncomeList.add(incomeCategoryM);
				}
				logger.info("seq:"+allIncomeList.size());
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
