<%@page import="com.eaf.j2ee.system.LoadXML"%>
<%@page import="com.eaf.j2ee.pattern.control.ScreenDefinition"%>
<%@page import="org.apache.log4j.Logger"%>
<%    
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache,no-store");
	response.setDateHeader("Expires", 0);
	response.setCharacterEncoding("UTF-8");
%>
<%@page contentType="text/html;charset=UTF-8"%> 
<%@taglib uri="/WEB-INF/lib/JStartTagLib.tld" prefix="taglib"%>
<%@page import="java.util.*"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<jsp:useBean id="screenFlowManager" class="com.eaf.j2ee.pattern.control.ScreenFlowManager" scope="session"/> 
<%
Logger logger = Logger.getLogger(this.getClass());
UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser"); 
if(userM == null){
	response.sendRedirect("logout.jsp");
	return;
}
ScreenDefinition sd = (ScreenDefinition) LoadXML.getScreenDefinitionMap().get(screenFlowManager.getPopupScreen());
	logger.debug("POPUP SCREEN >>>>"+screenFlowManager.getPopupScreen());
	pageContext.getRequest().setAttribute("HtmlTitle", sd.getHtmlTitle());
	pageContext.getRequest().setAttribute("TopNav", sd.getTopNav());
	pageContext.getRequest().setAttribute("SubHeader", sd.getSubHeader());
	pageContext.getRequest().setAttribute("TopMenu", sd.getTopMenu());
	pageContext.getRequest().setAttribute("LeftNav", sd.getLeftNav()); 
	pageContext.getRequest().setAttribute("RightNav", sd.getRightNav());
	pageContext.getRequest().setAttribute("BottomNav", sd.getBottomNav());
	pageContext.getRequest().setAttribute("HtmlBody", sd.getHtmlBody());	
	pageContext.getRequest().setAttribute("CopyRight", sd.getCopyRight());	
logger.debug("[HtmlBody]="+pageContext.getRequest().getAttribute("HtmlBody"));
%>
<taglib:insert parameter="HtmlBody" direct="false"/>