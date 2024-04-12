<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>

<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/CBSendbackPopup.jsp");
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
	Vector reasonVect = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 82);
	
%>

<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td class="textR"><%=MessageResourceUtil.getTextDescription(request, "REASON")%><span style="color: red;">*</span> :</td>
						<td width="270"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(reasonVect, "","reasonCode","EDIT","")%></td>
						<td><%=HTMLRenderUtil.displayInputTagScriptAction("","EDIT","1000","remark","textbox","","250")%></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("CB_PL_INBOX_SCREEN");
%> 

