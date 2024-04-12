<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="js/SpouseSubForm.js"></script>
<%
	PersonalInfoDataM personalInfo = new PersonalInfoDataM();
 %>
<table>
	<tbody>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_TH_NAME")%></td>
			<td  colspan="3" ><%=HtmlUtil.popupList("M_TITLE_CODE",personalInfo.getmTitleCode(),"","","",request)%>&nbsp;
			<%=HtmlUtil.textBoxTH("M_TH_FIRST_NAME", personalInfo.getmThFirstName(), "120", "", "", request)%>&nbsp;
			<%=HtmlUtil.textBoxTH("M_TH_LAST_NAME", personalInfo.getmThLastName(), "50", "", "", request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_TH_OLD_LAST_NAME")%></td>
			<td><%=HtmlUtil.textBoxTH("M_TH_OLD_LAST_NAME", personalInfo.getmThOldLastName(), "50", "", "", request)%></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_COMPANY")%></td>
			<td><%=HtmlUtil.textBox("M_COMPANY",personalInfo.getmCompany(),"","50","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_HOME_TEL")%></td>
			<td><%=HtmlUtil.textBoxTel("M_HOME_TEL", personalInfo.getmHomeTel(), "9", "", "", request)%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_INCOME")%></td>
			<td><%=HtmlUtil.currencyBox("M_INCOME",personalInfo.getmIncome(),false,"15","","",request)%></td>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request, "M_POSITION")%></td>
			<td><%=HtmlUtil.textBox("M_POSITION",personalInfo.getmPosition(),"","100","","",request)%></td>
		</tr>
	</tbody>
</table>


