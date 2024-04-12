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
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.model.PreScoreDataM" %>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<%
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	String searchType = (String) request.getSession().getAttribute("searchType");
	
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	//String displayMode = formUtil.getDisplayMode("PRE_SCORE_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
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
	 
	String preScoreFileName="";
	if(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_individual.jsp";
	}else if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_corporate.jsp";
	}else if(OrigConstant.CUSTOMER_TYPE_FOREIGNER.equalsIgnoreCase(applicationDataM.getMainCustomerType())){
	preScoreFileName="pre_score_foreigner.jsp";
	} 
	String includedFileName=preScoreFileName;
	  	if(! (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase( applicationDataM.getJobState()) )){
      	displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
   	}
   	 PreScoreDataM prmPreScoreDataM=personalInfoDataM.getPreScoreDataM();
   	 if(prmPreScoreDataM==null){
   	   prmPreScoreDataM =new PreScoreDataM();
   	 }
   	 
%>

<table cellpadding="" cellspacing="1" width="100%">
	<tr><td colspan="2" class="inputCol">&nbsp;
	<%
 
 
 
	TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
 
		 
// 	if(applicationDataM == null){
// 		applicationDataM = new ApplicationDataM();
// 	}	   
	Vector occupationVect = utility.loadCacheByName("Occupation");
	Vector positionVect = new Vector();
	Vector qualificationVect = utility.getMasterDataFormCache("EDULEVCDE");//cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",2); 
   	Vector regisStatusVect =  cacheUtil.getCurrentAddresType(); 	 
	Vector loanVect=applicationDataM.getLoanVect() ;	 	 
   	PreScoreDataM preScoreDataM=personalInfoDataM.getPreScoreDataM();
   	if(preScoreDataM==null){
   	 preScoreDataM=new PreScoreDataM();
   	}
	String mktDesc = cacheUtil.getORIGMasterDisplayNameDataM("LoanOfficer",preScoreDataM.getMarketingCode());    
   	//if(! (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase( applicationDataM.getJobState()) )){
   ///   	displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
   	//}
   	if(!ORIGUtility.isEmptyString(preScoreDataM.getOccupation())){
		positionVect = cacheUtil.getPositionByOccupation(preScoreDataM.getOccupation());		 
	}
	String cmrDisplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
   	if((ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase( applicationDataM.getJobState()) )){
      	cmrDisplayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
   	}   	    
%>
<script language="javascript">
function calFinanceAmt(){
   var form=document.appFormName;
   var strCarPrice =removeCommas(form.pre_score_car_price.value);
   var strDownPayment=removeCommas(form.pre_score_down_payment.value);
   var numberCarPrice=Number(strCarPrice);
   var numberDownPayment=Number(strDownPayment);
   if( numberCarPrice>= numberDownPayment){    
      form.pre_score_financeAmtVAT.value=numberCarPrice - numberDownPayment;
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
		<td class="textColS" width="22%">&nbsp;<%=msgUtil.getTextDescription(request, "OCCUPATION") %> :</td>
		<td class="inputCol" width="30%">&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(occupationVect,preScoreDataM.getOccupation(),"pre_score_occupation",cmrDisplayMode,"onChange=\"javascript:parseOccupationToPosition(this.value,'preScorePositionId','pre_score_position'); \"") %></td>
		<td class="textColS" width="20%">&nbsp;</td>
		<td class="inputCol" width="23%">&nbsp;</td>
	</tr>
	<tr>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "POSITION") %> :</td>
		<td class="inputCol" id="preScorePositionId" >&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,preScoreDataM.getPosition(),"pre_score_position",cmrDisplayMode,"") %></td>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "BUS_TYPE") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessTypeVect,preScoreDataM.getBusinessType(),"pre_score_bus_type",cmrDisplayMode,"") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "SALARY") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getSalary()),cmrDisplayMode,"","pre_score_salary","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "OTHER_INCOME") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(preScoreDataM.getOtherIncome()),cmrDisplayMode,"","pre_score_other_income","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "QUALIFICATION") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(qualificationVect,preScoreDataM.getQualification(),"pre_score_qualification",cmrDisplayMode,"") %></td>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "TOTAL_WORKING") %><Font color="#ff0000"><%//=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","year")%></Font> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(preScoreDataM.getTotalWorkYear()),cmrDisplayMode,"5","pre_score_year","textbox","onBlur=\"javascript:checkCMRworingDate('birth_date','pre_score_year','pre_score_month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\"","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "YEAR")%>                            	 
		       					   <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(preScoreDataM.getTotalWorkMonth()),cmrDisplayMode,"5","pre_score_month","textbox","onBlur=\"javascript:checkMonth('pre_score_month');checkCMRworingDate('birth_date','pre_score_year','pre_score_month',this);returnZero(this);\" ","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "MONTH") %></td>
	</tr>
	<tr>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "PRE_SCORE_ADDRESS_STATUS") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(regisStatusVect,preScoreDataM.getHouseRegistStatus(),"pre_score_house_regis_status",cmrDisplayMode,"") %></td>
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "TIME_IN_CURRENT_ADDRESS") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(preScoreDataM.getTimeInCurrentAddressYear()),cmrDisplayMode,"5","pre_score_time_current_address_year","textbox","onKeyPress=\"keyPressInteger(this.value);\" onblur=\"javascript:addCommas('pre_score_time_current_address_year');checkCMRAddressReside('birth_date','pre_score_time_current_address_year','pre_score_time_current_address_month',this);returnZero(this);\"","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "YEAR") %>
		       					   <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(preScoreDataM.getTimeInCurrentAddressMonth()),cmrDisplayMode,"5","pre_score_time_current_address_month","textbox"," onKeyPress=\"keyPressInteger(this.value);\" onBlur=\"javascript:checkMonth('pre_score_time_current_address_month');checkCMRAddressReside('birth_date','pre_score_time_current_address_year','pre_score_time_current_address_month',this);returnZero(this);\" ","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "MONTH") %></td>
	</tr> 
	<tr>     	
		<td class="textColS" ><%=msgUtil.getTextDescription(request, "HOUSE_ID") %> :</td>
		<td class="inputCol" >&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction( preScoreDataM.getHouseIdno(),cmrDisplayMode,"15","pre_score_houseid","textbox ","","15") %></td>
		<td class="textColS" ></td>
		<td class="inputCol" >&nbsp;</td>
	</tr>	
</table>
	</td>		 
</table>
 
