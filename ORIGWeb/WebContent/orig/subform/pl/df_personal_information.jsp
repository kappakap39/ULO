<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.j2ee.pattern.util.DisplayFormatUtil"%>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<%
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	
	PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
	if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
	
	PLPersonalInfoDataM plpersonal = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	if(plpersonal == null) plpersonal = new PLPersonalInfoDataM();
	
	PLSaleInfoDataM plsaleinfo = plApplicationDataM.getSaleInfo();
	if(plsaleinfo == null) plsaleinfo = new PLSaleInfoDataM();
	
%>



<!-- <title>df_personal_information</title> -->

<table width="100%">
	<tr height="25">
		<td class="textR" align="left" width="13%"><%=MessageResourceUtil.getTextDescription(request,"SUM_NAME_SURNAME_TH")%>: &nbsp;</td>
		<td class="inputL" width="13%"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(3, plpersonal.getThaiTitleName()))%>
<!-- 		</td><td class="inputL" align="left" width="13%"> -->&nbsp;
		<%=HTMLRenderUtil.displayHTML(plpersonal.getThaiFirstName())%></td>
		<td class="inputL" align="left" width="13%"><%=HTMLRenderUtil.displayHTML(plpersonal.getThaiLastName())%></td>
		<td class="inputL" align="left" width="85%"><%=HTMLRenderUtil.displayHTML(plpersonal.getThaiMidName())%></td>	
	</tr>
	
	<tr >
		<td colspan="4"></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left" width="13%"><%=MessageResourceUtil.getTextDescription(request,"SUM_NAME_SURNAME_EN")%>: &nbsp;</td>
		<td class="inputL" width="13%"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(4, plpersonal.getEngTitleName()))%>
<!-- 		</td><td class="inputL" align="left" width="13%"> -->&nbsp;
		<%=HTMLRenderUtil.displayHTML(plpersonal.getEngFirstName())%></td>
		<td class="inputL" align="left" width="13%"><%=HTMLRenderUtil.displayHTML(plpersonal.getEngLastName())%></td>
		<td class="inputL" align="left" width="85%"><%=HTMLRenderUtil.displayHTML(plpersonal.getEngMidName())%></td>	
	</tr>
	
	<tr >
		<td colspan="4"></td>
	</tr>

	<tr height="25">
		
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request,"GENDER")%>: &nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(5, plpersonal.getGender()))%></td>
		<td class="inputL" align="left"><%=MessageResourceUtil.getTextDescription(request,"MARRIAGE_STATUS")%>: 
<!-- 		</td><td class="inputL" align="left"> -->
		<%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(9, plpersonal.getMaritalStatus()))%></td>	
	</tr>
	
	<tr height="25">
		<td colspan="5" align="center"><hr></td>
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request,"CUSTOMER_TYPE_DF")%>: &nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(1, plpersonal.getCustomerType()))%></td>
<!-- 		<td></td> -->
		<td></td>
		<td></td>	
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request,"SEGMENT_S")%>: &nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
<!-- 		<td></td> -->
		<td class="inputL" align="left"><%=MessageResourceUtil.getTextDescription(request,"TAKE_BRANCH")%>: 
<!-- 		</td><td class="inputL" align="left"> -->
		<%=HTMLRenderUtil.displayHTML(plsaleinfo.getSalesBranchCode())%></td>	
	</tr>
	
	<tr height="25">
		<td class="textR" align="left"><%=MessageResourceUtil.getTextDescription(request,"SERVICE_LEVEL")%>: &nbsp;</td>
		<td class="inputL" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
<!-- 		<td></td> -->
		<td class="inputL" align="left"><%=MessageResourceUtil.getTextDescription(request,"SERVICE_LEVEL_DATE")%>: 
<!-- 		</td><td class="inputL" align="left"> -->
		<%=HTMLRenderUtil.displayHTML("-")%></td>	
	</tr>
</table>