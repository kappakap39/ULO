<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<% 
	Logger log = Logger.getLogger(this.getClass());
	ORIGUtility utility = new ORIGUtility();
	ORIGFormUtil formUtil = new ORIGFormUtil();
	TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	PLApplicationDataM plapplicationDataM = PLORIGForm.getAppForm();
	PLPersonalInfoDataM plPersonM = plapplicationDataM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
	
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("PRODUCT_FUTURE_SUBFORM", ORIGUser.getRoles(), plapplicationDataM.getJobState(), PLORIGForm.getFormID(), searchType);

%>
<script type="text/javascript" src="orig/js/subform/pl/product_future.js"></script>
<!-- product_future -->
<table class="FormFrame">
		<tr> 
			<td class="textR" nowrap="nowrap" width="20%" ><%=PLMessageResourceUtil.getTextDescription(request, "CAR_OWNER") %>&nbsp;:&nbsp;</td>
			<td class="inputL"  width="25%" ><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(78, plapplicationDataM.getBusinessClassId(), plPersonM.getCarOwner(),"p_car_owner",displayMode,"")%></td>
			<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_END") %>&nbsp;:&nbsp;</td>
			<td class="inputL"  width="30%" nowrap="nowrap">
		        <table border="0" cellSpacing="0" cellPadding="0" width="150">
		        	<tbody>
		            	<tr height="25">
		               		<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "MONTH") %>&nbsp;</td>
		               		<td class="inputL"><%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(plPersonM.getCarLastInst_month()), displayMode, "2", "p_payment_month","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","2")%>&nbsp;</td>
		               		<td class="textR">&nbsp;<%=PLMessageResourceUtil.getTextDescription(request, "YEAR") %>&nbsp;</td>
		               		<td class="inputL"><%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(plPersonM.getCarLastInst_year()), displayMode, "4", "p_payment_year","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","4")%></td>
		              	</tr>
		       		</tbody>
			   </table>
		   </td>
		</tr>
		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENY_WITH") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(plPersonM.getCarLoanWith(), displayMode, "120", "p_payment_with","textbox","","120")%></td>
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PAYMENT_AMOUNT") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.DisplayInputCurrency(plPersonM.getCarFinancialAmount(),displayMode,"########0.00", "p_payment_amount","textbox-currency","","9",false)%></td>
		</tr>
		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CAR_TYPE") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(79, plapplicationDataM.getBusinessClassId(), plPersonM.getCarType(), "p_car_type", displayMode,"")%></td>
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MONTH_OF_INSURANCE_END") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(plPersonM.getCarInsureExpMonth()), displayMode, "4", "p_month_insurance_end","textboxCode","onkeypress= \"return keyPressInteger(event)\" ","2")%></td>
		</tr>
		
		
		<tr> 
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "INSURANCE") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(80, plapplicationDataM.getBusinessClassId(), plPersonM.getInsurance(), "p_insurance", displayMode, "")%></td>
			<td></td>
			<td></td>
		</tr>
		
	</table>