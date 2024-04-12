<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache,no-store");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ taglib uri="/WEB-INF/lib/JStartTagLib.tld" prefix="taglib"%>
<%@ page import="java.util.*"%>
<%@ include file="new_screen_definitions.jsp"%>
<HTML>
<HEAD>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<script language="JavaScript">
window.history.forward(-1);
var winOpener; 
if(window.opener!=null){
	winOpener = window.opener;
}else{
	winOpener = window.dialogArguments;
}
checkFrame();
function checkFrame(){
	if(winOpener==null){
		if(parent.frames[1]==null){
				window.location="Frameset.jsp";
		}
	}
}
function testKeyDown(){
	var keyCode = window.event.keyCode;
	var f_1 = 112;
	var f_2 = 113;
	var f_3 = 114;
	var f_4 = 115;
	var f_5 = 116;
	var f_6 = 117;
	var f_7 = 118;
	var f_8 = 119;
	var f_9 = 120;
	var f_10 = 121;
	var f_11 = 122;
	var f_12 = 123;
	var s = 83;
	var next = 190;
	var prev = 188;
	var enter = 13;
	var f = 70 ;
	if(keyCode==f_5){
	  	window.event.cancelBubble = true;
		window.event.returnValue = false;
		window.event.keyCode = false; 
	}
}
// BEGIN MODAL DIALOG CODE (can also be loaded as external .js file)
var Nav4 = ((navigator.appName == "Netscape") && (parseInt(navigator.appVersion) == 4)); 
var dialogWin = new Object(); 
function openDialog(url, width, height, returnFunc, args){
	if (!dialogWin.win || (dialogWin.win && dialogWin.win.closed)) {
		dialogWin.returnFunc = returnFunc;
		dialogWin.returnedValue = "";
		dialogWin.args = args;
		dialogWin.url = url;
		dialogWin.width = width;
		dialogWin.height = height;
		dialogWin.name = "openDialog";//(new Date()).getSeconds().toString()
		if (Nav4) {
			dialogWin.left = window.screenX + 
			   ((window.outerWidth - dialogWin.width) / 2);
			dialogWin.top = window.screenY + 
			   ((window.outerHeight - dialogWin.height) / 2);
			var attr = "screenX=" + dialogWin.left + 
			   ",screenY=" + dialogWin.top + ",resizable=no, scrollbars=1 ,width=" + 
			   dialogWin.width + ",height=" + dialogWin.height;
		} else {
			dialogWin.left = (screen.width - dialogWin.width) / 2;
			dialogWin.top = (screen.height - dialogWin.height) / 2;
			var attr = "left=" + dialogWin.left + ",top=" + 
			   dialogWin.top + ",resizable=no, scrollbars=1,width=" + dialogWin.width + 
			   ",height=" + dialogWin.height;
		}		
		dialogWin.win=window.open(dialogWin.url, dialogWin.name, attr);
		dialogWin.win.focus();
	} else {
		dialogWin.win.focus();
	}
}
function deadend() {
	if (dialogWin.win && !dialogWin.win.closed) {
		dialogWin.win.focus();
		return false;
	}
} 
var IELinkClicks;
function disableForms() {    
	IELinkClicks = new Array();
	for (var h = 0; h < frames.length; h++) {
		for (var i = 0; i < frames[h].document.forms.length; i++) {
			for (var j = 0; j < frames[h].document.forms[i].elements.length; j++) {
				frames[h].document.forms[i].elements[j].disabled = true;
			}
		}
		IELinkClicks[h] = new Array();
		for (i = 0; i < frames[h].document.links.length; i++) {
			IELinkClicks[h][i] = frames[h].document.links[i].onclick;
			frames[h].document.links[i].onclick = deadend;
		}
	}
}
function enableForms() {
	for (var h = 0; h < frames.length; h++) {
		for (var i = 0; i < frames[h].document.forms.length; i++) {
			for (var j = 0; j < frames[h].document.forms[i].elements.length; j++) {
				frames[h].document.forms[i].elements[j].disabled = false;
			}
		}
		for (i = 0; i < frames[h].document.links.length; i++) {
			frames[h].document.links[i].onclick = IELinkClicks[h][i];
		}
	}
} 
function blockEvents() {
	if (Nav4) {
		window.captureEvents(Event.CLICK | Event.MOUSEDOWN | Event.MOUSEUP | Event.FOCUS);
		window.onclick = deadend;
	} else {
		disableForms();
	}
	window.onfocus = checkModal;
}
function unblockEvents() {
	if (Nav4) {
		window.releaseEvents(Event.CLICK | Event.MOUSEDOWN | Event.MOUSEUP | Event.FOCUS);
		window.onclick = null;
		window.onfocus = null;
	} else {
		enableForms();
	}
}
function checkModal() {
	if (dialogWin.win && !dialogWin.win.closed) {
		dialogWin.win.focus();	
	}
} 
function setPrefs() { 
	document.returned.searchURL.value = dialogWin.returnedValue;
} 
function applySettings(){
	if (document.returned.searchURL.value) {
		location.href = "main.html" + document.returned.searchURL.value;
	}
}
/**#SeptemWi CloseWindowAction*/
function CloseWindowAction(){
   if (event.clientY < 0){
   		try{
   			LogicManualDisplay();
   		}catch(e){}
   }
}
</SCRIPT>

<link rel="StyleSheet" href="css/jquery-ui-1.8.18.custom.css" type="text/css">
<link rel="StyleSheet" href="css/MainStylesheet.css" type="text/css">
<link rel="StyleSheet" href="css/layout-default-latest.css" type="text/css">
<link rel="StyleSheet" href="css/jquery-boxy.css" type="text/css">

<script language="JavaScript" src="js/template/utility.js"></script>
<script language="JavaScript" src="DateFormat.js"></script>
<script language="JavaScript" src="naosformutil.js"></script>

<script language="javascript" src="ssnformat.js"></script>  
<script language="javascript" src="datarequestor.js"></script>
<script language="javascript" src="keypress.js"></script>
<script language="JavaScript" src="date-picker.js"></script>
</HEAD>

<BODY id="AvaleTemplate" class="AvaleTemplate" leftMargin="0" topMargin="0" marginheight="0" marginwidth="0"  onClick="checkModal()" onFocus="checkModal()" onbeforeunload="CloseWindowAction()" onkeydown="testKeyDown()">
<script type="text/javascript" src="js/template/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/template/jquery-ui-1.8.16.js"></script>
<script type="text/javascript" src="js/template/jquery.layout-latest.js"></script>
<script type="text/javascript" src="js/template/jquery-ui-latest.js"></script>	
<script type="text/javascript" src="js/template/jquery.smoothzoom.js"></script>
<script type="text/javascript" src="js/template/jquery.mousewheel.js"></script>
<script type="text/javascript" src="js/template/jquery.blockUI.js"></script>
<script type="text/javascript" src="js/template/jquery-boxy.js"></script>
<script type="text/javascript" src="js/template/slimScroll.js"></script>
<script type="text/javascript" src="js/template/ajaxMaster.js"></script>

<jsp:include page="message/constant-javascript.jsp" flush="true" />
 
<script type="text/javascript" src="js/template/ulo.display.js"></script>
<script type="text/javascript" src="js/template/ulo.validate.form.js"></script>
<script type="text/javascript" src="js/template/ulo.date.js"></script>
<script type="text/javascript" src="js/template/ulo.ajax.js"></script>
<script type="text/javascript" src="js/template/DisableInvalidKey.js"></script>

<form name="appFormName" id="appFormName" class="appFormName" method="post" action="FrontController">
<input type="hidden" name="action" id="action" value=""> 
<input type="hidden" name="handleForm" id="handleForm" value="Y"> 
<input type="hidden" name="page" id="page" value="">
<input type="hidden" name="tab_ID" id="tab_ID" value="">
<input type="hidden" name="processType" id="processType" value="SaveApplication">
<input type="hidden" name="formID" id="formID" value="">
<input type="hidden" name="currentTab" id="currentTab"  value="">
<input type="hidden" name="flagEventClose" id="flagEventClose" value="">
<input type="hidden" name="menuSequence" id="menuSequence" value="">
<input type="hidden" name="renderPage" id="renderPage" value="">
<TABLE id="avaleTable" border="0" width="100%"  height="100%" cellpadding="0" cellspacing="0"> 
    <TBODY>		
        <TR>
            <TD valign="top" align="left" width="100%">
<%	
		// Warning Expire Password
		String expireErr = (String)request.getSession().getAttribute("pwdWarnning");
		if((expireErr!=null)&& (!expireErr.equals(""))) {	
			out.println("<font color=red>*&nbsp;warning : "+expireErr+"</font>");
		}	
		logger.debug("[HtmlBody]="+pageContext.getRequest().getAttribute("HtmlBody"));	
%>			
            			<taglib:insert parameter="HtmlBody" direct="false"/>
					
            </TD>
        </TR>
    </TBODY>
</TABLE>
</form>
</BODY>
<script type="text/javascript" src="js/template/ulo.control.engine.js"></script>
<script language="JavaScript">
function copyForm(form, formAction){
	var data = "";
	if (document.layers){
	    document.layers.popupLayer.document.write(data);
	    document.layers.popupLayer.document.close();
	}else{
		if (document.all){
	      popupLayer.innerHTML = data;
	     }
	}
	var inter = "'";
	document.popupForm.action=formAction; 
	count=0;
	for (var i=0; i <= form.elements.length; i++) {  
	   if(form.elements[i]!=null){       
	      count++;
	       var name = form.elements[i].outerHTML;
	   }; 
	  data = data +name;  
	}
	if (document.layers) {
	   document.layers.popupLayer.document.write(data);
	   document.layers.popupLayer.document.close();
	}else{
		if(document.all) {
	    	popupLayer.innerHTML = data;
	      }
	} 
}
function checkSessionAlive(){
	try{
		var req = new DataRequestor();
		//var url = action;
		req.getURL("CheckSessionAliveServlet");
		req.addArg(_POST, "dummy", "1");
		req.onload = function (data, obj) {
							if(data!=null && "false" == data){
								window.top.status = "This page has been expired. Please close this window and login again on new window.";
								if(confirm("This page has been expired. Please close this window and login again on new window.")){
									window.top.close();
								}
							}else{
								window.top.status = "";
							}
						};
		req.onfail = function (status) {
						window.top.status = "Connection has problem.";
                    };
	}catch(exception){
	}
}
function logoutProcess(){
	appFormName.action.value="NaosUserLogout";
	appFormName.handleForm.value = "N";
	appFormName.submit();
}
</script>
</HTML>