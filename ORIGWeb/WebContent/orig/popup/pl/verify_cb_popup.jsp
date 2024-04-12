<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/verify_cb_popup.jsp");    	
	ORIGCacheUtil origCache = new ORIGCacheUtil();
	Vector actionVect = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 102);	
		
	SearchHandler HandlerM = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
	if(HandlerM == null){
		HandlerM = new SearchHandler();
	}
	SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
	if(searchDataM == null){
		searchDataM = new SearchHandler.SearchDataM();
		HandlerM.setSearchM(searchDataM);
	}
	
%>
<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR" width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "ACTION")%> :</td>
						<td width="270"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(actionVect, "","cb-action","EDIT","")%></td>
					</tr>
					<tr>
						<td class="textR"><%=PLMessageResourceUtil.getTextDescription(request, "COMMENT")%> :</td>
						<td><%=HTMLRenderUtil.displayInputTagScriptAction(searchDataM.getComment(),"EDIT","70","cb-comment","textbox-b","onkeypress= \"return keyPressEnter(event)\"","100")%></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<%
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("VERIFY_NCB_SCREEN");
%>