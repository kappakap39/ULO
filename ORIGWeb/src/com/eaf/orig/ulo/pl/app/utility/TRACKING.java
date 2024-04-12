package com.eaf.orig.ulo.pl.app.utility;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.util.OrigUtil;

public class TRACKING extends TrackingTableRenderUtility{
	public static String displayTableLogonFlag(String jobstatus){
		if(OrigUtil.isEmptyString(jobstatus)){
			return "-";
		}
		StringBuilder str = new StringBuilder("");
		String[] queue = jobstatus.split("\\|");
		if(null != queue && queue.length >0){
			str.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
			for(int i=0; i<queue.length; i++){
				String box = (String)queue[i];
				if(null != box){
					String[] data = box.split("\\#");
					if(null != data){
						String style = "";
						if(i != queue.length-1){
							style += "class='line-tracking'";
						}
						str.append("<tr width='100%' "+style+"><td class=\"textColSCenter\">"+HTMLRenderUtil.displayHTML(data[0])+"</td>");
						str.append("<td class=\"textColSCenter\" nowrap='nowrap'>:"+HTMLRenderUtil.displayHTML(data[1])+"</td></tr>");
					}					
				}
			}
			str.append("</table>");
		}
		return str.toString();
	}
	public static String displayTableStatus(HttpServletRequest request , String appStatus){
		if(OrigUtil.isEmptyString(appStatus)){
			return "-";
		}
		StringBuilder str = new StringBuilder("");
		String[] queue = appStatus.split("\\|");
		if(null != queue && queue.length >0){
			str.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
			WorkflowTool wt = new WorkflowTool();
			for(int i=0; i<queue.length; i++){
				String box = (String)queue[i];
				if(null != box){
					String[] data = box.split("\\#");
					if(null != data && data.length > 0){
						String style = "";
						if(i != queue.length-1){
							style += "class='line-tracking'";
						}
						str.append("<tr width='100%' "+style+"><td class=\"textColSCenter\">"+HTMLRenderUtil.displayHTML(wt.GetMessageAppStatus(request,data[0]))+"</td></tr>");
					}					
				}
			}
			str.append("</table>");
		}
		return str.toString();
	}
	public static String displayTableCountApp(String count){
		if(OrigUtil.isEmptyString(count)){
			return "0";
		}
		StringBuilder str = new StringBuilder("");
		String[] queue = count.split("\\|");
		if(null != queue && queue.length >0){
			str.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">");
			for(int i=0; i<queue.length; i++){
				String box = (String)queue[i];
				if(null != box){
					String[] data = box.split("\\#");
					if(null != data && data.length == 2){
						String style = "";
						if(i != queue.length-1){
							style += "class='line-tracking'";
						}
						str.append("<tr width='100%' "+style+"><td class=\"textColSCenter\">"+HTMLRenderUtil.displayHTML(data[1])+"</td></tr>");
					}					
				}
			}
			str.append("</table>");
		}
		return str.toString();
	}
}
