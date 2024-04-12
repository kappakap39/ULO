<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.model.PreScoreDataM" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
//	String displayMode = formUtil.getDisplayMode("OCCUPATION_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
    String displayMode =ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	
	Vector businessTypeVect = utility.loadCacheByName("BusinessType");
	Vector businessSizeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",1);
	Vector landOwnerVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",6);
	Vector NPLVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",18);	
    PreScoreDataM preScoreDataM=personalInfoDataM.getPreScoreDataM();
   	if(preScoreDataM==null){
   	 preScoreDataM=new PreScoreDataM();
   	}
    String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer",preScoreDataM.getMarketingCode());    
   	String cmrDisplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
   	if((ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase( applicationDataM.getJobState()) )){
      	cmrDisplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
   	}  	 
%>
<script language="javascript">
function calFinanceAmt(ojb){
   var form=document.appFormName;
   var strCarPrice =removeCommas(form.pre_score_car_price.value);
   var strDownPayment=removeCommas(form.pre_score_down_payment.value);
   var numberCarPrice=Number(strCarPrice);
   var numberDownPayment=Number(strDownPayment);
   if(numberCarPrice>= numberDownPayment){    
   form.pre_score_financeAmtVAT.value=(numberCarPrice - numberDownPayment);
     addComma(form.pre_score_financeAmtVAT);
     addDecimalFormat(form.pre_score_financeAmtVAT);
    }else {
    alert("<%=ErrorUtil.getShortErrorMessage(request,"downpayment_greater_than_carprice")%>");
    form.pre_score_down_payment.value=0;
   }
}
</script>
<table cellpadding="" cellspacing="1" width="100%">
	<tr>
		<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "MKT_CODE") %><Font color="#ff0000"><%//=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","mkt_code")%></Font> :</td>
		<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(preScoreDataM.getMarketingCode(),cmrDisplayMode,"5","pre_score_mkt_code","20","pre_score_mkt_code_desc","textbox","","20","...","button_text","LoadMarkettingCode",mktDesc,"LoanOfficer") %></td>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "SHARE_CAPITY") %> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getShareCapital()),displayMode,"","pre_score_share_capital","textboxCurrency"," onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+"\" ","10") %> <%-- =msgUtil.getTextDescription(request, "MILLION") --%></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "BUS_SIZE") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessSizeVect,preScoreDataM.getBusinessSize(),"pre_score_bus_size",cmrDisplayMode,"") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "BUS_TYPE") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessTypeVect,preScoreDataM.getBusinessType(),"pre_score_bus_type",cmrDisplayMode,"") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "SALARY") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getSalary()),cmrDisplayMode,"","pre_score_salary","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "OTHER_INCOME") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getOtherIncome()),cmrDisplayMode,"","pre_score_other_income","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "LAND_OWNERSHIP") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(landOwnerVect,preScoreDataM.getLandOwnerShip(),"pre_score_land_ownership",cmrDisplayMode,"") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "CHEQUE_RETURNED") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",preScoreDataM.getChequeReturn()),cmrDisplayMode,"","pre_score_cheque_return","textbox","onKeyPress=\"keyPressInteger();\" onblur=\"javascript:returnZero(this);\" ","2") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "NUMBER_OF_EMPLOYEE") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(preScoreDataM.getNoOfEmployee()),cmrDisplayMode,"","pre_score_no_of_employee","textbox","onKeyPress=\"keyPressInteger();\" onblur=\"javascript:returnZero(this);\" ","4") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "OVERDUE_OVER_60_DAYS") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToStringNCBData(preScoreDataM.getOverDue60dayIn12Month()),displayMode,"","pre_score_overdue_over_60_days","textbox","onKeyPress=\"keyPressIntegerNCBData(this);\" onblur=\"addCommasNCBData(this);\" ","50") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "MAX_TIME_OVERDUE") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToStringNCBData(preScoreDataM.getMaxOverDue6Month()),displayMode,"","pre_score_max_time_overdue","textbox","onKeyPress=\"keyPressIntegerNCBData(this);\" onblur=\"javascript:addCommasNCBData(this);\" ","50") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "NUMBER_OF_REVOLVING_LOAN") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToStringNCBData(preScoreDataM.getNoRevolvingLoan()),displayMode,"","pre_score_number_of_revolving_loan","textbox","onKeyPress=\"keyPressIntegerNCBData(this);\" onblur=\"javascript:addCommasNCBData(this);\" ","50") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "NUMBER_OF_AUTOMOBILE_HIRE_PURCHASE") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToStringNCBData(preScoreDataM.getNoAutoHirePurchase()),displayMode,"","pre_score_number_of_automobile_hire_purchase","textbox"," onKeyPress=\"keyPressIntegerNCBData(this);\"  onblur=\"javascript:addCommasNCBData(this);\" ","50") %></td>
		<td class="textColS" ><%//=msgUtil.getTextDescription(request, "CAR_PRICE") %> <input type="hidden" name="pre_score_car_price" value="800000"></td>
		<td class="inputCol" ><%//=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getCarPrice()),cmrDisplayMode,"","pre_score_car_price","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);calFinanceAmt(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ "onMouseOver=\""+tooltipUtil.getTooltip(request,"prescore_car_vat")+"\" ","16") %></td>			
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "TERM_LOAN") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(preScoreDataM.getTermLoan()),cmrDisplayMode,"","pre_score_term_loan","textbox","onKeyPress=\"keyPressInteger();\" onblur=\"javascript:returnZero(this);\" ","16") %></td>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "DOWN_PAYMENT_VAT") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getDownPayment()),cmrDisplayMode,"","pre_score_down_payment","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);calFinanceAmt(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ "onMouseOver=\""+tooltipUtil.getTooltip(request,"prescore_down_payment")+"\" ","16") %></td>
	</tr>
	<tr>
     	<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "INSTALLMENT_AMT_VAT") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getInstallmentAmountVAT()),cmrDisplayMode,"","pre_score_InstallmentAmtVAT","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td> 
	    <td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "FINANCE_AMT_VAT") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getFinanceAmountVAT()),cmrDisplayMode,"","pre_score_financeAmtVAT","textboxCurrencyReadOnly","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"  readonly","16") %></td>
	</tr>		 
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "HOUSE_ID") %> :</td>
		<td class="inputCol" ><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( preScoreDataM.getHouseIdno(),cmrDisplayMode,"15","pre_score_houseid","textbox ","","15") %></td>	
		<td class="textColS" >&nbsp;</td>
		<td class="inputCol" >&nbsp;</td>	
	</tr>
	<tr>
		<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "NPL_HISTORY_RESTRUCTURE") %> :</td>
		<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(NPLVect,preScoreDataM.getNPL(),"pre_score_NPL_histry_restructure",displayMode,"") %></td>
		<td class="inputCol" >&nbsp;</td>	
	</tr>	 
</table>

