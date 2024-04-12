<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector occupationVect = utility.loadCacheByName("Occupation");
	Vector businessTypeVect = utility.loadCacheByName("BusinessType");
	Vector businessSizeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",1);
	Vector positionVect = new Vector();
	//Vector qualificationVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",2);
   Vector qualificationVect = utility.getMasterDataFormCache("EDULEVCDE");
	Vector regisStatusVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",3);
	//Vector regisStatusVect = utility.loadCacheByName("AddressStatus");
	Vector builingConditionVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",11);
	Vector locationVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",5);
	Vector landOwnerVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",6);
	Vector preRecordVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",4);	
	
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
	//String disablePosition = "disabled";
	if(!ORIGUtility.isEmptyString(personalInfoDataM.getOccupation())){
		positionVect = cacheUtil.getPositionByOccupation(personalInfoDataM.getOccupation());
		//disablePosition = "";
	}
	double timeInAddress = personalInfoDataM.getTimeInCurrentAddress();
	String timeInAddressStr = String.valueOf(timeInAddress);
	String timeYear = "0";
	String timeMonth = "0";
	if(timeInAddressStr != null){
		int position = timeInAddressStr.indexOf(".");
		System.out.println("position = "+position);
		if(position == -1){
			timeYear = timeInAddressStr.substring(0, position);
		}else{
			timeYear = timeInAddressStr.substring(0, position);
			timeMonth = timeInAddressStr.substring(position+1, timeInAddressStr.length());
		}
	}
%>

<table cellpadding="" cellspacing="1" width="100%">
	<tr>
		<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "OCCUPATION") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(occupationVect,personalInfoDataM.getOccupation(),"occupation",displayMode,"onChange=\"javascript:parseOccupationToPosition(this.value,'PositionId','position'); \" style=\"width:80%;\" ") %></td>
		<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "BUS_TYPE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessTypeVect,personalInfoDataM.getBusinessType(),"bus_type",displayMode," style=\"width:80%;\" ") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "BUS_SIZE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessSizeVect,personalInfoDataM.getBusinessSize(),"bus_size",displayMode," style=\"width:80%;\"  ") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "COMPANY") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getCompanyName(),displayMode,"","company","textbox","style=\"width:80%;\" ","50") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "DEPARTMENT") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getDepartment(),displayMode,"","department","textbox","style=\"width:80%;\" ","30") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "POSITION") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" id="PositionId"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,personalInfoDataM.getPosition(),"position",displayMode," style=\"width:80%;\"  ") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "SALARY") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getSalary()),displayMode,"","salary","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "OTHER_INCOME") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getOtherIncome()),displayMode,"","other_income","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "POSITION_DESC") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getPositionDesc(),displayMode,"","position_desc","textbox","style=\"width:80%;\" ","70") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "SOURCE_OTHER_INCOME") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getSourceOfOtherIncome(),displayMode,"","source_other_income","textbox","style=\"width:80%;\" ","50") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "QUALIFICATION") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(qualificationVect,personalInfoDataM.getQualification(),"qualification",displayMode," style=\"width:80%;\"  ") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "TOTAL_WORKING") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol">
			<table cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
			<tr><td><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(personalInfoDataM.getYearOfWork()),displayMode,"","year","textbox","onBlur=\"javascript:;checkWoringDate('birth_date','year','month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:80%;\" ","2") %>
			</td><td class="textColS"><%=msgUtil.getTextDescription(request, "YEAR") %>
			</td><td><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(personalInfoDataM.getMonthOfWork()),displayMode,"","month","textbox","onBlur=\"javascript:checkMonth('month');checkWoringDate('birth_date','year','month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:80%;\" ","2") %>
			</td><td class="textColS"><%=msgUtil.getTextDescription(request, "MONTH") %>
			</td></tr> 
			</table>
		</td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "HOUSE_REGIS_STATUS") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(regisStatusVect,personalInfoDataM.getHouseRegisStatus(),"house_regis_status",displayMode," style=\"width:80%;\"  ") %></td>
		<td class="textColS"><%--=msgUtil.getTextDescription(request, "TIME_IN_CURRENT_ADDRESS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","no_of_employee")</Font> :--%></td>
		<td class="inputCol">&nbsp;<%--=ORIGDisplayFormatUtil.displayInputTagScriptAction(timeYear,displayMode,"","time_year","textbox","onBlur=\"javascript:returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:30%;\" ","3") %>&nbsp;<%=msgUtil.getTextDescription(request, "YEAR") %>
		       					   <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(timeMonth,displayMode,"","time_month","textbox","onBlur=\"javascript:checkMonth('time_month');returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:30%;\" ","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "MONTH") --%></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "BUILDING_CONDITION") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(builingConditionVect,personalInfoDataM.getBulidingCondition(),"building_condition",displayMode," style=\"width:80%;\"  ") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "PREVIOUS_RECORD") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(preRecordVect,personalInfoDataM.getPreviousRecord(),"pre_record",displayMode," style=\"width:80%;\"  ") %></td>
	</tr>
	<tr>
		<td class="textColS" width="17%"><%=msgUtil.getTextDescription(request, "ASSET_AMT") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol" width="32%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getAssetAmount()),displayMode,"","asset_amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "LOCATION") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(locationVect,personalInfoDataM.getLocation(),"location",displayMode," style=\"width:80%;\"  ") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "CHEQUE_RETURNED") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(personalInfoDataM.getChequeReturn()),displayMode,"","cheque_return","textbox","onKeyPress=\"keyPressInteger(this.value);\" onblur=\"javascript:addCommas('cheque_return')\" style=\"width:80%;\" ","2") %></td>
		<td class="textColS" width="15%"><%=msgUtil.getTextDescription(request, "DEBT_AMT") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getDebitAmount()),displayMode,"","debt_amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "LAND_OWNERSHIP") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(landOwnerVect,personalInfoDataM.getLandOwnerShip(),"land_ownership",displayMode," style=\"width:80%;\" ") %></td>
		<td class="textColS"><%=msgUtil.getTextDescription(request, "NUMBER_OF_EMPLOYEE") %><Font color="#ff0000">*</Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(personalInfoDataM.getNoOfEmployee()),displayMode,"","no_of_employee","textbox","onKeyPress=\"keyPressInteger(this.value);\" onblur=\"javascript:addCommas('no_of_employee')\" style=\"width:80%;\" ","4") %></td>
	</tr>
</table>