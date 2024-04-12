<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.OtherNameDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>


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
	String displayMode = formUtil.getDisplayMode("OTHERNAME_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	OtherNameDataM otherNameDataM = (OtherNameDataM) request.getSession().getAttribute("POPUP_DATA");
	System.out.print("<<<page othername_popup.jsp>>> otherNameDataM ----> = " + otherNameDataM);
	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	
	System.out.println(">>otherNameDataM.getTitleName()="+otherNameDataM.getTitleName());
	String titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", otherNameDataM.getTitleName());
	
	//Vector positionVect = utility.loadCacheByName("Position");
	
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
	
	Vector occupationVect = utility.loadCacheByName("Occupation");
	Vector positionVect = new Vector();
	if(!ORIGUtility.isEmptyString(otherNameDataM.getOccupation())){
		positionVect = cacheUtil.getPositionByOccupation(otherNameDataM.getOccupation());
	}
	
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
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
			                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "OTHERNAME")%> </td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryPopupField('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.PopupName.POPUP_OTHER_NAME%>')" class="button" <%=disabledBtn %>>
			                                    </td><td>
			                                    <%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "CLOSE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "closeBnt", "button", "onClick=\"javascript:closePopup()\"", "") %>
			                                    </td></tr>
			                                </table>
			                            </td></tr>	
			                            </table>
			                        </td></tr>
			                        <tr> <td  height="15"></td></tr>  
									<tr class="sidebar10"> <td align="center">
										<table cellpadding="0" cellspacing="0" width="100%" align="center">
											<tr>
												<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OTHER_NAME_POPUP","citizen_id")%></Font> :</td>
												<td class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(otherNameDataM.getCitizenId(),displayMode,"","citizen_id","textbox","","15") %></td>
												<td class="textColS" width="15%"><%=MessageResourceUtil.getTextDescription(request, "NAME_THAI") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OTHER_NAME_POPUP","name")%></Font> :</td>
												<td class="inputCol" width="55%">
													<table cellpadding="0" cellspacing="0" width="100%" align="center">
														<tr><td width='170'>
															<%=ORIGDisplayFormatUtil.displayPopUpOneTextBoxTagScriptAction(titleThai,displayMode,"7","title_thai_desc","textbox","","","...","button_text","LoadTitleThai","title_thai","Title","title_eng_desc","title_eng") %>
														</td><td width='150'>
															<input type="hidden" name="title_thai" value="<%=ORIGDisplayFormatUtil.displayHTML(otherNameDataM.getTitleName()) %>">	
															<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(otherNameDataM.getName(),displayMode,"","name","textbox","","60") %>
														</td><td width='150'>		
															<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(otherNameDataM.getLastName(),displayMode,"","last_name","textbox","","50") %>
														</td>
														<td>&nbsp;</td></tr>
													</table>
												</td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OTHER_NAME_POPUP","occupation")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(occupationVect,otherNameDataM.getOccupation(),"occupation",displayMode,"onChange=\"javascript:parseOccupationToPosition(this.value,'PositionId','position'); \"") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "POSITION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OTHER_NAME_POPUP","position")%></Font> :</td>
												<td class="inputCol" id="PositionId"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,otherNameDataM.getPosition(),"position",displayMode,"") %></td>
											</tr>
											<tr>
												<td class="textColS" ><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"OTHER_NAME_POPUP","description")%></Font> :</td>
												<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(otherNameDataM.getDescription(),displayMode,"","description","textbox","","100") %></td>		
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

<input type="hidden" name="seq" value="<%=otherNameDataM.getSeq() %>"> 
<script language="JavaScript">
function saveData(customerType,popupType){
		form = document.appFormName;
		form.action.value = "SaveOtherName";
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
