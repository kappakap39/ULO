package com.eaf.orig.ulo.app.view.form.popup.comment.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.NotePadDataM;

public class AddCommentPopup implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(AddCommentPopup.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		String subFormId = request.getParameter("subFormId");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.ADD_NOTEPAD);
		try{
			String NOTE_PAD_DESC = request.getParameter("NOTE_PAD_DESC");
			logger.debug("NOTE_PAD_DESC : "+NOTE_PAD_DESC);		
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			UserDetailM userM =	(UserDetailM)request.getSession().getAttribute("ORIGUser");
			ArrayList<NotePadDataM> notePads  = ORIGForm.getObjectForm().getNotePads();
			logger.debug("ORIGForm notepad : " + notePads);
			if(Util.empty(notePads)){
				notePads = new ArrayList<NotePadDataM>();
				ORIGForm.getObjectForm().setNotePads(notePads);
			}
			if(!"".equals(NOTE_PAD_DESC)){
				NotePadDataM  notePad= new NotePadDataM();
				notePad.setSeq(notePads.size());
				notePad.setNotePadDesc(NOTE_PAD_DESC);
				notePad.setUpdateBy(userM.getUserName());
				notePad.setUpdateDate(ApplicationDate.getTimestamp());
				notePad.setCreateBy(userM.getUserName());
				notePad.setUserRole(ORIGForm.getRoleId());
				notePad.setCreateDate(ApplicationDate.getTimestamp());
				notePad.setStatus(MConstant.FLAG_Y);
				notePads.add(notePad);
			}
			return responseData.success(subFormId);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
