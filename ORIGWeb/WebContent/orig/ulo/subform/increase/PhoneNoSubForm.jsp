<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/PhoneNoSubForm.js"></script>
<%
	String subformId = "INCREASE_PHONE_NO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("subformId >> "+subformId);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();	
	
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "MOBILE")%></div>
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, "MOBILE", "MOBILE", "col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.textBoxMobile("PHONE_NUMBER", personalElementId, "PHONE_NUMBER_"+personalElementId, "MOBILE",
			 personalInfo.getMobileNo(), "","", "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>
	<!-- <div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"CONTACT_TIME","CONTACT_TIME","col-sm-4 col-md-5 control-label")%>
			<%=HtmlUtil.dropdown("CONTACT_TIME", personalElementId, "CONTACT_TIME_"+personalElementId, "CONTACT_TIME", "", 
			personalInfo.getContactTime(), "", FIELD_ID_CONTACT_TIME, "ALL_ALL_ALL", "", "col-sm-8 col-md-7",personalInfo, formUtil)%>
		</div>
	</div>  -->
</div>
</div>

</div>