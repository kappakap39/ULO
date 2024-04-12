<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.BankDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>


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
	String displayMode = formUtil.getDisplayMode("FINANCE_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	ORIGUtility utility = new ORIGUtility();
	//Vector bankVect = utility.loadCacheByName("BankInformation");
	//Vector financeVect = utility.loadCacheByName("FinancialType");
	BankDataM bankDataM = (BankDataM) request.getSession().getAttribute("POPUP_DATA");
	if (bankDataM==null){
		bankDataM = new BankDataM();
	}
	Vector bankVect = utility.loadCacheByNameAndCheckStatus("BankInformation", bankDataM.getBankCode());
	Vector financeVect = utility.loadCacheByNameAndCheckStatus("FinancialType", bankDataM.getFinancialType());
	if(!("Y").equals(bankDataM.getOrigOwner()) && bankDataM.getOrigOwner() != null && !("").equals(bankDataM.getOrigOwner())){
		displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
	}
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	
	String branchDesc = null;
	String disableBranch = "";
	if(ORIGUtility.isEmptyString(bankDataM.getBankCode())){
		branchDesc = "";
		disableBranch = "disabled";
	}else{
		branchDesc = cacheUtil.getORIGCacheDisplayNameFormDB(bankDataM.getBranchCode(), "Branch", bankDataM.getBankCode());
	}
	
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
	String disabledBtn = "";
	if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
		disabledBtn = "disabled";
	}
	String sDate;

%>
<script language="JavaScript" src="naosformutil.js"></script>
<script language="JavaScript" src="keypress.js"></script>
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
			                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "FINANCE")%> </td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<input type="button" name="saveBnt" value="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %>" onClick="javascript:mandatoryPopupField('<%=personalInfoDataM.getCustomerType()%>','<%=OrigConstant.PopupName.POPUP_FINANCE%>')" class="button" <%=disabledBtn %>>
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
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FINANCIAL_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","type")%></Font> :</td>
												<td class="inputCol" width="35%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(financeVect,bankDataM.getFinancialType(),"type",displayMode,"style=\"width:97%;\" ") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "ACCOUNT_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","account_no")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(bankDataM.getAccountNo(),displayMode,"","account_no","textbox","","16") %></td>
												<% 
													if(bankDataM.getOpenDate()==null){
														sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
													}else{
														sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getOpenDate()));
													}
												%>												
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BANK") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","bank")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(bankVect,bankDataM.getBankCode(),"bank",displayMode,"onChange=\"javascript:setBranch(this.value,'branch','branch_desc')\" style=\"width:97%;\" ") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","amount")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(bankDataM.getAmount()),displayMode,"","amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" ","16") %></td>
												<% 
													if(bankDataM.getExpiryDate()==null){
														sDate="";
													}else{
														sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(bankDataM.getExpiryDate()));
													}
												%>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BRANCH") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","branch")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(bankDataM.getBranchCode(),displayMode,"5","branch","24","branch_desc","textbox",disableBranch,"3","...","button_text","LoadBranch",new String[] {"branch", "bank"},disableBranch,branchDesc,"Branch") %></td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "OPEN_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","open_date")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","open_date","textbox","right","onblur=\"javascript:checkDate('open_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FINANCE_POPUP","expiry_date")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","expiry_date","textbox","left","onblur=\"javascript:checkDate('expiry_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\"") %></td>
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

<input type="hidden" name="finance_seq" value="<%=bankDataM.getSeq() %>">
<script language="JavaScript">
function saveData(customerType,popupType){
		form = document.appFormName;
		form.action.value = "SaveFinance";
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