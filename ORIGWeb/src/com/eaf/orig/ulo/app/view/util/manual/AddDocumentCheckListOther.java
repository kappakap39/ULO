package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;

public class AddDocumentCheckListOther implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(AddDocumentCheckListOther.class);
	ArrayList<HashMap> masterDocResonMList = CacheControl.getCacheList(SystemConstant.getConstant("CACH_NAME_DOCUMENT_REASON"));	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_DOCUMENT_CHECKLIST_OTHER);
		String data = "DOCUMENT_CHECK_LIST_OTHERS";
		try {
			String DOCUMENT_CODE = request.getParameter("DOCUMENT_CODE");
			String APPLICANT_TYPE = request.getParameter("APPLICANT_TYPE");
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ArrayList<DocumentCheckListDataM> docCheckLists = ORIGForm.getObjectForm().getDocumentCheckLists();
			if(Util.empty(docCheckLists)) {
				docCheckLists = new ArrayList<DocumentCheckListDataM>();
			}
			DocumentCheckListDataM docCheckList  = new DocumentCheckListDataM();
			docCheckList.setDocumentCode(DOCUMENT_CODE);
			docCheckList.setApplicantType(APPLICANT_TYPE);
			docCheckList.setDocumentCheckListReasons(getDocListResons(masterDocResonMList,DOCUMENT_CODE));
			docCheckLists.add(docCheckList);						
			ORIGForm.getObjectForm().setDocumentCheckLists(docCheckLists);
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}	
	private ArrayList<DocumentCheckListReasonDataM>  getDocListResons(ArrayList<HashMap> masterDocResonMList,String documentCode){		
		ArrayList<DocumentCheckListReasonDataM> docResonsList = new ArrayList<DocumentCheckListReasonDataM>();			
		DocumentCheckListReasonDataM  docCheckListResons = new DocumentCheckListReasonDataM();			
		for(HashMap hData :masterDocResonMList ){
			docCheckListResons = new DocumentCheckListReasonDataM();
			String RESONS_DOCUMENT_CODE = (String) hData.get("DOCUMENT_CODE");
			String DOC_LIST_REASON_ID = (String) hData.get("REASON_ID");				
			logger.debug("##Document resons ##"+hData);				
			if(documentCode.equals(RESONS_DOCUMENT_CODE)){
				docCheckListResons.setDocReason(DOC_LIST_REASON_ID);					
				docResonsList.add(docCheckListResons);
			}
		}		
		return docResonsList;
	}
}
