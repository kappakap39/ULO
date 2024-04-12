package com.eaf.orig.ulo.pl.ajax;

import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.ejb.PLORIGApplicationManager;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.orig.bpm.ulo.model.WorkflowDataM;

public class ButtonOnJobAction implements AjaxDisplayGenerateInf {
	Logger logger = Logger.getLogger(this.getClass());	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ProcessMenuM menuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
		if(menuM == null) menuM = new ProcessMenuM();
		
		String buttonStatus = request.getParameter("statusButton");		
		logger.debug("buttonStatus >> "+buttonStatus);
		
		WorkflowDataM workflowM = new WorkflowDataM();
			workflowM.setUserID(userM.getUserName());
			workflowM.setStatus(buttonStatus);
			workflowM.setUserRole(userM.getCurrentRole());
			workflowM.setTdID(menuM.getMenuID());			
		try{			
			PLORIGApplicationManager origManager = PLORIGEJBService.getPLORIGApplicationManager();
			String statusJob = origManager.SetButtonStatus(workflowM, userM);
			
			if(OrigConstant.FLAG_Y.equals(statusJob)){
				return HTMLRenderUtil.displayButtonTagScriptAction(OrigConstant.StatusOnJob.ON
								, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button", "button-job-wf", "button", "onclick=\"ChangeButtonStatus()\"", "");			
			}else{
				return HTMLRenderUtil.displayButtonTagScriptAction(OrigConstant.StatusOnJob.OFF
						, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button", "button-job-wf", "button-red", "onclick=\"ChangeButtonStatus()\"", "");	
			}			
		}catch(EJBException e){
			logger.error("Error "+e.getMessage());
		}catch(Exception e){
			logger.error("Error "+e.getMessage());
		}
		return "";
	}

}
