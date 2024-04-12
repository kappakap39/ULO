package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

//import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigTrackingDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class TrackingQueryDB implements AjaxDisplayGenerateInf {

	Logger log = Logger.getLogger(TrackingQueryDB.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException {
		
		String method = request.getParameter("method");
		String owner = request.getParameter("owner");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String logOn = request.getParameter("logOn");
		String onJob = request.getParameter("onJob");
		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		String role = WebActionUtil.getUnderRole(userM.getCurrentRole());
		String roleSup = userM.getCurrentRole();
		
		String currentRole = userM.getCurrentRole(); 
		
		ProcessMenuM currentMenuM = (ProcessMenuM) request.getSession().getAttribute("CurrentMenuM");
		if(currentMenuM == null) currentMenuM = new ProcessMenuM();;
		String menuId = currentMenuM.getMenuID();
		
//		log.debug("method = "+method);
//		log.debug("role = "+role);
		
		int returnCount = 0;
		try {
			if(!method.isEmpty() && method.equals("queryAction")){
				queryAction(owner, role);
			} else if(!method.isEmpty() && method.equals("countUser")){			
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countUser(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countLogOnStatus")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countLogOnStatus(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countOnjobStatus")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countOnjobStatus(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countPreviousJob")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countPreviousJob(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countPreviousJobEdit")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countPreviousJobEdit(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countNewJob")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countNewJob(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countSubmitJob")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countSubmitJob(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countSubmitEditJob")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countSubmitEditJob(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countRemainJob")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countRemainJob(role, firstName, lastName, logOn, onJob);
			} else if(!method.isEmpty() && method.equals("countAutoQueue")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countAutoQueue(currentRole,WebActionUtil.getUnderRoleWf(currentRole));
			} else if(!method.isEmpty() && method.equals("countAutoQueueSup")){
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countAutoQueue(roleSup,WebActionUtil.getUnderRoleWf(currentRole));
			}else if(!method.isEmpty() && method.equals("countInboxAutoQueueSup")){
				if(WorkflowConstant.TODO_LIST_ID.CA_SUP_ICDC_CONFIRM_REJECT.equals(menuId)){
					currentRole = OrigConstant.ROLE_I_SUP_CA;
				}
				returnCount = PLORIGEJBService.getORIGDAOUtilLocal().countInboxAutoQueueSup(menuId, currentRole, WebActionUtil.getUnderRoleWf(currentRole));
			}
		} catch (Exception e) {
			log.fatal(e.getMessage());
		}
		return String.valueOf(returnCount);
	}
	
	private String queryAction(String owner, String role) throws PLOrigApplicationException{
		
		StringBuffer tableAction = new StringBuffer();
		StringBuffer tableCountAction = new StringBuffer();
		
		Vector<PLTrackingDataM> trackVect = null;
		try {
			trackVect = PLORIGEJBService.getORIGDAOUtilLocal().trackingAction(owner, role);	
			if(trackVect!=null && trackVect.size()>0){
				tableAction.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
				tableCountAction.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
				for(int i=0 ; i<trackVect.size() ; i++){
					PLTrackingDataM trackM = trackVect.get(i);
					tableAction.append("<tr><td>"+trackM.getAction()+"</td></tr>");
					tableCountAction.append("<tr><td>"+trackM.getCountAction()+"</td></tr>");
				}
				tableAction.append("</table>");
				tableCountAction.append("</table>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(tableAction + "|" + tableCountAction);
	}

}
