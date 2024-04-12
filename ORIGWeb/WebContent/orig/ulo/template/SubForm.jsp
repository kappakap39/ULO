<%@page import="com.eaf.core.ulo.common.performance.TraceController"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%
	String transactionId = (String)request.getSession().getAttribute("transactionId");
	TraceController trace = new TraceController("SubForm.jsp",transactionId);
	trace.create("SubForm");
%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%
	
	Logger logger = Logger.getLogger(this.getClass());
	String subformId = request.getParameter("subformId");
	logger.debug("Refresh SubFormId >> "+subformId);
	HashMap SubForm = FormControl.getSubForm(subformId);
	String SUBFORM_FILE_NAME = SQLQueryEngine.display(SubForm,"SUBFORM_FILE_NAME");
	String SUBFORM_DESC = SQLQueryEngine.display(SubForm,"SUBFORM_DESC");
	logger.debug("SUBFORM_FILE_NAME >> "+SUBFORM_FILE_NAME);
	logger.debug("SUBFORM_DESC >> "+SUBFORM_DESC);
%>
<header class="titlecontent"><%=LabelUtil.getText(request,SUBFORM_DESC)%></header>
<div class="row">
<div class="col-xs-12">
	<jsp:include page="<%=SUBFORM_FILE_NAME%>" flush="true"/>
</div>
</div>
<%
	trace.end("SubForm");
	trace.trace();
%>