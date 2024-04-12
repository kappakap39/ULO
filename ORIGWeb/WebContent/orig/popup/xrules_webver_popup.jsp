<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.eaf.xrules.shared.constant.XRulesConstant"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.xrules.shared.model.XRulesVerificationResultDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<script>
function saveWebVerification(){    
    form = document.appFormName;
	form.action.value = "SaveXrulesWebVerPopup";
	form.handleForm.value = "N";
	form.submit();
}
function upperCaseFiled(fieldName){
  fieldName.value=fieldName.value.toUpperCase();
}
</script>
<%   
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
    	if(applicationDataM == null){
    		applicationDataM = new ApplicationDataM();
    	}
    	ORIGUtility utility = new ORIGUtility();
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
    		personalInfoDataM.setPersonalType(personalType);
    		applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
    	}
    	XRulesVerificationResultDataM xRulesVerification=personalInfoDataM.getXrulesVerification();
    ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("VERIFICATION_LIST_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
String strServiceId=request.getParameter("serviceid");
String txtResultName=request.getParameter("txtResultName");
int serviceID=0;
if(strServiceId!=null){
  serviceID=Integer.parseInt(strServiceId);
}
String serviceVer="";
String result="";
String comment="";
if(serviceID==XRulesConstant.ServiceID.KHONTHAI){
  serviceVer="Khonthai.com";
  result=xRulesVerification.getKhonThaiResult();
  comment=xRulesVerification.getKhonThaiComment();
}else if(serviceID==XRulesConstant.ServiceID.BOL){ 
  serviceVer="BOL";
  result=xRulesVerification.getBOLResult();
  comment=xRulesVerification.getBOLComment();
}else if(serviceID==XRulesConstant.ServiceID.THAIREGITRATION){
  serviceVer="Thai Registration";
   result=xRulesVerification.getThaiRegistrationResult();
  comment=xRulesVerification.getThaiRegistrationComment();
}else if(serviceID==XRulesConstant.ServiceID.YELLOWPAGE){
  serviceVer="Yellow Page";
   result=xRulesVerification.getYellowPageResult();
  comment=xRulesVerification.getYellowPageComment();
}else if(serviceID==XRulesConstant.ServiceID.PHONEBOOK){
  serviceVer="Phone Book";
   result=xRulesVerification.getPhoneBookResult();
  comment=xRulesVerification.getPhoneBookComment();
}
String strReadOnly="";
 
if(comment==null){comment="";}
%>
<TITLE><%=serviceVer%> Verification Result Popup Screen</TITLE>
</HEAD>
<INPUT type="hidden" name="serviceId" value="<%=serviceID%>">
<INPUT type="hidden" name="txtResultName" value="<%=txtResultName%>">
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
	                          	<td class="text-header-detail"><%=serviceVer%> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<%if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){ %>
	                              				<INPUT class="button" type=button value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="saveWebVerification()">
	                              				</td><td>
	                              			<%}%>	 
												<INPUT class="button" type=button value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" onClick="window.close()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<div>
							<FIELDSET><LEGEND><font class="Bigtodotext3">Web Verification</font></LEGEND><BR>
								<table cellpadding="0" cellspacing="0" width="100%" align="center">	 
									<tr>
										<td class="textColS" width="50%" align="right"><b><%=MessageResourceUtil.getTextDescription(request, "FINAL_RESULT") %></b></td>
										<td class="textColS" width="20%">&nbsp;</td>
										<td class="textColS" align="left" width="30%">
											<%if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){ %>
											 	<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											 	<tr><td><font class="textColS">
										           Pass</font> </td> 
										        <td>             
										        	<%=ORIGDisplayFormatUtil.displayRadioTag( XRulesConstant.ExecutionResultString.RESULT_PASS,displayMode,"webFinalResult",result ,XRulesConstant.ExecutionResultString.RESULT_PASS,XRulesConstant.ExecutionResultString.RESULT_FAIL ) %>
										        </td><td><font class="textColS">
										        	Fail</font>
										        </td><td>
										        	<%=ORIGDisplayFormatUtil.displayRadioTag( XRulesConstant.ExecutionResultString.RESULT_FAIL,displayMode,"webFinalResult",result ,XRulesConstant.ExecutionResultString.RESULT_FAIL,XRulesConstant.ExecutionResultString.RESULT_PASS ) %>
										        </td></tr></table> 
										     <%}else{ %>
										     	<%=ORIGDisplayFormatUtil.displayHTML(result)%>
										     <%} %> 
										</td>
									</tr>
									<tr><td height="20">&nbsp;
									</td></tr>
									<tr>
										<td colspan="3" align="center">
											<%=ORIGDisplayFormatUtil.displayInputTextAreaTag(comment,"webVerComment","6","40",displayMode," onKeyUp='upperCaseFiled(this)' ") %>
										</td>
									</tr>
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


 
