<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.j2ee.pattern.control.ScreenFlowManager"%>
<jsp:useBean id="SearchPopupM" scope="session" class="com.eaf.orig.shared.model.PopupMasterDataM"/>
<script type="text/javascript" src="orig/js/popup/master.popup.engine.js"></script>
<div id="div-master-popup">
	
</div>
<%=HTMLRenderUtil.displayHiddenField(SearchPopupM.getModuleWebAction(),"module-webaction")%>
<%
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
%>