<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
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
		if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) || PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
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