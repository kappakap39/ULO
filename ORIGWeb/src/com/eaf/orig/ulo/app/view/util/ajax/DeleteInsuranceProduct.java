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
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;

public class DeleteInsuranceProduct implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteInsuranceProduct.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_PRVLG_PROJECTCODE_INSURANCE);
		try{
			String subFormID = request.getParameter("subformId");
			String SEQ = request.getParameter("SEQ");			
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();		
			if(Util.empty(privilegeProjectCode)){
				privilegeProjectCode = new PrivilegeProjectCodeDataM();
			}	
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM>  prvlgProjectCodeProductInsurances=  privilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
			Iterator<PrivilegeProjectCodeProductInsuranceDataM> iterator = prvlgProjectCodeProductInsurances.iterator();
			int count=0;
			while(iterator.hasNext()){
				PrivilegeProjectCodeProductInsuranceDataM privilegeProjectCodeProductInsurance = iterator.next();
				if(privilegeProjectCodeProductInsurance.getSeq()==FormatUtil.getInt(SEQ)){
					iterator.remove();
				}else{
					int newSeq  = count++;
					privilegeProjectCodeProductInsurance.setSeq(newSeq);
				}
			}
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
