<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="FlowControl" class="com.eaf.orig.ulo.model.control.FlowControlDataM" scope="session"/>
<%
	String menuName = FlowControl.getMenuName();
	if(Util.empty(menuName)){
		menuName = "";
	}
 %>
<section id="navigation-info" class="navigation">
	<h1><%=menuName%></h1>
</section>