<%@ include file="new_screen_definitions.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
	<TITLE>UNIVERSAL LOAN ORIGINATION SYSTEM - [<%=request.getServerName() %>]</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</HEAD>
<%if( ((String)request.getSession().getAttribute("userName")==null) || ((String)request.getSession().getAttribute("password")==null) ){%>
<script type="text/javascript">
	function rewrite(){
		var url = "<%=request.getContextPath()%>/logout.jsp";
		eval("parent.window.location='"+url+"' ");
	}
	rewrite();
</script>
<%}%>
<frameset rows="83,*" cols="*" frameborder="NO" border="1" framespacing="1">
  <frame src="<%=pageContext.getRequest().getAttribute("TopNav") %>" name="topFrame" scrolling="no" noresize>
	  <frameset cols="220,*" frameborder="NO" border="1" framespacing="1" id="bodyFrameset">
	  	<frame src="screen_template/frame/default.jsp" name="leftFrame" id="leftFrame" noresize>
	    <frame src="Avale_template.jsp" name="mainFrame" id="mainFrame" scrolling="yes">
	  </frameset> 
</frameset><noframes></noframes>
</HTML>