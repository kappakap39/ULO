<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		
		IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("BONUS_QUESTION"));
		if(Util.empty(identifyQuestion)){
			identifyQuestion = new  IdentifyQuestionDataM();
			identifyQuestion.setQuestionNo(SystemConstant.getConstant("BONUS_QUESTION"));
			verificationResult.addIdentifyQuestion(identifyQuestion);
		}
		
	
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<tr>
	<td >
		<div class="col-sm-2">
			<div class="form-group">
			<%=FormatUtil.display(String.valueOf(verificationResult.getIndexQuesetion(SystemConstant.getConstant("BONUS_QUESTION"),SystemConstant.getConstant("QUESTION_SET_TYPE_HR"))+1) ) %>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "VER_HR_BONUS","col-sm-8 col-md-7")%>
			</div>
		</div>
	</td>
	<td colspan="4">
		<div class="col-sm-12">
			<div class="form-group">
			<%=HtmlUtil.numberBox("VER_HR_BONUS", SystemConstant.getConstant("BONUS_QUESTION"), "", "VER_HR_BONUS", personalInfo.getHrBonus(), "", "##", "", "", true, "2", HtmlUtil.elementTagId("VER_HR_BONUS_"+SystemConstant.getConstant("BONUS_QUESTION")), "col-sm-8 col-md-7", formUtil)%>
				<%-- <%=HtmlUtil.currencyBox("VER_HR_BONUS", SystemConstant.getConstant("Q27"), "", "VER_HR_BONUS", personalInfo.getHrBonus(), "", true, "15", HtmlUtil.tagId("VER_HR_BONUS_"+SystemConstant.getConstant("Q27")), "col-sm-8 col-md-7", formUtil)%> --%>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
						<%=HtmlUtil.checkBox("RESULT_CHECK", SystemConstant.getConstant("BONUS_QUESTION"), "", "VER_HR_BONUS", identifyQuestion.getResult(), "", "Y", HtmlUtil.elementTagId("RESULT_CHECK_"+SystemConstant.getConstant("BONUS_QUESTION")), "RESULT_CHECK", "col-sm-8 col-md-12", formUtil)%>
				
			</div>
		</div>
	</td>
</tr>