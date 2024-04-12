<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>

<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/popup/pl/DCBundleClaimPopup.jsp");
	
	ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();	
	Vector vCreditCardResult = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 100);
	Vector vCreditCardAppScore = (Vector)origCache.getNaosCacheDataMs("ALL_ALL_ALL", 101); 
	
	String ccResult = request.getParameter("ccResult");
	String ccAppScore = request.getParameter("ccAppScore");
	
	String displayMode=HTMLRenderUtil.DISPLAY_MODE_EDIT;
	if(!OrigUtil.isEmptyString(ccResult)){
	  	displayMode=HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
%>
<div class="nav-inbox">
	<div class="PanelFirst">	
		<div class="PanelSecond TextHeaderNormal">
			<div class="div-error-mandatory" id="div-error"></div>
			<div class="PanelThird">
				<table class="FormFrame">
					<tr>
						<td width="350">Credit Card Result <span style="color: red;">*</span> :</td>
						<td width="200"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vCreditCardResult,HTMLRenderUtil.displayHTML(ccResult),"creditCardResult",displayMode,"")%></td>
						<td width="400"> Credit Card App Score <span style="color: red;">*</span> </td>
						<td width="200"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vCreditCardAppScore, HTMLRenderUtil.displayHTML(ccAppScore),"creditCardAppScore",displayMode,"")%> </td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>

<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("DC_PL_SEARCH_BUNDLE_SCREEN");
%>