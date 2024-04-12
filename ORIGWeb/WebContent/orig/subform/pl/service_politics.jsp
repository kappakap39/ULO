<%@page import="com.eaf.orig.ulo.pl.app.utility.ManualMandatory"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLKYCDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/service_politics.js"></script>
<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null == applicationM){
	 	applicationM = new PLApplicationDataM();
	}
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	PLKYCDataM kYCM = applicationM.getKycM();
	
	if(null == kYCM){
		 kYCM = new PLKYCDataM();
	}
	
	String currentRole = ORIGUser.getCurrentRole();
	
	String searchType = (String) request.getSession().getAttribute("searchType");	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("SERVICE_POLITICS_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	ORIGCacheUtil origc = new ORIGCacheUtil();
	
	String displayModeRelationFlag = ORIGLogic.DisplayModeRelationFlag(displayMode,kYCM.getRelationFlag());
	String mandatoryRelationFlag = ORIGLogic.MandatoryRelationFlag(currentRole,kYCM.getRelationFlag());	
%>

<table class="FormFrame">
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "HAVE_RELATED")%>
			<%=ManualMandatory.getManadatory("SERVICE_POLITICS_SUBFORM","have_related",request)%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(86, applicationM.getBusinessClassId(), kYCM.getRelationFlag(), "have_related",displayMode,"")%></td>
		<td class="textR" width="25%"></td>
		<td class="textR" width="30%"></td>
	</tr>	
	<tr>
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "NAME_ONLY",ORIGUser,personalM.getCustomerType(),"SERVICE_POLITICS_SUBFORM","ser_name_th")%>		
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>		
		<td class="inputL">
		<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(kYCM.getRelTitleName(),3,displayModeRelationFlag,"sertitleThai","textbox-code","50"
						,kYCM.getRelName(),"ser_name_th","textbox","120","ser_title_thai"
							,"LoadTitleThaiNew" ,MessageResourceUtil.getTextDescription(request, "TITLE_THAI"))%>
		</td>		
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "SURNAME_ONLY")%>
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>		
		<td class="inputL"><%=HTMLRenderUtil.displayInputTag(kYCM.getRelSurName(),displayModeRelationFlag,"50","ser_surname_thai","textbox") %></td>
	</tr>
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "POSITION")%>
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"><%=HTMLRenderUtil.displayInputTag(kYCM.getRelPosition(),displayModeRelationFlag,"100","ser_position","textbox") %></td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "HOW_RELATION")%>
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"><%=HTMLRenderUtil.displayInputTag(kYCM.getRelDetail(),displayModeRelationFlag,"100","ser_relation","textbox") %></td>
	</tr>	
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "POSITION_DURATION")%>
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>
		<td class="inputL" id="tr_ser_position_duration"><%=HTMLRenderUtil.displayInputTagJsDate("",DataFormatUtility.datetoStringForThai(kYCM.getWorkStartDate()),displayModeRelationFlag,"10","ser_position_duration","textbox","","") %></td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "TO_YEARS")%>
			<font color="#ff0000" class="mandatory_have_related"><%=mandatoryRelationFlag%></font>&nbsp;:&nbsp;
		</td>
		<td class="inputL" id="tr_ser_to_years"><%=HTMLRenderUtil.displayInputTagJsDate("",DataFormatUtility.datetoStringForThai(kYCM.getWorkEndDate()),displayModeRelationFlag,"10","ser_to_years","textbox posDuration","left","") %></td>
	</tr>	
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "SANCTION_LIST")%>
			<%=ManualMandatory.getManadatory("SERVICE_POLITICS_SUBFORM","ser_sanction_list",request)%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(87, applicationM.getBusinessClassId(), kYCM.getSanctionList(), "ser_sanction_list",displayMode,"")%>
		</td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CUSTOMER_RISK_GRADE")%>
			<%=ManualMandatory.getManadatory("SERVICE_POLITICS_SUBFORM","ser_customer_risk",request)%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(88, applicationM.getBusinessClassId(), kYCM.getCustmoerRiskGrade(), "ser_customer_risk",displayMode,"")%>
		</td>
	</tr>
</table>
<%=HTMLRenderUtil.displayHiddenField(currentRole, "currentrole_servicepolitics")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode, "displaymode_servicepolitics")%>