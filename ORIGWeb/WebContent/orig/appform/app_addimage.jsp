<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.AttachmentHistoryDataM" %>
<%@ page import="com.eaf.j2ee.system.LoadXML" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<LINK href="<%=request.getContextPath()%>/MainStyle.css" type=text/css rel=StyleSheet>
<%
System.out.println("app add image.jsp call");
ApplicationDataM appForm = ORIGForm.getAppForm();
 
	if(appForm == null){
		appForm = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
StringBuffer aaBuf = new StringBuffer();
StringBuffer reBuf = new StringBuffer();
String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("ATTACHMENT_SUBFORM", ORIGUser.getRoles(), appForm.getJobState(), ORIGForm.getFormID(), searchType);
%>
<form name="AddImageForm" method="post" enctype="multipart/form-data" action="">
<table width="100%" border="0" bgcolor="e9edf6" height="100%">
	<TBODY>
		<tr valign="top">
			<td width="100%" valign="top">
			<table width="100%" border="0" bgcolor="#e9edf6">
				<TBODY>
					<tr valign="top">
						<td width="92">&nbsp;</td>
						<td colspan="2"><font class="font2"></font></td>
						<td width="51" >&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><font class="font2"><%=MessageResourceUtil.getTextDescription(request, "FILE_NAME") %> :</font>&nbsp;</td>
						<td colspan="2"><input type="file" name="fileName" value="" onchange="checkFileFormat()"></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td colspan="2">&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td align="center" colspan="3">
						<input type="button" name="Attachement" value="<%=MessageResourceUtil.getTextDescription(request, "ATTACH") %>" onClick="upload();"> 
						<input type="submit" name="cancel" value="<%=MessageResourceUtil.getTextDescription(request, "CANCEL") %>" onClick="javascript:window.close();">
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">		
						<div id="PlsWaitMessage" style="visibility: visible"><font
							color='red' > <b>&nbsp;&nbsp;&nbsp; </b></font></div>
						<br><!--img src="/NaosSgWeb/en_US/images/progressbar.gif"-->
						</td>
						<script language="javascript" type='text/javascript'> 
				    	function hideDiv() { 
							document.getElementById('PlsWaitMessage').style.visibility = 'hidden'; 
						} 
						
						function showDiv() { 
							document.getElementById('PlsWaitMessage').innerHTML ='<font color=\'red\' >Please wait ...<b></b></font>'; 
						} 
						</script>
					</tr>
				</TBODY>
			</table>
			</td>
		</tr>
	</TBODY>
</table>
<input type="hidden" name="appRecId" value="<%=appForm.getAppRecordID()%>">
<%if(com.eaf.orig.shared.utility.ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equalsIgnoreCase(displayMode)){ %>
    <input type="hidden" name="redirectUrl" value="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/appform/app_addimage.jsp">  
<%}else{ %>
    <input type="hidden" name="redirectUrl" value="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/SaveAttachmentServlet">  
<%} %>
</form>
<script language="JavaScript">
function upload(){
	var form = document.AddImageForm;
	form.Attachement.disabled = true;
	form.cancel.disabled = true;
	//form.fileName.disabled = true;
	var filename = document.AddImageForm.fileName.value;
	//alert(filename);
	var name = filename.substring(filename.lastIndexOf('\\')+1,filename.length);	 
	//if(name.length>255){
	//	alert('File name of the attached must less than 255 character');
	//	return false;
	//}
	var form = document.AddImageForm;
	if(document.AddImageForm.fileName.value!=""){
		showDiv();
		document.AddImageForm.action = "http://<%=String.valueOf(LoadXML.getCmServerEx().get("1"))%>/<%=String.valueOf(LoadXML.getServerCmProject().get("1"))%>/UploadDoc";
		document.AddImageForm.submit(); 
		
	}else {
	  	alert('can\'t upload');
	  	form.Attachement.disabled = false;
		form.cancel.disabled = false;
		//form.fileName.disabled = false;
	}  	
	//window.close();
}

function deleteImage(){
	document.AddImageForm.action = "FrontController?action=DeleteImage&handleForm=N";
	document.AddImageForm.submit();  	
}

function checkFileFormat(){
	  var form=document.AddImageForm;
 	  var vfileName=form.fileName.value;
 	   if(vfileName!=''){
 	    var extension=vfileName.substring(vfileName.lastIndexOf('.')+1,vfileName.length);
 	    if(extension!='' && !(extension.toLowerCase()=='jpg'||extension.toLowerCase()=='jpeg'||extension.toLowerCase()=='gif'||extension.toLowerCase()=='doc' ||extension.toLowerCase()=='xls'||extension.toLowerCase()=='ppt'||extension.toLowerCase()=='pdf'||extension.toLowerCase()=='txt')){
 	       alert("Extension of file must be jpg, jpeg, gif, doc, xls, ppt,pdf, txt, only.");
 	       form.reset();
         }  
         
       } 
	}	
<%
String success = request.getParameter("success");
System.out.println("success = " +success);
System.out.println("count = " +request.getParameter("count"));
if ("0".equals(success)) {   	  
%>          
      reWriteErrMsg();      
<%	  
}else if ("1".equals(success)) {   
request.getSession().setAttribute("AttachFlag","Y");

%>
	 reWriteImage();
<%   
} 
%> 

function reWriteImage(){
<%	 
String path = request.getContextPath() +"/en_US/images/pic1.gif ";
int loop=0;
String count = request.getParameter("count");
if(count!=null && count.length()>0) 
	loop = Integer.parseInt(count);
if(loop>0){
reBuf.append("");
reBuf.append("<TABLE><TR>");
	for(int index=1; index<=loop ;index++){
String docId =  request.getParameter("docId"+index+"");
String fileName =  java.net.URLDecoder.decode(request.getParameter("fileName"+index+""));

System.out.println(".....fileName=" + fileName);

String size =  request.getParameter("size"+index+"");
int sizeKB = (int)Math.round(Double.parseDouble(size)/1024);
String docType = request.getParameter("docType"+index+"");
reBuf.append("<TD>");
reBuf.append("<TABLE>");
reBuf.append("<TR><TD><CENTER>");
reBuf.append("<a href=javascript:showImage('"+docId+"')><img src="+path+" border=0></a>");
reBuf.append("</CENTER></TD></TR>");
reBuf.append("<TR><TD><input type=checkbox name=docId value="+docId+">");
reBuf.append(fileName+":"+sizeKB+" KB</TD>");
reBuf.append("</TR></TABLE>");
reBuf.append("</TD>");
reBuf.append("<input type=hidden name=docId"+index+" value="+docId+" id=docId> ");
reBuf.append("<input type=hidden name=fileName"+index+" value=\\"+"\""+java.net.URLEncoder.encode(fileName)+"\\"+"\""+"> ");
reBuf.append("<input type=hidden name=size"+index+" value="+size+"> ");
reBuf.append("<input type=hidden name=docType"+index+" value="+docType+">");
} // end for
reBuf.append("<input type=hidden name=loop  value="+count+" id=loop>");
reBuf.append("</TR></TABLE>");
} // end if 1
%>
var isNav=(navigator.appName.indexOf("Netscape")!=-1);
var newaa = "<%=reBuf.toString()%>";
<%System.out.println(".....reBuf.toString()=" + reBuf.toString());%>

window.opener.document.getElementById("image_div").innerHTML = newaa ;
window.close();
}	
function reWriteErrMsg(){
	var objDiv = window.opener.document.getElementById("msgErr");	
	objDiv.innerHTML = '<span class=\"TextWarningNormal\">Can\'t upload file.</span>';
	window.close();
}
window.onunload = closePop;
function closePop(){
	var form = opener.document.appFormName; 
	if(window.opener.document.appFormName.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){
	form.submitAppBtn.disabled = false;
	form.saveAppBtn.disabled = false;
	}
	if(form.cancelAppBtn){
	form.cancelAppBtn.disabled = false;
	}
	form.attachButton.disabled = false;
	form.delAttachButton.disabled = false;	
}
</script>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>