package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAO;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAOImpl;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class VerIncomeIsKBankEmployee  implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(VerIncomeIsKBankEmployee.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VALIDATE_KBANK_EMPLOYEE);
		String isKbankEmp = "false";
		try {
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			PersonalInfoDataM personalInfo =PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			String idNo = personalInfo.getIdno();
			DihDAO dihDAO = new DihDAOImpl();
			logger.debug("idNo : "+idNo);
			if(!Util.empty(idNo)){
				isKbankEmp = String.valueOf(dihDAO.isKbankEmployee(idNo));
			}
			return responseData.success(isKbankEmp);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
