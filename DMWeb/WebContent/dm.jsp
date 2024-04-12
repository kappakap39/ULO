<%@page import="org.apache.log4j.Logger"%>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String userName = (String)request.getRemoteUser();	
	logger.debug("userName >> "+userName); 
%>