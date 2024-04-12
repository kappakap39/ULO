<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>[KVI System] - </title>	
<link rel="stylesheet" href="css/ulo/logon/kbankulo-login.css"/>
<link rel="shortcut icon" href="images/ulo/kbank2.ico">
<script type="text/javascript" src="js/ulo/jquery-2.1.3.js"></script>
</head>
<body>
<% 
 String tokenId = request.getParameter("tokenId");
 String fId = request.getParameter("fId");
 String ORIGIN_URL = SystemConfig.getProperty("ORIGIN_URL");
%>
Welcome tokenId <%=tokenId %> fid <%=fId %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				Verified Income :
			</div>
			<div class="col-sm-6">
			 <input type="text" name ="income" class="textbox_login">
			</div>
			<div class="clearfix"></div>
			<div class="col-sm-6">
				Percent Return : 
			</div>
			<div class="col-sm-6">
				<input type="text" name ="percent" class="textbox_login">
			</div>
			<div class="clearfix"></div>
			<section class="groupList clear btnlogon">
				<button type="submit" class="button green" name="SubmitBtn" onclick="submitBackToFLP()">Submit</button>
			</section>
		</div>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	function submitBackToFLP() {
		var income = $('[name="income"]').val();
		var percent = $('[name="percent"]').val();
		var KVIObjectData = {serviceId:'KVI',data:{verifiedIncome:income, percentChequeReturn:percent}};
		parent.postMessage(KVIObjectData,'<%=ORIGIN_URL%>'); 
	}
</script>