<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.cache.data.CacheDataM"%>
<%@ page import="com.eaf.orig.shared.model.AddressDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page
	import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM"%>
<%@ page
	import="com.eaf.xrules.shared.model.XRulesPhoneVerificationDataM"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>

<jsp:useBean id="ORIGUser" scope="session"
	class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session"
	class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%//MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
            ORIGUtility utility = new ORIGUtility();
            ORIGCacheUtil cacheUtil = new ORIGCacheUtil();

            %>

<%ApplicationDataM appForm = ORIGForm.getAppForm();
            //get Personal
            PersonalInfoDataM personalInfoDataM;
            String personalType = (String) request.getSession().getAttribute("PersonalType");
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
                personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
            }else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
				personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			} else {
                personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
            }
             String  displayMode=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
            System.out.println("viewFromReport " + request.getParameter("viewFromReport"));
            String chkDisable = "";
            //=============================================
            if (request.getParameter("viewFromReport") != null) {
                if (session.getAttribute("applicationVerification") != null) {
                    ApplicationDataM applicationDataM = (ApplicationDataM) session.getAttribute("applicationVerification");
			      String reportPersonalSeq=request.getParameter("reportPersonalSeq");     
			      String reportPersonalType=request.getParameter("reportPersonalType");            
			      int personalSeq=utility.stringToInt(reportPersonalSeq);
			      personalInfoDataM=utility.getPersonalInfoByTypeAndSeq( applicationDataM,reportPersonalType,personalSeq);     
                    appForm=applicationDataM;
                }               
                displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
                chkDisable = " disabled";
            }
            if (personalInfoDataM == null) {
                System.out.println("==> personalInfoDataM is Null");
                personalInfoDataM = new PersonalInfoDataM();
            }

            //  System.out.println("XXXXXXXXXXXXXXXXXX getXrulesVerification");   
            XRulesVerificationResultDataM xrulesVer = personalInfoDataM.getXrulesVerification();
            if (xrulesVer == null) {
                xrulesVer = new XRulesVerificationResultDataM();
            }
            Vector vPhoneVerification = null;
            if (session.getAttribute("phoneVerItem") != null) {
                vPhoneVerification = (Vector) session.getAttribute("phoneVerItem");
            } else {
                if (xrulesVer.getVXRulesPhoneVerificationDataM() != null) {
                    vPhoneVerification = (Vector) xrulesVer.getVXRulesPhoneVerificationDataM().clone();

                } else {
                    vPhoneVerification = new Vector();
                }
                session.setAttribute("phoneVerItem", vPhoneVerification);
            }
            //  System.out.println("XXXXXXXXXXXXXXXXXX Personal");   
            if (vPhoneVerification == null) {
                vPhoneVerification = new Vector();
            }
            Vector vPerson = appForm.getPersonalInfoVect();
           // String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
            Vector vPartyItems = new Vector();
            int guarantorCount = 0;
            System.out.println("XXXXXXXXXXXXXXXXXX Personal vPerson.size() " + vPerson.size());
            //System.out.println("XXXXXXXXXXXXXXXXXX Phone Party");
            String selectPartyType = request.getParameter("phonePartyType");
            System.out.println("selectPartyType  " + selectPartyType);
            PersonalInfoDataM phoneVerPersonalInfoDataM = null;
            if (selectPartyType != null) {
                int index = 0;
                try {
                    index = ORIGDisplayFormatUtil.StringToInt(selectPartyType.substring(selectPartyType.indexOf("_") + 1));
                    phoneVerPersonalInfoDataM = (PersonalInfoDataM) vPerson.get(index);
                } catch (Exception ex1) {
                    System.out.print(ex1.getMessage());
                }
            } else {
                //phoneVerPersonalInfoDataM=(PersonalInfoDataM)vPerson.get(0);
                phoneVerPersonalInfoDataM = personalInfoDataM;
            }
            if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType) && vPerson != null) {
                for (int i = 0; i < vPerson.size(); i++) {
                    PersonalInfoDataM appPersonalInfoDataM = (PersonalInfoDataM) vPerson.get(i);
                    CacheDataM cacheDataM = new CacheDataM();
                    cacheDataM.setCode(appPersonalInfoDataM.getPersonalType() + "_" + String.valueOf(i));
                    //String partyType=cacheUtil.getORIGMasterDisplayNameDataM("PersonalType", appPersonalInfoDataM.getPersonalType());
                    String partyType = "";
                    if (OrigConstant.PERSONAL_TYPE_APPLICANT.equalsIgnoreCase(appPersonalInfoDataM.getPersonalType())) {
                        partyType = "Main Applicant";
                        if (selectPartyType == null) {
                            selectPartyType = appPersonalInfoDataM.getPersonalType() + "_" + String.valueOf(i);
                        }
                    } else if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equalsIgnoreCase(appPersonalInfoDataM.getPersonalType())){
                        partyType = "Guarantor " + (++guarantorCount);
                    }else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equalsIgnoreCase(appPersonalInfoDataM.getPersonalType())){
                        partyType = "Supplementary Card " + (++guarantorCount);
                    }
                    cacheDataM.setShortDesc(partyType);
                    vPartyItems.add(cacheDataM);
                }

            } else {
                CacheDataM cacheDataM = new CacheDataM();
                cacheDataM.setCode(OrigConstant.PERSONAL_TYPE_GUARANTOR);
                String partyType = "Guarantor ";
                cacheDataM.setShortDesc(partyType);
                vPartyItems.add(cacheDataM);
            }

            String phonePartyValue = "";
            //String diplayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
            String phoneVerResult = xrulesVer.getPhoneVerResult();
            HashMap hPersonal = XRulesConstant.hCustomerType;
            //System.out.println("viewFromReport " + request.getParameter("viewFromReport"));
           // String chkDisable = "";
           // if (request.getParameter("viewFromReport") != null) {
           //     if (session.getAttribute("personalApplicatinVerification") != null) {
            //        personalInfoDataM = (PersonalInfoDataM) session.getAttribute("personalApplicatinVerification");
            //    }
            //    diplayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
            //    chkDisable = " disabled";
            //}

            System.out.println(" Persoanl Type " + phoneVerPersonalInfoDataM.getPersonalType());

            %>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<TITLE>Phone Verrification</TITLE>
<script language="javascript">
window.onBlur = self.focus(); 
function savePhoneVerification(){
    form = document.appFormName;
	form.action.value = "SaveXrulesPhoneVerPopup";
	form.handleForm.value = "N";
	form.phoneVerAction.value="save";
	if(form.phoneVerificationResult[0].checked ||form.phoneVerificationResult[1].checked){
	form.submit();
	}else{
	alert("Please Verify Phone result");
	}
}
function saveAndClosePhoneVerificatin(){
    form = document.appFormName;
	form.action.value = "SaveXrulesPhoneVerPopup";
	form.handleForm.value = "N";
	form.phoneVerAction.value="saveAndClose";
		if(form.phoneVerificationResult[0].checked ||form.phoneVerificationResult[1].checked){
	form.submit();
	}else{
	 alert("Please Verify Phone result");
	}

}
function closePhoneVerification(){  
  <%if( ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equalsIgnoreCase( displayMode)){%>
    window.close();
  <%}else {%>
    form = document.appFormName;
	form.action.value = "SaveXrulesPhoneVerPopup";
	form.handleForm.value = "N";
	form.phoneVerAction.value="Close";      
	form.submit();	 
  <%}%>
}
function addPhoneVerification(){
     form = document.appFormName;
     if(form.phoneItem){
      var itemLength=form.phoneItem.length;
      var itemCheck=false;
      if(itemLength>1){
      for(c=0;c<itemLength;c++){
        if( eval("form.phoneItem["+c+"].checked")){
           itemCheck=true;
           break;
        }
      }     
      }else{
       if( eval("form.phoneItem.checked")){
           itemCheck=true; 
        }
      }  
      if(itemCheck){
	   form.action.value = "SaveXrulesPhoneVerPopup";
	   form.handleForm.value = "N";
       form.phoneVerAction.value="addPhone";
       //form.txtRemark.vaule= form.txtRemark.value.toUpperCase();
       form.submit();
       }else{
        alert("Please select Phone no.");
       }
    }else{
     alert("Can't found address Phone Verifiation");
    }
}
function upperCaseFiled(fieldName){
  fieldName.value=fieldName.value.toUpperCase();
}
function changeParty(){    
    form = document.appFormName;
	form.action.value = "LoadXrulesPhoneVerPopup";	 
	form.handleForm.value = "N";
	if(form.phonePartyType.value!=""){
	 form.submit();
	}
}
</script>
</HEAD>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "XRULES_BUTTON_16") %> </td>
	                            <td width="330">
	                            	<table width="60" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
		                              		<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>"
												onClick="savePhoneVerification()" <%=chkDisable%>>
	                              			 </td><td>
												<INPUT type="button" class="button" name="btnSaveAndClose" value="Save &amp; Close" 
												onClick="saveAndClosePhoneVerificatin()" <%=chkDisable%>>
											</td><td>
												<INPUT class="button" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>"
												onClick="closePhoneVerification()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>
                        <tr class="sidebar10"> <td align="center">
							<div>
							<FIELDSET><LEGEND><font class="Bigtodotext3">Phone Verification Status</font></LEGEND><BR>
							<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
								<tr>
									<td class="textColS" width="50%" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></b></td>
									<td class="textColS" width="20%">&nbsp;</td>
									<td class="textColS" align="left" width="30%">
										<%if("".equals(chkDisable)) {%>
										 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										 	<tr><td><font class="textColS">
									           Pass</font> </td> 
									        <td>             
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_PASS, displayMode, "phoneVerificationResult",
                                									phoneVerResult, XRulesConstant.ExecutionResultString.RESULT_PASS, XRulesConstant.ExecutionResultString.RESULT_PASS)%>
									        </td><td><font class="textColS">
									        	Fail</font>
									        </td><td>
									        	<%=ORIGDisplayFormatUtil.displayRadioTag(XRulesConstant.ExecutionResultString.RESULT_FAIL, displayMode, "phoneVerificationResult",
                                									phoneVerResult, XRulesConstant.ExecutionResultString.RESULT_FAIL, XRulesConstant.ExecutionResultString.RESULT_FAIL)%>
									        </td></tr></table> 
									     <%}else{ %>
									     	<%=ORIGDisplayFormatUtil.displayHTML(phoneVerResult)%>
									     <%} %> 
									</td>
								</tr>
							</table>
							<%if( ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equalsIgnoreCase( displayMode)){ %>
							<table cellpadding="" cellspacing="0" width="100%" align="center">
								<tr>
									<td colspan="2" align="center" height="50"><input type="hidden" name="phoneVerAction" value=""></TD>
								</TR>							
								<tr>
									<td class="Bigtodotext3" colspan="2" align="center">Phone Verification Entry Detail</TD>
								</TR>
								<TR>
									<TD width="300">&nbsp;</TD>
									<TD width="400">&nbsp;</TD>
								</TR>
								<TR>
									<TD align="right" class="textColS">Party <FONT color="red">*</FONT>:&nbsp;&nbsp;
									</TD>
									<TD class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagJS(vPartyItems, selectPartyType, "phonePartyType", displayMode, " onChange='changeParty()' "
						                            + chkDisable)%></TD>
								</TR>
								<TR>
									<TD align="right" class="textColS">Name&nbsp;:&nbsp;&nbsp;</TD>
									<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(phoneVerPersonalInfoDataM.getThaiFirstName())%>&nbsp;&nbsp; <%=ORIGDisplayFormatUtil.displayHTML(phoneVerPersonalInfoDataM.getThaiLastName())%></TD>
								</TR>
								<TR>
									<TD align="right" class="textColS">Citizen ID:&nbsp;&nbsp;</TD>
									<TD class="inputCol"><%=ORIGDisplayFormatUtil.displayHTML(phoneVerPersonalInfoDataM.getIdNo())%></TD>
								</TR>
								<TR>
									<TD height="28">&nbsp;</TD>
									<TD height="28">&nbsp;</TD>
								</TR>
								<%Vector vAddressVect = phoneVerPersonalInfoDataM.getAddressVect();
						            if (vAddressVect == null) {
						                vAddressVect = new Vector();
						            }
						            for (int j = 0; j < vAddressVect.size(); j++) {
						                AddressDataM addrDataM = (AddressDataM) vAddressVect.get(j);
						                String addressType = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", addrDataM.getAddressType());
						
						                %>
								<%if (addrDataM.getPhoneNo1() != null && !"".equals(addrDataM.getPhoneNo1())) {%>
								<TR>
									<TD align="right" class="textColS">
										<INPUT type="radio"	name="phoneItem" value="<%=addrDataM.getAddressType()%>_phone1"	<%=chkDisable%>>
									</TD>
									<TD class="inputCol"><%=addressType%> Telephone No
									1.&nbsp;&nbsp;<%=addrDataM.getPhoneNo1()%> 
									<%if (addrDataM.getPhoneExt1() != null) {
						                        out.print(" EXT " + ORIGDisplayFormatUtil.displayHTML(addrDataM.getPhoneExt1()));
						              }%></TD>
								</TR>
								<%}//if%>
								<%if (addrDataM.getPhoneNo2() != null && !"".equals(addrDataM.getPhoneNo2())) {%>
								<TR>
									<TD align="right" class="textColS">
										<INPUT type="radio" name="phoneItem" value="<%=addrDataM.getAddressType()%>_phone2" <%=chkDisable%>>
									</TD>
									<TD class="inputCol"><%=addressType%> Telephone No
									2.&nbsp;&nbsp;<%=addrDataM.getPhoneNo2()%> 
									<%if (addrDataM.getPhoneExt2() != null) {
						                        out.print(" EXT " + ORIGDisplayFormatUtil.displayHTML(addrDataM.getPhoneExt2()));
						                 }%>
						            </TD>
								</TR>
								<%}//if%>
								<%if (addrDataM.getMobileNo() != null && !"".equals(addrDataM.getMobileNo())) {%>
								<TR>
									<TD align="right" class="textColS">
										<INPUT type="radio" name="phoneItem" value="<%=addrDataM.getAddressType()%>_mobile" <%=chkDisable%>>
									</TD>
									<TD class="inputCol"><%=addressType%> Mobile No. <%=ORIGDisplayFormatUtil.displayHTML(addrDataM.getMobileNo())%></TD>
								</TR>
								<%}//if%>
						
								<%}//for %>
								<TR>
									<TD align="right">&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
								<TR>
									<TD align="right" colspan="2">&nbsp;
										<TEXTAREA rows="2" cols="77" name="txtRemark" onkeyup="upperCaseFiled(this)"></TEXTAREA></TD>
								</TR>
								<TR>
									<TD align="right" colspan="2"> 
										<INPUT class="button_text" type="button" name="btnAddPhone" value="Add" onClick="addPhoneVerification()" <%=chkDisable%>>
									</TD>
								</TR>
								<TR>
									<TD height="28">&nbsp;</TD>
									<TD>&nbsp;</TD>
								</TR>
							</TABLE>
							<%}%>
							
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								<tr><td colspan="4">
									<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr><td class="TableHeader">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
													<td class="Bigtodotext3" align="center">Date Time</TD>
													<TD class="Bigtodotext3" align="center">Party</TD>
													<TD class="Bigtodotext3" align="center">Name</TD>
													<TD class="Bigtodotext3" align="center">Number Call</TD>
													<TD class="Bigtodotext3" align="center">Remark</TD>
												</tr>
											</table>
										</td></tr>
			<%for (int i = 0; i < vPhoneVerification.size(); i++) {
	                XRulesPhoneVerificationDataM prmXrulesPhoneVer = (XRulesPhoneVerificationDataM) vPhoneVerification.get(i);
	                //get Phone From address
	                String strCallType = prmXrulesPhoneVer.getCallType();
	                int indexPhone = 0;
	                PersonalInfoDataM verPhoneVerPersonalInfoDataM = null;
	                try {
	                    indexPhone = ORIGDisplayFormatUtil.StringToInt(strCallType.substring(strCallType.indexOf("_") + 1));
	                    verPhoneVerPersonalInfoDataM = (PersonalInfoDataM) vPerson.get(indexPhone);
	                } catch (Exception ex2) {
	                }
	                if (verPhoneVerPersonalInfoDataM == null) {
	                    verPhoneVerPersonalInfoDataM = new PersonalInfoDataM();
	                }
	                String strContact = prmXrulesPhoneVer.getContactType();
	                String strAddressType = strContact.substring(0, strContact.indexOf("_"));
	                String strPhoneType = strContact.substring(strContact.indexOf("_") + 1);
	                // AddressDataM   phoneAddress=utility.getAddressByType(verPhoneVerPersonalInfoDataM,strAddressType);
	                String phoneNumberCall = cacheUtil.getORIGMasterDisplayNameDataM("AddressType", strAddressType);
	                if ("phone1".equals(strPhoneType)) {
	                    phoneNumberCall += "  No1.";
	                } else if ("phone2".equals(strPhoneType)) {
	                    phoneNumberCall += "  No2.";
	                } else if ("mobile".equals(strPhoneType)) {
	                    phoneNumberCall += "  moblie No.";
	                }
	                String bgColor = "bgColor=\"#FAFAFA\"";
	                if (i % 2 == 0) {
	                    bgColor = "bgColor=\"#FFFFFF\"";
	                }
	
	                %>
										<tr><td align="center" class="gumaygrey2">
											<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<tr>
												    <td class="jobopening2"><%=ORIGDisplayFormatUtil.dateTimetoString(prmXrulesPhoneVer.getUpdateDate())%></TD>
													<td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(hPersonal.get(verPhoneVerPersonalInfoDataM.getPersonalType()))%></TD>
													<td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(verPhoneVerPersonalInfoDataM.getThaiFirstName()) + " "
										                        + ORIGDisplayFormatUtil.displayHTML(verPhoneVerPersonalInfoDataM.getThaiLastName())%></TD>
													<td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(phoneNumberCall)%></TD>
													<td class="jobopening2"><%=ORIGDisplayFormatUtil.displayHTML(prmXrulesPhoneVer.getRemark())%></TD>
												</tr>
											</table>
										</td></tr>
							<%}
					            %>
									</table>
								</td></tr>
							</table>
							</FIELDSET></div>
						</td></tr>
					</table>
					</td></tr>
				</table>
			</td></tr>
		</table>
	</td></tr>
</table>

<%//set current screen to main Form
            com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager = (com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true)
                    .getAttribute("screenFlowManager");
            screenFlowManager.setCurrentScreen("MAIN_APPFORM");

        %>