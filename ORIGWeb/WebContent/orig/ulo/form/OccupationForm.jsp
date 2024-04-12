<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.shared.view.form.ORIGSubForm"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = ORIGForm.getRoleId();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String formId = FormControl.getFormId("OCCUPATION_FORM",applicationGroup.getProductId(),applicationGroup.getBundingId());
	logger.debug("formId >> "+formId);
%>
<%
ArrayList<ORIGSubForm> subForms = FormControl.getSubForm(formId,roleId);
if(!Util.empty(subForms)){
	for(ORIGSubForm subForm:subForms){
		logger.debug("subForm.getFileName() >> "+subForm.getFileName());
 %>
		<jsp:include page="<%=subForm.getFileName()%>" flush="true" />
	<%}%>
<%}%>