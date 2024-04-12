<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM" %>
<%@ page import="com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<%
	PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
	if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
	
	PLPersonalInfoDataM plpersonal = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	if(plpersonal == null) plpersonal = new PLPersonalInfoDataM();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	PLXRulesVerificationResultDataM plVerDataM = plpersonal.getXrulesVerification();
	PLXRulesDebtBdDataM plXruleDeptDataM = plVerDataM.getXRulesDebtBdDataM();
%>
<!-- <title>df_general_information</title> -->
<table width="100%">

	<tr height="25">
		<td class="textR" align="left" width="30%"><%=MessageResourceUtil.getTextDescription(request, "DF_EDUCATION")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(6,plpersonal.getDegree()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "POSITION")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(77,plpersonal.getPositionLevel()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "START_WORKING_DATE")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "DF_SALARY_PER_MONTH")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML(DataFormatUtility.displayBigDecimalZeroToEmpty(plXruleDeptDataM.getTotalIncome()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "SUMMARY_SALARY_MONTH")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "NO_OF_CHILDREN")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(DataFormatUtility.IntToString(plpersonal.getNoOfchild()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "OLDER_CHILD_AGE")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "YOUNGER_CHILD_AGE")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "DF_NUMBER_OF_EMPLOYEE")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "ASSET_VAL_EXCEPT_LAND")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request, "BUSINESS_TYPE_CODE")%>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
</table>

