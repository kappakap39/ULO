<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesSpecialCustomerDataM"%>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="org.apache.log4j.Logger"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/special_customer.js"></script>

<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null == applicationM){
	 	applicationM = new PLApplicationDataM();
	}
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

	PLXRulesVerificationResultDataM xrulesM = personalM.getXrulesVerification();
	if(null == xrulesM){ 
		xrulesM = new PLXRulesVerificationResultDataM();
		personalM.setXrulesVerification(xrulesM);
	}
	PLXRulesSpecialCustomerDataM specialM = xrulesM.getXrulesSpecialCusM();
	if(null == specialM) {
		specialM = new PLXRulesSpecialCustomerDataM();
		xrulesM.setXrulesSpecialCusM(specialM);
	}

	String searchType = (String) request.getSession().getAttribute("searchType");	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("SPECIAL_CUSTOMER_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
		
	BigDecimal csaRiskValue = specialM.getCsaRiskGrade();
	int csaIntVal = 0;
	if(null != csaRiskValue){
		csaIntVal = csaRiskValue.intValue();
	}	
%>
<table class="FormFrame">
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CUSTOMER_GROUP_NAME")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SPECIAL_CUSTOMER_SUBFORM","customer_group_name")%>
			&nbsp;:&nbsp;			
		</td>
		<td class="inputL"  width="25%" id="div_customer_group_name">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null, specialM.getCustomerGroupName(), "customer_group_name",displayMode,"")%>
		</td>
		<td class="textR"  width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CSA_RISK_GRADE")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(97,applicationM.getBusinessClassId(),DataFormatUtility.IntToString(csaIntVal), "csa_risk_name",displayMode,"")%>
		</td>
	</tr>
	
	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "AUM_AVG_MONTH")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
<%-- 		<%=HTMLRenderUtil.displayInputTag(DataFormatUtility.displayCommaNumber(plXruleSpecialDataM.getAumAve()),displayMode,"20","aum_avg_month","textbox") %> --%>
			<%=HTMLRenderUtil.DisplayInputCurrency(specialM.getAumAve(), displayMode, "########0.00", "aum_avg_month","textbox-currency","","12",false)%>
		</td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "PAYROLL_TYPE")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displayInputTag(specialM.getPayrollType(),displayMode,"50","payroll_type","textbox") %>
		</td>
	</tr>

	<tr>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "FINAL_PROXY_INCOME")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"SPECIAL_CUSTOMER_SUBFORM","final_proxy_income")%>
			&nbsp;:&nbsp;
		</td>
		<td class="inputL">
<%-- 		<%=HTMLRenderUtil.displayInputTag(DataFormatUtility.displayCommaNumber(plXruleSpecialDataM.getFinalProxyIncome()),displayMode,"20","final_proxy_income","textbox") %> --%>
			<%=HTMLRenderUtil.DisplayInputCurrency(specialM.getFinalProxyIncome(), displayMode, "########0.00", "final_proxy_income","textbox-currency","","12",false)%>
		</td>
		<td class="textR">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CREDIT_CARD_CREDIT_LINE")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL">
<%-- 		<%=HTMLRenderUtil.displayInputTag(DataFormatUtility.displayCommaNumber(plXruleSpecialDataM.getCreditCardCreditLine()),displayMode,"20","credit_card_line","textbox") %> --%>
			<%=HTMLRenderUtil.DisplayInputCurrency(specialM.getCreditCardCreditLine(), displayMode, "########0.00", "credit_card_line","textbox-currency","","12",false)%>
		</td>
	</tr>	
	<tr>
		<td class="textR"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CONTACT_TYPE")%>&nbsp;:&nbsp;</td>
		<td class="inputL">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(96,applicationM.getBusinessClassId(), specialM.getContractChannel(), "contact_type",displayMode,"")%>
		</td>
		<td class="textR"><%=PLMessageResourceUtil.getTextDescriptionMessage(request, "PREMIUM_ON_TOP")%>&nbsp;:&nbsp;</td>
		<td class="inputL"><%=HTMLRenderUtil.displayInputTag(specialM.getPremiumOnTop(),displayMode,"50","premium_on_top","textbox") %></td>
	</tr>	
</table>