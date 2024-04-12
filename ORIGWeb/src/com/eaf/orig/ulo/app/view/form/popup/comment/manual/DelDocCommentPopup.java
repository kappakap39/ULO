package com.eaf.orig.ulo.app.view.form.popup.comment.manual;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;

public class DelDocCommentPopup implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DelDocCommentPopup.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_DOCUMENT_COMMENT);
		String docCommentDate = request.getParameter("docCommentId");
		String subFormId = request.getParameter("subFormId");
		try{
			ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ArrayList<DocumentCommentDataM> documentComments = ORIGForm.getObjectForm().getDocumentComments();
			Iterator<DocumentCommentDataM> iterator = documentComments.iterator();
			while(iterator.hasNext()){
				DocumentCommentDataM documentComment = iterator.next();
				if(!Util.empty(docCommentDate) && compare(docCommentDate, documentComment.getCreateDate())){
					if(documentComment.isDirty()){
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
	private boolean compare(String docCommentDate, Timestamp createDate) {
		if(docCommentDate.equals(String.valueOf(createDate.getTime()))){
			return true;
		}else{
			return false;
		}
	}
}
