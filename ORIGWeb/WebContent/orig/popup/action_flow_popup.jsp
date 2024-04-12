<html><head><title>UNIVERSAL LOAN ORIGINATION SYSTEM</title></head>
<%@page import="com.eaf.orig.shared.model.ApplicationLogDataM" %>
<%@page import="java.util.Vector" %>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>

<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
	System.out.println("==> in action_flow_popup.jsp");
	Vector actionFlow = ORIGForm.getAppForm().getActionFlowVect();
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
                          	<tr>
	                          	<td class="text-header-detail"><%=MessageResourceUtil.getTextDescription(request, "ACTION_FLOW") %> </td>
	                            <td width="330">
	                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
	                              		<tr height="30"><td>
	                              			<input type="button" value="<%=MessageResourceUtil.getTextDescription(request, "CLOSE") %>" class="button" onclick="closeWindow()">
	                                    </td></tr>
	                                </table>
	                            </td></tr>
                            </table>
                        </td></tr>
                        <tr> <td  height="15"></td></tr>                        
						<tr class="sidebar10"> <td align="center">
							<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
								<tr><td>								
									<div id="TWTable">
										<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr><td class="TableHeader">
												<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
													<tr>
														<td width="10%" align="center" class="Bigtodotext3">
															<%=MessageResourceUtil.getTextDescription(request, "NO") %></td>
														<td width="25%" align="center" class="Bigtodotext3">
															<%=MessageResourceUtil.getTextDescription(request, "APPLICATION_STATUS") %></td>
														<td width="20%" align="center" class="Bigtodotext3">
															<%=MessageResourceUtil.getTextDescription(request, "ACTION") %></td>
														<td width="20%" align="center" class="Bigtodotext3">
															<%=MessageResourceUtil.getTextDescription(request, "UPDATE_BY") %></td>
														<td width="25%" align="center" class="Bigtodotext3">
															<%=MessageResourceUtil.getTextDescription(request, "UPDATE_DATE") %></td>
													</tr>
												</table> 
											</td></tr>

											<%
											if (actionFlow!=null && actionFlow.size()>0) {
												String sDate;
												for (int i=0;i<actionFlow.size();i++) {
													ApplicationLogDataM bean = (ApplicationLogDataM) actionFlow.get(i);
											%>
											<tr><td align="center" class="gumaygrey2">
												<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
												<TR>
													<TD class="jobopening2" align="center" width="10%"><%=i+1%>.</TD>
													<TD class="jobopening2" align="center" width="25%"><%=bean.getApplicationStatus()%></TD>
													<TD class="jobopening2" align="center" width="20%"><%=bean.getAction()%></TD>
													<TD class="jobopening2" align="center" width="20%"><%=bean.getUpdateBy()%></TD>
													<TD class="jobopening2" align="center" width="25%"><%=ORIGDisplayFormatUtil.dateTimetoStringForThai(bean.getUpdatDate())%></TD>
												</tr>
												</table> 
											</td></tr>

										<%
												} //end for (int i=0;i<vBean.size();i++) {
											} else {
										%>
											<tr><td align="center" class="gumaygrey2">
												<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
													<tr>
												  		<td class="jobopening2" align="center">Results Not Found.</td>
												  	</tr>
												</table> 
											</td></tr>
										<%
											} //end if (vBean!=null && vBean.size()>0) {
										%>
										</table>
									</div>
								</td>
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