<!DOCTYPE html>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.logon.LogonEngine"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>LOG OUT</TITLE>
</HEAD>
<BODY>
<%
	String ERROR_CODE = (String)request.getSession().getAttribute("ERROR_CODE");
	String ERROR_MSG = (String)request.getSession().getAttribute("ERROR_MSG");	
	try{
		LogonEngine.processLogout(request, MConstant.PROCESS.FAIL);
	}catch(Exception e){		
	}
%>
</form>
<FORM METHOD=POST ACTION="Logout" NAME="logout">
	<%if(!Util.empty(ERROR_CODE)&&!Util.empty(ERROR_MSG)){%>
		<INPUT TYPE="HIDDEN" name="logoutExitPage" VALUE="origLogon.jsp?ERROR_CODE=<%=ERROR_CODE%>&ERROR_MSG=<%=ERROR_MSG%>"/>
	<%}else if(!Util.empty(ERROR_CODE)){%>
		<INPUT TYPE="HIDDEN" name="logoutExitPage" VALUE="origLogon.jsp?ERROR_CODE=<%=ERROR_CODE%>"/>
	<%}else{%>
		<INPUT TYPE="HIDDEN" name="logoutExitPage" VALUE="origLogon.jsp"/>
	<%}%>
</FORM>
<script>
	document.logout.submit();
</script>
</BODY>
</HTML>