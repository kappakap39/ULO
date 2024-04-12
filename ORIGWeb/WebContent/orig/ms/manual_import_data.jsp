<%@page import="com.eaf.orig.shared.constant.OrigConstant.ExcelType"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM"%>
<%@ page import="com.eaf.orig.shared.dao.utility.OrigApplicationUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.cache.CacheDataInf"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil" %>
<%@ page import="com.eaf.orig.shared.model.InterfaceImportResponseDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>

<!-- <script language="javascript"> -->
<!-- 	$(document).ready(function (){ -->
<!-- 		$('#container').layout(); -->
<!-- 	}); -->
	
<!-- </script> -->
<%  org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/ms/import_credit_line_data.jsp");
    log.debug("import_credit_line_data");	    
    OrigApplicationUtil appUtil = OrigApplicationUtil.getInstance();
    ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
    
    InterfaceImportResponseDataM responseDetailM = null;
    
    Vector<CacheDataInf> interfaceTypeVect = cacheUtil.getNaosCacheDataMs(OrigConstant.BusClass.FCP_ALL_ALL,OrigConstant.fieldId.INTERFACE_TYPE);
    
    log.debug("interfaceTypeVect.size"+interfaceTypeVect.size());
	if(request.getSession().getAttribute("IMPORT_RESPONSE") != null){
		responseDetailM = (InterfaceImportResponseDataM) request.getSession().getAttribute("IMPORT_RESPONSE");
		request.getSession().removeAttribute("IMPORT_RESPONSE");
	}
	String responseDetail = "";
	String importFileLink = "";
	String attachFileLink = "";
	String approveFileDisable = "disabled";
	if(responseDetailM != null){
		if(responseDetailM.getResponseDetail() != null){
			responseDetail = responseDetailM.getResponseDetail();
		}
		if(responseDetailM.getImportFileName() != null){
			importFileLink = "<a onclick=\"downLoadFile('" + responseDetailM.getImportFileName() + "')\" href=\"#\">"+ responseDetailM.getImportFileName() +"</a>";
		}
		if(responseDetailM.getAttachFileName() != null){
			attachFileLink = "<a onclick=\"downLoadFile('" + responseDetailM.getAttachFileName() + "')\" href=\"#\">"+ responseDetailM.getAttachFileName() +"</a>";
		}
		if(!OrigConstant.InterfaceType.INCREASE_CREDIT_LINE.equals(responseDetailM.getInterfaceType())){
			approveFileDisable = "disabled";
		}else{
			approveFileDisable = "";
		}
	}else{
		responseDetailM = new InterfaceImportResponseDataM();
	}
	System.out.println("@@@ responseDetail:"+responseDetail);
%>
<script language="javascript">
	$(document).ready(function (){
		$('#container').css({
			'height': $(window).height(),
			'width':'100%'
		});
		$('#container').layout();
	});
</script>
<div id="container">
	<div class="pane ui-layout-center">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id ="importfilediv">
  <tr height="25">
    <td class="bg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr height="25">
          <td valign="top" class="bgmenuright"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr height="25">
                <td width="16">&nbsp;</td>
                <td valign="top">
                	<!--<form name="importForm" id="importForm" method="post" enctype="multipart/form-data" action="">-->
                	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr height="25">
                      <td class="sidebar87"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr height="25">
                            <td><span class="BigtodotextGreenLeft">Import Data</span>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="sidebar10"> 
                                <tr height="25">
                                  <td>
                                    <fieldset class="field-set">
                                    <legend>Import</legend>
                                    <input type="hidden" name="requestType" id="requestType" value="<%=OrigConstant.ExcelType.IMPORT_CREDIT_LINE_DATA%>">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
                                      <tr height="25">
                                        <td width="30%" class="textColS"><%=PLMessageResourceUtil.getTextDescription(request, "FILE_TYPE")%><span style="color: red">*</span> :</td>
                                        <td width="35%" class="inputCol"><div id="interfaceTypeDiv"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(interfaceTypeVect,responseDetailM.getInterfaceType(),"interface_type",HTMLRenderUtil.DISPLAY_MODE_EDIT," onChange='refreshImportFile()' style='width:300px' ")%></div></td>
                                        <td width="35%" class="textColS"></td>
                                      </tr>
                                      <tr height="25">
                                        <td width="30%" class="textColS"><%=PLMessageResourceUtil.getTextDescription(request, "FILE_IMPORT")%><span style="color: red">*</span> :</td>
                                        <td width="35%" class="inputCol"><div id="creditDiv"><input type="file" name="fileNameCreditLine" id="fileNameCreditLine"></div></td>
                                        <td width="35%" class="textColS">
                                        	<div align="left" id="div_import_file_name"><%=importFileLink%></div>
                                        </td>
                                      </tr>
                                      <tr height="25">
                                        <td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "FILE_ATTACH")%><span style="color: red" id="approve_star"></span> :</td>
                                        <td class="inputCol"><div id="approveDiv"><input type="file" name="fileNameApprove" id="fileNameApprove" <%=approveFileDisable%>></div></td>
                                        <td class="textColS">
                                        	<div align="left" id="div_approve_file_name"><%=attachFileLink%></div>
                                        </td>
                                      </tr>
                                      <tr height="25"><td colspan="3"></td></tr>
                                      <tr height="25">
                                        <td colspan="3" align="center" >
                                        	<span id="div_import_button"><input type="button" name="importBT" value="Import" class="button" onclick="importExcel2(this.form)">&nbsp;&nbsp;</span>
                                        	<input type="button" name="clearFile" value="Cancel" class="button" onclick="cancel()">&nbsp;&nbsp;
                                        	<input type="button" name="deleteFile" value="Delete" class="button" onclick="confirmDelete()">
                                        </td>
                                      </tr>
                                      <tr height="25"><td colspan="3" align="left"><div id="div_response"><%=responseDetail%></div></td></tr>
                                    </table>
                                    </fieldset></td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                  </table>
                  </td>
              </tr>
              <tr height="25">
                <td>&nbsp;</td>
                <td valign="top">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<script type="text/javascript">
function validateInputFile(){
	var fileNameCreditLine = appFormName.fileNameCreditLine.value;
	var fileNameApprove = appFormName.fileNameApprove.value;
	
	var interfaceType = $('#interface_type').val();
	if(interfaceType == undefined || interfaceType == ""){
		alertMassage('Please select Interface type');
		return false;
	}else if("<%=OrigConstant.InterfaceType.INCREASE_CREDIT_LINE%>" == interfaceType){
		if(fileNameCreditLine == "" || fileNameApprove == "") {
			alertMassage('Please select both file before import data');
			return false;
		}
	}else{
		if(fileNameCreditLine == "") {
			alertMassage('Please select attached file before import data');
			return false;
		}
	}	
	return true;
}
function cancel(){
	$('#creditDiv').html($('#creditDiv').html());
	$('#approveDiv').html($('#approveDiv').html());
}
function checkFileType(){
    var resultFlag = true;
	var fileNameCreditLine = appFormName.fileNameCreditLine.value;
	var fileTypeCreditLine = fileNameCreditLine.substr(fileNameCreditLine.lastIndexOf(".")+1,fileNameCreditLine.length);
	var fileNameApprove = appFormName.fileNameApprove.value;
	var fileTypeApprove = fileNameApprove.substr(fileNameApprove.lastIndexOf(".")+1,fileNameApprove.length);
	var interfaceType   = appFormName.interface_type.value;
	
	if(INTERFACE_TYPE_PAYROLL != interfaceType && fileNameCreditLine != "" && "XLS"!=fileTypeCreditLine.toUpperCase() && "XLSX"!=fileTypeCreditLine.toUpperCase()){
		alertMassage('Please select <%=PLMessageResourceUtil.getTextDescription(request, "FILE_IMPORT")%> only .XLS or .XLSX');
		resultFlag = false;
	}else if(INTERFACE_TYPE_PAYROLL == interfaceType && fileNameCreditLine != "" && "TXT"!=fileTypeCreditLine.toUpperCase()){
		alertMassage('Please select <%=PLMessageResourceUtil.getTextDescription(request, "FILE_IMPORT")%> only .TXT');
		resultFlag = false;
	}
	if(fileNameApprove != "" &&
	   "GIF"!=fileTypeApprove.toUpperCase()&& 
	   "JPG"!=fileTypeApprove.toUpperCase()&& 
	   "JPEG"!=fileTypeApprove.toUpperCase()&& 
	   "DOC"!=fileTypeApprove.toUpperCase()&& 
	   "DOCX"!=fileTypeApprove.toUpperCase()&& 
	   "XLS"!=fileTypeApprove.toUpperCase()&& 
	   "XLSX"!=fileTypeApprove.toUpperCase()&& 
	   "PPT"!=fileTypeApprove.toUpperCase()&& 
	   "PPTX"!=fileTypeApprove.toUpperCase()&& 
	   "PDF"!=fileTypeApprove.toUpperCase()&& 
	   "TXT"!=fileTypeApprove.toUpperCase()&& 
	   "XPS"!=fileTypeApprove.toUpperCase()){
		alertMassage('Please select <%=PLMessageResourceUtil.getTextDescription(request, "FILE_ATTACH")%> only .GIF, .JPG, .JPEG, .DOC, .DOCX, .XLS, .XLSX, .PPT, .PPTX, .PDF, .TXT or .XPS');
		resultFlag = false;
	}
	return resultFlag;
}
function importExcel2(form){
	try{
		if(validateInputFile() && checkFileType()){
			blockScreen();
			appFormName.importBT.disabled = true;
		    form.setAttribute("action", 'OrigManualImportInfServlet');
		    form.setAttribute("method", "post");
		    form.setAttribute("enctype", "multipart/form-data");
		    form.setAttribute("encoding", "multipart/form-data");
		    $('#div_response').html('');
		    form.submit();
		}
	}catch(e){
		unblockScreen();
	}
}
function downLoadFile(fileName){
 
    var form=document.reportForm;
    var interfaceType = $('#interface_type').val();
         
   	form.action="PLDownLoadAttachmentServlet";   
 	 
   	var params="<input type=\"hidden\" name=\"fileName\" value=\""+fileName+"\"><input type=\"hidden\" name=\"interface_type\" value=\""+interfaceType+"\">";
   	document.getElementById("reportParam").innerHTML=params; 
 
   	form.submit();
 
}
function deleteFile(){
	try{
		blockScreen();
		var form=document.appFormName;
		form.setAttribute("action", 'PLDeleteAttachmentServlet');
		$('#div_response').html('');
		form.submit();
	}catch(e){
		unblockScreen();
	}
}
function confirmDelete(){
	var interfaceType = $('#interface_type').val();
	var importFileShow = document.getElementById("div_import_file_name").innerHTML;
	var attachFileShow = document.getElementById("div_approve_file_name").innerHTML;
	if(interfaceType == undefined || interfaceType == ""){
		alertMassage('Please select Interface type');
	}else if(importFileShow != "" || attachFileShow != ""){
		alertMassageYesNoFunc('<%=PLMessageResourceUtil.getTextDescription(request, "CONFIRM_DELETE_FILE")%>',deleteFile,null);
	}else{
		alertMassage('No file for delete');
	}
	
}

function refreshImportFile(){
	//alert('reCalDecisionILog');
	var interfaceType = $('#interface_type').val();
	if(interfaceType != "<%=OrigConstant.InterfaceType.INCREASE_CREDIT_LINE%>"){
		appFormName.fileNameApprove.disabled = true;
		$('#approve_star').html('');
	}else{
		appFormName.fileNameApprove.disabled = false;
		$('#approve_star').html('*');
	}
	var servletName = "RefreshImportFile";
	var dataString = $("#avale-obj-form *").serialize();
	ajaxDisplayElementJson(servletName,"2",dataString);
}

</script>
</div>
</div>