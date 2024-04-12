<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="org.apache.log4j.Logger"%>

<script type="text/javascript" src="orig/ulo/subform/question/js/PermanentStaff.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
	 personalInfo = new PersonalInfoDataM();
	}
	String PERMANENT_STAFF_QUESTION = SystemConstant.getConstant("PERMANENT_STAFF_QUESTION");
	String FIELD_ID_PERMANENT_STAFF = SystemConstant.getConstant("FIELD_ID_PERMANENT_STAFF");
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
	 verificationResult = new VerificationResultDataM();
	}
	
	IdentifyQuestionDataM identifyQuestion= verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("PERMANENT_STAFF_QUESTION"));
	if(Util.empty(identifyQuestion)){
	 identifyQuestion = new IdentifyQuestionDataM();
	}
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<td><%=HtmlUtil.getFieldLabel(request, "PERMANENT_STAFF","")%></td>
<td><%=HtmlUtil.dropdown("PERMANENT_STAFF", PERMANENT_STAFF_QUESTION, "", "PERMANENT_STAFF","",personalInfo.getPermanentStaff(), "", FIELD_ID_PERMANENT_STAFF, "ALL", HtmlUtil.elementTagId("PERMANENT_STAFF_"+PERMANENT_STAFF_QUESTION), "", formUtil)%></td>
<td></td>
