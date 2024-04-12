<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
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


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
		String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
		String NAME_QUESTION = SystemConstant.getConstant("NAME_QUESTION");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo	 = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
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
		 
		 
		if(!Util.empty(identifyQuestions)){
				for(IdentifyQuestionDataM identifyQuestion:identifyQuestions){
					if(!Util.empty(identifyQuestion.getQuestionNo()) && NAME_QUESTION.equals(identifyQuestion.getQuestionNo())){
				 		 questionDesc = CacheControl.getName("QuestionCacheDataM",identifyQuestion.getQuestionNo());
				 }
			}
		}
		
	String subformId ="IDENTIFY_QUESTION_CUSTOMER_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>		

<tr>
	<td colspan="3">
	<div class="col-sm-12">
		<div class="form-group">
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(questionDesc)%></div>
		</div>
	</div>
	</td>
	  <td colspan="5">
		   <div class="col-xs-2">
				<div class="input-group">
					  <%=HtmlUtil.dropdown("TH_TITLE_CODE", "", "", "", personalInfo.getThTitleCode(), "", FIELD_ID_TH_TITLE_CODE, "", HtmlUtil.elementTagId("TH_TITLE_CODE"), "", formUtil)%>
				</div>
			</div>
	  	<div class="col-xs-10">
			<div class="input-group">
				<%=HtmlUtil.textBoxTH("TH_FIRST_NAME", "", "", "", personalInfo.getThFirstName(), "textbox-name-1", "120", HtmlUtil.elementTagId("TH_FIRST_NAME"), "input-group-btn", formUtil)%>
               	<%=HtmlUtil.textBoxTH("TH_MID_NAME","","","",personalInfo.getThMidName(),"textbox-name-2","60",HtmlUtil.elementTagId("TH_MID_NAME"),"input-group-btn",formUtil)%>
	             <%=HtmlUtil.textBoxTH("TH_LAST_NAME","","","",personalInfo.getThLastName(),"textbox-name-3","50",HtmlUtil.elementTagId("TH_LAST_NAME"),"input-group-btn",formUtil)%>
			</div>
		</div>
	<%=HtmlUtil.hidden("QUESTION_NO", questionCode) %>
</tr>