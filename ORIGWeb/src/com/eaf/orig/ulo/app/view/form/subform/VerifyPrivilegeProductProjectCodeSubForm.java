package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public class VerifyPrivilegeProductProjectCodeSubForm  extends ORIGSubForm{
	private String[] productOfProjectSubform = SystemConstant.getConstant("PRODUCT_OF_PROJECT").split(",");
	private String subformId="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
	private int PRVLG_PROJECT_DOC_INDEX=0;
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)appForm;
		for(int i=0;i<productOfProjectSubform.length;i++){ 
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRODUCT_OF_PROJECT,productOfProjectSubform[i]);
			element.processElement(request, privilegeProjectCode);
		}
	}

	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)appForm;
		FormErrorUtil formError = new FormErrorUtil();
		for(int i=0;i<productOfProjectSubform.length;i++){ 
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRODUCT_OF_PROJECT,productOfProjectSubform[i]);
			HashMap<String,Object>  errorList= element.validateElement(request, privilegeProjectCode);
			if(!Util.empty(errorList)){
				formError.addAll(errorList);
			}
		}
		
//		FormErrorUtil formError2 = new FormErrorUtil("PREFIX_ERROR_3", request); DF000000000594 ERR MSG : Incorrect error message
		FormErrorUtil formError2 = new FormErrorUtil();
		PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);		
		if(SystemConstant.getConstant("PRVLG_DOC_TYPE_NO_DOC").equals(prvlgProjectDoc.getDocType())){
			if((null==privilegeProjectCode.getPrivilegeProjectCodeProductSavings() || privilegeProjectCode.getPrivilegeProjectCodeProductSavings().size()==0)
				&& (null==privilegeProjectCode.getPrivilegeProjectCodeProductInsurances() || privilegeProjectCode.getPrivilegeProjectCodeProductInsurances().size()==0 )
				&& (null==privilegeProjectCode.getPrivilegeProjectCodeProductCCAs() || privilegeProjectCode.getPrivilegeProjectCodeProductCCAs().size()==0)){
				formError2.mandatory(subformId,"PRVL_PRODUCT_PROJECT_CODE_SUBFORM_SECTION",privilegeProjectCode.getPrivilegeProjectCodeProductSavings(), request);
				formError.addAll(formError2.getFormError());		
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
