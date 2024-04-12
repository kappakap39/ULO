package com.eaf.orig.ulo.app.view.util.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.InboxDAO;
import com.eaf.orig.ulo.app.dao.WorkflowDAOFactory;

public class InboxAjax implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(InboxAjax.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.INBOX_PROCESS);
		try{
			UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
			String INBOX_FLAG = request.getParameter("INBOX_FLAG");
			logger.debug("INBOX_FLAG >> "+INBOX_FLAG);
			InboxDAO dao = WorkflowDAOFactory.getInboxDAO();
			dao.updateTableUserInboxInfo(userM.getUserName(),INBOX_FLAG);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
