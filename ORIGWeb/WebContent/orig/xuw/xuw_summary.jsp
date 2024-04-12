<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="java.util.Vector" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<%
	com.eaf.orig.shared.model.ApplicationDataM applicationDataM = ORIGForm.getAppForm();	
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ORIGUtility utility = new ORIGUtility();
	
	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	Vector personalVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR); 
	
	String appDecision = applicationDataM.getAppDecision();
	String xcmrDecision = applicationDataM.getXcmrDecision();
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = xcmrDecision;
	}
%>

<table cellpadding="0" cellspacing="0" width="80%" align="center" bgcolor="#FFFFFF">
<tr>
	<td>
		<table cellpadding="0" cellspacing="1" border="0" width="99%" align="center" >
		<tr bgcolor="#F4F4F4">
			<td class="textColS" colspan="4"><center><font size="2"><%=msgUtil.getTextDescription(request, "SUMMARYPAGE") %></font></center></td>
		</tr>
		<tr bgcolor="#F4F4F4">
			<td class="textColS" colspan="4"><center><font size="2" color="blue">This application has been <%=appDecision%></font></center></td>
		</tr>
		<tr>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
			<td class="inputCol" width="30%"><%= applicationDataM.getApplicationNo()%></td>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "CITIZEN_ID") %> :</td>
			<td class="inputCol" width="25%"><%= personalInfoDataM.getIdNo()%></td>
		</tr>
		<tr>
			<td class="textColS"><%=msgUtil.getTextDescription(request, "CUSTOMER_NAME") %> :</td>
			<td class="inputCol"><%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %>
			</td>
			<td class="textColS" valign="top"><%=msgUtil.getTextDescription(request, "GUARANTOR_NAME") %> :</td>
			<td class="inputCol">
			<%	
				if(personalVect!= null){
					PersonalInfoDataM personalInfoDataM2;
					for (int i =0 ; i< personalVect.size() ; i++){ 
						personalInfoDataM2 = (PersonalInfoDataM)personalVect.get(i);
			%>			
			<%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM2.getThaiTitleName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiFirstName()) %>
			<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM2.getThaiLastName()) %>
				<%if((i+1)!=personalVect.size()){
				 %>,&nbsp;<br> 	
				<%}else{
				%>
			</td>
				<%
				}
				 %>
			<%
					}
				}
			 %>
		</tr>
		<tr>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "UPDATE_BY") %> :</td>
			<td class="inputCol" width="30%"><%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getFirstName())%>  <%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getLastName())%></td>
			<td class="textColS" width="20%"><%=msgUtil.getTextDescription(request, "UPDATE_DATE") %> :</td>
			<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(new java.util.Date())%></td>
		</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table cellpadding="0" cellspacing="1" border="0" width="100%" align="center" >
		<tr bgcolor="#FFFFFF">
			<td align="right" colspan="4"><INPUT type="button" name="pritBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "PRINT") %> " onClick="javascript:print()" class="button_text">&nbsp;<INPUT type="button" name="okayBtn" value=" OK " onClick="javascript:backToSearchType()" class="button_text"></td>
		</tr>		
		</table>
	</td>
</tr>
</table>
<script language="JavaScript">

function backToSearchType(){

	appFormName.action.value="FristApp";
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script>