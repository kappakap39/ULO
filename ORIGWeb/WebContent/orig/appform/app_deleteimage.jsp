<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<SCRIPT language="JavaScript">
function writeImage(){
	reWriteImage();      
    window.close();
    window.opener.setFocusAttach();		
}
</SCRIPT>
<META http-equiv="Content-Type" content="text/html; charset=WINDOWS-874">  
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>application_popup.jsp</TITLE>
</HEAD>
<BODY onload="writeImage()">
<P>Loading .. Please Wait</P>

<SCRIPT language="JavaScript">
function reWriteImage(){
//alert('reWriteImage');
<%
StringBuffer reBuf = new StringBuffer();
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
String fileName =  request.getParameter("fileName"+index+"");
String size =  request.getParameter("size"+index+"");
int sizeKB = (int)Math.round(Double.parseDouble(size)/1024);
String docType = request.getParameter("docType"+index+"");
reBuf.append("<TD>");
reBuf.append("<TABLE>");
reBuf.append("<TR><TD><CENTER>");
reBuf.append("<a href=javascript:showImage('"+docId+"')><img src="+path+" border=0></a>");
reBuf.append("</CENTER></TD></TR>");
reBuf.append("<TR><TD><input type=checkbox name='docId' value='"+docId+"'>");
reBuf.append(fileName+":"+sizeKB+" KB</TD>");
reBuf.append("</TR></TABLE>");
reBuf.append("</TD>");
reBuf.append("<input type=hidden name='docId"+index+"' value='"+docId+"'> ");
reBuf.append("<input type=hidden name='fileName"+index+"' value='"+fileName+"'> ");
reBuf.append("<input type=hidden name='size"+index+"' value='"+size+"'> ");
reBuf.append("<input type=hidden name='docType"+index+"' value='"+docType+"'>");
} // end for
reBuf.append("<input type=hidden name='loop'  value='"+count+"' id='loop'>");
reBuf.append("</TR></TABLE>");
} // end if 1
request.getSession().setAttribute("AttachFlag","Y");
%> 
var isNav=(navigator.appName.indexOf("Netscape")!=-1);
var newaa = "<%=reBuf.toString()%>";
//alert(newaa);
window.opener.document.getElementById("image_div").innerHTML = newaa ; 
//window.opener.document.appFormName.target = "_top"; 
if(window.opener.document.appFormName.imageAttachSubmit.value=="<%=ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT%>"){
window.opener.document.appFormName.submitAppBtn.disabled = false;
window.opener.document.appFormName.saveAppBtn.disabled = false;
}
window.opener.document.appFormName.cancelAppBtn.disabled = false;
window.opener.document.appFormName.attachButton.disabled = false;
window.opener.document.appFormName.delAttachButton.disabled = false;
window.close();  
}
</SCRIPT>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>
</BODY>
</HTML>