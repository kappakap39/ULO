<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
	  personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
	  verificationResult = new VerificationResultDataM();
	}
	String TOTAL_WORK_HR_QUESTION = SystemConstant.getConstant("TOTAL_WORK_HR_QUESTION");
	IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("TOTAL_WORK_HR_QUESTION"));
	if(Util.empty(identifyQuestion)){
	 identifyQuestion = new IdentifyQuestionDataM();
	}
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<td><%=HtmlUtil.getSubFormLabel(request, subformId, "TOT_WORK_HR")%></td>
<td>
	<%=HtmlUtil.numberBox("TOT_WORK_YEAR", TOTAL_WORK_HR_QUESTION, "", "TOT_WORK_HR", FormatUtil.toBigDecimal(personalInfo.getTotWorkYear(), true), "", "", "", "", true, "2", HtmlUtil.elementTagId("TOT_WORK_YEAR_"+TOTAL_WORK_HR_QUESTION)+" style='margin-left:-13px;'", "col-sm-4", formUtil) %>
	<%=HtmlUtil.getLabel(request,"YEAR","col-sm-2")%>
	<%=HtmlUtil.numberBox("TOT_WORK_MONTH", TOTAL_WORK_HR_QUESTION, "", "TOT_WORK_HR", FormatUtil.toBigDecimal(personalInfo.getTotWorkMonth(), true), "", "", "11", "", true, "2",HtmlUtil.elementTagId("TOT_WORK_MONTH_"+TOTAL_WORK_HR_QUESTION)+"onblur='validateMonth(this)'", "col-sm-4", formUtil) %>
	<%=HtmlUtil.getLabel(request,"TOT_WORK_MONTH","col-sm-2")%>
</td>
<td class="text-right"><%=HtmlUtil.checkBoxInline("RESULT_CHECK", TOTAL_WORK_HR_QUESTION, "", "TOT_WORK_HR", identifyQuestion.getResult(), "", "Y", HtmlUtil.elementTagId("RESULT_CHECK_"+TOTAL_WORK_HR_QUESTION) + "style='min-height:0px'", "RESULT_CHECK", "checkbox-inline text-left", formUtil)%></td>