<%@page import="com.eaf.orig.logon.LogonEngine"%>
<%
	String renderPage = "Avale_template.jsp";
	if(!LogonEngine.auth(request)){
		renderPage = "timeout.jsp";
	}
%>
<div class="row">
	<div class="col-lg-12">
		<jsp:include page="<%=renderPage%>" flush="true"/>
	</div>
</div>