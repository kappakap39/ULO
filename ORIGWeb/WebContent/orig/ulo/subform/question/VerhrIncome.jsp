<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
		personalInfo = new  PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
	 	verificationResult = new  VerificationResultDataM();
	}
	IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("VER_HR_INCOME_QUESTION"));
	if(Util.empty(identifyQuestion)){
	 identifyQuestion = new IdentifyQuestionDataM();
	}
	
	String VER_HR_INCOME_QUESTION = SystemConstant.getConstant("VER_HR_INCOME_QUESTION");
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<td><%=HtmlUtil.getFieldLabel(request, "VER_HR_INCOME","")%></td>
<td><%=HtmlUtil.currencyBox("VER_HR_INCOME", VER_HR_INCOME_QUESTION, "", "VER_HR_INCOME", personalInfo.getHrIncome() , "", true, "15", HtmlUtil.elementTagId("VER_HR_INCOME_"+VER_HR_INCOME_QUESTION), "", formUtil)%></td>
<td class="text-right"><%=HtmlUtil.checkBoxInline("RESULT_CHECK", VER_HR_INCOME_QUESTION, "", "VER_HR_INCOME", identifyQuestion.getResult(), "", "Y", HtmlUtil.elementTagId("RESULT_CHECK_"+VER_HR_INCOME_QUESTION) + "style='min-height:0px'", "RESULT_CHECK", "checkbox-inline text-left", formUtil)%></td>
