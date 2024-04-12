package com.eaf.orig.ulo.app.view.form.popup.comment.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.NotePadDataM;

public class DelCommentPopup implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DelCommentPopup.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		String subFormId = request.getParameter("subFormId");
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_NOTEPAD);
		try{
			String DEL_SEQ[] = request.getParameter("SEQ").split(",");
			ORIGFormHandler	ORIGForm	=	(ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ArrayList<String> seqList = new ArrayList<String>(Arrays.asList(DEL_SEQ));
			ArrayList<NotePadDataM> notepads = ORIGForm.getObjectForm().getNotePads();			
			Iterator<NotePadDataM> iterator = notepads.iterator();
			while(iterator.hasNext()){
				NotePadDataM notePad = iterator.next();
				if(!Util.empty(seqList)&& seqList.contains(FormatUtil.toString(notePad.getSeq())) ){
					if(null!=notePad.getApplicationGroupId()){
						notePad.setStatus(MConstant.FLAG_N);
					}else{
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
