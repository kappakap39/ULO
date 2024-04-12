<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" />
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<script type="text/javascript" src="orig/js/popup/pl/ncbPopup.js"></script>
<div id="ncbContainer">
	<div class="ui-layout-center">
		<div id="ncbImgThumbnail">			
		</div>
	</div>
	<div class="ui-layout-west" id="ncbImgView">
		<div id="ncbImg">			
		</div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLOrigFormHandler.PL_MAIN_APPFORM_SCREEN);
%>