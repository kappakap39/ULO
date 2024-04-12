package com.eaf.orig.ulo.pl.app.utility;


import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

//import java.util.Vector;
//import com.eaf.orig.shared.util.OrigUtil;
//import com.eaf.orig.ulo.pl.model.app.PLTrackingDataM;
//import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
//import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class TrackingTableRenderUtility{
	
//	ORIGDAOUtilLocal origBean = null;
//	public TrackingTableRenderUtility(){
//		origBean = PLORIGEJBService.getORIGDAOUtilLocal();
//	}	
	
	static Logger log = Logger.getLogger(TrackingTableRenderUtility.class);	
	
	/**@deprecated not used this method change to query at SearchTrackinWebAction #septemwi*/
	public String renderTableAction(String userName, String role, HttpServletRequest request) throws Exception{	
//		#septemwi comment
//		StringBuilder tableAction = new StringBuilder();
////		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
//		Vector<PLTrackingDataM> trackVect = origBean.trackingAction(userName, role);		
//		if(!OrigUtil.isEmptyVector(trackVect)){
//			WorkflowTool wt = new WorkflowTool();
//			tableAction.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
//			for(int i=0 ; i<trackVect.size() ; i++){
//				PLTrackingDataM trackM = trackVect.get(i);
//				if (i == (trackVect.size() - 1)) {
//					tableAction.append("<tr><td class=\"textColSCenter\">"+HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, trackM.getAction()))+"</td></tr>");
//				} else {
//					tableAction.append("<tr><td class=\"textColSCenter\">"+HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request, trackM.getAction()))+",</td></tr>");
//				}
//			}
//			tableAction.append("</table>");
//		}else{
//			tableAction.append("-");
//		}
//		return String.valueOf(tableAction);
		return null;
		
	}	
	/**@deprecated not used this method change to query at SearchTrackinWebAction #septemwi*/
	public String renderTableCountAction(String userName, String role) throws Exception{		
//		StringBuilder tableCountAction = new StringBuilder();
////		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
//		Vector<PLTrackingDataM> trackVect = origBean.trackingAction(userName, role);		
//		if(!OrigUtil.isEmptyVector(trackVect)){
//			tableCountAction.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
//			for(int i=0 ; i<trackVect.size() ; i++){
//				PLTrackingDataM trackM = trackVect.get(i);
//				tableCountAction.append("<tr><td class=\"textColSCenter\">"+trackM.getCountAction()+"</td></tr>");
//			}
//			tableCountAction.append("</table>");
//		}else{
//			tableCountAction.append("0");
//		}
//		return String.valueOf(tableCountAction);
		return null;		
	}	
	/**@deprecated not used this method change to query at SearchTrackinWebAction #septemwi*/
	public String renderTableLogOnFlag(String userName) throws Exception{		
//		StringBuilder table = new StringBuilder();		
////		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
//		Vector<String[]> obJobVect = origBean.renderOnjobFlag(userName);		
//		if(!OrigUtil.isEmptyVector(obJobVect)){
//			table.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
//			for(int i=0 ; i<obJobVect.size() ; i++){
//				String[] onJobArr = obJobVect.get(i);
//				if (i == (obJobVect.size() - 1)) {
//					table.append("<tr><td class=\"textColSCenter\">"+onJobArr[0]+"</td>");
//					table.append("<td class=\"textColSCenter\">"+onJobArr[1]+"</td></tr>");
//				} else {
//					table.append("<tr><td class=\"textColSCenter\">"+onJobArr[0]+",</td>");
//					table.append("<td class=\"textColSCenter\">"+onJobArr[1]+"</td></tr>");
//				}
//			}
//			table.append("</table>");
//		}
//		return String.valueOf(table);
		return null;		
	}
	
}
