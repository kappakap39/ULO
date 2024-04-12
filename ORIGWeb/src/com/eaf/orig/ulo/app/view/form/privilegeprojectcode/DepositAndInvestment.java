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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeProductSavingDataM;

public class DepositAndInvestment extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DepositAndInvestment.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/product/DepositAndInvestmentSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		int PROJECT_DOC_ASSET_INDEX=0;
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;		
		if(Util.empty(privilegeProjectCode)){
			privilegeProjectCode = new PrivilegeProjectCodeDataM();
		}

		ArrayList<PrivilegeProjectCodeProductSavingDataM>  prvlgProjectCodeProductSavings =  privilegeProjectCode.getPrivilegeProjectCodeProductSavings();
		if(!Util.empty(prvlgProjectCodeProductSavings)){
			for(PrivilegeProjectCodeProductSavingDataM prvlgProjectCodeProductSaving :prvlgProjectCodeProductSavings){
				 String SEQ =FormatUtil.getString(prvlgProjectCodeProductSaving.getSeq());
				 String PRODUCT_TYPE = request.getParameter("PRODUCT_TYPE_"+SEQ);
				 String ACCOUNT_NO = request.getParameter("ACCOUNT_NO_"+SEQ);
				 String HOLDING_RATIO = request.getParameter("HOLDING_RATIO_"+SEQ);
				 String ACCOUNT_BALANCE = request.getParameter("ACCOUNT_BALANCE_"+SEQ);
				 String ACCOUNT_BALANCE_CURRENT = request.getParameter("ACCOUNT_BALANCE_CURRENT_"+SEQ);
				 String CIS_NUMBER = request.getParameter("CIS_NUMBER_"+SEQ);
				 String RELATIONSHIP_TRANSFER = request.getParameter("RELATIONSHIP_TRANSFER_"+SEQ);
				 logger.debug("SEQ >> "+SEQ);
				 logger.debug("PRODUCT_TYPE >> "+PRODUCT_TYPE);
				 logger.debug("ACCOUNT_NO >> "+ACCOUNT_NO);
				 logger.debug("HOLDING_RATIO >> "+HOLDING_RATIO);
				 logger.debug("ACCOUNT_BALANCE >> "+ACCOUNT_BALANCE);
				 logger.debug("ACCOUNT_BALANCE_CURRENT >> "+ACCOUNT_BALANCE_CURRENT);
				 logger.debug("CIS_NUMBER >> "+ CIS_NUMBER);
				 logger.debug("RELATIONSHIP_TRANSFER >> "+RELATIONSHIP_TRANSFER);
				 
				 prvlgProjectCodeProductSaving.setProductType(PRODUCT_TYPE);
				 prvlgProjectCodeProductSaving.setAccountNo(ACCOUNT_NO);
				 prvlgProjectCodeProductSaving.setHoldingRatio(!Util.empty(HOLDING_RATIO)?FormatUtil.toBigDecimal(HOLDING_RATIO):null);
				 prvlgProjectCodeProductSaving.setCisNo(CIS_NUMBER);
				 prvlgProjectCodeProductSaving.setRelationshipTransfer(RELATIONSHIP_TRANSFER);
				 prvlgProjectCodeProductSaving.setAccountBalance(!Util.empty(ACCOUNT_BALANCE_CURRENT)?FormatUtil.toBigDecimal(ACCOUNT_BALANCE_CURRENT):null);
				 prvlgProjectCodeProductSaving.setAccountBalanceStart(!Util.empty(ACCOUNT_BALANCE)?FormatUtil.toBigDecimal(ACCOUNT_BALANCE):null);
				 
				 PrivilegeProjectCodeDocDataM privilegeProjectDoc =  privilegeProjectCode.getPrivilegeProjectCodeDoc(PROJECT_DOC_ASSET_INDEX);
				 
				 privilegeProjectDoc.setPrivilegeProjectCodeKassetDocs(null);
				 privilegeProjectDoc.setPrivilegeProjectCodeProductTradings(null);
				 privilegeProjectDoc.setPrivilegeProjectCodeExceptionDocs(null);
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		try {
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectElement;
			ArrayList<PrivilegeProjectCodeProductSavingDataM>  prvlgProjectCodeProductSavings =  privilegeProjectCode.getPrivilegeProjectCodeProductSavings();
			if(!Util.empty(prvlgProjectCodeProductSavings)){
				for(PrivilegeProjectCodeProductSavingDataM prvlgProjectCodeProductSaving :prvlgProjectCodeProductSavings){
					 String SEQ = FormatUtil.getString(prvlgProjectCodeProductSaving.getSeq());					 
					 formError.mandatory(subformId,"TYPE_OF_DEPOSIT", "PRODUCT_TYPE_"+SEQ,"",prvlgProjectCodeProductSaving.getProductType(), request);		 
					 formError.mandatory(subformId,"ACCOUNT_NO", "ACCOUNT_NO_"+SEQ,"",prvlgProjectCodeProductSaving.getAccountNo(), request);		 
					 formError.mandatory(subformId,"OWNER_PART", "HOLDING_RATIO_"+SEQ,"",prvlgProjectCodeProductSaving.getHoldingRatio(), request);		 
					 formError.mandatory(subformId,"AMOUNT_OF_MONEY", "ACCOUNT_BALANCE_CURRENT_"+SEQ,"",prvlgProjectCodeProductSaving.getAccountBalance(), request);
					 formError.mandatory(subformId,"CIS_NUMBER", "CIS_NUMBER_"+SEQ,"",prvlgProjectCodeProductSaving.getCisNo(), request);
					 formError.mandatory(subformId,"RELATIONSHIP_TRANSFER", "RELATIONSHIP_TRANSFER_"+SEQ,"",prvlgProjectCodeProductSaving.getRelationshipTransfer(), request);
//					 AMOINT_OF_MONEY_START
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
