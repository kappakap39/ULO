<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAddressDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.orig.cache.properties.ORIGCacheDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>
<%
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/df/inbox_screen.jsp");
	PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
	if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
	
	PLPersonalInfoDataM plpersonal = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	if(plpersonal == null) plpersonal = new PLPersonalInfoDataM();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	Vector vAddressType = (Vector)plpersonal.getAddressVect();
	if(vAddressType == null) vAddressType = new Vector();
	
	
%>
<!-- <title>df_address</title> -->
<table width="100%">
	<tr>
		<td>
<!-- 		<table cellpadding="" cellspacing="1" width="98%" align="center" class="gumayframe3" bgcolor="#ffffff"> -->
<!-- 		        <tr height="25"> -->
			<table class="TableFrame">
        		<tr class="Header" height="25">
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "DF_ADDRESS_LEVEL")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "BANKING_NUMBER_ACCOUNT")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_TYPE")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "ADDRESS_CODE")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "NUMBER")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "TAMBOL")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "AMPHUR")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "PROVINCE")%></span></td>
<%-- 		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "TAMBOL")%></span></td> --%>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "ZIP_CODE")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "COUNTRY")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "CANCEL_RELATION")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "AVALABLE_TIME")%></span></td>
		               <td align="center" class="Bigtodotext3"><%=PLMessageResourceUtil.getTextDescription(request, "NOTE")%></span></td>
		        </tr>
		        <%if(!ORIGUtility.isEmptyVector(vAddressType)){
		        	for(int i=0;i<vAddressType.size();i++){ 
			       		if(vAddressType.get(i)!=null){
			       		PLAddressDataM plAddressDataM = (PLAddressDataM)vAddressType.get(i);
			       		ORIGCacheDataM CountryM = origc.GetListboxCacheDataM(104, plApplicationDataM.getBusinessClassId(), plAddressDataM.getCountry());
			       		%>
<!-- 		       	<tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'"> -->
		        	<tr class="Result-Obj ResultEven">
		        	   <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getNaosCacheDisplayNameDataM(12,plAddressDataM.getAddressType()))%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getAddressNo())%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameFormDB(plAddressDataM.getTambol(), "Tambol"))%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameFormDB(plAddressDataM.getAmphur(), "Amphur"))%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameFormDB(plAddressDataM.getProvince(), "Province"))%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getZipcode())%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(CountryM.getThDesc())%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		               <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
		       </tr>
		      <%		}
		       		}
		       }%>
	
		</table>
		</td>
	<tr><td>
	<table width="100%">
	
	<tr>
		<td colspan="5" align="center"><hr></td>
	</tr>
	
	<tr>
		<td class="textColS" align="left" width="30%"><%=PLMessageResourceUtil.getTextDescription(request, "NEWS_UPDATE_FROM_BANK")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "HOME_TEL")%>: &nbsp;&nbsp;<%=HTMLRenderUtil.displayHTML("-")%>
		<%=PLMessageResourceUtil.getTextDescription(request, "OFFICE_TEL")%>: &nbsp;<%=HTMLRenderUtil.displayHTML("-")%>
		<%=PLMessageResourceUtil.getTextDescription(request, "MOBILE_TEL")%>: &nbsp;<%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr>
		<td colspan="5" align="center"><hr></td>
	</tr>
	
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "OPTIONAL_FROM_BANK")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><font style="color: red"><%=PLMessageResourceUtil.getTextDescription(request, "EXCEPTION_CUSTOMER")%></font></td>
	</tr>
	
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "EMAIL_DESC")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "MAIL_DESC")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
		
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "PRODUCT_OFFER_BY_PHONE")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "MESSAGE_VIA_PHONE")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	
	<tr height="25">
		<td class="textColS" align="left"><%=PLMessageResourceUtil.getTextDescription(request, "VIA_ALL")%>: &nbsp;&nbsp;</td>
		<td class="inputCol" align="left"><%=HTMLRenderUtil.displayHTML("-")%></td>
	</tr>
	</table>
	</td></tr>	
</table>