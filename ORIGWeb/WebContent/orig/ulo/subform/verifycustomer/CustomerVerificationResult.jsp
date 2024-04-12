<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PaymentMethodDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PhoneVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.HRVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	String FIELD_ID_APPROVAL_RESULT = SystemConstant.getConstant("FIELD_ID_APPROVAL_RESULT");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult  = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
	String subformId = "CUSTOMER_VERIFICATION_RESULT";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-3">
				<div class="form-group">
<%-- 				<div class="col-sm-8 col-md-7"><%=LabelUtil.getText(request, "FINAL_DECISION")%></div> --%>
					<%=HtmlUtil.getSubFormLabel(request,subformId,"APPROVAL_RESULT_DECISION","FINAL_DECISION","col-sm-8 col-md-7 control-label")%>					
				</div>
			</div>
			<div class="col-sm-9">
				<div class="form-group"> 
				 <%=HtmlUtil.dropdown("APPROVAL_RESULT_DECISION", "","", "APPROVAL_RESULT_DECISION", "", verificationResult.getVerCusComplete(), "", FIELD_ID_APPROVAL_RESULT, "", "", "col-sm-8 col-md-7", formUtil)%> 
				</div>
			</div>
		</div>
	</div>
</div>