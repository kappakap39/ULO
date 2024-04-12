package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;
//import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class GetTargetCurrent implements AjaxDisplayGenerateInf {
	private static Logger log = Logger.getLogger(GetTargetCurrent.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String userName = request.getParameter("username");
		String role =  request.getParameter("role");
		
		JsonObjectUtil json = new JsonObjectUtil();
		if(OrigUtil.isEmptyString(userName)||OrigUtil.isEmptyString(role)){
			json.CreateJsonObject("target-point-day", "-");
			json.CreateJsonObject("total-job-done", "-");
			return json.returnJson();
		}
		
		String menuID = request.getParameter("MenuID");		
		if (ORIGUtility.isEmptyString(menuID)) {
			ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
			if(currentMenuM == null) currentMenuM = new ProcessMenuM();;
			menuID = currentMenuM.getMenuID();
		}
		
		if(OrigConstant.ROLE_DF_REJECT.equals(role)){
			role = OrigConstant.ROLE_DF;
		}		
		if("DC101".equals(menuID)){
			role = OrigConstant.ROLE_I_DC;
		}
		
//		log.debug("@@@@@ menuID:" + menuID);
		
		ORIGDAOUtilLocal bean = PLORIGEJBService.getORIGDAOUtilLocal();
		
		String currentTarget = bean.getTargetPointCurrentFromUser(userName);
		
		TrackingDataM trackM = new TrackingDataM();
			trackM.setUserName(userName);
			trackM.setRole(role);
			trackM.setWfRole(WebActionUtil.getRoleWf(role));
		
		log.debug("Role >> "+trackM.getRole());	
		log.debug("WfRole >> "+trackM.getWfRole());	
		
		String targetFinish = bean.getTotolJobWorking(trackM);
		
//		#septemwi change logic get targetFinish
//		if(WorkflowConstant.TODO_LIST_ID.DC_ICDC_INBOX.equals(menuID)){
//			targetFinish = PLORIGEJBService.getORIGDAOUtilLocal().getTargetPointFinishFromRole(username, role, menuID);
//		}else{
//			targetFinish = PLORIGEJBService.getORIGDAOUtilLocal().getTargetPointFinishFromRole(username, role);
//		}
		
		json.CreateJsonObject("target-point-day", HTMLRenderUtil.DisplayReplaceLineWithNull(currentTarget));
		json.CreateJsonObject("total-job-done",  HTMLRenderUtil.DisplayReplaceLineWithNull(targetFinish));
		
		return json.returnJson();
	}

}
