<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.LoanDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.model.InstallmentDataM" %>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%> 
<%@ page import="java.math.BigDecimal"%> 


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<span id="errorMessage" class="TextWarningNormal"></span>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("LOAN_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector balloonTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",7);
	Vector paymentVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",8);
	Vector downTypeVect = cacheUtil.loadCacheByName("DownType");
	Vector bankVect = cacheUtil.loadCacheByName("BankInformation");
	Vector vatVect = cacheUtil.loadCacheByName("VATRate");	
	
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
	
	String formId = ORIGForm.getFormID();
	/*if(formId!=null&&formId.equals("CAR_DETAIL_FORM")){
		loanDataM.setLoanType(ORIGForm.getAppForm().getLoanType());
	}*/
	String readOnlyCost = "readOnly";
	String styleCost = "textboxCurrencyReadOnly";
	String readOnlyTotal = "readOnly";
	String styleTotal = "textboxCurrencyReadOnly";
	String readOnlyBalloon = "readOnly";
	String styleBalloon = "textboxCurrencyReadOnly";
	String readOnlyInstallment = "";
	String styleInstallment = "textboxCurrency";
	System.out.println("loanDataM.getVAT() = "+loanDataM.getVAT());
	if(OrigConstant.NO_VAT.equals(loanDataM.getVAT())){
		readOnlyCost = "";
		styleCost = "textboxCurrency";
	}else{
		readOnlyTotal = "";
		styleTotal = "textboxCurrency";
	}
	if("Y".equals(loanDataM.getBalloonFlag())){
		readOnlyBalloon = "";
		styleBalloon = "textboxCurrency";
		readOnlyInstallment = "readOnly";
		styleInstallment = "textboxCurrencyReadOnly";
	}
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	String displayForXCMR = displayMode;
	if(OrigConstant.ROLE_XCMR.equals(userRole)){
		displayForXCMR = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	}
	if(ORIGUtility.isEmptyString(loanDataM.getCollectionCode())){
		if(OrigConstant.LOAN_TYPE_FLEET.equals(loanDataM.getLoanType())){
			//loanDataM.setCollectionCode(OrigConstant.DEFAULT_COLLECTION_CODE_FLEET);
			loanDataM.setCollectionCode("006");
		}else{
			loanDataM.setCollectionCode(OrigConstant.DEFAULT_COLLECTION_CODE);
		}
	}
	if(ORIGUtility.isEmptyString(loanDataM.getCollectorCode())){
		loanDataM.setCollectorCode(OrigConstant.DEFAULT_COLLECTOR_CODE);
	}
	String loanTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("Product", loanDataM.getLoanType());
	String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
	String campaignDesc = cacheUtil.getORIGMasterDisplayNameDataM("Campaign", loanDataM.getCampaign());
	String inCheckDesc = cacheUtil.getORIGMasterDisplayNameDataM("InternalChecker", loanDataM.getInternalCkecker());
	String exCheckDesc = cacheUtil.getORIGMasterDisplayNameDataM("ExternalChecker", loanDataM.getExternalCkecker());
	String creditDesc = cacheUtil.getORIGCacheDisplayNameDataMByType(loanDataM.getCreditApproval(), "CREDITAPRV");
	String collectionDesc = cacheUtil.getORIGMasterDisplayNameDataM("CollectionCode", loanDataM.getCollectionCode());
	String collectorDesc = cacheUtil.getORIGMasterDisplayNameDataM("CollectorCode", loanDataM.getCollectorCode());	
	String branckDesc = null;
	String disableBranch = "";
	if(campaignDesc!=null && campaignDesc.indexOf( "\"")!=-1){
	   campaignDesc=campaignDesc.replaceAll("\"","&quot;");
	}
	
	//String disableStyleBranch = "textbox";
	if(ORIGUtility.isEmptyString(loanDataM.getBankCode())){
		branckDesc = "";
		disableBranch = "disabled";
		//disableStyleBranch = "textboxReadOnly";
	}else{
		branckDesc = cacheUtil.getORIGCacheDisplayNameFormDB(loanDataM.getBranchCode(), OrigConstant.CacheName.CACHE_NAME_BRANCH, loanDataM.getBankCode());
	}
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	String sDate;
	
	String disabledForDE = "";
	String disableStyleForDE = "textbox";
	if(OrigConstant.ROLE_DE.equals(userRole) || OrigConstant.ROLE_CMR.equals(userRole)){
		disabledForDE = "disabled";
		disableStyleForDE = "textboxReadOnly";
	}
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
	
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}else{
		if(!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
			displayForXCMR = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
			disabledBtn = ORIGDisplayFormatUtil.DISABLED;
		}
	}
   String stepInstallment="disabled";
   if("".equals(disabledBtn)&& OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag()) ){
    stepInstallment="";
   }
   boolean enableStepInstallment=false;
   if(OrigConstant.ORIG_FLAG_Y.equals(utility.getGeneralParamByCode("STEP_INSTALLMENT_FLAG")) ){
   enableStepInstallment=true;
   }
	
%>
<script language="JavaScript" src="naosformutil.js"></script>
<script language="JavaScript" src="keypress.js"></script>
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
	<tr>
		<td height="20">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">
				<tr><td colspan="3" class="sidebar8">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
			    	<tr><td class="sidebar9">
						<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
				 			<tr> <td  height="10">
				 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "LOAN")%> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    
	                                    </td></tr>
	                                </table>
	                            </td></tr>	
	                            </table>
	                        </td></tr>
							<tr> <td  height="15"></td></tr>  
							<tr class="sidebar10"> <td align="center">
								<table cellpadding="0" cellspacing="0" width="100%" align="center">	
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","loan_type")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getLoanType(),displayMode,"10","loan_type","60","loan_type_desc","textboxReadOnly","readOnly","30","...","button_text","LoadLoanType",loanTypeDesc,"LoanType") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MKT_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","mkt_code")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getMarketingCode(),displayMode,"10","mkt_code","60","mkt_code_desc","textbox","","30","...","button_text","LoadMarkettingCode",mktDesc,"LoanOfficer") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CAMPAIGN") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","campaign")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptJSBusClassAction(loanDataM.getCampaign(),displayForXCMR,"10","campaign","60","campaign_desc","textbox","","30","...","button_text","LoadCampaign",campaignDesc,"Campaign","", "AL_ALL_ALL") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "INTERNAL_CHECKER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","internal")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getInternalCkecker(),displayMode,"10","internal","60","internal_desc","textbox","","30","...","button_text","LoadInternalChecker",inCheckDesc,"InternalChecker") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXTERNAL_CHECKER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","external")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getExternalCkecker(),displayMode,"10","external","60","external_desc","textbox","","30","...","button_text","LoadExternalChecker",exCheckDesc,"ExternalChecker") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_APPROVAL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","credir_approval")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCreditApproval(),displayMode,"10","credir_approval","60","credir_approval_desc","textboxReadOnly","readOnly","","...","button_text","LoadCreditApproval",creditDesc,"CREDITAPRV") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "COLLECTION_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","collection_code")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCollectionCode(),displayMode,"10","collection_code","60","collection_code_desc","textbox","","30","...","button_text","LoadCollectionCode",collectionDesc,"CollectionCode") %></td>
									<td class="inputCol">&nbsp;</td>
									<td class="inputCol" colspan="3"><%if(enableStepInstallment){%><%=ORIGDisplayFormatUtil.displayCheckBoxTag("S",loanDataM.getInstallmentFlag(),displayMode,"installment_flag","onClick=\"javascript:setInstallmentFlag();\"",MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT")) %><%} %>
									</td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "APPRAISAL_PRICE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","appraisal_price")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getAppraisalPrice()),displayMode,"26","appraisal_price","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);calculateAppraisalPercent()\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %>
									<% 
										BigDecimal appraisalPercent = new BigDecimal(0);
										if(loanDataM.getCostOfFinancialAmt() != null && loanDataM.getAppraisalPrice() != null && (loanDataM.getAppraisalPrice().compareTo(new BigDecimal(0)) != 0)){
											appraisalPercent = loanDataM.getCostOfFinancialAmt().divide(loanDataM.getAppraisalPrice(),10,0).multiply(new BigDecimal(100));
										}
									%>
									<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(appraisalPercent),displayMode,"11","appraisal_price_percent","textboxCurrencyReadOnly","readOnly","3") %>%</td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BALLOON") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","balloon_flag")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayCheckBoxTag("Y",loanDataM.getBalloonFlag(),displayMode,"balloon_flag","onClick=\"javascript:setBalloon();\"",MessageResourceUtil.getTextDescription(request, "BALLOON_TYPE")) %>
										<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(balloonTypeVect,loanDataM.getBalloonType(),"balloon",displayMode,"style=\"width:68%;\" ") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BANK_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","bank")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(bankVect,loanDataM.getBankCode(),"bank",displayMode,"onChange=\"javascript:setBranch(this.value,'branch','branch_desc')\"") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BRANCH_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","branch")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getBranchCode(),displayMode,"10","branch","60","branch_desc","textbox",disableBranch,"30","...","button_text","LoadBranch",new String[]{"bank"},disableBranch,ORIGDisplayFormatUtil.displayHTML(branckDesc),"Branch") %></td>
								</tr>
								<tr>
									<td class="textColS" width="12%"><%=MessageResourceUtil.getTextDescription(request, "AC_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","account_no")%></Font> :</td>
									<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getAccountNo(),displayMode,"18","account_no","textbox","","30") %></td>
									<td class="textColS" width="14%"><%=MessageResourceUtil.getTextDescription(request, "AC_NAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","account_name")%></Font> :</td>
									<td class="inputCol" width="11%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getAccountName(),displayMode,"19","account_name","textbox","","30") %></td>
									<%if(loanDataM.getBookingDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getBookingDate()));
									}%>
									<td class="textColS" width="13%"><%=MessageResourceUtil.getTextDescription(request, "BOOKING_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","booking_date")%></Font> :</td>
									<td class="inputCol" width="11%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"15","booking_date","textbox","right","onblur=\"javascript:checkDate('booking_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
									<%if(loanDataM.getContractDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getContractDate()));
									}%>
									<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","contact_date")%></Font> :</td>
									<td class="inputCol" width="14%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"15","contact_date","textbox","right","onblur=\"javascript:checkDate('contact_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "COLLECTOR_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","collector_code")%></Font> :</td>
									<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCollectorCode(),displayMode,"10","collector_code","60","collector_code_desc","textbox","","30","...","button_text","LoadCollectorCode",collectorDesc,"CollectorCode") %></td>
									<%if(loanDataM.getFirstDueDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getFirstDueDate()));
									}%>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FIRST_DUE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","first_due_date")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"15","first_due_date","textbox","right","onblur=\"javascript:checkDate('first_due_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
									<%if(loanDataM.getEndDueDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getEndDueDate()));
									}%>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "END_DUE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","end_due_date")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(sDate,displayMode,"15","end_due_date","textboxReadOnly","readOnly","") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DOWN_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","down_type")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(downTypeVect,loanDataM.getDownType(),"down_type",displayMode,"onChange=\"javascript:setDownType();\"") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DOWN_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","down_amount")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment()),displayMode,"19","down_amount","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PAYMENT_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","payment_type")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(paymentVect,loanDataM.getPaymentType(),"payment_type",displayMode,"") %></td>
									<%if(loanDataM.getDocumentDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getSysNulldate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getDocumentDate()));
									}%>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DOCUMENT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","document_date")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"15","document_date","textbox","right","onblur=\"javascript:checkDate('document_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>					
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NET_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","net_rate")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getNetRate())),displayMode,"18","net_rate","textboxCurrency","","") %>%</td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SPECIAL_HIRE_CHARGE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","special_hire_charge")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getSpecialHireCharge(),displayMode,"19","special_hire_charge","textboxCurrencyReadOnly","readOnly","30") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FIRST_INSTALLMENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","first_installment")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstInstallment()),displayMode,"15","first_installment","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></td>
									<%if(loanDataM.getChequeDate()==null){
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getSysNulldate()));
									}else{
										sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getChequeDate()));
									}%>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CHEQUE_PAYMENT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","cheque_payment_date")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"15","cheque_payment_date","textbox","right","onblur=\"javascript:checkDate('cheque_payment_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
								</tr>
								</table>																
							</td></tr>
							</table>
						</td>
						</tr>
					</table>
				</td></tr>
				<tr><td colspan="3" class="sidebar8">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
			    	<tr><td class="sidebar9">
						<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
				 			<tr> <td  height="10">
				 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          	<tr><td class="text-header-detail"></td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    
	                                    </td></tr>
	                                </table>
	                            </td></tr>	
	                            </table>
	                        </td></tr>
							<tr> <td  height="15"></td></tr>  			
							<tr class="sidebar10"> <td align="center">
								<table cellpadding="0" cellspacing="0" width="100%" align="center">
								<tr>
									<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CAMPAIGN_TYPE") %> :</td>
									<td class="inputCol" width="10%"></td>
									<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "COST") %></td>
									<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "VAT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","vat")%></Font>&nbsp;&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(vatVect,loanDataM.getVAT(),"vat",displayMode,"onChange=\"javascript:setVat();\"") %>%</td>
									<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "TOTAL") %></td>									
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CAR_PRICE") %> :</td>
									<td class="textColS">&nbsp;</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfCarPrice()),displayMode,"","car_price_cost",styleCost,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyCost,"") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfCarPrice()),displayMode,"","car_price_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfCarPrice()),displayMode,"","car_price_total",styleTotal,"onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyTotal,"") %></td>									
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DOWN_PAYMENT") %> :</td>
									<td class="textColS">&nbsp;</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment()),displayMode,"","down_payment_cost",styleCost,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyCost,"") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfDownPayment()),displayMode,"","down_payment_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfDownPayment()),displayMode,"","down_payment_total",styleTotal,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyTotal,"") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FINANCE_AMT") %> :</td>
									<td class="textColS">&nbsp;</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt()),displayMode,"","finance_cost","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfFinancialAmt()),displayMode,"","finance_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfFinancialAmt()),displayMode,"","finance_total","textboxCurrencyReadOnly","readOnly","") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BALLOON_AMT") %> :</td>
									<td class="textColS">&nbsp;</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfBalloonAmt()),displayMode,"","balloon_cost","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfBalloonAmt()),displayMode,"","balloon_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfBalloonAmt()),displayMode,"","balloon_total",styleBalloon,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+readOnlyBalloon,"") %></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","rate1")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getRate1())),displayMode,"5","rate1","textboxCurrency","","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRate1()),displayMode,"","rate1_cost","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfRate1()),displayMode,"","rate1_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfRate1()),displayMode,"","rate1_total","textboxCurrencyReadOnly","readOnly","") %></td>
								</tr>
								<%
									int installment1=0;
									if(loanDataM.getInstallment1()!=null){
										installment1 = loanDataM.getInstallment1().intValue();
									}
								%>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TERM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_POPUP","installment1")%></Font> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(installment1),displayMode,"5","installment1","textboxCurrency","onKeyPress=\"keyPressInteger();\"","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfInstallment1()),displayMode,"","installment1_cost","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfInstallment1()),displayMode,"","installment1_vat","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfInstallment1()),displayMode,"","installment1_total","textboxCurrencyReadOnly","readOnly","") %></td>
								</tr>	
								<tr>
									<td class="textColS" colspan="5" height="30"></td>					
								</tr>									
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "HIRE_PURCHASE_AMT") %> :</td>
									<td class="textColS">&nbsp;</td>
									<% 
									String stepInstalmentScript=" ReadOnly";
								    String stepInstalmentStyle="textboxCurrencyReadOnly";
								    if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){ 				     
								    stepInstalmentStyle="textboxCurrency";
								    stepInstalmentScript="";
								    }				 
									%>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfHairPurchaseAmt()),displayMode,"","hire_purchase_cost",stepInstalmentStyle, " onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+stepInstalmentScript,"") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfHairPurchaseAmt()),displayMode,"","hire_purchase_vat","textboxCurrencyReadOnly","","") %></td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfHairPurchaseAmt()),displayMode,"","hire_purchase_total",stepInstalmentStyle, " onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+stepInstalmentScript,"") %></td>
									
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EFFECTIVE_RATE") %> :</td>
									<td class="textColS">&nbsp;</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getEffectiveRate())),displayMode,"","effective_rate","textboxCurrencyReadOnly","readOnly","") %></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "IRR_RATE") %> :</td>
									<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getIRRRate())),displayMode,"","irr_rate","textboxCurrencyReadOnly","readOnly","") %></td>
								</tr>
								</table>
							</td></tr>
							</table>
						</td>
						</tr>
					</table>
				</td></tr>
				<tr><td colspan="3" class="sidebar8">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
			    	<tr><td class="sidebar9">
						<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
				 			<tr> <td  height="10">
				 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ORIGINAL_REQ") %></td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    
	                                    </td></tr>
	                                </table>
	                            </td></tr>	
	                            </table>
	                        </td></tr>
							<tr> <td  height="15"></td></tr> 	
							<tr class="sidebar10"> <td align="center">
								<table cellpadding="0" cellspacing="0" width="100%" align="center">
									<tr>   
								        <td class="textColS" width="60%"><%=MessageResourceUtil.getTextDescription(request, "DOWN_PAYMENT") %> :</td>
								        <td class="inputCol" width="40%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getOrigDownPayment()),displayMode,"","down_payment","textboxCurrencyReadOnly","readOnly","") %></td>
								    </tr>
								    <tr>
								        <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FINANCIAL_AMT") %> :</td>
								        <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getOrigFinance()),displayMode,"","financial_amt","textboxCurrencyReadOnly","readOnly","") %></td>
								    </tr>
								      <% 
								      	int term = 0;
								      	if(loanDataM.getOrigInstallmentTerm() != null){
								      		term = loanDataM.getOrigInstallmentTerm().intValue();
								      	}
								      %>
								    <tr>
								        <td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TERM") %> :</td>
								        <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.formatNumber("###,###", term)),displayMode,"","install_1","textboxCurrencyReadOnly","readOnly","") %></td>
								    </tr>
								      <%if(OrigConstant.ROLE_UW.equals(userRole)){ %>
									 <tr>
									   	<td colspan="2" align="right"><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "ACTUAL_PRICE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT, "button", "actualBnt", "button_text", "onClick=\"javascript:loadActualPopup()\"", "") %></td>
									 </tr>  
									  <%} %>                 
								</table>	
							</td></tr>
							</table>
						</td>
						</tr>
					</table>
				</td></tr>
				<% if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){ %>  					 
				<tr><td colspan="3" class="sidebar8">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
			    	<tr><td class="sidebar9">
						<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
				 			<tr> <td  height="10">
				 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %></td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    
	                                    </td></tr>
	                                </table>
	                            </td></tr>	
	                            </table>
	                        </td></tr>
							<tr> <td  height="15"></td></tr> 	
							<tr class="sidebar10"> <td align="center">
								<table cellpadding="0" cellspacing="0" width="100%" align="center"><tr><td>
  					 				<div id="idStepInstallment">
	  					 				<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr><td class="TableHeader">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
							  					 	<td width="20%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></TD>
							  					 	<td width="40%" class="Bigtodotext3"  align="center"><%=MessageResourceUtil.getTextDescription(request, "INSTALLMENT") %></td>
							  					 	<td width="40%" class="Bigtodotext3" align="center"><%=MessageResourceUtil.getTextDescription(request, "TERM") %></td>
							  					</tr>
											</table> 
										</td></tr>
	  					 <% 
	  					 Vector vStepInstallment=loanDataM.getStepInstallmentVect();
	  					 if(vStepInstallment==null){vStepInstallment=new Vector();}
	  					 if(vStepInstallment.size()>0){
	  					 for(int i=0;i<vStepInstallment.size();i++){
	  					     InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
	  					  %>
  					  				<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
					  					   <tr><td width="20%" align="right" class="jobopening2"><%=i+1%>&nbsp;</td>
					  					       <td width="40%" align="right" class="jobopening2"><%=ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentDataM.getInstallmentAmount()) %>&nbsp;</td>
					  					       <td width="40%" align="right" class="jobopening2"><%=ORIGDisplayFormatUtil.displayInteger(prmInstallmentDataM.getTermDuration())%>&nbsp;</td>
					  					   </tr> 
					  					</table> 
									</td></tr>   					 
                     		<%}%>	
                     	<%}else{ %>				   				 
                       				<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
									  			<td class="jobopening2" colspan="15" align="center">No Record</td>
									  		</tr>
										</table> 
									</td></tr>
                     <%}%>
                     			</table>
								</div>
							</td></tr>
						</table>
						</td></tr>
  					 </table>
				</td></tr>
			</table>
			</td></tr>
  		 <% }%>
			<tr><td>
			<%if(OrigConstant.ROLE_XCMR.equals(userRole)){ %>
			<table width="100%">
				<tr align="right"><td><%if(enableStepInstallment){ %><input type="button" name="stepInstallmentBnt" value="<%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %>" onClick="javascript:popupStepInstallment()" class="button" <%=stepInstallment %>><%}%>
					<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "SAVE"), displayForXCMR,"button", "saveBnt", "button", "onClick=\"javascript:saveDataForXcmr()\"", "") %>
					<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), displayForXCMR,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %></td>
				</tr>
			</table>
			<%}else{ %>
			<table width="100%">
				<tr align="right">
					<td><%if(enableStepInstallment){ %>
					<input type="button" name="stepInstallmentBnt" value="<%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %>" onClick="javascript:popupStepInstallment()" class="button" <%=stepInstallment%> ><%}%>
					<input type="button" name="calculateBnt" value="<%=MessageResourceUtil.getTextDescription(request, "CALCULATE") %>" onClick="javascript:mandateLoanForCal('<%=personalInfoDataM.getCustomerType()%>','')" class="button" <%=disabledBtn %>>
					<input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryLoanPopupField('<%=personalInfoDataM.getCustomerType()%>','')" class="button" <%=disabledBtn %>>
					<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %></td>
				</tr>
			</table>
			<%} %>
		</td></tr>
	</TABLE>
</td></tr>
</table>

<input type="hidden" name="car_type">
<input type="hidden" name="actual_car_price" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualCarPrice())%>">
<input type="hidden" name="actual_down" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualDown())%>">
<script language="JavaScript">
if(opener.document.appFormName.car != null){
	form = document.appFormName;
	form.car_type.value = opener.document.appFormName.car.value;
	<%if("EDIT".equals(displayMode)){%>
		if(form.car_type.value == 'N'){
			var appraisalObj = document.getElementById("appraisal_price"); 
			var appraisalPercentObj = document.getElementById("appraisal_price_percent");
			appraisalObj.className = 'textboxCurrencyReadOnly';
			appraisalObj.readOnly = true;
			appraisalPercentObj.className = 'textboxCurrencyReadOnly';
			appraisalPercentObj.readOnly = true;
		}
	<%}%>
}
function saveData(){
	form = document.appFormName;
	form.action.value = "SaveLoan";
	form.handleForm.value = "N";
	if(opener.document.appFormName.final_credit_limit != null){
		opener.document.appFormName.final_credit_limit.value = form.finance_cost.value;
		<%  BigDecimal installment=loanDataM.getTotalOfInstallment1();
		      if(installment==null){installment=new BigDecimal(0);}
		 %>
		var oldInstallment=Number(<%=ORIGDisplayFormatUtil.decimalFormatNoE(installment.doubleValue())%>);
		var newInstallment=Number(removeCommas(form.installment1_total.value));
		//var oldTerm=(<%=installment1%>);
		//alert( oldInstallment+ "   " +newInstallment );
		 if(oldInstallment!=newInstallment){
         var   debtAmt=window.opener.document.getElementById('<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>');
         var   debtAmtAdjust=window.opener.document.getElementById('<%=(String)XRulesConstant.hResultBoxName.get( String.valueOf(XRulesConstant.ServiceID.DEBT_AMOUNT))%>Adjust');
         if(debtAmt){
          debtAmt.value='';
         }
         if(debtAmtAdjust){
          debtAmtAdjust.value='';
         }  	
         }			         
	}
	form.submit();
	var openerForm = opener.document.appFormName;
	var uwDecision = openerForm.decision_uw;
	if(uwDecision != null){
		if(uwDecision[0].checked){
			opener.clearEscalateGroup();
			opener.checkApproveApp();
		}else if(uwDecision[1].checked){
			opener.clearEscalateGroup();
		}else if(uwDecision[2].checked){
			opener.clearEscalateGroup();
		}else if(uwDecision[3].checked){
			opener.clearEscalateGroup();
			opener.checkExceptionApp();
		}else if(uwDecision[4].checked){
			opener.getEscalateGroup();
		}else if(uwDecision[5].checked){
			opener.clearEscalateGroup();
			opener.checkExceptionApp();
		}else if(uwDecision[6].checked){
			opener.clearEscalateGroup();
			opener.checkApproveApp();
		}else if(uwDecision[7].checked){
			opener.clearEscalateGroup();
			opener.checkApproveApp();
		}
	}
}
function saveDataForXcmr(){
	form = document.appFormName;
	form.action.value = "SaveLoan";
	form.handleForm.value = "N";
	form.submit();
}
function closePopup(){
	window.close();
}
function setVat(){
	form = document.appFormName;
	var downType=form.down_type.value;
	if(form.vat.value == '<%=OrigConstant.NO_VAT%>'){
		form.car_price_cost.readOnly = false;
		var x=document.getElementById('car_price_cost');
		x.className = "textboxCurrency";
		form.car_price_total.readOnly = true;
		x=document.getElementById('car_price_total');
		x.className = "textboxCurrencyReadOnly";
		if(downType!='<%=OrigConstant.DOWN_TYPE_NODOWN%>'){
		form.down_payment_cost.readOnly = false;
		x=document.getElementById('down_payment_cost');
		x.className = "textboxCurrency";
		}
		form.down_payment_total.readOnly = true;
		x=document.getElementById('down_payment_total');
		x.className = "textboxCurrencyReadOnly";
		if(form.installment_flag.value=='S'){
	      obj_hire_purchase_cost=document.getElementById("hire_purchase_cost");
          form.hire_purchase_cost.readOnly=false;
          obj_hire_purchase_cost.className = "textboxCurrency";
          obj_hire_purchase_total=document.getElementById("hire_purchase_total");
          form.hire_purchase_total.readOnly=true;
         obj_hire_purchase_total.className = "textboxCurrencyReadOnly";
          
		}
	}else{
		form.car_price_cost.readOnly = true;
		var x=document.getElementById('car_price_cost');
		x.className = "textboxCurrencyReadOnly";
		form.car_price_total.readOnly = false;
		x=document.getElementById('car_price_total');
		x.className = "textboxCurrency";
		form.down_payment_cost.readOnly = true;
		x=document.getElementById('down_payment_cost');
		x.className = "textboxCurrencyReadOnly";
		if(downType!='<%=OrigConstant.DOWN_TYPE_NODOWN%>'){
		form.down_payment_total.readOnly = false;
		x=document.getElementById('down_payment_total');
		x.className = "textboxCurrency";
		}
		if(form.installment_flag.value=='S'){
	      obj_hire_purchase_total=document.getElementById("hire_purchase_total");
          form.hire_purchase_total.readOnly=false;
          obj_hire_purchase_total.className = "textboxCurrency";
          obj_hire_purchase_cost=document.getElementById("hire_purchase_cost");
         obj_hire_purchase_cost.readOnly=true;
         obj_hire_purchase_cost.className = "textboxCurrencyReadOnly";
		}
	}
}
function setBalloon(){
	form = document.appFormName;
	if(form.balloon_flag.checked){
		form.balloon_total.readOnly = false;
		var x=document.getElementById('balloon_total');
		x.className = "textboxCurrency";
		//form.balloon_amt_term.readOnly = false;
		//x=document.getElementById('balloon_amt_term');
		//x.className = "textboxCurrency";
		//form.installment1.readOnly = true;
		//x=document.getElementById('installment1');
		//x.className = "textboxCurrencyReadOnly";
	}else{
		form.balloon_total.readOnly = true;
		var x=document.getElementById('balloon_total');
		x.className = "textboxCurrencyReadOnly";
		//form.balloon_amt_term.readOnly = true;
		//x=document.getElementById('balloon_amt_term');
		//x.className = "textboxCurrencyReadOnly";
		//form.installment1.readOnly = false;
		//x=document.getElementById('installment1');
		//x.className = "textboxCurrency";
	}
}
function loadActualPopup(){
	var url = "/ORIGWeb/FrontController?action=LoadActualPopup";
	var childwindow = window.open(url,'','width=500,height=130,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
<%if(ORIGUtility.isEmptyString(loanDataM.getVAT())){%>
	var objPriceCost = document.getElementById('car_price_cost');
	var objPriceTotal = document.getElementById('car_price_total');
	var objDownCost = document.getElementById('down_payment_cost');
	var objDownTotal = document.getElementById('down_payment_total');
	if(opener.document.appFormName.car.value == '<%=OrigConstant.CAR_TYPE_NEW%>'){
		document.appFormName.vat.value = '<%=OrigConstant.HAVE_VAT%>';
		objPriceCost.className = "textboxCurrencyReadOnly";
		objPriceCost.readOnly = true; 
		objDownCost.className = "textboxCurrencyReadOnly";
		objDownCost.readOnly = true;
		objDownTotal.className = "textboxCurrency";
		objDownTotal.readOnly = false; 
 		objPriceTotal.className = "textboxCurrency";
		objPriceTotal.readOnly = false;
	}else{
		document.appFormName.vat.value = '<%=OrigConstant.NO_VAT%>';
		objPriceCost.className = "textboxCurrency";
		objPriceCost.readOnly = false; 
		objDownCost.className = "textboxCurrency";
		objDownCost.readOnly = false;
		objDownTotal.className = "textboxCurrencyReadOnly";
		objDownTotal.readOnly = true;
 		objPriceTotal.className = "textboxCurrencyReadOnly";
		objPriceTotal.readOnly = true;
	}
<%}%>
<%if( OrigConstant.DOWN_TYPE_NODOWN.equals(loanDataM.getDownType())){%>
        document.appFormName.down_payment_cost.className = "textboxCurrencyReadOnly";
		document.appFormName.down_payment_cost.readOnly = true;
		document.appFormName.down_payment_total.className = "textboxCurrencyReadOnly";
		document.appFormName.down_payment_total.readOnly = true;
<%}%>

addScript2Uppercase(window.document.appFormName);
function addScript2Uppercase(form){
	var elements = form.elements;
	for(var i = 0; i < elements.length; i++){
		var element = elements[i];
		if(element.type == 'text'){
			var eventOnBlur = element.onblur;
			var newFunction = "";
			if(eventOnBlur != null){
				var eventOnBlurStr = eventOnBlur.toString();
				newFunction = "change2Uppercase('" + element.name + "'); " + eventOnBlurStr.substring(eventOnBlurStr.indexOf("{") + 1, eventOnBlurStr.lastIndexOf("}")); 
			}else{
				newFunction = "change2Uppercase('" + element.name + "'); ";
			}
			var func = new Function(newFunction);
			element.onblur = func;
		}
	}
}

function change2Uppercase(textFieldName){
	var textField = eval("window.document.appFormName." + textFieldName);
	if(!isUndefined(textField)){
		var values = textField.value;
		textField.value = values.toUpperCase();
	}
}
function calculateAppraisalPercent(){
	var form = document.appFormName;
	if(form.appraisal_price != null){
		var appraisal = form.appraisal_price.value;
		if(appraisal != '' && parseFloat(appraisal) != 0){
			var financeAmount = form.finance_cost.value;
			financeAmount = removeCommas(financeAmount);
			appraisal = removeCommas(appraisal);
			var percent = parseFloat(financeAmount)/parseFloat(appraisal)*100;
			percent = Math.round(percent*100)/100
			form.appraisal_price_percent.value  = percent;
		}else{
			form.appraisal_price_percent.value  = "0.00";
		}
		addCommas( "appraisal_price" );
		addDecimalFormat(form.appraisal_price);
		addCommas( "first_installment" );
		addDecimalFormat(form.first_installment);
		addDecimalFormat(form.appraisal_price_percent);
	}
	
}
function setDownType(){
   // alert('down_type');
	form = document.appFormName;
	if(form.down_type.value == '<%=OrigConstant.DOWN_TYPE_NODOWN%>'){
		/*form.car_price_cost.readOnly = false;
		var x=document.getElementById('car_price_cost');
		x.className = "textboxCurrency";
		form.car_price_total.readOnly = true;
		x=document.getElementById('car_price_total');
		x.className = "textboxCurrencyReadOnly";*/
		form.down_payment_cost.readOnly = true;
		x=document.getElementById('down_payment_cost');
		x.className = "textboxCurrencyReadOnly";
		x.value='';
		form.down_payment_total.readOnly = true;
		x=document.getElementById('down_payment_total');
		x.className = "textboxCurrencyReadOnly";
		x.value='';
	}else{
		/*form.car_price_cost.readOnly = true;
		var x=document.getElementById('car_price_cost');
		x.className = "textboxCurrencyReadOnly";
		form.car_price_total.readOnly = false;
		x=document.getElementById('car_price_total');
		x.className = "textboxCurrency";*/		
		if(form.vat.value == '<%=OrigConstant.NO_VAT%>'){
		form.down_payment_cost.readOnly = false;
		x=document.getElementById('down_payment_cost');
		x.className = "textboxCurrency";
		}else{
		form.down_payment_total.readOnly = false;
		x=document.getElementById('down_payment_total');
		x.className = "textboxCurrency";
		}
	}
}
function setInstallmentFlag(){
   form = document.appFormName;
  // alert('Step installment Click');
      if( form.installment_flag.checked){   
      form.stepInstallmentBnt.disabled=false;
        obj=document.getElementById("idStepInstallment");
        obj.style.display ="inline"; 
        if(form.vat.value == '<%=OrigConstant.NO_VAT%>'){
        obj_hire_purchase_cost=document.getElementById("hire_purchase_cost");
        obj_hire_purchase_cost.readOnly=false;
        obj_hire_purchase_cost.className = "textboxCurrency";
        }else{        
        obj_hire_purchase_total=document.getElementById("hire_purchase_total");
        form.hire_purchase_total.readOnly=false;
        obj_hire_purchase_total.className = "textboxCurrency";
        }
      }else{
        form.stepInstallmentBnt.disabled=true;    
        obj=document.getElementById("idStepInstallment");
        obj.style.display = "none";
       // if(form.vat.value == '<%=OrigConstant.NO_VAT%>'){
        obj_hire_purchase_cost=document.getElementById("hire_purchase_cost");
        obj_hire_purchase_cost.readOnly=true;
        obj_hire_purchase_cost.className = "textboxCurrencyReadOnly";
       // }  else{
        obj_hire_purchase_total=document.getElementById("hire_purchase_total");
        form.hire_purchase_total.readOnly=true;
        obj_hire_purchase_total.className = "textboxCurrencyReadOnly";
       // }
     }              
 }     
function popupStepInstallment(){
     form = document.appFormName;
     var vat=form.vat.value;
     var term=form.installment1.value;
     var hire_purchase_total=form.hire_purchase_total.value;
     //alert(hire_purchase_total);
    var pram="&vat="+vat+"&term="+term+"&hire_purchase_total="+hire_purchase_total;
    //alert(pram);
    var url = "/ORIGWeb/FrontController?action=LoadStepInstallmentPopup"+pram;
	var childwindow = window.open(url,'popupInstallment','width=800,height=400,top=200,left=200,scrollbars=1,status=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>