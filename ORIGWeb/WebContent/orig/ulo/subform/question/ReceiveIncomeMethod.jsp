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

		String FIELD_ID_RECEIVE_INCOME_METHOD =SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_METHOD");
		String FIELD_ID_RECEIVE_INCOME_BANK = SystemConstant.getConstant("FIELD_ID_RECEIVE_INCOME_BANK");
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		
		IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"));
		if(Util.empty(identifyQuestion)){
			identifyQuestion = new  IdentifyQuestionDataM();
			identifyQuestion.setQuestionNo(SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"));
			verificationResult.addIdentifyQuestion(identifyQuestion);
		}
		
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<tr>
	<td >
		<div class="col-sm-2">
			<div class="form-group">
			<%=FormatUtil.display(String.valueOf(verificationResult.getIndexQuesetion(SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"),SystemConstant.getConstant("QUESTION_SET_TYPE_HR"))+1) ) %>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
				<%= HtmlUtil.getFieldLabel(request, "RECEIVE_INCOME_METHOD","col-sm-8 col-md-7")%>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.dropdown("RECEIVE_INCOME_METHOD", SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"), "", "RECEIVE_INCOME_METHOD", ""
				, personalInfo.getReceiveIncomeMethod(), "", FIELD_ID_RECEIVE_INCOME_METHOD, "ALL", HtmlUtil.elementTagId("RECEIVE_INCOME_METHOD_"+SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION")), "col-sm-8 col-md-12", formUtil)%>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.getFieldLabel(request, "RECEIVE_INCOME_BANK","col-sm-8 col-md-12")%>
			</div>
		</div>
	</td>
	<td colspan="2">
		<div class="col-sm-12">
			<div class="form-group">
				<%=HtmlUtil.dropdown("RECEIVE_INCOME_BANK", SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"), "", "RECEIVE_INCOME_METHOD", ""
				, personalInfo.getReceiveIncomeBank(), "", FIELD_ID_RECEIVE_INCOME_BANK, "ALL", HtmlUtil.elementTagId("RECEIVE_INCOME_BANK_"+SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION")), "col-sm-8 col-md-12", formUtil)%>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
			<%=HtmlUtil.checkBox("RESULT_CHECK", SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION"), "", "RECEIVE_INCOME_METHOD", identifyQuestion.getResult()
			, "", "Y", HtmlUtil.elementTagId("RESULT_CHECK_"+SystemConstant.getConstant("RECEIVE_INCOME_METHOD_QUESTION")), "RESULT_CHECK", "col-sm-8 col-md-12", formUtil)%>
				
			</div>
		</div>
	</td>
</tr>