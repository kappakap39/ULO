<%@page import="com.eaf.core.ulo.common.performance.TraceController"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
%>
<%
	String transactionId = (String)request.getSession().getAttribute("transactionId");
	TraceController trace = new TraceController("index.jsp",transactionId);
	trace.create("index");
%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@include file="new_screen_definitions.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>UNIVERSAL LOAN ORIGINATION SYSTEM - [<%=request.getServerName() %>]</title>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<link href="images/ulo/kbank2.ico" rel="shortcut icon" />
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" />
	<link href="bootstrap/css/bootstrap-dialog.min.css" rel="stylesheet" />
	<link href="css/ulo/selectize.bootstrap3.css" rel="stylesheet" />
	<link href="css/ulo/selectize.search.css" rel="stylesheet" />
	<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" />
	<link href="metis-menu/metisMenu.min.css" rel="stylesheet" />    
	<link href="css/sb-admin-2.css" rel="stylesheet" />
	<link href="css/kasikorn.style.css" rel="stylesheet" />    
	<link href="css/ulo/jquery-ui.css" rel="stylesheet" />
	<link href="css/ulo/jquery-ui.structure.css" rel="stylesheet" />
	<link href="css/ulo/jquery-ui.theme.css" rel="stylesheet" />
	<link href="css/ulo/uploadfile.css" rel="stylesheet" />
	<link href="css/ulo/jquery-confirm.css" rel="stylesheet" />
	<link href="css/ulo/jquery-ui.theme.override.css" rel="stylesheet" />
	<link href="tooltipster/css/tooltipster.css" rel="stylesheet" />
	<link href="tooltipster/css/themes/tooltipster-shadow.css" rel="stylesheet" />
	<link href="css/ulo/ulo.theme.css" rel="stylesheet" />
	<link href="css/ulo/ulo.menu.css" rel="stylesheet" />
	<link href="css/ulo/ulo.theme.override.css" rel="stylesheet" />	
	<link href="css/ulo/ulo.theme.checkbox.css" rel="stylesheet" />
	<link href="css/digits.css" rel="stylesheet" />
	<link href="css/pace.css" rel="stylesheet" />
	<script src="js/localstorage.js"></script>
	<script src="js/log4html.js"></script>
	<script src="js/ulo/jquery-2.1.4.js"></script>
	<script src="js/ulo/jquery-ui.js"></script>
	<script src="js/ulo/jquery.resize.js"></script>
	<script src="js/ulo/jquery.form.js"></script>
	<script src="js/ulo/jquery.uploadfile.js"></script>
	<script src="js/ulo/jquery-confirm.js"></script>
	<script src="js/jquery.countdown.js"></script>
	<script src="tooltipster/js/jquery.tooltipster.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/bootstrap-dialog.min.js"></script>
	<script src="bootstrap/js/bootstrap-notify.js"></script>
	<script src="metis-menu/metisMenu.min.js"></script>	
	<script src="js/pace.js"></script>
	<script src="js/eModal.js"></script>
	<script src="js/ulo/form.ui.js"></script>
	<script src="js/ulo/form.util.js?v=001"></script>
	<script src="js/ulo/form.plugin.js"></script>
	<script src="js/ulo/form.validate.js"></script>
	<script src="js/ulo/form.data.js"></script>
	<script src="js/sb-admin-2.js"></script>
	<script src="js/ulo/selectize.js"></script>
	<script src="js/ulo/jquery.mask.js"></script>
	<script src="js/template/ulo.application.javascript.js"></script>
	<script src="js/ulo/mousetrap.js"></script>	
	<script type="text/javascript">
	var DIALOG_INFOS = {dialog:[]};
	var htmlBodyHeight = function() {
		return ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
	};
	function resizeIframe(obj){
// 	   {obj.style.height = 0;};
// 	   {obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';}
	}
	function resizeIframePage(){
		try{
	        var iframePage = document.getElementById('external-page');
	        if(iframePage != undefined){
	         resizeIframe(iframePage);
	     	}
	    }catch(e){}
	}
	function logout(){
	  window.location = '/ORIGWeb/logout.jsp';
	}
// 	$(window).on("unload", function(e){
// 		var url = '/ORIGWeb/Logout';
// 		$.post(url);
// 	});
// 	chrome.app.runtime.onLaunched.addListener(function() {
// 		chrome.app.window.onClosed.addListener(function(){
// 			var url = '/ORIGWeb/Logout';
// 			$.post(url);
// 		});
// 	});
	</script>
</head>
<body class="kstyle">
<script>
// 	PACE Settings
// 	window.Pace.options.ajax.trackMethods = ['GET','POST','PUT','DELETE'];
//  window.Pace.options.easeFactor = 0.2;
// 	Detect sidebar toggling
if (webStorage.getSessStorage().sidebarCollapsed == "true") {
// 	console.log("Collapsing sidebar");
	$('body').addClass('noTransition').addClass('sb-l-m');
	setTimeout(function() {$('body').removeClass('noTransition');}, 200);
}
</script>
<div id="wrapper">
	<jsp:include page="header.jsp" />
	<jsp:include page="left.jsp" />
	<footer id="footer-nav">
	<article id="footer-info">
		<%=LabelUtil.getText(request,"FOOTER_TEXT")%>
		<span style="position: fixed; float: right; right: 10px;"><%=SystemConfig.getGeneralParam("ENVIRONMENT_VERSION")+"&nbsp;"%></span>
	</article>
	</footer>
	<div id="page-wrapper">
		<jsp:include page="HtmlBoby.jsp" />
	</div>	
</div>
<script type="text/javascript">
//Tooltipster(Tooltip)
$(function() {
	try{reloadTooltip();}catch(e){}
	
	//Prevent double click for button
	$('button[onclick]').dblclick(function(e){
		e.preventDefault();
		e.stopPropagation();
		return false;
	});
});
//#rawi fix memory leak jquery event
$(function(){
	if(window.attachEvent && !window.addEventListener){
		window.attachEvent("onunload", function() {
			for ( var id in jQuery.cache ) {
				if ( jQuery.cache[ id ].handle ) {
					// Try/Catch is to handle iframes being unloaded, see #4280
					try {
						jQuery.event.remove( jQuery.cache[ id ].handle.elem );
					} catch(e) {}
				}
			}
			// The following line added by me to fix leak!
			window.detachEvent( "onload", jQuery.ready );
		});
	}
	var rx = /INPUT|SELECT|TEXTAREA/i;
	$(document).bind("keydown keypress",function(e){
		if(e.which == 8){
			if(!rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly){
				e.preventDefault();
			}
		}
	});
});
$(window).on("message", function(e) {
	try{
		var objectData = e.originalEvent.data;
		var serviceId = objectData.serviceId;
		var data = objectData.data;
		try{
			var onMessageAction = eval(serviceId+'OnMessageActionJS');
			onMessageAction(data);
		}catch(e){}
	}catch(e){}	
});
$(function(){

});
function disableF5(e) { 
	if ((e.which || e.keyCode) == 116) e.preventDefault(); 
};
$(window).bind("keydown", disableF5);
window.onload = function() {
    document.addEventListener("contextmenu", function(e){
      e.preventDefault();}, false);};
</script>
</body>
</head>
</html>
<%
	trace.end("index");
	trace.trace();
%>