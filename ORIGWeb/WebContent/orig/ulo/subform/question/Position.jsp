<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
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
<script type="text/javascript" src="orig/ulo/subform/question/js/Position.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
		String subformId ="IDENTIFY_QUESTION_SUBFORM";
		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
		String FIELD_ID_POSITION_LEVEL = SystemConstant.getConstant("FIELD_ID_POSITION_LEVEL");
		String FIELD_ID_PROFESSION =SystemConstant.getConstant("FIELD_ID_PROFESSION");
		String POSITION_LEVEL_OTHER =SystemConstant.getConstant("POSITION_LEVEL_OTHER");
		String VER_HR_RESULT_TRUE = SystemConstant.getConstant("VER_HR_RESULT_TRUE");
		String PERMANENT_STAFF_N =SystemConstant.getConstant("PERMANENT_STAFF_N");
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		IdentifyQuestionDataM identifyQuestion20	 = verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("VER_HR_RESULT_QUESTION"));
		IdentifyQuestionDataM identifyQuestion21	 = verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("PERMANENT_STAFF_QUESTION"));
		IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("POSITION_QUESTION"));
		if(Util.empty(identifyQuestion)){
			identifyQuestion = new  IdentifyQuestionDataM();
			identifyQuestion.setQuestionNo(SystemConstant.getConstant("POSITION_QUESTION"));
			verificationResult.addIdentifyQuestion(identifyQuestion);
		}
		
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<tr>
	<td >
		<%=FormatUtil.display(String.valueOf(verificationResult.getIndexQuesetion(SystemConstant.getConstant("POSITION_QUESTION"),SystemConstant.getConstant("QUESTION_SET_TYPE_HR"))+1) ) %>
	</td>
	<td>
		<%
		ArrayList<HRVerificationDataM>	hrVerifications = verificationResult.getHrVerifications();
		if(!Util.empty(hrVerifications)){
			 Comparator<HRVerificationDataM> comp = new BeanComparator("createDate");
		 Collections.sort(hrVerifications,comp);
		if(!Util.empty(hrVerifications)){
			HRVerificationDataM hrVerification =	hrVerifications.get(hrVerifications.size()-1);
			 if(hrVerification.getPhoneVerStatus().equals(MConstant.FLAG_Y)&&!identifyQuestion20.equals(VER_HR_RESULT_TRUE) &&!identifyQuestion21.equals(PERMANENT_STAFF_N)){
			 	%>
			 		<%=HtmlUtil.getMandatoryLabel(request, "POSITION","col-sm-8 col-md-7")%>
			 	<%
			 }
			 else if(hrVerification.getPhoneVerStatus().equals(MConstant.FLAG_N)){
			 	%>
			 		<%=HtmlUtil.getSubFormLabel(request, "POSITION")%>
			 	<%
			 }	
			}
		}
		else{
			%>
				<%=HtmlUtil.getMandatoryLabel(request, "POSITION","col-sm-8 col-md-7")%>
			<%
			}
		 %>
	</td>
	<td><%=HtmlUtil.dropdown("POSITION", SystemConstant.getConstant("POSITION_QUESTION"), "", "POSITION","", personalInfo.getPositionCode(), "", FIELD_ID_PROFESSION, "ALL",  HtmlUtil.elementTagId("POSITION_"+SystemConstant.getConstant("POSITION_QUESTION")), "", formUtil) %></td>
	<td><%=HtmlUtil.textBox("POSITION_OTH", SystemConstant.getConstant("POSITION_QUESTION"), "", "POSITION_OTH_22", personalInfo.getOccpationOther(), "", "100", HtmlUtil.elementTagId("POSITION_OTH_"+SystemConstant.getConstant("POSITION_QUESTION")), "", formUtil)%></td>
	<td><%=HtmlUtil.checkBox("RESULT_CHECK", SystemConstant.getConstant("POSITION_QUESTION"), "", "POSITION", identifyQuestion.getResult(), "", "Y", HtmlUtil.elementTagId("RESULT_CHECK_"+SystemConstant.getConstant("POSITION_QUESTION")), "RESULT_CHECK", "col-sm-8 col-md-12", formUtil)%></td>
</tr>
