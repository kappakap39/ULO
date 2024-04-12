<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page import="com.eaf.j2ee.system.LoadXML" %>
<%
	String CM_SERVER_NAME_EXT = String.valueOf(LoadXML.getCmServerEx().get("1"));
	String userid = String.valueOf(LoadXML.getCmUserid().get("1"));
	String password = String.valueOf(LoadXML.getCmPassword().get("1"));
	String server = String.valueOf(LoadXML.getCmServerName().get("1"));
	String imageItemType = String.valueOf(LoadXML.getCmDatatype().get("1"));
	String serverCmProject = String.valueOf(LoadXML.getServerCmProject().get("1"));
	String dataType = String.valueOf(LoadXML.getCmMergeItemtype().get("1"));
	System.out.println("request.getParameter(attachID) = "+request.getParameter("attachID"));
	String attachId = request.getParameter("attachID").toString();
	String requestNo = request.getParameter("requestID").toString();
	
	String queryString = "?userid=" + userid + "&password=" + password + "&server=" + server + "&imageId=" + requestNo + "&attachId=" + attachId  + "&dataType=" + dataType;
	String action="http://" + CM_SERVER_NAME_EXT + "/"+serverCmProject+"/ConnectCM";
	
	System.out.println("requestNo = "+requestNo);
	System.out.println("attachId = "+attachId);
	System.out.println("dataType = "+dataType);
	System.out.println("queryString = "+queryString);
	System.out.println("action = "+action);
	
//	resp.sendRedirect(action + queryString);
	
	// Tiwa edited, change to post method
	response.setContentType("text/html");
	try{
		out.write("<html><body onload=\"document.forms[0].submit();\"><form action=\""+action+"\" method=post>");
		out.write("<input type=hidden name=userid value=\""+userid+"\">");
		out.write("<input type=hidden name=password value=\""+password+"\">");
		out.write("<input type=hidden name=server value=\""+server+"\">");
		out.write("<input type=hidden name=imageId value=\""+requestNo+"\">");
		out.write("<input type=hidden name=attachId value=\""+attachId+"\">");
		out.write("<input type=hidden name=dataType value=\""+dataType+"\">");
		out.write("</form></body></html>");
		out.flush();
		//out.close();
	}catch(Exception e){
		e.printStackTrace();
	}
%>