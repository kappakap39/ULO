<!DOCTYPE html>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<HTML>
<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<TITLE>Logon Processing...</TITLE>
</HEAD>
<BODY>
<%
	String userName = (String)request.getSession().getAttribute("userName");
	String password = (String)request.getSession().getAttribute("password");
%>
<%if(!Util.empty(userName)&&!Util.empty(password)){%>
	<P>Logon Processing...</P>
	<FORM METHOD=POST ACTION="j_security_check" name="j_security_form">
		<input type="hidden" name="action" value="UserAction">
		<input type="hidden" name="handleForm" value="N">
		<input type="hidden" name="j_username" value="<%=userName%>">
		<input type="hidden" name="j_password" value="<%=password%>">
	</FORM>
	<script>
		document.j_security_form.submit();
	</script>
<%}else{%>
  	<br>
	This page has been expired. Please close this window and login again on new window.
  	<a href="logout.jsp"><U>close window</U></a>
<%}%>
</BODY>
</HTML>