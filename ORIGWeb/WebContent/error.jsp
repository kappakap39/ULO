<%@ page isErrorPage="true" import="java.io.*" %>

<%-- <%=exception.getMessage()%> --%>

<%

	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
%>

<!DOCTYPE html>
<!-- saved from url=(0039)http://localhost:9081/ORIGWeb/index.jsp -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-hide-animate){display:none !important;}ng\:form{display:block;}.ng-animate-shim{visibility:hidden;}.ng-animate-anchor{position:absolute;}</style>
	<title>Error Exception</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link href="images/ulo/kbank2.ico" rel="shortcut icon">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/bootstrap-dialog.min.css" rel="stylesheet">
	<link href="css/ulo/selectize.bootstrap3.css" rel="stylesheet">
	<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <link href="metis-menu/metisMenu.min.css" rel="stylesheet">    
    <link href="css/sb-admin-2.css" rel="stylesheet">
    <link href="css/kasikorn.style.css" rel="stylesheet">    
	<link href="css/ulo/jquery-ui.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.structure.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.theme.css" rel="stylesheet">
	<link href="css/ulo/uploadfile.css" rel="stylesheet">
	<link href="css/ulo/jquery-confirm.css" rel="stylesheet">
	<link href="css/ulo/jquery-ui.theme.override.css" rel="stylesheet">
	<link href="css/ulo/ulo.theme.css" rel="stylesheet">
	<link href="css/ulo/ulo.menu.css" rel="stylesheet">
	<link href="css/ulo/ulo.theme.override.css" rel="stylesheet">
	<link href="css/pace.css" rel="stylesheet">
	<script src="js/pace.min.js"></script>
	<script src="js/localstorage.js"></script>
	<script src="js/ulo/jquery-2.1.4.js"></script>
	<script src="js/ulo/jquery-ui.js"></script>
	<script src="js/ulo/jquery.resize.js"></script>
	<script src="js/ulo/jquery.form.js"></script>
	<script src="js/ulo/jquery.uploadfile.js"></script>
	<script src="js/ulo/jquery-confirm.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/bootstrap-dialog.min.js"></script>	
	<script src="angular/angular.min.js"></script>
	<script src="metis-menu/metisMenu.min.js"></script>
	<script src="js/ulo/form.util.js"></script>
	<script src="js/ulo/form.plugin.js"></script>
	<script src="js/ulo/form.validate.js"></script>
	<script src="js/sb-admin-2.js"></script>
	<script src="js/ulo/selectize.js"></script>
	<script src="js/ulo/jquery.mask.js"></script>
	<script src="js/template/ulo.application.javascript.js"></script>
<style type="text/css">@keyframes resizeanim { from { opacity: 0; } to { opacity: 0; } } .resize-triggers { animation: 1ms resizeanim; visibility: hidden; opacity: 0; } .resize-triggers, .resize-triggers > div, .contract-trigger:before { content: " "; display: block; position: absolute; top: 0; left: 0; height: 100%; width: 100%; overflow: hidden; } .resize-triggers > div { background: #eee; overflow: auto; } .contract-trigger:before { width: 200%; height: 200%; }</style></head>
<body class="kstyle sb-l-m">
<div id="wrapper">
	





<nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0; white-space: nowrap;">
	<div class="navbar-header">
		<a class="navbar-brand"><img src="images/ulo/logo.jpg"></a>
	</div>
	<!-- /.navbar-header -->
	<div class="nav navbar-top-links navbar-left col-xs-6">
		<div class="row">
			<div class="col-xs-12 line1">JSP Error !</div>			
		</div>
		<div class="row">
			<div class="col-xs-6 line2" style="font-size: 12px">
				Please contact your Administrator with exceptions below.
			</div>			
		</div>
	</div>

</nav>

<div class="navbar-default sidebar fixed" role="navigation">
	<div class="sidebar-nav navbar-collapse">
		<ul class="nav" id="side-menu">
			
		</ul>		
	</div>
</div>
<div class="sidebar-toggle-mini"></div>
	<footer id="footer-nav"><article id="footer-info">© 2016 KASIKORNBANK PLC. All rights Reserved.</article></footer>
	<div id="page-wrapper" style="position: relative; min-height: 549px;">

<div class="row">
	<div class="col-lg-12">
		<nav>


<section id="navigation-info" class="navigation"><h1></h1></section></nav>
		
<!-- <pre> -->
<!-- Message: -->
<%-- <%=exception.getMessage()%> --%>
<!-- </pre> -->
<h3 style="color: red">Message : </h3>
<br/>
<pre>
<%
	out.println(stringWriter);
%>

</pre>



	</div>
</div>
</div>	
</div>

</body></html>
<%
	printWriter.close();
	stringWriter.close();
%>