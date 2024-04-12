<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
%>
<%
ArrayList<ORIGSubForm> headerSubforms = FormControl.getSubForm("VERIFY_HR_FORM",ORIGForm.getRoleId());
if(!Util.empty(headerSubforms)){
	for(ORIGSubForm subForm:headerSubforms){
		logger.debug("subForm.getFileName() >> "+subForm.getFileName());
%>
		<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
	<%}%>
<%}%>

