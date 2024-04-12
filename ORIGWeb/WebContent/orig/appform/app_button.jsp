<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />

<%
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	ORIGUtility utility = new ORIGUtility();
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
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
	String customerType = personalInfoDataM.getCustomerType();
	
	String menu = (String)request.getSession(true).getAttribute("PROPOSAL_MENU");
	String userRole = (String) ORIGUser.getRoles().elementAt(0);
	String searchType = (String)request.getSession().getAttribute("searchType");
	String disabledImg = "";
	if(ORIGForm.getAppForm().getRequestID() == null){
		disabledImg = ORIGDisplayFormatUtil.DISABLED;
	}
	System.out.println("ORIGForm.getAppForm().getRequestID() = "+ORIGForm.getAppForm().getAttachId());
	
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("APPLICANT_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
	if(OrigConstant.ROLE_PD.equals(userRole)){
		displayMode = formUtil.getDisplayMode("PD_RESULT_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
		if(personalInfoDataM.getPersonalType().equals(OrigConstant.PERSONAL_TYPE_GUARANTOR)){
			displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		}
	}else if(OrigConstant.ROLE_XCMR.equals(userRole)){
		displayMode = formUtil.getDisplayMode("CMR_DECISION_SUBFORM", ORIGUser.getRoles(), ORIGForm.getAppForm().getJobState(), ORIGForm.getFormID(), searchType);
	}
	String disabledBtn = "";
	if(!displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){
		disabledBtn = ORIGDisplayFormatUtil.DISABLED;
	}
	ApplicationDataM applicationDataM=ORIGForm.getAppForm();
	String cancelApp = (String)request.getSession().getAttribute("cancelApp");
    boolean showCancelApplication=false;
	if(OrigConstant.ORIG_FLAG_Y.equals(cancelApp)){
  	 String jobState=applicationDataM.getJobState();
  	 if(jobState!=null){
    showCancelApplication=true;
      String[] jobStateEndFlow=OrigConstant.JOBSTATE_ENDFLOW;
      for(int i=0;i<jobStateEndFlow.length;i++){
        if(jobState.equals(jobStateEndFlow[i])){
            showCancelApplication=false;
            break;
        }
      }      
      }
    }
%>
<%if(!OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){ %>
	<TABLE height="50" cellSpacing="0" cellPadding="0" width="100%" bgColor=#FFFFFF border="0" align="center">   
		<TR><TD height="10"></TD></TR>
	    <TR>
	    	<td width="40%">&nbsp;</td>
	    	<td width="60%">
	    		<TABLE cellSpacing="0" cellPadding="0" width="100%" bgColor=#FFFFFF border="0" align="right">
	    			<tr>
						<TD>
							<INPUT class="button_text" onclick="javascript:viewImg()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "VIEW_IMAGE") %>" name="viewImageBtn" <%=disabledImg %> 
								onMouseOver="<%=tooltipUtil.getTooltip(request,"view_image")%>">
						</td>
						<td>
							<INPUT class="button_text" onclick="javascript:loadNotepad()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "NOTEPAD") %>" name="notepadBtn"  
								onMouseOver="<%=tooltipUtil.getTooltip(request,"notepad")%>">
						</td>
						<td>
							<INPUT class="button_text" onclick="javascript:loadDocumentlist()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "DOCUMENT_LIST") %>" name="docListBtn" 
								onMouseOver="<%=tooltipUtil.getTooltip(request,"doclist")%>">
						</td>
						<td>
							<INPUT class="button_text" onclick="javascript:loadActionFlow()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "ACTION_FLOW") %>" name="actionFlowBtn" 
								onMouseOver="<%=tooltipUtil.getTooltip(request,"action_flow")%>">
						</td>
						<td>
							<INPUT class="button_text" onclick="javascript:loadTailorDocumentlist()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "TAILOR_MADE_LST") %>" name="tailorMadeBtn">
						</TD>
					</tr>
				</TABLE>
			</td>
		</tr>
		<TR><TD height="10"></TD></TR>
		<tr>
			<td>&nbsp;</td>
			<TD>
			<!-- 
				<TABLE cellSpacing="0" cellPadding="0" width="50%" bgColor=#FFFFFF border="0" align="right">
	    			<tr>
	    				<td>
						     <%if(showCancelApplication){%>
						     <INPUT class="button"  type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL_APP") %>" name="cancelApplication" onclick="javascript:ManualCancelApplication();">
						     <%}%>
						</td>
						<td>
						    <%if(OrigConstant.SEARCH_TYPE_REVERSE.equals(searchType)){ %>
						    <INPUT class="button"  type="button" value="<%=MessageResourceUtil.getTextDescription(request, "REVERSE") %>" name="reverseApplication" onclick="javascript:reverseApp();">
						    <%}%>
						 </td>   
			<% 
								String fromMultiApp = (String)request.getSession().getAttribute("fromMultiApp");							
			%>			
						<td></td>
						<td>    
							<INPUT class="button" onclick="javascript:mandatoryField('<%=customerType%>', '<%=userRole%>')" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "SEND") %>" name="submitAppBtn" <%=disabledBtn %>>
						</td>
						<td>
							<INPUT class="button" onclick="javascript:mandatoryFieldSaveNewApp('<%=customerType %>', '<%=userRole %>', 'save')" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "DRAFT") %>" name="saveAppBtn" <%=disabledBtn %>> 
						</td>
						<td>
							<%if(menu!=null&&menu.equals("Y")){%>
								<INPUT class="button" onclick="javascript:cancelClaimApp()" type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" name="cancelClaimAppBtn">
							<%}else{  
								String cancelOnClick = "onclick=\"javascript:cancelClaimApplication()\"";
								if ("Y".equals(fromMultiApp)) {
									cancelOnClick ="";
								}					
			%>
								<INPUT class="button" <%=cancelOnClick%> type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" name="cancelClaimAppBtn">
							<%} %>
							
						</TD>
					</tr>
				</TABLE> -->
			</TD>
		</TR>
		<TR><TD height="10"></TD></TR>
	</TABLE>
<!-- 
<%}else{%>
	<TABLE height="30" cellSpacing="0" cellPadding="0" width="100%" bgColor=#FFFFFF border="0" align="center">   
		<TR><TD height="10"></TD></TR>
	    <TR>
	    	<td width="83%">
			<TD width="7%">
				<INPUT class="button" onclick="javascript:mandatoryFieldGuarantor('<%=customerType %>', '<%=userRole %>')" type="button" value=" <%=MessageResourceUtil.getTextDescription(request, "SAVE") %> " name="saveGuarantorBtn" <%=disabledBtn %>>
			</td>
			<td width="7%">
				<INPUT class="button" onclick="javascript:closeGuarantor()" type="button" value=" <%=MessageResourceUtil.getTextDescription(request, "CLOSE") %> " name="closeAppBtn"> 
			</TD>
			<td width="3%"></td>
		</TR>
		<TR><TD height="10"></TD></TR>
	</TABLE>
<%} %>
 -->
<input type="hidden" name="closeGuarantorPop" value="N">
<input type="hidden" name="searchType">
<input type="hidden" name="role">
<input type="hidden" name="requestID" value="<%=ORIGForm.getAppForm().getRequestID() %>"> 
<input type="hidden" name="attachID" value="<%=ORIGForm.getAppForm().getAttachId() %>">
<input type="hidden" name="exceptionFlag" value="N">
<script language="JavaScript">
function saveApp(userRole){
	var form = document.appFormName;
	displayLoading('Processing...');
	var proposalFlag = '<%=menu%>';
	if(userRole == '<%=OrigConstant.ROLE_DE%>'){
		form.action.value = "SaveApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_CMR%>'){
		form.action.value = "CMRSaveApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_UW%>'){
		if(proposalFlag=='Y'){
			form.action.value = "ProposalSaveApplication";
		}else{
			form.action.value = "UWSaveApplication";
		}
	}else if(userRole == '<%=OrigConstant.ROLE_PD%>'){
		form.action.value = "PDSaveApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_XCMR%>'){
		form.action.value = "XCMRSaveApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_XUW%>'){
		form.action.value = "XUWSaveApplication";
	}
	
	form.handleForm.value = "Y";
	form.submit();
	//toUppercase();
	disableFields(form);
}
function saveGuarantor(){
	var form = document.appFormName;
	form.action.value = "SaveGuarantor";
	form.handleForm.value = "Y";
	form.submit(); 
	disableFields(form);
}
function closeGuarantor(){
	var form = document.appFormName;
	form.action.value = "CloseGuarantor";
	form.handleForm.value = "N";
	form.closeGuarantorPop.value = "Y";		
	form.submit();
	//window.close(); 
}
function loadNotepad(){
	var url = "/ORIGWeb/FrontController?action=LoadNotepadPopup";
	var childwindow = window.open(url,'','width=615,height=345,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();}};	
	childwindow.onunload = function(){alert('closing');};
}
function loadDocumentlist(){
	var url = "/ORIGWeb/FrontController?action=LoadDocumentList";
	var childwindow = window.open(url,'','width=750,height=320,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();}};	
	childwindow.onunload = function(){alert('closing');};
}
function loadTailorDocumentlist(){
	var url = "/ORIGWeb/FrontController?action=LoadTailorDocumentList";
	var childwindow = window.open(url,'','width=750,height=330,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
function submitApp(userRole){
//alert(userRole);
	var form = document.appFormName;
	var proposalFlag = '<%=menu%>';
	var confirmException = true;
	if(userRole == '<%=OrigConstant.ROLE_DE%>'){
<%
	String fromMultiApp = (String)request.getSession().getAttribute("fromMultiApp");
	if ("Y".equalsIgnoreCase(fromMultiApp)) {
%>
		form.action.value = "saveAppMulti";
<% } else {%>
		form.action.value = "SubmitApplication";
<%
	}
%>
		/* if(!form.decision_de.checked){
			//Check Exception
			var form = document.appFormName;
			var req = new DataRequestor();
			var url = "CheckExceptionAppServlet";
			req.getURL(url);
			req.onload = function (data, obj) {
				if(data == '<%=ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION%>'){
					var confirmException = confirm(' Application not matched the campaign. Do you want to sent this application to Exception CMR?');
					if(confirmException){
						form.exceptionFlag.value = 'Y';
						confirmException = true;
						
						form.handleForm.value = "Y";
						displayLoading('Processing...');
						form.submit();
						disableFields(form);
						return true;
					}else{
						form.exceptionFlag.value = 'N';
						confirmException = false;
					}
				}else{
					form.exceptionFlag.value = 'N';
					confirmException = true;
					form.handleForm.value = "Y";
					displayLoading('Processing...');
					form.submit();
					disableFields(form);
				}
			}
		}else{*/
			form.handleForm.value = "Y";
			displayLoading('Processing...');
			form.submit();
			disableFields(form);
		//}
		return true;
	}else if(userRole == '<%=OrigConstant.ROLE_CMR%>'){
		form.action.value = "CMRSubmitApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_UW%>'){
		if(proposalFlag=='Y'){
			form.action.value = "ProposalSubmitApplication";
		}else{
			form.action.value = "UWSubmitApplication";
		}
		// alert("Submit UW OK");
		// return;
		//if(form.exceptionFlag.value == 'Y'){
		//	confirmException = confirm(' Application not matched the campaign. Do you want to sent this application to Exception CMR?');
		// }
	}else if(userRole == '<%=OrigConstant.ROLE_PD%>'){
		form.action.value = "PDSubmitApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_XCMR%>'){
		form.action.value = "XCMRSubmitApplication";
	}else if(userRole == '<%=OrigConstant.ROLE_XUW%>'){
		form.action.value = "XUWSubmitApplication";
	}
	
	//alert(confirmException);
	if(confirmException){
		displayLoading('Processing...');
		form.handleForm.value = "Y";
		//alert("Submit Application");
		form.submit(); 
		disableFields(form);
	}else{
		return false;
	}
}

function cancelClaimApp(){
	setPosXY(105,40);
	displayLoading('Processing...');
	var form = document.appFormName;
	form.action.value = "CancelApp";
	form.searchType.value = '<%=searchType%>';
	form.role.value = '<%=userRole%>';
	form.handleForm.value = "N";
	form.submit(); 
	disableFields(form);
}
function cancelClaimApplication(){
	setPosXY(105,40);
	displayLoading('Processing...');
	var form = document.appFormName;
	form.action.value = "CancelApp";
	form.searchType.value = '<%=searchType%>';
	form.role.value = '<%=userRole%>';
	form.handleForm.value = "N";
	form.submit(); 
	disableFields(form);
}
function viewImg(){
		var requestID = document.appFormName.requestID.value;
		var attachID = document.appFormName.attachID.value;
	 //if((document.getElementById('fileName').innerHTML != null && document.getElementById('fileName').innerHTML != '') ||(document.getElementById('requestID').innerHTML != null && document.getElementById('requestID').innerHTML != '')){
		//var url ="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/popup/view_image.jsp?requestID="+requestID+"&attachID="+attachID;
		var url ="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/openIE.jsp?url=http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/popup/view_popup_image.jsp&requestID="+requestID+"&attachID="+attachID;
		//alert(url);
		var viewPopup;
		var name = "ViewImagePopup";
		var width = screen.availWidth-10
		var height =screen.availHeight-60;
		var option = "toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width="+width+", height="+height+", top=0, left=0";
		viewPopup = window.open(url, name, option);
	//}
}
function loadActionFlow(){
	var url = "/ORIGWeb/FrontController?action=LoadActionFlowPopup";
	var childwindow = window.open(url,'','width=600,height=300,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
function checkException(){
	var form = document.appFormName;
	var req = new DataRequestor();
	var url = "CheckExceptionAppServlet";
	req.getURL(url);
	req.onload = function (data, obj) {
		if(data == '<%=ORIGWFConstant.ApplicationDecision.APPROVE_WITH_EXCEPTION%>'){
			var confirmException = confirm(' Application not matched the campaign. Do you want to sent this application to Exception CMR?');
			if(confirmException){
				form.exceptionFlag.value = 'Y';
				return 'Y';
			}else{
				form.exceptionFlag.value = 'N';
				return 'N';
			}
		}else{
			form.exceptionFlag.value = 'Y';
			return 'Y';
		}
	}
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
function reverseApp(){
	setPosXY(105,40);
	displayLoading('Processing...');
	var form = document.appFormName;
	form.action.value = "PDReverseApplication";
	form.searchType.value = '<%=searchType%>';
	form.role.value = '<%=userRole%>';
	form.handleForm.value = "N";
	form.submit(); 
	disableFields(form);
}
function ManualCancelApplication(){
   var confirmCancel = confirm('Do you want to cancel this application?');
   if(confirmCancel){
    displayLoading('Processing...');    
	var form = document.appFormName;
	form.action.value = "ManualCancelApp";
	form.searchType.value = '<%=searchType%>';
	form.role.value = '<%=userRole%>';
	form.handleForm.value = "N";
	form.submit(); 
	disableFields(form);
	}else{
	//cancelClaimApplication();
	}
}
</script>