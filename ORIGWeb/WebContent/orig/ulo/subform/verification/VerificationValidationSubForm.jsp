<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/verification/js/ValidationSubForm.js"></script>
<%
	String subformId ="VERIFICATION_VALIDATION_SUBFROM";	
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String roleId = ORIGForm.getRoleId();
	String displayMode = HtmlUtil.EDIT;
	String[] elementSubform = SystemConstant.getConstant("VERIFICATION_VALIDATION_"+roleId).split(",");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM  personalInfo= PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(Util.empty(personalInfo)){
		personalInfo = new  PersonalInfoDataM();
	}
	
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
 %>
 
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
<%	for(int i=0;i<elementSubform.length;i++){
		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION_VALIDATION,elementSubform[i]);
		element.setObjectRequest(applicationGroup);
		element.writeElement(pageContext,verificationResult); 
%>

<%} %>
		</div>
	</div>
</div>