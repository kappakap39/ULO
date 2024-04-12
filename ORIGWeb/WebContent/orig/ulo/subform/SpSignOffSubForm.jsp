<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/SignOffSubForm.js"></script>
<%
	String subformId = "SP_SIGN_OFF_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationDataM applications = ORIGForm.getObjectForm().getApplication(0);
	if(null == applications){
		applications = new ApplicationDataM();
	}
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE, PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId, "SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.calendar("SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE_"+personalElementId, "SP_SIGN_OFF_DATE", 
						applications.getSpSignoffDate(), "", "", HtmlUtil.TH, "col-sm-8 col-md-7", formUtil)%>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"SP_SIGN_OFF_AUTHORIZED_BY","SP_SIGN_OFF_AUTHORIZED_BY","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("SP_SIGN_OFF_AUTHORIZED_BY","SP_SIGN_OFF_AUTHORIZED_BY_"+personalElementId,"SP_SIGN_OFF_AUTHORIZED_BY",
						applications.getSpSignoffAuthBy(),"","100","","col-sm-8 col-md-7",formUtil)%>
				</div>
			</div>
		</div>
	</div>
</div>