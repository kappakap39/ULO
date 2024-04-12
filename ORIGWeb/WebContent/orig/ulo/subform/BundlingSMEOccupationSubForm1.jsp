<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/OccupationSubForm.js"></script>

<%
	String subformId = "BUNDLING_SME_OCCUPATION_SUBFORM_1";
	Logger logger = Logger.getLogger(this.getClass());
// 	int PERSONAL_SEQ = 1;
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
// 	String PERSONAL_TYPE = personalInfo.getPersonalType();	
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	
	String occupationCode = personalInfo.getOccupation();
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<section class="table">
<table width='100%'>
	<tbody>
		<tr>
			<td colspan="4" class="subtoppic label"><%=LabelUtil.getText(request, "WORKPLACE")%></td>
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request,subformId, "POSITION")%> </td>
			<td><%=HtmlUtil.textBox("POSITION_DESC",personalInfo.getPositionDesc(),"textboxsmall","100",HtmlUtil.VIEW,HtmlUtil.elementTagId("POSITION_DESC",personalElementId),request)%></td>			
		</tr>
		<tr>
			<td class="label"><%=HtmlUtil.getSubFormLabel(request,subformId, "WORKPLACE_OLD")%> </td>
			<td>
				<%=HtmlUtil.numberBox("TOT_WORK_YEAR",personalInfo.getTotWorkYear(),"##", true,"2","",HtmlUtil.elementTagId("TOT_WORK_YEAR",personalElementId),request)%>
				<span class="onlyfloatleft"><%=LabelUtil.getText(request, "TOT_WORK_YEAR")%> </span>
				<%=HtmlUtil.numberBox("TOT_WORK_MONTH", personalInfo.getTotWorkMonth(), "##", true, "2","", HtmlUtil.elementTagId("TOT_WORK_MONTH",personalElementId)+" onblur='validateMonth(this);'", request)%>
				<span class="onlyfloatleft"><%=LabelUtil.getText(request, "TOT_WORK_MONTH")%></span>
			</td>		
		</tr>
		
	</tbody>
</table>
</section>

