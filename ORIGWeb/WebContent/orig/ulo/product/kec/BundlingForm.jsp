<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
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
	String subformId = "KEC_BUNDLING_FORM";
	Logger logger = Logger.getLogger(this.getClass());
	String roleId = ORIGForm.getRoleId();
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String formId = FormControl.getFormId("BUNDLE_FORM",MConstant.Product.K_EXPRESS_CASH,applicationGroup.getBundingId());
	logger.debug("formId >> "+formId);		
	FormUtil formUtil = new FormUtil(subformId,request);
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