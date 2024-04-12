<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/dv/js/DvSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
// 	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(1);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String displayMode = HtmlUtil.EDIT;
// 	String tagId = PersonalInfoDataM.PersonalType.APPLICANT+"_1";
%>
<section class="table">
<table>
	<tbody>
		<tr>
			<td><%=HtmlUtil.button("VERIFY_HR_BTN","VERIFY_HR_BTN",displayMode,"","",request) %></td>
		</tr>
		<tr>
			<td><%=HtmlUtil.button("VERIFY_CUSTOMER_BNT","VERIFY_CUSTOMER_BNT",displayMode,"","",request) %></td>
		</tr>
	</tbody>
</table>
</section>

