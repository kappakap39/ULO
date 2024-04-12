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
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class DocumentForTransfer extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentForTransfer.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/document/DocumentForTransferSubForm.jsp";
//		return "/orig/ulo/subform/projectcode/product/VerifyPrivilegeProductProjectCodeSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {	
		
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc  =  ((PrivilegeProjectCodeDocDataM)objectElement);	
 
		ArrayList<PrivilegeProjectCodeTransferDocDataM> 	prvlgProjectCodeTransferList =	privilegeProjectCodeDoc.getPrivilegeProjectCodeTransferDocs();
		if(!Util.empty(prvlgProjectCodeTransferList)){
				for(PrivilegeProjectCodeTransferDocDataM prvlgProjectCodeTransfer : prvlgProjectCodeTransferList){
					String SEQ = FormatUtil.getString(prvlgProjectCodeTransfer.getSeq());
					
					String INVEST_TYPE = request.getParameter("INVEST_TYPE_"+SEQ);
					String ACCOUNT_NO = request.getParameter("ACCOUNT_NO_"+SEQ);
					String HOLDING_RATIO = request.getParameter("HOLDING_RATIO_"+SEQ);
					String AMOUNT = request.getParameter("AMOUNT_"+SEQ);
					String ID_NO = request.getParameter("ID_NO_"+SEQ);
					String CIS_NO = request.getParameter("CIS_NO_"+SEQ);
					String RELATIONSHIP = request.getParameter("RELATIONSHIP_"+SEQ);
					
					prvlgProjectCodeTransfer.setInvestType(INVEST_TYPE);
					prvlgProjectCodeTransfer.setAccountNo(ACCOUNT_NO);
					prvlgProjectCodeTransfer.setHoldingRatio(null!=HOLDING_RATIO && !"".equals(HOLDING_RATIO)?FormatUtil.toBigDecimal(HOLDING_RATIO):null);
					prvlgProjectCodeTransfer.setAmount(null!=AMOUNT && !"".equals(AMOUNT)?FormatUtil.toBigDecimal(AMOUNT):null);
					prvlgProjectCodeTransfer.setIdNo(ID_NO);
					prvlgProjectCodeTransfer.setCisNo(CIS_NO);
					prvlgProjectCodeTransfer.setRelationship(RELATIONSHIP);		
				}
				privilegeProjectCodeDoc.setPrivilegeProjectCodeExceptionDocs(null);
				privilegeProjectCodeDoc.setPrivilegeProjectCodeKassetDocs(null);
				privilegeProjectCodeDoc.setPrivilegeProjectCodeMGMDocs(null);
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
		FormErrorUtil formError = null;
		try {
			PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc  =  ((PrivilegeProjectCodeDocDataM)objectElement);	
			ArrayList<PrivilegeProjectCodeTransferDocDataM> prvlgProjectCodeTransferList =	privilegeProjectCodeDoc.getPrivilegeProjectCodeTransferDocs();
			if(!Util.empty(prvlgProjectCodeTransferList)){
				formError = new FormErrorUtil();
				for(PrivilegeProjectCodeTransferDocDataM prvlgProjectCodeTransfer:prvlgProjectCodeTransferList){
					String SEQ = FormatUtil.getString(prvlgProjectCodeTransfer.getSeq());
					
					logger.debug("===getHoldingRatio>"+prvlgProjectCodeTransfer.getHoldingRatio());
					logger.debug("===getAmount>"+prvlgProjectCodeTransfer.getAmount());
					formError.mandatory(subformId,"TYPE_OF_DEPOSIT", "INVEST_TYPE_"+SEQ,prvlgProjectCodeTransfer.getInvestType(), request);
					formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+SEQ,prvlgProjectCodeTransfer.getAccountNo(), request);
					formError.mandatory(subformId,"OWNER_PART","HOLDING_RATIO_"+SEQ,prvlgProjectCodeTransfer.getHoldingRatio(), request);
					formError.mandatory(subformId,"AMOUNT_OF_MONEY","AMOUNT_"+SEQ,prvlgProjectCodeTransfer.getAmount(), request);
					formError.mandatory(subformId,"ID_CARD_NO","ID_NO_"+SEQ,prvlgProjectCodeTransfer.getIdNo(), request);
					formError.mandatory(subformId,"CIS_NO","CIS_NO_"+SEQ,prvlgProjectCodeTransfer.getCisNo(), request);
					formError.mandatory(subformId,"RELATION_OF_DOC_TRANSFER","RELATIONSHIP_"+SEQ,prvlgProjectCodeTransfer.getRelationship(), request);
				}
			}else{
				formError = new FormErrorUtil();
				formError.mandatory(subformId, "DOCUMENT_FOR_TRANSFER_TABLE", prvlgProjectCodeTransferList, request);
			}	 
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
