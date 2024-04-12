<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<%
	PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
	if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
	
	PLPersonalInfoDataM plpersonal = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	if(plpersonal == null) plpersonal = new PLPersonalInfoDataM();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
%>
<!-- <title>df_document_account</title> -->

<table width="100%">
	<tr height="25">
		<td class="textR" align="left" width="15%"><%=MessageResourceUtil.getTextDescription(request, "IMPORTANT_DOC_TYPE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" width="13%"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(2, plpersonal.getCidType()))%></td>
		<td colspan="2"></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "IMPORTANT_DOC_NO") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML(plpersonal.getIdNo())%></td>
		<td colspan="2"></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "PERMIT_BY") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML("-")%></td>
		<td colspan="2"></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "CURRENT_DATE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML("-")%></td>
		<td colspan="2"></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "EXPIRE_DATE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML(DataFormatUtility.DateEnToStringDateTh(plpersonal.getCidExpDate(), 1))%></td>
		<td colspan="2"></td>
	</tr>
	
	<tr height="25">
		<td colspan="4"><hr></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "BIRTH_DATE_INFO") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML(DataFormatUtility.DateEnToStringDateTh(plpersonal.getBirthDate(),1))%></td>
		<td class="textR" align="left" width="10%"><%=MessageResourceUtil.getTextDescription(request, "AGE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML(DataFormatUtility.IntToString(plpersonal.getAge()))%>&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "YEAR") %></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "PERSONA_TAX_ID") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "NATIONALITY") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(7, plpersonal.getNationality()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "RACE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(8, plpersonal.getRace()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(15, plpersonal.getOccupation()))%></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" ><%=MessageResourceUtil.getTextDescription(request, "OCCUPATION_TYPE") %>: &nbsp;&nbsp;</td>
		<td class="inputL" align="left" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(origc.getNaosCacheDisplayNameDataM(16, plpersonal.getProfession()))%></td>
	</tr>
</table>