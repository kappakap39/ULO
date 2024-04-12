<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/ListSupPersonalInfoSubForm.js"></script>
<%
	String subformId = "LIST_SUP_PERSONAL_INFO_SUBFORM_2";
	Logger logger = Logger.getLogger(this.getClass());	
	String displayMode = HtmlUtil.EDIT;
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FIELD_ID_RELATION_WITH_APPLICANT = SystemConstant.getConstant("FIELD_ID_RELATION_WITH_APPLICANT");
	logger.debug("PERSONAL_TYPE_SUPPLEMENTARY >> "+PERSONAL_TYPE_SUPPLEMENTARY);
	ArrayList<PersonalInfoDataM> personals = ORIGForm.getObjectForm().getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);	
	logger.debug("personals.size >> "+personals.size());
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");
		
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
%>
<div class="panel panel-default">
<table class="table table-striped table-hover">
	<%
		if(!Util.empty(personals)){
		int ROW_NUM = 1;
		for(PersonalInfoDataM personalInfo:personals){
			String PERSONAL_ID = personalInfo.getPersonalId();
 			String PERSONAL_SEQ = FormatUtil.getString(personalInfo.getSeq());
 			String PERSONAL_TYPE = personalInfo.getPersonalType();
 			String loadActionJS = "onclick=LOAD_SUP_PERSONAL_INFOActionJS('"+PERSONAL_ID+"','"+PERSONAL_SEQ+"','"+PERSONAL_TYPE+"')";
 			String deleteActionJS = "";
	%>
	 	<tr>
	 		<td width="5%"><%=HtmlUtil.icon("DELETE_SUP_PERSONAL_INFO","DELETE_SUP_PERSONAL_INFO","btnsmall_delete",deleteActionJS,formEffect) %></td>
	 		<td><%= ROW_NUM %>. <%=HtmlUtil.linkText("LOAD_SUP_PERSONAL_INFO",personalInfo.getThName(),HtmlUtil.EDIT,loadActionJS + " style='display: inline-block'",request)%></td>
	 		<td><%=ListBoxControl.getName(FIELD_ID_RELATION_WITH_APPLICANT, personalInfo.getRelationshipType())%></td>
	 	</tr>
	 <%ROW_NUM++;}%>
	 <%}else{%>
	 	<tr>
	 		<td align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	 <%}%>
</table>
</div>