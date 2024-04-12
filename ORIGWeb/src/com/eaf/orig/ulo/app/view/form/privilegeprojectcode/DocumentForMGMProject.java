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
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.exception.EncryptionException;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeMGMDocDataM;

public class DocumentForMGMProject extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentForMGMProject.class);	
	private 	int PRVLG_PROJECT_MGM_INDEX=0;
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		return "/orig/ulo/subform/projectcode/document/DocumentForMGMProjectSubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String REFER_CARD_NO = request.getParameter("REFER_CARD_NO");
		String REFER_CARD_HOLDER_NAME = request.getParameter("REFER_CARD_HOLDER_NAME");
		String encryptedCardDIH = null;	
		PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc = ((PrivilegeProjectCodeDocDataM)objectElement);	 
		
		ArrayList<PrivilegeProjectCodeMGMDocDataM>  prvlgProjectMGMs = privilegeProjectCodeDoc.getPrivilegeProjectCodeMGMDocs();
		if(Util.empty(prvlgProjectMGMs)){
			prvlgProjectMGMs = new ArrayList<PrivilegeProjectCodeMGMDocDataM>();
		}				
		PrivilegeProjectCodeMGMDocDataM prvlgProjectMGM  =	privilegeProjectCodeDoc.getPrivilegeProjectCodeMGMDoc(PRVLG_PROJECT_MGM_INDEX);
		if(Util.empty(prvlgProjectMGM)){
			prvlgProjectMGM = new PrivilegeProjectCodeMGMDocDataM();
			prvlgProjectMGMs.add(PRVLG_PROJECT_MGM_INDEX,prvlgProjectMGM);
			
		}
		if(!Util.empty(REFER_CARD_NO)) {
			REFER_CARD_NO = FormatUtil.removeDash(REFER_CARD_NO);
			Encryptor dihEnc = EncryptorFactory.getDIHEncryptor();
			try {
				encryptedCardDIH = dihEnc.encrypt(REFER_CARD_NO);
			} catch (EncryptionException e) {
				logger.fatal("Encription failed with "+e.getMessage());
				e.printStackTrace();
			}
		}
		prvlgProjectMGM.setReferCardNo(encryptedCardDIH);
		prvlgProjectMGM.setReferCardHolderName(REFER_CARD_HOLDER_NAME);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeMGMDocs(prvlgProjectMGMs);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeExceptionDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeKassetDocs(null);
		privilegeProjectCodeDoc.setPrivilegeProjectCodeTransferDocs(null);
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		String subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
		 
		String REFER_CARD_NO = request.getParameter("REFER_CARD_NO");
		String REFER_CARD_HOLDER_NAME = request.getParameter("REFER_CARD_HOLDER_NAME");
		FormErrorUtil formError = null;
		try {
			if(Util.empty(REFER_CARD_NO) && Util.empty(REFER_CARD_HOLDER_NAME)){
				formError = new FormErrorUtil();
				formError.mandatory(subformId, "DOCUMENT_FOR_MGM_PROJECT", "", request);
			}else{
				formError = new FormErrorUtil("PREFIX_ERROR_3", request);
				PrivilegeProjectCodeMGMDocDataM  prvlgProjectMGM = new PrivilegeProjectCodeMGMDocDataM();
				PrivilegeProjectCodeDocDataM privilegeProjectCodeDoc  = (PrivilegeProjectCodeDocDataM)objectElement;
				ArrayList<PrivilegeProjectCodeMGMDocDataM>  prvlgProjectMGMs = privilegeProjectCodeDoc.getPrivilegeProjectCodeMGMDocs();
				
				if(!Util.empty(prvlgProjectMGMs)){
					 prvlgProjectMGM = privilegeProjectCodeDoc.getPrivilegeProjectCodeMGMDoc(PRVLG_PROJECT_MGM_INDEX);
				}
				formError.mandatory(subformId,"REF_CREDIT_CARD_NO", "REFER_CARD_NO",prvlgProjectMGM.getReferCardNo(), request);
				formError.mandatory(subformId,"CUSTOMER_CARD_NAME", "REFER_CARD_HOLDER_NAME",prvlgProjectMGM.getReferCardHolderName(), request);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
