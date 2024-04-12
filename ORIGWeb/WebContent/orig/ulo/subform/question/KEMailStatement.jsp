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
		String seq = request.getParameter("SEQ");
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
// 		String seq ="";
		 
		if(!Util.empty(identifyQuestions)){
				for(IdentifyQuestionDataM identifyQuestion:identifyQuestions){
					if(!Util.empty(identifyQuestion.getQuestionNo()) && identifyQuestion.getQuestionNo().equals(SystemConstant.getConstant("K_EMAIL_STATEMENT_QUESTION"))){
				 		 questionDesc = CacheControl.getName("QuestionCacheDataM",identifyQuestion.getQuestionNo());
// 				 		 seq = FormatUtil.getString(identifyQuestion.getSeq());
				 }
			}
		}
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode =FormDisplayModeUtil.getDisplayMode("", "", formUtil);	
	logger.debug("questionDesc >> "+questionDesc);
%>
				<div class="form-group">
					<div class="" style="display: inline-block; vertical-align: middle;"><%=FormatUtil.display(seq)%>.</div>
					<div style="display: inline-block; width: 500px; white-space: nowrap; margin-left: -9px; height: 21px; vertical-align: middle;">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"EMAIL","EMAIL","col-sm-1 control-label")%>
					<%=HtmlUtil.textBoxEmail("EMAIL_"+personalId, "EMAIL_"+personalId, "EMAIL", personalInfo.getEmailPrimary(), "", "", "", "col-sm-4 col-md-5", formUtil)%></div>
				</div>
<%=HtmlUtil.hidden("QUESTION_NO", questionCode) %>