<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>	
<body>
<form method='post' action="Logout" name="logout">
	<input type="hidden" name="logoutExitPage" value="origLogon.jsp"/>
</form>
<script>
	document.logout.submit();
</script>
</body>
</html>