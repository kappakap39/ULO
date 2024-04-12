package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
//import com.eaf.orig.ulo.pl.app.dao.PLOrigReasonLogDAO;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class HistoryPopupUtility {
	
	public String Reason(String appLogId, String action) throws Exception {
		StringBuilder HTML = new StringBuilder("");
		HTML.append("<table cellpadding='0' cellspacing='0' width='100%' align='center'>");
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
		if(!OrigConstant.Action.REASSIGN.equals(action) && !OrigConstant.Action.UNBLOCK.equals(action) 
				&& !OrigConstant.Action.COMPLETE_REJECT.equals(action)){
			Vector<String> reasonVect = origBean.loadDisplayReasonLog(appLogId);
			if(reasonVect !=null && reasonVect.size()>0 ){
				for(int i=0; i<reasonVect.size(); i++){
					String reason = reasonVect.get(i);
					HTML.append("<tr align='center'><td align='center'>"+reason+"</td></tr>");				
				}
			}else{
				HTML.append("<tr align='center'><td align='center'>-</td></tr>");
			}
		}else{
			HTML.append("<tr align='center'><td align='center'>-</td></tr>");
		}
		HTML.append("</table>");
		return HTML.toString();
	}
	
	public String historyAction(HttpServletRequest request, String jobState, String actionDesc){
		//Check case DC send to CB with same action CB request KCBS (REQUEST CB), so need to check job state
		if(OrigConstant.Action.REQUEST_CB.equals(actionDesc)
				&& (WorkflowConstant.JobState.NCB_CQ.equals(jobState) || WorkflowConstant.JobState.NCB_I_CQ.equals(jobState))){
			actionDesc = OrigConstant.Action.SEND_TO_CB;
		}
		StringBuilder actionDecision = new StringBuilder();
		actionDecision.append("ACTION_").append(actionDesc.replace(" ","_").toUpperCase());
		String actionMessage = MessageResourceUtil.getTextDescription(request, actionDecision.toString());
		if(!"".equals(actionMessage)){
			return actionMessage;
		}else{
			return actionDesc;
		}
	}
}
