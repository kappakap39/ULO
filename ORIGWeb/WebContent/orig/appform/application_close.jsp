<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=UTF-8"
%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>application_close.jsp</TITLE>
</HEAD>
<BODY>
<P>Please Wait </P>
</BODY>
</HTML>
<SCRIPT language="JavaScript">
//window.location.href("/NaosWeb/FrontController?action=LogoutFromClose");
window.location.href("<%=request.getContextPath()%>/FrontController?action=NaosUserLogout");

window.close();  

</SCRIPT>