<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLBundleALDataM"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(OrigUtil.isEmptyObject(applicationM)){
		 applicationM = new PLApplicationDataM();
	}
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	PLBundleALDataM bundleALM =  applicationM.getBundleALM();
	if(OrigUtil.isEmptyObject(bundleALM)){
	 	bundleALM = new PLBundleALDataM();
	}
	
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil utility = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");	
	String displayMode = formUtil.getDisplayMode("AUTO_LOAN_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
%>
<script type="text/javascript" src="orig/js/subform/pl/auto_loan_information.js"></script>
<table class="FormFrame">
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CAR_BRAND")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"AUTO_LOAN_SUBFORM","car_brand")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundleALM.getCarType(),displayMode,"50","car_brand","textbox","","50") %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CAR_SERIES")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundleALM.getCarModel(),displayMode,"50","car_series","textbox","","50") %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CONTACT_NUMBER")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundleALM.getContractNo(),displayMode,"20","car_contact_no","textbox","","20") %>
		</td>
		<td class="textR" width="25%"></td>
		<td class="inputL"  width="30%"></td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "BUYER_TRUE_SALARY")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getAppSalary(), displayMode, "########0.00", "buyer_true_salary", "textbox-currency", "", "12", false) %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "EVELUATE_SALARY")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getEstSalary(), displayMode, "########0.00", "eveluate_salary", "textbox-currency", "", "12", false) %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "FICO_SCORE")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(DataFormatUtility.StringToBigDecimalEmptyNull(bundleALM.getFicoScore()), displayMode, "##0", "car_fico_score", "textbox-currency", "", "12", true) %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "DEBT_BURDEN_SCORE")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.DisplayInputCurrency(DataFormatUtility.StringToBigDecimalEmptyNull(bundleALM.getDebtBurdenScore()), displayMode, "##0.00", "debt_burden_score", "textbox-currency", "", "12", true) %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "PERCENT_DOWN_PAYMENT")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getDownPayment(), displayMode, "#0.00", "percent_down_payment", "textbox-currency", "", "12", true) %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "LOAN_CAR_CASH")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getHpAmount(), displayMode, "########0.00", "loan_car_cash", "textbox-currency", "", "12", false) %>
		</td>
	</tr>
	
		<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "PAYMENT_PER_MONTH")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getMonthlyInstallment(), displayMode, "########0.00", "payment_per_month", "textbox-currency", "", "12", true) %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "PERMIT_DATE")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displayInputTagJsDate("",DataFormatUtility.DateEnToStringDateTh(bundleALM.getApproveDate(),1),displayMode,"10","permit_date","textbox","right","") %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "OFFICER_CONTACT_NAME")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundleALM.getContractCreateBy(),displayMode,"100","officer_contact_name","textbox","","100") %>
		</td>
		<td class="textR" width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "OFFICER_CREDIT_PERMIT")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displayInputTagScriptAction(bundleALM.getCreditApproveBy(),displayMode,"100","officer_credit_limit","textbox","","100") %>
		</td>
	</tr>
	
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "KEC_CASH_PAYMENT")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrency(bundleALM.getApproveCreditLine(), displayMode, "########0.00", "kec_cash_payment", "textbox-currency", "", "12", false) %>
		</td>
		<td class="textR" width="25%"></td>
		<td class="inputL"  width="30%"></td>
	</tr>
</table>