package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductCCADataM;

public class CCAInformation extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(CCAInformation.class);	
	private int PRVLG_PROJECT_PRODUCT_CCA_INDEX=0;
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/product/CCAInformationSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String RESULT = request.getParameter("RESULT");
		logger.debug("RESULT >> "+RESULT);
//		if(!Util.empty(RESULT)){ 
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;		
			if(Util.empty(privilegeProjectCode)){
				privilegeProjectCode = new PrivilegeProjectCodeDataM();
			}
			 ArrayList<PrivilegeProjectCodeProductCCADataM>  prvlgProjectCodeProductList =privilegeProjectCode.getPrivilegeProjectCodeProductCCAs();
			 if(Util.empty(prvlgProjectCodeProductList)){
				 prvlgProjectCodeProductList  = new ArrayList<PrivilegeProjectCodeProductCCADataM>();
				 prvlgProjectCodeProductList.add(PRVLG_PROJECT_PRODUCT_CCA_INDEX, new PrivilegeProjectCodeProductCCADataM());
				 privilegeProjectCode.setPrivilegeProjectCodeProductCCAs(prvlgProjectCodeProductList);
			 }
			PrivilegeProjectCodeProductCCADataM prvlgProjectCodeProduct =	privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_PRODUCT_CCA_INDEX);
			if(Util.empty(prvlgProjectCodeProduct)){
				prvlgProjectCodeProduct = new PrivilegeProjectCodeProductCCADataM();
			}
			prvlgProjectCodeProduct.setResult(RESULT);	
//		}
		return null;
	}
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		try {
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;
			PrivilegeProjectCodeProductCCADataM prvlgProjectCodeProduct =	privilegeProjectCode.getPrivilegeProjectCodeProductCCAs(PRVLG_PROJECT_PRODUCT_CCA_INDEX);
			if(Util.empty(prvlgProjectCodeProduct)){
				prvlgProjectCodeProduct = new PrivilegeProjectCodeProductCCADataM();
			}
//			if(!Util.empty(prvlgProjectCodeProduct.getCcaProduct())){
//				formError.mandatoryElement("CCA_RESULT", "CCA_RESULT", prvlgProjectCodeProduct.getResult(), request);
//			}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
