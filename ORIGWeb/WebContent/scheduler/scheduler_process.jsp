<%
String pageProcess = request.getParameter("pageProcess");
if(pageProcess!=null){
	if(pageProcess.trim().equals("1")){
%>
		<jsp:include page="scheduler.jsp" flush="true" />
<%		
	}else if(pageProcess.trim().equals("2")){
%>
		<jsp:include page="scheduler_create.jsp" flush="true" />
<%
	}
}
%>
