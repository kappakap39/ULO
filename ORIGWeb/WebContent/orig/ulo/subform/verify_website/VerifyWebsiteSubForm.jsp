<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.WebVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.google.gson.Gson"%>

<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/VerifyWebsiteSubForm.js"></script>
<%
	FormUtil formUtil = new FormUtil("VERIFY_WEBSITE_SUBFORM",request);
	String displayMode = FormDisplayModeUtil.getDisplayMode("", "", formUtil);
	ArrayList<String> WEBSITE_CODE_SSO = SystemConstant.getArrayListConstant("WEBSITE_CODE_SSO");
	String tagId = "";
	Logger logger = Logger.getLogger(this.getClass());
	VerificationResultDataM verificationResultM = (VerificationResultDataM)EntityForm.getObjectForm();
	if(verificationResultM == null) {
		verificationResultM = new VerificationResultDataM();
	}
	ArrayList<WebVerificationDataM> webList = verificationResultM.getWebVerifications();
	if(webList == null) {
		webList = new ArrayList<WebVerificationDataM>();
		verificationResultM.setWebVerifications(webList);
	}else{
		verificationResultM.getWebVerification(webList, WEBSITE_CODE_SSO);
	}

	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
	applicationGroup.setSortType(ApplicationGroupDataM.SORT_TYPE.ASC);
	if(null != personalInfos){
		Collections.sort(personalInfos, new ApplicationGroupDataM());
	}
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
%>
<div class="panel panel-default">
<table class="table table-striped table-hover">
<%if(!Util.empty(personalInfos)){
	for(PersonalInfoDataM personalInfo:personalInfos){
		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
%>
	<tr>
		<td style="font-weight: bold;"><%=FormatUtil.display(CacheControl.getName(FIELD_ID_PERSONAL_TYPE,personalInfo.getPersonalType())) %></td>
		<td><%=HtmlUtil.getFieldLabel(request, "M_TH_NAME","control-label")%>
		<%=FormatUtil.display(personalInfo.getThFirstName()) %>&nbsp;<%=FormatUtil.display(personalInfo.getThLastName()) %></td>
		<td><%=HtmlUtil.getFieldLabel(request,"ID_NO","control-label") %> <%=FormatUtil.display(personalInfo.getIdno()) %></td>
	</tr>
<%	}}
} %>	
</table>
</div>
<div class="panel panel-default">
	<div class="panel-body">
		<table id="VerifyWebsite" class="table table-noline">
			<tbody>
			<%-- For --%>
			<%
				if(!Util.empty(webList)) {
					for(WebVerificationDataM webM: webList) {
						ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.WEBSITE, webM.getWebCode());
						element.setObjectRequest(displayMode);
						element.writeElement(pageContext,webM);
					}
				} 
			%>
			</tbody>
		</table>
	</div>
</div>