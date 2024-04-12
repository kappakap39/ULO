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
<%@ page import="com.eaf.orig.cache.properties.ParameterDetailCodeProperties" %>
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

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();	 
	Vector balloonTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",7);
	Vector downTypeVect = utility.loadCacheByName("DownType");
	Vector bankVect = utility.loadCacheByName("BankInformation");
	Vector vatVect = utility.loadCacheByName("VATRate");	
	Vector paymentVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",8);
	Vector firstPayTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",21);
	
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	String displayForXCMR = displayMode;
	if(OrigConstant.ROLE_XCMR.equals(userRole)){
		displayForXCMR = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	}
	
	String readOnlyCost = "readOnly";
	String styleCost = "textboxCurrencyReadOnly";
	String readOnlyTotal = "readOnly";
	String styleTotal = "textboxCurrencyReadOnly";
	if(OrigConstant.NO_VAT.equals(loanDataM.getVAT())){
		readOnlyCost = "";
		styleCost = "textboxCurrency";
	}else{
		readOnlyTotal = "";
		styleTotal = "textboxCurrency";
	}
	String loanTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("Product", loanDataM.getLoanType());
	String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer", loanDataM.getMarketingCode());
	String campaignDesc = ORIGDisplayFormatUtil.replaceDoubleQuot(cacheUtil.getORIGMasterDisplayNameDataM("Campaign", loanDataM.getCampaign()));
	String inCheckDesc = cacheUtil.getORIGMasterDisplayNameDataM("InternalChecker", loanDataM.getInternalCkecker());
	String exCheckDesc = cacheUtil.getORIGMasterDisplayNameDataM("ExternalChecker", loanDataM.getExternalCkecker());
	String creditDesc = cacheUtil.getORIGCacheDisplayNameDataMByType(loanDataM.getCreditApproval(), "CREDITAPRV");
	String collectionDesc = cacheUtil.getORIGMasterDisplayNameDataM("CollectionCode", loanDataM.getCollectionCode());
	String collectorDesc = cacheUtil.getORIGMasterDisplayNameDataM("CollectorCode", loanDataM.getCollectorCode());
	String payDealerDesc = cacheUtil.getORIGCacheDisplayNameFormDB(loanDataM.getPayDealer(),OrigConstant.CacheName.CACHE_NAME_DEALER);//cacheUtil.getORIGMasterDisplayNameDataM("DealerCode", loanDataM.getPayDealer());	
	if(payDealerDesc==null){payDealerDesc="";}
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	String sDate;
	if(campaignDesc!=null && campaignDesc.indexOf( "\"")!=-1){
	   campaignDesc=campaignDesc.replaceAll("\"","&quot;");
	}
	String disabledForDE = "";
	String disableStyleForDE = "textbox";
	if(OrigConstant.ROLE_DE.equals(userRole)){
		disabledForDE = "disabled";
		disableStyleForDE = "textboxReadOnly";
	}
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}else{
		if(!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		}
	}
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
	//System.out.println("loanDataM.getFirstInsPayType() = "+loanDataM.getFirstInsPayType());
    String stepInstallment="disabled";
   if("".equals(disabledBtn)&& OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag()) ){
    stepInstallment="";
   }
   boolean enableStepInstallment=false;
   if(OrigConstant.ORIG_FLAG_Y.equals(utility.getGeneralParamByCode("STEP_INSTALLMENT_FLAG")) ){
   enableStepInstallment=true;
   }
%>
<table width="100%" border=0 cellpadding="0" cellspacing="0">
	<TR><TD height="5" colspan="3"></TD></TR>
	<TR><TD height="2" bgcolor="#3399cc" colspan="3"></TD></TR>
</table>
<div style="background-color: #F4F4F4">
	<SPAN style="background-color: #3399cc; vertical-align: top; height: 16">&nbsp;&nbsp;<font color="#FFFFFF"><b><%=msgUtil.getTextDescription(request, "LOAN")%></b></font>&nbsp;</SPAN><SPAN><img src="en_US/images/SFCorner.gif"></SPAN>&nbsp;
</div>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td>
			<table width="100%">
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "LOAN_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","loan_type")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getLoanType(),displayMode,"10","loan_type","55","loan_type_desc","textboxReadOnly","readOnly","30","...","button_text","LoadLoanType",loanTypeDesc,"LoanType") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "MKT_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","mkt_code")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getMarketingCode(),displayMode,"10","mkt_code","55","mkt_code_desc","textbox","","30","...","button_text","LoadMarkettingCode",mktDesc,"LoanOfficer") %></td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "CAMPAIGN") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","campaign")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptJSBusClassAction(loanDataM.getCampaign(),displayForXCMR,"10","campaign","55","campaign_desc","textbox","","30","...","button_text","LoadCampaign",campaignDesc,"Campaign","", "AL_ALL_ALL") %></td>	
					<td class="textColS"><%=msgUtil.getTextDescription(request, "EXTERNAL_CHECKER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","external")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getExternalCkecker(),displayMode,"10","external","55","external_desc","textbox","","30","...","button_text","LoadExternalChecker",exCheckDesc,"ExternalChecker") %></td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "INTERNAL_CHECKER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","internal")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getInternalCkecker(),displayMode,"10","internal","55","internal_desc","textbox","","30","...","button_text","LoadInternalChecker",inCheckDesc,"InternalChecker") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "COLLECTION_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","collection_code")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCollectionCode(),displayMode,"10","collection_code","55","collection_code_desc","textbox","","30","...","button_text","LoadCollectionCode",collectionDesc,"CollectionCode") %></td>			
			   </tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "CREDIT_APPROVAL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","credir_approval")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCreditApproval(),displayMode,"10","credir_approval","55","credir_approval_desc","textboxReadOnly","readOnly","","...","button_text","LoadCreditApproval",creditDesc,"CREDITAPRV") %></td>		
					<td class="inputCol">&nbsp;</td>
					<td class="inputCol">&nbsp;<%if(enableStepInstallment){%><%=ORIGDisplayFormatUtil.displayCheckBoxTag("S",loanDataM.getInstallmentFlag(),displayMode,"installment_flag","onClick=\"javascript:setInstallmentFlag();\"",msgUtil.getTextDescription(request, "STEP_INSTALLMENT")) %><%}%>
					</td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "COLLECTOR_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","collector_code")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCollectorCode(),displayMode,"10","collector_code","55","collector_code_desc","textbox","","30","...","button_text","LoadCollectorCode",collectorDesc,"CollectorCode") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "DEPOSIT_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","down_type")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(downTypeVect,loanDataM.getDownType(),"down_type",displayMode,"style=\"width:96%;\" ") %></td>
				</tr>
				<tr>	
					<td class="textColS"><%=msgUtil.getTextDescription(request, "NET_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","net_rate")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getNetRate())),displayMode,"","net_rate","textbox","style=\"width:97%;\" ","") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "SUBSIDIES_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","subsidies_amount")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSubsidiesAmount()),displayMode,"","subsidies_amount","textboxCurrencyReadOnly","style=\"width:96%;\" readOnly","") %></td>
				</tr>
				<%if(loanDataM.getBookingDate()==null){
					sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
				}else{
					sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getBookingDate()));
				}%>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "BOOKING_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","booking_date")%></Font> :</td>
					<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","booking_date","textbox","right","onblur=\"javascript:checkDate('booking_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
					<%if(loanDataM.getAgreementDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getAgreementDate()));
					}%>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "AGREEMENT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","agreement_date")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","agreement_date","textbox","right","onblur=\"javascript:checkDate('agreement_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
				</tr>
				<tr>
					<%if(loanDataM.getExcutionDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getExcutionDate()));
					}%>
					<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "EXCUTION_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","excution_date")%></Font> :</td>
					<td class="inputCol" width="30%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","excution_date","textbox","right","onblur=\"javascript:checkDate('excution_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
					<%if(loanDataM.getLastDueDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getLastDueDate()));
					}%>
					<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "LAST_DUE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","last_due_date")%></Font> :</td>
					<td class="inputCol" width="30%">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(sDate,displayMode,"15","last_due_date","textboxReadOnly","style=\"width:30%;\" readOnly","") %></td>
				</tr>
				<tr>
					<%if(loanDataM.getDeliveryDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getDeliveryDate()));
					}%>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "DELIVERY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","delivery_date")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","delivery_date","textbox","right","onblur=\"javascript:checkDate('delivery_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
					<%if(loanDataM.getExpiryDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getExpiryDate()));
					}%>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","expiry_date")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","expiry_date","textbox","right","onblur=\"javascript:checkDate('expiry_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "PAYMENT_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","payment_type")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(paymentVect,loanDataM.getPaymentType(),"payment_type",displayMode,"onChange=\"javascript:parsePaymentToFirstPay();\" style=\"width:97%;\" ") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "BALLOON_INS_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","balloon_cost")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfBalloonAmt()),displayMode,"","balloon_cost","textbox","style=\"width:97%;\" ","") %></td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "FIRST_INS_PAY_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","first_ins_type")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(firstPayTypeVect,loanDataM.getFirstInsPayType(),"first_ins_type",displayMode,"style=\"width:97%;\" ") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "FIRST_INS_DEDUCT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","first_ins_deduct")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getFirstInsDeduct(),displayMode,"","first_ins_deduct","textbox","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:97%;\" ","") %></td>
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "PAY_DEALER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","pay_dealer")%></Font> :</td>
					<td class="inputCol" colspan="3">&nbsp;<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getPayDealer(),displayMode,"10","pay_dealer","55","pay_dealer_desc","textbox","","30","...","button_text","LoadDealerCode",payDealerDesc,OrigConstant.CacheName.CACHE_NAME_DEALER) %></td>
				</tr>
				<tr>
					<%if(loanDataM.getDocumentDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getSysNulldate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getDocumentDate()));
					}%>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "DOCUMENT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","document_date")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","document_date","textbox","right","onblur=\"javascript:checkDate('document_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
					<%if(loanDataM.getChequeDate()==null){
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getSysNulldate()));
					}else{
						sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(loanDataM.getChequeDate()));
					}%>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "CHEQUE_PAYMENT_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","cheque_payment_date")%></Font> :</td>
					<td class="inputCol">&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","cheque_payment_date","textbox","right","onblur=\"javascript:checkDate('cheque_payment_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr bgcolor="#F4F4F4"><td height="20"></td></tr>
	<tr>
	   <td>
	   <!-- begin by PoNg -->
	   <table width="100%" align="left">
	   <tr><td width="70%">
			<table width="100%" align="center">
				<tr>
					<td class="textColS"></td>
					<td class="inputCol" colspan="2"></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "COST") %>&nbsp;&nbsp;</td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "VAT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_LEASING_POPUP","vat")%></Font>&nbsp;&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(vatVect,loanDataM.getVAT(),"vat",displayMode,"onChange=\"javascript:setVat();\"") %>%</td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "TOTAL") %>&nbsp;&nbsp;</td>					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "EQUIPMENT_COST") %> :</td>
					<td class="inputCol" colspan="2">&nbsp;</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfCarPrice()),displayMode,"","car_price_cost",styleCost,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+readOnlyCost,"") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfCarPrice()),displayMode,"","car_price_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfCarPrice()),displayMode,"","car_price_total",styleTotal,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyTotal,"") %></td>
					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "DEPOSIT") %> :</td>
					<td class="textColS" colspan="2">&nbsp;</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfDownPayment()),displayMode,"","down_payment_cost",styleCost,readOnlyCost,"") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfDownPayment()),displayMode,"","down_payment_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfDownPayment()),displayMode,"","down_payment_total",styleTotal,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+ readOnlyTotal,"") %></td>
					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "FINANCE_AMT") %> :</td>
					<td class="textColS" colspan="2">&nbsp;</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfFinancialAmt()),displayMode,"","finance_cost","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfFinancialAmt()),displayMode,"","finance_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfFinancialAmt()),displayMode,"","finance_total","textboxCurrencyReadOnly","readOnly","") %></td>
					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "TERM") %> :</td>
					<%
					int installment1=0;
					if(loanDataM.getInstallment1()!=null){
						installment1 = loanDataM.getInstallment1().intValue();
					}
				%>
					<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction( ORIGDisplayFormatUtil.displayInteger(installment1),displayMode,"3","installment1","textboxCurrency"," onKeyPress=\"keyPressInteger();\" ","") %>
					<%=msgUtil.getTextDescription(request, "TERM") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfInstallment1()),displayMode,"","installment1_cost","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfInstallment1()),displayMode,"","installment1_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfInstallment1()),displayMode,"","installment1_total","textboxCurrencyReadOnly","readOnly","") %></td>
					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "RV") %> :</td>
					<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getRvPercent()),displayMode,"3","rv_percent","textboxCurrency","","") %> %</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfRV()),displayMode,"","rv_cost","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfRV()),displayMode,"","rv_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfRV()),displayMode,"","rv_total","textboxCurrencyReadOnly","readOnly","") %></td>
					
				</tr>
				<tr>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "LEASE_AMOUNT") %> :</td>
					<td class="textColS" colspan="2">&nbsp;</td>
					<% 
					String stepInstalmentScript=" ReadOnly";
				    String stepInstalmentStyle="textboxCurrencyReadOnly";
				    if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){ 				     
				    stepInstalmentStyle="textboxCurrency";
				    stepInstalmentScript="";
				    }				 
					%>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getCostOfHairPurchaseAmt()),displayMode,"","hire_purchase_cost",stepInstalmentStyle," onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+stepInstalmentScript,"") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getVATOfHairPurchaseAmt()),displayMode,"","hire_purchase_vat","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTotalOfHairPurchaseAmt()),displayMode,"","hire_purchase_total",stepInstalmentStyle," onblur=\"javascript:addComma(this);addDecimalFormat(this)\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" "+stepInstalmentScript,"") %></td>
					
				</tr>
				<tr>
					<td class="textColS" width="18%"><%=msgUtil.getTextDescription(request, "RATE") %> :</td>
					<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getRate1())),displayMode,"3","rate1","textboxCurrency","","") %> %</td>
					<td class="textColS" width="12%"><%=msgUtil.getTextDescription(request, "LOAN_RATE") %> :</td>
					<td class="textColS" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getEffectiveRate())),displayMode,"","effective_rate","textboxCurrencyReadOnly","readOnly","") %></td>
					<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "IRR_RATE") %> :</td>
					<td class="textColS" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getIRRRate())),displayMode,"","irr_rate","textboxCurrencyReadOnly","readOnly","") %></td>
				</tr>
				<tr>
					<td class="textColS" colspan="3"><%=msgUtil.getTextDescription(request, "PENALTY_RATE") %> :</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getPenaltyRate())),displayMode,"","penalty_rate","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></td>
					<td class="textColS"><%=msgUtil.getTextDescription(request, "DISC_RATE") %> :</td>
					<td class="textColS"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(String.valueOf(loanDataM.getDiscRate())),displayMode,"","disc_rate","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></td>
				</tr>
			</table>
		</td>			
		<td class="textColS" width="30%" valign="top">
			<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0 align="center">
	 		 <TR>
	    		<TD>
					<table>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					<tr><td></td></tr>
					</table>
					<fieldset><LEGEND><font class="font2"><strong><%=msgUtil.getTextDescription(request, "ORIGINAL_REQ") %></strong></font></Legend><br>
					<table cellpadding="" cellspacing="1" align=center  border=0 width="100%">	  
				  	  <tr>   
				        <td class="textColS" width="60%"><FONT class="font2"><B><%=msgUtil.getTextDescription(request, "DEPOSIT") %> :</td>
				        <td class="inputCol" width="40%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getOrigDownPayment()),displayMode,"","down_payment","textboxCurrencyReadOnly","readOnly","") %></td>
				      </tr>
				      <tr>
				        <td class="textColS"><%=msgUtil.getTextDescription(request, "FINANCIAL_AMT") %> :</td>
				        <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getOrigFinance()),displayMode,"","financial_amt","textboxCurrencyReadOnly","readOnly","") %></td>
				      </tr>
				      <% 
				      	int term = 0;
				      	if(loanDataM.getOrigInstallmentTerm() != null){
				      		term = loanDataM.getOrigInstallmentTerm().intValue();
				      	}
				      %>
				      <tr>
				        <td class="textColS"><%=msgUtil.getTextDescription(request, "TERM") %> :</td>
				        <td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayHTML(ORIGDisplayFormatUtil.formatNumber("###,###", term)),displayMode,"","install_1","textboxCurrencyReadOnly","readOnly","") %></td>
				      </tr>
				      <%if(OrigConstant.ROLE_UW.equals(userRole)){ %>
					      <tr>
					      	<td colspan="2" align="right"><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "ACTUAL_PRICE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT, "button", "actualBnt", "button_text", "onClick=\"javascript:loadActualPopup()\"", "") %></td>
					      </tr>  
					  <%} %>            
				     </table>	          
  					 </fieldset>
  					  <div id="idStepInstallment">
  					 <% if(OrigConstant.INSTALLMENT_TYPE_STEP.equals(loanDataM.getInstallmentFlag())){ %>
  					 <fieldset><LEGEND><font class="font2"><strong><%=msgUtil.getTextDescription(request, "STEP_INSTALLMENT") %></strong></font></Legend><br>
  					 <table cellpadding="0" cellspacing="1" align="center"  border=0 width="100%" bgcolor="white">
  					 <tr><TD width="20%" class="TableHeader" align="center"><b>Seq</b></TD><td width="40%" class="TableHeader"  align="center" ><b>Installment</b></td><td width="40%" class="TableHeader" align="center" ><b>Term</b></td></tr>
  					 <% 
  					 Vector vStepInstallment=loanDataM.getStepInstallmentVect();
  					 if(vStepInstallment==null){vStepInstallment=new Vector();}
  					 if(vStepInstallment.size()>0){
  					 for(int i=0;i<vStepInstallment.size();i++){
  					     InstallmentDataM prmInstallmentDataM=(InstallmentDataM)vStepInstallment.get(i);
  					  %>
  					   <tr><td  align="right" class="textColS"><%=i+1%>&nbsp;</td>
  					       <td  align="right" class="textColS"><%=ORIGDisplayFormatUtil.displayCommaNumber(prmInstallmentDataM.getInstallmentAmount()) %>&nbsp;</td>
  					       <td  align="right" class="textColS"><%=ORIGDisplayFormatUtil.displayInteger(prmInstallmentDataM.getTermDuration())%>&nbsp;</td>
  					  </tr>    					 
                     <%}%>	
                     <%}else{ %>				   				 
                       <tr><td colspan="3" align="center" class="textColS">No Record</td></tr>
                     <%}%>
  					 </table>
  					 </fieldset>
  					 <%}%>
  					 </div>
				</TD></TR>
			</TABLE>
		</td></tr>
		</table>
			<!-- end by Pong -->
		</td>
	</tr>
	<tr bgcolor="#F4F4F4"><td height="20"></td></tr>
	<tr>
		<td>	
			<table width="100%">
				<tr align="right">				    
					<td><%if(enableStepInstallment){%><input type="button" name="stepInstallmentBnt" value="<%=msgUtil.getTextDescription(request, "STEP_INSTALLMENT") %>" onClick="javascript:popupStepInstallment()" class="button_text" <%=stepInstallment%> ><%}%>
					<input type="button" name="calculateBnt" value="<%=msgUtil.getTextDescription(request, "CALCULATE") %>" onClick="javascript:mandateLoanForCal('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.LOAN_TYPE_LEASING %>')" class="button_text">
					<input type="button" name="saveBnt" value="<%=msgUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryLoanPopupField('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.LOAN_TYPE_LEASING %>')" class="button_text">
					<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(msgUtil.getTextDescription(request, "CLOSE"), displayMode,"button", "closeBnt", "button_text", "onClick=\"javascript:closePopup()\"", "") %></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<input type="hidden" name="car_type">
<input type="hidden" name="actual_car_price" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualCarPrice())%>">
<input type="hidden" name="actual_down" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualDown())%>">
<script language="JavaScript">
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
}
function closePopup(){
	window.close();
}
function setVat(){
	form = document.appFormName;
	if(form.vat.value == '<%=OrigConstant.NO_VAT%>'){
		form.car_price_cost.readOnly = false;
		var x=document.getElementById('car_price_cost');
		x.className = "textboxCurrency";
		form.car_price_total.readOnly = true;
		x=document.getElementById('car_price_total');
		x.className = "textboxCurrencyReadOnly";
		form.down_payment_cost.readOnly = false;
		x=document.getElementById('down_payment_cost');
		x.className = "textboxCurrency";
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
		form.down_payment_total.readOnly = false;
		x=document.getElementById('down_payment_total');
		x.className = "textboxCurrency";
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
function loadActualPopup(){
	var url = "/ORIGWeb/FrontController?action=LoadActualPopup";
	var childwindow = window.open(url,'','width=500,height=130,top=200,left=200,scrollbars=1,Status=1');
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
function parsePaymentToFirstPay(){
	var form = document.appFormName;
	var paymentType = form.payment_type.value;
	var insPay = form.first_ins_type;
	if(paymentType == '<%=OrigConstant.PAYMENT_BEGINING%>'){
		insPay.options[0] = new Option("Please select");
		<%	com.eaf.orig.cache.CacheDataInf properties;
			int size = firstPayTypeVect.size();
			String intPayType = loanDataM.getFirstInsPayType();
			for(int i=1; i<size+1; i++){
				properties = (com.eaf.orig.cache.CacheDataInf) firstPayTypeVect.get(i-1);
		%>
				insPay.options[<%=i%>] = new Option('<%=properties.getThDesc()%>','<%=properties.getCode()%>');
		<%
				if(properties.getCode().equals(intPayType)){
		%>		
					insPay.options[<%=i%>].selected = true;
		<%
				}
			}
		%>
		insPay.disabled = false;
	}else{
		var insPaySize = insPay.options.length;
		while (insPay.length > 0) { 
			insPay.remove(0); 
		}
		insPay.options[0] = new Option("Please select");
		insPay.disabled = true;
	}
}
if(document.appFormName.first_ins_type ){
	parsePaymentToFirstPay();
}
function calculateAppraisalPercent(){
	/* var form = document.appFormName;
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
	}*/
	
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
        obj_hire_purchase_cost=document.getElementById("hire_purchase_cost");
        obj_hire_purchase_cost.readOnly=true;
        obj_hire_purchase_cost.className = "textboxCurrencyReadOnly";     
        obj_hire_purchase_total=document.getElementById("hire_purchase_total");
        form.hire_purchase_total.readOnly=true;
        obj_hire_purchase_total.className = "textboxCurrencyReadOnly";
     }              
}
function popupStepInstallment(){ 
    form = document.appFormName;
    var vat=form.vat.value;
    var term=form.installment1.value;
    var hire_purchase_total=form.hire_purchase_total.value;
    var pram="&vat="+vat+"&term="+term;
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