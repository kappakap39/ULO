package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeTransferDocDataM;

public class DeleteDocumentForTransfer implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DeleteDocumentForTransfer.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_PRVLG_PROJECTCODE_DOCUMENT);
		try{
			String subFormID = request.getParameter("subformId");
			String SEQ = request.getParameter("SEQ");
			String PRVLG_PROJECT_DOC_INDEX = request.getParameter("PRVLG_PROJECT_DOC_INDEX");
			
			EntityFormHandler entityForm = (EntityFormHandler) request.getSession().getAttribute("EntityForm");
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();		
			ArrayList<PrivilegeProjectCodeDocDataM> prvlgProjectDocs = privilegeProjectCode.getPrivilegeProjectCodeDocs();		
			PrivilegeProjectCodeDocDataM  prvlgProjectDoc = prvlgProjectDocs.get(Integer.parseInt(PRVLG_PROJECT_DOC_INDEX));
			ArrayList<PrivilegeProjectCodeTransferDocDataM> prvlProjectDocTransfers= prvlgProjectDoc.getPrivilegeProjectCodeTransferDocs();
			
			Iterator<PrivilegeProjectCodeTransferDocDataM> iterator = prvlProjectDocTransfers.iterator();
			int count=0;
			while(iterator.hasNext()){
				PrivilegeProjectCodeTransferDocDataM prvlProjectDocTransfer =iterator.next();
				if(prvlProjectDocTransfer.getSeq()==FormatUtil.getInt(SEQ)){
					iterator.remove();
				}else{
					prvlProjectDocTransfer.setSeq(count++);
				}
			}
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
