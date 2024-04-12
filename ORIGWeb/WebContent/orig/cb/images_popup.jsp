<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<script type="text/javascript" src="orig/js/popup/pl/cb.ncb.viewimage.js"></script>
<%
	PLApplicationDataM applicationM  = PLORIGForm.getAppForm();		
	if(null == applicationM) applicationM  = new PLApplicationDataM();		
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);	
%>
<div class="PanelSecond TextHeaderNormal">
	<div class="PanelThird">
			<table class="FormFrame">
				<tr>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "DUP_NAME_THAI") %>:</td>
					<td class="textL" width="20%">
						<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiFirstName())%>
						&nbsp;&nbsp;
						<%=HTMLRenderUtil.displayHTML((String) personalM.getThaiLastName())%></td>
					<td class="textR" nowrap="nowrap" width="25%"><%=MessageResourceUtil.getTextDescription(request, "DUP_ID_NO") %>:</td>
					<td class="textL" width="30%"><%=HTMLRenderUtil.displayHTML((String) personalM.getIdNo())%></td>
				</tr>
			</table>
	</div>
</div>
<div id="ncbContainer">
	<div class="ui-layout-center">
		<div class="center-center" id="div-ncbImg-2"><div id="view-img-2"></div></div>
		<div class="center-south"><div id="thumbnail-img-2"></div></div>
	</div>
	<div class="ui-layout-west">
		<div class="west-center" id="div-ncbImg-1"><div id="view-img-1"></div></div>
		<div class="west-south"><div id="thumbnail-img-1"></div></div>
	</div>
</div>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
	screenFlowManager.setCurrentScreen(PLORIGForm.getCurrentScreen());
%>