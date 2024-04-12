package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public class VerifyPrivilegeDocumentProjectCodeSubForm  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(VerifyPrivilegeDocumentProjectCodeSubForm.class);
	private String PRVLG_DOC_TYPE_CUSTOMER_PRODUCT = SystemConstant.getConstant("PRVLG_DOC_TYPE_CUSTOMER_PRODUCT");
	private int PRVLG_PROJECT_DOC_INDEX=0;
	String subformId="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String DOCUMENT_TYPE = request.getParameter("SELECTED_DOCUMENT_TYPE");
		logger.debug("DOCUMENT_TYPE>>"+DOCUMENT_TYPE);
		PrivilegeProjectCodeDataM privilegeProjectCode = ((PrivilegeProjectCodeDataM)appForm);
		if(Util.empty(privilegeProjectCode)){
			privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}
		
		ArrayList<PrivilegeProjectCodeDocDataM> prvlgProjectDocs = privilegeProjectCode.getPrivilegeProjectCodeDocs();
		if(Util.empty(prvlgProjectDocs)){
			prvlgProjectDocs = new ArrayList<PrivilegeProjectCodeDocDataM>();	
		}
		
		PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
		if(Util.empty(prvlgProjectDoc)){
			prvlgProjectDoc = new PrivilegeProjectCodeDocDataM();	
			prvlgProjectDocs.add(PRVLG_PROJECT_DOC_INDEX,prvlgProjectDoc);
		}
		privilegeProjectCode.setProjectType(DOCUMENT_TYPE);
//		prvlgProjectDoc.setDocType(DOCUMENT_TYPE);		
		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DOCUMENT_OF_PROJECT,"DOCUMENT_OF_PROJECT_"+DOCUMENT_TYPE);
		if(DOCUMENT_TYPE.equals(PRVLG_DOC_TYPE_CUSTOMER_PRODUCT)){
			element.processElement(request, privilegeProjectCode);
		}else{
			element.processElement(request, prvlgProjectDoc);
			clearValueSaveingAndInsurance(privilegeProjectCode);
		}
		privilegeProjectCode.setPrivilegeProjectCodeDocs(prvlgProjectDocs);
	}

	private void clearValueSaveingAndInsurance(PrivilegeProjectCodeDataM privilegeProjectCode){
		privilegeProjectCode.setPrivilegeProjectCodeProductInsurances(null);
		privilegeProjectCode.setPrivilegeProjectCodeProductSavings(null);
		if(!Util.empty(privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_DOC_INDEX))){
			privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_DOC_INDEX).setResult(null);
		}
	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		
		PrivilegeProjectCodeDataM privilegeProjectCode = ((PrivilegeProjectCodeDataM)appForm);
		if(Util.empty(privilegeProjectCode)){
			privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}
		
		PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
		logger.debug("DOC_TYPE:"+privilegeProjectCode.getProjectType());
		if(Util.empty(privilegeProjectCode.getProjectType())){
			formError.mandatory(subformId,"VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM", privilegeProjectCode.getProjectType(), request);
		}else{
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.DOCUMENT_OF_PROJECT,"DOCUMENT_OF_PROJECT_"+privilegeProjectCode.getProjectType());
			HashMap<String,Object> hFormError = null;
			if(PRVLG_DOC_TYPE_CUSTOMER_PRODUCT.equals(privilegeProjectCode.getProjectType())){
				hFormError = element.validateElement(request, privilegeProjectCode);	
			}else{
				hFormError= element.validateElement(request, prvlgProjectDoc);
			}
			if(!Util.empty(hFormError)){
				formError.addAll(hFormError);
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
