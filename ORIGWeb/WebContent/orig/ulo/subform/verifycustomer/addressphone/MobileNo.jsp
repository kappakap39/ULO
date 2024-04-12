<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>


<%
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
%>
<tr>
	<td width="250px">
		<%=HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", displayMode, "",FormatUtil.display(personalInfo.getPersonalType()!=null && personalInfo.getPersonalType().equals(PersonalInfoDataM.PersonalType.APPLICANT)?HtmlUtil.getText(request, "PRIMARY_CARD"):HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+"    "+HtmlUtil.getText(request, "PHONE_NUMBER") , "col-sm-12", request)%>
	</td>
	<td><%=FormatUtil.getMobileNo(personalInfo.getMobileNo())%></td>
	<td><%=HtmlUtil.hidden("PHONE_NO", FormatUtil.getMobileNo(personalInfo.getMobileNo())) %></td>
</tr>