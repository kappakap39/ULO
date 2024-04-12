package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeExceptionDocDataM;

public class DocumentForOverride extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentForOverride.class);	
	private int PROJECT_DOC_EXC_INDEX=0;
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/document/DocumentForOverrideSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String EXCEPT_POLICY = request.getParameter("EXCEPT_POLICY");
		String EXCEPT_POLICY_OTH = request.getParameter("EXCEPT_POLICY_OTH");
		String EXCEPT_DOC_FROM = request.getParameter("EXCEPT_DOC_FROM");
		
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc = ((PrivilegeProjectCodeDocDataM)objectElement);	 
		ArrayList<PrivilegeProjectCodeExceptionDocDataM>  prvlgProjectExceptions =   privilegeProjectCodeDoc.getPrivilegeProjectCodeExceptionDocs();
		if(Util.empty(prvlgProjectExceptions)){
			prvlgProjectExceptions = new ArrayList<PrivilegeProjectCodeExceptionDocDataM>();
			
		}
	
		PrivilegeProjectCodeExceptionDocDataM  prvlgProjectException =privilegeProjectCodeDoc.getPrivilegeProjectCodeExceptionDoc(PROJECT_DOC_EXC_INDEX);
		if(Util.empty(prvlgProjectException)){
			prvlgProjectException  = new PrivilegeProjectCodeExceptionDocDataM();
			prvlgProjectExceptions.add(PROJECT_DOC_EXC_INDEX,prvlgProjectException);
			
		}
		prvlgProjectException.setExceptPolicy(EXCEPT_POLICY);
		prvlgProjectException.setExceptPolicyOth(EXCEPT_POLICY_OTH);
		prvlgProjectException.setExceptDocFrom(EXCEPT_DOC_FROM);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeExceptionDocs(prvlgProjectExceptions);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeKassetDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeMGMDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeTransferDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeProductTradings(null);
		
		return null;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
		String EXCEPT_POLICY = request.getParameter("EXCEPT_POLICY");
		String EXCEPT_POLICY_OTH = request.getParameter("EXCEPT_POLICY_OTH");
		String EXCEPT_DOC_FROM = request.getParameter("EXCEPT_DOC_FROM");
		FormErrorUtil formError = new FormErrorUtil();
		try {
			PrivilegeProjectCodeExceptionDocDataM  prvlgProjectException = new PrivilegeProjectCodeExceptionDocDataM();
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc  = (PrivilegeProjectCodeDocDataM)objectElement;
			ArrayList<PrivilegeProjectCodeExceptionDocDataM>  prvlgProjectExceptions =   privilegeProjectCodeDoc.getPrivilegeProjectCodeExceptionDocs();
			
			if(Util.empty(EXCEPT_POLICY) && Util.empty(EXCEPT_POLICY_OTH) && Util.empty(EXCEPT_DOC_FROM)){
//				formError = new FormErrorUtil("PREFIX_ERROR_3", request); DF000000000594 ERR MSG : Incorrect error message
				formError.mandatory(subformId,"DOCUMENT_FOR_OVERRIDE","", request);
			}else{
				formError = new FormErrorUtil();
				if(!Util.empty(prvlgProjectExceptions)){
					 prvlgProjectException = privilegeProjectCodeDoc.getPrivilegeProjectCodeExceptionDoc(PROJECT_DOC_EXC_INDEX);
				}	
				formError.mandatory(subformId,"UNDER_STANDART", "EXCEPT_POLICY",prvlgProjectException.getExceptPolicy(), request);
				if(SystemConstant.getConstant("PRVLG_EXCEPT_POLICY_OTHER").equals(prvlgProjectException.getExceptPolicy())){
					formError.mandatory(subformId,"UNDER_STANDART","EXCEPT_POLICY_OTH",prvlgProjectException.getExceptPolicyOth(), request);
				}
				formError.mandatory(subformId,"DEFERENCE_DOCUMENT","EXCEPT_DOC_FROM",prvlgProjectException.getExceptDocFrom(), request);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
