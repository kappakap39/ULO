<%@page import="com.eaf.xrules.shared.constant.PLXrulesConstant"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="java.util.Vector"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudCompanyDataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFraudDataM"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<%
	PLApplicationDataM appM  = PLORIGForm.getAppForm();	
	if(null == appM) appM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	PLXRulesVerificationResultDataM xRulesVerM = personalM.getXrulesVerification();
	
	if(null == xRulesVerM) xRulesVerM = new PLXRulesVerificationResultDataM();	
	Vector<PLXRulesFraudCompanyDataM> fraudCompanyVect = xRulesVerM.getXrulesFraudCompVect();
	
 %>
<script type="text/javascript" src="orig/js/popup/pl/fraudcompanyPopup.js"></script>
<div class="PanelFirst">
	<%if(!OrigUtil.isEmptyVector(fraudCompanyVect)){%>	
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-fraud-madatory"></div>
			<div class="PanelThird">			
					<%for(PLXRulesFraudCompanyDataM fraudCompM:fraudCompanyVect){%>
						<table class="FormFrame">
							<tr>
								<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_NAME") %>:</td>
								<td class="textL" width="25%">
								<%=HTMLRenderUtil.displayHTML((String) fraudCompM.getCompanyTitle())%>
								<%=HTMLRenderUtil.displayHTML((String) fraudCompM.getCompanyName())%>
								</td>
								<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_DIRECTOR") %>:</td>
								<td class="textL" width="25%"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getCompanyOwner())%></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_OFFICE_ADDRESS") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getAddress())%></td>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_DETAIL") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getRemark())%></td>
							</tr>
							<tr style="height: 35px; vertical-align: bottom;">
								<td colspan="4" class="textL" nowrap="nowrap" style="font-weight: bold;">
								<%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_REASON") %>
								</td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_NOTE_1") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getFraudRemark1())%></td>
								<td class="textR" nowrap="nowrap"></td>
								<td class="textL"></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_NOTE_2") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getFraudRemark2())%></td>
								<td class="textR" nowrap="nowrap"></td>
								<td class="textL"></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_NOTE_3") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getFraudRemark3())%></td>
								<td class="textR" nowrap="nowrap"></td>
								<td class="textL"></td>
							</tr>
							<tr></tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_CREATE_DATE") %>:</td>
								<td class="textL"><%=DataFormatUtility.DateFormat(fraudCompM.getFraudInfoCreateDate(),"ddMMyyyy")%></td>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_CREATE_BY") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getFraudInfoCreateBy())%></td>
							</tr>
							<tr>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_LAST_EDITDATE") %>:</td>
								<td class="textL"><%=DataFormatUtility.DateFormat(fraudCompM.getFraudInfoUpdateDate(),"ddMMyyyy")%></td>
								<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY_LAST_EDITBY") %>:</td>
								<td class="textL"><%=HTMLRenderUtil.displayHTML((String) fraudCompM.getFraudInfoUpdateBy())%></td>
							</tr>
							<tr></tr>
						
					<%}%>
					
					<%if(PLXrulesConstant.SearchType.SEARCH_TYPE_LIKE.equals(xRulesVerM.getFraudCompanySearchType())){ %>
						<tr>
							<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "FRUAD_COMPANY_RESULT") %>:</td>
							<td colspan="3">
								<%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(appM.getBusinessClassId(),121,xRulesVerM.getFraudCompanyDecision(),"fraudcomp-decision",HTMLRenderUtil.DISPLAY_MODE_EDIT,"") %>
							</td>
						</tr>
					<%} %>
					</table>			
			</div>
		</div>
	<%}else{%>
		<table class="TableFrame">
			<tr class="ResultNotFound">
	        	<td>No Data Found</td>
	        </tr>
		</table>
	<%}%>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>