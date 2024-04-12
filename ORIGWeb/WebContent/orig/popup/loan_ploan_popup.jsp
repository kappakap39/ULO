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
	String displayMode = formUtil.getDisplayMode("PURCHASE_LIST_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector bankVect = utility.loadCacheByName("BankInformation");
	Vector purposeOfLoanVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",27);
	
	LoanDataM loanDataM = (LoanDataM) request.getSession().getAttribute("POPUP_DATA");
	if(loanDataM == null){
		loanDataM = new LoanDataM();
	}
	
	String formId = ORIGForm.getFormID();
	String readOnlyCost = "readOnly";
	String styleCost = "textboxCurrencyReadOnly";
	String readOnlyTotal = "readOnly";
	String styleTotal = "textboxCurrencyReadOnly";
	String readOnlyBalloon = "readOnly";
	String styleBalloon = "textboxCurrencyReadOnly";
	String readOnlyInstallment = "";
	String styleInstallment= "textboxCurrency";
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
	String schemeCodeDese = cacheUtil.getORIGMasterDisplayNameDataM("IntScheme", loanDataM.getSchemeCode());
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
	//TODO
	String segment = personalInfoDataM.getCustomerSegment();
	
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
	<tr><td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "LOAN") %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                              			<%if(OrigConstant.ROLE_XCMR.equals(userRole)){ %>
											<table width="100%">
												<tr align="right"><%if(enableStepInstallment){ %>
													<td><input type="button" name="stepInstallmentBnt" value="<%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %>" onClick="javascript:popupStepInstallment()" class="button" <%=stepInstallment %>></td><%}%>
													<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "SAVE"), displayForXCMR,"button", "saveBnt", "button", "onClick=\"javascript:saveDataForXcmr()\"", "") %></td>
													<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), displayForXCMR,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %></td>
												</tr>
											</table>
											<%}else{ %>
											<table width="100%">
												<tr align="right">
													<%if(enableStepInstallment){ %>
													<td><input type="button" name="stepInstallmentBnt" value="<%=MessageResourceUtil.getTextDescription(request, "STEP_INSTALLMENT") %>" onClick="javascript:popupStepInstallment()" class="button" <%=stepInstallment%> ></td>
													<%}%>
													<td><input type="button" name="calculateBnt" value="<%=MessageResourceUtil.getTextDescription(request, "CALCULATE") %>" onClick="javascript:mandatePLoanForCal('<%=personalInfoDataM.getCustomerType()%>','')" class="button" <%=disabledBtn %>></td>
													<td> <input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryPLoanPopupField('<%=personalInfoDataM.getCustomerType()%>','')" class="button" <%=disabledBtn %>></td>
													<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %></td>
												</tr>
											</table>
										<%} %>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
						<tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	
							<tr>
								<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "LOAN_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","loan_type")%></Font> :</td>
								<td class="inputCol" width="35%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getLoanType(),displayMode,"10","loan_type","40","loan_type_desc","textboxReadOnly","readOnly","30","...","button_text","LoadLoanType",loanTypeDesc,"LoanType") %></td>
								<td class="textColS" width="15%" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MKT_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","mkt_code")%></Font> :</td>
								<td class="inputCol" width="35%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getMarketingCode(),displayMode,"10","mkt_code","40","mkt_code_desc","textbox","","30","...","button_text","LoadMarkettingCode",mktDesc,"LoanOfficer") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CAMPAIGN") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","campaign")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptJSBusClassAction(loanDataM.getCampaign(),displayForXCMR,"10","campaign","40","campaign_desc","textbox","","30","...","button_text","LoadCampaign",campaignDesc,"Campaign","clearSchemeByCampaign()", "PL_ALL_ALL") %></td>
								<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "INTERNAL_CHECKER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","internal")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getInternalCkecker(),displayMode,"10","internal","40","internal_desc","textbox","","30","...","button_text","LoadInternalChecker",inCheckDesc,"InternalChecker") %></td>
							</tr>
							<tr>
								<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "SCHEME_COME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","scheme_code")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getSchemeCode(),displayMode,"10","scheme_code","40","scheme_code_desc","textbox","","30","...","button_text","LoadSchemeCode",schemeCodeDese,"IntScheme") %>			
								<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "CREDIT_APPROVAL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","credir_approval")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getCreditApproval(),displayMode,"10","credir_approval","40","credir_approval_desc","textboxReadOnly","readOnly","","...","button_text","LoadCreditApproval",creditDesc,"CREDITAPRV") %></td>
							</tr>
							<tr>
								<td class="textColS">&nbsp;</td>
								<td class="inputCol">
									<table cellpadding="0" cellspacing="0" width="100%" align="center">	
									<tr>
										<td class="inputCol">
											<input type="checkbox" name="MRTA" value="Y" <%=disabledBtn %>>
										</td><td class="textColS"> MRTA </td>
										<td class="inputCol"><input type="button" style="width:100;" name="MRTABnt" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_SCHEME") %>" onclick='retrieveSchema()' class="button_text" <%=disabledBtn %>>		
										</td>
										<td class="inputCol"><input type="button" style="width:150;" name="SchemeBnt" value="<%=MessageResourceUtil.getTextDescription(request, "RETRIEVE_SCHEME") %> From DB" onclick='retrieveSchemaFromDB()' class="button_text" <%=disabledBtn %>>		
										</td>
									</tr>
									</table>						
								</td>
								<td class="textColS" colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BANK_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","bank")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(bankVect,loanDataM.getBankCode(),"bank",displayMode,"onChange=\"javascript:setBranch(this.value,'branch','branch_desc')\"") %></td>
								<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "BRANCH_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","branch")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(loanDataM.getBranchCode(),displayMode,"10","branch","40","branch_desc","textbox",disableBranch,"30","...","button_text","LoadBranch",new String[]{"bank"},disableBranch,ORIGDisplayFormatUtil.displayHTML(branckDesc),"Branch") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AC_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","account_no")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getAccountNo(),displayMode,"18","account_no","textbox","","30") %></td>
								<td class="textColS" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "AC_NAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","account_name")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(loanDataM.getAccountName(),displayMode,"19","account_name","textbox","","30") %></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	
				   			<tr>
								<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "PURPOSE_OF_LOAN") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","purpose_of_loan")%></Font> :</td>
								<td class="inputCol" ><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(purposeOfLoanVect,loanDataM.getPurposeLoan(),"purpose_of_loan",displayMode,"") %></td>
								
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LOAN_AMOUNT_APPLIED") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","loan_amount_applied")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLoanAmt()),displayMode,"15","loan_amount_applied","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></td>
								
							</tr>	
							<tr>
								<td class="textColS" colspan=2 align=center><b>1 Tier</b></td>
								<td class="textColS" colspan=2 align=center><b>2 Tier</b></td>
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","monthly_instalment_one")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtOne()),displayMode,"15","monthly_instalment_one","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","monthly_instalment_two")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtTwo()),displayMode,"15","monthly_instalment_two","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LAST_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","last_instalment_one")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLastIntalmentAmtOne()),displayMode,"15","last_instalment_one","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LAST_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","last_instalment_two")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLastIntalmentAmtTwo()),displayMode,"15","last_instalment_two","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FIRST_INTEREST_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","first_interest_rate")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierRate()),displayMode,"15","first_interest_rate","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SECOND_INTEREST_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","second_interest_rate")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierRate()),displayMode,"15","second_interest_rate","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FIRST_TIER_TERM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","first_tier_term")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getFirstTierTerm()),displayMode,"15","first_tier_term","textboxCurrencyReadOnly","readOnly","3") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SECOND_TIER_TERM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","second_tier_term")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getSecondTierTerm()),displayMode,"15","second_tier_term","textboxCurrencyReadOnly","readOnly","3") %></td>
							</tr>
							<tr>								
								<td class="textColS" colspan=2 align=center><b>3 Tier</b></td>
								<td class="textColS" colspan=2 align=center><b>4 Tier</b></td>
							</tr>
							<tr>
								<!-- Wiroon -->
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","monthly_instalment_three")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtThree()),displayMode,"15","monthly_instalment_three","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MONTHLY_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","monthly_instalment_four")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getMonthlyIntalmentAmtFour()),displayMode,"15","monthly_instalment_four","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<!-- Wiroon -->
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LAST_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","last_instalment_three")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLastIntalmentAmtThree()),displayMode,"15","last_instalment_three","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LAST_INSTALMENT_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","last_instalment_four")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getLastIntalmentAmtFour()),displayMode,"15","last_instalment_four","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<!-- Wiroon -->
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "THIRD_INTEREST_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","third_interest_rate")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierRate()),displayMode,"15","third_interest_rate","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FORTH_INTEREST_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","forth_interest_rate")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierRate()),displayMode,"15","forth_interest_rate","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
				   			<tr>
								<!-- Wiroon -->
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "THIRD_TIER_TERM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","third_tier_term")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getThirdTierTerm()),displayMode,"15","third_tier_term","textboxCurrencyReadOnly","readOnly","3") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FORTH_TIER_TERM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","forth_tier_term")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getForthTierTerm()),displayMode,"15","forth_tier_term","textboxCurrencyReadOnly","readOnly","3") %></td>
							</tr>
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "IRR_RATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","irr_rate")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getIRRRate()),displayMode,"15","irr_rate","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	
				   			<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SERVICE_FEE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","service_fee")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getServiceFee()),displayMode,"15","service_fee","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REGISTRATION_FEE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","registration_fee")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getRegistrationFee()),displayMode,"15","registration_fee","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TRANSFER_FEE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","transfer_fee")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getTransferFee()),displayMode,"15","transfer_fee","textboxCurrencyReadOnly","readOnly","") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PREPAYMENT_FEE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"LOAN_PLOAN_POPUP","prepayment_fee")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(loanDataM.getPrepaymentFee()),displayMode,"15","prepayment_fee","textboxCurrencyReadOnly","readOnly","") %></td>
							</tr>	
							</table>
						</td></tr>
						</table>
					</td></tr>
					</table>
				</td></tr>
			</table>
		</td> 
	</tr>
</table>

<input type="hidden" name="car_type">
<input type="hidden" name="actual_car_price" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualCarPrice())%>">
<input type="hidden" name="actual_down" value="<%=ORIGDisplayFormatUtil.displayBigDecimalZeroToEmpty(loanDataM.getActualDown())%>">
<script language="JavaScript">

var termOne = document.getElementsByName("first_tier_term")[0].value;
document.getElementsByName("first_tier_term")[0].value = termOne.split(".")[0];

var termTwo = document.getElementsByName("second_tier_term")[0].value;
document.getElementsByName("second_tier_term")[0].value = termTwo.split(".")[0];

var termThree = document.getElementsByName("third_tier_term")[0].value;
document.getElementsByName("third_tier_term")[0].value = termThree.split(".")[0];

var termFour = document.getElementsByName("forth_tier_term")[0].value;
document.getElementsByName("forth_tier_term")[0].value = termFour.split(".")[0];

function saveData(){
	form = document.appFormName;
	form.action.value = "SavePLoanLoan";
	form.handleForm.value = "N";
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
	form.action.value = "SavePLoanLoan";
	form.handleForm.value = "N";
	form.submit();
}
function closePopup(){
	window.close();
}
function loadActualPopup(){
	var url = "/ORIGWeb/FrontController?action=LoadActualPopup";
	var childwindow = window.open(url,'','width=500,height=130,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing');};
}

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
	childwindow.onunload = function(){alert('closing');};
}

function retrieveSchemaFromDB() {
	if(document.getElementById('scheme_code').value==""){
		alert('Please Enter scheme_code');
		return false;		
	} else {
		var scheme_code = document.getElementById('scheme_code').value;
		retrieveScheme(scheme_code);
	}
}



function mandatePLoanForMRTA(){
	try {
		if(document.getElementsByName('campaign')[0].value==""){
			alert('Please Enter Campaign');
			return false;
		} else if("<%=segment%>"=="null"){
			alert('Please Enter Customer Segment');
			return false;		
		} else {
			return true;
		}
	} catch(er) {
		alert("Error mandateLoanForMRTA : "+er);
	}
}

function callMRTA(){
		var req = new DataRequestor();
		//var url = "http://larukunb:8080/pau/PricingService.jsp";	
		var url = "http://vmwas61:8080/pau/PricingService.jsp";	
			
		
		var MRTA ;
		if(document.getElementsByName('MRTA')[0].checked){
			MRTA = "Y";
		} else {
			MRTA = "N";		
		}
		
		req.addArg(_POST, "MRTA", MRTA );
		req.addArg(_POST, "accredited", "Y");
		req.addArg(_POST, "LTV", "90");
		req.addArg(_POST, "loanType", document.getElementsByName('loan_type')[0].value);
		req.addArg(_POST, "campaign", document.getElementsByName('campaign')[0].value);
		req.addArg(_POST, "segment", "<%=segment%>");	
		//req.addArg(_POST, "segment", "S1");	
		
		
		req.getURLForThai(url, _RETURN_AS_TEXT);
		
		req.onload = function (data, obj) {		
			var rate = "";
			var term = "";
			var elResults = data.split('|');			
			
			if(elResults!=null && elResults.length > 0){
				var schema = elResults[0];
				document.getElementsByName('scheme_code')[0].value=schema;
				for(var i=1; i<elResults.length; i++){
					var elResultData = elResults[i].split(',');
					rate = elResultData[0];
					term = elResultData[1];
			
					if(i == 1){				
						document.getElementsByName('first_interest_rate')[0].value=rate;
						document.getElementsByName('first_tier_term')[0].value=term;				
					} else if(i == 2){
						document.getElementsByName('second_interest_rate')[0].value=rate;
						document.getElementsByName('second_tier_term')[0].value=term;					
					} else if(i == 3){
						document.getElementsByName('third_interest_rate')[0].value=rate;
						document.getElementsByName('third_tier_term')[0].value=term;					
					} else if(i == 4){				
						document.getElementsByName('forth_interest_rate')[0].value=rate;
						document.getElementsByName('forth_tier_term')[0].value=term;					
					}
				}
			}		
		};
}


function retrieveSchema(){
	if(mandatePLoanForMRTA()){
		callMRTA();
	}
}




</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>