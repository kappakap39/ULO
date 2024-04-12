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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;

public class AddInsuranceProduct implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddInsuranceProduct.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_INSURANCE_PRODUCT);
		try{
			String subFormID = request.getParameter("subformId");
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();		
			if(Util.empty(privilegeProjectCode)){
				privilegeProjectCode = new PrivilegeProjectCodeDataM();
			}	
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM>  prvlgProjectCodeProductInsurances=  privilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
			if(Util.empty(prvlgProjectCodeProductInsurances)){
				prvlgProjectCodeProductInsurances = new ArrayList<PrivilegeProjectCodeProductInsuranceDataM>();
				privilegeProjectCode.setPrivilegeProjectCodeProductInsurances(prvlgProjectCodeProductInsurances);
			}			
			PrivilegeProjectCodeProductInsuranceDataM  prvlgProjectCodeProductInsurance =  new PrivilegeProjectCodeProductInsuranceDataM();
			prvlgProjectCodeProductInsurance.setSeq(prvlgProjectCodeProductInsurances.size());
			prvlgProjectCodeProductInsurances.add(prvlgProjectCodeProductInsurance);
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
