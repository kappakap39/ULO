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
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
	Vector personalVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR); 
	
	String appDecision = applicationDataM.getAppDecision();
	String xcmrDecision = applicationDataM.getXcmrDecision();
	if(ORIGUtility.isEmptyString(appDecision)){
		appDecision = xcmrDecision;
	}
%>

<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td colspan="2" class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "SUMMARYPAGE") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                                    	<INPUT type="button" name="pritBtn" value=" <%=MessageResourceUtil.getTextDescription(request, "PRINT") %> " onClick="javascript:print()" class="button">
	                                    </td><td>
	                                    	<INPUT type="button" name="okayBtn" value=" OK " onClick="javascript:backToSearchType()" class="button">
	                                    </td></tr>
	                                </table>
	                            </td>
	                        </tr>
                            <tr>
                            	<td class="Bigtodotext3" colspan="2" align="center">This application has been <%=appDecision%></td>
                            </tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
									<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO") %> :</td>
									<td class="inputCol" width="30%"><%= applicationDataM.getApplicationNo()%></td>
									<td class="textColS" width="20%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %> :</td>
									<td class="inputCol" width="25%"><%= personalInfoDataM.getIdNo()%></td>
								</tr>
								<tr>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_NAME") %> :</td>
									<td class="inputCol"><%= cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName()) %>
									<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiFirstName()) %>
									<%= ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName()) %>
									</td>
									<td class="textColS" valign="top"><%=MessageResourceUtil.getTextDescription(request, "GUARANTOR_NAME") %> :</td>
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
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getFirstName())%>  <%=ORIGDisplayFormatUtil.forHTMLTag(ORIGUser.getLastName())%></td>
									<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE") %> :</td>
									<td class="inputCol"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(new java.util.Date())%></td>
								</tr>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
		</table>
	</td></tr>
</table>

<script language="JavaScript">

function backToSearchType(){

	appFormName.action.value="FristApp";
	appFormName.handleForm.value = "N";
	appFormName.submit(); 
}
</script>