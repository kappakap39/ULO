package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;

public class AddDepositAndInvestment implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddDepositAndInvestment.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_DEPOSIT_AND_INVERTMENT);
		try{
			String subFormID = request.getParameter("subformId");
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();		
			if(Util.empty(privilegeProjectCode)){
				privilegeProjectCode = new PrivilegeProjectCodeDataM();
			}
	
			ArrayList<PrivilegeProjectCodeProductSavingDataM>  prvlgProjectCodeProductSavings=  privilegeProjectCode.getPrivilegeProjectCodeProductSavings();
			if(Util.empty(prvlgProjectCodeProductSavings)){
				prvlgProjectCodeProductSavings = new ArrayList<PrivilegeProjectCodeProductSavingDataM>();
				privilegeProjectCode.setPrivilegeProjectCodeProductSavings(prvlgProjectCodeProductSavings);
			}
			
			PrivilegeProjectCodeProductSavingDataM  prvlgProjectCodeProductSaving =  new PrivilegeProjectCodeProductSavingDataM();
			prvlgProjectCodeProductSaving.setSeq(prvlgProjectCodeProductSavings.size());
			prvlgProjectCodeProductSavings.add(prvlgProjectCodeProductSaving);
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
