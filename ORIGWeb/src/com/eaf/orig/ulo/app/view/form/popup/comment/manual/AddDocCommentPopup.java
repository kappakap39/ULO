package com.eaf.orig.ulo.app.view.form.popup.comment.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;

public class AddDocCommentPopup implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddDocCommentPopup.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_DOCUMENT_COMMENT);
		String subFormId = request.getParameter("subFormId");
		try{
			String DOC_COMMENT = request.getParameter("DOC_COMMENT");
			logger.debug("DOC_COMMENT : "+DOC_COMMENT);
			ORIGFormHandler ORIGForm =(ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
			ArrayList<DocumentCommentDataM>  docComments  = ORIGForm.getObjectForm().getDocumentComments();
			logger.debug("ORIGForm DOC_COMMENTs : " + docComments);			
			if(null == docComments){
				docComments = new ArrayList<DocumentCommentDataM>();
				ORIGForm.getObjectForm().setDocumentComments(docComments);
			}
			if(!"".equals(DOC_COMMENT)){
				DocumentCommentDataM  docComment = new DocumentCommentDataM();
				docComment.setCommentDesc(DOC_COMMENT);
				docComment.setUpdateBy(userM.getUserName());
				docComment.setUpdateDate(ApplicationDate.getTimestamp());
				docComment.setCreateBy(userM.getUserName());
				docComment.setCreateDate(ApplicationDate.getTimestamp());
				docComment.setStatus(MConstant.FLAG_Y);
				docComment.setDirty();
				docComments.add(docComment);
			}
			return responseData.success(subFormId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
