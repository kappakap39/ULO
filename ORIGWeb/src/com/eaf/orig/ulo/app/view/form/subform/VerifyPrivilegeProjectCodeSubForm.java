package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;

public class VerifyPrivilegeProjectCodeSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(VerifyPrivilegeProjectCodeSubForm.class);
	private int PRVLG_PROJECT_CODE_INDEX=0;
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String PROJECT_CODE = request.getParameter("RCC_PROJECT_CODE");
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)appForm;	
		if(Util.empty(privilegeProjectCode)){
			privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}	
		/*ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>  projectRccList = privilegeProjectCode.getPrivilegeProjectCodePrjCdes();	
		if(Util.empty(projectRccList)){
			projectRccList = new ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>();
			projectRccList.add(PRVLG_PROJECT_CODE_INDEX, new PrivilegeProjectCodeRccmdPrjCdeDataM());
			privilegeProjectCode.setPrivilegeProjectCodePrjCdes(projectRccList);
		}
		PrivilegeProjectCodeRccmdPrjCdeDataM projectRcc =    projectRccList.get(PRVLG_PROJECT_CODE_INDEX);
	 
		if(Util.empty(projectRcc)){
			projectRcc  = new PrivilegeProjectCodeRccmdPrjCdeDataM();
			projectRcc.setProjectCode(PROJECT_CODE);
		}*/
		
		logger.debug("#####PROJECT_CODE###"+PROJECT_CODE);
		
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		/*String subformId = "VERIFY_PRIVILEGE_PROJECT_CODE_SUBFORM";
		
		PrivilegeProjectCodeRccmdPrjCdeDataM projectRcc =  new PrivilegeProjectCodeRccmdPrjCdeDataM();
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)appForm;	
		if(!Util.empty(privilegeProjectCode)){
			ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>  projectRccList = privilegeProjectCode.getPrivilegeProjectCodePrjCdes();
			if(!Util.empty(projectRccList)){
				projectRcc = projectRccList.get(PRVLG_PROJECT_CODE_INDEX);
			}
		}	

		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"PROJECT_CODE_DESC","PROJECT_CODE",projectRcc.getProjectCode(), request);
		return formError.getFormError();*/
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {	
		return false;
	}

}
