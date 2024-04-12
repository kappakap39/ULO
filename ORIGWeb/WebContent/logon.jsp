<!DOCTYPE html>
<HTML>
	<HEAD>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<TITLE>Logon Processing...</TITLE>
	</HEAD>
	<BODY>
	<P>Logon Processing...</P>
	<%if(((String)request.getSession().getAttribute("userName")!=null) && ((String)request.getSession().getAttribute("password")!=null)){%>	
		<FORM METHOD=POST ACTION="j_security_check" name="j_security_form">
			<input type="hidden" name="action" value="UserAction">
			<input type="hidden" name="handleForm" value="N">
			<input type="hidden" name="j_username" value="<%=(String)request.getSession().getAttribute("userName")%>">
			<input type="hidden" name="j_password" value="<%=(String)request.getSession().getAttribute("password")%>">
		</FORM>
		<script>
			document.j_security_form.submit();
		</script>
	<%}else{%>
	 <br>
	 <a href="javascript:window.close();"><U> close window</U></a> 
	<%}%>	
	</BODY>
</HTML>