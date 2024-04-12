<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.InsuranceDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.cache.properties.VATRateProperties" %>
<%@ page import="java.math.BigDecimal" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("INSURENCE_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();	
	Vector ownerInsurence = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",12);
	Vector paymentType = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",15);
	Vector accInsType = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",16);
    Vector paymenteByType = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",25);
    Vector taxRequireType = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",26);
	//System.out.println("ownerInsurenceVect = "+ownerInsurence.size());
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	String readOnlyForDrawDown = "";
	String styleForDrawDown = "textbox";
	String styleForDrawDown2 = "textboxCurrency";
	String disableForDrawDown = "";
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	if(((!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())) || ("Y").equals(applicationDataM.getDrawDownFlag())) && !OrigConstant.ROLE_PD.equals(userRole)){
			//displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
			readOnlyForDrawDown = ORIGDisplayFormatUtil.READ_ONLY;
			styleForDrawDown = "textboxReadOnly";
			styleForDrawDown2 = "textboxCurrencyReadOnly";
			disableForDrawDown = "disabled";
	}
	
	InsuranceDataM insuranceDataM = vehicleDataM.getInsuranceDataM();
	if(insuranceDataM == null){
		insuranceDataM = new InsuranceDataM();
	}
	//String ownerInsDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", insuranceDataM.getOwerInsurance());
	String insCompanyDesc = cacheUtil.getORIGMasterDisplayNameDataM("InsuranceCompany", insuranceDataM.getInsuranceCompany());
	String insTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("InsuranceType", insuranceDataM.getInsuranceType());
	String accInsCompanyDesc = cacheUtil.getORIGMasterDisplayNameDataM("InsuranceCompany", insuranceDataM.getAccInsuranceComany());
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, com.eaf.orig.shared.constant.OrigConstant.PERSONAL_TYPE_APPLICANT);
	//Date defaultDate = new Date(OrigConstant.DefaultValue.DEFAULT_DATE);
	String sDate;
	double vat = 0;
	Vector vatVect = utility.loadCacheByName("VATRate");
	for(int i=0; i<vatVect.size(); i++){
		VATRateProperties properties = (VATRateProperties) vatVect.get(i);
		if(new BigDecimal(0).compareTo(properties.getVATRATE()) != 0){
			vat = properties.getVATRATE().doubleValue();
		}
	}
	String rateStr = utility.getGeneralParamByCode(OrigConstant.RATE_FOR_NET_PREMIUM);
 
	double rate = 0;
	if(rateStr != null){
		rate = Double.parseDouble(rateStr);
	}
	double constantForNetPremium = ((vat/100)+1)*((rate/100)+1);
	System.out.println("constantForNetPremium = "+constantForNetPremium);
%>

<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "OWNER_INS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","owner_ins")%></Font> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(ownerInsurence,insuranceDataM.getOwerInsurance(),"owner_ins",displayMode,"style=\"width:95%;\" " +disableForDrawDown) %></td>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "GUARANTEE_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","guarantee_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getGuaranteeAmount()),displayMode,"","guarantee_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this);\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "INS_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","ins_type")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(insuranceDataM.getInsuranceType(),displayMode,"5","ins_type","34","ins_type_desc",styleForDrawDown,"" +readOnlyForDrawDown,"20","...","button_text","LoadInsuranceType",insTypeDesc,"InsuranceType") %></td>	
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "POLICY_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","policy_no")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(insuranceDataM.getPolicyNo(),displayMode,"","policy_no",styleForDrawDown,"style=\"width:60%;\" "+readOnlyForDrawDown,"20") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "INS_COMPANY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","ins_company")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(insuranceDataM.getInsuranceCompany(),displayMode,"5","ins_company","34","ins_company_desc",styleForDrawDown,readOnlyForDrawDown,"20","...","button_text","LoadInsuranceCompany",insCompanyDesc,"InsuranceCompany") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "COVERAGE_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","coverage_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getCoverageAmt()),displayMode,"","coverage_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this);\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GROSS_PREMIUM_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","gross_premium_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getGrossPremiumAmt()),displayMode,"","gross_premium_amount",styleForDrawDown2,"onblur=\"javascript:calculateInsurance();addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:95%;\" "+readOnlyForDrawDown,"16") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PREMIUM_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","premium_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getNetPremiumAmt()),displayMode,"","premium_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CONFIRM_PREMIUM_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","confirm_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getConfirmAmt()),displayMode,"","confirm_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:95%;\" "+readOnlyForDrawDown,"16") %></td>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PAYMENT_TYPE_INS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(paymentType,insuranceDataM.getPaymentType(),"payment_type_ins",displayMode,"style=\"width:60%;\" "+disableForDrawDown) %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PAYMENT_INS_BY") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(paymenteByType,insuranceDataM.getPaymentType(),"payment_inst_by",displayMode,"style=\"width:60%;\" "+disableForDrawDown) %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VAT_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getVatAmount()),displayMode,"","vat_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DUTY_AMT") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getDutyAmount()),displayMode,"","duty_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "WH_TAX_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getWithHoldingTaxAmount()),displayMode,"","wh_tax_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>

		</td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "WH_TAX_REQ") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(taxRequireType,insuranceDataM.getWithHoldingTaxRequire(),"wh_tax_req",displayMode,"style=\"width:60%;\" "+disableForDrawDown) %></td>
	</tr>
	<tr>
		<%if(insuranceDataM.getEffectiveDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(insuranceDataM.getEffectiveDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EFFECTIVE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","ins_effective_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","ins_effective_date",styleForDrawDown,"right","onblur=\"javascript:checkDate('ins_effective_date');setEffectiveDate(this.value, 'ins_expiry_date', 1);\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
		<%if(insuranceDataM.getExpiryDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(insuranceDataM.getExpiryDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","ins_expiry_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","ins_expiry_date",styleForDrawDown,"left","onblur=\"javascript:checkDate('ins_expiry_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_INS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_insurence")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(accInsType,insuranceDataM.getAccInsurance(),"acc_insurence",displayMode,"onChange=\"javascript:getInsuranceData()\" style=\"width:95%;\" "+disableForDrawDown) %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_POLICY_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_policy_no")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(insuranceDataM.getAccPolicyNo(),displayMode,"","acc_policy_no",styleForDrawDown,"style=\"width:60%;\" "+readOnlyForDrawDown,"20") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_INS_COMPANY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_ins_company")%></Font> :</td>
		<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(insuranceDataM.getAccInsuranceComany(),displayMode,"5","acc_ins_company","34","acc_ins_company_desc",styleForDrawDown,readOnlyForDrawDown,"50","...","button_text","LoadInsuranceCompany",accInsCompanyDesc,"InsuranceCompany") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_GROSS_PREMIUM_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_gross_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccGrossPremiumAmt()),displayMode,"","acc_gross_amount",styleForDrawDown2,"onblur=\"javascript:calculateInsuranceAcc();addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:95%;\" "+readOnlyForDrawDown,"16") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_PREMIUM_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_promiun_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccNetPremiunAmt()),displayMode,"","acc_promiun_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_CONFIRM_PREMIUN_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_confirm_amount")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccConfirmAmt()),displayMode,"","acc_confirm_amount",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:95%;\" "+readOnlyForDrawDown,"16") %></td>
		<td class="textColS" colspan="2"></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_VAT_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountVatAmount()),displayMode,"","acc_vat_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_DUTY_AMT") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountDutyAmount()),displayMode,"","acc_duty_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
    </td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_WH_TAX_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountWithHoldingTaxAmount()),displayMode,"","acc_wh_tax_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
         </td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_WH_TAX_REQ") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction(taxRequireType,insuranceDataM.getAccountWithHoldingTaxRequire(),"acc_wh_tax_req",displayMode,"style=\"width:60%;\" "+disableForDrawDown) %></td>
	</tr>
	<tr>
		<%if(insuranceDataM.getAccEffectiveDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(insuranceDataM.getAccEffectiveDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_EFFECTIVE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_effective_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","acc_effective_date",styleForDrawDown,"right","onblur=\"javascript:checkDate('acc_effective_date');setEffectiveDate(this.value, 'acc_expiry_date', 1);\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
		<%if(insuranceDataM.getAccExpiryDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(insuranceDataM.getAccExpiryDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_EXPIRY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","acc_expiry_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","acc_expiry_date",styleForDrawDown,"left","onblur=\"javascript:checkDate('acc_expiry_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
	</tr>
	<tr>
		<%if(insuranceDataM.getNotificationDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(insuranceDataM.getNotificationDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NOTIFICATION_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","notification_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","notification_date",styleForDrawDown,"right","onblur=\"javascript:checkDate('notification_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NOTIFIACTION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","notification_no")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(insuranceDataM.getNotificationNo(),displayMode,"","notification_no",styleForDrawDown,readOnlyForDrawDown,"20") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DISC_CUST_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getDistcountToCustAmount()),displayMode,"","disc_cust_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REBATE_DEALER_AMT") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getRebateToDealerAmount()),displayMode,"","rebate_dealer_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "DEDUCTED_BY_SALES") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getDeductBySales()),displayMode,"","deducted_by_sales",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
        </td>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_DISC_CUST_AMT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountDistcountToCustAmount()),displayMode,"","acc_disc_cust_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %> 
		</td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_REBATE_DEALER_AMT") %> : </td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountRebateToDealerAmount()),displayMode,"","acc_rebate_dealer_amt",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACC_DEDUCTED_BY_SALES") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"INSURENCE_SUBFORM","payment_type_ins")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(insuranceDataM.getAccountDeductBySales()),displayMode,"","acc_deducted_by_sales",styleForDrawDown2,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:60%;\" "+readOnlyForDrawDown,"16") %>
		</td>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
	</tr>
</table>
<script language="JavaScript">
function calculateInsurance( ){
    var grossField='gross_premium_amount';
    var netField='premium_amount';
    var vatAmtFiled='vat_amt';
    var dutyAmtFiled='duty_amt';
    var whTaxAmtFiled='wh_tax_amt';

	var grossObj = eval("document.appFormName."+grossField);
	var netObj = eval("document.appFormName."+netField);
	var vatAmtObj=eval("document.appFormName."+vatAmtFiled);
	var dutyAmtObj=eval("document.appFormName."+dutyAmtFiled);
	var whTaxAmtObj=eval("document.appFormName."+whTaxAmtFiled);

	var constant = '<%=constantForNetPremium%>';
	var vatRate=new Number('<%=vat/100+1%>');
	var netPremiumRate=new Number('<%=rateStr%>');
	var grossPremiumAmt=new Number(grossObj.value);
	var netPremium = (grossObj.value)/(parseFloat(constant));
	netObj.value = (parseFloat(netPremium)).toFixed(2);
	var vatAmt=(grossPremiumAmt)-(grossPremiumAmt/vatRate);
	vatAmtObj.value=Math.round(vatAmt*100)/100;
	var dutyAmt=( grossPremiumAmt-vatAmt)*netPremiumRate/100;
	if(dutyAmt%1 >0){
	  dutyAmt=Math.round(dutyAmt)+1	;
	}else{
	  dutyAmt=Math.round(dutyAmt);	
	}
    dutyAmtObj.value=dutyAmt; 
    whTaxAmtObj.value=Math.round( ((netPremium+dutyAmt)*1/100)*100)/100;          
	addComma(netObj);
	addComma(vatAmtObj);
	addComma(dutyAmtObj);
	addComma(whTaxAmtObj);

	
} 
function calculateInsuranceAcc( ){
    var accGrossField='acc_gross_amount';
    var accNetField='acc_promiun_amount';
    var accVatAmtFiled='acc_vat_amt';
    var accDutyAmtFiled='acc_duty_amt';
    var accWhTaxAmtFiled='acc_wh_tax_amt';
    var accGrossObj = eval("document.appFormName."+accGrossField);
	var accNetObj = eval("document.appFormName."+accNetField);    
   	var accVatAmtObj=eval("document.appFormName."+accVatAmtFiled);
	var accDutyAmtObj=eval("document.appFormName."+accDutyAmtFiled);
	var accWhTaxAmtObj=eval("document.appFormName."+accWhTaxAmtFiled);	
	alert('obj');
	var constant = '<%=constantForNetPremium%>';
	var vatRate=new Number('<%=vat/100+1%>');
	var netPremiumRate=new Number('<%=rateStr%>');
	var grossPremiumAmt=new Number(accGrossObj.value);
	var netPremium = (accGrossObj.value)/(parseFloat(constant));
	accNetObj.value = (parseFloat(netPremium)).toFixed(2);
	var vatAmt=(grossPremiumAmt)-(grossPremiumAmt/vatRate);
	accVatAmtObj.value=Math.round(vatAmt*100)/100;
	var dutyAmt=( grossPremiumAmt-vatAmt)*netPremiumRate/100;
	if(dutyAmt%1 >0){
	  dutyAmt=Math.round(dutyAmt)+1	;
	}else{
	  dutyAmt=Math.round(dutyAmt);	
	}
    accDutyAmtObj.value=dutyAmt; 
    accWhTaxAmtObj.value=Math.round( ((netPremium+dutyAmt)*1/100)*100)/100;	
    alert('a set value');
	addComma(accNetObj);
	addComma(accVatAmtObj);
	addComma(accDutyAmtObj);
	addComma(accWhTaxAmtObj);
}
</script>