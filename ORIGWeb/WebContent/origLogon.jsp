<!DOCTYPE html>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.eaf.orig.logon.LogonEngine"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.MessageErrorUtil"%>
<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
	String userName = LogonEngine.getValue(request,"LogonUserName");
	String password =  LogonEngine.getValue(request,"LogonPassword");
	String ERROR_MSG = LogonEngine.getErrorMsg(request);
	LogonEngine.init(request,response);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>UNIVERSAL LOAN ORIGINATION SYSTEM - [<%=request.getServerName() %>]</title>	
<link rel="stylesheet" href="css/ulo/logon/kbankulo-login.css"/>
<link rel="shortcut icon" href="images/ulo/kbank2.ico">
<style>
[name='password']{
    -webkit-text-security:disc;
}
</style>
<script type="text/javascript" src="js/ulo/jquery-2.1.3.js"></script>
<script type="text/javascript">
	(function($){
	    $.fn.setCursorToTextEnd = function() {
	        $initialVal = this.val();
	        this.val($initialVal+' ');
	        this.val($.trim($initialVal));
	    };
	})(jQuery);
	//#rawi fix memory leak jquery event
	$(document).ready(function (){
		if (window.attachEvent && !window.addEventListener){
			window.attachEvent("onunload", function() {
				for ( var id in jQuery.cache ) {
					if ( jQuery.cache[ id ].handle ) {
						// Try/Catch is to handle iframes being unloaded, see #4280
						try {
							jQuery.event.remove(jQuery.cache[id].handle.elem );
						} catch(e) {}
					}
				}
				// The following line added by me to fix leak!
				window.detachEvent("onload", jQuery.ready );
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
// 		setTimeout(function() {$('[name=password]').focus();$('[name=password]').setCursorToTextEnd();}, 100);	
	});	
</script>	
</head>
<body>
	<form action="Logon" method="post" name="logonForm"  autocomplete="off" class="outerlogin">
		<input type="hidden" name="action" value="UserAction"/>	
		<input type="hidden" name="handleForm" value="N"/>
		<article class="wraplogin">
			<aside class="brandMode">
				<figure></figure>  
				<div class="brandinfo">
					<h1>Formula Lending System</h1>
					<span>© 2016 - KASIKORNBANK PCL. All right reserved.</span>		
				</div>
				<div class="versioninfo">
				<span><%=SystemConfig.getGeneralParam("ENVIRONMENT_VERSION")+"&nbsp;"%></span>	
				</div>
			</aside>
							
										
			<article class="login">
				<figure class="logo_iconsmall"></figure>				
				<section class="info">
					<h1>FLP</h1>
					<h2>SIGN IN</h2> 
					<p>Please enter your account info.</p>
				</section>				
				<section class="wraptext_user"><input name="userName" type="text" class="textbox_login" style="text-transform: uppercase" placeholder="K0+staff ID Ex.K0336491" value="<%=userName%>" autocomplete="off"/></section>
				<section class="textbox_password"><input name="password" type="password" class="textbox_login" placeholder="Your KBankNet Password" value="<%=password%>" autocomplete="new-password"/></section>
				<section class="groupList clear btnlogon">
					<button type="submit" class="button green" name="LoginBtn">Login</button>
				</section>
				<section class="errormessage"><%=ERROR_MSG%></section>
			</article>
		</article>
	</form>
</body>
</html>