<%@ page contentType="text/html;charset=UTF-8"%> 
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<script type="text/javascript" src="orig/js/subform/pl/verification.js"></script>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<div id="verification">Loading...</div>
<% 
	Logger logger = Logger.getLogger(this.getClass());
	PLApplicationDataM appM = PLORIGForm.getAppForm();
	if(null == appM) appM = new PLApplicationDataM();
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", ORIGUser.getRoles(), appM.getJobState(), PLORIGForm.getFormID(), searchType);
// 	logger.debug("DisplayMode >> "+displayMode);
%>
<%=HTMLRenderUtil.displayHiddenField("","change-saletype")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode,"display-mode-verify")%>
<%=HTMLRenderUtil.displayHiddenField(searchType,"searchtype-verify")%>
