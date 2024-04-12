<%@page import="org.apache.log4j.Logger"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLAddressDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="java.util.Vector" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<!-- <title>df_contact</title> -->

<%
	org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger("orig/subform/pl/df_contact.jsp");
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
	
	PLApplicationDataM plApplicationDataM = PLORIGForm.getAppForm();
	if(plApplicationDataM == null) plApplicationDataM = new PLApplicationDataM();
	
	PLPersonalInfoDataM plpersonalInfoDataM = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	if(plpersonalInfoDataM==null) plpersonalInfoDataM = new PLPersonalInfoDataM();
	
	Vector vAddressType = plpersonalInfoDataM.getAddressVect();
// 	log.debug("[df_contact.jsp]...[vAddressType.size()=]"+vAddressType.size());

 %>
<!-- 	<table cellpadding="" cellspacing="1" width="98%" align="center" class="gumayframe3" bgcolor="#ffffff"> -->
		<table class="TableFrame">
        <tr class="Header" height="25">
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "CONTACT_TYPE_DF")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "PLACE")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "DF_NUMBER")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "DF_EXT_NO.")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "AVALABLE_TIME")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "AVALABLE_TIME_FOR_CONTACT")%></td>
        </tr>
        
        <%if(vAddressType!=null){
		      		for(int i=0;i<vAddressType.size();i++){
						PLAddressDataM plAddressDataM = (PLAddressDataM)vAddressType.get(i);
						if(!ORIGUtility.isEmptyString(plAddressDataM.getPhoneNo1())){%>
<!-- 						<tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'"> -->
						<tr class="Result-Obj ResultEven">
							<td align="center" class="inputCol"><%=MessageResourceUtil.getTextDescription(request,"PHONE_NO1") %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(12, plAddressDataM.getAddressType())) %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getPhoneNo1())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getPhoneExt1())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
						</tr>
				<%}
					if(!ORIGUtility.isEmptyString(plAddressDataM.getPhoneNo2())){%>
						<tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'">
							<td align="center" class="inputCol"><%=MessageResourceUtil.getTextDescription(request,"PHONE_NO2") %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(12, plAddressDataM.getAddressType())) %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getPhoneNo2())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getPhoneExt2())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
						</tr>
				<%}
					if(!ORIGUtility.isEmptyString(plAddressDataM.getFaxNo())){%>
						<tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'">
							<td align="center" class="inputCol"><%=MessageResourceUtil.getTextDescription(request,"DF_FAX_NO") %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(12, plAddressDataM.getAddressType())) %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getFaxNo())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
						</tr>
				<%}
					if(!ORIGUtility.isEmptyString(plAddressDataM.getMobileNo())){%>
						<tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'">
							<td align="center" class="inputCol"><%=MessageResourceUtil.getTextDescription(request,"DF_MOBILE_NO") %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(origc.getORIGCacheDisplayNameDataM(12, plAddressDataM.getAddressType())) %></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML(plAddressDataM.getMobileNo())%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
							<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
						</tr>
				<%}
		        	}
		        }else{%>
		        	<tr height="25" this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'">
		        	<td colspan="6" class="inputCol"></td>
		        	</tr>
		        <%}%>
    </table>
    
<!--     <table cellpadding="" cellspacing="1" width="98%" align="center" class="gumayframe3" bgcolor="#ffffff"> -->
	<table class="TableFrame">
        <tr class="Header" height="25">
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "EMAIL_TYPE")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "PLACE")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "E_ADDRESS")%></td>
               <td align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "AVALABLE_TIME")%></td>
        </tr>
         		
        <tr height="25" onmouseover="this.style.cursor='hand' ;this.style.background='#D7D6D7'" onmouseout=" this.style.background='#FFFFFF'">
				<td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
                <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>
                 <% 
                 	String email = plpersonalInfoDataM.getEmail1();
                 	if(ORIGUtility.isEmptyString(email)){
                 		email = plpersonalInfoDataM.getEmail2();
                 	}else{
                 		if(!ORIGUtility.isEmptyString(plpersonalInfoDataM.getEmail2())){
                 			email = email + ", " + plpersonalInfoDataM.getEmail2();
                 		}
                 	}
	              %>
                <td align="center" class="inputCol"><%=HTMLRenderUtil.DisplayReplaceLineWithNull(email)%></td>
                <td align="center" class="inputCol"><%=HTMLRenderUtil.displayHTML("-")%></td>       
        </tr>
    </table>