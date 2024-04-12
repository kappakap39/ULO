<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" %>


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
	ORIGFormHandler popupForm = (ORIGFormHandler)ORIGForm.getPopupForm();
	String displayMode = formUtil.getDisplayMode("MG_COLLATERAL_ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), popupForm.getFormID(), searchType);	

	System.out.println("Address Popup");
	ORIGUtility utility = new ORIGUtility();
	AddressDataM addressDataM = (AddressDataM) request.getSession().getAttribute("ADDRESS_POPUP_DATA");
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
	Vector addressVect = utility.getAddressByCustomerTypePersonalTypeAndNotUsed(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType(), addressDataM.getAddressType(), personalType);
	Vector addressUsedVect = utility.getAddressByCustomerTypeAndUsed(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType());
	Vector statusVect = utility.loadCacheByName("AddressStatus");
	//if(!("Y").equals(addressDataM.getOrigOwner()) && addressDataM.getOrigOwner() != null && !("").equals(addressDataM.getOrigOwner())){
	//	displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
	//}
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	String provinceDesc = cacheUtil.getORIGMasterDisplayNameDataM("Province", addressDataM.getProvince());
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
%>
<script language="JavaScript" src="naosformutil.js"></script>
<table cellpadding="" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "COPY_ADDRESS_TYPE") %> :</td>
		<td class="inputCol" width="25%">
			<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(addressUsedVect,"","address_type_copy",displayMode,"") %>
			&nbsp;<input type="button" name="Copy" value="Copy" onClick="copyAddress()" class="button_text" <%=disabledBtn %>>
		</td>
		<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","address_no")%></Font> :</td>
		<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getAddressNo(),displayMode,"15","address_no","textbox","","50") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ROAD") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","road")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getRoad(),displayMode,"15","road","textbox","","30") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SOI") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","soi")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getSoi(),displayMode,"15","soi","textbox","","30") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VILLAGE_APARTMENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","apartment")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getApartment(),displayMode,"15","apartment","textbox","","150") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MOO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","moo")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getMoo(),displayMode,"15","moo","textbox","","5") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ROOM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","room")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getRoom(),displayMode,"15","room","textbox","","5") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FLOOR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","floor")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getFloor(),displayMode,"15","floor","textbox","","5") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROVINCE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","province")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataM.getProvince(),displayMode,"5","province","20","province_desc","textbox","","10","...","button_text","LoadProvince",new String[] {"zipcode"}, "",provinceDesc,"Province") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ZIP_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","zipcode")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataM.getZipcode(),displayMode,"7","zipcode","textbox","onblur=\"javascript:checkZipCode('zipcode')\"","10","...","button_text","LoadZipCode",new String[] {"province"},"") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TAMBOL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","tambol")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getTambol(),displayMode,"15","tambol","textbox","","30") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AMPHUR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MG_COLLATERAL_ADDRESS_SUBFORM","amphur")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getAmphur(),displayMode,"15","amphur","textbox","","30") %></td>
	</tr>
</table>
<input type="hidden" name="address_seq" value="<%=addressDataM.getAddressSeq() %>">
<input type="hidden" name="address_type" value="<%=addressDataM.getAddressType() %>">
<script language="JavaScript">
function saveData(customerType,popupType){
	form = document.appFormName;
	form.action.value = "SaveAddress";
	form.handleForm.value = "N";
	form.submit();
	
}
function closePopup(){
	window.close();
}
function setPersonalData(){
	form = document.appFormName;
	openForm = opener.document.appFormName;
	form.identification_no.value = openForm.identification_no.value;
	form.name1.value = openForm.title_thai_desc.value;
	form.name2.value = openForm.name_thai.value;
	form.name3.value = openForm.surname_thai.value;
	
}
if(document.appFormName.identification_no != null){
	setPersonalData();
}

function copyAddress(){
	//try{
		var form = document.appFormName;
		var address_type_copy = form.address_type_copy.value;
		var address_type = form.address_type.value;
		var address_seq = form.address_seq.value;
		//alert(personalId);
		var req = new DataRequestor();
		var url = "CopyAddressServlet";
		req.addArg(_POST, "address_type_copy", address_type_copy);
		req.addArg(_POST, "address_type", address_type);
		req.addArg(_POST, "address_seq", address_seq);
		req.getURL(url, _RETURN_AS_DOM);
		req.onload = function (data, obj) {
			if (data!=null) {
				var list = data.documentElement;
				var fields = list.childNodes;
				
				for(i=0; i<fields.length; i++){
			
					var field = fields[i];
					//alert("field ->" + field.nodeName);
					var values = "";
					if(field.firstChild != null){
						values = field.firstChild.nodeValue;
					}
					//alert("value -> "+ values);
					var fieldName = field.getAttribute('name');
					//alert("fieldName ->" + fieldName);
					if(values != null){
						var resultField = document.getElementById(fieldName);
						if(resultField != null){
							resultField.setAttribute('value', values);
						}
						
					}
				}
			}
		}
	//} catch(er) {
	//	alert("Error Retieve Data : "+er);
	//}
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
function checkResideYearMonth(pBirthDate,pYear,pMonth,activeObject){ 
    var objBirthDate=eval("window.opener.appFormName."+pBirthDate);
    var objResideYear=eval("document.appFormName."+pYear);
    var objResideMonth=eval("document.appFormName."+pMonth);  
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
   	 //if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
 	 if(cMonth < intMonth  ){
			ageYear = ageYear-1;
			ageMonth=12+cMonth-intMonth;
		}   	  

   	 var tAgeYear=ageYear;
   	 var tWorkingYear=Number(objResideYear.value);
   	 var tWorkingMonth=Number(objResideMonth.value);
   	//  if(tWorkingMonth>0){
   	//  tWorkingYear=Number(tWorkingYear)+1;
   	// }   	    	  
    // if(  Number(tWorkingYear)>Number(tAgeYear) ){
    if(  Number(tWorkingYear)>Number(tAgeYear)||((Number(tWorkingYear)==Number(tAgeYear))&&(tWorkingMonth>ageMonth)) ){
        alert("Total Reside must not greater than Age ("+ageYear+" Year "+ageMonth+" Month)"); 
        activeObject.value='';
        activeObject.focus();  
     }
}
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>