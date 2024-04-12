<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.FeeInformationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>


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
	String displayMode = formUtil.getDisplayMode("PURCHASE_LIST_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector feeTypeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",31);
	Vector feeOptionVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",32);
	
	FeeInformationDataM feeInformationDataM = (FeeInformationDataM) request.getSession().getAttribute("FEE_INFO_POPUP_DATA");
	if(feeInformationDataM == null){
		feeInformationDataM = new FeeInformationDataM();
	}
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
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
			                          	<tr><td class="text-header-detail" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "MG_FEE_INFO") %> </td>
			                            <td width="330">
			                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
			                              		<tr height="30"><td>
			                                    	<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "SAVE"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "saveBnt", "button", "onClick=\"javascript:saveData()\"", "") %>
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
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FEE_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FEE_INFORMATION_POPUP","fee_type")%></Font> :</td>
												<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(feeTypeVect,feeInformationDataM.getFeeType(),"fee_type",displayMode,"") %></td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FEE_AMOUNT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FEE_INFORMATION_POPUP","fee_amount")%></Font> :</td>
												<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(feeInformationDataM.getFeeAmount()),displayMode,"26","fee_amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\"","") %></td>
											</tr>
											<tr>
												<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "FEE_PAYMENT_OPTION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"FEE_INFORMATION_POPUP","fee_payment_option")%></Font> :</td>
												<td class="inputCol" colspan="3"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(feeOptionVect,feeInformationDataM.getFeePaymentOption(),"fee_payment_option",displayMode,"") %></td>
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
<script language="JavaScript">
function saveData(){
	form = document.appFormName;
	form.action.value = "SaveFeeInformationPopup";
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
function popupStepInstallment(){
     form = document.appFormName;
     var vat=form.vat.value;
     var term=form.installment1.value;
     var hire_purchase_total=form.hire_purchase_total.value;
     //alert(hire_purchase_total);
    var pram="&vat="+vat+"&term="+term+"&hire_purchase_total="+hire_purchase_total;
    //alert(pram);
    var url = "/ORIGWeb/FrontController?action=LoadStepInstallmentPopup"+pram;
	var childwindow = window.open(url,'popupInstallment','width=800,height=400,top=200,left=200,scrollbars=1,status=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>