package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAO;
import com.eaf.orig.ulo.app.wmtask.WMTaskDAOImpl;

public class RetryWMTask implements AjaxInf {
	
	private static final String WM_TASK = "WM_TASK";

	private static transient Logger logger = Logger.getLogger(RetryWMTask.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request, WM_TASK);
		try {
			String taskId = request.getParameter("TASK_ID");
			WMTaskDAO wmTaskDAO = new WMTaskDAOImpl();
			wmTaskDAO.setRetry(taskId);
			return responseData.success();
		} catch(Exception e) {
			logger.fatal("ERROR ", e);
			return responseData.error();
		}
	}
	
}