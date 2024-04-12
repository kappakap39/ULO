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
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DeleteIncomeType implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteIncomeType.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_INCOME_BY_TYPE);
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	//		PersonalInfoDataM personal = ORIGForm.getObjectForm().getPersonalInfo(1);
			PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			String seqStr = request.getParameter("seq");
			logger.info("Income Seq :"+seqStr);
			String incomeType = request.getParameter("incomeType");
			logger.info("Income Type :"+incomeType);
			int seq = FormatUtil.getInt(seqStr);
			//IncomeInfoDataM incomeM = personal.getIncomeBySeq(seq);		
			int counter = 0;
			ArrayList<IncomeInfoDataM> incomeList = personal.getIncomeInfos();
			if(!Util.empty(incomeList)) {
				Iterator<IncomeInfoDataM> incomeIter = incomeList.iterator();
				while(incomeIter.hasNext()) {
					IncomeInfoDataM incomeM = incomeIter.next();
					if(incomeM.getSeq() == seq) {
						incomeIter.remove();
					} else {
						incomeM.setSeq(++counter);
					}
				}
			}
			return responseData.success("VERIFY_INCOME_SUBFORM");
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
