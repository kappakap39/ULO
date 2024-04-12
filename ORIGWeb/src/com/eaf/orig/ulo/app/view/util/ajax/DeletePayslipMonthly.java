package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;

public class DeletePayslipMonthly implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeletePayslipMonthly.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_PAYSLIP_MONTHLY);
		try{
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
			ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
			PayslipDataM payslipM = (PayslipDataM)incomeList.get(0);
			
			ArrayList<PayslipMonthlyDataM> payslipMonthlyList = payslipM.getPayslipMonthlys();
			String subFormID = request.getParameter("subformId");
			String seqVal = request.getParameter("seq");
			logger.info("DeletePayslipMonthly seqVal:"+seqVal);
			String[] seqSegments = seqVal.split("_");
			String seqStr = seqSegments[1];
			logger.info("DeletePayslipMonthly seq:"+seqStr);
			int seq = FormatUtil.getInt(seqStr);
			Iterator<PayslipMonthlyDataM> iter = payslipMonthlyList.iterator();
			int counter = 0;
			while(iter.hasNext()) {
				PayslipMonthlyDataM monthData = iter.next();
				if(monthData.getSeq() == seq) {
					iter.remove();
				} else {
					monthData.setSeq(++counter);
				}
			}
			logger.info("DeletePayslipMonthly list:"+payslipMonthlyList.size());
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
