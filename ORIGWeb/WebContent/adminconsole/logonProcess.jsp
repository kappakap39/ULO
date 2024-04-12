<link rel="StyleSheet" href="main.css" type="text/css">  

<%
String userName = request.getParameter("j_username");
String userPassword = request.getParameter("j_password");

session.setAttribute("userName",userName);
session.setAttribute("password", userPassword);

response.sendRedirect("adminconsole/main_naos.jsp");

%>
