package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class AddPayslipMonthly implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddPayslipMonthly.class);
	String PAYSLIP_INCOME_VARIABLE = SystemConstant.getConstant("PAYSLIP_INCOME_VARIABLE");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_PAYSLIP_MONTHLY);
		try{
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			String subFormID = request.getParameter("subformId");
			IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
			ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
			if(incomeList == null) {
				incomeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(incomeList);
			}
			PayslipDataM payslipM = null;
			if(incomeList.size() > 0){
				payslipM = (PayslipDataM)incomeList.get(0);
			} else {
				payslipM = new PayslipDataM();
				payslipM.setSeq(incomeList.size() + 1);
				incomeList.add(payslipM);
			}			
			ArrayList<PayslipMonthlyDataM> payslipMonthlyList = payslipM.getPayslipMonthlys();
			if(payslipMonthlyList == null) {
				payslipMonthlyList = new ArrayList<PayslipMonthlyDataM>();
				payslipM.setPayslipMonthlys(payslipMonthlyList);
			}
			PayslipMonthlyDataM monthData = new PayslipMonthlyDataM();
			payslipMonthlyList.add(monthData);
			monthData.setSeq(payslipMonthlyList.size());
			monthData.setIncomeType(PAYSLIP_INCOME_VARIABLE);
			logger.info("AddPayslipMonthly seq:"+payslipMonthlyList.size());
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
