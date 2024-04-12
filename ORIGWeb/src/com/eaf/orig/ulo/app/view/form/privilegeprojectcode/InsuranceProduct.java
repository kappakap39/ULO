package com.eaf.orig.ulo.app.view.form.privilegeprojectcode;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductInsuranceDataM;

public class InsuranceProduct extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(InsuranceProduct.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/product/InsuranceProductSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {	
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;	
		if(Util.empty(privilegeProjectCode)){
			privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}
		
		ArrayList<PrivilegeProjectCodeProductInsuranceDataM> prvlgProjectCodeProductInsurances = privilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
		if(!Util.empty(prvlgProjectCodeProductInsurances)){
			for(PrivilegeProjectCodeProductInsuranceDataM  prvlgProjectCodeProductInsurance :prvlgProjectCodeProductInsurances){
			String SEQ = FormatUtil.getString(prvlgProjectCodeProductInsurance.getSeq());
			 String INSURANCE_TYPE = request.getParameter("INSURANCE_TYPE_"+SEQ);
			 String POLICY_NO = request.getParameter("POLICY_NO_"+SEQ);
			 String PREMIUM = request.getParameter("PREMIUM_"+SEQ);
			 String CIS_NUMBER_INSURANCE = request.getParameter("CIS_NUMBER_INSURANCE_"+SEQ);
			 String RELATIONSHIP_TRANSFER_INSURANCE = request.getParameter("RELATIONSHIP_TRANSFER_INSURANCE_"+SEQ);
			 												
			 logger.debug("SEQ >>"+SEQ);
			 logger.debug("INSURANCE_TYPE >>"+INSURANCE_TYPE);
			 logger.debug("POLICY_NO >>"+POLICY_NO);
			 logger.debug("PREMIUM >>"+PREMIUM);
			 logger.debug("CIS_NUMBER_INSURANCE >>"+CIS_NUMBER_INSURANCE);
			 logger.debug("RELATIONSHIP_TRANSFER_INSURANCE >>"+RELATIONSHIP_TRANSFER_INSURANCE);
			 
			 prvlgProjectCodeProductInsurance.setInsuranceType(INSURANCE_TYPE);
			 prvlgProjectCodeProductInsurance.setPolicyNo(POLICY_NO);
			 prvlgProjectCodeProductInsurance.setPremium(!Util.empty(PREMIUM)?FormatUtil.toBigDecimal(PREMIUM):null);
			 prvlgProjectCodeProductInsurance.setCisno(CIS_NUMBER_INSURANCE);
			 prvlgProjectCodeProductInsurance.setRelationTranfer(RELATIONSHIP_TRANSFER_INSURANCE);
			 
			 
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
		FormErrorUtil formError = new FormErrorUtil();
		
		try {
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;
			ArrayList<PrivilegeProjectCodeProductInsuranceDataM> prvlgProjectCodeProductInsurances = privilegeProjectCode.getPrivilegeProjectCodeProductInsurances();
			if(!Util.empty(prvlgProjectCodeProductInsurances)){
				for(PrivilegeProjectCodeProductInsuranceDataM  prvlgProjectCodeProductInsurance :prvlgProjectCodeProductInsurances){
				 String SEQ = FormatUtil.getString(prvlgProjectCodeProductInsurance.getSeq());
				 formError.mandatory(subformId,"TYPE","INSURANCE_TYPE_"+SEQ,"",prvlgProjectCodeProductInsurance.getInsuranceType(), request);
				 formError.mandatory(subformId,"POLICY_CODE","POLICY_NO_"+SEQ,"",prvlgProjectCodeProductInsurance.getPolicyNo(), request);
				 formError.mandatory(subformId,"INSURANCE_PREMIUM_PER_YEAR","PREMIUM_"+SEQ,"",prvlgProjectCodeProductInsurance.getPremium(), request);
				 formError.mandatory(subformId,"CIS_NUMBER_INSURANCE","CIS_NUMBER_INSURANCE_"+SEQ,"",prvlgProjectCodeProductInsurance.getCisno(), request);
				 formError.mandatory(subformId,"RELATIONSHIP_TRANSFER_INSURANCE","RELATIONSHIP_TRANSFER_INSURANCE_"+SEQ,"",prvlgProjectCodeProductInsurance.getRelationTranfer(), request);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
