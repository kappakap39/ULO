package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;

public class DefaultIncomeCategoryList implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddIncomeCategoryList.class);
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	ArrayList<String> INCOME_TYPE_EXCEPTION = SystemConfig.getArrayListGeneralParam("INCOME_TYPE_EXCEPTION");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_LIST_INCOME_CATEGORY);
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			boolean isContainIncomeTypeException =personalInfo.isContainIncomeTypeException(INCOME_TYPE_EXCEPTION);
			AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK);
			if(Util.empty(workAddress)){
				workAddress = new AddressDataM();
			}
			logger.debug("COMPANY_NAME : "+workAddress.getCompanyName());
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = ((IncomeInfoDataM) moduleHandler.getObjectForm());
			String subFormID = request.getParameter("subformId");
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
			if(allIncomeList == null) {
				allIncomeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(allIncomeList);
			}
			String className = IncomeTypeUtility.getClassNameByIncomeType(incomeM.getIncomeType());
			logger.info("incomeM.getIncomeType():"+incomeM.getIncomeType());
			logger.info("className:"+className);
			if(allIncomeList.size() == 0){
			IncomeCategoryDataM incomeCategoryM = (IncomeCategoryDataM)(Class.forName(className)).newInstance();
			incomeCategoryM.setIncomeId(incomeM.getIncomeId());
			incomeCategoryM.setSeq(isContainIncomeTypeException?allIncomeList.size():allIncomeList.size()+1);
			incomeCategoryM.setIncomeType(incomeM.getIncomeType());
			if(incomeCategoryM instanceof SalaryCertDataM){
				SalaryCertDataM salartCertM = (SalaryCertDataM)incomeCategoryM;
				salartCertM.setCompanyName(workAddress.getCompanyName());
			}
			allIncomeList.add(incomeCategoryM);
			logger.info("seq:"+allIncomeList.size());
			}
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}

