<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="org.apache.log4j.Logger"%>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String LOGON_CONTEXT = SystemConfig.getProperty("LOGON_CONTEXT");
%>
<br>This page has been expired. Please close this window and login again on new window.
<a href="<%=LOGON_CONTEXT+"/"%>logout.jsp"><U>close window</U></a>