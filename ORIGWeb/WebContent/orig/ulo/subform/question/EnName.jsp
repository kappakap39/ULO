<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
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
	
		String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		String personalId =request.getParameter("PERSONAL_ID");
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
		String questionDesc ="";
		String questionCode ="";
		String seq ="";
		 
		if(!Util.empty(identifyQuestions)){
				for(IdentifyQuestionDataM identifyQuestion:identifyQuestions){
					if(!Util.empty(identifyQuestion.getQuestionNo()) &&  SystemConstant.getConstant("NAME_QUESTION").equals(identifyQuestion.getQuestionNo())){
				 		 questionDesc = CacheControl.getName("QuestionCacheDataM",identifyQuestion.getQuestionNo());
				 		 seq = FormatUtil.getString(identifyQuestion.getSeq()) ;
				 }
			}
		}
		
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>	



<tr>
	<td width="5%">
	<div class="col-sm-12">
		<div class="form-group">
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(seq)%></div>
		</div>
	</div>
	</td>
	<td colspan="2">
	<div class="col-sm-12">
		<div class="form-group">
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(questionDesc)%></div>
		</div>
	</div>
	</td>
	  <td colspan="3">
	    <div class="col-xs-2">
			<div class="input-group">
			 <%=HtmlUtil.dropdown("EN_TITLE_CODE", personalInfo.getPersonalId(), "", FIELD_ID_EN_TITLE_CODE, "", personalInfo.getEnTitleCode(), "", FIELD_ID_EN_TITLE_CODE, "", HtmlUtil.elementTagId("EN_TITLE_CODE_"+personalInfo.getPersonalId()), "col-sm-8 col-md-12", formUtil)%>
			</div>
			</div>
	  <div class="col-xs-10">
			<div class="input-group">
				<%=HtmlUtil.textBoxEN("EN_FIRST_NAME", personalInfo.getPersonalId(), "", "", personalInfo.getEnFirstName(), "textbox-name-1","120", HtmlUtil.elementTagId("EN_FIRST_NAME"+"_"+personalInfo.getPersonalId()), "input-group-btn", formUtil)%>
               	<%=HtmlUtil.textBoxEN("EN_MID_NAME",personalInfo.getPersonalId(),"","",personalInfo.getEnMidName(),"textbox-name-2","60",HtmlUtil.elementTagId("EN_MID_NAME"+"_"+personalInfo.getPersonalId()),"input-group-btn",formUtil)%>
	             <%=HtmlUtil.textBoxEN("EN_LAST_NAME",personalInfo.getPersonalId(),"","",personalInfo.getEnLastName(),"textbox-name-3","50",HtmlUtil.elementTagId("EN_LAST_NAME"+"_"+personalInfo.getPersonalId()),"input-group-btn",formUtil)%>
			</div>
		</div>
	<%=HtmlUtil.hidden("QUESTION_NO", questionCode) %>
</tr>
		
