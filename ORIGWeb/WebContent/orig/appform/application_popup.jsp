<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="java.util.*" %>
<%@ page import="com.eaf.orig.shared.view.form.ORIGSubForm" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" %>

<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler" />
<jsp:useBean id="formHandlerManager" scope="session" class="com.eaf.j2ee.pattern.view.form.FormHandleManager" />
<jsp:setProperty name="formHandlerManager" property="currentFormHandler" value="ORIGForm" />
<span id="errorMessage" class="TextWarningNormal"></span>
<script language="javascript">
// window.onbeforeunload = function(){
// 	if((window.event.clientX<0)||(window.event.clientY<0)){	
// 	}
// };
</Script>
<script language="JavaScript" src="../../js/template/utility.js"></script>

<%
ORIGFormHandler popupForm = (ORIGFormHandler) ORIGForm.getPopupForm();
com.eaf.j2ee.pattern.util.ErrorUtil error = new com.eaf.j2ee.pattern.util.ErrorUtil();
if(popupForm.hasErrors()) {
	Vector v = new Vector(popupForm.getErrors().values());
	System.out.println("Error Size = " + v.size());
	for(int i=0; i<v.size();i++) {
		String errorMessage = (String)v.elementAt(i);
		out.println("<span class=\"TextWarningNormal\">*&nbsp;"+errorMessage+"</span><br>");
	}
	
	popupForm.setErrors(null);
	HashMap clear = new HashMap();
}

%>
<input type="hidden" name="FORM_ID"  id="FORM_ID" value="<%=popupForm.getFormID()%>">
<table width="100%" border=0 cellpadding="0" cellspacing="0" align="left" valign="top" onkeydown="testKeyDown()">
	<tr>
		<td height="20">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">			
			<%
			// get All subForm from Hash
			// check with current tab
			// sort Subform put in Vector
			// loop include file
			com.eaf.orig.shared.utility.ORIGFormUtil formUtil=com.eaf.orig.shared.utility.ORIGFormUtil.getInstance();
			String currentTab = popupForm.getCurrentTab();
			HashMap allSubForms = popupForm.getSubForms();		
			System.out.println("allSubForms = " +allSubForms );
			System.out.println("currentTab = " +currentTab );
			Vector allIncludedFiles = formUtil.getSortedFileNameByCurrentTab(currentTab,allSubForms);
			
			System.out.println("allIncludedFiles = " +allIncludedFiles );	
			String includedFileName=null;
			String subformDesc=null;
			ORIGSubForm subForm;
			for(int i=0;i<allIncludedFiles.size();i++ ){ 
				subForm = (ORIGSubForm) allIncludedFiles.get(i);
				//includedFileName = (String)allIncludedFiles.get(i);
				includedFileName = subForm.getFileName();
				subformDesc = subForm.getSubformDesc();
				System.out.println("subForm.getSubFormID() = "+subForm.getSubFormID());
				if(com.eaf.orig.shared.constant.OrigConstant.SubformName.APPLICANT_SUBFORM.equals(subForm.getSubFormID())){
					subformDesc = "GUARANTOR_DATAIL";
				}
			%>
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, subformDesc) %> </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                              		<tr height="30"><td>
                                    
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
						<tr> <td  height="15"></td></tr>  
						<tr class="sidebar10"> <td align="center">
							<jsp:include page="<%=includedFileName%>" flush="true" /></TD>
						</tr>
					</table>
				</td></tr>
			</table>
			</td></tr>
			<%
			}// end for
			%>
			<TR>
				<TD colspan="3">
				<TABLE border=0 cellSpacing=0 cellPadding=0 width=100% bgColor=#FFFFFF height="1">
					<tr>
						<td>
							<%String menuSequence = (String)session.getAttribute("menuSequence");%> 
						</td>
						<td>
							<%if(popupForm.getButtonFile()!=null){%>
								<jsp:include page="<%=popupForm.getButtonFile()%>" flush="true" /> 
							<%}%>
						</td>
					</tr>
				</TABLE>
				</TD>
			</TR>
		</table>
		</td>
	</tr>
</table>

<SCRIPT language="JavaScript">
function refreshApp(){
	alert('refresh');
	appFormName.action.value="loadAppForm";
	appFormName.handleForm.value = "N";
	appFormName.submit();
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
window.onunload = closeApp;          
function closeApp(){
     //alert('close app');
	if(window.name == 'guarantor' && document.appFormName.closeGuarantorPop.value != "Y"){
	    // alert('Call close G');
		var form = document.appFormName;
		form.action.value = "CloseGuarantor";
		form.handleForm.value = "N";			
		form.submit();
		//window.close();
	}
}  

</SCRIPT>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>