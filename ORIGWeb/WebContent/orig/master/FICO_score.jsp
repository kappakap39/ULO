<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.master.shared.model.FicoM" %>


<%
	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;

	FicoM ficoM = (FicoM)request.getSession().getAttribute("FICO_M");
	if(ficoM==null){
		ficoM = new FicoM();
	}
	
	String completed;
	completed = (String)request.getSession().getAttribute("COMPLETED");
	if(completed==null){
		completed="";
	}
	
%>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="0">
	<TR><TD vAlign=top>
	<table cellpadding="0" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
		<tr><td class="sidebar8">
			<table cellSpacing="1" cellPadding="0" width="100%" border="0"> 
		    	<tr><td class="sidebar9">
					<table cellspacing=0 cellpadding=0 width="100%" align=center border=0>	     		
			 			<tr> <td  height="10">
			 				<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          	<tr><td class="text-header-detail">&nbsp;&nbsp;&nbsp;&nbsp; </td>
                            <td width="330">
                            	<table width="70" border="0" align="right" cellpadding="2" cellspacing="0">
                            		<tr height="30"><td>
                                    	<input type="button"  class ="button" name="Reset" value="Reset" onclick="reset1()" >
									</td><td>
										<input type="button"  class ="button" name="Save" value="Save" onclick="saveFicoScore()" >	
									</td><td><FONT color = red><b>&nbsp;&nbsp;<%=completed %></b></FONT>		
                                    </td></tr>
                                </table>
                            </td></tr>	
                            </table>
                        </td></tr>
                        <tr class="sidebar10"> <td align="center">
							<table width="100%" align=center cellpadding="0" cellspacing="1" border="0">
								<tr>
					                <TD class="textColS" width="10%">Accept :</TD>
					                <TD class="textColS" width="15%" align="right">More than and equal to &nbsp;</td>
									<TD class="inputCol" width="12%" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",ficoM.getAcceptScore()),displayMode,"12","accept","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValue() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %> </TD>
									<TD class="textColS" width="50%">score.</TD>
								</TR>
								<TR>
									<TD class="textColS">Refer :</TD>
									<TD class="textColS" align="right">More than and equal to &nbsp;</td>
									<TD class="inputCol" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",ficoM.getRejectScore()),displayMode,"12","rejectRef","textboxReadOnly","readonly","30") %></TD>
									<TD class="textColS"> and</TD>
								</TR>
								<TR>
									<TD class="textColS"></TD>
									<TD class="textColS" align="right">less than &nbsp;</td>
									<TD class="inputCol" nowrap="nowrap"> <%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",ficoM.getAcceptScore()),displayMode,"12","acceptRef","textboxReadOnly","readonly","30") %></TD>
									<TD class="textColS"> score.</TD>
								</TR>
								<TR>
									<TD class="textColS">Reject :</TD>
									<TD class="textColS" align="right">Less than &nbsp;</td>
									<TD class="inputCol" nowrap="nowrap"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",ficoM.getRejectScore()),displayMode,"12","reject","textbox","onKeyPress=\"keyPressIntegerWithMinus(this.value);\" onkeyup=giveValue() onblur=\"javascript:addComma(this)\" onfocus=\"removeCommaField(this)\"","30") %></TD>
									<TD class="textColS"> score.</TD>
								</TR>
							</table>
						</td></tr>
					</table>
				</td></tr>
			</table>
		</td></tr>		
	</table>
	</TD></TR>
</TABLE>

<script language="JavaScript">		

function saveFicoScore(){
		appFormName.action.value="SaveFicoScore";
		appFormName.submit();	
}
function reset1(){
		appFormName.accept.value="";
		appFormName.rejectRef.value="";
		appFormName.acceptRef.value="";
		appFormName.reject.value="";
}
function giveValue(){
	var accept = appFormName.accept.value;
	var reject = appFormName.reject.value;
	
	appFormName.rejectRef.value = reject;
	appFormName.acceptRef.value = accept;
}						
				
</script>	


