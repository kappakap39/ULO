<%@page import="com.eaf.j2ee.pattern.control.ScreenFlowManager"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="org.apache.log4j.Logger"%>
<%@ page import="com.eaf.j2ee.pattern.control.ScreenNames" %>
<%@ page import="com.eaf.j2ee.system.LoadXML" %>
<%@ page import="com.eaf.j2ee.pattern.control.ScreenDefinition" %> 
<jsp:useBean id="screenFlowManager" class="com.eaf.j2ee.pattern.control.ScreenFlowManager" scope="session"/> 
<%
	Logger logger = Logger.getLogger(this.getClass());	
	if(Util.empty(screenFlowManager.getCurrentScreen())){
		screenFlowManager.setCurrentScreen(ScreenFlowManager.INDEX_SCREEN);
	}
	ScreenDefinition sd = (ScreenDefinition) LoadXML.getScreenDefinitionMap().get(screenFlowManager.getCurrentScreen());
	logger.debug("Current Screen>>>>"+screenFlowManager.getCurrentScreen());
	pageContext.getRequest().setAttribute("HtmlTitle", sd.getHtmlTitle());
	pageContext.getRequest().setAttribute("TopNav", sd.getTopNav());
	pageContext.getRequest().setAttribute("SubHeader", sd.getSubHeader());
	pageContext.getRequest().setAttribute("TopMenu", sd.getTopMenu());
	pageContext.getRequest().setAttribute("LeftNav", sd.getLeftNav()); 
	pageContext.getRequest().setAttribute("RightNav", sd.getRightNav());
	pageContext.getRequest().setAttribute("BottomNav", sd.getBottomNav());
	pageContext.getRequest().setAttribute("HtmlBody", sd.getHtmlBody());	
	pageContext.getRequest().setAttribute("CopyRight", sd.getCopyRight());	
%>