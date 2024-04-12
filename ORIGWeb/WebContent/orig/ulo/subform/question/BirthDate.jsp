<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="org.apache.commons.beanutils.BeanComparator"%>
<%@page import="java.util.Comparator"%>
<%@page import="com.eaf.orig.ulo.model.app.PhoneVerificationDataM"%>
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
<jsp:useBean id="CacheControl" scope="session" class="com.eaf.core.ulo.common.properties.CacheControl"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		String QuestionSet =SystemConstant.getConstant("QUESTION_SET_A");
		String personalId= request.getParameter("PERSONAL_ID");
		logger.debug("personalId >>>?"+personalId);
		PersonalInfoDataM personalInfo	 = applicationGroup.getPersonalInfoId(personalId);
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
			applicationGroup.addPersonalInfo(personalInfo);
		}
		
		VerificationResultDataM verificationResult= personalInfo.getVerificationResult();
		if(Util.empty(verificationResult)){
			verificationResult = new VerificationResultDataM();
			personalInfo.setVerificationResult(verificationResult);
		}
		ArrayList<IdentifyQuestionDataM> identifyQuestions=  verificationResult.getIndentifyQuestions();
		StringBuilder htmlDisplay = new StringBuilder();
		String questionDesc ="";
		String questionCode ="";
		String seq ="";
		 
		if(!Util.empty(identifyQuestions)){
				for(IdentifyQuestionDataM identifyQuestion:identifyQuestions){
					if(!Util.empty(identifyQuestion.getQuestionNo()) && identifyQuestion.getQuestionNo().equals(SystemConstant.getConstant("DATE_OF_BIRTH_QUESTION"))){
				 		 questionDesc = CacheControl.getName("QuestionCacheDataM",identifyQuestion.getQuestionNo());
				 		 seq = FormatUtil.getString(identifyQuestion.getSeq()) ;
				 }
			}
		}
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode =FormDisplayModeUtil.getDisplayMode("", "", formUtil);
		
		 
	
	logger.debug("questionDesc >> "+questionDesc);
%>
<tr>	
	<td width="5%">
		<div class="col-sm-12">
			<div class="form-group">
				<div class="col-sm-8 col-md-7"><%=FormatUtil.display(seq)%></div>
			</div>
		</div>
	</td>
		<td width="20%">
			<div class="col-sm-12">
				<div class="form-group">
					<%=FormatUtil.display(questionDesc)  %><%= HtmlUtil.getMandatoryLabel(request, "")%> 
				</div>
			</div>
			</td>
			<td width="5%">
			<div class="col-sm-12">
				<div class="form-group">
					<%=LabelUtil.getText(request, "CE") %>
				</div>
			</div>
			</td>
			<td width="20%">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.calendar("BIRTH_DATE_CE", personalInfo.getPersonalId(), "", personalInfo.getBirthDate(), "", displayMode, HtmlUtil.elementTagId("BIRTH_DATE_CE_"+personalInfo.getPersonalId()), HtmlUtil.EN, "col-sm-8 col-md-7", request)%>
				</div>
			</div>
			</td>
			<td width="5%">
			<div class="col-sm-12">
				<div class="form-group">
					<%=LabelUtil.getText(request, "BE") %>
				</div>
			</div>
			</td>
			<td width="20%">
			<div class="col-sm-12">
					<div class="form-group">
							<%=HtmlUtil.calendar("BIRTH_DATE_BE", personalInfo.getPersonalId(), "", personalInfo.getBirthDate(), "", displayMode, HtmlUtil.elementTagId("BIRTH_DATE_BE_"+personalInfo.getPersonalId()), HtmlUtil.TH, "col-sm-8 col-md-7", request)%>
					</div>
			</div>
			</td>
			

<%=HtmlUtil.hidden("QUESTION_NO", questionCode) %>
</tr>