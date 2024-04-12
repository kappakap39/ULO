<%@page import="com.eaf.orig.ulo.model.app.QuestionObjectDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%	
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String personalId = request.getParameter("PERSONAL_ID");
	QuestionObjectDataM questionObject = new QuestionObjectDataM();
		questionObject.setPersonalId(personalId);
		questionObject.setApplicationGroup(applicationGroup);
// 	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
	String[] QUESTION_SET_VERIFY_CUSTOMER = SystemConstant.getArrayConstant("QUESTION_SET_VERIFY_CUSTOMER");
	String QUESTION_SET_TYPE_VERIRY_CUSTOMER = SystemConstant.getConstant("QUESTION_SET_TYPE_VERIRY_CUSTOMER");
	String CALL_TO_APPLICANT = SystemConstant.getConstant("CALL_TO_APPLICANT");
	String CALL_TO_SUPPLEMENTARY = SystemConstant.getConstant("CALL_TO_SUPPLEMENTARY");
	
	String FLAG_ENABLED = SystemConstant.getConstant("FLAG_ENABLED");
	
	ArrayList<IdentifyQuestionSetDataM> identifyQuestionSets = verificationResult.getVerifyCustomerQuestionSets(QUESTION_SET_VERIFY_CUSTOMER);
	if(Util.empty(identifyQuestionSets)){
		identifyQuestionSets = new ArrayList<IdentifyQuestionSetDataM>();
	}
	ArrayList<IdentifyQuestionDataM> identifyQuestions = verificationResult.filterIndentifyQuesitions(QUESTION_SET_TYPE_VERIRY_CUSTOMER);
	if(Util.empty(identifyQuestions)){
		identifyQuestions = new ArrayList<IdentifyQuestionDataM>();
	}
	
	if(!Util.empty(identifyQuestionSets) && !Util.empty(identifyQuestions)){
		for(IdentifyQuestionSetDataM identifyQuestionSet : identifyQuestionSets){
			String QuestionSetItem = identifyQuestionSet.getQuestionSetCode();
			questionObject.setQuestionSetCode(QuestionSetItem);
			String callTo = identifyQuestionSet.getCallTo();
			String callToDesc = "";
			if(CALL_TO_APPLICANT.equals(callTo)){
				callToDesc = HtmlUtil.getText(request,"BORROWER");
			}else if(CALL_TO_SUPPLEMENTARY.equals(callTo)){
				callToDesc = HtmlUtil.getText(request,"SUPPLEMENTARY");
			}
%>
<div class="section-border">
	<div class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-7">
			<%=HtmlUtil.getFieldLabel(request,"VERIFY_CUSTOMER_DATA","")%> 
			<%=HtmlUtil.getText(request, "QUESTION_NO") %> <%=FormatUtil.display(QuestionSetItem) %>
			</div>
			<div class="col-sm-5">
			<%=HtmlUtil.getText(request, "TEL_VERIFY")%>
			<%=callToDesc%>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-default" style="padding:0px;">
	<table class="table table-hover text-center">
		<thead style="background-color:#F1F1F1;">
			<tr>
				<th width="5%"><%=FormatUtil.display("No.")%></th>
				<th width="40%"><%=LabelUtil.getText(request, "QUESTION")%></th>
				<th width="20%"><%=LabelUtil.getText(request, "RESULT")%></th>
				<th width="10%"><%=LabelUtil.getText(request, "NOT_CUSTOMER_ANSWER")%></th>
				<th width="10%"><%=LabelUtil.getText(request, "CORRECT")%></th>
				<th width="15%"><%=LabelUtil.getText(request, "INCORRECT")%></th>
			</tr>
		</thead>
		<tbody>
			<%	int seq=1;
				for(IdentifyQuestionDataM identifyQuestion : identifyQuestions){
					if(identifyQuestionSet.getQuestionSetCode().equals(identifyQuestion.getQuestionSetCode())){
						if(!Util.empty(identifyQuestion)){
							ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFY_QUESTION_CUSTOMER, identifyQuestion.getQuestionNo());
							elementInf.setObjectRequest(questionObject);
			%>
			<tr>
				<td><%=seq++ %></td>
					<%
							elementInf.writeElement(pageContext, identifyQuestion);
					%>
			</tr>
				<% 		}
					}
				} %>
		</tbody>
	</table>
</div>
	<%	
		}
 	}%>
