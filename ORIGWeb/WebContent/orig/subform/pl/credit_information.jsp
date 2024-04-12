<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLBundleCCDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<script type="text/javascript" src="orig/js/subform/pl/credit_information.js" ></script>

<%
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/subform/pl/credit_information.jsp");	
	PLApplicationDataM plAppM = PLORIGForm.getAppForm();
	if(plAppM == null) plAppM = new PLApplicationDataM();
	PLPersonalInfoDataM plpersonalInfoDataM = plAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil utility = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");	

	String displayMode = formUtil.getDisplayMode("CREDIT_CARD_SUBFORM", ORIGUser.getRoles(), plAppM.getJobState(), PLORIGForm.getFormID(), searchType);
	log.debug("Credit displayMode="+displayMode);
	PLBundleCCDataM plBundleCCDataM = plAppM.getBundleCCM();
	if(OrigUtil.isEmptyObject(plBundleCCDataM)) plBundleCCDataM = new PLBundleCCDataM();
%>

<table class="FormFrame">
	<tr>
		<td class="textR" width="20%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CREDIT_CARD_RESULT")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(plpersonalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CREDIT_CARD_SUBFORM","credit_card_result")%>:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(100,plAppM.getBusinessClassId(),plBundleCCDataM.getCreditCardResult(),"credit_card_result",displayMode,"") %>
		</td>
		<td class="textR"  width="25%">
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "CREDIT_CARD_APP_SCORE")%>
			<%=HTMLRenderUtil.getMandatory_ORIG(plpersonalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CREDIT_CARD_SUBFORM","credit_card_app_score")%>:&nbsp;
		</td>
		<td class="inputL"  width="30%">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(101,plAppM.getBusinessClassId(),plBundleCCDataM.getCreditCardAppScore(),"credit_card_app_score",displayMode,"") %>
		</td>
	</tr>
</table>