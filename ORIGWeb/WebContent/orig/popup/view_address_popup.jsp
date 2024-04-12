<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.AddressDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="java.util.Vector" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGUtility utility = new ORIGUtility();
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
	//Vector addressVect = ORIGForm.getPersonalTmp().getAddressVect();
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
	//PersonalInfoDataM personalInfoDataMTmp = ORIGForm.getPersonalTmp();
	Vector addressTypeVect = utility.getAddressByCustomerType(personalInfoDataM.getCustomerType());
	Vector statusVect = utility.loadCacheByName("AddressStatus");
	
	Vector addressVect = personalInfoDataM.getAddressVect();
	Vector addressTmpVect = personalInfoDataM.getAddressTmpVect();
	AddressDataM addressDataM;
	AddressDataM addressDataMTmp;
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	String titleDesc = cacheUtil.getORIGMasterDisplayNameDataM("Title",personalInfoDataM.getThaiTitleName());
	String provinceDesc;
	
	//Print error
	Vector v = ORIGForm.getFormErrors();
	Vector vErrorFields = ORIGForm.getErrorFields();
	System.out.println("Error Size = " + v.size());
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
	ORIGForm.setFormErrors(new Vector());
%>
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" onkeydown="testKeyDown()">
	<tr><td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">	
	<%
	if(addressTmpVect!= null) {
		String displayCheck;
		String addressType;
		String sDate;
		for(int i=0; i<addressTmpVect.size(); i++){
			displayCheck = "";
			addressDataMTmp = (AddressDataM) addressTmpVect.get(i);
			System.out.println("addressDataMTmp.getAddressType() = "+personalInfoDataM.getAddressVect().size());
    		addressDataM = utility.getAddressByType(personalInfoDataM, addressDataMTmp.getAddressType());
    		if(addressDataM != null){
    			displayCheck = "disabled";
    		}else{
    		addressDataM=new AddressDataM();
    		}
    		provinceDesc = cacheUtil.getORIGMasterDisplayNameDataM("Province", addressDataMTmp.getProvince());
    		addressType = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addressDataMTmp.getAddressType());
    		if(addressDataMTmp.getUpdateDate()==null){
				sDate="";
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(addressDataMTmp.getUpdateDate()));
			}
	%>
		<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=addressType %> : <%=ORIGDisplayFormatUtil.dateTimetoStringForThai(addressDataMTmp.getUpdateDate()) %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    	<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "SAVE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "saveBnt", "button", "onClick=\"javascript:saveData()\"", "") %></td>
									<td><%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %>
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
						<tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="0" cellspacing="0" width="100%" align="center">
							<tr>
								<td bgcolor="#F4F4F4"><%=ORIGDisplayFormatUtil.displayCheckBoxTag(ORIGDisplayFormatUtil.displayInteger(addressDataMTmp.getAddressSeq()), "", "EDIT", "select", displayCheck, "") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NAME") %> :</td>
								<td class="inputCol" colspan="2"><%=ORIGDisplayFormatUtil.displayHTML(titleDesc)+" "+ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName())+" "+ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %></td>
								<td class="textColS" colspan="2"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %> :</td>
								<td class="inputCol" colspan="4"><%=personalInfoDataM.getIdNo() %></td>
							</tr>
							<tr>
								<td rowspan="10" bgcolor="#F4F4F4"></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE") %> :</td>
								<td class="inputCol" colspan="9"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(addressTypeVect,addressDataMTmp.getAddressType(),"address_type",displayMode,"") %></td>
							</tr>
							<tr>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","seq")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getSeqTmp(),displayMode,"15","seq","textboxReadOnly","readOnly","10") %></td>
								<td class="textColS" width="10%" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "ADDRESS_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","address_no")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getAddressNo(),displayMode,"15","address_no","textbox","","50") %></td>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "ROOM") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","room")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getRoom(),displayMode,"15","room","textbox","","5") %></td>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "FLOOR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","floor")%></Font> :</td>
								<td class="inputCol" width="30%" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getFloor(),displayMode,"15","floor","textbox","","5") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "VILLAGE_APARTMENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","apartment")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getApartment(),displayMode,"15","apartment","textbox","","150") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MOO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","moo")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getMoo(),displayMode,"15","moo","textbox","","5") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "SOI") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","soi")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getSoi(),displayMode,"15","soi","textbox","","30") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ROAD") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","road")%></Font> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getRoad(),displayMode,"15","road","textbox","","30") %></td>
							</tr>
							<tr>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "TAMBOL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","tambol")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getTambol(),displayMode,"15","tambol","textbox","","30") %></td>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "AMPHUR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","tambol")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getAmphur(),displayMode,"15","amphur","textbox","","30") %></td>
								<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "PROVINCE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","province")%></Font> :</td>
								<td class="inputCol" width="30%" colspan="3">
									<%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataMTmp.getProvince(),displayMode,"5","province","20","province_desc","textbox","","10","...","button_text","LoadProvince",new String[] {"zipcode"}, "",provinceDesc,"Province") %></td>
								<td class="textColS" width="10%" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "ZIP_CODE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","zipcode")%></Font> :</td>
								<td class="inputCol" width="10%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(addressDataMTmp.getZipcode(),displayMode,"7","zipcode","textbox","onblur=\"javascript:checkZipCode('zipcode')\"","10","...","button_text","LoadZipCode",new String[] {"province"},"") %>		
								</td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO1") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","phone1")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getPhoneNo1(),displayMode,"15","phone1","textbox","","20") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXT1") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","getMandatory_ORIG")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getPhoneExt1(),displayMode,"15","ext1","textbox","","5") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MOBILE_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","mobile_no")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getMobileNo(),displayMode,"15","mobile_no","textbox","","20") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_PERSON") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","contact_person")%></Font> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getContactPerson(),displayMode,"15","contact_person","textbox","","50") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PHONE_NO2") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","phone2")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getPhoneNo2(),displayMode,"15","phone2","textbox","","20") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXT2") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","fax_no")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getPhoneExt2(),displayMode,"15","ext2","textbox","","5") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FAX_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","fax_no")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getFaxNo(),displayMode,"15","fax_no","textbox","","20") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EMAIL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","email")%></Font> :</td>
								<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getEmail(),displayMode,"15","email","textbox","onblur=\"javascript:checkEmailAddress('email')\"","30") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "HOUSE_ID") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","house_id")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getHouseID(),displayMode,"15","house_id","textbox","","15") %></td>
							<% 
							int reside_year=0; 
							int reside_month=0;
							if(addressDataMTmp.getResideYear()!=null){
							  reside_year=addressDataMTmp.getResideYear().intValue();
							  reside_month=(int)(addressDataMTmp.getResideYear().doubleValue()*100%100);
							}
							%>		
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "RESIDE_YEAR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","reside_year")%></Font> :</td>
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(reside_year),displayMode,"15","reside_year","textbox"," onBlur=\"javascript:returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" ","10") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "RESIDE_MONTH") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","reside_month")%></Font>:</td>		
								<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(reside_month),displayMode,"15","reside_month","textbox","onBlur=\"javascript:checkMonth('reside_month');returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" ","10") %></td>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "STATUS") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","status")%></Font> :</td>
								<td class="inputCol" colspan="5"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(statusVect,addressDataMTmp.getStatus(),"status",displayMode,"") %></td>
							</tr>
							<tr>
								<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REMARK") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"ADDRESS_POPUP","remark")%></Font> :</td>
								<td class="inputCol" colspan="9"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(addressDataMTmp.getRemark(),displayMode,"110","remark","textbox","","50") %></td>
							</tr>
						</table>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</td></tr>
<%
		} 
	} else {
%>
	<tr><td class="textColS">
		No Data
	</td></tr>
<%	
	}
%>	
	</table>
</td></tr>
</table>
<script language="JavaScript">
function saveData(){
	form = document.appFormName;
	form.action.value = "SaveSelectAddress";
	form.handleForm.value = "N";
	form.submit();
}
function closePopup(){
	window.close();
}
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>