<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires",0);
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib uri="/WEB-INF/lib/JStartTagLib.tld" prefix="taglib"%>
<%@taglib uri="/WEB-INF/lib/anti_csrf.tld" prefix="csrftoken"%>
<%@include file="new_screen_definitions.jsp"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:include page="/constant/constant.jsp" flush="true"/>
<form name="appFormName" id="appFormName" class="appFormName" method="post" action="FrontController" autocomplete="off">
	<csrftoken:csrftoken/>
	<input type="hidden" name="action" id="action" value=""> 
	<input type="hidden" name="handleForm" id="handleForm" value="N"> 
	<input type="hidden" name="page" id="page" value="">
	<input type="hidden" name="tab_ID" id="tab_ID" value="">
	<input type="hidden" name="processType" id="processType" value="SaveApplication">
	<input type="hidden" name="formID" id="formID" value="">
	<input type="hidden" name="currentTab" id="currentTab"  value="">
	<input type="hidden" name="flagEventClose" id="flagEventClose" value="">
	<input type="hidden" name="menuSequence" id="menuSequence" value="">
	<input type="hidden" name="renderPage" id="renderPage" value="">
	<input type="hidden" name="popup-action" id="popup-action" value="">
	<input type="hidden" name="formActionName" id="formActionName" value="">
	<%	
		logger.debug("[HtmlBody]="+pageContext.getRequest().getAttribute("HtmlBody"));	
	%>	
	<taglib:insert parameter="HtmlBody" direct="false"/>
</form>
