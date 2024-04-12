package com.eaf.orig.ulo.app.view.util.manual;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;

public class DeleteDocumentCheckListOther implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DeleteDocumentCheckListOther.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_DOCUMENT_CHECKLIST_OTHERS);
		String data = "DOCUMENT_CHECK_LIST_OTHERS";
		try{
			String SEQ = request.getParameter("ROW_SEQ");
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ArrayList<DocumentCheckListDataM> docCheckLists = ORIGForm.getObjectForm().getDocumentCheckLists();		
			if(!Util.empty(docCheckLists)){
				Iterator<DocumentCheckListDataM> iterator = docCheckLists.iterator();
				while(iterator.hasNext()){
					DocumentCheckListDataM docCheckList = iterator.next();
					if(!Util.empty(SEQ)&& SEQ.equals(FormatUtil.toString(docCheckList.getSeq()))){
						iterator.remove();	
					}
				}	
			}
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
