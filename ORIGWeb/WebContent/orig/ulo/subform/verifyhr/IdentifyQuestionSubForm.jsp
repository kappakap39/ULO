<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="org.apache.log4j.Logger"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	String[] QUESTION_SET_VERIFY_HR = SystemConstant.getArrayConstant("QUESTION_SET_VERIFY_HR");
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}	
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();	
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
	String verifyHrQuestionSet = verificationResult.getVerifyHrQuestionSet(QUESTION_SET_VERIFY_HR);
	logger.debug("verifyHrQuestionSet >> "+verifyHrQuestionSet);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "VERIFY_CUSTOMER_DATA")%></div>
<table class="table table-striped table-hover">
	<tbody>
		<%	ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_HR);
			ArrayList<String> implementIds = new ArrayList<String>();
			int rowNum = 1;
			for(ElementInf element:elements){
				String questionNo = element.getImplementId();
				IdentifyQuestionDataM indentifyQuestion = verificationResult.getIndentifyQuesitionNo(questionNo);
				if(!Util.empty(indentifyQuestion)){
					implementIds.add(questionNo);
		%>
			<tr>
				<td width="10px"><%=rowNum++%></td>
				<%element.writeElement(pageContext,personalInfo);%>
			</tr>
		<%
				} 
			}
		%>
		<% if(Util.empty(implementIds)){%>			
			<tr><td colspan="999" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>	
		<%} %>	
	</tbody>
</table>
</div>


