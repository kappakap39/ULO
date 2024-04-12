<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXrulesNplLpmDataM"%>
<%@page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%
	PLApplicationDataM appM  = PLORIGForm.getAppForm();
	
	if(null == appM) appM = new PLApplicationDataM();
	
	PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
	PLXRulesVerificationResultDataM xRulesVerM = personalM.getXrulesVerification();
	if(null == xRulesVerM) xRulesVerM = new PLXRulesVerificationResultDataM();
		
	PLXrulesNplLpmDataM xrulesNpmM = xRulesVerM.getxRulesNplLpmM();
	if(null == xrulesNpmM) xrulesNpmM = new PLXrulesNplLpmDataM();	
	
%>
<div class="PanelFirst">
	<div class="PanelSecond TextHeaderNormal">
		<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NPL_NAME_SURNAME") %>&nbsp;:</td>
					<td class="textL" width="25%">
					<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiFirstName())%>
					&nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiLastName())%>
					</td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "NPL_ID_NO") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.displayHTML((String) personalM.getIdNo())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "NPL_BIRTH_DAY") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DateEnToStringDateTh(personalM.getBirthDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %>
					</td>
					<td class="textR" nowrap="nowrap" width="25%"></td>
					<td class="textL" width="30%"></td>
				</tr>
				<tr></tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "AMC_TAMC_Flag_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getAmcTamcFlag()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"></td>
					<td class="textL" width="30%"></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Classify_Level_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getClassifyLevel()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Current_NPL_Flag_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getCurrentNPLFlag()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Guarantor_Flag_(Index)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getGuarantorFlagIndex()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Bankruptcy_Litigation_Forced_Closed_Flag_(Index)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getBankruptcyLitigationForcedClosedFlagIndex()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Unique_Customer_Found_Flag_(Index)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getUniqueCustomerFoundFlagIndex()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Unique_Customer_Found_Flag_(AMC)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getUniqueCustomerFoundFlagAMC()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Max_Delinquency_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getMaxDelinquency()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Last_Restructure_Date_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DateEnToStringDateTh(xrulesNpmM.getLastRestructureDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstanding())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterest())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimit())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Status_Restructure_Flag_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.replaceNull(xrulesNpmM.getStatusRestructureFlag()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Outstanding_Greater_than_30_Days_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getOutstandingGreaterthan30Days())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Existing_Unsecured_Limits_with_KBANK_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getExistingUnsecuredLimitswithKBANK())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_of_Revolving_Credit_Accs_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberofRevolvingCreditAccs()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Number_of_Non-Revolving_Credit_Accs_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberofNonRevolvingCreditAccs()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_Loan_for_Better_Life_(LBL)_Loans_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberLoanforBetterLifeLBLLoans()) %></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_LBL_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitLBL())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_LBL_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalanceLBL())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Monthly_Installment_Amount_LBL_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalMonthlyInstallmentAmountLBL()) %></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_LBL_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getWorstDelinquencyDaysLBL())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_LBL_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestLBL())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_Provident_Fund_(PF)_Loans_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberProvidentFundPFLoans())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_PF_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitPF())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_PF_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalancePF())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Monthly_Installment_Amount_PF_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalMonthlyInstallmentAmountPF())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_PF_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getWorstDelinquencyDaysPF())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_PF_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestPF())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_of_Other_Term(OT)_Loan(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberofOtherTermOTLoan())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_OT_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitOT())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_OT_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalanceOT())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Monthly_Installment_Amount_OT_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalMonthlyInstallmentAmountOT())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_OT_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getWorstDelinquencyDaysOT())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_OT_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestOT())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_Credit_Cards_(CC)_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberCreditCardsCC())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_CC_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitCC())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_CC_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalanceCC())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_CC_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getWorstDelinquencyDaysCC())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_CC_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestCC())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Number_Overdraft_(OD)_Loans_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberOverdraftODLoans())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_OD_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitOD())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_OD_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalanceOD())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_OD_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getWorstDelinquencyDaysOD())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_OD_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestOD())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Number_Other_Lending_Accounts_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayNumberNullEmpty(xrulesNpmM.getNumberOtherLendingAccounts())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Limit_Other_Lending_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalLimitOtherLending())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Outstanding_Balance_Other_Lending_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalOutstandingBalanceOtherLending())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Worst_Delinquency_Days_Other_Lending_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getWorstDelinquencyDaysOtherLending())%></td>
				</tr>
				<tr>
					<td class="textR" nowrap="nowrap" width="20%"><%=MessageResourceUtil.getTextDescription(request, "Total_Monthly_Installment_Amount_Other_Lending_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="25%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalMonthlyInstallmentAmountOtherLending())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "Total_Accrued_Interest_Other_Lending_(LPM)") %>&nbsp;:</td>
					<td class="textL" width="30%"><%=DataFormatUtility.DisplayCurrencyNullEmpty(xrulesNpmM.getTotalAccruedInterestOtherLending())%></td>
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