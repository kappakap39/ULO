package com.eaf.orig.ulo.app.view.util.ajax;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class DeleteIncomeCategoryList implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteIncomeCategoryList.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_LIST_INCOME_CATEGORY);
		try{
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = ((IncomeInfoDataM) moduleHandler.getObjectForm());
			String subFormID = request.getParameter("subformId");
			
			String seqVal = request.getParameter("seq");
			logger.info("seqVal:"+seqVal);
			String[] seqSegments = seqVal.split("_");
			String seqStr = seqSegments[1];
			logger.info("DeleteIncomeType seq:"+seqStr);
			int seq = FormatUtil.getInt(seqStr);
			ArrayList<IncomeCategoryDataM> incomeTypelist = incomeM.getAllIncomes();
			if(!Util.empty(incomeTypelist)) {
				Iterator<IncomeCategoryDataM> typeIter = incomeTypelist.iterator();
				int counter = 0;
				while(typeIter.hasNext()) {
					IncomeCategoryDataM incomeCategM = typeIter.next();
					if(incomeCategM.getSeq() == seq) {
						typeIter.remove();
					} else {
						incomeCategM.setSeq(++counter);
					}
				}
			}
			logger.info("DeleteIncomeCategoryList list:"+incomeTypelist.size());
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
