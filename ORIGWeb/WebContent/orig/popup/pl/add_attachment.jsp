<%@page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@page import="java.util.Vector"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil"%>
<%@page import="com.eaf.orig.shared.constant.OrigConstant"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/app_attachment.jsp");
	String contextPath = request.getContextPath();
	PLApplicationDataM plAppM = PLORIGForm.getAppForm();
	String appRecId = plAppM.getAppRecordID();
	String ERROR_MSG = (String) session.getAttribute("ATTACH_ERROR_MSG");
	session.removeAttribute("ATTACH_ERROR_MSG");
	String displayMode = HTMLRenderUtil.DISPLAY_MODE_EDIT; 
	//formUtil.getDisplayMode("ATTACHMENT_SUBFORM", ORIGUser.getRoles(), plApplicationDataM.getJobState(), PLORIGForm.getFormID(), searchType);
	//Vector vFileCategory=ORIGCacheUtil.getInstance().getvListboxbyFieldID(40);
%>
<link rel="StyleSheet" href="<%=request.getContextPath()%>/css/MainStylesheet.css" type="text/css">
<link rel="StyleSheet" href="<%=request.getContextPath()%>/css/easyui.css" type="text/css">
<link rel="StyleSheet" href="<%=request.getContextPath()%>/css/layout-default-latest.css" type="text/css">
<link rel="StyleSheet" href="<%=request.getContextPath()%>/css/jquery-boxy.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-ui-1.8.16.js"></script>
<div class="PanelFirst"><font color="red"><div id="errorAttachMsg"><%=HTMLRenderUtil.displayHTML(ERROR_MSG)%></div></font>	 
<form name="appFormName" method="post" enctype="multipart/form-data" action="<%=contextPath%>/PLUploadAttachmentServlet">						
	<table>
		<tbody>
			<tr valign="top">
				<td width="600" valign="top">
					<table width="600" border="0" cellpadding="0">
						<tbody>
							<tr>
								<td width="200" align="right" class="textR">&nbsp;<font
									class="font2">File Category<font color="red">*</font> :</font>
								</td>
								<td width="600" class="inputL">&nbsp;
								<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(40,plAppM.getBusinessClassId(),"","file_category_code",displayMode,"comboResize") %>								 
								</td>
								<td width="50">&nbsp;</td>
							</tr>
						</tbody>				 			 		 
					<tr>
						<td align="right" class="textR" align="right">&nbsp;File Name<font color="red">*</font> :</td>
						<td class="inputL">&nbsp;&nbsp;<input type="file" id="file_upload" name="fileName" value=""  class=""></td>	
						<td  >&nbsp;</td>				 				
					</tr>					  		 				 
	   
	     <tr>
		 <td align="right" colspan="3">
	 	<%=HTMLRenderUtil.displayButtonTagScriptAction("Attach", displayMode, "button", "btnAttach", "buttonNew", "id='btnAttach' ", "") %>
	 	<%=HTMLRenderUtil.displayButtonTagScriptAction("Close", displayMode, "button", "btnClose", "buttonNew", "id='btnClose' ", "") %>
	 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    </td>
		</tr>
	   </table>		 
		 
	</tbody>
</table>
<input type="hidden" name="appRecId" id="appRecId" value="<%=appRecId%>"> 
<input type="hidden" name="returnURL" value="<%=request.getRequestURI()%>">			 
</form></div>
<script language="javaScript">
function checkFileFormat(){
	//var uploadAttachmentForm =   document.frames["attachmentIframe"].appFormName;
	var uploadAttachmentForm = document.appFormName;
	var vfileName = uploadAttachmentForm.file_upload.value;
	if (vfileName != '') {
		var extension = vfileName.substring(vfileName.lastIndexOf('.') + 1,
				vfileName.length);
		if (extension != ''
				&& !(extension.toLowerCase() == 'jpg'
						|| extension.toLowerCase() == 'jpeg'
						|| extension.toLowerCase() == 'gif'
						|| extension.toLowerCase() == 'doc'
						|| extension.toLowerCase() == 'docx'
						|| extension.toLowerCase() == 'pdf'
						|| extension.toLowerCase() == 'txt'
						|| extension.toLowerCase() == 'xls'
						|| extension.toLowerCase() == 'xlsx'
						|| extension.toLowerCase() == 'xps'
						|| extension.toLowerCase() == 'ppt' 
						|| extension.toLowerCase() == 'pptx'
						|| extension.toLowerCase() == 'tif'
						|| extension.toLowerCase() == 'tiff')														
						) {
			$("#errorAttachMsg").html("* Extension of file must be jpg, jpeg, gif, doc, docx, xls ,xlsx, ppt, pptx, pdf, txt, xps,tif,tiff only.");
			uploadAttachmentForm.reset();
			return false;
		}

	} else {
		$("#errorAttachMsg").html("* กรุณาระบุ File Name");
		return false;
	}
	return true;
}
function upload() {
	if ($('#file_category_code').val() == ""){
		$("#errorAttachMsg").html("* กรุณาระบุ  File Category");
	} else {
		if (checkFileFormat()) {
			//var uploadAttachmentForm = document.frames["attachmentIframe"].appFormName;
			var uploadAttachmentForm = document.appFormName;
			uploadAttachmentForm.file_category_code.value = $('#file_category_code').val();
			uploadAttachmentForm.submit();
		}
	}
} 
$(document).ready(function() {	 
	$('#file_category_code').width(250);	 
	$('#btnClose').click(function() {
		window.close();
	});
	$('#btnAttach').click(function() {
		upload();
	});
});  
</script>
