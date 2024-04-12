<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page import="com.eaf.orig.ulo.model.app.HRVerificationDataM"%>
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
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	 Logger logger = Logger.getLogger(this.getClass());
	
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		String QuestionSet =SystemConstant.getConstant("QUESTION_SET_A");
		String VER_HR_RESULT_TRUE =SystemConstant.getConstant("VER_HR_RESULT_TRUE");
		String PERMANENT_STAFF_N =SystemConstant.getConstant("PERMANENT_STAFF_N");
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		
		IdentifyQuestionDataM identifyQuestion20= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("VER_HR_RESULT_QUESTION"));
		IdentifyQuestionDataM identifyQuestion21= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("PERMANENT_STAFF_QUESTION"));
		IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("INFORMANT_NAME_QUESTION"));
		if(Util.empty(identifyQuestion)){
			identifyQuestion = new  IdentifyQuestionDataM();
			identifyQuestion.setQuestionNo(SystemConstant.getConstant("INFORMANT_NAME_QUESTION"));
			verificationResult.addIdentifyQuestion(identifyQuestion);
		}
		 
		 
		
		
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<tr>
	<td >
		<div class="col-sm-2">
			<div class="form-group">
			<%=FormatUtil.display(String.valueOf(verificationResult.getIndexQuesetion(SystemConstant.getConstant("INFORMANT_NAME_QUESTION"),SystemConstant.getConstant("QUESTION_SET_TYPE_HR"))+1) ) %>
			</div>
		</div>
	</td>
	<td>
		<div class="col-sm-12">
			<div class="form-group">
			<%
			ArrayList<HRVerificationDataM>	hrVerifications = verificationResult.getHrVerifications();
			if(!Util.empty(hrVerifications)){
				 Comparator<HRVerificationDataM> comp = new BeanComparator("createDate");
			 Collections.sort(hrVerifications,comp);
			if(!Util.empty(hrVerifications)){
				HRVerificationDataM hrVerification =	hrVerifications.get(hrVerifications.size()-1);
				 if(hrVerification.getPhoneVerStatus().equals(MConstant.FLAG_Y)){
				 	%>
				 		<%=HtmlUtil.getMandatoryLabel(request, "DATA_PROVIDER","col-sm-8 col-md-7")%>
				 	<%
				 }
				 else if(hrVerification.getPhoneVerStatus().equals(MConstant.FLAG_N)&&!identifyQuestion20.equals(VER_HR_RESULT_TRUE) &&!identifyQuestion21.equals(PERMANENT_STAFF_N)){
				 	%>
				 		<%=HtmlUtil.getSubFormLabel(request, "DATA_PROVIDER")%>
				 	<%
				 }	
				}
			}
			else{
				%>
					<%=HtmlUtil.getMandatoryLabel(request, "DATA_PROVIDER","col-sm-8 col-md-7")%>
				<%
				}
			 %>

			</div>
		</div>
	</td>
	<td colspan="4">
		<div class="col-sm-12">
			<div class="form-group">
			<%=HtmlUtil.textBox("DATA_PROVIDER",  SystemConstant.getConstant("INFORMANT_NAME_QUESTION"), "", "DATA_PROVIDER",  identifyQuestion.getCustomerAnswer(), "", "120", HtmlUtil.elementTagId("DATA_PROVIDER_"+SystemConstant.getConstant("INFORMANT_NAME_QUESTION")), "col-sm-8 col-md-7", formUtil)%>
				<%-- <%=HtmlUtil.textBox("DATA_PROVIDER", SystemConstant.getConstant("Q24"), identifyQuestion.getCustomerAnswer(), "", "120", displayMode, HtmlUtil.tagId("DATA_PROVIDER_"+SystemConstant.getConstant("Q24")), "col-sm-8 col-md-7", request) %> --%>
			</div>
		</div>
	</td>
	<td colspan="4">
		<div class="col-sm-12">
			<div class="form-group"></div>
		</div>
	</td>
</tr>