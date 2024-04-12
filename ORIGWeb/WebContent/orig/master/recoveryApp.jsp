<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.wf.shared.model.WorkListM" %>

<jsp:useBean id="CriticalProcessList" scope="session" class="java.util.Vector" />
<jsp:useBean id="RetryErr" scope="session" class="java.util.Vector" />
<% 
for(int i=0; i<RetryErr.size();i++) {
	String errorMessage = (String)RetryErr.elementAt(i);
	out.println("<span class=\"TextWarningNormal\">*&nbsp;Error occur in ProcessInstant "+errorMessage+"</span><br>");
}
request.getSession().removeAttribute("RetryErr");
%>

<input type="hidden" name="recoveryType" value="">

<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">Recovery Application </td>
                            <td width="330">
                            	<table width="50" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<INPUT type="button" name="Retry" value="Force Retry" class="button" onclick="recoveryApp('Retry')">		
									</td><td>
										<INPUT type="button" name="Complete" value="Force Complete" class="button" onclick="recoveryApp('Complete')">			
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr> <td align="center">
							<div id="KLTable">
								<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
									<tr><td class="TableHeader">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
										<tr>
											<td class="Bigtodotext3" width="10%">&nbsp;</td>
											<td width="90%" align="center" class="Bigtodotext3">ProcessInstant Name</td>
										</tr>
										</table> 
									</td></tr>
<%
	if(CriticalProcessList.size()>0){
		for(int i=0; i<CriticalProcessList.size(); i++){
			WorkListM worklistM = (WorkListM)CriticalProcessList.elementAt(i);
%>
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
												<td class="jobopening2" width="10%" align="center"><%=ORIGDisplayFormatUtil.displayCheckBoxTag(worklistM.getAiid()+"#"+worklistM.getProcessInstanceName(),"",ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"recoveryChk","","") %></td>
												<td class="jobopening2" width="90%" align="center"><%=ORIGDisplayFormatUtil.displayHTML(worklistM.getProcessInstanceName()) %>
													<input type="hidden" name="processName" value ="<%=ORIGDisplayFormatUtil.displayHTML(worklistM.getProcessInstanceName()) %>"></td>
											</tr>
										</table> 
									</td></tr>
<%
		}
	}else{
%>	
									<tr><td align="center" class="gumaygrey2">
										<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
											<tr>
									  			<td class="jobopening2" align="center">Not Found Critical Application.</td>
									  		</tr>
										</table> 
									</td></tr>
<%
	}
%>
				
								</TABLE>
							</div>
						</TD></TR>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>
<SCRIPT language="JavaScript">
function recoveryApp(recoverType){
	appFormName.action.value="RecoveryApp";
	appFormName.handleForm.value = "N";
	appFormName.recoveryType.value = recoverType;
	appFormName.submit();
}
</SCRIPT>
