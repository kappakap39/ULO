package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class DocumentPrivilegeProductCode extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentPrivilegeProductCode.class);	
	private String[] productOfProjectSubform = SystemConstant.getConstant("PRODUCT_OF_PROJECT").split(",");
	private String subformId="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
	private int PRVLG_PROJECT_DOC_INDEX=0;
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/product/VerifyPrivilegeProductProjectCodeSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {		
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;
		for(int i=0;i<productOfProjectSubform.length;i++){ 
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRODUCT_OF_PROJECT,productOfProjectSubform[i]);
			element.processElement(request, privilegeProjectCode);
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {
		int PRVLG_PROJECT_DOC_INDEX = 0;
		HashMap<String,Object> hError = null;
		FormErrorUtil formError = new FormErrorUtil();
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;
		if(Util.empty(privilegeProjectCode.getPrivilegeProjectCodeProductInsurances()) && Util.empty(privilegeProjectCode.getPrivilegeProjectCodeProductSavings())){
			formError.error(MessageErrorUtil.getText(request, "ERROR_PRODUCT_INSURANCE"));
		}
		for(int i=0;i<productOfProjectSubform.length;i++){ 
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRODUCT_OF_PROJECT,productOfProjectSubform[i]);
			hError = element.validateElement(request, privilegeProjectCode);
			if(!Util.empty(hError)){
				formError.addAll(hError);
			}
		}
		
		return formError.getFormError();
	}
}