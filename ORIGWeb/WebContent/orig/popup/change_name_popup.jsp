<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ChangeNameDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="js/popcalendar-screen.js"></script>
<span id="errorMessage" class="TextWarningNormal"></span>
<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CHANGE_NAME_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ChangeNameDataM changeNameDataM = (ChangeNameDataM) request.getSession().getAttribute("POPUP_DATA");
	if(!("Y").equals(changeNameDataM.getOrigOwner()) && changeNameDataM.getOrigOwner() != null && !("").equals(changeNameDataM.getOrigOwner())){
		//displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
	}
	System.out.println("changeNameDataM="+changeNameDataM);
	System.out.println("changeNameDataM.getChangeDate()"+changeNameDataM.getChangeDate());

	String sDate;	
	String styleDisabled = "textbox";
	if(changeNameDataM.getChangeDate()==null){
		sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
	}else{
		sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(changeNameDataM.getChangeDate()));
		displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		styleDisabled = "textboxReadOnly";
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
	}
    System.out.println("change name vect size "+personalInfoDataM.getChangeNameVect().size());
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
	
	Vector v = ORIGForm.getFormErrors();
	Vector vErrorFields = ORIGForm.getErrorFields();
	System.out.println("Error Size = " + v.size());
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
	ORIGForm.setFormErrors(new Vector());
%>

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
			                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "CHANGE_NAME")%></td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<td align="right"><input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryPopupField('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.PopupName.POPUP_CHANGE_NAME%>')" class="button" <%=disabledBtn %>>
													</td><td>
													<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), "EDIT","button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %>
			                                    </td></tr>
			                                </table>
			                            </td></tr>	
			                            </table>
			                        </td></tr>
			                        <tr> <td  height="15"></td></tr>  
									<tr class="sidebar10"> <td align="center">
										<table cellpadding="0" cellspacing="0" width="100%" align="center">
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CHANGE_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CHANGE_NAME_POPUP","change_date")%></Font> :</td>
												<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","change_date",styleDisabled,"right","onblur=\"javascript:checkDate('change_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" ") %></td>
											</tr>
											<tr>
												<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "OLD_NAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CHANGE_NAME_POPUP","old_name")%></Font> :</td>
												<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(changeNameDataM.getOldName(),displayMode,"","old_name",styleDisabled,"","30") %></td>
												<td class="textColS" width="25%"><%=MessageResourceUtil.getTextDescription(request, "OLD_SURNAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CHANGE_NAME_POPUP","old_surname")%></Font> :</td>
												<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(changeNameDataM.getOldSurName(),displayMode,"","old_surname",styleDisabled,"","50") %></td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NEW_NAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CHANGE_NAME_POPUP","new_name")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(changeNameDataM.getNewName(),displayMode,"","new_name",styleDisabled,"","30") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "NEW_SURNAME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CHANGE_NAME_POPUP","new_surname")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(changeNameDataM.getNewSurname(),displayMode,"","new_surname",styleDisabled,"","50") %></td>
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

<input type="hidden" name="seq" value="<%=changeNameDataM.getSeq() %>">
<script language="JavaScript">
function saveData(customerType,popupType){
	form = document.appFormName;
	form.action.value = "SaveChangeName";
	form.handleForm.value = "N";
	form.submit();
}
function closePopup(){
	window.close();
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
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>