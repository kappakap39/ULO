<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<%
	PLApplicationDataM appM  = PLORIGForm.getAppForm();
	
	if(null == appM) appM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
	
	if(xrulesVerM == null){
	 	xrulesVerM = new PLXRulesVerificationResultDataM();
	}
	PLXRulesFraudDataM fraudM = xrulesVerM.getxRulesFraudDataMs();
	
	if(null == fraudM) fraudM = new PLXRulesFraudDataM();
	
	ORIGCacheUtil origCache = new ORIGCacheUtil();
	
 %>

<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_NAME_SURNAME") %>&nbsp;:</td>
					<td class="textL" width="25%">
					<%=HTMLRenderUtil.displayHTML((String) fraudM.getFirstName())%>
					&nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML((String) fraudM.getLastName())%>
					</td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_BIRTH_DAY") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.displayHTML(fraudM.getBirthDate()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_TYPE_OF_DOCUMENT") %>&nbsp;:</td>
					<td class="textL"><%=origCache.getNaosCacheDisplayNameDataM(2,fraudM.getIdDoc())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_ID_NO") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getIdNo())%></td>
				</tr>
				<tr></tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_FROM") %>&nbsp;:</td>
					<td class="textL"><%=origCache.getNaosCacheDisplayNameDataM(125,fraudM.getSourceOfFraudInfo())%></td>
					<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_REASON") %>:</td>
					<td class="textL"><%=origCache.getNaosCacheDisplayNameDataM(126,fraudM.getFraudReason())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_NOTE_1") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudRemark1())%></td>
					<td class="textR" nowrap="nowrap"></td>
					<td class="textL"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_NOTE_2") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudRemark2())%></td>
					<td class="textR" nowrap="nowrap"></td>
					<td class="textL"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_NOTE_3") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudRemark3())%></td>
					<td class="textR" nowrap="nowrap"></td>
					<td class="textL"></td>
				</tr>
				<tr></tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_CREATE_DATE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudInfoCreateDate())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_CREATE_BY") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudInfoCreateBy())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_LAST_EDITDATE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudInfoUpdateDate())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_LAST_EDITBY") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudM.getFraudInfoUpdateBy())%></td>
				</tr>	
			</table>
		</div>
	</div>
</div>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>