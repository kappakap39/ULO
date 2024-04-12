<%@page import="org.apache.log4j.Logger"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
	response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>CONSOLE SYSTEM - [<%=request.getServerName() %>]</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<%
	Logger logger = Logger.getLogger(this.getClass());
%>
<frameset cols="220,*" frameborder="no" border="1" framespacing="1" >
   <frame src="console/menuFlow.jsp" name="menuFlow" id="menuFlow" noresize>
   <frame src="console/screenFlow.jsp" name="screenFlow" id="screenFlow" scrolling="yes">
</frameset>
</html>