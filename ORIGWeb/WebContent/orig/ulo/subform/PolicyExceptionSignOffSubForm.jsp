<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/PolicyExceptionSignOffSubForm.js"></script>
<%
	String subformId = "POLICY_EXCEPTION_SIGN_OFF";
	Logger logger = Logger.getLogger(this.getClass());
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String PERSONAL_TYPE = personalInfo.getPersonalType();
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, "POLICY_EXCEPTION_SIGN_OFF_DATE", "POLICY_EXCEPTION_SIGN_OFF_DATE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.calendar("POLICY_EXCEPTION_SIGN_OFF_DATE", "", "POLICY_EXCEPTION_SIGN_OFF_DATE_"+PERSONAL_TYPE,"POLICY_EXCEPTION_SIGN_OFF_DATE", 
				applicationGroup.getPolicyExSignOffDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", applicationGroup, formUtil)%>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"POLICY_EXCEPTION_AUTHORIZED_BY","POLICY_EXCEPTION_AUTHORIZED_BY","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBox("POLICY_EXCEPTION_AUTHORIZED_BY","","POLICY_EXCEPTION_AUTHORIZED_BY_"+PERSONAL_TYPE,"POLICY_EXCEPTION_AUTHORIZED_BY",
				applicationGroup.getPolicyExSignOffBy(),"","100","","col-sm-8 col-md-7",applicationGroup,formUtil)%>
		</div>
	</div>
</div>
</div>
</div>
