package com.eaf.orig.ulo.app.view.form.subform.docchecklist;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilter;
import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;

public class DocumentCheckListManualAddAdditional extends ORIGSubForm {
	
	private static transient Logger logger = Logger.getLogger(DocumentCheckListManualAdd.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		String DOC_CODE = SystemConstant.getConstant("DOCUMENT_TYPE_THAI_NATIONALITY");
		String FIELD_ID_FOLLOWED_DOC_REASON = SystemConstant.getConstant("FIELD_ID_FOLLOWED_DOC_REASON");
		String DOC_RELATION_REF_LEVEL_PERSONAL = SystemConstant.getConstant("DOC_RELATION_REF_LEVEL_PERSONAL");
		String CLASS_FOLLOWED_DOC_REASON_FILTER = SystemConstant.getConstant("CLASS_FOLLOWED_DOC_REASON_FILTER");
		String DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL");
		String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
		String ADDITIONAL = SystemConstant.getConstant("ADDITIONAL");
		String FOLLOWED_DOC_REASONFilter = ListBoxFilter.get(CLASS_FOLLOWED_DOC_REASON_FILTER);
		
		ArrayList<HashMap<String,Object>> listFollowedDocReason = null;	
		ListBoxFilterInf FilterInf  = null; 
		try {			        		    	
			FilterInf = (ListBoxFilterInf) Class.forName(FOLLOWED_DOC_REASONFilter).newInstance();			        		    	
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
		}
		if(null != FilterInf) {
			listFollowedDocReason = FilterInf.filter(DOC_CODE, FIELD_ID_FOLLOWED_DOC_REASON, "", request);
		}
		String manualFlag = DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL;
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
		ArrayList<DocumentCheckListDataM> docCheckListManuals;
		docCheckListManuals = applicationGroup.getDocumentCheckListManualAdditionals();
		if(null == docCheckListManuals) {
			docCheckListManuals = new ArrayList<>();
			applicationGroup.setDocumentCheckListManualAdditionals(docCheckListManuals);
		}
		
		int size = docCheckListManuals.size();
		for(int seq = 1; seq <= size; seq++) {
			String suffix = "_" + seq;
			
			DocumentCheckListDataM docCheckListManual = docCheckListManuals.get(seq - 1);
			
			String DOC_LIST_INCOME = request.getParameter("DOC_LIST_" + manualFlag + suffix);
			String FOLLOWED_REMARK = request.getParameter("FOLLOWED_REMARK_" + manualFlag + suffix);
			logger.debug("DOC_LIST_" + manualFlag + " :: " + DOC_LIST_INCOME);
			logger.debug("FOLLOWED_REMARK_" + manualFlag + " :: " + FOLLOWED_REMARK);
			
			DocumentCheckListDataM docCheckList = docCheckListManual;
			String docCheckListId = docCheckListManual.getDocCheckListId();
			if(null == docCheckListId) {
				docCheckList = new DocumentCheckListDataM();				
				docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK );
			}
			docCheckList.setDocCheckListId(docCheckListId);
			docCheckList.setDocumentCode(DOC_LIST_INCOME);
			docCheckList.setRemark(FOLLOWED_REMARK);
			docCheckList.setApplicationGroupId(applicationGroup.getApplicationGroupId());
			docCheckList.setDocTypeId(ADDITIONAL);
			docCheckList.setApplicantTypeIM(IM_PERSONAL_TYPE_APPLICANT);
			docCheckList.setManualFlag(manualFlag);
			
			ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = new ArrayList<>();
			for(HashMap<String,Object> listRadio : listFollowedDocReason) {
				String CODE = SQLQueryEngine.display(listRadio, "CODE");
				String FOLLOWED_DOC_REASON_SELECT = request.getParameter("FOLLOWED_DOC_REASON_SELECT_" + manualFlag + "_" + CODE + suffix);
				logger.debug("FOLLOWED_DOC_REASON_SELECT_" + manualFlag + "_" + CODE + " :: " + FOLLOWED_DOC_REASON_SELECT);
				if(null != FOLLOWED_DOC_REASON_SELECT) {
					DocumentCheckListReasonDataM docCheckListReason = new DocumentCheckListReasonDataM();
					docCheckListReason.setDocCheckListId(docCheckListId);
					docCheckListReason.setDocReason(CODE);
					docCheckListReasons.add(docCheckListReason);
				}
			}
			docCheckList.setDocumentCheckListReasons(docCheckListReasons);
			
			ArrayList<DocumentRelationDataM> newDocRelations = new ArrayList<>();
			String personalId = applicationGroup.getPersonalInfos().get(0).getPersonalId();
			DocumentRelationDataM newDocRelation = new DocumentRelationDataM();
			newDocRelation.setDocCheckListId(docCheckListId);
			newDocRelation.setRefId(personalId);
			newDocRelation.setRefLevel(DOC_RELATION_REF_LEVEL_PERSONAL);
			newDocRelations.add(newDocRelation);
			docCheckList.setDocumentRelations(newDocRelations);
			
			docCheckListManuals.set(seq - 1, docCheckList);
		}
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		
		String DOC_CODE = SystemConstant.getConstant("DOCUMENT_TYPE_THAI_NATIONALITY");
		String FIELD_ID_FOLLOWED_DOC_REASON = SystemConstant.getConstant("FIELD_ID_FOLLOWED_DOC_REASON");
		String CLASS_FOLLOWED_DOC_REASON_FILTER = SystemConstant.getConstant("CLASS_FOLLOWED_DOC_REASON_FILTER");
		String DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL");
		String FOLLOWED_DOC_REASONFilter = ListBoxFilter.get(CLASS_FOLLOWED_DOC_REASON_FILTER);
		
		ArrayList<HashMap<String,Object>> listFollowedDocReason = null;	
		ListBoxFilterInf FilterInf  = null; 
		try {			        		    	
			FilterInf = (ListBoxFilterInf) Class.forName(FOLLOWED_DOC_REASONFilter).newInstance();			        		    	
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
		}
		if(null != FilterInf) {
			listFollowedDocReason = FilterInf.filter(DOC_CODE, FIELD_ID_FOLLOWED_DOC_REASON, "", request);
		}
		String manualFlag = DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL;
		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
		ArrayList<DocumentCheckListDataM> docCheckListManuals = applicationGroup.getDocumentCheckListManualAdditionals();
		if(null == docCheckListManuals) {
			docCheckListManuals = new ArrayList<>();
			applicationGroup.setDocumentCheckListManualAdditionals(docCheckListManuals);
		}
		int size = docCheckListManuals.size();
		for(int seq = 1; seq <= size; seq++) {
			String suffix = "_" + seq;
			
			String DOC_LIST_INCOME = request.getParameter("DOC_LIST_" + manualFlag + suffix);
			
			if(Util.empty(DOC_LIST_INCOME)) {
				formError.error("DOC_LIST_" + manualFlag, MessageErrorUtil.getText(request, "ERROR_ID_ATLEAST_ONE_RECORD"));
			}
			
			boolean isSelect = false;
			for(HashMap<String,Object> listRadio : listFollowedDocReason) {
				String CODE = SQLQueryEngine.display(listRadio, "CODE");
				String FOLLOWED_DOC_REASON_SELECT = request.getParameter("FOLLOWED_DOC_REASON_SELECT_" + manualFlag + "_" + CODE + suffix);
				if(!Util.empty(FOLLOWED_DOC_REASON_SELECT)) {
					isSelect = true;
				}
			}
			if(!isSelect) {
				formError.error("FOLLOWED_DOC_REASON_SELECT_" + manualFlag, MessageErrorUtil.getText(request, "ERROR_ID_ATLEAST_ONE_RECORD"));
			}
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
}
