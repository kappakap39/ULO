<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" %>
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
    ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
%>
<TABLE height=1 cellSpacing=0 cellPadding=0 width="50%" bgColor=#FFFFFF border=0 align="right">   
	<TR><TD height="10"></TD></TR>
    <TR>
    	<td width="65%">
		<TD>
			<INPUT class="button" onclick="javascript:mandatoryField('<%=customerType %>', '<%=userRole %>', '<%=popupForm.getFormID() %>', 'Y')" type="button" 
					value=" <%=MessageResourceUtil.getTextDescription(request, "SAVE") %>  " name="saveGuarantorBtn" <%=disabledBtn %>>
		</td><td>
			<INPUT class="button" onclick="javascript:closeGuarantor()" type="button" value=" <%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>  " name="closeAppBtn"> 
		</TD>
		<td width="3%"></td>
	</TR>
	<TR><TD height="10"></TD></TR>
</TABLE>
<input type="hidden" name="closeGuarantorPop" value="N">
<input type="hidden" name="searchType">
<input type="hidden" name="role">
<input type="hidden" name="requestID" value="<%=ORIGForm.getAppForm().getRequestID() %>"> 
<input type="hidden" name="attachID" value="<%=ORIGForm.getAppForm().getAttachId() %>">
<input type="hidden" name="exceptionFlag" value="N">
<script language="JavaScript">
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
function submitApp(userRole){
//alert(userRole);
	var form = document.appFormName;
	form.action.value = "SaveSupplementaryCardPopup";
	form.handleForm.value = "Y";
	form.submit(); 
	disableFields(form);
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
</script>