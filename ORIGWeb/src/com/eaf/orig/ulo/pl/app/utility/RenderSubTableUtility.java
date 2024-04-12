package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class RenderSubTableUtility{
	
	public static String renderTableReason(String appRecID) throws Exception{
		StringBuilder tableReason = new StringBuilder();
		Vector<String> reasonVect = new Vector<String>();		
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
		reasonVect = origBean.loadReasonEnquiry(appRecID);		
		if(reasonVect != null && reasonVect.size() > 0){
			tableReason.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
				for(int i = 0; i < reasonVect.size(); i++){
					tableReason.append("<tr><td class=\"textColSCenter\" nowrap=\"nowrap\">");
					tableReason.append(reasonVect.get(i)+",");
					if(i==reasonVect.size()-1){
						tableReason.deleteCharAt(tableReason.length()-1);
					}
					tableReason.append("</td></tr>");
				}
			tableReason.append("</table>");
		}else{
			tableReason.append("-");
		}
		return String.valueOf(tableReason);
	}
	public static String renderTableReasonCS(String appRecID,String jobState,String finalDecision) throws Exception{
		StringBuilder HTML = new StringBuilder("");	
		UtilityDAO DAO = ObjectDAOFactory.getUtilityDAO();
		Vector<String> reasonVect = DAO.loadReasonCS(appRecID, jobState, finalDecision);
		if(null != reasonVect && reasonVect.size()>0){
			for(String reason:reasonVect){
				HTML.append(reason+"<br>");
			}
		}else{
			HTML.append("-");
		}
		return String.valueOf(HTML);
	}
	
	public static String renderTableCallCenterTrackingReason(String appRecID, String jobState) throws Exception{
		StringBuilder tableReason = new StringBuilder();
		Vector<String> reasonVect = new Vector<String>();		
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
		reasonVect = origBean.loadReasonCallCenterTracking(appRecID, jobState);		
		if(reasonVect != null && reasonVect.size() > 0){
			tableReason.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">");
				for(int i = 0; i < reasonVect.size(); i++){
					tableReason.append("<tr><td class=\"textColS\" style=\"padding: 0;text-align: left;\" nowrap=\"nowrap\">");
					tableReason.append(reasonVect.get(i)+",");
					if(i==reasonVect.size()-1){
						tableReason.deleteCharAt(tableReason.length()-1);
					}
					tableReason.append("</td></tr>");
				}
			tableReason.append("</table>");
		}else{
			tableReason.append("-");
		}
		return String.valueOf(tableReason);
	}
	
	public static String renderTableDocCheckListName(String appRecId) throws Exception{
		StringBuilder table = new StringBuilder();
		Vector<String> docVect = new Vector<String>();
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();		
		docVect = origBean.loadTrackingDoclistName(appRecId);		
		if(docVect != null && docVect.size() > 0){
			table.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
				for (int i = 0; i < docVect.size(); i++) {
					table.append("<tr><td class=\"textColSCenter\" nowrap=\"nowrap\">");
					table.append(docVect.get(i) + ",");
					if(i==docVect.size()-1){
						table.deleteCharAt(table.length()-1);
					}
					table.append("</td></tr>");
				}
			table.append("</table>");
		}else{
			table.append("-");
		}
		return String.valueOf(table);
	}
	public static String renderTableDocCheckListNameCS(String appRecID) throws Exception{
		StringBuilder HTML = new StringBuilder("");	
		UtilityDAO DAO = ObjectDAOFactory.getUtilityDAO();
		Vector<String> docVect = DAO.loadTrackingDoclistName(appRecID);		
		if(null != docVect && docVect.size() > 0){
			for(int i = 0; i < docVect.size(); i++) {
				HTML.append(docVect.get(i) );
				 if(i!=docVect.size()-1){
					 HTML.append("<br>");
				}
			}
		}else{
			HTML.append("-");
		}
		return String.valueOf(HTML);
	}

}
