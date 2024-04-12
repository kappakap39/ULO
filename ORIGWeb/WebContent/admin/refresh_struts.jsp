<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%
	String sNm = request.getParameter("serverName");
%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>refresh_cache_process.jsp on <%=sNm%></TITLE>
</HEAD>
<link href="<%=request.getContextPath()%>/naos_adminconsole/css/adminconsole.css" rel="StyleSheet" type="text/css">
<BODY>
<%
        String msg="";
        String realPath = request.getRealPath("/WEB-INF/struts-config.xml");
		System.out.println("RealPath : "+realPath);
		com.eaf.j2ee.system.LoadXML loadxml = new com.eaf.j2ee.system.LoadXML(realPath);
		msg = "Refresh strut-config at path : "+realPath+" completed.";
%>
<FONT class="queue-table">
	<B><%=msg%></B>
</FONT>
<BR>
<input type="button" class="queue-button" name="Close" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onclick="winClose();">
<script>
function winClose(){
	window.close();
}
//	self.close();
</script>
</BODY>
</HTML>
