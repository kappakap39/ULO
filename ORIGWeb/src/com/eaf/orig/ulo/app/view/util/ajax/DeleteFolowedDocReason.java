package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DeleteFolowedDocReason implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DeleteFolowedDocReason.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_DOCUMENT_CHECKLIST_REASON);
		try{
			String subFormId = request.getParameter("subFormId");
			String elementObjectId = request.getParameter("elementObjectId");		
			logger.info("subFormId : "+subFormId);
			logger.info("elementObjectId : "+elementObjectId);
			String[] elementObject = elementObjectId.split("_");
			String personalId = elementObject[2];
			String docCode = elementObject[0];
			logger.debug("personalId : "+personalId);
			logger.debug("docCode : "+docCode);
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
			String applicantTypeIM = PersonalInfoUtil.getIMPersonalType(personalInfo);
			logger.debug("applicantTypeIM : "+applicantTypeIM);
	//		#rawi comment change logic remove document check list.
	//		DocumentCheckListDataM documentCheckList = applicationGroup.getDocumentCheckList(applicantTypeIM,docCode);
	//		logger.debug("documentCheckList : "+documentCheckList);
	//		if(null != documentCheckList){
	//			documentCheckList.setRemark("");
	//			documentCheckList.setDocumentCheckListReasons(new ArrayList<DocumentCheckListReasonDataM>());
	//		}	
			ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckLists();
			if(null != documentCheckLists){
				for (Iterator<DocumentCheckListDataM> iterator = documentCheckLists.iterator(); iterator.hasNext();) {
					DocumentCheckListDataM documentCheckList = iterator.next();
					if(null != docCode && docCode.equals(documentCheckList.getDocumentCode()) 
							&& null != applicantTypeIM && applicantTypeIM.equals(documentCheckList.getApplicantTypeIM())){				
						iterator.remove();
					}
				}
			}
			return responseData.success(subFormId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
