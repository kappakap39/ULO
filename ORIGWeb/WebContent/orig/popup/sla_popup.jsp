<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@ page import="java.util.*"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.model.SLADataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>

<%
	HashMap slaHash = (HashMap) request.getSession(true).getAttribute("SLA");
	request.getSession(true).removeAttribute("SLA");
	HashMap slaRoleHash;
	Vector slaVect;
	
%>
<table cellspacing=0 cellpadding=0 width="100%" align=center border=0 >
	<tr><td height="20">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="left" bgcolor="#FFFFFF">
			<tr><td colspan="3" class="sidebar8">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"> 
				<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	
				<%
						Vector keySet = new Vector(slaHash.keySet());
						for(int i=0; i<keySet.size(); i++){
							slaRoleHash = (HashMap) slaHash.get(keySet.get(i));
					%>
						<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                          	<tr><td class="text-header-detail" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "SLA") %> <%=keySet.get(i)%> 
	                          		</td>
									<td width="330">&nbsp;</td>
								<tr> 
									<td >&nbsp;</td>	
							    </tr>
							</table>
						</td></tr>
						<%		
								Vector keySlaSet=new  Vector(slaRoleHash.keySet());
								for(int j=0;j<keySlaSet.size();j++){ 
								   slaVect=(Vector)slaRoleHash.get(keySlaSet.get(j)); 
								if (slaVect!=null && slaVect.size()>1) { %>
						<tr class="sidebar10"> <td align="center">
							<div id="TWTable">
						    	<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0" bgcolor="#FFFFFF">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS") %></td>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "APPLICATION_NO1") %></td>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_NAME") %></td>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "INTERNAL_CHECKER_CODE") %></td>
												<td width="10%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "MKT_CODE") %></td>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "CREATE_DATE") %></td>
												<td width="15%" align="center" class="Bigtodotext3"><%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE") %></td>
											</tr>
										</table>
									</td></tr>
					
									<%
										SLADataM dataM;
										String titleThai;
										ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
										for (int k=0;k<slaVect.size()-1;k++) {
											dataM = (SLADataM) slaVect.get(k);
											titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", dataM.getTitleName());
									%>
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<TD class="jobopening2" align="center" width="15%"><%=dataM.getAppStatus()%></TD>
												<TD class="jobopening2" align="center" width="15%"><%=dataM.getAppNo()%></TD>
												<TD class="jobopening2" align="center" width="15%"><%=ORIGDisplayFormatUtil.displayHTML(titleThai)%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(dataM.getFirstName())%>&nbsp;<%=ORIGDisplayFormatUtil.displayHTML(dataM.getLastName())%></TD>
												<TD class="jobopening2" align="center" width="15%"><%=ORIGDisplayFormatUtil.displayDashForEmptyString(dataM.getInernalCheckCode())%></TD>
												<TD class="jobopening2" align="center" width="10%"><%=ORIGDisplayFormatUtil.displayDashForEmptyString(dataM.getMktCode())%></TD>
												<TD class="jobopening2" align="center" width="15%"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(dataM.getCreateDate())%></TD>
												<TD class="jobopening2" align="center" width="15%"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(dataM.getUpdateDate())%></TD>
											</TR>
										</table>
									</td></tr>
					
									<%
										} //end for %>
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
										  		<td class="jobopening2" align="center"><b>Total Record</b> = <%=slaVect.get(slaVect.size()-1) %></td>
										  	</tr>
										</table> 
									</td></tr>
								</table>
								</div>
							</td>
						</tr>
						<%					
						} //end if
					   }
						%>
						<tr>
							<td height="10">&nbsp;</td>
						</tr>
				<%
			} //sla role vec
			%>
	
						<tr><td>
							<div align="right">
								<input type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" class="button" onclick="closeWindow()">
							</div>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>
	</table>
	</td></tr>
</table>

<SCRIPT language="JavaScript">
//<!--

function closeActionFlow() {
	document.appFormName.closeWindow.value = "N";
	window.location.href = "tw/filterMainScreen.jsp";
}

function closeWindow() {
	window.close();
}

//-->
</SCRIPT>
<%	//set current screen to main Form
	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute(
		"screenFlowManager");
	screenFlowManager.setCurrentScreen("MAIN_APPFORM");
%>