<%    
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache,no-store");
response.setDateHeader("Expires", 0);
response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE jsp:include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.eaf.orig.profile.model.UserDetailM" %>
<%
UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser"); 

if(userM != null && userM.getUserName().trim().length() > 0){				
}else{
	response.sendRedirect("logout.jsp");
	return;
}
%>
<%
	if(session.getAttribute("FirstLogon")!=null && ((String)session.getAttribute("FirstLogon") ).equalsIgnoreCase("Y")   ){
		request.getSession().removeAttribute("FirstLogon");
		response.sendRedirect("Frameset.jsp");
	}else{
%>
	<jsp:include page="popup_template.jsp"/>
<%--<jsp:include page="popup_template.jsp" flush="true" /> --%>
<%}%>