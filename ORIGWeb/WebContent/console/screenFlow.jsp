<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.apache.log4j.Logger"%>
<html>
<head>
	<link rel="stylesheet" href="css/console-style.css" type="text/css">
</head>
<body>
<%
	Logger logger = Logger.getLogger(this.getClass());	
	String pageID = request.getParameter("pageID");
	logger.debug(">> pageID "+pageID);
	if(null != pageID && !"".equals(pageID)){
		try{			
			String LINK = "page/"+pageID+".jsp";
			pageContext.include(LINK,true);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
%>
</body>
</html>