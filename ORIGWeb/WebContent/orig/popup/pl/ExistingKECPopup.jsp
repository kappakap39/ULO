<%@page import="com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.ulo.pl.constant.CacheConstant"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesExistCustDataM"%>
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

	PLXRulesVerificationResultDataM xRulesVerM = personalM.getXrulesVerification();
	if(null == xRulesVerM) xRulesVerM = new PLXRulesVerificationResultDataM();
	EAIDataM eaiM = xRulesVerM.getEaiM();
	
	PLXRulesExistCustDataM xRulesExistM = xRulesVerM.getxRulesExistCustM();	
	if(null == xRulesExistM) xRulesExistM = new PLXRulesExistCustDataM();
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EXT_NAME_THAI")%>&nbsp;:</td>
					<td class="textL" width="25%">
					<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiFirstName())%>
					&nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiLastName())%>
					</td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "EXT_ID_NO")%>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.displayHTML((String) personalM.getIdNo())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_APP_NO")%>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) appM.getApplicationNo())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_TYPE_OF_SALE")%>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTMLCacheDesc((String) appM.getSaleType(),CacheConstant.CacheName.MainSaleType)%></td>
				</tr>
				<tr></tr>			
				<tr>
					<td class="textR" nowrap="nowrap"><b><%=MessageResourceUtil.getTextDescription(request, "EXT_EAI_DATA") %></b></td>
					<td class="textL"></td>
					<td class="textR" nowrap="nowrap"></td>
					<td class="textL"></td>
				</tr>
				
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CARD_NO") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getCardNo())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_KEC_BLOCK_CODE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getBlockCode())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_KEC_CREDIT_LINE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xRulesExistM.getCreditLine()) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_BLOCK_DATE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DateEnToStringDateTh(xRulesExistM.getBlockCodeDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CREDIT_LIMIT") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xRulesExistM.getCreditLimit()) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_LAST_CREDIT_LIMIT_AMOUNT") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xRulesExistM.getLastTemporaryCreditLimitAmount()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CREDIT_LIMIT_DATE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayExistingCreditLimitDate((String)xRulesExistM.getCreditLimitDate()) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_LAST_CREDIT_LIMIT_DATE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DateEnToStringDateTh(xRulesExistM.getLastTemporaryCreditLimitDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CARD_TYPE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getCardType())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_12_MONTH_PROFILE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getMonthsProfile())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CARD_CODE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getCardCode())%></td>
					<td class="textR" nowrap="nowrap" ><%=MessageResourceUtil.getTextDescription(request, "EXT_CURRENT_BALANCE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xRulesExistM.getCurrentBalance()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CARD_LEVEL") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getCardLevel())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_AVE_SPEND_PER_MONTH") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xRulesExistM.getAveMonthRetailSpendL12M()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_EXPIREY_DATE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML(xRulesExistM.getExpriyDate())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_NUMBER_CASH_ADV_L12M") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DisplayNumberNullEmpty(xRulesExistM.getNumberCashAdvancesL12M()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_MEMBER_SINCE_DATE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DateEnToStringDateTh(xRulesExistM.getMemberSinceDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CASH_ADV_LASTEST_DATE") %>&nbsp;:</td>
					<td class="textL"><%=DataFormatUtility.DateEnToStringDateTh(xRulesExistM.getCashAvanceLatestDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_MEMBER_SINCE_TIME") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getMemberSinceTime())%></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_LAST_TEMPORARY_CREDIT_LIMEIT_USED") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String)xRulesExistM.getLastTimporaryCreditLimitUsed()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_PAYMENT_METHOD") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String)xRulesExistM.getPaymentMethod()) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_CARD_BEHAVIOR_SCORE") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String)xRulesExistM.getCardBehavior()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_PAYMENT_CONDITION") %>&nbsp;:</td>
					<td class="textL"><%=HTMLRenderUtil.displayHTML((String)xRulesExistM.getPaymentCondition()) %></td>
					<td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "EXT_BILLING_CYCLE") %>&nbsp;:</td>
					<td class="textL" ><%=HTMLRenderUtil.displayHTML((String) xRulesExistM.getBillingCycle())%></td>
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