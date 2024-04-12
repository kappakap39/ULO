package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class AddDocumentForTransfer implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddDocumentForTransfer.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_PRVLG_PROJECTCODE_DOCUMENT);
		try{
			String subFormID = request.getParameter("subformId");
			String PRVLG_PROJECT_DOC_INDEX = request.getParameter("PRVLG_PROJECT_DOC_INDEX");
			String DOCUMENT_TYPE = request.getParameter("DOCUMENT_TYPE");
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();		
			if(Util.empty(privilegeProjectCode)){
				privilegeProjectCode = new PrivilegeProjectCodeDataM();
			}	
			ArrayList<PrivilegeProjectCodeDocDataM> prvlgProjectDocs = privilegeProjectCode.getPrivilegeProjectCodeDocs();
			if(Util.empty(prvlgProjectDocs)){
				prvlgProjectDocs = new  ArrayList<PrivilegeProjectCodeDocDataM>();
				privilegeProjectCode.setPrivilegeProjectCodeDocs(prvlgProjectDocs);
				prvlgProjectDocs.add(new PrivilegeProjectCodeDocDataM());
			}
			
			PrivilegeProjectCodeDocDataM  prvlgProjectDoc = prvlgProjectDocs.get(Integer.parseInt(PRVLG_PROJECT_DOC_INDEX));
			if(DOCUMENT_TYPE.equals(SystemConstant.getConstant("PRVLG_DOC_TYPE_TRANSFER"))){
				prvlgProjectDoc.setDocType(DOCUMENT_TYPE);
				
				ArrayList<PrivilegeProjectCodeTransferDocDataM> prvlProjectDocTransfers= prvlgProjectDoc.getPrivilegeProjectCodeTransferDocs();
				if(Util.empty(prvlProjectDocTransfers)){
					prvlProjectDocTransfers = new ArrayList<PrivilegeProjectCodeTransferDocDataM>();
					prvlgProjectDoc.setPrivilegeProjectCodeTransferDocs(prvlProjectDocTransfers);
				}
				PrivilegeProjectCodeTransferDocDataM  prvlgProjectTransferDoc =  new PrivilegeProjectCodeTransferDocDataM();
				prvlgProjectTransferDoc.setSeq(prvlProjectDocTransfers.size());
				prvlProjectDocTransfers.add(prvlgProjectTransferDoc);
			}
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
