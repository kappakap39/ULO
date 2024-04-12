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
	int seq = Integer.parseInt(request.getSession().getAttribute("seq").toString());
	if(seq == 0) {	//	Create new record
		
	}
	String displayMode = formUtil.getDisplayMode("MAIN_HOME_ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	System.out.println("Address Popup");
	ORIGUtility utility = new ORIGUtility();
	AddressDataM addressDataM = (AddressDataM) request.getSession().getAttribute("POPUP_DATA");
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
		
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	} 
	
	Vector addressVect = utility.getAddressByCustomerTypePersonalTypeAndNotUsed(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType(), addressDataM.getAddressType(), personalType);
	Vector addressUsedVect = utility.getAddressByCustomerTypeAndUsed(personalInfoDataM.getAddressVect(), personalInfoDataM.getCustomerType());
	//Vector statusVect = utility.loadCacheByName("AddressStatus");
	Vector statusVect = utility.loadCacheByNameAndCheckStatus("AddressStatus" , addressDataM.getStatus());
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
<script language="JavaScript" src="../../naosformutil.js"></script>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR>
		<TD vAlign=top>
			<TABLE  cellSpacing=0 cellPadding=0  width="100%" align=left border="0">
	          	<TR><TD class="sidebar8">
					<TABLE cellSpacing=0 cellPadding=0 width="100%" align="left"  border="0" bgcolor="FFFFFF">	
							<tr><td height="20"></td></tr>
							<tr>
								<td class="sidebar9">
									<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 height="20" >
									<tr> <td  height="10">
						 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
			                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_DETAIL")%></td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryPopupField('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.PopupName.POPUP_ADDRESS%>')" class="button" <%=disabledBtn %>>
			                                    </td><td>
			                                    	<input type="button" name="closeBnt" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="javascript:closePopup()" class="button">
			                                    </td></tr>
			                                </table>
			                            </td></tr>	
			                            </table>
			                        </td></tr>
			                        <tr> <td  height="15"></td></tr>  
									<tr class="sidebar10"> <td align="center">
										<table cellpadding="0" cellspacing="0" width="100%" align="center">
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NAME") %> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiTitleName(),displayMode,"5","name1","textboxReadOnly","readOnly","") %> </td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiFirstName(),displayMode,"","name2","textboxReadOnly","readOnly","") %></td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getThaiLastName(),displayMode,"","name3","textboxReadOnly","readOnly","") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getIdNo(),displayMode,"","identification_no","textboxReadOnly","readOnly","") %></td>
												<td colspan="3">&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","address_type")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(addressVect,addressDataM.getAddressType(),"address_type",displayMode,"") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "COPY_ADDRESS_TYPE") %> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(addressUsedVect,"","address_type_copy",displayMode,"") %></td>
												<td><input type="button" name="Copy" value="<%=MessageResourceUtil.getTextDescription(request, "COPY") %>" onClick="copyAddress()" class="button_text" <%=disabledBtn %>></td>
												<td colspan="4">&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","seq")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getSeqTmp(),displayMode,"15","seq","textboxReadOnly","readOnly","10") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","address_no")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getAddressNo(),displayMode,"15","address_no","textbox","","50") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "ROOM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","room")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getRoom(),displayMode,"15","room","textbox","","5") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FLOOR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","floor")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getFloor(),displayMode,"15","floor","textbox","","5") %></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VILLAGE_APARTMENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","apartment")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getApartment(),displayMode,"15","apartment","textbox","","150") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MOO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","moo")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getMoo(),displayMode,"15","moo","textbox","","5") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "SOI") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","soi")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getSoi(),displayMode,"15","soi","textbox","","30") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ROAD") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","road")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getRoad(),displayMode,"15","road","textbox","","30") %></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "TAMBOL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","tambol")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getTambol(),displayMode,"15","tambol","textbox","","10") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AMPHUR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","tambol")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getAmphur(),displayMode,"15","amphur","textbox","","30") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "PROVINCE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","province")%></Font> :</td>
												<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataM.getProvince(),displayMode,"5","province","20","province_desc","textbox","","10","...","button_text","LoadProvince",new String[] {"zipcode"}, "",provinceDesc,"Province") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ZIP_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","zipcode")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataM.getZipcode(),displayMode,"7","zipcode","textbox","onblur=\"javascript:checkZipCode('zipcode')\"","10","...","button_text","LoadZipCode",new String[] {"province"},"") %>	</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO1") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","phone1")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getPhoneNo1(),displayMode,"15","phone1","textbox","","20") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXT1") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","getMandatory_ORIG")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getPhoneExt1(),displayMode,"15","ext1","textbox","","5") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "MOBILE_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","mobile_no")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getMobileNo(),displayMode,"15","mobile_no","textbox","","20") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","contact_person")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getContactPerson(),displayMode,"15","contact_person","textbox","","50") %></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO2") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","phone2")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getPhoneNo2(),displayMode,"15","phone2","textbox","","20") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXT2") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","fax_no")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getPhoneExt2(),displayMode,"15","ext2","textbox","","5") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "FAX_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","fax_no")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getFaxNo(),displayMode,"15","fax_no","textbox","","20") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EMAIL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","email")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getEmail(),displayMode,"15","email","textbox","onblur=\"javascript:checkEmailAddress('email')\"","30") %></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "HOUSE_ID") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","house_id")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getHouseID(),displayMode,"15","house_id","textbox","","15") %></td>
												<% 
												int reside_year=0; 
												int reside_month=0;
												if(addressDataM.getResideYear()!=null){
												  reside_year=addressDataM.getResideYear().intValue();
												  reside_month=(int)(addressDataM.getResideYear().doubleValue()*100%100);
												}
												%>
												
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "RESIDE_YEAR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","reside_year")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(reside_year),displayMode,"15","reside_year","textbox"," onBlur=\"javascript:checkResideYearMonth('birth_date','reside_year','reside_month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" ","10") %></td>
												<td class="textColS">&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "RESIDE_MONTH") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","reside_month")%></Font>:</td>		
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(reside_month),displayMode,"15","reside_month","textbox","onBlur=\"javascript:checkMonth('reside_month');checkResideYearMonth('birth_date','reside_year','reside_month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" ","10") %></td>
												
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "STATUS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","status")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(statusVect,addressDataM.getStatus(),"status",displayMode,"") %></td>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REMARK") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","remark")%></Font> :</td>
												<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataM.getRemark(),displayMode,"110","remark","textbox","","50") %></td>
											</tr>
										</table>
									</td></tr>
								</table>
							</td>
						</tr>
					</table>
				</td></tr>
			</table>
		</td> 
	</tr>
</table>
<input type="hidden" name="address_seq" value="<%=addressDataM.getAddressSeq() %>">
<input type="hidden" name="address_type" value="<%=addressDataM.getAddressType() %>">
<script language="JavaScript">
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