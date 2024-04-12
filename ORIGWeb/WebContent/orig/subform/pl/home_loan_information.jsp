<%@page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@page import="com.eaf.orig.ulo.pl.model.app.PLBundleHLDataM"%>
<%@page import="java.math.BigDecimal"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>

<%
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(this.getClass());

	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(applicationM == null){
		 applicationM = new PLApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil utility = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");	
	String displayMode = formUtil.getDisplayMode("HOME_LOAN_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);

	PLBundleHLDataM bundleHLM = applicationM.getBundleHLM();
	if(OrigUtil.isEmptyObject(bundleHLM)){
		bundleHLM = new PLBundleHLDataM();
	}
%>

<table class="FormFrame">
	<tr>
		<td class="textR" width="20%" >
			<%=PLMessageResourceUtil.getTextDescriptionMessage(request, "KEC_PERMIT")%><font color="#ff0000">*</font>&nbsp;:&nbsp;
		</td>
		<td class="inputL"  width="25%">
			<%=HTMLRenderUtil.DisplayInputCurrencyEmptyNull(bundleHLM.getApproveCreditLine(), displayMode, "########0.00","kec_permit","textbox-currency", "", "12", true) %>
		</td>
		<td class="textR" width="25%"></td>
		<td class="textR" width="30%"></td>
	</tr>
	
	
</table>