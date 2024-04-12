<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="new_screen_definitions.jsp" %>
<!DOCTYPE html>
<HTML>
<HEAD>
	<title>UNIVERSAL LOAN ORIGINATION SYSTEM - [<%=request.getServerName() %>]</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link href="images/ulo/kbank2.ico" rel="shortcut icon">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-dialog.min.css" rel="stylesheet">
	<link href="css/ulo/selectize.bootstrap3.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/kasikorn.style.css" rel="stylesheet">    
	<link href="css/ulo/jquery-ui.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.structure.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.theme.css" rel="stylesheet">
	<link href="css/ulo/uploadfile.css" rel="stylesheet">
	<link href="css/ulo/jquery-confirm.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.theme.override.css" rel="stylesheet">
	<link href="css/ulo/ulo.theme.css" rel="stylesheet">
	<link href="css/ulo/ulo.theme.override.css" rel="stylesheet">
	<script src="js/localstorage.js"></script>
	<script src="js/log4html.js"></script>
	<script src="js/ulo/jquery-2.1.4.js"></script>
	<script src="js/ulo/jquery-ui.js"></script>
	<script src="js/ulo/jquery.resize.js"></script>
	<script src="js/ulo/jquery.form.js"></script>
	<script src="js/ulo/jquery.uploadfile.js"></script>
	<script src="js/ulo/jquery-confirm.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/bootstrap-dialog.min.js"></script>
	<script src="js/ulo/form.util.js"></script>
	<script src="js/ulo/form.plugin.js"></script>
	<script src="js/ulo/form.validate.js"></script>
	<script src="js/ulo/selectize.js"></script>
	<script src="js/ulo/jquery.mask.js"></script>
	<script src="js/ulo/form.admin.js"></script>
</HEAD>
<body>
<div id="wrapper">
	<div id="page-wrapper">
		<jsp:include page="HtmlBoby.jsp" flush="true"/>
	</div>	
</div>
</body>
<script type="text/javascript">
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
	try{
		parent.Pace.unblock();
	}catch(e){}	
});
</script>
</HTML>