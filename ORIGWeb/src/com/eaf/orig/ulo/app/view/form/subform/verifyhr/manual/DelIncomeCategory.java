package com.eaf.orig.ulo.app.view.form.subform.verifyhr.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.OtherIncomeDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DelIncomeCategory implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DelIncomeCategory.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_INCOME_CATEGORY);
		try{
			String subFormID = request.getParameter("subformId");
			String seq =  request.getParameter("SEQ") ;
			logger.debug("SEQ >> "+seq);
			int incomeInfoSeq =0;
			if(!Util.empty(seq)){
				incomeInfoSeq = Integer.parseInt(seq);
			}
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PersonalInfoDataM personal = (PersonalInfoDataM)entityForm.getObjectForm();	
			ArrayList<IncomeInfoDataM> incomeInfos = personal.getIncomeInfos();
	
			if(!Util.empty(incomeInfos)){
				for(IncomeInfoDataM incomeInfo:incomeInfos){
					ArrayList<IncomeCategoryDataM> AllIncomes = incomeInfo.getAllIncomes();
					logger.debug("AllIncomes >>> "+AllIncomes.size());
					if(!Util.empty(AllIncomes)){
						for(int i=0;i<AllIncomes.size();i++){
							if(AllIncomes.get(i) instanceof OtherIncomeDataM){
								if(!Util.empty(incomeInfoSeq) && AllIncomes.get(i).getSeq() ==incomeInfoSeq){
									logger.debug("AllIncomes.get(i).getSeq() >> "+AllIncomes.get(i).getSeq());
									AllIncomes.remove(i);
								}
							}
						}
					}
				}
			}
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
