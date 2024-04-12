
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.OtherNameDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("OTHERNAME_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	ORIGUtility utility = new ORIGUtility();
	
	Vector otherNameVect = ORIGForm.getAppForm().getOtherNameDataM();
	
	String positionDesc;
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="4">
			<div id="KLTable">
				<div id="OtherName">
					<table class="gumayframe3" cellpadding="0" cellspacing="1" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="3%"></td>
									<td class="Bigtodotext3" align="center" width="14%"><%=MessageResourceUtil.getTextDescription(request, "CITIZEN_ID") %></td>
									<td class="Bigtodotext3" align="center" width="17%"><%=MessageResourceUtil.getTextDescription(request, "NAME") %></td>
									<td class="Bigtodotext3" align="center" width="17%"><%=MessageResourceUtil.getTextDescription(request, "LASTNAME") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "POSITION") %></td>
									<td class="Bigtodotext3" align="center" width="16%"><%=MessageResourceUtil.getTextDescription(request, "DESCRIPTION") %></td>							
								</tr>
							</table> 
						</td></tr>
						<%if(otherNameVect != null && otherNameVect.size() > 0){
							OtherNameDataM otherNameDataM;
							String disabledChk;
							for(int i=0; i<otherNameVect.size(); i++){
								otherNameDataM = (OtherNameDataM) otherNameVect.get(i);
								disabledChk = "";
								if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
									disabledChk = "disabled";
								}
								positionDesc = cacheUtil.getORIGMasterDisplayNameDataMForPosition(otherNameDataM.getPosition(), "Position", otherNameDataM.getOccupation());
						%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="jobopening2" align="center" width="3%"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","otherNameSeq",String.valueOf(otherNameDataM.getSeq()),"") %></td>
									<td class="jobopening2" align="center" width="14%"><a href="javascript:loadPopup('otherName','LoadOtherNamePopup','850','280','200','100','<%=otherNameDataM.getSeq()%>','','')"><u><%=otherNameDataM.getCitizenId() %></u></a></td>																							
									<td class="jobopening2" width="17%"><%=otherNameDataM.getName() %></td>
									<td class="jobopening2" width="17%"><%=otherNameDataM.getLastName() %></td>
									<td class="jobopening2" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(positionDesc) %></td>
									<td class="jobopening2" width="16%"><%=ORIGDisplayFormatUtil.displayHTML(otherNameDataM.getDescription()) %></td>
								</tr>
							</table> 
						</td></tr>
						<%	} 
						  }else{%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
							  		<td class="jobopening2" align="center">No Record</td>
							  	</tr>
							</table> 
						</td></tr>
						<%}%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20" colspan="4"></td></tr>
	<% if(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT.equals(displayMode)){%>
	<tr>
		<td width="84%"></td>
		<td align="right" width="7%">
			<INPUT type="button" name="addOtherNameBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:loadPopup('otherName','LoadOtherNamePopup','850','280','200','100','','','')" class="button_text">	
		</td><td align="right" width="7%">	
		 	<INPUT type="button" name="deleteOtherNameBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('otherNameSeq','OtherName','DeleteOtherNameServlet')" class="button_text"></td>
		<td width="2%"></td>
	</tr>
	<%} %>
</table>
