<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/question/js/VrhrResult.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String VER_HR_RESULT_QUESTION = SystemConstant.getConstant("VER_HR_RESULT_QUESTION");
	String VER_HR_RESULT_TRUE = SystemConstant.getConstant("VER_HR_RESULT_TRUE");
	String FIELD_ID_VER_HR_RESULT = SystemConstant.getConstant("FIELD_ID_VER_HR_RESULT");
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
	 personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
	 verificationResult = new VerificationResultDataM();
	}
	IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(VER_HR_RESULT_QUESTION);
	if(Util.empty(identifyQuestion)){
	 identifyQuestion = new IdentifyQuestionDataM();
	}
	
	logger.debug("identifyQuestion >>> "+identifyQuestion.getCustomerAnswer());
	String subformId = "IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
%>

<td width="180px"><%=HtmlUtil.getSubFormLabel(request, subformId, "VER_HR_RESULT")%></td>
<td width="280px">
<%-- 	<%=HtmlUtil.dropdown("VER_HR_RESULT", VER_HR_RESULT_QUESTION, "ALL", identifyQuestion.getCustomerAnswer(), "", FIELD_ID_VER_HR_RESULT, "ALL", HtmlUtil.EDIT, HtmlUtil.tagId("VER_HR_RESULT_"+VER_HR_RESULT_QUESTION), "", request)%> --%>
<%-- 	<%=HtmlUtil.dropdown(String name,String suffix,String id,String configId,String listboxConfigId,String value,String style,String cacheId,String typeId,String tag,String gridId,FormUtil form) %> --%>
<%-- 	<%=HtmlUtil.dropdown("PERMANENT_STAFF", PERMANENT_STAFF_QUESTION, "", "PERMANENT_STAFF","",personalInfo.getPermanentStaff(), "", FIELD_ID_PERMANENT_STAFF, "ALL", HtmlUtil.tagId("PERMANENT_STAFF_"+PERMANENT_STAFF_QUESTION), "", formUtil)%> --%>
	<%=HtmlUtil.dropdown("VER_HR_RESULT", VER_HR_RESULT_QUESTION, "VER_HR_RESULT_"+VER_HR_RESULT_QUESTION, "ALL", "", identifyQuestion.getCustomerAnswer(), "", FIELD_ID_VER_HR_RESULT, "ALL", HtmlUtil.elementTagId("VER_HR_RESULT_"+VER_HR_RESULT_QUESTION), "", formUtil) %>
</td>
<td style="padding-left:30px"></td>