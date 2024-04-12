<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.cache.properties.TitleProperties"%>
<%@ page import="com.eaf.orig.cache.properties.TitleEngProperties"%>





<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	String menu = (String)request.getSession(true).getAttribute("PROPOSAL_MENU");
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";	

//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	Vector raceVect = utility.loadCacheByName("Race");
	Vector genderVect = utility.getMasterDataFormCache("GENDERCDE");
	Vector maritalStatusVect = utility.loadCacheByName("MaritalStatus");
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	Vector nationalityVect = utility.loadCacheByName("Nationality");
	Vector sourceOfCustomerVect = utility.getMasterDataFormCache("SRCOFCST");
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	Vector cardTypeVect = utility.loadCacheByName("CustomerIdentify");
	Vector<TitleEngProperties> engTitles = utility.loadCacheByNameAndCheckStatus(OrigConstant.CacheName.CACHE_NAME_TITLE_ENG, "Dummy");
	Vector<TitleProperties> thaiTitles = utility.loadCacheByNameAndCheckStatus(OrigConstant.CacheName.CACHE_NAME_TITLE_THAI, "Dummy");
	
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
	//Default client group
	String personalClientGroup = personalInfoDataM.getClientGroup();
	System.out.println("menu = "+menu);
	if(ORIGUtility.isEmptyString(personalClientGroup) && !("Y").equals(applicationDataM.getDrawDownFlag()) && !("Y").equals(menu)){
		personalClientGroup = OrigConstant.DefaultValue.DEFAULT_CLIENT_GROUP;
		personalInfoDataM.setClientGroup(personalClientGroup);
	}
	
	String titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getEngTitleName());
	String titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
	String clientGroup = cacheUtil.getORIGMasterDisplayNameDataM("ClientGroup", personalInfoDataM.getClientGroup());
	String areaCode = cacheUtil.getORIGMasterDisplayNameDataM("Area", applicationDataM.getAreaCode());
	String sDate = null;
	
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	Vector userRoles = userM.getRoles();
	String role = (String)userRoles.get(0);
	//Default nationality
	String nationality = personalInfoDataM.getNationality();
	if(ORIGUtility.isEmptyString(nationality)){
		nationality = OrigConstant.DefaultValue.DEFAULT_NATIONALITY;
	}
	//Default race
	String race = personalInfoDataM.getRace();
	if(ORIGUtility.isEmptyString(race)){
		race = OrigConstant.DefaultValue.DEFAULT_RACE;
	}
	//Default marital status
	String maritalStatus = personalInfoDataM.getMaritalStatus();
	if(ORIGUtility.isEmptyString(maritalStatus)){
		if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())){
			maritalStatus = OrigConstant.DefaultValue.DEFAULT_MARITAL_CORPORATE;
		}else{
			maritalStatus = OrigConstant.DefaultValue.DEFAULT_MARITAL_INDIVIDUAL;
		}
	}
	String currentDateStr = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(new Date()));
	String cardType = personalInfoDataM.getCardIdentity();
	if(ORIGUtility.isEmptyString(cardType) && OrigConstant.CUSTOMER_TYPE_INDIVIDUAL.equals(personalInfoDataM.getCustomerType())){
		cardType = OrigConstant.CARD_TYPE_IDENTIFICATION;
	}else if(ORIGUtility.isEmptyString(cardType) && OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())){
		cardType = OrigConstant.CARD_TYPE_CERTIFY;
	}
	
	String disabledForUW = "";
	if(personalInfoDataM.getKConsentDate() != null && (OrigConstant.ROLE_UW.equals(ORIGUser.getRoles().elementAt(0)) || (OrigConstant.ROLE_DE.equals(ORIGUser.getRoles().elementAt(0)) && !ORIGUtility.isEmptyString(applicationDataM.getCmrFirstId())))){
		disabledForUW = "disabled";
	}
	System.out.println("disabledForUW = "+disabledForUW);
	System.out.println("CoBorrower Flag = "+personalInfoDataM.getCoborrowerFlag());
%>
<table cellpadding="" cellspacing="1" width="100%">
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NAME_THAI") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" colspan="3">
			<table  cellpadding="0" cellspacing="0" width="100%" align="left">
			<tr><td width='170'>
					<%=ORIGDisplayFormatUtil.displayPopUpOneTextBoxTagScriptAction(titleThai,displayMode,"7","title_thai_desc","textbox","","","...","button_text","LoadTitleThai","title_thai","Title","title_eng_desc","title_eng") %>
				</td><td width='140'>
					<input type="hidden" name="title_thai" value="<%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiTitleName()) %>">		
			<% if(OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())){%>				
					<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiFirstName(),displayMode,"","name_thai","textbox","","60") %>
				</td><td width='140'><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiLastName(),displayMode,"","surname_thai","textbox","","50") %>				
			<%}else{ %>
					<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiFirstName(),displayMode,"","name_thai","textbox","onblur=\"javascript:checkThaiFName('name_thai')\"","60") %>
				</td><td width='140'><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiLastName(),displayMode,"","surname_thai","textbox","onblur=\"javascript:checkThaiLName('surname_thai')\"","50") %>
			<%} %>				
				</td>
			<td>&nbsp;</td></tr>
			</table>
		<td width="17%"></td>
		<td width="17%"></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NAME_ENG") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" colspan="3">
			<table  cellpadding="0" cellspacing="0" width="100%" align="left">
			<tr><td width='170'>
					<%=ORIGDisplayFormatUtil.displayPopUpOneTextBoxTagScriptAction(titleEng,displayMode,"7","title_eng_desc","textbox","","","...","button_text","LoadTitleEng","title_eng","TitleEng","title_thai_desc","title_thai") %>
				</td><td width='140'>
					<input type="hidden" name="title_eng" value="<%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getEngTitleName()) %>">
					<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getEngFirstName(),displayMode,"","name_eng","textbox","onblur=\"javascript:checkEngFName('name_eng')\"","30") %>
				</td><td width='140'>
					<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getEngLastName(),displayMode,"","surname_eng","textbox","onblur=\"javascript:checkEngLName('surname_eng')\"","50") %>
				</td>
			<td>&nbsp;</td></tr>
			</table>
	</tr>
		<%if(personalInfoDataM.getBirthDate()==null){
			sDate="";
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getBirthDate()));
		}%>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BIRTH_DATE") %><Font color="#ff0000">*</Font> :</td>
		<% String checkBdScript="";
		if(OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){
		      if(!OrigConstant.CUSTOMER_TYPE_CORPORATE.equals(personalInfoDataM.getCustomerType())){
		       checkBdScript= ";checkCMRworingDate('birth_date','pre_score_year','pre_score_month',this);checkCMRAddressReside('birth_date','pre_score_time_current_address_year','pre_score_time_current_address_month',this);";
		       }
		  }else{
		   checkBdScript= ";checkWoringDate('birth_date','year','month',this);validateAddressReside('birth_date')";
		  }		
		%>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","birth_date","textbox","right","onblur=\"javascript:checkBirthDate('birth_date','age')"+checkBdScript+"\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\"   maxlength =\"10\"") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AGE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(personalInfoDataM.getAge()),displayMode,"","age","textboxReadOnly","style=\"width:90%;\" readOnly","") %></td>
		<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "RACE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(raceVect,race,"race",displayMode," style=\"width:90%;\" ") %></td>
		<%}else{ %>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
		<%} %>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GENDER") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(genderVect,personalInfoDataM.getGender(),"gender",displayMode,"style=\"width:120px;\"") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MARITAL_STATUS") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(maritalStatusVect,maritalStatus,"marital_status",displayMode," style=\"width:90%;\" ") %></td>
		<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NATIONALITY") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(nationalityVect,nationality,"nationality",displayMode," style=\"width:90%;\" ") %></td>
		<%}else{ %>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
		<%} %>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CARD_IDENTITY") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(cardTypeVect,cardType,"card_identity",displayMode,"onChange=\"javascript:\" style=\"width:90%;\" ") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CARD_ID") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getCardID(),displayMode,"","card_id","textbox","onblur=\"javascript:checkCardType('card_identity','card_id')\" style=\"width:90%;\" ","20") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,personalInfoDataM.getCustomerType(),"customer_type_tmp",displayMode,"style=\"width:120px;\" disabled") %>
		<input type="hidden" name="customer_type" value="01"></td>	
	</tr>
	<tr>
		<%if(personalInfoDataM.getIssueDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getIssueDate()));
		}%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ISSUE_DATE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","issue_date","textbox","right","onblur=\"javascript:checkDate('issue_date');checkYears(this)\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
		<%if(personalInfoDataM.getExpiryDate()==null){
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getExpiryDate()));
		}%>
		<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ISSUE_BY") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIssueBy(),displayMode,"","issue_by","textbox"," style=\"width:90%;\" ","20") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","expiry_date","textbox","left","onblur=\"javascript:checkDate('expiry_date');checkYears(this)\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
		<%}else{ %>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","expiry_date","textbox","left","onblur=\"javascript:checkDate('expiry_date');checkYears(this)\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
		<%} %>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CLIENT_GROUP") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" colspan="2">
		<%if(menu!=null&&menu.equals("Y")){ %>
			<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getClientGroup(),displayMode,"6","client_group","textbox","textboxReadOnly","4")%>
		<%}else{ %>
			<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(personalInfoDataM.getClientGroup(),displayMode,"5","client_group","60","client_desc","textbox","","4","...","button_text","LoadClientGroup",clientGroup,"ClientGroup") %>
		<%}%>
		</td>
		<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
		%>
		<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "TAX_ID") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getTaxID(),displayMode,"","tax_id","textbox","onblur=\"javascript:checkTaxID('tax_id')\" style=\"width:90%;\" ","13") %></td>
		<td class="inputCol">&nbsp;</td>
		<%}else{ %>
		<td class="textColS">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
		<td class="inputCol">&nbsp;</td>
		<%} %>
	<%if(!OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){ %>
		<tr>
			<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AREA_CODE") %><Font color="#ff0000">*</Font> :</td>
			<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(applicationDataM.getAreaCode(),displayMode,"5","area_code","50","area_desc","textbox","","4","...","button_text","LoadArea",new String[] {"area_code"},"",areaCode,"Area") %></td>
		</tr>	
	<%} %>
	<tr>
		<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){     
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SOURCE_CUSTOMER") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(sourceOfCustomerVect,applicationDataM.getSourceOfCustomer(),"source_of_customer",displayMode,"style=\"width:94%;\" ") %></td>
		<%if(personalInfoDataM.getKConsentDate()==null){
			sDate="";
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getKConsentDate()));
		}%>
		<td class="textColS"><%=ORIGDisplayFormatUtil.displayCheckBoxTagDesc("Y",personalInfoDataM.getKConsentFlag(),displayMode,"k_consent_flag","onClick=\"javascript:checkConsentFlag('k_consent_flag','k_consent_date');\" "+disabledForUW,MessageResourceUtil.getTextDescription(request, "APPLICATION_CONSENT")) %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","k_consent_date","textbox","left","onblur=\"javascript:checkDate('k_consent_date');checkConsentDate('k_consent_flag','k_consent_date');\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+disabledForUW) %></td>
		<td>&nbsp;</td>
		<%}else{ %>
		<%if(personalInfoDataM.getKConsentDate()==null){
			sDate="";
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getKConsentDate()));
		}%>
		<td class="textColS"><%=ORIGDisplayFormatUtil.displayCheckBoxTagDesc("Y",personalInfoDataM.getKConsentFlag(),displayMode,"k_consent_flag","onClick=\"javascript:checkConsentFlag('k_consent_flag','k_consent_date');\" "+disabledForUW,MessageResourceUtil.getTextDescription(request, "APPLICATION_CONSENT")) %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","k_consent_date","textbox","left","onblur=\"javascript:checkDate('k_consent_date');checkConsentDate('k_consent_flag','k_consent_date');checkCurrentDate('k_consent_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+disabledForUW) %></td>
		<%} %>
	</tr>
</table>
<script language="JavaScript">
if(document.appFormName.birth_date != null){
	checkBirthDate('birth_date','age');
}
function checkConsentDate(consentFlag, consentDate){
	var consentFlagField = eval("document.appFormName."+consentFlag);
	var consentDateField = eval("document.appFormName."+consentDate);
	
	if(consentDateField.value == ''){
		consentFlagField.checked = false;
	}else{
		consentFlagField.checked = true;
	}
	
}
function checkConsentFlag(consentFlag, consentDate){
	var consentFlagField = eval("document.appFormName."+consentFlag);
	var consentDateField = eval("document.appFormName."+consentDate);
	
	if(!consentFlagField.checked){
		consentDateField.value = '';
	}else{
		consentDateField.value = '<%=currentDateStr%>';
	}
	
} 
function checkYears(objectCheck){
  var dateCheck=objectCheck.value;
  if( Number(dateCheck.substring(dateCheck.lastIndexOf('/')+1))   <2300) {
   alert('Year not less than 2300');
   objectCheck.value='';
  }
}
function checkWoringDate(birthDate,workingYear,workingMonth,objFocus){ 
     var objBirthDate=eval("document.appFormName."+birthDate);
     var objWorkingYear=eval("document.appFormName."+workingYear);
     var objWorkingMonth=eval("document.appFormName."+workingMonth);      
     var strValue = objBirthDate.value ;
  	 var arrayDate = strValue.split('/');
 	 var intYear = parseInt(arrayDate[2]);
 	 var intMonth = arrayDate[1];
 	 var currentDate = new Date();
	 var cYear  = currentDate.getFullYear()+543;
   	 var cMonth = currentDate.getMonth()+1;
     var cDate=currentDate.getDate();
   	 var intDate=arrayDate[0];
   	 var ageYear=cYear-intYear;
  	 var ageMonth=cMonth-intMonth;
   	// if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
   	 if(cMonth < intMonth ){
			ageYear = ageYear-1;
			ageMonth=12+cMonth-intMonth;
		}   
   	if (objWorkingYear!=undefined && objWorkingMonth!=undefined){
	   	 var tAgeYear=ageYear;
	   	 var tWorkingYear=Number(objWorkingYear.value);
	   	 var tWorkingMonth=Number(objWorkingMonth.value);
	   	//  if(tWorkingMonth>0){
	   	//  tWorkingYear=Number(tWorkingYear)+1;
	   	// }   	    	  
	     if(  Number(tWorkingYear)>Number(tAgeYear) ||((Number(tWorkingYear)==Number(tAgeYear))&&(tWorkingMonth>ageMonth))){
	        alert("Total Working must not greater than Age ("+ageYear+" Year "+ageMonth+" Month)"); 
	        objFocus.value='';
	        objFocus.focus();  
	     }
    }
 
}

$(document).ready(function (){
	
	var thaiArray = new Array();
	var engArray = new Array();		
	<%
	for(int i=0; i< engTitles.size(); i++) {
		String value = ((TitleEngProperties) engTitles.get(i)).getEnDesc(); %>
		engArray[<%=i%>] = "<%=value%>";
	<%}
	for(int i=0; i< thaiTitles.size(); i++) {
		String value = ((TitleProperties) thaiTitles.get(i)).getThDesc();%>	
		thaiArray[<%=i%>] = "<%=value%>";
	<%}%>
	
	$("#title_thai_desc").autocomplete({
			source: thaiArray,
			minLength: 1,
			open: function(){
				$("#title_thai_desc").autocomplete("widget").width(330);
			}
	 });
			
	$("#title_eng_desc").autocomplete({
			source: engArray,
			minLength: 1,
			open: function(){
				$("#title_eng_desc").autocomplete("widget").width(330);
			}
	 });		
			
	$("#area_code").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q: request.term, lookUp: "areaCode"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#area_code").autocomplete("widget").width(340);
			} ,
			select: function(event, ui) {
				$("#area_desc").val(ui.item.desc);
			}
	});	
	
	$("#client_group").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	lookUp: "clientGroup", q:request.term},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					} 
				});
			},
			minLength: 1,
			open: function(){
				$("#client_group").autocomplete("widget").width(340);
			} ,
			select: function(event, ui) {
				$("#client_desc").val(ui.item.desc);
			}
	});	
	
	
});
</script>